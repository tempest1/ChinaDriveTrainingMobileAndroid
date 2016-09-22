package com.smartlab.drivetrain.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainContent;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.PayServices;
import com.smartlab.drivetrain.util.LogUtils;

/**
 * Created by smartlab on 15/12/23.
 * 产品购买
 */
public class BuyProduct extends WebDetail implements ActionCallBackListener<String> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        initView();
        initData();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null){
            timestamp = intent.getStringExtra("productId");
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            PayServices services = bundle.getParcelable("service");
            if (services != null) {

                appAction.pay(services.getCmd(),timestamp,services.isAllpay(),services.isBase(),services.isInstallment1(),services.isInstallment2(),services.isInstallment3(), Params.ALI_PLAY, this);
                loading.setVisibility(View.VISIBLE);
                ss_htmlprogessbar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected boolean onLoadSuccess(String url) {
        if (url != null){
            String target_url = "/user/userCenter/userCenter.html";//
            if (url.contains(target_url)){
                showLongToast("支付成功");
                Intent intent = new Intent(this, MainContent.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
            LogUtils.e(url);//  http://139.129.96.134:8080/user/userCenter/userCenter.html
        }
        return false;
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(String data) {
        if (data != null){
            LogUtils.e(data);
            webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
        }
        loading.setVisibility(View.GONE);
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        showToast(message);
        loading.setVisibility(View.GONE);
    }
}
