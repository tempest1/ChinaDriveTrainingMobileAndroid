package com.smartlab.drivetrain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Evaluate;
import com.smartlab.drivetrain.model.EvaluateModel;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.EmoticonsEditText;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.lang.reflect.Type;

/**
 * Created by smartlab on 15/12/29.
 * EvaluatePage
 */
public class EvaluatePage extends BaseFragment implements View.OnClickListener,ActionCallBackListener<Void>,RadioGroup.OnCheckedChangeListener{
    private RadioGroup choose_radio;
    private EmoticonsEditText edit;
    private LoadingDialog loading;
    private View view;
    private CheckBox object_one;
    private CheckBox object_two;
    private EvaluateModel evaluate;
    private RadioGroup km_radio;
    private long coachTimestamp = 0;
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
            view = inflater.inflate(R.layout.evaluate_fragment, container, false);
        }
        initView();
        return view;
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView() {
        loading = new LoadingDialog(getActivity(),R.drawable.loading);
        choose_radio = (RadioGroup) view.findViewById(R.id.choose_radio);
        RadioButton grade_one = (RadioButton) view.findViewById(R.id.grade_one);
        grade_one.setText("一般");
        RadioButton grade_two = (RadioButton) view.findViewById(R.id.grade_two);
        grade_two.setText("好");
        RadioButton grade_three = (RadioButton) view.findViewById(R.id.grade_three);
        grade_three.setText("很好");
        RadioButton grade_four = (RadioButton) view.findViewById(R.id.grade_four);
        grade_four.setText("非常好");
        grade_four.setVisibility(View.VISIBLE);
        TextView complain_title = (TextView) view.findViewById(R.id.complain_title);
        complain_title.setText(getResources().getString(R.string.evaluate_text));
        edit = (EmoticonsEditText) view.findViewById(R.id.edit);
        view.findViewById(R.id.btn_send).setOnClickListener(this);
        object_one = (CheckBox) view.findViewById(R.id.object_one);
        object_two = (CheckBox) view.findViewById(R.id.object_two);
        km_radio = (RadioGroup) view.findViewById(R.id.km_radio);
        km_radio.setOnCheckedChangeListener(this);
        RadioButton km_two = (RadioButton) view.findViewById(R.id.km_two);
        RadioButton km_three = (RadioButton) view.findViewById(R.id.km_three);
        km_two.setText(getString(R.string.km2));
        km_three.setText(getString(R.string.km3));
        initData();
    }

    /**
     * 初始化基本数据
     */
    @Override
    protected void initData() {
        User user = MainApplication.getUserInfo();
    //   获取类型
        appAction.getEvaluateType("getEvaluateType",user.getTimeStamp(), Params.EvaluateType, new ActionCallBackListener<EvaluateModel>() {
            @Override
            public void onSuccess(EvaluateModel data) {
                LogUtils.e(data.toString());
                setData(data);
                loading.dismiss();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                showToast(message);
                loading.dismiss();
            }
        });
    }

    //  填充数据
    private void setData(EvaluateModel data) {
        evaluate = data;

        if (!TextUtils.isEmpty(data.getDrivingName())) {
            object_one.setText(data.getDrivingName());
            object_one.setVisibility(View.VISIBLE);
        }

        chooseCoachByKm(km_radio.getCheckedRadioButtonId());
    }

    private void chooseCoachByKm(int checkedRadioButtonId) {
        switch (checkedRadioButtonId){
            case R.id.km_two:
                if (!TextUtils.isEmpty(evaluate.getCoachName1())) {
                    object_two.setText(evaluate.getCoachName1());
                    object_two.setVisibility(View.VISIBLE);
                    coachTimestamp = evaluate.getCoachTimestamp1();
                }
                break;
            case R.id.km_three:
                if (!TextUtils.isEmpty(evaluate.getCoachName2())) {
                    object_two.setText(evaluate.getCoachName2());
                    object_two.setVisibility(View.VISIBLE);
                    coachTimestamp = evaluate.getCoachTimestamp2();
                } else {
                    object_two.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                send();
                break;
            default:break;
        }
    }
    //  包装发送数据
    private void send() {
        Evaluate obj = new Evaluate();
        if (evaluate != null){
            if (object_one.isShown()){
                if (object_one.isChecked()){
                    obj.setCoachTimestamp(String.valueOf(coachTimestamp));
                }
            }

            if (object_two.isShown()) {
                if (object_two.isChecked()) {
                    if (coachTimestamp != 0)
                    obj.setDrivingTimestamp(String.valueOf(evaluate.getDrivingTimestamp()));
                }
            }
            if (object_one.getVisibility() == View.GONE && object_two.getVisibility() == View.GONE){
                showToast(getString(R.string.no_school));
                return;
            }
            if (!object_one.isChecked() && !object_two.isChecked()){
                showToast("请选择评论对象");
                return;
            }

            obj.setCmd("addEvaluate");
        } else {
            initData();
            loading.show();
            return;
        }
        obj.setContent(edit.getText().toString().trim());
        switch (choose_radio.getCheckedRadioButtonId()){
            case R.id.grade_one:
                obj.setGrade("1");
                break;
            case R.id.grade_two:
                obj.setGrade("2");
                break;
            case R.id.grade_three:
                obj.setGrade("3");
                break;
            case R.id.grade_four:
                obj.setGrade("4");
                break;
            default:break;
        }

        switch (km_radio.getCheckedRadioButtonId()){
            case R.id.km_two:
                obj.setKm("2");
                break;
            case R.id.km_three:
                obj.setKm("3");
                break;
            default:break;
        }
        User user = MainApplication.getUserInfo();
        obj.setUserTimestamp(user.getTimeStamp());
        Type type = new TypeToken<Evaluate>(){}.getType();
        String json = gson.toJson(obj,type);
        loading.show();
        appAction.addEvaluate(json,Params.addEvaluate,this);
    }
    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(Void data) {
        showToast("提交成功");
        loading.dismiss();
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
        showToast(message);
        loading.dismiss();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        chooseCoachByKm(checkedId);
    }
}
