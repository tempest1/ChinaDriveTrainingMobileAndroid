package com.smartlab.drivetrain.detail;

import android.os.Bundle;

import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.product.WebDetail;

/**
 * Created by smartlab on 15/12/23.
 * 预约考试
 */
public class LoadUrl extends WebDetail {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        initView();
        loadUrl();
    }

    @Override
    protected boolean onLoadSuccess(String url) {
        return false;
    }

    private void loadUrl() {
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }

}
