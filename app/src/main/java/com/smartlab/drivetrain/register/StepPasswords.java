package com.smartlab.drivetrain.register;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;

/**
 * Created by zhoudi on 16/3/22.
 */
public class StepPasswords extends ForgetStep implements View.OnClickListener,TextWatcher,ActionCallBackListener<Void> {

    //  设置密码
    private EditText passWord;
    //  二次确认密码
    private EditText passWord_r;
    private boolean mIsChange = true;
    public StepPasswords(Forget activity, View content) {
        super(activity, content);
    }

    @Override
    public void initViews() {
        passWord = (EditText) findViewById(R.id.passWord);
        passWord_r = (EditText) findViewById(R.id.passWord_r);

    }

    @Override
    public void initEvents() {
        passWord.addTextChangedListener(this);
        passWord_r.addTextChangedListener(this);
    }


    @Override
    public void doNext() {
        //  要添加dialog
        loading.setmText("正在提交,请稍后...");
        loading.show();

        activity.getAppAction().registerNew("register", activity.getmPhone(), passWord.getText().toString().trim(), Params.ResetPass, this);

      //  activity.getAppAction().registerNew("register", "15027018250", passWord.getText().toString().trim(), Params.ResetPass, this);


    }

    @Override
    public boolean validate() {
        String pwd = null;
        String rePwd = null;
        if (isNull(passWord)) {
            activity.showToast("请输入密码");
            passWord.requestFocus();
            return false;
        } else {
            pwd = passWord.getText().toString().trim();
            if (MainApplication.isVar(pwd)) {
                activity.showToast("密码中输入了非法字符");
                passWord.requestFocus();
                return false;
            }
                if (pwd.length() < 6) {
                    activity.showToast("密码不能小于6位");
                    passWord.requestFocus();
                    return false;
                }
            }
            if (isNull(passWord_r)) {
                activity.showToast("请重复输入一次密码");
                passWord_r.requestFocus();
                return false;
            } else {
                rePwd = passWord_r.getText().toString().trim();
                if (!pwd.equals(rePwd)) {
                    activity.showToast("两次输入的密码不一致");
                    passWord_r.requestFocus();
                    return false;
                }
            }

        return true;
    }

    @Override
    public boolean isChange() {
        return mIsChange;
    }

    @Override
    public void onClick(View view) {
    }




    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(Void data) {
        showCustomToast("设置成功");
        activity.close(activity);

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
        if (TextUtils.isEmpty(message)){
            showCustomToast(" 设置失败");
        } else {
            showCustomToast(message);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        loading.dismiss();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mIsChange = true;
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
