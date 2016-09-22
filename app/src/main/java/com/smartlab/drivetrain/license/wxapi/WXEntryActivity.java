package com.smartlab.drivetrain.license.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.Login;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.MainContent;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.license.TestActivity;
import com.smartlab.drivetrain.util.ActivityCollector;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by zhoudi on 16/4/14.
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler, View.OnClickListener {
    private IWXAPI api;
    private Button weixi;
    @Override
    public Gson getGson() {
        return super.getGson();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void doBack(View view) {
        super.doBack(view);
    }

    @Override
    public void showToast(String str) {
        super.showToast(str);
    }

    @Override
    public void showLongToast(String str) {
        super.showLongToast(str);
    }

    @Override
    public void close(Activity context) {
        super.close(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitywx);

        //注册API
        api = WXAPIFactory.createWXAPI(this, MainApplication.AppID);
        api.registerApp(MainApplication.AppID);


        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
    @Override
    public void onResp(BaseResp resp) {

        String code="";
        Intent inetent=new Intent();


        switch (resp.errCode)
        {
            case BaseResp.ErrCode.ERR_OK:
                //appAction.WeiXinInfoByCode("WeiXinInfoByCode", ((SendAuth.Resp) resp).code,Params.WeiXinInfoByCode, this);
                code=((SendAuth.Resp)resp).code;
               // showToast(code);
                MainApplication.CODE=code;

                inetent.setClass(this, TestActivity.class);
                startActivity(inetent);
                break;

            case BaseResp.ErrCode.ERR_AUTH_DENIED:


                showToast("用户拒绝授权");
                inetent.setClass(this, Login.class);
                startActivity(inetent);

                ActivityCollector.finishAll();
                break;

            case BaseResp.ErrCode.ERR_USER_CANCEL:
                showToast("用户取消");

                inetent.setClass(this, Login.class);
                startActivity(inetent);

                ActivityCollector.finishAll();
                break;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.weixi:
                send();
                break;
        }
    }
    private void send()
    {
        SendAuth.Req req = new SendAuth.Req();

        //授权读取用户信息
        req.scope = "snsapi_userinfo";

        //自定义信息
        req.state = "wechat_sdk_demo_test";


        ;
        boolean falg=api.sendReq(req);
        Log.e("sys","falg="+falg);
    }

}
