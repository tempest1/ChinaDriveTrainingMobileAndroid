package com.smartlab.drivetrain.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;
import com.smartlab.drivetrain.adapter.InfoAdapter;
import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.detail.InfoDetail;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Info;
import com.smartlab.drivetrain.model.NewsEntity;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.model.networks;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/16.
 * 资讯展示页面
 *
 */
public class InfoPage extends BaseFragment implements PullToRefreshLayout.OnRefreshListener,ActionCallBackListener<List<Info>>{
    private View view;
    private List<Info> infosList;
    private ListView info_list;
    private PullToRefreshLayout layout;
    private InfoAdapter adapter;
    private int total = 0;      //总新闻条数
    private int totalPage = 0;  //总页数
    private int index = 1;      //默认加载第一页
    private String CMD_INFO= "userQueryAllInfo";
    private static int currentState = PullToRefreshLayout.PULL_REFRESH;


    private AdapterView.OnItemClickListener info_click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Log.e("info_click", adapterView.getAdapter().getItem(position).toString() + "");
            Info data = (Info) adapterView.getAdapter().getItem(position);
            showInfoDetail(data);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showInfoDetail(Info data) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), InfoDetail.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", data);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("sys", "onCreateView");
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null){
                parent.removeView(view);
            }
            return view;
        }else {
            view = inflater.inflate(R.layout.info_page, container, false);
        }
        initView();
        return view;
    }

    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        infosList = new ArrayList<>();
        layout = (PullToRefreshLayout) view.findViewById(R.id.refresh_view);
        layout.setOnRefreshListener(this);
        info_list = (ListView) view.findViewById(R.id.pull_list);
        adapter = new InfoAdapter(getActivity(), infosList);
        info_list.setAdapter(adapter);
        info_list.setOnItemClickListener(info_click);
        info_list.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true));//设置ListView在滑动时不加载图片
        TextView detail_title = (TextView) view.findViewById(R.id.detail_title);
        Button detail_back = (Button) view.findViewById(R.id.detail_back);
        detail_back.setVisibility(View.GONE);
        detail_title.setText("新闻资讯");
    }

    /**
     * 初始化基本数据
     */
    @Override
    public void initData() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        layout.autoRefresh();
    }

    /**
     * 刷新操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_REFRESH;
        appAction.loadInfo(CMD_INFO, index, 10, "20150101", DateTools.getTime(), "news", Params.INFO_URL, this);
    }

    @Override
    public void onStart() {
        Log.e("sys","onStart");
        super.onStart();
    }

    /**
     * 加载操作
     *
     * @param pullToRefreshLayout
     */
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_LOAD;
        int i = total / 15;
        int j = total % 15;
        if (i > 0 && j > 0){
            index = i + 1;
            LogUtils.i(totalPage+"");
            if (totalPage > index) {
                index++;
                appAction.loadInfo(CMD_INFO, index, 10, "20150101", DateTools.getTime(), "news", Params.INFO_URL,this);
            }
        } else {
            layout.loadmoreFinish(PullToRefreshLayout.FAIL);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        Log.e("sys","onAttach");
        super.onAttach(activity);
    }



    @Override
    public void onDestroy() {
        Log.e("sys","onDestroy");
        super.onDestroy();
    }
    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<Info> data) {
        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                if (data != null){
                    infosList.clear();
                    infosList.addAll(data);
                    adapter.notifyDataSetChanged();
                    layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    layout.refreshFinish(PullToRefreshLayout.FAIL);
                }
                break;
            case PullToRefreshLayout.PULL_LOAD:
                if (data != null){
                    infosList.addAll(data);
                    adapter.notifyDataSetChanged();
                    layout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                } else {
                    layout.loadmoreFinish(PullToRefreshLayout.FAIL);
                }
                break;
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
        LogUtils.i(message);
        showToast(message);
        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                    layout.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                layout.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }

    }

    @Override
    public void onStop() {

        super.onStop();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
     //   Log.e("sys", "onHiddenChanged");

        if(MainApplication.isWeb==30) {
            if (infosList.toString() == "[]") {
              //  Log.e("sys", "infosList=null");
                currentState = PullToRefreshLayout.PULL_REFRESH;
                appAction.loadInfo(CMD_INFO, index, 10, "20150101", DateTools.getTime(), "news", Params.INFO_URL, this);
            } else {
               // Log.e("sys", infosList.toString());
            }
        }else
        {
                Log.e("sys","网络连接中断");
        }
    }
}
