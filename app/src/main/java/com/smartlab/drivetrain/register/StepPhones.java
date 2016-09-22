package com.smartlab.drivetrain.register;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.pullable.TimeButton;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.HandyTextView;
import com.smartlab.drivetrain.view.TimeCount;

/**
 * Created by zhoudi on 16/3/22.
 */
public class StepPhones extends ForgetStep implements TextWatcher,ActionCallBackListener<Void>,View.OnClickListener {


    /**
     * 中国国家编码
     */
    private String mAreaCode = "+86";
    /**
     * 输入的电话号码
     */
    private String mPhone;
    private boolean mIsChange = true;
    //  电话号码输入框
    private EditText reg_phone_et_phone;
    //  放大显示的字体
    private HandyTextView reg_phone;
    private EditText edit_msg_code;
    private Button req_msg_code;
    private static String response_code = "";
    private TimeCount time;




    private ActionCallBackListener<String> listener = new ActionCallBackListener<String>() {
        @Override
        public void onSuccess(String data) {
            Log.e("sys","onSuccess");
            response_code = data;
            Log.e("data=", data);
            loading.dismiss();
            data=data.trim();

            if(data=="false"||data.equals("false"))
            {
                Log.e("sys","success");
                Log.e("sys",data);
                showCustomToast("服务器繁忙.请稍后再试");
            } else {
                Log.e("sys","else");
                Log.e("sys",data);
                time.start();
            }
            loading.dismiss();
        }

        @Override
        public void onFailure(String errorEvent, String message) {
            Log.e("sys","onFailure");
            showCustomToast(message);
            loading.dismiss();
        }
    };

    public StepPhones(Forget activity, View content) {
        super(activity, content);
    }


    @Override
    public void initViews() {
        reg_phone_et_phone = (EditText) findViewById(R.id.reg_phone_et_phone);
        reg_phone = (HandyTextView) findViewById(R.id.reg_phone);
        edit_msg_code = (EditText) findViewById(R.id.edit_msg_code);
        req_msg_code = (Button) findViewById(R.id.req_msg_code);
        time=new TimeCount(90000,1000,req_msg_code);
    }

    /**
     * 这里需要验证手机号码是否已经被注册过了
     */
    @Override
    public void doNext() {

        //如果有电话号码则为Success,没有则是onFailure
     //   activity.getAppAction().validPhones("validPhone", mPhone, Params.validPhone, this);
        mIsChange = false;
        onStepChange.OnStepChanger();
        activity.setmPhone(mPhone);


    }

    @Override
    public void initEvents() {
        reg_phone_et_phone.addTextChangedListener(this);
        req_msg_code.setOnClickListener(this);
    }

    /**
     * 验证手机号格式是否正确
     * @return
     */
    @Override
    public boolean validate() {
        String code = edit_msg_code.getText().toString().trim();
        Log.e("sys",edit_msg_code.getText().toString().trim());//199374
        if (TextUtils.isEmpty(code)){
            activity.showCustomToast("请输入验证码？");
            return false;
        }
        String msg_code = Util.makeMD5(code);

        Log.e("msg_code=",msg_code);
        Log.e("response_code=",response_code);

        if (TextUtils.isEmpty(response_code)){
            LogUtils.e("系统未获取到验证码");
            return false;
        } else {
            if (response_code.equals(msg_code)||response_code==msg_code) {
                LogUtils.i(msg_code);
                return true;
            } else {
                activity.showCustomToast("验证码输入有误");
                return false;
            }
        }
    }

    public boolean validPhone(){
        mPhone = null;
        if (isNull(reg_phone_et_phone)) {
            activity.showCustomToast("请填写手机号码");
            reg_phone_et_phone.requestFocus();
            return false;
        }
        String phone = reg_phone_et_phone.getText().toString().trim();
        if (matchPhone(phone)) {
            mPhone = phone;
            LogUtils.e("电话号码" + phone);
            return true;
        }
        activity.showCustomToast("手机号码格式不正确");
        reg_phone_et_phone.requestFocus();
        return false;
    }
    @Override
    public boolean isChange() {
        return mIsChange;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public String getmPhone() {
        return mPhone;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int i1, int i2) {
        if (s.toString().length() > 0){
            reg_phone.setVisibility(View.VISIBLE);
            //  这里对数字进行拆分显示，
            char[] chars = s.toString().toCharArray();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                if (i % 4 == 2) {
                    buffer.append(chars[i] + "  ");
                } else {
                    buffer.append(chars[i]);
                }
            }
            reg_phone.setText(buffer.toString());
        } else {
            reg_phone.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.req_msg_code:

                //如果有电话号码则为Success,没有则是onFailure

                if (validPhone()){
                    loading.setmText("正在获取验证码...");
                    loading.show();
                    activity.getAppAction().validPhones("validPhone", mPhone, Params.validPhone, this);

                }
                break;
            default:break;
        }
    }


    /**
     * 处理成功
     *
     * @param data 返回数据 Void Type = null
     */
    @Override
    public void onSuccess(Void data) {
        Log.e("sys", "validPhones onSuccess");
        if (validPhone()){
            loading.setmText("正在获取验证码...");
           // loading.show();
            //验证码请求
            activity.getAppAction().registerCode("registerCode", reg_phone_et_phone.getText().toString().trim(), Params.MsgCode_Register, listener);
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
        Log.e("sys", "validPhones onSuccess");
        loading.dismiss();
        if(message=="获取失败")
        {
            showCustomToast("可能是您没有注册");
        }
        else {
            showCustomToast(message);
        }
    }


}
