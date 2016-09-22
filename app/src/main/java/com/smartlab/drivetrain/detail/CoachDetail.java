package com.smartlab.drivetrain.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.CoachAdapter;
import com.smartlab.drivetrain.data.CoachData;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.CoachBrief;
import com.smartlab.drivetrain.model.Discuss;
import com.smartlab.drivetrain.model.SchoolItem;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;
import com.smartlab.drivetrain.util.HttpEngine;
import com.smartlab.drivetrain.util.LogUtils;

import java.util.List;

/**
 * Created by smartlab on 15/9/27.
 *  教练详情页面
 */
public class CoachDetail extends Activity implements View.OnClickListener{
    private PullToRefreshLayout layout;
    private int pagination = 1; //页码
    private int pageNum = 12;   //  每页显示数量
    private static int currentState = PullToRefreshLayout.PULL_REFRESH;    //当前list的状态
    private List<CoachBrief> coachBriefs;
    private CoachAdapter adapter;
    TextView dianping;
    PullableListView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving);
        initView();
    }

    private void initView() {
        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
       view = (PullableListView) findViewById(R.id.pull_list);
//        TextView pinglun = (TextView) findViewById(R.id.driving_pinglun);
//        pinglun.setVisibility(View.VISIBLE);

        coachBriefs = CoachData.getCoachBrief();
//        adapter = new CoachAdapter(coachBriefs,this);
//        view.setAdapter(adapter);
//        dianping = (TextView) findViewById(R.id.driving_pinglun);
//        dianping.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CoachBriefs(Discuss.diacussname);
//            }
//        });
        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText("教练推荐");
        Button back = (Button) findViewById(R.id.detail_back);
        back.setOnClickListener(this);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CoachBrief item = (CoachBrief) parent.getAdapter().getItem(position);
                CoachBriefs(item.getCoachName());
//                Discuss.coachTimestamp = CoachData.coachTimespase[position];
            }
        });
        new NewsAsyncTask().execute();
    }

    private void CoachBriefs(String CoachName) {
        if (!TextUtils.isEmpty(CoachName)){
            Intent intent = new Intent(this, discuss.class);
            intent.putExtra("CoachName",CoachName);
            startActivity(intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        } else {
            LogUtils.e("内部错误");
        }
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.detail_back:
                CoachDetail.this.finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                break;
//            case R.id.driving_pinglun:
//                CoachBriefs(Discuss.diacussname);
//                break;
//            default:break;
        }
    }
    class NewsAsyncTask extends AsyncTask<String, Void, List<CoachBrief>> {
        @Override//耗时处理完成后就可以在oncreate中进行数据的使用
        protected List<CoachBrief> doInBackground(String... params) {
            HttpEngine httpEngine=new HttpEngine();
            return httpEngine.getJsonData();
        }

        @Override//实现与UI的交互
        protected void onPostExecute(List<CoachBrief> newsBeans) {

            super.onPostExecute(newsBeans);
            CoachAdapter adapter = new CoachAdapter( newsBeans,CoachDetail.this);
            view.setAdapter(adapter);
//            mSwipeLayout.setRefreshing(false);//设置首次不刷新
        }

    }
}
