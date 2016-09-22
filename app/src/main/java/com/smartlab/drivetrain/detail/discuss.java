package com.smartlab.drivetrain.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.CoachBrief;
import com.smartlab.drivetrain.model.Discuss;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.HttpEngine;
import com.smartlab.drivetrain.view.EmoticonsEditText;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NK on 2016/3/25.
 * 教练点评
 */
public class discuss extends Activity implements View.OnClickListener{
    String CoachName;
    private CheckBox object_one;
    private CheckBox object_two;
    private LoadingDialog loading;
    private CoachBrief evaluate;
//    private int coachTimestamp = 0;
    private EmoticonsEditText edit;
    private RadioGroup choose_radio;

    private static Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_discuss);
        initView();
    }



    public void initView(){
        Intent intent = getIntent();
        if (intent != null){
           CoachName = intent.getStringExtra("CoachName");
            if (CoachName == null) {
                return;
            }
      }
        TextView coachlike= (TextView) findViewById(R.id.coachlike);
        coachlike.setOnClickListener(this);
        TextView textView1= (TextView) findViewById(R.id.diacuss_name);
        textView1.setText(CoachName);
        TextView textView2= (TextView) findViewById(R.id.title);
         textView2.setText("点评等级");
        choose_radio = (RadioGroup) findViewById(R.id.choose_radio);
        RadioButton radioButton1= (RadioButton) findViewById(R.id.grade_one);
        radioButton1.setText("差");
        RadioButton radioButton2= (RadioButton) findViewById(R.id.grade_two);
        radioButton2.setText("一般");
        RadioButton radioButton3= (RadioButton) findViewById(R.id.grade_three);
        radioButton3.setText("好");
        RadioButton radioButton4= (RadioButton) findViewById(R.id.grade_four);
        radioButton4.setText("很好");
        radioButton4.setVisibility(View.VISIBLE);
        edit= (EmoticonsEditText) findViewById(R.id.edit);   //输入框
        Button button = (Button) findViewById(R.id.btn_send);  //提交
        button.setOnClickListener(this);
        Button button1 = (Button) findViewById(R.id.detail_back);//放回按钮
        button1.setOnClickListener(this);
        TextView textView3= (TextView) findViewById(R.id.detail_title);
        textView3.setText("我要点评");

   }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
                this.finish();
                break;
            case R.id.btn_send:
                setdata();
                Toast.makeText(discuss.this, "正在提交！", Toast.LENGTH_SHORT).show();
                new NewsAsyncTask().execute();
//                Toast.makeText(discuss.this, "正在提交中！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.coachlike:
                Intent intent=new Intent(discuss.this,CoachLike.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            default:break;
        }
    }
//   static Discuss obj = new Discuss();
    //  设置发送数据
    User user = MainApplication.getUserInfo();
    private void setdata() {
//        Discuss.coachTimestamp=String.valueOf("1445415657767");
                                 //1444723934180
//        Discuss.drivingTimestamp=String.valueOf("1444723934180");
//        obj.setDrivingTimestamp(String.valueOf("1444723934180"));
        Discuss.cmd="addComment";
//            obj.setCmd("addComment");
        Discuss.content=edit.getText().toString().trim();
//        obj.setContent("CoachName:"+edit.getText().toString().trim());
        switch (choose_radio.getCheckedRadioButtonId()){
            case R.id.grade_one:
                Discuss.grade="1";
//                obj.setGrade("1");
                break;
            case R.id.grade_two:
                Discuss.grade="2";
//                obj.setGrade("2");
                break;
            case R.id.grade_three:
                Discuss.grade="3";
//                obj.setGrade("3");
                break;
            case R.id.grade_four:
                Discuss.grade="4";
//                obj.setGrade("4");
                break;
            default:break;
        }

        if(user==null)
        {
            Discuss.userTimestamp="1445415657767";
//            obj.setUserTimestamp("");
        }
        else {
            Discuss.userTimestamp=user.getTimeStamp();
//            obj.setUserTimestamp(user.getTimeStamp());
        } //获取用户时间戳
//        Log.i("userTimeStamp",user.getTimeStamp());
//        Log.i("coach",obj.getCoachTimestamp());
//        Log.i("driving",obj.getDrivingTimestamp());
//        Log.i("content",obj.getContent());
//        Log.i("grade",obj.getGrade());
//        Type type = new TypeToken<Discuss>(){}.getType();
//        Gson gson=new Gson();
//        String json = gson.toJson(obj);
//        loading.show();
//        appAction.addEvaluate(json, Params.adddiscuss);
//        Log.i("ssy",json);

    }
    public String jsons(){
        Map<String,String> map = new HashMap<>();
        map.put("cmd", Discuss.cmd);//mspeakcontent.getCmd()
        map.put("userTimestamp", Discuss.userTimestamp);//mspeakcontent.getUserTimestamp()
        map.put("drivingTimestamp", Discuss.drivingTimestamp);//mspeakcontent.getDrivingTimestamp()
        map.put("coachTimestamp",Discuss.coachTimestamp );//mspeakcontent.getCoachTimestamp()
        map.put("content",Discuss.content );//mspeakcontent.getContent()
        map.put("grade", Discuss.grade); //mspeakcontent.getGrade()
//        map.put("cmd", Discuss.cmd);//mspeakcontent.getCmd()
//        map.put("phone", Discuss.likephone);//mspeakcontent.getUserTimestamp()
//        map.put("name", Discuss.likename);//mspeakcontent.getDrivingTimestamp()
//        map.put("coachTimestamp",Discuss.coachTimestamp );
        String json = gson.toJson(map);
        return json;
    }

    class NewsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpEngine.getCoachlike(jsons(), Params.adddiscuss);//.getJsonData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(Discuss.res.equals("true"))// obj.getres()=="true"
            {
                Toast.makeText(discuss.this, "提交成功！", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(discuss.this, "提交失败！", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
