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
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.util.LogUtils;

/**
 * Created by smartlab on 15/9/29.
 * 注册界面
 */
public class Register extends BaseActivity implements View.OnClickListener,BaseActivity.MyHandler.Resolve,RegisterStep.OnStepChangeListener{
    private MyHandler<Register> handler = new MyHandler<Register>(this,this);
    String cmd = "register";
    private ViewFlipper viewFlipper;
    private Button next;
    private Button previous;
    private RegisterStep mCurrentStep;
    //  默认选择第一个
    private int mCurrentStepIndex = 1;
    private StepPhone stepPhone;
    private StepPassword stepPassword;

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
        initEvents();
        //  对话框
        initBackDialog();

    }

    private void initEvents() {
        //  监听事件
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        mCurrentStep.setOnStepChangeListener(this);
        detail_title.setText("注册账户");
        detail_back.setOnClickListener(this);
    }


//
    public void TestInstanceOf(){
    }
    /**
     * 当前步骤
     * @return 当前步骤
     */
    private RegisterStep initStep() {
        switch (mCurrentStepIndex){
            case 1:
                //  验证手机号码
                if (stepPhone == null){
                    stepPhone = new StepPhone(this,viewFlipper.getChildAt(0));
                }
                next.setText("下一步");
                previous.setText("返回");
                return stepPhone;
            case 2:
                //  设置密码
                if (stepPassword == null){
                    stepPassword = new StepPassword(this,viewFlipper.getChildAt(1));
                }
                previous.setText("上一步");
                next.setText("注册");
                return stepPassword;
            default:break;
        }
        return null;
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
    @Override
    protected void initData() {

    }

    //  拦截keyBack press
    @Override
    public void onBackPressed() {
        mBackDialog.show();
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
            } else {
                OnStepChanger();
            }
        }

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

    private void initBackDialog() {
        mBackDialog = BaseDialog.getDialog(Register.this, "提示",
                "确认要放弃注册么?", "确认", new DialogInterface.OnClickListener() {

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


    private void initBackDialogs() {
        mBackDialog = BaseDialog.getDialog(Register.this, "提示",
                "确认要放弃找回密码么?", "确认", new DialogInterface.OnClickListener() {

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
    /**
     * 消息回调
     *
     * @param msg Message对象
     */
    @Override
    public void resolveData(Message msg) {

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

    public void showCustomToast(String s) {
        super.showToast(s);
    }
    public AppAction getAppAction(){
        return appAction;
    }





}
