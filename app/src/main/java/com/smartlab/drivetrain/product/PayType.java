package com.smartlab.drivetrain.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.HaveServices;
import com.smartlab.drivetrain.model.PayServices;
import com.smartlab.drivetrain.model.Product;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.HandyTextView;

import java.util.List;

/**
 * Created by smartlab on 15/12/11.
 * 选择支付方式 一口价或者是分期
 */
public class PayType extends BaseActivity implements View.OnClickListener {

    private PayServices service = new PayServices();    //服务
    private Product product;

    //  分期
    private Button pay_first;
    private Button pay_two;
    private Button pay_three;

    //  一口价
    private Button all_pay;
    private String already_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_type);
        initData();
        initView();
        initAllPay();
        initTranche();
    }

    //  分期付款页
    private void initTranche() {
        pay_first = (Button) findViewById(R.id.pay_first);
        pay_two = (Button) findViewById(R.id.pay_two);
        pay_three = (Button) findViewById(R.id.pay_three);

        pay_first.setOnClickListener(this);
        pay_two.setOnClickListener(this);
        pay_three.setOnClickListener(this);

        HandyTextView pay_first_text = (HandyTextView) findViewById(R.id.pay_first_text);
        HandyTextView pay_two_text = (HandyTextView) findViewById(R.id.pay_two_text);
        HandyTextView pay_three_text = (HandyTextView) findViewById(R.id.pay_three_text);

        //  分期一服务费 + 学时卡 + 照相费 + 科目一 + 科目二 + 科目三 + 科目四
        double Install1 = product.getInstallment1() + product.getHoursCardFee() + product.getPhotoexamFee()
                + product.getKm1() + product.getKm2() + product.getKm3() + product.getKm4();


        pay_first.setText(getResources().getString(R.string.pay, Install1));
        pay_two.setText(getResources().getString(R.string.pay, product.getInstallment2()));
        pay_three.setText(getResources().getString(R.string.pay, product.getInstallment3()));

        pay_first_text.setText(getResources().getString(R.string.first_pay, Install1));
        pay_two_text.setText(getResources().getString(R.string.second_pay, product.getInstallment3()));
        pay_three_text.setText(getResources().getString(R.string.third_pay, product.getInstallment3()));

    }
    //  一口价
    private void initAllPay() {
        all_pay = (Button) findViewById(R.id.all_pay);
        TextView all_pay_text = (TextView) findViewById(R.id.all_pay_text);

        all_pay.setOnClickListener(this);

        all_pay.setText(getResources().getString(R.string.pay, product.getAllPay()));
        all_pay_text.setText(getResources().getString(R.string.all_play, product.getAllPay()));
    }

    @Override
    protected void initData() {
        already_pay = getResources().getString(R.string.already_pay);
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                product = bundle.getParcelable("product");
                if (product != null) LogUtils.e(product.toString());
            }
        }
        // 获取用户购买的服务
        User user = MainApplication.getUserInfo();
        if (user != null) {
            String timestamp = user.getTimeStamp();
            appAction.getServiceByUserId("getServiceByUserId", timestamp, Params.Get_SERVICE, new ActionCallBackListener<List<HaveServices>>() {
                @Override
                public void onSuccess(List<HaveServices> data) {
                    if (data != null) {
                        if (data.size() > 0) {
                            LogUtils.e(data.toString());
                            //
                            for (HaveServices list : data) {
                                if (list.getProductTimestamp() != product.getTimestamp()){
                                    //  全为false
                                    if (!list.isBase() && !list.isKm1() && !list.isKm2() && !list.isKm3() && !list.isKm4() ){
                                        data.remove(list);
                                    } else {
                                        //
                                        showToast("抱歉，您已购买过了其他的驾校的服务。");
                                    }
                                }
                            }

                            LogUtils.e(data.toString());
                            if (data.size() > 0){
                                HaveServices list = data.get(0);
                                if (list.isBase() && list.isKm1() && list.isKm2() && list.isKm3() && list.isKm4()){
                                    showToast("您已购买该服务，不可重复购买");
                                    all_pay.setEnabled(false);
                                    pay_first.setEnabled(false);
                                    pay_two.setEnabled(false);
                                    pay_three.setEnabled(false);
                                } else {

                                    // 是否购买了分期三
                                    if (!list.isKm3() && !list.isKm4()){
                                        //  是否购买了分期二
                                        if (!list.isKm2()){
                                            // 是否购买了分期一
                                            if (!list.isBase() && !list.isKm1()){
                                                //没有购买分期一，就一定没有购买一口价
                                                all_pay.setEnabled(true);
                                                pay_first.setEnabled(true);
                                            } else {
                                                pay_first.setText(already_pay);
                                                pay_two.setEnabled(true);
                                            }
                                        } else {
                                            pay_first.setText(already_pay);
                                            pay_two.setText(already_pay);
                                            pay_three.setEnabled(true);
                                        }
                                    } else {
                                        pay_three.setText(already_pay);
                                        pay_two.setText(already_pay);
                                        pay_first.setText(already_pay);
                                    }
                                    // 全为true表示一口价
                                }
                            } else {
                                all_pay.setEnabled(true);
                                pay_first.setEnabled(true);
                            }

                        } else {
                            all_pay.setEnabled(true);
                            pay_first.setEnabled(true);
                        }
                    }
                }

                @Override
                public void onFailure(String errorEvent, String message) {
                    // 获取用户服务失败

                }
            });
        } else {
            showToast("请登录");
        }
    }

    @Override
    protected void initView() {
        RadioGroup radio_pay = (RadioGroup) findViewById(R.id.radio_pay);
        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setDisplayedChild(0);
        radio_pay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.allplay:
                        viewFlipper.setDisplayedChild(0);
                        break;
                    case R.id.period:
                        viewFlipper.setDisplayedChild(1);
                        break;
                }
            }
        });
        findViewById(R.id.detail_back).setOnClickListener(this);
        TextView detail_tilte = (TextView) findViewById(R.id.detail_title);
        detail_tilte.setText("套餐类型");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_pay:
                service.setCmd("pay");
                service.setAllpay(true);
                service.setBase(false);
                service.setInstallment1(false);
                service.setInstallment2(false);
                service.setInstallment3(false);
                productPay(service);
                break;
            case R.id.detail_back:
                close(this);
                break;
            case R.id.pay_first:
                service.setCmd("pay");
                service.setAllpay(false);
                service.setBase(true);
                service.setInstallment1(true);
                service.setInstallment2(false);
                service.setInstallment3(false);
                productPay(service);
                break;
            case R.id.pay_two:
                service.setCmd("pay");
                service.setAllpay(false);
                service.setBase(false);
                service.setInstallment1(false);
                service.setInstallment2(true);
                service.setInstallment3(false);
                productPay(service);
                break;
            case R.id.pay_three:
                service.setCmd("pay");
                service.setAllpay(false);
                service.setBase(false);
                service.setInstallment1(false);
                service.setInstallment2(false);
                service.setInstallment3(true);
                productPay(service);
                break;
            default:
                break;
        }
    }

    private void productPay(PayServices service) {
        if (product != null) {
            Intent intent = new Intent();
            intent.putExtra("productId", String.valueOf(product.getTimestamp()));
            Bundle bundle = new Bundle();
            bundle.putParcelable("service", service);
            intent.putExtras(bundle);
            intent.setClass(this, BuyProduct.class);
            startActivity(intent);
            overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        }
    }

}
