package com.smartlab.drivetrain.license;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.smartlab.drivetrain.adapter.TextWatcherImp;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.fragment.UserCenter;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.wxapi.WXEntryActivity;
import com.smartlab.drivetrain.register.Forget;
import com.smartlab.drivetrain.register.Register;
import com.smartlab.drivetrain.util.HttpEngine;
import com.smartlab.drivetrain.view.LoadingDialog;
import com.smartlab.drivetrain.view.MyAutocompleteTextView;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by smartlab on 15/9/22.
 * 登录界面     
 */
public class Login extends BaseActivity implements View.OnClickListener,ActionCallBackListener<Void>,IWXAPIEventHandler{
    public static final String KEY_USER_ID="KEY_USER_ID";
    private MyAutocompleteTextView userName;
    private MyAutocompleteTextView passWord;
    private Button btn_Login,weixi;
    private LoadingDialog loadingDialog;
    private CheckBox remember;

    private IWXAPI api;

    private Handler myhandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("sys", "login_layout");
        setContentView(R.layout.login_layout);
        //api注册
        api = WXAPIFactory.createWXAPI(this, MainApplication.AppID);
        api.registerApp(MainApplication.AppID);
        api.handleIntent(getIntent(), this);
        initView();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText("登录");
        findViewById(R.id.detail_back).setOnClickListener(this);
        findViewById(R.id.to_regist).setOnClickListener(this);
        findViewById(R.id.to_forget).setOnClickListener(this);
        userName = (MyAutocompleteTextView) findViewById(R.id.userName);
        passWord = (MyAutocompleteTextView) findViewById(R.id.passWord);
        userName.addTextChangedListener(textListener);
        passWord.addTextChangedListener(textListener);
        btn_Login = (Button) findViewById(R.id.btn_Login);
        weixi=(Button)findViewById(R.id.weixi);
        remember = (CheckBox) findViewById(R.id.remember);
        btn_Login.setOnClickListener(this);
        weixi.setOnClickListener(this);
        loadingDialog = new LoadingDialog(this,R.drawable.loading);
        remember.setChecked(true);  //  默认自动记住密码


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
        if (userName.getText().length() > 0 && passWord.getText().length() > 0){
            btn_Login.setEnabled(true);
        }else{
            btn_Login.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_back:
                // 结束当前界面
                loginClose();
                break;
            case R.id.btn_Login:
                String str="";
                str=passWord.getText().toString().trim();

                if(MainApplication.isVar(str))
                {
                    Log.e("sys",str);
                    showToast("您输入了非法字符");

                }
                else {
                    loadingDialog.show();
                    login();
                }
                break;
            case R.id.to_regist:
                register();
                break;
            case  R.id.to_forget:
                Log.e("sys","log_reg_layout");
                forget();
                break;
            case  R.id.weixi:

                loadingDialog.show();
                send();
//                Intent intent = new Intent();
//
//                intent.setClass(Login.this,Test.class);
//                startActivity(intent);
//                HttpTest();
//                new MyTask().execute("http://www.baidu.com");
                break;
        }
    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        //doInBackground方法内部执行后台任务,不可在此方法内修改UI
        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e("sys","params[0]="+params[0]);
                URL url=new URL(params[0]);
                InputStream is=url.openStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                StringBuffer sb=new StringBuffer();
                String line=null;
                try {
                    while ((line=reader.readLine())!=null)
                    {
                        sb.append(line+"/n");
                    }
                    MainApplication.Data=sb.toString();
                }
                catch (IOException e){
                    e.printStackTrace();
                }finally {
                    try {
                        is.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                Log.e("sys","MainApplication.Data="+MainApplication.Data);

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(String result) {
            Log.e("sys","Data="+MainApplication.Data);
        }

    }


    private void send()
    {
        SendAuth.Req req = new SendAuth.Req();
        //授权读取用户信息
        req.scope = "snsapi_userinfo";
      //  Login.this.finish();
        //自定义信息
        req.state = "wechat_sdk_demo_test";
        boolean falg=api.sendReq(req);
        loadingDialog.dismiss();
        Log.e("sys", "falg=" + falg);
    }


    private void register() {
        Intent intent = new Intent();

        Bundle bundle=new Bundle();
        bundle.putString("type", "register");
        intent.putExtras(bundle);
        intent.setClass(Login.this,Register.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right,R.anim.out_from_left);
    }

    private void forget() {
        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        bundle.putString("type", "forget");
        intent.putExtras(bundle);
        intent.setClass(Login.this, Forget.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }

    private void loginClose() {
        Login.this.finish();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_from_right);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            loginClose();
        }
        return super.onKeyDown(keyCode, event);
    }

    // 登录方法
    private void login() {
        String phone = userName.getText().toString().trim();
        String password = passWord.getText().toString().trim();

        appAction.login("login", phone, password, Params.LOGIN, this);

        //  登录
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 是否记住登录
     */
    private void rememberMe() {
        SharedPreferences mpre = MainApplication.getPreferences();
        SharedPreferences.Editor editor = mpre.edit();
        editor.putBoolean("autoLogin", true);
        editor.putString("phone", userName.getText().toString().trim());
        editor.putString("password", passWord.getText().toString().trim());
        editor.apply();
    }

    /**
     * 登录成功后返回用户中心
     */
    private void backToUserCenter() {
        Intent inten = new Intent();
        inten.putExtra(KEY_USER_ID, userName.getText().toString().trim());
        setResult(RESULT_OK, inten);
        UserCenter.alreadyLogin = true;
        loadingDialog.dismiss();
        loginClose();
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(Void data) {
        if (remember.isChecked()) rememberMe();
        backToUserCenter();
        MainApplication.setIsLogin(true);
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        if (!TextUtils.isEmpty(errorEvent) && !TextUtils.isEmpty(message)) {
            showToast(message);
        } else {
            showToast("登录失败");
            MainApplication.setBeanDefinitionReaderSid(null);
            MainApplication.setIsLogin(false);

        }
        loadingDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onResp(BaseResp baseResp) {




    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}
