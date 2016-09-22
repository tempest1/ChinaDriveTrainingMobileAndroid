package com.smartlab.drivetrain.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartlab.drivetrain.adapter.MyGridAdapter;
import com.smartlab.drivetrain.adapter.MyInfoPaferAdapter;
import com.smartlab.drivetrain.appointment.AppointCoach;
import com.smartlab.drivetrain.appointment.AppointmentActivity;
import com.smartlab.drivetrain.base.BaseFragment;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.detail.Alliance;
import com.smartlab.drivetrain.detail.Answers;
import com.smartlab.drivetrain.detail.CoachDetail;
import com.smartlab.drivetrain.detail.Driving;
import com.smartlab.drivetrain.detail.LoadUrl;
import com.smartlab.drivetrain.detail.Schedule;
import com.smartlab.drivetrain.detail.UserDetail;
import com.smartlab.drivetrain.detail.VipExclusive;
import com.smartlab.drivetrain.exam.Esoterica;
import com.smartlab.drivetrain.exam.WhichSubject;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.Login;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.map.KaoChangLocation;
import com.smartlab.drivetrain.map.SchoolLocation;
import com.smartlab.drivetrain.model.AdTrain;
import com.smartlab.drivetrain.model.Info;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.model.networks;
import com.smartlab.drivetrain.util.DateTools;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.view.MyGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by smartlab on 15/9/16.
 * 首页碎片
 */
public class HomePage extends BaseFragment implements ViewPager.OnPageChangeListener,BaseFragment.MyHandler.Resolve ,ActionCallBackListener<List<Info>> {
    private View view;
    private View viewnetwork;
    private ViewPager adv;//广告
    private ImageButton user_icon;
    private List<View> dots;
    private List<View> dotList;
    private int currentItem = 0; // 当前图片的索引号
    private TextView user_text;
    private TextView network;
    private LinearLayout photo_click;
    // 定义的五个指示点
    private View dot0;
    private View dot1;
    private View dot2;
    private View dot3;
    private View dot4;


    private ScrollTask scrollTask;
    private Handler mhandler;
//    private goUserCenter go;

    private boolean mOnTouch;


    // 异步加载图片yu
    private ImageLoader mImageLoader;
    private ScheduledExecutorService scheduledExecutorService;

    private BaseFragment.MyHandler handler = new BaseFragment.MyHandler<>(this,this);
    private AdapterView.OnItemClickListener itemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//            Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
            jumpNew(position);
        }
    };



    private void jumpNew(int position) {
        switch (position) {
            case 0:
                //驾校推荐
                Detail(Driving.class,null);
                break;
            case 1:
                //教练推荐
                Detail(CoachDetail.class,null);
                break;
            case 2:
                //我要报名
                Detail(Driving.class,null);
                break;
            case 3:
                //预约考试
                Detail(LoadUrl.class, Params.Appoint_Exam);
                break;
            case 4:
                //预约训练
                Detail(AppointmentActivity.class,null);
                break;
            case 5:
                //考试进度
                Detail(Schedule.class,null);
                break;
            case 6:
                //学时卡信息
                Detail(LoadUrl.class,Params.Query_Time_Card);
                break;
            case 7:
                //在线模拟
                Detail(WhichSubject.class,null);
                break;
            case 8:
                //考试秘籍
                Detail(Esoterica.class,null);
                break;
            case 9:
                //诚信联盟
                Detail(Alliance.class,null);
                break;
            case 10:
                //vip专享
                Detail(VipExclusive.class,null);
                break;
            case 11:
                //问题答疑
                Detail(Answers.class,null);
                break;
            default:
                break;
        }
    }


    //详情页面
    private void Detail(Class<?> Class,String url) {
        Intent intent = new Intent(getActivity(), Class);
        if (url != null) intent.putExtra("url",url);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.search_coach:
                    User user = MainApplication.getUserInfo();
                    if (user == null){
                        showToast(getString(R.string.please_login));
                        return;
                    }
                    //找教练
                    Detail(AppointCoach.class,null);
                    break;
                case  R.id.search_school:
                    //找驾校
                    Detail(SchoolLocation.class,null);
                    break;
                case  R.id.photo_click:
                    //进入用户中心
                    goUser();
//                    go.goUse();
                    break;
                case R.id.search_kao:
                    //找考场
                    Detail(KaoChangLocation.class,null);
                    break;
                default:break;
            }
        }
    };

    /**
     * 进入用户中心,实现以下功能:1.判断用户是否登录,若没有登录,跳转登录界面,若用登录了则进入用户信息界面,
     */
    private void goUser() {

        /**
         * isLogin为true表示已经登录
         * 为false表示没有登录
         */
        if (!MainApplication.isLogin()) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), Login.class);
            startActivityForResult(intent, Activity.RESULT_FIRST_USER);
            getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        } else {
            String sid = MainApplication.getBeanDefinitionReaderSid();
            if (!TextUtils.isEmpty(sid)) {
                User info = MainApplication.getUserInfo();
                if (info != null) {
                    Intent intent = new Intent(getActivity(), UserDetail.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                } else {
                    appAction.getUserDetail("getUserInfo", Params.getUser, userinfoListener);
                }
            }
        }
    }
    /***
     * 获取用户资料
     */
    private ActionCallBackListener<User> userinfoListener = new ActionCallBackListener<User>() {
        @Override
        public void onSuccess(User data) {
            if (data != null) {
                MainApplication.setUserInfo(data);
                setName(data);
            }

        }

        @Override
        public void onFailure(String errorEvent, String message) {
            MainApplication.setBeanDefinitionReaderSid(null);
            MainApplication.setIsLogin(false);
            LogUtils.e(message);
        }
    };
    private List<AdTrain> adList;
    private List<ImageView> imageViews;
    private DisplayImageOptions options;

    @Override
    public void resolveData(Message msg) {
        adv.setCurrentItem(currentItem);
    }

