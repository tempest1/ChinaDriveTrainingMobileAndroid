package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Frunm;
import com.smartlab.drivetrain.util.BaseViewHolder;

import java.util.List;

/**
 * Created by smartlab on 15/9/26.
 */
public class BankList extends BaseAdapter {
    private List<Frunm> frunmList;
    private Context context;
    public BankList(Context context,List<Frunm> frunmList){
        this.frunmList = frunmList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return frunmList.size();
    }

    @Override
    public Object getItem(int i) {
        return frunmList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context)
                    .inflate(R.layout.bank_list,viewGroup,false);
        }
        TextView titleText = BaseViewHolder.get(view,R.id.bank_title);
        TextView bank_description = BaseViewHolder.get(view,R.id.bank_description);
        Frunm frunm = frunmList.get(i);
        titleText.setText(frunm.getTitle());
        bank_description.setText(frunm.getDescription());
        return view;
    }
}
