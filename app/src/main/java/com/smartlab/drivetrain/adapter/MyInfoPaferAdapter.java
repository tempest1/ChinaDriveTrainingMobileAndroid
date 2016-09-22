package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smartlab.drivetrain.detail.InfoDetail;
import com.smartlab.drivetrain.model.Info;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhoudi on 16/3/15.
 * /**
 * Created by smartlab on 15/9/14.
 * Banner驾校新闻展示，数据适配器
 */

public class MyInfoPaferAdapter extends PagerAdapter {
    private List<Info> InfoList;
    private List<ImageView> imageViews;


    public MyInfoPaferAdapter(List<Info> InfoList, List<ImageView> imageViews, Context context ){
        this.InfoList = InfoList;
        this.imageViews = imageViews;
        this.context=context;

    }

    private Context context;



    @Override
    public int getCount() {
        return InfoList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView iv = imageViews.get(position);
        ((ViewPager) container).addView(iv);
        final Info info = InfoList.get(position);

        Log.e("sys", info.toString());
        // 在这个方法里面设置图片的点击事件
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 处理跳转逻辑
                Log.e("sys", "第" + position + "被点击了");
                Intent intent = new Intent();

                intent.setClass(context, InfoDetail.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("info", info);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });

        return iv;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {

    }

    @Override
    public void finishUpdate(View arg0) {

    }
}