//    public interface  goUserCenter{
//        void goUse();
//    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null){
                parent.removeView(view);
            }
            return view;
        } else {
            view = inflater.inflate(R.layout.home_page, container, false);
        }
        initView();
        return view;
    }

    Handler mhandlers=new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==0)
            {
                if(networks.networksss==10)
                {
                    network.setText("网络连接超时！");
                    MainApplication.isWeb=0;
                    viewnetwork.setVisibility(View.VISIBLE);

                }
                else if(networks.networksss==30)
                {
                    MainApplication.isWeb=30;
                    viewnetwork.setVisibility(View.GONE);
                }
                else if(networks.networksss==0)
                {
                    MainApplication.isWeb=0;
                    network.setText("网络连接不可用！");
                    viewnetwork.setVisibility(View.VISIBLE);
                }



            }
            super.handleMessage(msg);
        }
    };
    class myThread implements Runnable {
        public void run() {
//            while (!Thread.currentThread().isInterrupted()) {
            handler.postDelayed( this,1000);
            Message message = new Message();
            if(networks.networksss==30||networks.networksss==0||networks.networksss==10)
            {
                message.what =0 ;
            }

            mhandlers.sendMessage(message);
        }
//        }
    }

    /**
     * 当fragment与activity建立关系时调用
     * @param activity Activity对象
     */
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            go = (goUserCenter)activity;
//        } catch (Exception e) {
//            throw new ClassCastException(activity.toString()+"must implement tabShoeHideListener");
//        }
//    }

    List<Info> infosList;
    @Override
    protected void initView() {


        // infosList = new ArrayList<>();
        //  appAction.loadInfo("userQueryAllInfo", 1, 10, "20150101", DateTools.getTime(), "news", Params.INFO_URL, this);

        scrollTask=new ScrollTask();
        mhandler=new Handler();
        Button searchCoach = (Button) view.findViewById(R.id.search_coach);
        Button searchSchool = (Button) view.findViewById(R.id.search_school);
        Button search_kao = (Button) view.findViewById(R.id.search_kao);
        user_text = (TextView) view.findViewById(R.id.user_text);
        photo_click = (LinearLayout) view.findViewById(R.id.photo_click );
        photo_click.setOnClickListener(onClick);
        viewnetwork=view.findViewById(R.id.networklayout);
        network= (TextView) view.findViewById(R.id.network);
        searchCoach.setOnClickListener(onClick);
        searchSchool.setOnClickListener(onClick);
        search_kao.setOnClickListener(onClick);
        MyGridView gridView = (MyGridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new MyGridAdapter(getActivity()));
        gridView.setOnItemClickListener(itemClick);
        mImageLoader = MainApplication.getImageLoader();
        options = MainApplication.getDisplayImageOptions();
        // 广告

//        adList = BannerData.getBannerAd();

        imageViews = new ArrayList<>();
        // 点
        dots = new ArrayList<View>();
        dotList = new ArrayList<View>();
        dot0 = view.findViewById(R.id.v_dot0);
        dot1 = view.findViewById(R.id.v_dot1);
        dot2 = view.findViewById(R.id.v_dot2);
        dot3 = view.findViewById(R.id.v_dot3);
        dot4 = view.findViewById(R.id.v_dot4);
//        dot5 = view.findViewById(R.id.v_dot5);
//        dot6 = view.findViewById(R.id.v_dot6);
//        dot7 = view.findViewById(R.id.v_dot7);
//        dot8 = view.findViewById(R.id.v_dot8);
//        dot9 = view.findViewById(R.id.v_dot9);



        adv = (ViewPager) view.findViewById(R.id.adv_pager);
        new Thread(new myThread()).start();



        adv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                mOnTouch = true;
                if (action == MotionEvent.ACTION_DOWN) {
                    mOnTouch = true;
                    Log.e("sys", "mOnTouch=true");
                } else if (action == MotionEvent.ACTION_UP) {
                    mOnTouch = false;
                    Log.e("sys", "mOnTouch=false");
                }
                return false;
            }
        });


        if (MainApplication.getUserInfo()!=null)
            setName(MainApplication.getUserInfo());
    }

    @Override
    protected void initData() {

    }


    /*
    * info
     */
    private void addView() {
        // 动态添加图片和下面指示的圆点
        // 初始化图片资源

        Log.e("sys", "addView()");



        if(infosList!=null) {


            for(int i=0;i<infosList.size();i++)
            {
                if((i+1)%5==1)
                    dots.add(dot0);
                if((i+1)%5==2) {
                    dots.add(dot1);
                    dot1.setVisibility(View.VISIBLE);
                }
                if((i+1)%5==3) {
                    dots.add(dot2);
                    dot2.setVisibility(View.VISIBLE);
                }
                if((i+1)%5==4) {
                    dots.add(dot3);
                    dot3.setVisibility(View.VISIBLE);
                }
                if((i+1)%5==0) {
                    dots.add(dot4);
                    dot4.setVisibility(View.VISIBLE);
                }

            }

            Log.e("sys","infosList.size()="+infosList.size()+"");
            for (int i = 0; i < infosList.size(); i++) {
                ImageView imageView = new ImageView(getActivity());
                // 异步加载图片
                mImageLoader.displayImage(infosList.get(i).getImage(), imageView,
                        options);
//                Log.e("sys", "adList" + adList.get(i).getImgUrl());
                LogUtils.e("infosList.get(i).getImage()"+infosList.get(i).getImage());

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setMaxHeight(100);
                imageViews.add(imageView);
                dots.get(i).setVisibility(View.VISIBLE);
                dotList.add(dots.get(i));
            }
        }
    }

    private void startAd() {

        if(infosList!=null) {
            Log.e("sys","startAd()");
             Log.e("sys","初始化显示");
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            // 当Activity显示出来后，每两秒切换一次图片显示
            scheduledExecutorService.scheduleAtFixedRate(scrollTask, 3, 3,
                    TimeUnit.SECONDS);
            handler.postDelayed(scrollTask, 5000);

        }

    }

    private class ScrollTask implements Runnable {

        public boolean falg=true;
        private int i=0;
        @Override
        public void run() {

            synchronized (adv) {

              //  finsh();
                if(i<5)
                {
                    falg=true;
                }
                i++;
               // Log.e("sys", "换图片"+falg);
                int count = 0;
                while (!falg) {
                    count = 0;
                    //这段逻辑用于用户手动滑动时，停止自动滚动
                    while (count < 30) {
                        count++;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (mOnTouch) {// 用戶手动滑动时，停止自动滚动
                            count = 0;
                        }
                       // falg=false;
                    }
                }

                finsh();
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget();

                }
        }


        public void stop()
        {
            falg=false;
        }

        public void restart()
        {
            falg=true;
        }

        public void finsh()
        {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void onStart() {
        super.onStart();

        if(imageViews.size()==0) {
            appAction.loadAdvertisement("userQueryAllInfo", 1, 10, "ad", Params.Advertisement, this);
        }
        LogUtils.e("onStart()");

        if(MainApplication.getUserInfo()==null)
        {
            user_text.setText(getString(R.string.Login));
        }
        if(infosList!=null) {
            startAd();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("sys", "onStop()");
        // 当Activity不可见的时候停止切换
        StopViewPager();
    }

    public void StopViewPager()
    {
        if(infosList!=null) {
            if(scheduledExecutorService!=null) {
                Log.e("sys","scheduledExecutorService.shutdown()");
                scheduledExecutorService.shutdown();
            }
        }
    }


    private int oldPosition = 0;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
       currentItem = position;
        dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
        dots.get(position).setBackgroundResource(R.drawable.dot_focused);
        oldPosition = position;
        //scrollTask.restart();
        //handler.postDelayed(scrollTask, 2000);

       // StopViewPager();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==1)
        {
            Log.e("sys", "skip");
            scrollTask.stop();


        } else if (state==2) {
            Log.e("sys", "stop");
            scrollTask.restart();

           //
        }else if (state==0) {
            Log.e("sys", "stop");
            scrollTask.restart();
            // scrollTask.restart();

            //
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSuccess(List<Info> data) {
        Log.e("sys", "onSuccess");

        if(data!=null)
        {
           Log.e("sys",data.get(0).getTitle());
        }
        else
        {
            Log.e("sys","data=null");
        }



        infosList=data;
        Log.e("sys", infosList.toString());
            try {
                for(int i=0;i<infosList.size();i++)
                {
                    Log.e("sys",infosList.get(i).getTitle());

                    if (infosList.get(i).getImage()==null)
                    {
                        Log.e("sys",infosList.get(i).getTitle());
                        infosList.remove(i);
                        i--;
                    }

                }
            }
            catch (Exception e){
                Log.e("sys", "刷新失败");
            }



        addView();
        adv.setAdapter(new MyInfoPaferAdapter(infosList, imageViews, getActivity()));
        adv.addOnPageChangeListener(this);
        startAd();


    }

    @Override
    public void onFailure(String errorEvent, String message) {
        Log.e("sys", "Link is failure");
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        autoLogin();
    }

    private void autoLogin() {
        //  若用户再上一个界面已经登录了则这里不登录
        if (MainApplication.isLogin()) {
            return;
        }
        SharedPreferences mPre = MainApplication.getPreferences();
        boolean autoLogin = mPre.getBoolean("autoLogin",false);
        if (autoLogin){
            String phone = mPre.getString("phone","");
            String password = mPre.getString("password","");
            appAction.login("login", phone, password, Params.LOGIN, new ActionCallBackListener<Void>() {
                @Override
                public void onSuccess(Void data) {
                    MainApplication.setIsLogin(true);
                    appAction.getUserDetail("getUserInfo", Params.getUser, userinfoListener);
                }

                @Override
                public void onFailure(String errorEvent, String message) {

                }
            });
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            User info = MainApplication.getUserInfo();
            setName(info);
        }

        if(MainApplication.isWeb==30) {
            if (infosList == null) {
                if(imageViews.size()==0) {
                    appAction.loadAdvertisement("userQueryAllInfo", 1, 10, "ad", Params.Advertisement, this);
                }
            } else {
                // Log.e("sys", infosList.toString());
            }
        }else
        {
            Log.e("sys", "网络连接中断");
        }

    }

    private void setName(User info) {
        if (info != null){
            String name = info.getRealName();
            if (TextUtils.isEmpty(name)) {
                name = info.getPhone();
            }
            if (TextUtils.isEmpty(name)) {
                user_text.setText(getString(R.string.Login));
            } else {
                user_text.setText(name);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER && resultCode == Activity.RESULT_OK){
            if (MainApplication.isLogin()) {
                //if login successful
                LogUtils.i("登录成功");
                appAction.getUserDetail("getUserInfo", Params.getUser, userinfoListener);
            }

        }
    }
}
