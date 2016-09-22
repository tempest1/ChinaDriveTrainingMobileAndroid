package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseObjectListAdapter;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Entity;
import com.smartlab.drivetrain.model.TlineEntity;
import com.smartlab.drivetrain.util.BaseViewHolder;
import com.smartlab.drivetrain.view.CircleImageView;
import com.smartlab.drivetrain.view.CircleTextView;
import com.smartlab.drivetrain.view.HandyTextView;

import java.util.List;

/**
 * Created by smartlab on 15/11/14.
 * 时间轴数据适配器
 */
public class TimeLineAdapter extends BaseObjectListAdapter {
    public TimeLineAdapter(Context context, List<? extends Entity> datas) {
        super(context, datas);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.timeline_item,parent,false);
        }
        //  当前科目
        CircleTextView time_index = BaseViewHolder.get(convertView,R.id.time_index);

        //  当前科目后面的背景
        CircleImageView time_bac = BaseViewHolder.get(convertView,R.id.time_bac);

        //  提示标签
        HandyTextView time_tips = BaseViewHolder.get(convertView,R.id.time_tips);

        //  当前项的名称
        TextView time_title = BaseViewHolder.get(convertView,R.id.time_title);
        //
        View line_v = BaseViewHolder.get(convertView,R.id.line_v);
        //  数据实体
        TlineEntity entity = (TlineEntity) getItem(position);
        if (entity != null){
            //  正在进行显示为
            if (entity.isDoing()){
                time_bac.setImageResource(R.color.orange);
                //  已经通过显示为绿色
            } else if (entity.isPass()){
                time_bac.setImageResource(R.color.blue);
                //  是否
            } else if (entity.isDone()){
                time_bac.setImageResource(R.color.greeny);
            } else {
                time_bac.setImageResource(R.color.gray);
            }
        }
        time_index.setText(entity.getTime_index());
        time_tips.setText(entity.getTime_tips());
        time_title.setText(entity.getTime_title());
        return convertView;
    }
}
