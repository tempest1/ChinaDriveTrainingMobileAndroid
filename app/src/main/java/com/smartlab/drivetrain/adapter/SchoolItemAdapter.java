package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartlab.drivetrain.base.BaseObjectListAdapter;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Entity;
import com.smartlab.drivetrain.model.SchoolItem;
import com.smartlab.drivetrain.util.BaseViewHolder;
import com.smartlab.drivetrain.view.HandyTextView;

import java.util.List;

/**
 * Created by smartlab on 15/9/27.
 * 驾校列表
 */
public class SchoolItemAdapter extends BaseObjectListAdapter {

    private ImageLoader loader;
    private DisplayImageOptions options;
    public SchoolItemAdapter(Context context, List<? extends Entity> datas) {
        super(context, datas);
        loader = MainApplication.getImageLoader();
        options = MainApplication.getDisplayImageOptions();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = mInflater.inflate(R.layout.driving_item, viewGroup, false);
        }
        ImageView imageView = BaseViewHolder.get(view,R.id.driving_image);
        HandyTextView title = BaseViewHolder.get(view,R.id.driving_title);
        HandyTextView driving_description = BaseViewHolder.get(view,R.id.driving_description);
        HandyTextView driving_AllPlay = BaseViewHolder.get(view,R.id.all_play);
        HandyTextView address = BaseViewHolder.get(view,R.id.address);
        SchoolItem temp = (SchoolItem) getItem(i);
        if (!TextUtils.isEmpty(temp.getAllPay())){

            driving_AllPlay.setText(mContext.getResources().getString(R.string.all_play,temp.getAllPay()));
        } else {
            driving_AllPlay.setText(mContext.getResources().getString(R.string.all_play,"暂无数据"));
        }
        if (!TextUtils.isEmpty(temp.getAddress())){
            address.setText(mContext.getResources().getString(R.string.address,temp.getAddress()));
        } else {
            address.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(temp.getDescription())){
            driving_description.setText(mContext.getResources().getString(R.string.description, temp.getDescription()));
        } else {
            driving_description.setVisibility(View.GONE);
        }
        title.setText(temp.getName());
        loader.displayImage(temp.getImageUrl(),imageView,options,new AnimListener());
        return view;
    }
}
