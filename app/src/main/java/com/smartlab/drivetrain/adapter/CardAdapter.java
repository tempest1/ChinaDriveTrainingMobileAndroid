package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseObjectListAdapter;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Card;
import com.smartlab.drivetrain.model.Entity;
import com.smartlab.drivetrain.util.BaseViewHolder;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.HandyTextView;

import java.util.Date;
import java.util.List;

/**
 * Created by smartlab on 15/10/8.
 */
public class CardAdapter extends BaseObjectListAdapter {

    public CardAdapter(Context context, List<? extends Entity> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.card_item,viewGroup,false);
        }

        TextView card_title = BaseViewHolder.get(view, R.id.card_title);
        TextView card_author = BaseViewHolder.get(view,R.id.card_author);
        HandyTextView card_time = BaseViewHolder.get(view,R.id.card_time);
        Card temp = (Card) mDatas.get(i);
        System.out.println(temp.getCreateTime());
        Date date = Util.StringToDate(temp.getCreateTime());
        if (date != null){
            String time = DateTools.formatDateTime(date);
            card_time.setText(time);
        } else {
            card_time.setText("");
        }
        card_title.setText(temp.getTitle());
        card_author.setText(temp.getName());
        return view;
    }
}
