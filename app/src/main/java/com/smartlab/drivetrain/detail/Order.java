package com.smartlab.drivetrain.detail;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.base.CommonAdapter;
import com.smartlab.drivetrain.base.ViewHolder;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Orders;
import com.smartlab.drivetrain.model.PayServices;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.product.BuyProduct;
import com.smartlab.drivetrain.pullable.PullToRefreshLayout;
import com.smartlab.drivetrain.pullable.PullableListView;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/12/24.
 * 我的订单
 */
public class Order extends BaseActivity implements ActionCallBackListener<List<Orders>>,View.OnClickListener{


    private PullToRefreshLayout layout;
    private List<Orders> orders;
    private ForegroundColorSpan f;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving);
        initView();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        findViewById(R.id.detail_back).setOnClickListener(this);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText("订单详情");
        findViewById(R.id.location).setVisibility(View.GONE);
        layout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        PullableListView view = (PullableListView) findViewById(R.id.pull_list);
        orders = new ArrayList<>();
        view.setDivider(null);
        view.setDividerHeight((int) getResources().getDimension(R.dimen.login_edit_padding));
        loadingDialog = new LoadingDialog(this,R.drawable.loading);
        f = new ForegroundColorSpan(getResources().getColor(R.color.orange));
        adapter = new CommonAdapter<Orders>(this,orders,R.layout.order_layot) {
            @Override
            public void convert(final ViewHolder helper, final Orders item) {
                helper.setTextByResource(R.id.orderId, R.string.order_id,
                        item.getOrderId())
                .setTextByResource(R.id.schoolId,R.string.school,
                item.getName())
                        .setTextByResource(R.id.description, R.string.order_description,
                                item.getDescription());
                //
                String strs = item.getTime();
                if (!TextUtils.isEmpty(strs)){
                    if (strs.length() > 10){
                        strs = strs.substring(0,10);
                    }
                }
                helper.setTextByResource(R.id.orderTime, R.string.order_time,
                        strs);
                Button pay = helper.getView(R.id.pay);
                Button cancel = helper.getView(R.id.cancel);
                TextView state_tv = helper.getView(R.id.state_tv);
                TextView total_money = helper.getView(R.id.money);
                String money = String.valueOf(item.getMoney());
                SpannableStringBuilder str = new SpannableStringBuilder(mContext.getResources().getString(R.string.total_money,
                        money));

                str.setSpan(f, 5, 6 + money.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                total_money.setText(str);
                /**
                 * doing, // 正在做,待付款,wait
                 fail, // 失败
                 success, // 成功
                 */
                if (!TextUtils.isEmpty(item.getState())){
                    //  待支付订单
                    if (item.getState().equals("doing")){
                        cancel.setVisibility(View.VISIBLE);
                        pay.setVisibility(View.VISIBLE);
                        SpannableStringBuilder style = new SpannableStringBuilder(mContext.getResources().getString(R.string.state, "待支付"));
                        style.setSpan(f, 3, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        state_tv.setText(style);
                        pay.setText(getString(R.string.continue_pay));
                        cancel.setText(getString(R.string.cancel_order));
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadingDialog.show();
                                removeOrder(item);
                            }
                        });
                        pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                String payType = item.getProductDescription();
                                PayServices service = new PayServices();
                                service.setCmd("pay");
                                service.setAllpay(payType("allpay", payType));
                                service.setBase(payType("base", payType));
                                service.setInstallment1(payType("installment1", payType));
                                service.setInstallment2(payType("installment2", payType));
                                service.setInstallment3(payType("installment3", payType));
                                intent.putExtra("productId", item.getProductTimestamp());
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("service", service);
                                intent.putExtras(bundle);
                                intent.setClass(Order.this, BuyProduct.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                            }
                        });
                    }
                    //  失败订单
                    if (item.getState().equals("fail")){
                        SpannableStringBuilder style = new SpannableStringBuilder(mContext.getResources().getString(R.string.state, "失败"));
                        style.setSpan(f, 3, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        state_tv.setText(style);
                        pay.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                    }
                    //  成功订单
                    if (item.getState().equals("success")){
                        SpannableStringBuilder style = new SpannableStringBuilder(mContext.getResources().getString(R.string.state, "成功"));
                        style.setSpan(f, 3, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                        state_tv.setText(style);
                        pay.setVisibility(View.GONE);
                        cancel.setVisibility(View.GONE);
                    }
                }

            }
        };
       view.setAdapter(adapter);
        Util.runDelay(layout);
        layout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                User user = MainApplication.getUserInfo();
                appAction.getOrderByUserId("getOrderByUserId", user.getTimeStamp(), "20151001", DateTools.getTime(), Params.Order_List, Order.this);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                layout.loadmoreFinish(PullToRefreshLayout.DONE);
            }
        });
    }

    /**
     * 取消订单
     * @param order 对象
     */
    private void removeOrder(final Orders order) {
        appAction.removeOrderByOrderId("removeOrderByOrderId", order.getOrderId(), Params.RemoveOrder, new ActionCallBackListener<Void>() {
            @Override
            public void onSuccess(Void data) {
                showToast("取消成功");
                orders.remove(order);
                adapter.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                showToast(message);
                loadingDialog.dismiss();
            }
        });
    }

    private boolean payType(String str,String payType) {
        return payType.contains(str);
    }

    private CommonAdapter adapter;
    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(List<Orders> data) {
        if (data != null) {
            LogUtils.e(data.toString());
            orders.clear();
            orders.addAll(data);
            adapter.notifyDataSetChanged();
            layout.refreshFinish(PullToRefreshLayout.SUCCEED);
        } else {
            showToast("异常错误");
            layout.refreshFinish(PullToRefreshLayout.FAIL);
        }
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
        layout.refreshFinish(PullToRefreshLayout.FAIL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
                close(this);
                break;
            default:
                break;
        }
    }
}
