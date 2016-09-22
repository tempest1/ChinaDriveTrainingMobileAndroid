package com.smartlab.drivetrain.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.base.CommonAdapter;
import com.smartlab.drivetrain.base.ViewHolder;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择性别
 */
public class ChooseSex extends BaseActivity implements ActionCallBackListener<Void>, View.OnClickListener, AdapterView.OnItemClickListener{

    private User info = null;
    private Type uType;
    private LoadingDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sex);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                info = (User) bundle.getSerializable("info");
            }
        }
    }

    @Override
    protected void initView() {
        Button detail_back = (Button) findViewById(R.id.detail_back);
        detail_back.setOnClickListener(this);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText("性别");
        ListView sex_list = (ListView) findViewById(R.id.sex_list);
        List<String> sexs = new ArrayList<String>();
        sexs.add("男");
        sexs.add("女");
        CommonAdapter<String> adapter = new CommonAdapter<String>(this, sexs, R.layout.text_view) {
            @Override
            public void convert(ViewHolder helper, String item) {
                LogUtils.e(item);
                helper.setText(R.id.sex_text,item);
            }
        };
        sex_list.setAdapter(adapter);
        sex_list.setOnItemClickListener(this);
        uType = new TypeToken<User>() {}.getType();
        loading = new LoadingDialog(this,R.drawable.loading);
        if (info != null) {
            // true表示性别女，false 表示性别男
            if (info.getSex()) {
                sex_list.setSelection(0);
            } else {
                sex_list.setSelection(1);
            }
        } else {
            sex_list.setSelection(0);
        }
    }


    /**
     *
     * @param sex true 或者是 false
     */
    private void modify(boolean sex){
        if (info != null) {
            info.setCmd("baseInfo");
            info.setSex(sex);
            loading.show();
            String json = gson.toJson(info, uType);
            appAction.modifyUserInfo(json, Params.ModifyUser, this);
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
        if (loading.isShowing()) loading.dismiss();
        close(this);
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        if (loading.isShowing()) loading.dismiss();
        showToast(message);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                close(this);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.e("current select item" + position);
        switch (position){
            case 0:
                modify(false);
                break;
            case 1:
                modify(true);
                break;
        }
    }
}
