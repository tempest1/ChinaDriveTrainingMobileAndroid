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
import com.smartlab.drivetrain.model.EvaluateBack;
import com.smartlab.drivetrain.model.EvaluateModel;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/12/29.
 * Evaluates
 */
public class Evaluates extends BaseFragment implements PullToRefreshLayout.OnRefreshListener{
    private PullToRefreshLayout layout;
    private View view;
    private List<EvaluateBack> feedBacks;
    private CommonAdapter<EvaluateBack> adapter;
    private EvaluateModel evaluateModel;
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
        feedBacks = new ArrayList<>();
        adapter = new CommonAdapter<EvaluateBack>(getActivity(),feedBacks,R.layout.evaluate_layout) {
            @Override
            public void convert(ViewHolder helper, EvaluateBack item) {
                TextView title = helper.getView(R.id.object_coach);
                TextView coach = helper.getView(R.id.object_title);
                //  教练名称
                String strs = item.getCoachName();
                if (!TextUtils.isEmpty(strs)){
                    coach.setText(helper.formatByText(R.string.coach, strs));
                    coach.setVisibility(View.VISIBLE);
                } else {
                    coach.setVisibility(View.GONE);
                }
                //  驾校名称
                strs = item.getDrivingName();
                if (!TextUtils.isEmpty(strs)){
                    title.setText(helper.formatByText(R.string.school,strs));
                    title.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                }
                //  评论时间
                strs = item.getEvaluateTime();
                if (!TextUtils.isEmpty(strs)){
                    if (strs.length() > 10){
                        strs = strs.substring(0,10);
                    }
                } else {
                    strs = "";
                }
                helper.setText(R.id.evaluate_time, strs);
                strs = item.getContent();
                if (TextUtils.isEmpty(strs)){
                    strs = "";
                }
                helper.setText(R.id.content,strs);
                switch (item.getGrade()){
                    case 1:
                        helper.setTextResource(R.id.grade,R.string.evaluate_level,
                                R.string.ok);
                        break;
                    case 2:
                        helper.setTextResource(R.id.grade,R.string.evaluate_level,
                                R.string.realOk);
                        break;
                    case 3:
                        helper.setTextResource(R.id.grade,R.string.evaluate_level,
                                R.string.very_good);
                        break;
                }

            }
        };
        list.setAdapter(adapter);
        list.setDividerHeight((int) getResources().getDimension(R.dimen.login_edit_padding));
        Util.runDelay(layout);
    }

    /**
     * 初始化基本数据
     */
    @Override
    protected void initData() {

        appAction.getEvaluateByUserId("getEvaluateByUserId", Params.EvaluateByUserId, new ActionCallBackListener<List<EvaluateBack>>() {
            @Override
            public void onSuccess(List<EvaluateBack> data) {
                if (data != null) {
                    LogUtils.e(data.toString());
                    feedBacks.clear();
                    feedBacks.addAll(data);
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


    /**
     * 刷新操作
     *
     * @param pullToRefreshLayout PullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
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
