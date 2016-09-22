package com.smartlab.drivetrain.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.smartlab.drivetrain.adapter.UserOption;
import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.detail.Consult;
import com.smartlab.drivetrain.detail.MyQrCode;
import com.smartlab.drivetrain.detail.Order;
import com.smartlab.drivetrain.detail.UserDetail;
import com.smartlab.drivetrain.evaluate.Complain;
import com.smartlab.drivetrain.evaluate.Evaluate;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.About;
import com.smartlab.drivetrain.license.Login;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.LoadingDialog;

/**
 * Created by smartlab on 15/9/16.
 * 用户中心
 */
public class UserCenter extends BaseFragment implements ActionCallBackListener<User>{
    public static boolean alreadyLogin = false;
    private View isLogin;
    private View login_or_register;
    private View view;
    private TextView userRealName;
    private TextView phone;
    private String [] stringList = {"我的二维码","我的订单","我的评价","我的咨询","我的投诉","我的钱包","设置","关于"};
    private Class [] activities = {MyQrCode.class,Order.class,Evaluate.class,Consult.class,Complain.class,null,null,About.class};
    private LoadingDialog loadingDialog;
    private View.OnClickListener orLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.login_or_register:
                    goMore(Login.class);
                break;
                case R.id.isLogin:
                    //  查看用户详情
                    goMore(UserDetail.class);
                    break;
            }
        }
    };

    private void goMore(Class<?> Class) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), Class);
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }
    /**
     * Receive the result from a previous call to startActivityForResult(Intent, int).
     **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER && resultCode == Activity.RESULT_OK){
            if (alreadyLogin) {
                //if login successful
                LogUtils.i("登录成功");
                appAction.getUserDetail("getUserInfo", Params.getUser, this);
            }
            setVisibile();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            detail(stringList[position],activities[position]);
        }
    };

    private void detail(String message,Class<?> Class) {
        if (Class != null){
            User user = MainApplication.getUserInfo();
            if (user != null) {
                LogUtils.e(user.toString());
                Intent intent = new Intent();
                intent.setClass(getActivity(),Class);
                intent.putExtra("title", message);
                startActivity(intent);
            } else {
                showToast("请先登录");
            }
        }
    }

    private void setVisibile() {
        if (isLogin.getVisibility() == View.GONE) {
            login_or_register.setVisibility(View.GONE);
            isLogin.setVisibility(View.VISIBLE);
        }else{
            isLogin.setVisibility(View.GONE);
            login_or_register.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null){
                parent.removeView(view);
            }
            return view;
        }else{
            view = inflater.inflate(R.layout.user_ceneter, container, false);
        }
        initView();
        initData();
        return view;
    }

    @Override
    protected void initView() {
        isLogin = view.findViewById(R.id.isLogin);// 这里是显示用登录之后的信息的，默认情况下是隐藏的
        login_or_register = view.findViewById(R.id.login_or_register);// 点击登录
        ListView listOption = (ListView) view.findViewById(R.id.user_center);
        listOption.setAdapter(new UserOption(stringList, this.getActivity()));
        listOption.setOnItemClickListener(itemClick);
        login_or_register.setOnClickListener(orLogin);
        TextView detail_title = (TextView) view.findViewById(R.id.detail_title);
        detail_title.setText("我的");
        isLogin.setOnClickListener(orLogin);
        userRealName = (TextView) view.findViewById(R.id.realName);
        phone = (TextView) view.findViewById(R.id.phone);
        loadingDialog = new LoadingDialog(getActivity(),R.drawable.loading);
    }

    /**
     * 初始化基本数据
     */
    @Override
    protected void initData() {
        //  若用户再上一个界面已经登录了则这里不登录
        if (MainApplication.isLogin()) {
            return;
        }
        SharedPreferences mPre = MainApplication.getPreferences();
        boolean autoLogin = mPre.getBoolean("autoLogin",false);
        if (autoLogin){
            String phone = mPre.getString("phone","");
            String password = mPre.getString("password","");
            loadingDialog.show();
            appAction.login("login", phone, password, Params.LOGIN, listener);
        }
        setUserInfo();
    }


    private void setUserInfo() {
        User info = MainApplication.getUserInfo();
        String name = "欢迎您";
        if (info != null) {
            if (info.getRealName() != null) {
                 name = name + "，"+ info.getRealName();
            }

            userRealName.setText(name);
            phone.setText(info.getPhone());

            //  隐藏登录入口
            login_or_register.setVisibility(View.GONE);
            isLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);    //
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * 自动登录监听
     */
    private ActionCallBackListener<Void> listener = new ActionCallBackListener<Void>() {
        @Override
        public void onSuccess(Void data) {
            loadingDialog.dismiss();
            showToast("自动登录成功");
            setVisibile();
            loadingDialog.show();
            MainApplication.setIsLogin(true);
            appAction.getUserDetail("getUserInfo", Params.getUser, UserCenter.this);
        }

        @Override
        public void onFailure(String errorEvent, String message) {
            loadingDialog.dismiss();
            showToast(message);
        }
    };
    /**
     * 处理成功
     *  获取用户详情
     * @param data 返回数据
     */
    @Override
    public void onSuccess(User data) {
        if (data != null) {
            LogUtils.e(data.toString());
            MainApplication.setUserInfo(data);
            setUserInfo();
        }
        loadingDialog.dismiss();
    }

    /**
     * 请求失败
     *  用户详情请求失败
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        if (TextUtils.isEmpty(errorEvent) && TextUtils.isEmpty(message)){
            showToast("未获取到您的详细信息");
        } else {
            setVisibile();
            showToast(message);
            MainApplication.setBeanDefinitionReaderSid(null);
            MainApplication.setUserInfo(null);
            MainApplication.setIsLogin(false);
        }
        loadingDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        LogUtils.e("UserCenter onHidden is call");
        if (!hidden) {
            if (MainApplication.isLogin()) {
                setUserInfo();
            }
        }
    }
}
