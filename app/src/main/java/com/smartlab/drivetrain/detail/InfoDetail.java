package com.smartlab.drivetrain.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Info;
import com.smartlab.drivetrain.model.InfoContent;
import com.smartlab.drivetrain.service.DetailService;
import com.smartlab.drivetrain.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/28.
 * 新闻详情页
 *
 */
public class InfoDetail extends BaseActivity implements ActionCallBackListener<List<InfoContent>> {

    private String news_title;
    private Long news_date;
    private WebView webView;
    public static final String CMD_INFO_DETAIL = "userQueryDetailsInfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        initData();
        initView();

    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        Info info = bundle.getParcelable("info");
        if (info != null) {
            LogUtils.e("info" + info);
            news_title = info.getTitle();
            news_date = info.getTimeStamp();
        }
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initView() {
        webView = (WebView)findViewById(R.id.wb_details);
        webView.setMinimumWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        TextView title = (TextView) findViewById(R.id.news_title);
        title.setText(news_title) ;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//设置可以运行JS脚本
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(false);// 用于设置webview放大
        settings.setBuiltInZoomControls(false);
        webView.setBackgroundResource(R.color.transparent);
        // 添加js交互接口类，并起别名 imagelistner
        webView.addJavascriptInterface(new JavascriptInterface(getApplicationContext()), "imagelistner");
        webView.setWebViewClient(new MyWebViewClient());
        appAction.loadInfoDetail(CMD_INFO_DETAIL,String.valueOf(news_date), Params.INFO_DETAIL,this);
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<InfoContent> data) {
        String content = DetailService.getNewsDetails(data.get(0).getContent(), news_title);
        LogUtils.i(content);
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {

    }

    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
//            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
//            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
//            progressBar.setVisibility(View.GONE);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\");"
                + "var imgurl=''; " + "for(var i=0;i<objs.length;i++)  " + "{"
                + "imgurl+=objs[i].src+',';"
                + "    objs[i].onclick=function()  " + "    {  "
                + "        window.imagelistner.openImage(imgurl);  "
                + "    }  " + "}" + "})()");
    }
    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        public void openImage(String img) {

            //
            String[] imgs = img.split(",");
            ArrayList<String> imgsUrl = new ArrayList<String>();
            for (String s : imgs) {
                imgsUrl.add(s);
                LogUtils.i(s);
            }
//            Intent intent = new Intent();
//            intent.putStringArrayListExtra("infos", imgsUrl);
//            intent.setClass(context, ImageShowActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }

    /*
	 * 返回
	 */
    public void doBack(View view) {
        onBackPressed();
    }
}
