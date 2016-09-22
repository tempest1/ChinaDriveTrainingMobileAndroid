package com.smartlab.drivetrain.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.StatusSchool;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 16/1/6.
 *
 */
public class AppointPage extends BaseFragment implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    private CharSequence times [] = new CharSequence[]{"6点","7点","8点","9点","10点","11点","12点","13点","14点","15点","16点","17点"};
    private RadioGroup object_radio;
    private TextView name;
    private TextView phone;
    private StatusSchool data;
    private Spinner date;
    private Spinner time;
    private User user;
    private String timestamp;
    private List<CharSequence> weekTime;
    private LoadingDialog loadingDialog;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null){
                parent.removeView(view);
            }
            return view;
        } else {
            view = inflater.inflate(R.layout.appoint_coach_layout, container, false);
        }
        initView();
        return view;
    }

    @Override
    protected void initData() {
        user = MainApplication.getUserInfo();
        appAction.getSchoolByUserId("getSchoolByUserId", user.getTimeStamp(), Params.SchoolByUserId, new ActionCallBackListener<StatusSchool>() {
            @Override
            public void onSuccess(StatusSchool data) {
                LogUtils.e(data.toString());
                setCoach(data);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                showToast(message);
            }
        });
    }

    private void setCoach(StatusSchool data) {
        this.data = data;
        setData(object_radio.getCheckedRadioButtonId());
    }

    // 设置内容
    private void setData(int checkedRadioButtonId) {
        switch (checkedRadioButtonId){
            case R.id.object_one:
                if (!TextUtils.isEmpty(data.getCoachName1())){
                    name.setText(getString(R.string.coachName,data.getCoachName1()));
                    timestamp = data.getCoachTimestamp1();
                } else {
                    name.setText(getString(R.string.no_info));
                    timestamp = null;
                }
                if (!TextUtils.isEmpty(data.getCoachNumber1())) {
                    phone.setText(getString(R.string.calls, data.getCoachNumber1()));
                } else {
                    phone.setText(getString(R.string.no_info));
                }
                break;
            case R.id.object_two:
                if (!TextUtils.isEmpty(data.getCoachName2())){
                    name.setText(getString(R.string.coachName,data.getCoachName2()));
                    timestamp = data.getCoachTimestamp2();
                } else {
                    name.setText(getString(R.string.no_info));
                    timestamp = null;
                }
                if (!TextUtils.isEmpty(data.getCoachNumber2())) {
                    phone.setText(getString(R.string.calls, data.getCoachNumber2()));
                } else {
                    phone.setText(getString(R.string.no_info));
                }
                break;
            default:break;
        }
    }

    @Override
    protected void initView() {
        date = (Spinner) view.findViewById(R.id.date);
        time = (Spinner) view.findViewById(R.id.dateTime);
        view.findViewById(R.id.btn_send).setOnClickListener(this);
        object_radio = (RadioGroup) view.findViewById(R.id.object_radio);
        object_radio.setOnCheckedChangeListener(this);
        name = (TextView) view.findViewById(R.id.name);
        phone = (TextView) view.findViewById(R.id.phone);
        view.findViewById(R.id.phone_layout).setOnClickListener(this);
        loadingDialog = new LoadingDialog(getActivity(),R.drawable.loading);
        // 获取当前系统的点间及星期
        weekTime = new ArrayList<>();
        for (int i = 1;i < 8;i++) {
            CharSequence t = DateTools.getDate(System.currentTimeMillis(), i);
            weekTime.add(t);
        }
        ArrayAdapter dateAdpater = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,weekTime);
        dateAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date.setAdapter(dateAdpater);

        ArrayAdapter timeAdpater  = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,times);
        timeAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(timeAdpater);
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                sendRequest();
                break;
            case R.id.phone_layout:
                call();
                break;
            default:break;
        }
    }

    private void call() {
        if (data == null){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        switch (object_radio.getCheckedRadioButtonId()){
            case R.id.object_one:
                if (!TextUtils.isEmpty(data.getCoachNumber1())){
                    intent.setData(Uri.parse("tel:" + data.getCoachNumber1()));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
                break;
            case R.id.object_two:
                if (!TextUtils.isEmpty(data.getCoachNumber2())){
                    intent.setData(Uri.parse("tel:" + data.getCoachNumber2()));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
                break;
        }


    }

//    public void callPhoneNumber(String phoneNumber) {
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:" + phoneNumber));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }
    private void sendRequest() {
        if (user != null) {
            if (!TextUtils.isEmpty(timestamp)) {
                CharSequence d = weekTime.get(date.getSelectedItemPosition());
                CharSequence t = times[time.getSelectedItemPosition()];
                String dt = d + "" + t;
                loadingDialog.show();
                appAction.appoinmentCoach("appoinmentCoach", user.getTimeStamp(),timestamp,dt,Params.appoinmentCoach, new ActionCallBackListener<Void>() {
                    /**
                     * 处理成功
                     *
                     * @param data 返回数据
                     */
                    @Override
                    public void onSuccess(Void data) {
                        showToast(getString(R.string.commit_success));
                        loadingDialog.dismiss();
                        close(getActivity());
                    }

                    /**
                     * 请求失败
                     *
                     * @param errorEvent 错误码
                     * @param message    错误详情
                     */
                    @Override
                    public void onFailure(String errorEvent, String message) {
                        showToast(getString(R.string.commit_bad));
                        loadingDialog.dismiss();
                    }
                });
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        setData(checkedId);
    }
}
