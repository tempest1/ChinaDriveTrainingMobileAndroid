package com.smartlab.drivetrain.product;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.license.R;

/**
 * Created by smartlab on 15/12/10.
 * 立即报名
 */
public abstract class WebDetail extends BaseActivity implements View.OnClickListener{

    protected String timestamp;
    protected WebView webView;
    protected ProgressBar ss_htmlprogessbar;
    protected ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.detail_back).setOnClickListener(this);
        loading = (ImageView) findViewById(R.id.loading);
        ss_htmlprogessbar = (ProgressBar) findViewById(R.id.ss_htmlprogessbar);
        webView = (WebView)findViewById(R.id.pay_web);
        webView.setMinimumWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDefaultTextEncodingName("utf-8");
//        settings.setSupportZoom(true);// 用于设置webview放大
        webView.setWebChromeClient(new MyChromClient());
        webView.setBackgroundResource(R.color.transparent);
        webView.setWebViewClient(new MyWebViewClient());
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
                close(this);
                break;
            default:break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            ss_htmlprogessbar.setVisibility(View.VISIBLE);
            if (onLoadSuccess(url)) return true;
            view.loadUrl(url);
            return true;
//            return super.shouldOverrideUrlLoading(view, url);
        }



        @Override
        public void onPageFinished(WebView view, String url) {
            ss_htmlprogessbar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            ss_htmlprogessbar.setVisibility(View.VISIBLE);
            if (onLoadSuccess(url)) return;
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            ss_htmlprogessbar.setVisibility(View.GONE);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    protected abstract boolean onLoadSuccess(String url);

    private class MyChromClient extends WebChromeClient {
        /**
         * Tell the host application the current progress of loading a page.
         * @param view        The WebView that initiated the callback.
         * @param newProgress Current page loading progress, represented by
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress <= 100){
                ss_htmlprogessbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }
}
