package com.smartlab.drivetrain.license;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.LogUtils;

/**
 * Created by smartlab on 16/1/4.
 * 关于
 */
public class About extends BaseActivity implements View.OnClickListener,ActionCallBackListener<Void>{
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        initData();
        initView();



        if(MainApplication.isVar("@yd^&^7"))
        {
            Log.e("sys","true");
        }
        else
        {
            Log.e("sys","false");
        }
        if(MainApplication.isVar("abjhfkhhjsdk32748583"))
        {
            Log.e("sys","true");
        }
        else
        {
            Log.e("sys","false");
        }
        if(MainApplication.isVar("@#&*(_?><*(*"))
        {
            Log.e("sys","true");
        }
        else
        {
            Log.e("sys","false");
        }
        //appAction.WeiXinCheckUserhasBind("WeiXinCheckUserhasBind", "ohVY5syMEHJeqdzpjtD8npYNX1f4", Params.WeiXinCheckUserhasBinded, this);//登录

        //code=   031d9b9353c12a27193a5c41c551fb80

      //  appAction.WeiXinInfoByCode("WeiXinInfoByCode", "ohVY5syMEHJeqdzpjtD8npYNX1f4", Params.WeiXinInfoByCode, this);//登录
       // appAction.WeiXinCheckWeixinPhone("WeiXinCheckWeixinPhone","15027018250",Params.WeiXinCheckWeixinPhone,this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
    }

    @Override
    protected void initView() {
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        if (title != null){
            detail_title.setText(title);
        } else {
            detail_title.setVisibility(View.GONE);
        }
        findViewById(R.id.detail_back).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
                close(this);
                break;
        }
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(Void data) {
        Log.e("sys","Success");
       // appAction.WeiXinLoginByOpenId("WeiXinLoginByOpenId", "ohVY5syMEHJeqdzpjtD8npYNX1f4", "13872315485",Params.WeiXinLoginByOpenId, WeiXinLoginByOpenIdListener);
        showToast(MainApplication.MESSAGE);

    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {

        Log.e("sys", "onFailure");
        showToast(MainApplication.MESSAGE);
    }
    /***
     *
     */
    private ActionCallBackListener<Void> WeiXinLoginByOpenIdListener = new ActionCallBackListener<Void>() {
        @Override
        public void onSuccess(Void data) {
            Log.e("sys","onSuccess WeiXinLoginByOpenId");

            appAction.getUserDetail("getUserInfo", Params.getUser, userinfoListener);

        }

        @Override
        public void onFailure(String errorEvent, String message) {
            showToast(MainApplication.MESSAGE);
        }
    };
    /***
     * 获取用户资料
     */
    private ActionCallBackListener<User> userinfoListener = new ActionCallBackListener<User>() {
        @Override
        public void onSuccess(User data) {
            if (data != null) {
                MainApplication.setUserInfo(data);

            }

        }

        @Override
        public void onFailure(String errorEvent, String message) {
            MainApplication.setBeanDefinitionReaderSid(null);
            MainApplication.setIsLogin(false);


            LogUtils.e(message);
        }
    };

}
