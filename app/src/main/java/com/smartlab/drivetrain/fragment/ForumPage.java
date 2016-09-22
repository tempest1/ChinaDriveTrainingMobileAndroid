package com.smartlab.drivetrain.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.BankList;
import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.frum.CardList;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Frunm;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/16.
 * 交流专区页面
 */
public class ForumPage extends BaseFragment implements PullToRefreshLayout.OnRefreshListener,ActionCallBackListener<List<Frunm>>{
    private View view;
    private ListView info_list;
    private PullToRefreshLayout layout;
    private List<Frunm> frunmList = new ArrayList<>();
    private BankList adapter;
    private String Serializable_card = "card";
    private String CMD_FORUM = "showPlateList";
    private static int currentState = PullToRefreshLayout.PULL_REFRESH;
    private AdapterView.OnItemClickListener info_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Frunm frunm = (Frunm) adapterView.getAdapter().getItem(i);
            showCardList(frunm);
        }
    };

    private void showCardList(Frunm frunm) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(getActivity(),CardList.class);
        bundle.putSerializable(Serializable_card, (Serializable) frunm);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null){
                parent.removeView(view);
            }
            return view;
        }else{
            view = inflater.inflate(R.layout.forum_page, container, false);
        }
        initView();
        return view;
    }

    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        layout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        layout.setOnRefreshListener(this);
        info_list = (ListView) view.findViewById(R.id.pull_list);
        adapter = new BankList(getActivity(), frunmList);
        info_list.setAdapter(adapter);
        info_list.setOnItemClickListener(info_click);
        TextView title = (TextView) view.findViewById(R.id.detail_title);
        title.setText("交流");
        Button button = (Button) view.findViewById(R.id.detail_back);
        button.setVisibility(View.GONE);
        Util.runDelay(layout);
    }

    @Override
    public void initData() {

    }

    /**
     * 刷新操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_REFRESH;
        appAction.loadBank(CMD_FORUM,Params.FORUM_URL,this);
    }

    /**
     * 加载操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_REFRESH;
        appAction.loadBank(CMD_FORUM,Params.FORUM_URL,this);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        LogUtils.i("handler 消息已清除");
        super.onDestroy();
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<Frunm> data) {
        switch (currentState) {
            case PullToRefreshLayout.PULL_REFRESH:
                frunmList.clear();
                frunmList.addAll(data);
                adapter.notifyDataSetChanged();
                layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                frunmList.addAll(data);
                adapter.notifyDataSetChanged();
                layout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
            default:break;
        }
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                layout.refreshFinish(PullToRefreshLayout.FAIL);
                showToast(message);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                layout.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.e("sys", "onHiddenChanged");
//
//         Log.e("sys",frunmList.toString());
        if(MainApplication.isWeb==30) {
            if (frunmList.toString() == "[]") {
                Log.e("sys", "infosList=null");
                currentState = PullToRefreshLayout.PULL_REFRESH;
                appAction.loadBank(CMD_FORUM, Params.FORUM_URL, this);
            } else {
                //Log.e("sys", infosList.toString());
            }
        }else
        {
            Log.e("sys","网络连接中断");
        }
    }
}
