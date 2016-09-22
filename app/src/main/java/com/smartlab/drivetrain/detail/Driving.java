package com.smartlab.drivetrain.detail;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.smartlab.drivetrain.adapter.SchoolItemAdapter;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.database.SQLdm;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.SchoolItem;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/27.
 * 驾校推荐
 */
public class Driving extends BaseActivity implements View.OnClickListener,PullToRefreshLayout.OnRefreshListener,ActionCallBackListener<List<SchoolItem>>{
    private SchoolItemAdapter adapter;
    private List<SchoolItem> schoolItem;
    private PullToRefreshLayout layout;
    private int pagination = 1; //页码
    private int pageNum = 12;   //  每页显示数量
    private CursorLoader cur;
    private Cursor cursor;
    private static int currentState = PullToRefreshLayout.PULL_REFRESH;    //当前list的状态
    private int citycode = 0;
    private SQLdm dm;
    private BDLocation location;
    private TextView btn_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.driving);
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected void initData() {
        cur = new CursorLoader(this);
        dm = new SQLdm(this);

        //通过百度定位获取当前城市信息，通过城市信息查询数据库获取城市编码
        location = MainApplication.getLocation();
        if (location != null){
            String city = location.getCity();
            if (city != null){
                btn_location.setText(city);
            } else {
                btn_location.setText("定位失败");
            }
        }
        // 查询数据库，城市编码
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return queryCityCode(location);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                citycode = integer;
                Util.runDelay(layout);
            }
        }.execute();

    }
    //  通过位置信息查询城市编码
    private int queryCityCode(BDLocation location) {
        int cityCode = 0;
        if (location != null){
            String city = location.getCity();
            if (!TextUtils.isEmpty(city)) {
                cursor = dm.queryCityCode(city);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        cityCode = cursor.getInt(cursor.getColumnIndex("CityCode"));
                    }
                    LogUtils.e(city + cityCode);
                    cursor.close();
                } else {
                    LogUtils.e("cursor is null");
                }
            }
        }
        LogUtils.e("citycode" + cityCode);
        return cityCode;
    }
    @Override
    protected void initView() {
        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        PullableListView view = (PullableListView) findViewById(R.id.pull_list);
        Button back = (Button) findViewById(R.id.detail_back);
        back.setOnClickListener(Driving.this);
        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText("驾校推荐");
        btn_location = (TextView) findViewById(R.id.location);
        schoolItem = new ArrayList<>();
        adapter = new SchoolItemAdapter(this,schoolItem);
        view.setAdapter(adapter);
        layout.setOnRefreshListener(this);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SchoolItem item = (SchoolItem) parent.getAdapter().getItem(position);
                SchoolDetail(item.getTimestamp());
            }
        });
    }

    private void SchoolDetail(String timestamp) {
        if (!TextUtils.isEmpty(timestamp)){
            Intent intent = new Intent(this, TrainingShow.class);
            intent.putExtra("timestamp",timestamp);
            startActivity(intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        } else {
            LogUtils.e("内部错误");
            showToast("无法或该驾校详情，请稍后再试");
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_back:
                close(Driving.this);
                break;

        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_REFRESH;
        if (citycode != 0) {
            LogUtils.i("citycode:" + citycode);
            appAction.getDrivingList("getDrivingListByCityCode", citycode, pagination, pageNum, Params.DrivingListByCityCode, this);
        } else {
            layout.refreshFinish(PullToRefreshLayout.FAIL);

        }
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        currentState = PullToRefreshLayout.PULL_LOAD;
        if (citycode != 0) {
            LogUtils.i("citycode:" + citycode);
            pagination++;
            appAction.getDrivingList("getDrivingListByCityCode", citycode, pagination, pageNum, Params.DrivingListByCityCode, this);
        } else {
            //请求定位信息
            layout.loadmoreFinish(PullToRefreshLayout.FAIL);
        }
    }

    @Override
    public void onSuccess(List<SchoolItem> data) {
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
    protected void onDestroy() {
        if (cur != null && cur.isStarted())
        //  关闭游标
        cur.onCanceled(cursor);
        // 关闭数据库对象
        if (dm != null)
        dm.closeDB();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
