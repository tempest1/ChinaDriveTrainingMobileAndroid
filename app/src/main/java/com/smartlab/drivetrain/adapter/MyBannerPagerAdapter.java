package com.smartlab.drivetrain.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smartlab.drivetrain.model.AdTrain;

import java.util.List;

/**
 * Created by smartlab on 15/9/14.
 * Banner驾校信息展示，数据适配器
 */
public class MyBannerPagerAdapter extends PagerAdapter {

    private List<AdTrain> adList;
    private List<ImageView> imageViews;
    public MyBannerPagerAdapter(List<AdTrain> adList,List<ImageView> imageViews){
        this.adList = adList;
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return adList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView iv = imageViews.get(position);
        ((ViewPager) container).addView(iv);
        final AdTrain AdTrain = adList.get(position);
        // 在这个方法里面设置图片的点击事件
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 处理跳转逻辑
                Log.i("BannerImage","第"+position+"被点击了");
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


