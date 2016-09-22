package com.smartlab.drivetrain.detail;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Discuss;
import com.smartlab.drivetrain.util.HttpEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 教练点评我喜欢功能
 * Created by NK on 2016/4/9.
 */
public class CoachLike extends Activity implements View.OnClickListener {

    private static Gson gson = new Gson();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coachlikes);
        init();
    }
    EditText phone,name;
    public void init() {
        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText("我看中的教练");
        Button back = (Button) findViewById(R.id.detail_back);
        back.setOnClickListener(this);
        Button complate= (Button) findViewById(R.id.coachlikecomplates);
        complate.setOnClickListener(this);
         phone= (EditText) findViewById(R.id.coachlikephones);
         name= (EditText) findViewById(R.id.coachlikenames);

    }

    public boolean checkde(){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(Discuss.likephone);

        if(Discuss.likephone.equals("")||Discuss.likephone==null)
        {
            Toast.makeText(this,"手机号不能为空",Toast.LENGTH_LONG).show();
            return false;
        }

        if (!m.matches()){
            Toast.makeText(this,"手机号格式不正确",Toast.LENGTH_LONG).show();
            return false;
        }

       if(Discuss.likename.equals("")||Discuss.likename==null)
        {
            Toast.makeText(this,"姓名不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
       return true;

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                CoachLike.this.finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                break;
            case R.id.coachlikecomplates:
                initDate();
                if(checkde())
                {
                    Toast.makeText(CoachLike.this, "正在提交！", Toast.LENGTH_SHORT).show();
                    new NewsAsyncTask().execute();
                }

        }
    }
    public void initDate(){
        Discuss.likename=name.getText().toString();
        Discuss.likephone=phone.getText().toString();
        Discuss.cmd="addUserCoach";
    }

    public String jsons(){
        Map<String,String> map = new HashMap<>();
        map.put("cmd", Discuss.cmd);//mspeakcontent.getCmd()
        map.put("phone", Discuss.likephone);//mspeakcontent.getUserTimestamp()
        map.put("name", Discuss.likename);//mspeakcontent.getDrivingTimestamp()
        map.put("coachTimestamp",Discuss.coachTimestamp );
        String json = gson.toJson(map);
        return json;
    }
    class NewsAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpEngine.getCoachlike(jsons(), Params.addUserCoach);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(Discuss.res.equals("true"))// obj.getres()=="true"
            {
                Toast.makeText(CoachLike.this, "提交成功！", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(CoachLike.this, "提交失败！", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
