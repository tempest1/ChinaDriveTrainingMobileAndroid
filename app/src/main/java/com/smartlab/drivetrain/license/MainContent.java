package com.smartlab.drivetrain.license;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.fragment.ForumPage;
import com.smartlab.drivetrain.fragment.HomePage;
import com.smartlab.drivetrain.fragment.InfoPage;
import com.smartlab.drivetrain.fragment.UserCenter;
import com.smartlab.drivetrain.service.NetState;
import com.smartlab.drivetrain.update.Check;
import com.smartlab.drivetrain.util.ActivityCollector;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.ScreenUtils;

/**
 * Created by smartlab on 15/9/15.
 * <p/>
 * 应用程序主界面
 *
 * @author Smartlab-Xiong
 * implements HomePage.goUserCenter修改成点击进入登录页面
 */
public class MainContent extends BaseActivity {

    private FragmentManager fragmentManager;
    private HomePage mHomePage;// 首页
    private InfoPage mInfoPage;//资讯
    private UserCenter mUser;//用户中心
    private ForumPage mForum;//论坛
    private RadioButton radioHome;
    private RadioButton radioInfo;
    private RadioButton radioCommuication;
    private RadioButton radioMe;
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
            switch (checkId) {
                case R.id.radio_home:
                    setTabSelection(0);
                    break;
                case R.id.radio_info:
                    setTabSelection(1);
                    break;
                case R.id.radio_commuicate:
                    setTabSelection(2);
                    break;
                case R.id.radio_mine:
                    setTabSelection(3);
                    break;
            }
        }
    };
    //  广播接受者
    private NetState myReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_content);
        LogUtils.i("MainActivity is onCreated run");
        initView();
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
        // 调用检查更新
        Check check = new Check(this);
        check.checkForUpdates(Params.UPDATE_CHECK_URL);

        // 获取设备尺寸信息
        ScreenUtils.initScreen(this);
        myReceiver = new NetState(this);
    }

    /**
     * 选择当前的界面index
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        String FRAGMENT_TAG_HOME = "home";
        String FRAGMENT_TAG_INFO = "infoPage";
        String FRAGMENT_TAG_FORUM = "forum";
        String FRAGMENT_TAG_USER = "user";
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (mHomePage == null) {
                    mHomePage = new HomePage();
                    transaction.add(R.id.main_frame, mHomePage, FRAGMENT_TAG_HOME);
                }
                transaction.show(mHomePage);
                radioHome.setChecked(true);

                break;
            case 1:
                if (mInfoPage == null) {
                    mInfoPage = new InfoPage();
                    transaction.add(R.id.main_frame, mInfoPage, FRAGMENT_TAG_INFO);
                }
                transaction.show(mInfoPage);
                radioInfo.setChecked(true);
                break;
            case 2:
                if (mForum == null) {
                    mForum = new ForumPage();
                    transaction.add(R.id.main_frame, mForum, FRAGMENT_TAG_FORUM);
                }
                transaction.show(mForum);
                radioCommuication.setChecked(true);
                break;
            case 3:
                if (mUser == null) {
                    mUser = new UserCenter();
                    transaction.add(R.id.main_frame, mUser, FRAGMENT_TAG_USER);
                }
                transaction.show(mUser);
                radioMe.setChecked(true);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mHomePage != null) {
            transaction.hide(mHomePage);
        }
        if (mInfoPage != null) {
            transaction.hide(mInfoPage);
        }
        if (mForum != null) {
            transaction.hide(mForum);
        }
        if (mUser != null) {
            transaction.hide(mUser);
        }
    }

    @Override
    protected void initView() {
        RadioGroup radio_tab = (RadioGroup) findViewById(R.id.radio_tab);
        radio_tab.setOnCheckedChangeListener(listener);
        radioHome = (RadioButton) findViewById(R.id.radio_home);
        radioInfo = (RadioButton) findViewById(R.id.radio_info);
        radioCommuication = (RadioButton) findViewById(R.id.radio_commuicate);
        radioMe = (RadioButton) findViewById(R.id.radio_mine);
    }

    private void allCheckFalse() {
        radioHome.setChecked(false);
        radioInfo.setChecked(false);
        radioCommuication.setChecked(false);
        radioMe.setChecked(false);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //
    }

    @Override
    protected void onStart() {
        myReceiver.registerReceiver();
        //
        ActivityCollector.removeActivity(this);
        super.onStart();
        LogUtils.i("MainContent is start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i("MainContent is Resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.i("MainContent is reStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.i("MainContent is onPause");
    }

    @Override
    protected void onStop() {
        unregisterReceiver(myReceiver); //  注销广播
        super.onStop();
        LogUtils.i("MainContent is onStop");
    }

    @Override
    protected void onDestroy() {
        MainApplication.setUserInfo(null);
        MainApplication.setBeanDefinitionReaderSid(null);
        MainApplication.setIsLogin(false);
        super.onDestroy();
        LogUtils.i("MainContent is onDestroy");
    }

//    @Override
//    public void goUse() {
//        allCheckFalse();
//        setTabSelection(3);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){

                ActivityCollector.finishAlls();
                System.exit(0);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
