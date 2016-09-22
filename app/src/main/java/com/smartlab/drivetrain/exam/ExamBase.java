package com.smartlab.drivetrain.exam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.base.BaseDialog;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.database.ExamServer;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.license.Result;
import com.smartlab.drivetrain.model.Question;
import com.smartlab.drivetrain.picture.PictureUrl;
import com.smartlab.drivetrain.service.PictureDownload;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.PhotoUtils;
import com.smartlab.drivetrain.util.ScreenUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.EmptyLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by smartlab on 15/12/17.
 *
 */
public abstract class ExamBase extends BaseActivity implements View.OnClickListener,Chronometer.OnChronometerTickListener,ActionCallBackListener<List<Question>> {
    public static final String Parcelable_q = "question";
    //  底部
    protected TextView exam_content;
    protected ImageView exam_image;
    protected Button page_up;
    protected Button commit;
    protected Chronometer last_time;
    protected Button page_down;
    protected List<Question> all_question;

    //  单选题
    protected RadioGroup answer;
    protected RadioButton optionA;
    protected RadioButton optionB;
    protected RadioButton optionC;
    protected RadioButton optionD;

    //  正在加载
    protected EmptyLayout error_layout;

    //  当前木
    protected int currentIndex = 0;
    protected int totalExam = 0;
    protected ImageLoader imageLoader = MainApplication.getImageLoader();
    protected SparseArray<String> userAnswer = new SparseArray< >() ;//用于记录题目是否已经做过了
    protected int second = 0;
    protected int minute = 45;
    protected AlertDialog.Builder dialog;
    private int singleSource = 0;

    //多选题
    protected CheckBox checkA;
    protected CheckBox checkB;
    protected CheckBox checkC;
    protected CheckBox checkD;
    protected LinearLayout check_layout;
    protected StringBuilder moreCheck = new StringBuilder();

    //视屏
    protected View play_btn;
    protected SuperVideoPlayer video_player;
    protected FrameLayout video_fram;
    protected String video_Url;

    protected Bitmap bitmap = null;

    private String yes;
    private String no;

    private int imageWidth  = 0;
    private int imageHeight = 0;

