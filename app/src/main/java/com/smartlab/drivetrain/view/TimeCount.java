package com.smartlab.drivetrain.view;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by zhoudi on 16/3/24.
 */
public class TimeCount extends CountDownTimer {

    private Button submit;

    public TimeCount(long millisInFuture, long countDownInterval,Button button) {
        super(millisInFuture, countDownInterval);
        submit=button;
    }
    @Override
    public void onFinish() {// 计时完毕
        submit.setText("获取短信验证码");
        submit.setClickable(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程
        submit.setClickable(false);//防止重复点击
        submit.setText(millisUntilFinished / 1000 + "s后可重新发送");
    }
}
