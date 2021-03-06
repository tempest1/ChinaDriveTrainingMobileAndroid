package com.smartlab.drivetrain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.base.CommonAdapter;
import com.smartlab.drivetrain.base.ViewHolder;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.AddConsult;
import com.smartlab.drivetrain.model.ComplainType;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.EmoticonsEditText;
import com.smartlab.drivetrain.view.ListViewForScrollView;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by smartlab on 16/1/5.
 *  咨询
 */
public class ConsultPage extends BaseFragment implements View.OnClickListener,ActionCallBackListener<Void>{

    private List<ComplainType> complainTypes;
    private CommonAdapter<ComplainType> commonAdapter;
    private HashMap<String, Boolean> states;
    private EmoticonsEditText edit;
    private LoadingDialog loading;
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
            view = inflater.inflate(R.layout.consult_page, container, false);
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
        states = new HashMap<>();
        TextView complain_title = (TextView) view.findViewById(R.id.complain_title);
        complain_title.setText(getString(R.string.consult_type));
        ListViewForScrollView list = (ListViewForScrollView) view.findViewById(R.id.list);
        edit = (EmoticonsEditText) view.findViewById(R.id.edit);
        view.findViewById(R.id.btn_send).setOnClickListener(this);
        complainTypes = new ArrayList<>();
        commonAdapter = new CommonAdapter<ComplainType>(getActivity(),complainTypes,R.layout.radio_item) {
            // 用于记录每个RadioButton的状态，并保证只可选一个
            @Override
            public void convert(final ViewHolder helper, ComplainType item) {
                helper.setText(R.id.name, item.getTypeCn());
                final RadioButton radio = helper.getView(R.id.radio);
                radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 重置，确保最多只有一项被选中
                        /**
                         * android:focusable="true"
                         android:focusableInTouchMode="true"
                         */
                        for (String key : states.keySet()) {
                            states.put(key, false);
                        }
                        states.put(String.valueOf(helper.getPosition()), radio.isChecked());
                        notifyDataSetChanged();
                    }
                });
                boolean res;
                if (states.get(String.valueOf(helper.getPosition())) == null
                        || !states.get(String.valueOf(helper.getPosition()))) {
                    res = false;
                    states.put(String.valueOf(helper.getPosition()), false);
                } else
                    res = true;

                radio.setChecked(res);
            }
        };
        list.setAdapter(commonAdapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        initData();
    }

    /**
     * 初始化基本数据
     */
    @Override
    protected void initData() {
//   获取投诉类型
        appAction.getConsultTypeList("getConsultTypeList", Params.ConsultTypeList, new ActionCallBackListener<List<ComplainType>>() {
            @Override
            public void onSuccess(List<ComplainType> data) {
                if (data != null) {
                    LogUtils.e(data.toString());
                    complainTypes.clear();
                    complainTypes.addAll(data);
                    commonAdapter.notifyDataSetChanged();
                    if (loading != null) {
                        loading.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                showToast(message);
                if (loading != null) {
                    loading.dismiss();
                }
            }
        });
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
    private void send() {
        AddConsult add = new AddConsult();

        //获取当前选中的位置
        for(String key:states.keySet()){
            if (states.get(key)){
                LogUtils.e(key);
                if (complainTypes.size() > 0){
                    ComplainType type = complainTypes.get(Integer.valueOf(key));
                    add.setType(type.getType());
                } else {
                    initData();
                    loading.show();
                    return;
                }
                break;
            }
        }
        //
        if (add.getType() == null){
            return;
        }
        //
        User user = MainApplication.getUserInfo();
        String content = edit.getText().toString().trim();
        add.setContent(content);
        add.setUserTimestamp(user.getTimeStamp());
        add.setCmd("addConsult");
        Type type = new TypeToken<AddConsult>(){}.getType();
        appAction.addConsult(gson.toJson(add, type), Params.addConsult, this);
        loading.show();
    }
    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(Void data) {
        showToast(getString(R.string.commit_success));
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
}
