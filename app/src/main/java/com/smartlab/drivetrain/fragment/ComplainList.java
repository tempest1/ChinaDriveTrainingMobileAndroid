package com.smartlab.drivetrain.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseDialog;
import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.base.CommonAdapter;
import com.smartlab.drivetrain.base.ViewHolder;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.ComplainType;
import com.smartlab.drivetrain.model.FeedBack;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.EmoticonsEditText;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/12/29.
 * list
 */
public class ComplainList extends BaseFragment implements PullToRefreshLayout.OnRefreshListener{

    private PullToRefreshLayout layout;
    private View view;
    private List<FeedBack> feedBacks;
    private CommonAdapter<FeedBack> adapter;
    private List<ComplainType> complainTypes;
    private BaseDialog dialog;
    private EmoticonsEditText input;
    private String id = null;
    private LoadingDialog load;
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
        complainTypes = new ArrayList<>();
        load = new LoadingDialog(getActivity(),R.drawable.loading);
        adapter = new CommonAdapter<FeedBack>(getActivity(),feedBacks,R.layout.order_layot) {
            @Override
            public void convert(ViewHolder helper, final FeedBack item) {
                helper.setTextByResource(R.id.orderId, R.string.complainId,
                        item.getId());
                helper.setTextByResource(R.id.schoolId, R.string.school,
                        item.getName());
                if (complainTypes != null) {
                   int size = complainTypes.size();
                    for (int i = 0;i < size;i++){
                        ComplainType type = complainTypes.get(i);
                        if (type.getType().equals(item.getType())){
                            helper.setTextByResource(R.id.description,R.string.complaintype,type.getTypeCn());
                        }
                    }
                }
                String strs = item.getContent();
                if (TextUtils.isEmpty(strs)){
                    strs = "";
                }
                helper.setTextByResource(R.id.money, R.string.me,
                        strs);
                String feedback = item.getFeedback();
                if (TextUtils.isEmpty(feedback)){
                    feedback = "";
                }
                strs = item.getFeedbackTime();
                if (!TextUtils.isEmpty(strs)){
                    if (strs.length() > 10){
                        strs = strs.substring(0,10);
                        strs = strs + "：" + feedback;
                    }
                } else {
                    strs = "";
                }
                helper.setTextByResource(R.id.state_tv,R.string.feed,
                        strs);
                strs = item.getComplainTime();
                if (!TextUtils.isEmpty(strs)){
                    if (strs.length() > 10){
                        strs = strs.substring(0,10);
                    }
                } else {
                    strs = "";
                }
                helper.setTextByResource(R.id.orderTime, R.string.order_time,
                        strs);

                helper.getView(R.id.cancel).setVisibility(View.GONE);

                final Button feed = helper.getView(R.id.pay);
                if (item.isHandled()) {
                    feed.setVisibility(View.VISIBLE);
                    feed.setText("反馈");
                    feed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LogUtils.e("---反馈---");
                            if (dialog != null){
                                id = item.getId();
                                if (id != null)
                                dialog.show();
                            }
                        }
                    });
                } else {
                    feed.setVisibility(View.GONE);
                }

                TextView answer = helper.getView(R.id.answer);
                strs = item.getAnswer();
                if (!TextUtils.isEmpty(strs)){
                    answer.setText(getString(R.string.answer,strs));
                    answer.setVisibility(View.VISIBLE);
                } else {
                    answer.setVisibility(View.GONE);
                }
            }
        };
        list.setAdapter(adapter);
        Util.runDelay(layout);
        initDialog();
    }

    //  初始化对话框
    private void initDialog() {
        dialog = new BaseDialog(getActivity());
        dialog.setDialogContentView(R.layout.feedback_layout);
        dialog.setTitle(getString(R.string.problem_feed));
        input = (EmoticonsEditText) dialog.findViewById(R.id.input);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  发送信息
                String str = input.getText().toString().trim();
                if (TextUtils.isEmpty(str)) {
                    showToast(getString(R.string.Not_Write));
                    return;
                }
                sendFeed(str);
                LogUtils.e("---------------");
                dialog.dismiss();
            }
        });
    }

    private void sendFeed(String str) {
        load.show();
        appAction.addFeedback("addFeedback", str, id, Params.addFeedback, new ActionCallBackListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                load.dismiss();
                layout.autoRefresh();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                showToast(message);
                load.dismiss();
            }
        });
    }

    /**
     * 初始化基本数据
     */
    @Override
    protected void initData() {

        appAction.getComplainList("getComplainList", Params.Complains, new ActionCallBackListener<List<FeedBack>>() {
            @Override
            public void onSuccess(List<FeedBack> data) {
                if (data != null) {
                    feedBacks.clear();
                    feedBacks.addAll(data);
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
        //   获取投诉类型
        appAction.getComplainTypeList("getComplainTypeList", Params.Complain_List, new ActionCallBackListener<List<ComplainType>>() {
            @Override
            public void onSuccess(List<ComplainType> data) {
                if (data != null) {
                    LogUtils.e(data.toString());
                    complainTypes.clear();
                    complainTypes.addAll(data);
                    initData();
                    layout.refreshFinish(PullToRefreshLayout.SUCCEED);
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