    protected TextView detail_title;
    //  当前开始题目类型 科目一，科目二
    private String km = "";
    protected ExamServer server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        server = new ExamServer(this);
    }

    @Override
    protected void initData() {
        imageWidth = ScreenUtils.getScreenW();  //  设置图片最大宽度为屏幕宽度
        imageHeight = Util.dip2px(this, 200);        //  图片最大高度200dip对应的像素值
    }

    @Override
    protected void initView() {
        error_layout = (EmptyLayout) findViewById(R.id.error_layout);
        all_question = new ArrayList<>();
        findViewById(R.id.detail_back).setOnClickListener(this);
        detail_title = (TextView) findViewById(R.id.detail_title);
        exam_content = (TextView) findViewById(R.id.exam_content);
        answer = (RadioGroup) findViewById(R.id.answer);
        exam_image = (ImageView) findViewById(R.id.exam_image);
        page_up = (Button) findViewById(R.id.page_up);
        page_down = (Button) findViewById(R.id.page_down);
        last_time = (Chronometer) findViewById(R.id.last_time);
        optionA = (RadioButton) findViewById(R.id.optionA);
        optionB = (RadioButton) findViewById(R.id.optionB);
        optionC = (RadioButton) findViewById(R.id.optionC);
        optionD = (RadioButton) findViewById(R.id.optionD);
        commit = (Button) findViewById(R.id.commit);
        //detail_title.setText(getResources().getString(R.string.test_on_line, "科一"));
        //detail_title.setText(getResources().getString(R.string.test_on_line, "科四"));
        yes = getResources().getString(R.string.yes);
        no = getResources().getString(R.string.no);

    }
    //多选题
    private void initCheckLayout(){
        check_layout = (LinearLayout) findViewById(R.id.check_layout);
        checkA = (CheckBox) findViewById(R.id.checkA);
        checkB = (CheckBox) findViewById(R.id.checkB);
        checkC = (CheckBox) findViewById(R.id.checkC);
        checkD = (CheckBox) findViewById(R.id.checkD);

        // 加载视屏
        play_btn = findViewById(R.id.play_btn);
        video_player = (SuperVideoPlayer) findViewById(R.id.video_player);
        video_fram = (FrameLayout) findViewById(R.id.video_fram);
        play_btn.setOnClickListener(this);


    }
    //  加载科目一的题目
    public void loadExamOne(){
        String object = "{\"cmd\":\"getExamination\"}";
        appAction.getExams(object, Params.EXAM1, this);
        singleSource = 1;
        //  科目一
        km = "1";
    }
    //  加载科目四的题目
    public void loadExamFour(){
        String object = "{\"cmd\":\"getExamination4\"}";
        appAction.getExams(object, Params.EXAM4, this);
        singleSource = 2;
        //  表示科目四
        km = "4";
        initCheckLayout();
    }
    // 加载本地错题
    public void loadError(final int i){
        all_question = new ArrayList<>();
        new AsyncTask<Void, Void, List<Question>>() {
            @Override
            protected List<Question> doInBackground(Void... params) {
                return server.queryQuestion(i);
            }

            @Override
            protected void onPostExecute(List<Question> questions) {
                String msg = "";
                if (i == 1) {
                    msg = "暂无科一错题";
                } else if (i == 4){
                    msg = "暂无科四错题";
                }
                if (questions == null){
                    showToast(msg);
                    onBackPressed();
                    return;
                }
                if (!questions.isEmpty()) {
                    all_question.clear();
                    all_question.addAll(questions);
                    LogUtils.e(all_question.toString());
                    error_layout.dismiss();
                    totalExam = all_question.size() - 1;
                    setData(all_question.get(0));
                    commit.setEnabled(false);
                    commit.setText(getResources().getString(R.string.totalExam, totalExam+1));
                } else {
                    error_layout.dismiss();
                    showToast(msg);
                    onBackPressed();
                }

            }
        }.execute();
    }
    //  添加事件
    protected void initEvent() {
        page_up.setOnClickListener(this);
        page_down.setOnClickListener(this);
        commit.setOnClickListener(this);
        last_time.setText(nowtime());
        last_time.setOnChronometerTickListener(this);
        error_layout.setOnLayoutClickListener(this);
        optionA.setOnClickListener(this);
        optionB.setOnClickListener(this);
        optionC.setOnClickListener(this);
        optionD.setOnClickListener(this);
    }
    //  当前时间
    private String nowtime() {
        if (second < 10){
            return minute + ":0"+second;
        }else {
            return minute + ":" + second;
        }
    }
    //  获取截止到当前的总分
    private int totalScore(){
        int score = getScore();
        //  将用户的填写的答案与正确答案进行比较
        for (int i = 0;i < all_question.size();i++){
            Object a = userAnswer.get(i,null);
            if (a != null) {
                String right = all_question.get(i).getStda().toLowerCase();
                LogUtils.e(right);
                LogUtils.e(a.toString());
                if (right.equals(a.toString())){
                    score = score + singleSource;
                    LogUtils.e(a.toString());
                }
            }
        }
        LogUtils.e(String.valueOf(userAnswer.size()));
        return score;
    }
    //当前题目是否正确
    private int getScore() {
        int score = 0;
        //获取这一题的答案
        Question Q = all_question.get(currentIndex);
        String currentAnswer = Q.getStda().toLowerCase();
        //在这里加一层过滤，若果某题目已经做过了，不允许修改，不允许重复做
        //判断答案是否正确
//        if (singleSource != 0) {

            if (optionA.isChecked()) {
                if ("a".equals(currentAnswer) || "y".equals(currentAnswer))
                    score = singleSource;
            } else if (optionB.isChecked()) {
                if ("b".equals(currentAnswer) || "n".equals(currentAnswer))
                    score = singleSource;
            } else if (optionC.isChecked()) {
                if ("c".equals(currentAnswer)) score = singleSource;
            } else if (optionD.isChecked()) {
                if ("d".equals(currentAnswer)) score = singleSource;
            }

            if (check_layout != null && check_layout.isShown()){
                // 获取多选题的答案，
                moreCheck.setLength(0);//初始化，
                if (checkA.isChecked()) moreCheck.append("a");
                if (checkB.isChecked()) moreCheck.append("b");
                if (checkC.isChecked()) moreCheck.append("c");
                if (checkD.isChecked()) moreCheck.append("d");
                LogUtils.i("userAnswer" + moreCheck.toString());

                if (moreCheck.toString().equals(currentAnswer)) score = singleSource;
            }

//        }

        return score;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_back:
                onBackPressed();
                break;
            case R.id.page_down:
                nextEntitle();
                break;
            case R.id.page_up:
                pageUp();
                break;
            case R.id.commit:
                showToast(String.valueOf(totalScore()));
                showCustomDialog();
                break;
            case R.id.play_btn:
                play_btn.setVisibility(View.GONE);
                if (video_Url != null) {
                    Video video = new Video();
                    VideoUrl videoUrl = new VideoUrl();
                    videoUrl.setFormatName("480p");
                    videoUrl.setFormatUrl(video_Url);
                    video.setPlayUrl(videoUrl);
                    ArrayList<Video> videoList = new ArrayList<>();
                    videoList.add(video);
                    video_player.loadMultipleVideo(videoList);
                }
                break;
            case R.id.optionA:
                LogUtils.e("A");
                if (getScore() == 0) {
                    record();
                    showRightAnswer();
                    //
                } else {
                    LogUtils.e(String.valueOf(getScore()));
                    nextEntitle();
                }
                break;
            case R.id.optionB:
                LogUtils.e("B");
                if (getScore() == 0) {
                    record();
                    showRightAnswer();
                } else {
                    nextEntitle();
                }
                break;
            case R.id.optionC:
                LogUtils.e("C");
                if (getScore() == 0) {
                    record();
                    showRightAnswer();
                } else {
                    nextEntitle();
                }
                break;
            case R.id.optionD:
                LogUtils.e("D");
                if (getScore() == 0) {
                    record();
                    showRightAnswer();
                } else {
                    nextEntitle();
                }
                break;
            case R.id.error_layout:
                if (singleSource == 1){
                    loadExamOne();
                } else if (singleSource == 2){
                    loadExamFour();
                }
                break;
            default:break;

        }
    }
    /**
     *  记录用户错误试题
     */
    public void record(){
        final Question all = all_question.get(currentIndex);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // 插入错题
                server.insertExam(km,all);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        }.execute();


    }
    //  交卷提醒
    private void showCustomDialog() {
        if (userAnswer.size() < 100) {
            alertDialog("题目还没有做完真的确认交卷吗？");
        } else {
            alertDialog("确认要交卷吗？");
        }
    }

    //
    private void alertDialog(String s) {
        dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(s);
        dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 结束模拟
                Intent intent = new Intent();
                intent.setClass(ExamBase.this, Result.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Parcelable_q, (ArrayList<? extends Parcelable>) all_question);
//                startActivity(intent);
                ExamBase.this.finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<Question> data) {
        if (data != null){
            LogUtils.e(data.toString());
            all_question.clear();
            all_question.addAll(data);
            totalExam = all_question.size() - 1;
            LogUtils.i(all_question.toString());
            error_layout.setVisibility(View.GONE);
            //  对List数据举行排序
            Collections.sort(all_question);

            //  开始批量下载图片
            List<String> urls = PictureUrl.getPicture(data);
            PictureDownload load = new PictureDownload(Params.IMAGE_CACHE, urls, new PictureDownload.DownloadStateListener() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onFailed() {

                }
            });
            load.startDownload();
            //  填充数据
            setData(all_question.get(0));
        }
    }

    public void showRightAnswer(){
        final BaseDialog dialog = new BaseDialog(this);
        dialog.setDialogContentView(R.layout.exam_error_layout);
        RadioGroup answer = (RadioGroup) dialog.findViewById(R.id.answer);
        RadioButton optionA = (RadioButton) dialog.findViewById(R.id.optionA);
        RadioButton optionB = (RadioButton) dialog.findViewById(R.id.optionB);
        RadioButton optionC = (RadioButton) dialog.findViewById(R.id.optionC);
        RadioButton optionD = (RadioButton) dialog.findViewById(R.id.optionD);
        LinearLayout check_layout = (LinearLayout) dialog.findViewById(R.id.check_layout);
        CheckBox checkA = (CheckBox) dialog.findViewById(R.id.checkA);
        CheckBox checkB = (CheckBox) dialog.findViewById(R.id.checkB);
        CheckBox checkC = (CheckBox) dialog.findViewById(R.id.checkC);
        CheckBox checkD = (CheckBox) dialog.findViewById(R.id.checkD);
        ImageView exam_image = (ImageView) dialog.findViewById(R.id.exam_image);
        TextView right_answer = (TextView) dialog.findViewById(R.id.right_answer);
        optionA.setEnabled(false);
        optionC.setEnabled(false);
        optionB.setEnabled(false);
        optionD.setEnabled(false);
        answer.clearCheck();

        checkA.setEnabled(false);
        checkB.setEnabled(false);
        checkC.setEnabled(false);
        checkD.setEnabled(false);
        dialog.setTitleBackground(R.color.blue);
        dialog.setTitle(getResources().getString(R.string.error_exam));
        dialog.setTitleColor(R.color.white);
        Question q = all_question.get(currentIndex);
        TextView exam_content = (TextView) dialog.findViewById(R.id.exam_content);
        if (q.getSttx() == 1){
            optionA.setText(yes);
            optionB.setText(no);
            optionC.setVisibility(View.GONE);
            optionD.setVisibility(View.GONE);

        }
        if (q.getSttx() == 2){
            optionA.setText(q.getStdaA());
            optionB.setText(q.getStdaB());
            optionC.setText(q.getStdaC());
            optionD.setText(q.getStdaD());
        }
        if (q.getSttx() == 3){
            check_layout.setVisibility(View.VISIBLE);
            answer.setVisibility(View.GONE);
            checkA.setText(q.getStdaA());
            checkB.setText(q.getStdaA());
            checkC.setText(q.getStdaA());
            checkD.setText(q.getStdaA());
        }

        if (q.getSttx() == 3) {
            String s = q.getIndex() + "(多选)" + q.getStnr();
            exam_content.setText(s);
        } else {
            String s = q.getIndex() + q.getStnr();
            exam_content.setText(s);
        }

        if (q.getStsx() == 2){
            if (bitmap != null) exam_image.setImageBitmap(bitmap);
        } else {
            exam_image.setVisibility(View.GONE);
        }
        String ans = q.getStda().toLowerCase();
        if (q.getSttx() == 3) {
            char[] array = ans.toCharArray();
            for (char a : array) {
                String c = String.valueOf(a);
                if ("a".equals(c)) {
                    checkA.setChecked(true);
                    LogUtils.i("a is check");
                }
                if ("b".equals(c)) {
                    checkB.setChecked(true);
                    LogUtils.i("b is check");
                }
                if ("c".equals(c)) {
                    checkC.setChecked(true);
                    LogUtils.i("c is check");
                }
                if ("d".equals(c)) {
                    checkD.setChecked(true);
                    LogUtils.i("d is check");
                }
                LogUtils.i(c + "");
            }
        } else {
            if (q.getSttx() == 1) {
                LogUtils.e(ans);
                switch (ans) {
                    case "y":
                        optionA.setChecked(true);
                        right_answer.setText(yes);
                        break;
                    case "n":
                        optionB.setChecked(true);
                        right_answer.setText(no);
                        break;
                }
            } else {
                switch (ans) {
                    case "a":
                        optionA.setChecked(true);
                        right_answer.setText("A");
                        break;
                    case "b":
                        optionB.setChecked(true);
                        right_answer.setText("B");
                        break;
                    case "c":
                        optionC.setChecked(true);
                        right_answer.setText("C");
                        break;
                    case "d":
                        optionD.setChecked(true);
                        right_answer.setText("D");
                        break;
                    default:
                        break;
                }

            }
            LogUtils.i(ans);
        }


        Button continue_exam = (Button) dialog.findViewById(R.id.continue_exam);
        continue_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                nextEntitle();
            }
        });
        dialog.show();
    }
    //下一题
    private void nextEntitle() {
        if (all_question != null) {
            // 每道题的分数是1
            if (currentIndex < totalExam) {
                isDoneCurrent();
                currentIndex++;
                isDoneNext();
                setData(all_question.get(currentIndex));
                LogUtils.e("done next");
            } else {
                Toast.makeText(this, "已经是最后题了", Toast.LENGTH_SHORT).show();
                //自动提交试卷，评分并结束考试
            }
            LogUtils.e(String.valueOf(currentIndex));
        }
    }
    //  需要在确定交卷的时候，调用用于存储用户最后做的的那道题的答案
    public void isDoneCurrent() {
        //对于多选题
        if (all_question.get(currentIndex).getSttx() == 3){
            if (userAnswer.get(currentIndex) == null) {
                moreCheck.setLength(0); //  初始化，
                if (checkA.isChecked()) moreCheck.append("a");
                if (checkB.isChecked()) moreCheck.append("b");
                if (checkC.isChecked()) moreCheck.append("c");
                if (checkD.isChecked()) moreCheck.append("d");
                userAnswer.put(currentIndex, moreCheck.toString());
                LogUtils.i("currentAnswer" + moreCheck.toString());
                moreCheck.setLength(0); //   每次记得初始化
            } else if (userAnswer.get(currentIndex).equals("")) {
                moreCheck.setLength(0); //   初始化，
                if (checkA.isChecked()) moreCheck.append("a");
                if (checkB.isChecked()) moreCheck.append("b");
                if (checkC.isChecked()) moreCheck.append("c");
                if (checkD.isChecked()) moreCheck.append("d");
                userAnswer.put(currentIndex, moreCheck.toString());
                LogUtils.i("currentAnswer" + moreCheck.toString());
                moreCheck.setLength(0); //   每次记得初始化
            }else {
                LogUtils.i(userAnswer.get(currentIndex));
            }
            //每次做完都需要初始化状态
            checkA.setChecked(false);
            checkB.setChecked(false);
            checkC.setChecked(false);
            checkD.setChecked(false);
        } else {
            //  对于单选或者判断题
            if (answer.getCheckedRadioButtonId() != -1){
                //  若选择了答案判断缓存中是否有
                if (userAnswer.get(currentIndex) == null){
                    userAnswer.put(currentIndex,getUserAnswer(answer.getCheckedRadioButtonId()));
                    answer.clearCheck();
                }
            }
        }
    }
    //  获取用户选中的答案
    private String getUserAnswer(int checkedRadioButtonId) {
        //  若题目为判断题,C,D选项不可见
        if (optionC.getVisibility() == View.GONE){
            switch (checkedRadioButtonId) {
                case R.id.optionA:
                    return "y";
                case R.id.optionB:
                    return "n";
                default:
                    return null;
            }
        }
        //  单选题
        switch (checkedRadioButtonId){
            case R.id.optionA:
                return "a";
            case R.id.optionB:
                return "b";
            case R.id.optionC:
                return "c";
            case R.id.optionD:
                return "d";
            default:
                return null;
        }
    }
    //上一题
    private void pageUp(){
        if (all_question != null){
            if (currentIndex > 0){
                isDoneCurrent();
                currentIndex--;
                isDoneNext();//获取下一题是否做了
                setData(all_question.get(currentIndex));
                LogUtils.e(String.valueOf(currentIndex));
            }else{
                Toast.makeText(this, "已经是第一题了", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //题目切换的之前判断当前的题目是否已经填写了答案
    private void isDoneNext(){
        if (userAnswer != null){
            //若当前题目已经做过了，显示之前填写的答案
            if (userAnswer.get(currentIndex) != null ){
                //对于多选判断是否是否已经做过了
                if (all_question.get(currentIndex).getSttx() == 3) {
                    String temp = (String) userAnswer.get(currentIndex);
                    char[] array = temp.toCharArray();
                    for (char a : array) {
                        String c = String.valueOf(a);
                        if ("a".equals(c)) {
                            checkA.setChecked(true);
                            LogUtils.i("a is check");
                        }
                        if ("b".equals(c)) {
                            checkB.setChecked(true);
                            LogUtils.i("b is check");
                        }
                        if ("c".equals(c)) {
                            checkC.setChecked(true);
                            LogUtils.i("c is check");
                        }
                        if ("d".equals(c)) {
                            checkD.setChecked(true);
                            LogUtils.i("d is check");
                        }
                        LogUtils.i(c + "");
                    }
                    checkA.setClickable(false);
                    checkB.setClickable(false);
                    checkC.setClickable(false);
                    checkD.setClickable(false);
                } else {
                    String temp = (String) userAnswer.get(currentIndex);
                    if (optionC.getVisibility() == View.GONE) {
                        switch (temp) {
                            case "y":
                                optionA.setChecked(true);
                                break;
                            case "n":
                                optionB.setChecked(true);
                                break;
                        }
                    } else {
                        switch (temp) {
                            case "a":
                                optionA.setChecked(true);
                                break;
                            case "b":
                                optionB.setChecked(true);
                                break;
                            case "c":
                                optionC.setChecked(true);
                                break;
                            case "d":
                                optionD.setChecked(true);
                                break;
                            default:
                                break;
                        }
                        LogUtils.i(temp);
                    }
                }
                optionA.setClickable(false);
                optionC.setClickable(false);
                optionB.setClickable(false);
                optionD.setClickable(false);
            } else {
                if (answer.isShown()) {
                    answer.clearCheck();
                    optionA.setClickable(true);
                    optionB.setClickable(true);
                    optionC.setClickable(true);
                    optionD.setClickable(true);
                }
                if (check_layout != null){
                    if(check_layout.isShown()) {
                        //初始化
                        checkA.setChecked(false);
                        checkB.setChecked(false);
                        checkC.setChecked(false);
                        checkD.setChecked(false);
                        //
                        checkA.setClickable(true);
                        checkB.setClickable(true);
                        checkC.setClickable(true);
                        checkD.setClickable(true);
                    }
                }
            }
        }
    }
    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        error_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
        showToast(message);
    }
    private void setData(Question all_question) {
        last_time.start();
        if (all_question != null) {
            // 普通题目
            if (all_question.getStsx() == 1) {
                if (video_fram != null) if (video_fram.isShown()) video_fram.setVisibility(View.GONE);
                if (exam_image.getVisibility() == View.VISIBLE) exam_image.setVisibility(View.GONE);//隐藏加载图片的
                switchType(all_question);
            }
            //需要加载图片的题
            else if (all_question.getStsx() == 2){
                if (video_fram != null) if (video_fram.isShown()) video_fram.setVisibility(View.GONE);
                if (exam_image.getVisibility() == View.GONE) exam_image.setVisibility(View.VISIBLE);//显示加载图片的
                switchType(all_question);
                String pathString =  Params.IMAGE_CACHE + Util.makeMD5(all_question.getStxh() + ".jpg");

                File image = new File(pathString);//    文件是否存在
                if (image.exists()){
                    bitmap = PhotoUtils.resizeImage(pathString, imageWidth, imageHeight);

                    exam_image.setImageBitmap(bitmap);
                } else {
                    imageLoader.displayImage(Params.EXAM_PIC + all_question.getStxh()+".jpg",exam_image);
                }

            }
            //需要加载视屏的题目
            else if (all_question.getStsx() == 3){
                if (exam_image.getVisibility() == View.VISIBLE) exam_image.setVisibility(View.GONE);//隐藏加载图片的
                LogUtils.i(Params.EXAM_PIC + all_question.getStxh() + ".flv");
                video_Url = Params.EXAM_PIC + all_question.getStxh() + ".flv";
                video_fram.setVisibility(View.VISIBLE);
                play_btn.setVisibility(View.VISIBLE);
                video_player.stopPlay();
            }
        }
    }
    //题目类型
    private void switchType(Question all_question){
        switch (all_question.getSttx()){
            //对错题
            case 1:
                answer.setVisibility(View.VISIBLE);
                if (check_layout != null && check_layout.isShown()) check_layout.setVisibility(View.GONE);//隐藏多选题
                decide(all_question);
                break;
            //四项单选
            case 2:
                if (check_layout != null && check_layout.isShown()) check_layout.setVisibility(View.GONE);//隐藏多选题
                answer.setVisibility(View.VISIBLE);
                singleChoose(all_question);
                break;
            //四项多选
            case 3:
                //隐藏单选按钮
                answer.setVisibility(View.GONE);
                check_layout.setVisibility(View.VISIBLE);
                moreChoose(all_question);
                break;
            default:break;
        }
    }
    //判断题
    private void decide(Question all_question){
        exam_content.setText(all_question.getIndex()+"、"+all_question.getStnr());
        optionA.setText(yes);
        optionB.setText(no);
        if (optionC.getVisibility() == View.VISIBLE)
            optionC.setVisibility(View.GONE);
        if (optionD.getVisibility() == View.VISIBLE)
            optionD.setVisibility(View.GONE);
    }
    //单选题
    private void singleChoose(Question all_question){
        exam_content.setText(all_question.getIndex()+"、"+all_question.getStnr());
        optionA.setText(all_question.getStdaA());
        optionB.setText(all_question.getStdaB());
        optionC.setText(all_question.getStdaC());
        optionD.setText(all_question.getStdaD());
        optionA.setVisibility(View.VISIBLE);
        optionB.setVisibility(View.VISIBLE);
        optionC.setVisibility(View.VISIBLE);
        optionD.setVisibility(View.VISIBLE);
    }
    //多选题
    private void moreChoose(Question all_question){
        exam_content.setText(all_question.getIndex()+"、（多选）"+all_question.getStnr());
        checkA.setText(all_question.getStdaA());
        checkB.setText(all_question.getStdaB());
        checkC.setText(all_question.getStdaC());
        checkD.setText(all_question.getStdaD());
    }

    /**
     * Notification that the chronometer has changed.
     *
     * @param chronometer
     */
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        //倒计时
        second --;
        //秒数变为0时分数减一，同时秒数归位
        if (second == -1){
            minute --;
            second  = 59;
        }
        //倒计时结束
        if (minute == 0 && second == 0){
            last_time.stop();
            //计时结束后立刻交卷

        }
        //时间不足五分钟时变为红色
        if (minute < 5) {
            chronometer.setTextColor(Color.RED);
            chronometer.setText(nowtime());
        } else {
            chronometer.setTextColor(Color.GREEN);
            chronometer.setText(nowtime());
        }
    }

    @Override
    protected void onDestroy() {
        if (server != null){
            server.close();
            LogUtils.e("db close");
        }
        super.onDestroy();
    }
}
