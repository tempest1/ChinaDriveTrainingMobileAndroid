package com.smartlab.drivetrain.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import org.xml.sax.XMLReader;

import java.io.File;

/**
 * Created by smartlab on 16/1/13.
 * TagHandler
 */
public class MyTagHandler implements Html.TagHandler {
    private Context context;
    public MyTagHandler(Context context){
        this.context = context;
    }
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (!TextUtils.isEmpty(tag)){
            LogUtils.e(tag);
        }
        if (tag.toLowerCase().equals("img")){
            // 获取长度
            int len = output.length();
            // 获取图片地址
            ImageSpan[] images = output.getSpans(len-1, len, ImageSpan.class);
            String imgURL = images[0].getSource();

            // 使图片可点击并监听点击事件
            output.setSpan(new ImageClick(context, imgURL), len-1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
    private class ImageClick extends ClickableSpan {

        private String url;
        private Context context;

        public ImageClick(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            //  对文件名采用MD5加密
            String fileName = Util.makeMD5(url.substring(url.lastIndexOf("/") + 1, url.length()));
            // 图片命名方式
            final File cacheFile = new File(Params.IMAGE_CACHE, fileName);

            if (cacheFile.exists()) {
                // 处理点击事件，开启一个新的activity来处理显示图片
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(cacheFile), "image/*");
                context.startActivity(intent);
            }
        }

    }
}
