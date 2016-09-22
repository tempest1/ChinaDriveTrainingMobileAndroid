package com.smartlab.drivetrain.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.InfoAdapter;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Info;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/11/13.
 * 诚信联盟，展示联盟里面的驾校
 */
public class Alliance extends BaseActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,ActionCallBackListener<List<Info>> {
    private InfoAdapter adapter;
    private List<Info> schoolItem;
    private PullToRefreshLayout layout;
    private int pagination = 1;                     //  页码
    private int pageNum = 12;                       //  每页显示数量
    private static int currentState = PullToRefreshLayout.PULL_REFRESH;    //当前list的状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.driving);
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        PullableListView view = (PullableListView) findViewById(R.id.pull_list);
        Button back = (Button) findViewById(R.id.detail_back);
        back.setOnClickListener(Alliance.this);
        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText("诚信联盟");
        schoolItem = new ArrayList<>();
        adapter = new InfoAdapter(this,schoolItem);
        view.setAdapter(adapter);
        layout.setOnRefreshListener(this);
        Util.runDelay(layout);
        view.setOnItemClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_back:
                close(Alliance.this);
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_REFRESH;
//        appAction.getAll_DrivingList("getDrivingList",pagination,pageNum, Params.DrivingListByCityCode,this);
        appAction.loadInfo("userQueryAllInfo", pagination, 10, "20150101", DateTools.getTime(), "union", Params.Alliance, this);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_LOAD;
        pagination++;
//        appAction.getAll_DrivingList("getDrivingList",pagination,pageNum,Params.DrivingListByCityCode,this);
        appAction.loadInfo("userQueryAllInfo", pagination, 10, "20150101", DateTools.getTime(), "union", Params.Alliance, this);
    }


    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<Info> data) {
        if (data != null){
            LogUtils.e(data.toString());
            switch (currentState){
                case PullToRefreshLayout.PULL_REFRESH:
                    schoolItem.clear();
                    schoolItem.addAll(data);
                    adapter.notifyDataSetChanged();
                    layout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    LogUtils.e("刷新操作");
                    break;
                case PullToRefreshLayout.PULL_LOAD:
                    if (data.size() <= 0) {
                        pagination --;
                        showToast("没有更多类容");
                    }
                    schoolItem.addAll(data);
                    adapter.notifyDataSetChanged();
                    layout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    LogUtils.e("加载操作");
                    break;
                default:break;
            }
        }
    }

    @Override
    public void onFailure(String errorEvent, String message) {
        LogUtils.e(message);
        switch (currentState){
            case PullToRefreshLayout.PULL_REFRESH:
                layout.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case PullToRefreshLayout.PULL_LOAD:
                layout.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
            default:break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.e("info_click" + parent.getAdapter().getItem(position).toString());
        Info data = (Info) parent.getAdapter().getItem(position);
        showInfoDetail(data);
    }
    private void showInfoDetail(Info data) {
        Intent intent = new Intent();
        intent.setClass(this, InfoDetail.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", data);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }
}
