package com.smartlab.drivetrain.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.CardResponse;
import com.smartlab.drivetrain.util.BaseViewHolder;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.HandyTextView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smartlab on 15/10/10.
 *  帖子回复列表数据适配器
 */
public class PostAdapter extends BaseAdapter {
    private ImageLoader mImageLoader;
    private List<CardResponse> cardResponses;
    private Context context;
    public PostAdapter(Context context,List<CardResponse> cardResponses){
        this.cardResponses = cardResponses;
        this.context = context;
        mImageLoader = MainApplication.getImageLoader();
    }

    @Override
    public int getCount() {
        return cardResponses.size();
    }

    @Override
    public Object getItem(int i) {
        return cardResponses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context)
                    .inflate(R.layout.response_viewed_item,viewGroup,false);
        }
        ImageView response_image = BaseViewHolder.get(view,R.id.response_image);
        TextView response_name = BaseViewHolder.get(view,R.id.response_name);
        TextView response_time = BaseViewHolder.get(view,R.id.response_time);
        HandyTextView response_content = BaseViewHolder.get(view,R.id.response_content);
        ImageView image_one = BaseViewHolder.get(view, R.id.image_one);
        ImageView image_two = BaseViewHolder.get(view, R.id.image_two);
        ImageView image_three = BaseViewHolder.get(view, R.id.image_three);
        ImageView big_pic = BaseViewHolder.get(view,R.id.big_pic);

        CardResponse temp = cardResponses.get(i);
        response_name.setText(temp.getName());
        response_time.setText(temp.getCreateTime());
        if (!Util.isEmptyString(temp.getContent())) {
            //过滤出所有img标签下的src地址
            List<String> img_url = Util.getImgSrc(temp.getContent());

            if (img_url != null){
                int size = img_url.size();
                if (size > 0) {
                    LogUtils.i(img_url.toString());
                    if (size == 1){
                        mImageLoader.displayImage(img_url.get(0), big_pic, new AnimListener());
                        image_one.setVisibility(View.VISIBLE);
                    } else {
                        image_one.setVisibility(View.GONE);
                        image_two.setVisibility(View.GONE);
                        image_three.setVisibility(View.GONE);
                        big_pic.setVisibility(View.GONE);
                        if (size == 2){
                            mImageLoader.displayImage(img_url.get(0), image_one, new AnimListener());
                            mImageLoader.displayImage(img_url.get(1), image_two, new AnimListener());
                            image_one.setVisibility(View.VISIBLE);
                            image_two.setVisibility(View.VISIBLE);
                        } else {
                            if (size == 3){
                                mImageLoader.displayImage(img_url.get(0), image_one, new AnimListener());
                                mImageLoader.displayImage(img_url.get(1), image_two, new AnimListener());
                                mImageLoader.displayImage(img_url.get(2), image_three, new AnimListener());
                                image_one.setVisibility(View.VISIBLE);
                                image_two.setVisibility(View.VISIBLE);
                                image_three.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            } else {
                image_one.setVisibility(View.GONE);
                image_two.setVisibility(View.GONE);
                image_three.setVisibility(View.GONE);
            }
            //使用正则表达式去除html标记
            Pattern pattern = Pattern.compile("<.+?>",Pattern.DOTALL);
            Matcher matcher = pattern.matcher(temp.getContent());
            String string = matcher.replaceAll("");
            response_content.setText(string);
        }
        response_content.setTextColor(context.getResources().getColor(R.color.black));
        response_image.setImageResource(R.mipmap.app_aligame);
        return view;
    }


    //图片加载监听
    private class AnimListener extends SimpleImageLoadingListener {

        final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            super.onLoadingComplete(imageUri, view, loadedImage);
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

}
