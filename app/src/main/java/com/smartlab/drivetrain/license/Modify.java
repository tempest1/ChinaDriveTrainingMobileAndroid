package com.smartlab.drivetrain.license;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.view.HandyTextView;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smartlab on 15/11/24.
 * 修改用户信息
 */
public class Modify extends BaseActivity implements View.OnClickListener ,ActionCallBackListener<Void>{
    private User info;
    private int method = 0;
    private AutoCompleteTextView edit_info;
    private LoadingDialog loading;
    private Gson gson;
    private Type uType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            method = bundle.getInt("method");
            info = (User) bundle.getSerializable("info");
        }
    }

    @Override
    protected void initView() {
        HandyTextView title = (HandyTextView) findViewById(R.id.title);
        HandyTextView text = (HandyTextView) findViewById(R.id.text);
        edit_info = (AutoCompleteTextView) findViewById(R.id.edit_info);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button save = (Button) findViewById(R.id.save);
        gson = new Gson();
        uType = new TypeToken<User>() {}.getType();
        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        if (method == 1){
            title.setText("姓名");
            text.setText("姓名");
            edit_info.requestFocus();
        }
        if (method == 2){
            title.setText("身份证");
            text.setText("身份证");
            edit_info.requestFocus();
        }
        loading = new LoadingDialog(this,R.drawable.loading);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.cancel:
                Modify.this.finish();
                break;
            case R.id.save:
                validIdNum();
                break;
            default:break;
        }
    }

    /**
     * 身份证号码是否有效
     */
    private void validIdNum() {
        //  姓名
        if (method == 1) {
            String name = edit_info.getText().toString().trim();
            info.setRealName(name);
            //
            loading.show();
            info.setCmd("baseInfo");
            String str = gson.toJson(info,uType);
            appAction.modifyUserInfo(str, Params.ModifyUser, this);
            return;
        }
        //  身份证号码修改
        if (method == 2) {
            String IdNum = edit_info.getText().toString().trim();
            Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            //通过Pattern获得Matcher
            Matcher idNumMatcher = idNumPattern.matcher(IdNum);
            //判断用户输入是否为身份证号
            if (idNumMatcher.matches()) {
                info.setCmd("baseInfo");
                info.setIdCard(IdNum);
                //
                loading.show();
                String str = gson.toJson(info,uType);
                appAction.modifyUserInfo(str, Params.ModifyUser,this);

            } else {
                showToast("您输入的身份证信息有误");
            }
        }
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(Void data) {
        MainApplication.setUserInfo(info);
        loading.dismiss();
        Modify.this.finish();
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        loading.dismiss();
        showToast(message);
    }
}
