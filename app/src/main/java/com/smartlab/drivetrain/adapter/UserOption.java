package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.util.BaseViewHolder;

/**
 * Created by smartlab on 15/9/16.
 *  用户中心选项
 */
public class UserOption extends BaseAdapter {

    private String []userOption;
    private int[] img_resource = {R.mipmap.qr_code,R.mipmap.dingdan,R.mipmap.shoucang,R.mipmap.consult,
            R.mipmap.pingjia,R.mipmap.qianbao,R.mipmap.shezhi,
            R.mipmap.guanyu};
    private Context context;
    public UserOption(String [] userOption,Context context){
        this.userOption = userOption;
//        this.img_resource = img_resource;
        this.context = context;
    }

    @Override
    public int getCount() {

        return userOption.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context)
                    .inflate(R.layout.user_center_item, viewGroup, false);
        }
        ImageView list_icon = BaseViewHolder.get(view,R.id.list_icon);
        TextView list_string = BaseViewHolder.get(view,R.id.list_string);
        ImageView imageRight = BaseViewHolder.get(view, R.id.imageRight);
        imageRight.setImageResource(R.mipmap.right);
        list_icon.setImageResource(img_resource[position]);
        list_string.setText(userOption[position]);
        return view;
    }
}
