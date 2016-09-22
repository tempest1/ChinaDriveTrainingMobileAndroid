package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.util.BaseViewHolder;


/**
 * Created by smartlab on 15/9/4.
 * 主界面九宫格布局适配器
 */
public class MyGridAdapter extends BaseAdapter {

    private Context mContext;

    public String[] img_text = { "驾校推荐","教练推荐", "我要报名", "预约考试", "预约训练", "考试进度", "学时卡",
            "在线模拟", "考试秘籍","诚信联盟","Vip专享","在线答疑" };
    public int[] imgs = {R.mipmap.jiaoxiaotuijian,R.mipmap.jiaoliantuijian,
            R.mipmap.woyaobaoming,R.mipmap.yuyuekaoshi,
            R.mipmap.yuyuexunlian,R.mipmap.kaoshijindu,R.mipmap.xueshika,R.mipmap.zaixianmoni,R.mipmap.kaoshimiji,
            R.mipmap.chengxin,R.mipmap.vip,R.mipmap.wentidayi
    };

    public MyGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item, parent, false);
        }
//        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

//        tv.setText(img_text[position]);
        return convertView;
    }
}
