package com.smartlab.drivetrain.appointment;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.TextWatcherImp;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.LoadingDialog;
import com.smartlab.drivetrain.view.MyAutocompleteTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/11/7.
 * 预约训练
 */
public class AppointmentActivity extends BaseActivity implements View.OnClickListener,BaseActivity.MyHandler.Resolve,ActionCallBackListener<Void>{
    /**
     * 班次
     */
    private Spinner banci;

    /**
     * 车型
     */
    private Spinner car_type;

    /**
     *
     *  训练时间
     */
    private Spinner train_time;
    /**
     * 点击预约
     */
    private Button appointment;
    /**
     *
     * 获取验证码
     */
    private Button req_code;
    /**
     * 输入验证码
     */
    private MyAutocompleteTextView edit_code;
    /**
     * 科目二和科目三的cmd
     */
    private String [] subject_cmd = {"mesIdCode","mesIdCodeT"};
    private String [] subject_order_url = {Params.SubjectTwoOrder,Params.SubjectThreeOrder};
    private String [] code_URl = {Params.MsgCode_SubTwo,Params.MsgCode_SubThree};
    String [] cmd = {"appointmentTwo","appointmentThree"};
    private String response_code;
    private CharSequence [] subject = {"科目二","科目三"};
    private CharSequence [] banci_array = {"加强适应训练(单训，早上)","加强适应训练(单训，下午)","加强适应训练(单训，晚上)","加强适应训练(连训，下午和早上)","加强适应训练(连训，晚上和早上)"};
    /**
     * "2001","2002","2003","2004","2005"
     */
    private String [] banci_value = {"2001","2002","2003","2004","2005"};
    private String [] kmIndex = {"2","3"};
    private MyHandler handler = new MyHandler<>(this,this);
    private int mReSendTime = 60;// 其实默认六十秒
    private LoadingDialog loading;
    private Spinner kemu;
    private Spinner coach;
    private ActionCallBackListener<String> listener = new ActionCallBackListener<String>() {
        @Override
        public void onSuccess(String data) {
            response_code = data;
            showToast("短信发送成功...");
            handler.sendEmptyMessage(0);    // 短信发送成功后,等待一分钟验证码发送到手机
            loading.dismiss();
        }

        @Override
        public void onFailure(String errorEvent, String message) {
            showToast(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);
        initView();
    }

    @Override
    protected void initData() {}

    @Override
    protected void initView() {
        Button detail_back = (Button) findViewById(R.id.detail_back);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setVisibility(View.GONE);
        detail_back.setOnClickListener(this);
        banci = (Spinner) findViewById(R.id.banci);
        car_type = (Spinner) findViewById(R.id.car_type);
        // 教练
        coach = (Spinner) findViewById(R.id.coach);
        train_time = (Spinner) findViewById(R.id.train_time);
        appointment = (Button) findViewById(R.id.appointment);
        req_code = (Button) findViewById(R.id.req_code);
        edit_code = (MyAutocompleteTextView) findViewById(R.id.edit_code);
        edit_code.addTextChangedListener(text);
        kemu = (Spinner) findViewById(R.id.kemu);
        appointment.setEnabled(false);

        ArrayAdapter<CharSequence> banciAdapter;         //  训练班次数据适配器
        ArrayAdapter<CharSequence> car_typeAdapter;     //  车型数据适配器
        ArrayAdapter<CharSequence> coachAdapter;        //  培训教练
        ArrayAdapter<CharSequence> time_Adapter;        //  一周时间的下拉列表
        ArrayAdapter<CharSequence> subject_adapter;     //  科目

        // 选择科目
        subject_adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,subject);
        subject_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kemu.setAdapter(subject_adapter);
        kemu.setSelection(0);
        loading = new LoadingDialog(this,R.drawable.loading);

        //车型
        car_typeAdapter = ArrayAdapter.createFromResource(this,R.array.car_type,android.R.layout.simple_spinner_item);
        car_typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        car_type.setAdapter(car_typeAdapter);
        car_type.setSelection(5);// 默认选择C1

        //教练
        coachAdapter = ArrayAdapter.createFromResource(this,R.array.coach,android.R.layout.simple_spinner_item);
        coachAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coach.setAdapter(coachAdapter);

        //获取当前系统的时间及星期
        List<CharSequence> weekTime = new ArrayList<>();
        for (int i = 1;i < 8;i++) {
            CharSequence time = DateTools.getDateStr(System.currentTimeMillis(),i);
            weekTime.add(time);
        }
        time_Adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,weekTime);
        time_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        train_time.setAdapter(time_Adapter);

