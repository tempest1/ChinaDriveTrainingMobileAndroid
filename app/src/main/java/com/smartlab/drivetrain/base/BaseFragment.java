package com.smartlab.drivetrain.base;


import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.smartlab.drivetrain.interfaces.AppAction;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.lang.ref.WeakReference;

/**
 * Created by smartlab on 15/11/16.
 * Fragment 基类
 */
public abstract class BaseFragment extends Fragment{
    protected AppAction appAction;
    public Gson gson;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication application = (MainApplication) this.getActivity().getApplication();
        appAction = application.getAppAction();
        gson = new Gson();
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化基本数据
     */
    protected abstract void initData();

    /**
     * 普通消息提示
     * @param str   消息内容
     */
    public void showToast(String str){
            if (!Util.isEmptyString(str)){
                Toast.makeText(getActivity(),str,Toast.LENGTH_SHORT).show();
            } else {
                LogUtils.i("str is null");
            }
    }


    /**
     *  Fragment 内部静态类
     * @param <T> 任意Fragment对象
     */
    public static class MyHandler<T> extends android.os.Handler {
        private Resolve resolve;

        @Override
        public void handleMessage(Message msg) {
            T the = reference.get();
            if (the != null){
                resolve.resolveData(msg);
            }
        }

        private WeakReference<T> reference;

        public MyHandler(T reference, Resolve resolve) {
            this.reference = new WeakReference<T>(reference);
            this.resolve = resolve;
        }

        public interface Resolve {
            void resolveData(Message msg);
        }
    }
    public void close(Activity activity){
        if (activity != null){
            activity.finish();
            activity.overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        }
    }
}
