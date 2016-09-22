package com.smartlab.drivetrain.license;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.database.DbHelper;
import com.smartlab.drivetrain.detail.CoachDetail;
import com.smartlab.drivetrain.interfaces.AppAction;
import com.smartlab.drivetrain.interfaces.AppActionImp;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.model.WeiXiAccess;
import com.smartlab.drivetrain.model.WeiXiRefreshToken;
import com.smartlab.drivetrain.util.FileUtils;
import com.smartlab.drivetrain.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smartlab on 15/9/15.
 * 应用程序的生命周期
 */
public class MainApplication extends android.app.Application {


    public static String MESSAGE="";
    public static String Data="";
    public static String DataWeiXi="";
    public static String DataWeiXiUser="";
    public static  String AppID="wxcc22f8c794e9b46a";
    public static String AppSecret="ac4cfc05661db3d32d75bd6ebbdfca21";
    public static String CODE="";
    public static int isWeb=0;
    public static boolean isLogin = false;
    public static  String Phone="";
    public static String BeanDefinitionReaderSid;
    private static ImageLoader mImageLoader;
    private static DisplayImageOptions options;
    private static Context mContext;
    private static SharedPreferences mPre;
    private static User userInfo;//用户信息集合
    public static List<String> mEmoticons = new ArrayList<>();
    public static Map<String, Integer> mEmoticonsId = new HashMap< >();
    public static List<String> mEmoticons_Zem = new ArrayList<>();
    public static List<String> mEmoticons_Zemoji = new ArrayList<>();
    private static BDLocation location;
    private AppAction appAction;
    private LocationClient mLocationClient;
    private static DbHelper helper;
    public static Map<String, Long> map;

    //微信的一些类
    public static WeiXiAccess weiXiAccess;
    public static WeiXiRefreshToken weiXiRefreshToken;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mPre = getSharedPreferences("license",
                Context.MODE_PRIVATE);

        //应用程序操作
        appAction = new AppActionImp(this);
        // 使用ImageLoader之前初始化
        initImageLoader();

        // 初始化日志打印级别，及保存的路径
        LogUtils.isSaveLog = false;
        LogUtils.ROOT = Params.DATA_PATH;//日志根目录

        //百度地图初始化
        SDKInitializer.initialize(getApplicationContext());

        //表情图片
        initEomtFace();

        //获取用户当前位置
        initLocation();

        //微信的一些类
        WeiXiAccess weiXiAccess=new WeiXiAccess();
        WeiXiRefreshToken weiXiRefreshToken=new WeiXiRefreshToken();

    }


    public static DbHelper getHelper() {
        return helper;
    }

    public AppAction getAppAction() {
        return appAction;
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(new MyLocationListener());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        // Battery_Saving:低精度模式
        // Hight_Accuracy 高精度模式：
        option.setCoorType("bd09ll"); //设置坐标系类型
        option.setOpenGps(true);
        option.setScanSpan(600 * 1000);// 扫描时间间隔  scanSpan - 单位毫秒，当<1000(1s)时，定时定位无效
        option.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
        option.setNeedDeviceDirect(true);// 网络定位时，是否需要设备方向
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public static synchronized MainApplication context() {
        return (MainApplication) mContext;
    }
    private void initEomtFace() {
        for (int i = 1; i < 64; i++) {
            String emoticonsName = "[zem" + i + "]";
            int emoticonsId = getResources().getIdentifier("zem" + i,
                    "mipmap", getPackageName());
            mEmoticons.add(emoticonsName);
            mEmoticons_Zem.add(emoticonsName);
            mEmoticonsId.put(emoticonsName, emoticonsId);
        }

        for (int i = 1; i < 59; i++) {
            String emoticonsName = "[zemoji" + i + "]";
            int emoticonsId = getResources().getIdentifier("zemoji_e" + i,
                    "mipmap", getPackageName());
            mEmoticons.add(emoticonsName);
            mEmoticons_Zemoji.add(emoticonsName);
            mEmoticonsId.put(emoticonsName, emoticonsId);
        }
    }

    public static synchronized String getBeanDefinitionReaderSid() {
        return BeanDefinitionReaderSid;
    }

    public static synchronized void setBeanDefinitionReaderSid(String beanDefinitionReaderSid) {
        BeanDefinitionReaderSid = beanDefinitionReaderSid;
    }
    public static void loop(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用ImageLoader之前初始化
     **/
    private void initImageLoader() {
        /**
         * 自定义图片缓存路径
         */
        File cacheDir = FileUtils.createNewFile(getApplicationContext(), Params.IMAGE_LOADER);
        /**
         * 默认加载方式
         */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).build();
        /**
         * 图片加载全局配置
         */
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(12 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        /**
         * 调用初始化
         */
        ImageLoader.getInstance().init(configuration);
        /**
         * 获取图片加载实例
         */
        mImageLoader = ImageLoader.getInstance();

        /**
         *  加载图片时的全局参数
         */
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_error)
                .showImageForEmptyUri(R.drawable.empty_photo)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.empty_photo)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
    }

    public static ImageLoader getImageLoader(){
        return mImageLoader;
    }
    public static DisplayImageOptions getDisplayImageOptions(){
        return options;
    }
    public static Context getContext() {
        return mContext;
    }
    public static SharedPreferences getPreferences() {
        return mPre;
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                LogUtils.e("信息为空");
                return;
            }
            setLocation(bdLocation);
        }
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        MainApplication.isLogin = isLogin;
    }

    public static User getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(User userInfo) {
        MainApplication.userInfo = userInfo;
    }

    public static BDLocation getLocation() {
        return location;
    }

    public static void setLocation(BDLocation location) {
        MainApplication.location = location;
    }

    public static boolean isVar(String str)
    {
        boolean hasSymble = !str.matches("^[\\da-zA-Z]*$");
        return hasSymble;
    }



}
