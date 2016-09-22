package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Info;
import com.smartlab.drivetrain.util.BaseViewHolder;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.Util;

import java.util.Date;
import java.util.List;

/**
 * Created by smartlab on 15/9/21.
 * 新闻列表适配器
 *
 */
public class InfoAdapter extends BaseAdapter {

    private List<Info> infosList;
    private Context mContext;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

    public InfoAdapter(Context mContext,List<Info> infosList){
        this.mContext = mContext;
        this.infosList = infosList;
        mImageLoader = MainApplication.getImageLoader();
        options = MainApplication.getDisplayImageOptions();
    }
    @Override
    public int getCount() {
        return infosList.size();
    }

    @Override
    public Object getItem(int i) {
        return infosList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.info_item, parent, false);
        }
        TextView title = BaseViewHolder.get(convertView,R.id.info_title);
        TextView origin = BaseViewHolder.get(convertView,R.id.time);
        ImageView imageView = BaseViewHolder.get(convertView, R.id.info_image);
        // 这里填充数据到视图当中
        Info temp = infosList.get(i);
        title.setText(temp.getTitle());
        Date date = Util.StringToDate(temp.getReleaseTime());
        if (date != null){
            String time = DateTools.formatDateTime(date);
            origin.setText(time);
        } else {
            origin.setText("");
        }
        if (temp.getImage() != null) {
            if (!temp.getImage().equals("")) {
                imageView.setVisibility(View.VISIBLE);
                mImageLoader.displayImage(temp.getImage(), imageView, options, new AnimListener());
            }
        } else {
            imageView.setVisibility(View.GONE);
        }
        return convertView;
    }

}
