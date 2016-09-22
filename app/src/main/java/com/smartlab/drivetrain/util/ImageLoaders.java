package com.smartlab.drivetrain.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by NK on 2015/12/23.
 */
public class ImageLoaders {
    public Bitmap getBitmapFormURL(String urlString )  {
        Bitmap bitmap;
        InputStream is;
        try {
            URL url=new URL(urlString);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(connection.getInputStream());
            bitmap= BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public void showImageBuAsyncTask(ImageView imageView,String url)
    {
        new NewsAsyncTask(imageView,url).execute(url);
    }
    private class NewsAsyncTask extends AsyncTask<String,Void ,Bitmap> {

        private ImageView mImageView;
        private String mUrl;
        public NewsAsyncTask(ImageView imageView,String url){
            mImageView=imageView;
            mUrl=url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFormURL(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView.getTag().equals(mUrl))
            {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }
}
