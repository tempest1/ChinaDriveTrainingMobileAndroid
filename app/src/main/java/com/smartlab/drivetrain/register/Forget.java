package com.smartlab.drivetrain.register;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.base.BaseDialog;
import com.smartlab.drivetrain.interfaces.AppAction;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.util.LogUtils;

/**
 * Created by zhoudi on 16/3/22.
 */
public class Forget extends BaseActivity implements View.OnClickListener,BaseActivity.MyHandler.Resolve,ForgetStep.OnStepChangeListener {


    private ViewFlipper viewFlipper;
    private Button next;
    private Button previous;
    private ForgetStep mCurrentStep;
    //  默认选择第一个
    private int mCurrentStepIndex = 1;
    private StepPhones stepPhone;
    private StepPasswords stepPassword;

    private BaseDialog mBackDialog;
    private Button detail_back;
    private TextView detail_title;
    private String mPhone;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register_layout);

        //  初始化视图
        initView();
        //  初始化注册
        mCurrentStep = initStep();
        initEvent();

        initBackDialogs();

    }

    @Override
    protected void initView() {
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        viewFlipper.setDisplayedChild(0);
        detail_back = (Button) findViewById(R.id.detail_back);
        detail_title = (TextView) findViewById(R.id.detail_title);

    }

    private void initEvent() {
        //  监听事件
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        mCurrentStep.setOnStepChangeListener(this);
        detail_title.setText("重置密码");
        detail_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    /**
     * 当前步骤
     * @return 当前步骤
     */
    private  ForgetStep initStep() {
        switch (mCurrentStepIndex){
            case 1:
                //  验证手机号码
                if (stepPhone == null){
                    stepPhone = new StepPhones(this,viewFlipper.getChildAt(0));
                }
                next.setText("下一步");
                previous.setText("返回");
                return stepPhone;
            case 2:
                //  设置密码
                if (stepPassword == null){
                    stepPassword = new StepPasswords(this,viewFlipper.getChildAt(1));
                }
                previous.setText("上一步");
                next.setText("重置密码");
                return stepPassword;
            default:break;
        }
        return null;
    }

    //  拦截keyBack press
    @Override
    public void onBackPressed() {
        mBackDialog.show();
    }
    private void initBackDialogs() {
        mBackDialog = BaseDialog.getDialog(Forget.this, "提示",
                "确认要放弃重置密码么?", "确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.previous:
                if (mCurrentStepIndex <= 1) {
                    mBackDialog.show();
                } else {
                    doPrevious();
                }
                break;
            case R.id.next:
                if (mCurrentStepIndex < 2) {
                    doNext();   //  进入下一步
                } else {
                    if (mCurrentStep.validate()) {
                        mCurrentStep.doNext();  //  提交操作
                    }
                }
                break;
            case R.id.detail_back:
                close(this);
                break;
            default:break;
        }
    }


    /**
     * 下一步
     */
    private void doNext() {
        if (mCurrentStep.validate()) {
            if (mCurrentStep.isChange()) {
                mCurrentStep.doNext();
                Log.e("sys","success");
            } else {
                Log.e("sys","else");
                OnStepChanger();
            }
        }
     //  mCurrentStep.doNext();
    }

    /**
     * 上一步
     */
    private void doPrevious() {
        mCurrentStepIndex--;
        mCurrentStep = initStep();
        if (mCurrentStep != null) {
            mCurrentStep.setOnStepChangeListener(this);
        } else {
            LogUtils.e("mCurrentStep is null");
            return;
        }
        viewFlipper.setInAnimation(this, R.anim.push_right_in);
        viewFlipper.setOutAnimation(this, R.anim.push_right_out);
        viewFlipper.showPrevious();

    }



    @Override
    public void resolveData(Message msg) {

    }


    public void showCustomToast(String s) {
        super.showToast(s);
    }
    public AppAction getAppAction(){
        return appAction;
    }
    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }


    @Override
    public void OnStepChanger() {
        mCurrentStepIndex++;
        mCurrentStep = initStep();
        if (mCurrentStep != null) {
            mCurrentStep.setOnStepChangeListener(this);
        } else {
            LogUtils.e("mCurrentStep is null");
            return;
        }
        viewFlipper.setInAnimation(this, R.anim.push_left_in);
        viewFlipper.setOutAnimation(this, R.anim.push_left_out);
        viewFlipper.showNext();
    }

}
