package com.smartlab.drivetrain.license;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartlab.drivetrain.guide.GuidePager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/21.
 * 应用程序欢迎界面
 */
public class WelCome extends Activity implements ViewPager.OnPageChangeListener{

    private List<View> views;
    private LayoutInflater inflater;

    private List<View> dots;
    private List<View> dotList;

//    String imageUri = "drawable://"; // from drawables (only images, non-9patch)   "drawable://" + R.mipmap.app_citycard
    private int imageResId [] = {R.mipmap.guide2};//R.mipmap.guide2
//    private String imageResId [] = {"drawable://"+R.mipmap.welcome_first, "drawable://"+R.mipmap.guide2 };
    private ImageLoader imageLoader = MainApplication.getImageLoader();
//    private String lastUrl = "drawable://" +R.mipmap.guide3;
//    String imageUri = "http://site.com/image.png"; // from Web
//    String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
//    String imageUri = "content://media/external/audio/albumart/13"; // from content provider
//    String imageUri = "assets://image.png"; // from assets
//    String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        initView();
    }


    private void initView() {
        ViewPager welCome = (ViewPager) findViewById(R.id.welCome);
        inflater = LayoutInflater.from(this);
        views = new ArrayList< >();

        // 点
        dots = new ArrayList< >();
        dotList = new ArrayList< >();
        // 定义的五个指示点
        View dot0 = findViewById(R.id.v_dot0);
        View dot1 = findViewById(R.id.v_dot1);
        View dot2 = findViewById(R.id.v_dot2);
        View dot3 = findViewById(R.id.v_dot3);
        View dot4 = findViewById(R.id.v_dot4);
        dots.add(dot0);
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);

        dot2.setVisibility(View.GONE);
        dot3.setVisibility(View.GONE);
        dot4.setVisibility(View.GONE);

        setImageRes(imageResId);
        welCome.setAdapter(new GuidePager(views, this));
        welCome.addOnPageChangeListener(this);
    }

    //添加图片导视图当中
    private void setImageRes(int [] imageRes) {
        for (int res :
                imageRes) {
            View view = inflater.inflate(R.layout.guide1,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setBackgroundResource(res);
           // imageLoader.displayImage(imageRes[i], imageView);
            views.add(view);
        }
        // 最后一页
        View last = inflater.inflate(R.layout.guide4, null);
        ImageView image = (ImageView) last.findViewById(R.id.imageView);
        image.setBackgroundResource(R.mipmap.guide3);
//        imageLoader.displayImage(lastUrl, image);
        views.add(last);
        // 点
        for (int i= 0; i < views.size();i++){
            dots.get(i).setVisibility(View.VISIBLE);
            dotList.add(dots.get(i));
        }
    }

    private int oldPosition = 0;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentDot(position);
    }

    private void setCurrentDot(int position) {
        dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
        dots.get(position).setBackgroundResource(R.drawable.dot_focused);
        oldPosition = position;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
