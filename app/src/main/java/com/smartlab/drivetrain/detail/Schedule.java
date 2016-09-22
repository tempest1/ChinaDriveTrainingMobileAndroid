package com.smartlab.drivetrain.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.TimeLineAdapter;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.TlineEntity;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/11/13.
 * 学车进度，timeLine
 */
public class Schedule extends BaseActivity implements View.OnClickListener{
    private TimeLineAdapter adapter;
    private List<TlineEntity> list;
    //  time line content
    private String [] index = {"报名成功","科一训练","科一考试(理论考试)",
            "科二训练","科二考试","科三训练","科三考试","科四训练","科四考试(理论考试)","拿驾照"};
    private String [] tip = {"报名学车","理论考试好好复习","沉着应对，考试其实没那么难",
            "熟悉车辆，不慌不忙，切勿心急浮躁","调整心态，有信心。","行车多看看周围，安全行驶最重要",
            "路上开车不慌忙，不抢道，注意车距，控制离合随时停车","细心做题不马虎，自我练习不偷懒","自信考试，拿照就在眼前。","恭喜您，拿驾照！"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving);
        initView();
        initData();
    }

    @Override
    protected void initData() {
        for (int i = 0;i < index.length;i++){
            TlineEntity entity = new TlineEntity();
            if (i % 2 == 0) {
                entity.setIsDoing(true);
            } else {
                entity.setIsDoing(false);
            }
            entity.setIsDone(false);
            entity.setIsPass(false);
            entity.setTime_tips(tip[i]);
            entity.setTime_title(index[i]);
            entity.setTime_index(i+1+"");
            list.add(entity);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        PullToRefreshLayout layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        PullableListView view = (PullableListView) findViewById(R.id.pull_list);
        view.setDivider(null);
        list = new ArrayList<>();
        adapter = new TimeLineAdapter(this,list);
        view.setAdapter(adapter);

        Button back = (Button) findViewById(R.id.detail_back);
        back.setOnClickListener(this);

        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText("考试进度");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_back:
                close(Schedule.this);
                break;
            default:break;
        }
    }
}
