package com.smartlab.drivetrain.map;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.detail.TrainingShow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/12/18.
 * 基础地址
 */
public class Navigation extends BaseActivity implements BaseActivity.MyHandler.Resolve{

    private BNRoutePlanNode mBNRoutePlanNode;
    private static final int MSG_SHOW = 1;
    private static final int MSG_HIDE = 2;
    private static final int MSG_RESET_NODE = 3;
    private MyHandler<Navigation> hd = new MyHandler<>(this,this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TrainingShow.activityList.add(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {}
        View view = BNRouteGuideManager.getInstance().onCreate(this, new BNRouteGuideManager.OnNavigationListener() {

            @Override
            public void onNaviGuideEnd() {
                finish();
            }

            @Override
            public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {
                Log.e("notifyOtherAction",
                        "actionType:" + actionType + "arg1:" + arg1 + "arg2:" + arg2 + "obj:" + obj.toString());
            }

        });

        if ( view != null ) {
            setContentView(view);
        }

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mBNRoutePlanNode = (BNRoutePlanNode) bundle.getSerializable(TrainingShow.ROUTE_PLAN_NODE);

            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }
    @Override
    protected void onResume() {
        BNRouteGuideManager.getInstance().onResume();
        super.onResume();
        if(hd != null){
            hd.sendEmptyMessageAtTime(MSG_SHOW,2000);
        }
    }

    protected void onPause() {
        BNRouteGuideManager.getInstance().onPause();
        super.onPause();
    };

    @Override
    protected void onDestroy() {
        BNRouteGuideManager.getInstance().onDestroy();
        TrainingShow.activityList.remove(this);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        BNRouteGuideManager.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        BNRouteGuideManager.getInstance().onBackPressed(false);
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        BNRouteGuideManager.getInstance().onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    };
    private void addCustomizedLayerItems() {
        List<BNRouteGuideManager.CustomizedLayerItem> items = new ArrayList<BNRouteGuideManager.CustomizedLayerItem>();
        BNRouteGuideManager.CustomizedLayerItem item1 = null;
        if (mBNRoutePlanNode != null) {
            item1 = new BNRouteGuideManager.CustomizedLayerItem(mBNRoutePlanNode.getLongitude(), mBNRoutePlanNode.getLatitude(),
                    mBNRoutePlanNode.getCoordinateType(), getResources().getDrawable(R.mipmap.app_iconss), BNRouteGuideManager.CustomizedLayerItem.ALIGN_CENTER);
            items.add(item1);
            BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
        }
        BNRouteGuideManager.getInstance().showCustomizedLayer(true);
    }
    /**
     * 消息回调
     *
     * @param msg Message
     */
    @Override
    public void resolveData(Message msg) {
        if ( msg.what == MSG_SHOW ) {
//            addCustomizedLayerItems();
        } else if (msg.what == MSG_HIDE) {
            BNRouteGuideManager.getInstance().showCustomizedLayer(false);
        } else if (msg.what == MSG_RESET_NODE) {
            BNRouteGuideManager.getInstance().resetEndNodeInNavi(
                    new BNRoutePlanNode(116.21142, 40.85087, "百度大厦11", null, BNRoutePlanNode.CoordinateType.GCJ02));
        }
    }
}