        //  班次
        banciAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,banci_array);
        banciAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        banci.setAdapter(banciAdapter);
        //获取验证码
        req_code.setOnClickListener(this);
        appointment.setOnClickListener(this);
    }

    TextWatcher text = new TextWatcherImp(){
        @Override
        public void afterTextChanged(Editable editable) {
            updateClickable();
        }
    };

    private void updateClickable() {
        appointment.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.req_code:
                requestCode();
                break;
            case R.id.appointment:
                sendRequest();
                break;
            case R.id.detail_back:
                close();
                break;
            default:break;
        }
    }

    private void close() {
        this.finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }

    // 立即预定
    private void sendRequest() {
        // 若验证码正确则继续往下执行
        if (response_code == null){
            return;
        } else {
            // MD5编码
            String md5 = Util.makeMD5(edit_code.getText().toString().trim());
            if (!response_code.equals(md5)){
                LogUtils.i(md5);
                LogUtils.i(response_code);
                showToast("验证码有误，请重新输入！");
                return;
            }
        }
        User info = MainApplication.getUserInfo();
        if (info == null){
            showToast("无法获取您的个人信息！");
            return;
        }
        if (!TextUtils.isEmpty(info.getRealName()) && !TextUtils.isEmpty(info.getIdCard())) {
            if (info.getRealName().equals("null") && info.getIdCard().equals("null")){
                LogUtils.e("true");
            }
            // 车型
            String type = car_type.getSelectedItem().toString();
            String carType = type.substring(type.length() - 2, type.length());

            // 训练时间
            type = train_time.getSelectedItem().toString();
            String traintime = type.substring(0,type.length()-3).replace("/-/g","").trim();
            // 教练
            type = coach.getSelectedItem().toString();
            String coach = type.substring(0,5);

            // 班次
            type = banci_value[banci.getSelectedItemPosition()];

            //  科目
            int index = kemu.getSelectedItemPosition();
            appAction.sendAppointOrder(cmd[index],kmIndex[index],info.getRealName(),info.getIdCard(),carType,type,coach,traintime,subject_order_url[index],this);
            loading.show();
        } else {
            showToast("请先完善里面的您的个人信息");
        }

    }

    //  获取验证码
    private void requestCode() {
        User info = MainApplication.getUserInfo();
        if (info != null) {
            String phone = info.getPhone();
            int i = kemu.getSelectedItemPosition();
            LogUtils.i("科目" + subject_cmd[i]);
            if (!Util.isEmptyString(phone)) {
                loading.show();
                appAction.requestCode(subject_cmd[i], phone, code_URl[i], listener);
            }
        } else {
            showToast("请先登录，再操作!");
        }
    }

    /**
     * 消息回调
     *
     * @param msg Message对象
     */
    @Override
    public void resolveData(Message msg) {
        switch (msg.what){
            case 0:
                //  一分钟倒计时
                if (mReSendTime > 1) {
                    mReSendTime--;
                    req_code.setEnabled(false);
                    req_code.setText(this.getResources().getString(R.string.resend,mReSendTime));
                    handler.sendEmptyMessageDelayed(0, 1000);
                } else {
                    mReSendTime = 60;
                    req_code.setEnabled(true);
                    req_code.setText("重    发");
                }
                break;
            case 1:
                //  延时退出
                close(AppointmentActivity.this);
                break;
            default:break;
        }
    }
    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(Void data) {
        loading.dismiss();
        showToast("提交成功，我们将及时处理，请注意查收短信。。");
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        showToast("预约失败");
    }
}
