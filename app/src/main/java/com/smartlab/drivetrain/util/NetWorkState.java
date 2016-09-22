package com.smartlab.drivetrain.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.smartlab.drivetrain.license.MainApplication;

import java.util.Locale;

/**
 * Created by smartlab on 15/9/15.
 * 获取当前网络状态
 */
public class NetWorkState {
    //-1表示网络错误，1表示WiFi连接，2表示wap移动网络连接，3表示cmnet类型3g网络连接
    static final int CMNET = 3;
    static final int CMWAP = 2;
    static final int WIFI = 1;
    static final int ERROR = -1;

    // 判断WiFi是否连接
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }///

    /**是否有网络连接*/
    public static boolean hasInternet() {
        boolean flag;
        if (((ConnectivityManager) MainApplication.context().getSystemService(
                Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null)
            flag = true;
        else
            flag = false;
        return flag;
    }
    // 判断移动网络是否连接
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 获取连接类型
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return ERROR;
    }

    /**
     * 判断当前是否有网络连接
     * @author Administrator
     * @param context 传递一个对象
     * @return boolean
     *
     * **/
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.i("NetWorkState", "Unavailabel");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.i("NetWorkState", "Availabel");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 网络类型判断
    /**
     * @author yu 获取当前的网络状态
     * @see -1：没有网络
     *         1：WIFI网络
     *         2：wap网络
     *         3：net网络
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        int netType = -1;

        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            Log.e("networkInfo", "networkInfo.getExtraInfo() is " + networkInfo.getExtraInfo());
            // using the default system Locale此方法获取默认语言环境的当前值的Java虚拟机实例。
            Locale defloc = Locale.getDefault();

            if (networkInfo.getExtraInfo().toLowerCase(defloc).equals("cmnet")) {
                netType = CMNET;
            } else {
                netType = CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = WIFI;
        }
        return netType;
    }
    /**
     * 网络已经连接，然后去判断是wifi连接还是GPRS连接
     * 设置一些自己的逻辑调用

     private void isNetworkAvailable(){

     State gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
     State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
     if(gprs == State.CONNECTED || gprs == State.CONNECTING){
     Toast.makeText(this, "wifi is open! gprs", Toast.LENGTH_SHORT).show();
     }
     //判断为wifi状态下才加载广告，如果是GPRS手机网络则不加载！
     if(wifi == State.CONNECTED || wifi == State.CONNECTING){
     Toast.makeText(this, "wifi is open! wifi", Toast.LENGTH_SHORT).show();
     //                loadAdmob();
     }

     }*/

    /**
     * 在wifi状态下 加载admob广告

     private void loadAdmob(){
     ll = (LinearLayout) findViewById(R.id.load_ads);
     ll.removeAllViews();
     adsView = new AdView(this, AdSize.BANNER, "a15194a1ac9505d");
     ll.addView(adsView);

     adsView.loadAd(new AdRequest());
     }*/
}
