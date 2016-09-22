package com.smartlab.drivetrain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.smartlab.drivetrain.model.ConsultBean;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 16/1/5.
 * 资讯记录
 */
public class Consults extends BaseFragment implements PullToRefreshLayout.OnRefreshListener{

    private PullToRefreshLayout layout;
    private View view;
    private List<ConsultBean> consult;
    private CommonAdapter<ConsultBean> adapter;
    private List<ComplainType> consultType;
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
        consult = new ArrayList<>();
        consultType = new ArrayList<>();
        adapter = new CommonAdapter<ConsultBean>(getActivity(), consult,R.layout.order_layot) {
            @Override
            public void convert(ViewHolder helper, final ConsultBean item) {
                helper.setTextByResource(R.id.orderId, R.string.complainId, item.getId())
                        .setTextByResource(R.id.description,R.string.me,item.getContent());
                //  评论时间
                String strs = item.getConsultTime();
                if (!TextUtils.isEmpty(strs)){
                    if (strs.length() > 10){
                        strs = strs.substring(0,9);
                    }
                } else {
                    strs = "";
                }
                helper.setTextByResource(R.id.orderTime, R.string.order_time, strs);

                TextView type = helper.getView(R.id.schoolId);
                if (!consultType.isEmpty()){
                    if (!TextUtils.isEmpty(item.getType())){
                        for (ComplainType key : consultType){
                           if (key.getType().equals(item.getType())){
                               type.setText(getString(R.string.type,key.getTypeCn()));
                           }
                        }
                    }
                }
                strs = item.getAnswer();
                TextView back = helper.getView(R.id.money);
                if (!TextUtils.isEmpty(strs)){
                    back.setText(getString(R.string.answer, strs));
                    back.setVisibility(View.VISIBLE);
                } else {
                    back.setVisibility(View.GONE);
                }
                helper.setVisibility(R.id.state_layout,View.GONE);
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

        appAction.getConsultByUserId("getConsultByUserId", Params.ConsultByUserId, new ActionCallBackListener<List<ConsultBean>>() {
            @Override
            public void onSuccess(List<ConsultBean> data) {
                if (data != null) {
                    consult.clear();
                    consult.addAll(data);
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
                    consultType.clear();
                    consultType.addAll(data);
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
        getComplainType();
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
