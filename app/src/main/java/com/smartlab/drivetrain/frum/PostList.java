package com.smartlab.drivetrain.frum;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.smartlab.drivetrain.adapter.HorizontalListAdapter;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.base.CommonAdapter;
import com.smartlab.drivetrain.base.ViewHolder;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.detail.ImageDetailActivity;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Card;
import com.smartlab.drivetrain.model.CardAnswer;
import com.smartlab.drivetrain.model.CardResponse;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.ScreenUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.EmoteInputView;
import com.smartlab.drivetrain.view.EmoticonsEditText;
import com.smartlab.drivetrain.view.HorizontalListView;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smartlab on 15/10/10.
 * 贴子内容及回复列表
 */
public class PostList extends BaseActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener,View.OnTouchListener,ActionCallBackListener<List<CardResponse>>{
    private Card card;
    private List<CardResponse> cardResponse;
    private int pagination = 1;//当前页码
    private int pageNum = 12;//每页显示的数量
    private EmoticonsEditText response_text;
    private PullToRefreshLayout layout;
    private EmoteInputView response_inputView;
    private ImageButton response_keybord;
    private ImageButton response_emote;
    private ImageButton response_add_pic;
    private LinearLayout upload_layout;
    private ImageButton pic_keybord;
    public String POST_LIST_SER = "PostList";
    private LoadingDialog loadingDialog;
    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            CardResponse cardResponse1 = (CardResponse) adapterView.getAdapter().getItem(i);
            LogUtils.i(cardResponse1.toString());
            hideAll();
        }
    };
    //  显示图片
    private LinearLayout img_pick_layout;
    private HorizontalListView picture_list;
    private HorizontalListAdapter pictureAdapter;
    private List<String> imgPath;
    private int currentState = PullToRefreshLayout.PULL_REFRESH;
    private PostListAdapter<CardResponse> postListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_item);
        getData();
        initView();
    }


    @Override
    protected void initData() {
    }

    private class PostListAdapter<T> extends CommonAdapter<T>{

        public PostListAdapter(Context mContext, List<T> mDatas, int mItemLayoutId) {
            super(mContext, mDatas, mItemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, T item) {
            CardResponse card = (CardResponse) item;
            helper.setText(R.id.response_name,card.getName())
                    .setText(R.id.response_time,card.getCreateTime());
            if (!TextUtils.isEmpty(card.getContent())){

                //使用正则表达式去除html标记
                Pattern pattern = Pattern.compile("<.+?>",Pattern.DOTALL);
                Matcher matcher = pattern.matcher(card.getContent());
                String string = matcher.replaceAll("");
                TextView content = helper.getView(R.id.response_content);
                content.setText(string);
                content.setTextColor(getResources().getColor(R.color.black));
                //过滤出所有img标签下的src地址
                List<String> img_url = Util.getImgSrc(card.getContent());
                ImageView image_one = helper.getView(R.id.image_one);
                ImageView image_two = helper.getView(R.id.image_two);
                ImageView image_three = helper.getView(R.id.image_three);
                ImageView big_pic = helper.getView(R.id.big_pic);
                image_one.setVisibility(View.GONE);
                image_two.setVisibility(View.GONE);
                image_three.setVisibility(View.GONE);
                big_pic.setVisibility(View.GONE);
                if (!img_url.isEmpty()){
                    int size = img_url.size();
                    if (size == 1){
                        imageLoader.displayImage(img_url.get(0),big_pic,new AnimListener());
                        big_pic.setOnClickListener(new ItemViewOnClick(img_url.get(0)));
                        big_pic.setVisibility(View.VISIBLE);
                    }
                    if (size == 2){
                        imageLoader.displayImage(img_url.get(0),image_one);
                        imageLoader.displayImage(img_url.get(1),image_two);
                        image_one.setOnClickListener(new ItemViewOnClick(img_url.get(0)));
                        image_two.setOnClickListener(new ItemViewOnClick(img_url.get(1)));
                        image_one.setVisibility(View.VISIBLE);
                        image_two.setVisibility(View.VISIBLE);
                    }
                    if (size == 3){
                        imageLoader.displayImage(img_url.get(0),image_one);
                        imageLoader.displayImage(img_url.get(1),image_two);
                        imageLoader.displayImage(img_url.get(2),image_three);
                        image_one.setOnClickListener(new ItemViewOnClick(img_url.get(0)));
                        image_two.setOnClickListener(new ItemViewOnClick(img_url.get(1)));
                        image_three.setOnClickListener(new ItemViewOnClick(img_url.get(2)));
                        image_one.setVisibility(View.GONE);
                        image_two.setVisibility(View.GONE);
                        image_three.setVisibility(View.GONE);
                    }
                }
            }
            helper.setImageResource(R.id.response_image,R.mipmap.app_aligame);
        }
        private class ItemViewOnClick implements View.OnClickListener{
            private String url;
            public ItemViewOnClick(String url){
                this.url = url;
            }
            /**
             * Called when a view has been clicked.
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostList.this,ImageDetailActivity.class);
                ImageView img = (ImageView) v;
                img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                intent.putExtra("url", url);
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                intent.putExtra("locationX", location[0]);
                intent.putExtra("locationY", location[1]);
                intent.putExtra("width", img.getWidth());
                intent.putExtra("height", img.getHeight());
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }
        //图片加载监听
        private class AnimListener extends SimpleImageLoadingListener {

            final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                if (loadedImage != null) {
                    ImageView imageView = (ImageView) view;
                    boolean firstDisplay = !displayedImages.contains(imageUri);
                    if (firstDisplay) {
                        FadeInBitmapDisplayer.animate(imageView, 500);
                        displayedImages.add(imageUri);
                    }
                    int width = loadedImage.getWidth();
                    if (width < ScreenUtils.getScreenW()){
                        imageView.setImageBitmap(loadedImage);
                        imageView.setMaxWidth(width);
                    } else {
                        imageView.setImageBitmap(loadedImage);
                    }
                }
            }
        }
    }

    @Override
    protected void initView() {
        loadingDialog = new LoadingDialog(this,R.drawable.loading);
        ListView mListView = (ListView) findViewById(R.id.pull_list);
        cardResponse = new ArrayList<CardResponse>();
        mListView.setOnItemClickListener(listener);
        postListAdapter = new PostListAdapter<>(this,cardResponse,R.layout.response_viewed_item);
        mListView.setAdapter(postListAdapter);
        mListView.setDivider(null);

        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        layout.setOnRefreshListener(this);
        //
        TextView news_title = (TextView) findViewById(R.id.news_title);
        if (card.getTitle()!= null) news_title.setText(card.getTitle());
        //回复帖子
        response_text = (EmoticonsEditText) findViewById(R.id.response_text);
        response_text.setOnTouchListener(this);
        Button btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        // 表情界面
        response_inputView = (EmoteInputView) findViewById(R.id.response_inputView);
        response_keybord = (ImageButton) findViewById(R.id.response_keybord);
        response_emote = (ImageButton) findViewById(R.id.response_emote);

        response_inputView.setEditText(response_text);
        response_keybord.setOnClickListener(this);
        response_emote.setOnClickListener(this);

        //添加图片并上传
        response_add_pic = (ImageButton) findViewById(R.id.response_add_pic);
        pic_keybord = (ImageButton) findViewById(R.id.pic_keybord);
        upload_layout  = (LinearLayout) findViewById(R.id.upload_layout);
        Button upload_camera = (Button) findViewById(R.id.upload_camera);
        Button upload_location = (Button) findViewById(R.id.upload_location);
        upload_location.setOnClickListener(this);
        upload_camera.setOnClickListener(this);
        response_add_pic.setOnClickListener(this);
        pic_keybord.setOnClickListener(this);
        Util.runDelay(layout);
    }

    private void getPostList(int currentState) {
        this.currentState = currentState;
        LogUtils.i("getDetail");
        String timestamp = "" ;
        if (card != null) timestamp = card.getTimestamp();
        User user = MainApplication.getUserInfo();
        if (user != null) {
            String userName = user.getPhone();
            if (!Util.isEmptyString(timestamp)) {
                appAction.loadPostList("showPostList", timestamp, pagination, userName, pageNum, Params.POST_LIST_URL, this);
            }
        }
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        card = (Card) bundle.getSerializable(POST_LIST_SER);
        LogUtils.i(card + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
//                sendText();
                sendPic();
                hideAll();
                break;
            case R.id.response_keybord:
                showKeybordHideAll();
                break;
            case R.id.response_emote:
                showEmojHideAll();
                break;
            case R.id.response_add_pic:

                showUpLoadHideAll();
                break;
            case R.id.upload_camera:

                break;
            case R.id.upload_location:
//                PhotoUtils.selectPhoto(this);
                /**
                 * 1、隐藏按钮
                 * 2、读取图片
                 * 3、以水平列表的形式显示在屏幕下方
                 */
                showLocalPicture();
                break;
            case R.id.pic_keybord:
               showKeybordHideAll();
                break;
            default:
                break;
        }
    }

    private void hideAll() {
//  隐藏表情输入
        if (response_inputView != null){
            if (response_inputView.isShown()){
                response_inputView.setVisibility(View.GONE);
            }
        }
        //  隐藏文件上传
        if (upload_layout != null){
            if (upload_layout.isShown()){
                upload_layout.setVisibility(View.GONE);
            }
        }
        //  隐藏图片选择
        if (img_pick_layout != null){
            if (img_pick_layout.isShown()){
                img_pick_layout.setVisibility(View.GONE);
            }
        }
        //  修改 添加 按钮的状态
        if (pic_keybord.isShown()){
            pic_keybord.setVisibility(View.GONE);
            response_add_pic.setVisibility(View.VISIBLE);
        }
        //  表情按钮
        if (response_keybord.isShown()){
            response_keybord.setVisibility(View.GONE);
            response_emote.setVisibility(View.VISIBLE);
        }
        //  隐藏键盘
        hideKeyBoard();
    }

    /**
     * 发送图片
     */
    private void sendPic() {
        List<String> fileList = getSelectImagePaths();
        if (fileList != null){
            loadingDialog.show();
            for (String file : fileList) {
                appAction.sendPicture(file, Params.File_UpLoad, new ActionCallBackListener<String>() {
                    @Override
                    public void onSuccess(String data) {
                        sendText(data);
                    }

                    @Override
                    public void onFailure(String errorEvent, String message) {
                        showToast(message);
                        loadingDialog.dismiss();
                    }
                });
            }
        } else {
            sendText(null);
        }
    }

    private void showEmojHideAll() {
        //  显示键盘按钮
        if (response_emote.isShown()){
            response_emote.setVisibility(View.GONE);
            response_keybord.setVisibility(View.VISIBLE);
            response_add_pic.setVisibility(View.VISIBLE);
            pic_keybord.setVisibility(View.GONE);
        }
        //  隐藏文件上传
        if (upload_layout != null){
            if (upload_layout.isShown()){
                upload_layout.setVisibility(View.GONE);
            }
        }
        //  隐藏图片选择
        if (img_pick_layout != null) {
            if (img_pick_layout.isShown()) {
                img_pick_layout.setVisibility(View.GONE);
            }
        }
        //  显示表情输入
        if (response_inputView != null){
            if (!response_inputView.isShown()){
                response_inputView.setVisibility(View.VISIBLE);
            }
        }
        hideKeyBoard();

    }

    /**
     * Hide response_add_pic
     */
    private void showUpLoadHideAll() {
        //  隐藏自己
        if (response_add_pic != null){
            if (response_add_pic.isShown()){
                response_add_pic.setVisibility(View.GONE);
                pic_keybord.setVisibility(View.VISIBLE);
                response_keybord.setVisibility(View.GONE);
                response_emote.setVisibility(View.VISIBLE);

            }
        }
        //  隐藏键盘
        hideKeyBoard();
        //  隐藏表情
        if (response_inputView != null){
            if (response_inputView.isShown()){
                response_inputView.setVisibility(View.GONE);
            }
        }
        //  隐藏图片选择
        if (img_pick_layout != null) {
            if (img_pick_layout.isShown()) {
                img_pick_layout.setVisibility(View.GONE);
            }
        }

        //  显示文件上传
        if (upload_layout != null){
            upload_layout.setVisibility(View.VISIBLE);
        }
        //

    }

    //
    private void showKeybordHideAll() {
        //  隐藏自己
        if (response_add_pic != null){
            if (!response_add_pic.isShown()){
                response_add_pic.setVisibility(View.VISIBLE);
                pic_keybord.setVisibility(View.GONE);
            }
        }
        //  表情按钮
        if (response_keybord.isShown()){
            response_keybord.setVisibility(View.GONE);
            response_emote.setVisibility(View.VISIBLE);
        }

        //  隐藏表情输入
        if (response_inputView != null){
            if (response_inputView.isShown()){
                response_inputView.setVisibility(View.GONE);
            }
        }
        //  隐藏文件上传
        if (upload_layout != null){
            if (upload_layout.isShown()){
                upload_layout.setVisibility(View.GONE);
            }
        }
        //  隐藏图片选择
        if (img_pick_layout != null) {
            if (img_pick_layout.isShown()) {
                img_pick_layout.setVisibility(View.GONE);
            }
        }
        //
        showKeyBoard();
        //
    }

    /**
     * 显示本地图片
     */
    private void showLocalPicture() {

        if (img_pick_layout == null){
            img_pick_layout = (LinearLayout) findViewById(R.id.img_pick_layout);
            picture_list = (HorizontalListView) findViewById(R.id.picture_list);
            //  图片地址
            imgPath = Util.getLatestImagePaths(100, this);
            pictureAdapter = new HorizontalListAdapter(this,imgPath);
            picture_list.setAdapter(pictureAdapter);
        }

        if (!img_pick_layout.isShown()){
            upload_layout.setVisibility(View.GONE);
            img_pick_layout.setVisibility(View.VISIBLE);
        }
        if (imgPath != null)
        // 所有的图片地址
        LogUtils.e(imgPath.toString());

    }
    //获取已选择的图片路径
    private ArrayList<String> getSelectImagePaths() {
        if (pictureAdapter != null) {
            SparseBooleanArray map = pictureAdapter.getSelectionMap();
            if (map.size() == 0) {
                return null;
            }

            ArrayList<String> selectedImageList = new ArrayList<>();

            for (int i = 0; i < imgPath.size(); i++) {
                if (map.get(i)) {
                    selectedImageList.add(imgPath.get(i));
                }
            }
            return selectedImageList;
        }
        return null;
    }
    //  发送文字信息
    private void sendText(String filePath) {
        String content = response_text.getText().toString().trim();
        String img = "";
        String timeStamp = DateTools.getTime();
        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
        if (filePath != null){
            //  模拟百度编辑器发送数据到服务，但是为保持服务器不做二次处理，在img与src中间多添加一个空格
            img = img + " <img  src=" + "http://139.129.96.117/mobileImage/" + filePath + " title="
                    + filePath + " alt=" + filePath + "/>";
            img ="</p>" + img + content + "</p>";
            LogUtils.e(img);
        } else {
            img = content;
        }

        if (TextUtils.isEmpty(img)) return;
        CardAnswer answer = new CardAnswer();
        answer.setCmd("addPost");
        answer.setName(MainApplication.getUserInfo().getPhone());
        answer.setTime(timeStamp);
        answer.setTimestamp(card.getTimestamp());
        answer.setContent(img);

        appAction.sendReply(answer.getCmd(), answer.getTimestamp(), answer.getTime(), answer.getName(), answer.getContent(), Params.POST_SEND_TEXT, new ActionCallBackListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                showToast("回复成功");
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                showToast("放松失败");
                loadingDialog.dismiss();
            }
        });

        response_text.setText("");
        response_text.clearFocus();
        //添加item并更新数据
        upDateItem(answer);
    }

    private void upDateItem(CardAnswer answer) {
        CardResponse response = new CardResponse();
        response.setName(answer.getName());
        response.setContent(answer.getContent());
        response.setTitle(answer.getTime());
        cardResponse.add(response);
        postListAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        getPostList(PullToRefreshLayout.PULL_REFRESH);
    }

    /**
     * 加载操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        LogUtils.i("加载更多");
        pagination = pagination + 1;
        getPostList(PullToRefreshLayout.PULL_LOAD);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.response_text:
                response_emote.setVisibility(View.VISIBLE);
                response_keybord.setVisibility(View.GONE);
                showKeyBoard();
                upload_layout.setVisibility(View.GONE);
                response_add_pic.setVisibility(View.VISIBLE);
                pic_keybord.setVisibility(View.GONE);
                //  隐藏图片选择
                if (img_pick_layout != null) {
                    if (img_pick_layout.isShown()) {
                        img_pick_layout.setVisibility(View.GONE);
                    }
                }
                break;
            default:break;
        }
        return false;
    }

    // 返回键的监听
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }

    public void doBack(View view){
        onBackPressed();
    }

    protected void showKeyBoard() {
        if (response_inputView.isShown()) {
            response_inputView.setVisibility(View.GONE);
        }
        response_text.requestFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .showSoftInput(response_text, 0);
    }

    protected void hideKeyBoard() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(PostList.this
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<CardResponse> data) {
        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                cardResponse.clear();
                cardResponse.addAll(data);
                postListAdapter.notifyDataSetChanged();
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                if (data.size() <= 0){
                    pagination --;
                    showToast("没有更多内容。。");
                }
                cardResponse.addAll(data);
                postListAdapter.notifyDataSetChanged();
                layout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
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

        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                layout.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                layout.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
        showToast(message);

    }
}