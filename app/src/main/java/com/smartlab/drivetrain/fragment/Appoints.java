package com.smartlab.drivetrain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.base.CommonAdapter;
import com.smartlab.drivetrain.base.ViewHolder;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.ComplainType;
import com.smartlab.drivetrain.model.MyAppointment;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 16/1/6.
 *
 */
public class Appoints extends BaseFragment implements PullToRefreshLayout.OnRefreshListener{

    private PullToRefreshLayout layout;
    private View view;
    private List<MyAppointment> appointments;
    private CommonAdapter<MyAppointment> adapter;
    private ForegroundColorSpan f;
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
            view = inflater.inflate(R.layout.pull_layout, container, false);
        }
        initView();
        return view;
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView() {
        layout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        PullableListView list = (PullableListView) view.findViewById(R.id.pull_list);
        layout.setOnRefreshListener(this);
        appointments = new ArrayList<>();
        f = new ForegroundColorSpan(getResources().getColor(R.color.orange));
        adapter = new CommonAdapter<MyAppointment>(getActivity(), appointments,R.layout.order_layot) {
            @Override
            public void convert(ViewHolder helper, final MyAppointment item) {
                helper.setTextByResource(R.id.orderId, R.string.complainId, item.getId())
                        .setTextByResource(R.id.state_tv,R.string.coachName,item.getCoachName());
                TextView appoint_time = helper.getView(R.id.schoolId);
                String strs = item.getCreatetime();
                if (!TextUtils.isEmpty(strs)){
                    if (strs.length() > 10){
                        strs = strs.substring(0,10);
                        helper.setTextByResource(R.id.orderTime,R.string.order_time,strs);
                    }
                }
                String time = item.getTime();
                if (!TextUtils.isEmpty(time)) {
                    appoint_time.setTextSize(16);
                    SpannableStringBuilder str = new SpannableStringBuilder(getString(R.string.appoint_time,
                            time));
                    str.setSpan(f,0,str.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    appoint_time.setText(str);
                } else {
                    appoint_time.setVisibility(View.GONE);
                }
                helper.setVisibility(R.id.description, View.GONE)
                        .setVisibility(R.id.money,View.GONE)
                        .setVisibility(R.id.cancel,View.GONE)
                        .setVisibility(R.id.pay,View.GONE)
                        .setVisibility(R.id.answer,View.GONE);
            }
        };
        list.setAdapter(adapter);
        Util.runDelay(layout);
    }


    /**
     * 初始化基本数据
     */
    @Override
    protected void initData() {

        appAction.userAppointmentList("userAppointmentList", Params.AppointmentList, new ActionCallBackListener<List<MyAppointment>>() {
            @Override
            public void onSuccess(List<MyAppointment> data) {
                if (data != null) {
                    appointments.clear();
                    appointments.addAll(data);
                    LogUtils.e(data.toString());
                    adapter.notifyDataSetChanged();
                    layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                layout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });
    }

    private void getComplainType(){
        //   咨询类型
        appAction.getConsultTypeList("getConsultTypeList", Params.ConsultTypeList, new ActionCallBackListener<List<ComplainType>>() {
            @Override
            public void onSuccess(List<ComplainType> data) {
                if (data != null) {
                    LogUtils.e(data.toString());
                    initData();
//                    layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                showToast(message);
                layout.refreshFinish(PullToRefreshLayout.FAIL);
            }
        });
    }
    /**
     * 刷新操作
     *
     * @param pullToRefreshLayout PullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//        getComplainType();
        initData();
    }

    /**
     * 加载操作
     *
     * @param pullToRefreshLayout PullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        layout.loadmoreFinish(PullToRefreshLayout.NO_MORE);
    }
}
