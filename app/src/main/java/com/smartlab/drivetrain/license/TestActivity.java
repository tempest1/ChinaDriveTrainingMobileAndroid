package com.smartlab.drivetrain.license;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smartlab.drivetrain.adapter.TextWatcherImp;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.base.BaseDialog;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.fragment.UserCenter;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.model.WeiXiAccess;
import com.smartlab.drivetrain.model.WeiXiErrcode;
import com.smartlab.drivetrain.model.WeiXiRefreshToken;
import com.smartlab.drivetrain.model.WeiXinGeData;
import com.smartlab.drivetrain.register.Register;
import com.smartlab.drivetrain.util.ActivityCollector;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.LoadingDialog;
import com.smartlab.drivetrain.view.MyAutocompleteTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by zhoudi on 16/4/15.
 */
public class TestActivity extends BaseActivity implements View.OnClickListener, ActionCallBackListener<Void> {

    private String openid="";
    private String phone="";

    private Button btn_Login;
    private MyAutocompleteTextView userName;
    private LoadingDialog loadingDialog;
    private BaseDialog mBackDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitywx);
        initView();
        initBackDialog();
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        btn_Login=(Button)findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener(this);
        userName=(MyAutocompleteTextView)findViewById(R.id.userName);
        userName.addTextChangedListener(textListener);
        loadingDialog = new LoadingDialog(this,R.drawable.loading);

        appAction.WeiXinInfoByCode("WeiXinInfoByCode", MainApplication.CODE, Params.WeiXinInfoByCode, this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_Login:

                phone=userName.getText().toString().trim();
                if(MainApplication.isVar(phone))
                {
                    showToast("您输入了非法字符");
                }
                else {
                    appAction.WeiXinCheckWeixinPhone("WeiXinCheckWeixinPhone", phone, Params.WeiXinCheckWeixinPhone, WeiXinWeiXinCheckWeixinPhone);
                }
                break;
        }
    }
    // 文本框内容监听器
    private TextWatcher textListener = new TextWatcherImp(){
        @Override
        public void afterTextChanged(Editable editable) {
            updateClickable();
        }
    };
    /**
     *  更新按钮的状态
     **/
    private void updateClickable() {
        if (userName.getText().length() > 0){
            btn_Login.setEnabled(true);
        }else{
            btn_Login.setEnabled(false);
        }
    }
    @Override
    public void onSuccess(Void data) {

        loadingDialog.show();
        String str=MainApplication.MESSAGE;
        WeiXinGeData Data=gson.fromJson(str,WeiXinGeData.class);

        openid=Data.getOpenid();
        appAction.WeiXinCheckUserhasBind("WeiXinCheckUserhasBind", openid, Params.WeiXinCheckUserhasBinded, WeiXinCheckUserhasBindListener);//登录

    }

    @Override
    public void onFailure(String errorEvent, String message) {
        showToast("请重试");
        Intent intent=new Intent();
        intent.setClass(this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.out_from_left, R.anim.in_from_right);
        ActivityCollector.finishAll();
    }

    /***
     *
     */


    private ActionCallBackListener<Void> WeiXinCheckUserhasBindListener = new ActionCallBackListener<Void>() {
        @Override
        public void onSuccess(Void data) {
            appAction.WeiXinLoginByOpenId("WeiXinLoginByOpenId", openid, Params.WeiXinLoginByOpenId, WeiXinLoginByOpenIdListener);

        }

        @Override
        public void onFailure(String errorEvent, String message) {
            showToast(message);

            loadingDialog.dismiss();
            //appAction.WeiXinCheckWeixinPhone("WeiXinCheckWeixinPhone", phone, Params.WeiXinCheckWeixinPhone, WeiXinWeiXinCheckWeixinPhone);

        }
    };


    private ActionCallBackListener<Void> WeiXinWeiXinCheckWeixinPhone = new ActionCallBackListener<Void>() {
        @Override
        public void onSuccess(Void data) {
            appAction.WeiXinMakeBinding("WeiXinLoginByOpenId", openid, phone, Params.WeiXinMakeBinding, WeiXinMakeBindingListener);
        }

        @Override
        public void onFailure(String errorEvent, String message) {
            showToast("您的手机未注册");
//            Intent intent = new Intent();
//            intent.setClass(TestActivity.this,Register.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

            loadingDialog.dismiss();
            mBackDialog.show();
        }
    };




    private ActionCallBackListener<Void> WeiXinLoginByOpenIdListener = new ActionCallBackListener<Void>() {
        @Override
        public void onSuccess(Void data) {
            appAction.getUserDetail("getUserInfo", Params.getUser, userinfoListener);

        }

        @Override
        public void onFailure(String errorEvent, String message) {
            showToast(MainApplication.MESSAGE);
            loadingDialog.dismiss();
        }
    };

    private ActionCallBackListener<Void> WeiXinMakeBindingListener = new ActionCallBackListener<Void>() {
        @Override
        public void onSuccess(Void data) {
            //tv4.setText("onSuccess");
            appAction.WeiXinLoginByOpenId("WeiXinLoginByOpenId", openid, Params.WeiXinLoginByOpenId, WeiXinLoginByOpenIdListener);

        }

        @Override
        public void onFailure(String errorEvent, String message) {
           // tv4.setText("onFailure");
            loadingDialog.dismiss();
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


            Intent intent = new Intent();
            intent.setClass(TestActivity.this, MainContent.class);
            TestActivity.this.finish();
            //ActivityCollector.finishAll();
            MainApplication.setIsLogin(true);
            startActivity(intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            loadingDialog.dismiss();
            showToast("微信登录成功");
        }

        @Override
        public void onFailure(String errorEvent, String message) {
            MainApplication.setBeanDefinitionReaderSid(null);
            MainApplication.setIsLogin(false);

            loadingDialog.dismiss();
            LogUtils.e(message);
        }
    };


    private void initBackDialog() {                
        mBackDialog = BaseDialog.getDialog(TestActivity.this, "提示",
                "该用户是否需要注册", "确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent();

                        Bundle bundle=new Bundle();
                        bundle.putString("type", "register");
                        intent.putExtras(bundle);
                        intent.setClass(TestActivity.this,Register.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                    }
                }, "取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Intent intent=new Intent();
            intent.setClass(this, Login.class);
            startActivity(intent);
            overridePendingTransition(R.anim.out_from_left, R.anim.in_from_right);


            ActivityCollector.finishAll();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
