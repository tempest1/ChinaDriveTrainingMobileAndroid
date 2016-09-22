package com.smartlab.drivetrain.register;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.FlippingLoading;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smartlab on 15/11/19.
 */
public abstract class RegisterStep {
    protected Register activity;
    protected Context context;
    protected OnStepChangeListener onStepChange;
    private View content;
    protected FlippingLoading loading;
    public RegisterStep(Register activity,View content){
        this.activity = activity;
        this.context = (Context)activity;
        this.content = content;
        initViews();
        initEvents();
        loadingView();
    }

    public FlippingLoading loadingView(){
        if (loading == null) {
            loading = new FlippingLoading(context, null);
        }
        return loading;
    }
    public abstract void initViews();

    public abstract void initEvents();

    public abstract boolean validate();

    public abstract boolean isChange();

    /**
     * 返回上一步
     */
    public void doPrevious() {}

    /**
     * 下一步
     */
    public void doNext() {}

    public View findViewById(int id){
        return content.findViewById(id);
    }
    public interface OnStepChangeListener{
        void OnStepChanger();
    }

    /**
     * 正则表达式验证手机号码
     * @param text 手机号码
     * @return 返回true 后者false
     */
    protected boolean matchPhone(String text) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(text);
        LogUtils.e(text);
        return m.matches();
    }

    /**
     *
     * @param editText
     * @return
     */
    protected boolean isNull(EditText editText) {
        String text = editText.getText().toString().trim();
        if (text != null && text.length() > 0) {
            return false;
        }
        return true;
    }
    public void setOnStepChangeListener(OnStepChangeListener onStepChange){
        this.onStepChange = onStepChange;
    }
    protected void showCustomToast(String s){
        activity.showCustomToast(s);
    }
}
