package com.smartlab.drivetrain.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smartlab.drivetrain.interfaces.AppAction;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.util.ActivityCollector;
import com.smartlab.drivetrain.util.Util;

import java.lang.ref.WeakReference;

/**
 * Created by smartlab on 15/10/21.
 * BaseActivity
 */
public abstract class BaseActivity extends FragmentActivity {
    public AppAction appAction;
    public Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication application = (MainApplication) this.getApplication();
        appAction = application.getAppAction();
        gson = getGson();


        ActivityCollector.addActivity(this);
        ActivityCollector.addActivitys(this);
    }

    public Gson getGson(){
        if (gson == null){
            gson = new Gson();
        }
        return gson;
    }
    protected abstract void initData();

    protected abstract void initView();

    public void doBack(View view){
        onBackPressed();
    }

    public void showToast(String str){
        if (!Util.isEmptyString(str)){
            Util.showToast(this,str,Toast.LENGTH_LONG);
        }
    }

    public void showLongToast(String str){
        if (!Util.isEmptyString(str)){
            Util.showToast(this,str,Toast.LENGTH_SHORT);
        }
    }
    public void close(Activity context){
        context.finish();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_from_right);
    }

    /**
     * Handler 静态内部类
     * @param <T>
     */
    public static class MyHandler<T> extends Handler {
        Resolve resolve;
        @Override
        public void handleMessage(Message msg) {
            T the = reference.get();
            if (the != null){
                resolve.resolveData(msg);
            }
        }
        private WeakReference<T> reference;

        public MyHandler(T reference,Resolve resolve){
            this.reference = new WeakReference< >(reference);
            this.resolve = resolve;

        }
        public interface Resolve{
            /**
             * 消息回调
             * @param msg Message
             */
            void resolveData(Message msg);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        ActivityCollector.removeActivitys(this);
    }
}
