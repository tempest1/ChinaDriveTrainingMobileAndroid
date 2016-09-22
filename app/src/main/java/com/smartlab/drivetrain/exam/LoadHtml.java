package com.smartlab.drivetrain.exam;

import android.os.Bundle;

import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.product.WebDetail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by smartlab on 15/12/26.
 * 科目二,科三考试秘籍
 */
public class LoadHtml extends WebDetail {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        initView();
        initData();
    }

    @Override
    protected void initData() {
        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader buffer = null;
        try {
            String fileName = getIntent().getStringExtra("url");
            in = getAssets().open(fileName);
            reader = new InputStreamReader(in,"utf-8");
            buffer = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String result;
            while ((result = buffer.readLine()) != null) {
                builder.append(result);
            }
            webView.loadDataWithBaseURL(null,builder.toString(),"text/html","utf-8",null);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (buffer != null){
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    protected boolean onLoadSuccess(String url) {
        return false;
    }
}
