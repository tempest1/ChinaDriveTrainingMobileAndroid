package com.smartlab.drivetrain.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smartlab.drivetrain.adapter.MyBannerPagerAdapter;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.map.Navigation;
import com.smartlab.drivetrain.model.AdTrain;
import com.smartlab.drivetrain.model.DrivingComment;
import com.smartlab.drivetrain.model.Product;
import com.smartlab.drivetrain.model.SchoolDetail;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.product.PayType;
import com.smartlab.drivetrain.service.PictureDownload;
import com.smartlab.drivetrain.service.PictureDownload.DownloadStateListener;
import com.smartlab.drivetrain.util.FileUtils;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.PhotoUtils;
import com.smartlab.drivetrain.util.ScreenUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.ResizeDrawable;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * Created by smartlab on 15/9/15.
 * 驾校展示页面，上面是图片展示，下面市具体内容的呈现
 *
 */
public class TrainingShow extends BaseActivity implements BaseActivity.MyHandler.Resolve,View.OnClickListener{


    private ViewPager adViewPager;
    private List<ImageView> imageViews;// 滑动的图片集合

    private List<View> dots; // 图片标题正文的那些点
    private List<View> dotList;


//    private TextView tv_topic_from;
    private int currentItem = 0; // 当前图片的索引号
    private MyBannerPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    // 异步加载图片
    private ImageLoader mImageLoader = MainApplication.getImageLoader();
    private DisplayImageOptions options = MainApplication.getDisplayImageOptions();

    // 轮播banner的数据
    private List<AdTrain> adList;

    private MyHandler handler = new MyHandler<>(this,this);

    private Product product;
    private Button buy;

    //
    private TextView tv_pre;    //  地址
    private TextView tv_title;  //  驾校名称
    private TextView price;     //  价格
    private TextView registrationNum;   //  驾校已报名数
    private TextView highPraiseNum;     //  好评数
    private TextView phone;             //  驾校电话
    private TextView name;              //  驾校名称
    private TextView address;           //  地址
    private TextView location;          //  距离
    private TextView introduction;      //  驾校简介

    //驾校经纬度
    private double latidute = 0;
    private double longitude = 0;

    private String drivingName;
    private BDLocation bd;
    private TextView mien;
    private TextView Characteristic;
    private TextView Remarks;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        this.context = this;
        initView();
        initData();
//        startAd();
        initNavigation();
    }

    @Override
    protected void initData() {
        bd = MainApplication.getLocation();
        // 点
        dots = new ArrayList<>();
        dotList = new ArrayList<>();
        // 定义的五个指示点
        View dot0 = findViewById(R.id.v_dot0);
        View dot1 = findViewById(R.id.v_dot1);
        View dot2 = findViewById(R.id.v_dot2);
        View dot3 = findViewById(R.id.v_dot3);
        View dot4 = findViewById(R.id.v_dot4);

        dots.add(dot0);
        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);
        adapter.notifyDataSetChanged();

        Intent intent = getIntent();
        if (intent != null){
            String timestamp = intent.getStringExtra("timestamp");
            if (timestamp == null) {
                return;
            }
            LogUtils.e(timestamp);
            //  获取驾校详情
            appAction.getDrivingDetail("getDriving", Long.valueOf(timestamp), Params.Driving_Detail, new ActionCallBackListener<SchoolDetail<List<DrivingComment>>>() {
                /**
                 * 处理成功
                 * @param data 返回数据
                 */
                @Override
                public void onSuccess(SchoolDetail<List<DrivingComment>> data) {
                    if (data != null){
                        LogUtils.e(data.toString());
                        setData(data);
                    }
                }

                /**
                 * 请求失败
                 * @param errorEvent 错误码
                 * @param message    错误详情
                 */
                @Override
                public void onFailure(String errorEvent, String message) {
                    LogUtils.e(message);
                    showToast(message);
                }
            });
            // 获取驾校产品
            appAction.getProductByDrivingId("getProductByDrivingId", timestamp, Params.GET_PRODUCT_ID, new ActionCallBackListener<Product>() {
                @Override
                public void onSuccess(Product data) {
                    product = data;
                    //  一口价不等于0
                    if (data.getAllPay() != 0) {
                        price.setText(getResources().getString(R.string.all_play, data.getAllPay()));
                        buy.setEnabled(true);
                    } else {
                        price.setText(getResources().getString(R.string.no_info));
                    }
                }

                @Override
                public void onFailure(String errorEvent, String message) {
                    showToast(message);
                }
            });
        }
    }
    @Override
    protected void initView() {
        activityList.add(TrainingShow.this);
        tv_title = (TextView) findViewById(R.id.tv_title);
//        tv_topic_from = (TextView) findViewById(R.id.tv_topic_from);
        adViewPager = (ViewPager) findViewById(R.id.vp);
        price = (TextView) findViewById(R.id.price);
        buy = (Button) findViewById(R.id.buy);
        buy.setOnClickListener(this);
        imageViews = new ArrayList<>();
//        adList = BannerData.getBannerAd();
        adList = new ArrayList<>();
        adapter = new MyBannerPagerAdapter(adList,imageViews);
        adViewPager.setAdapter(adapter);// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        adViewPager.addOnPageChangeListener(new MyPageChangeListener());
        findViewById(R.id.back).setOnClickListener(this);

        tv_pre = (TextView) findViewById(R.id.tv_pre);
        registrationNum = (TextView) findViewById(R.id.registrationNum);
        highPraiseNum = (TextView) findViewById(R.id.highPraiseNum);
        phone = (TextView) findViewById(R.id.phone);
        LinearLayout action_call = (LinearLayout) findViewById(R.id.action_call);
        action_call.setOnClickListener(this);
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        location = (TextView) findViewById(R.id.location);
        LinearLayout map_location = (LinearLayout) findViewById(R.id.map_location);
        map_location.setOnClickListener(this);
        introduction = (TextView) findViewById(R.id.introduction);
        introduction.setMovementMethod(LinkMovementMethod.getInstance());
        mien = (TextView) findViewById(R.id.mien);
        mien.setMovementMethod(LinkMovementMethod.getInstance());
        Characteristic = (TextView) findViewById(R.id.Characteristic);
        Characteristic.setMovementMethod(LinkMovementMethod.getInstance());
        Remarks = (TextView) findViewById(R.id.Remarks);
        Remarks.setMovementMethod(LinkMovementMethod.getInstance());
    }


    private void addDynamicView(List<String> img_url) {
        //
        if (img_url != null){
            if (img_url.size() > 0) {
                adList.clear();
                for (int i = 0; i < img_url.size(); i++) {
                    AdTrain adImg = new AdTrain();
                    adImg.setImgUrl(img_url.get(i));
                    adList.add(adImg);
                }
            }
        }
        // 动态添加图片和下面指示的圆点
        // 初始化图片资源
        for (int i = 0; i < adList.size(); i++) {
            ImageView imageView = new ImageView(this);
            // 异步加载图片
            mImageLoader.displayImage(adList.get(i).getImgUrl(), imageView,
                    options);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
            dots.get(i).setVisibility(View.VISIBLE);
            dotList.add(dots.get(i));
        }
        adapter.notifyDataSetChanged();
        startAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startAd() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
                TimeUnit.SECONDS);
    }

    @Override
    public void resolveData(Message msg) {
        adViewPager.setCurrentItem(currentItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                close(this);
                break;
            case R.id.buy:
                // 立即报名
                buy();
                break;
            case R.id.map_location:
                //跳转百度地图
                if (BaiduNaviManager.isNaviInited()){
                    routePlanToNavi();
                }
                break;
            default:break;
        }
    }

    String authinfo = null;

    /**
     * 初始化百度导航引擎
     */
    private void initNavigation() {
        BNOuterTTSPlayerCallback ttsCallback = null;
        String mSDCardPath = FileUtils.getRootPath();
        String APP_FOLDER_NAME = "Drive";
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                TrainingShow.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        LogUtils.e(authinfo);
                    }
                });
            }

            public void initSuccess() {
                LogUtils.e("百度导航引擎初始化成功");
            }

            public void initStart() {
                LogUtils.e("百度导航引擎初始化开始");
            }

            public void initFailed() {
                showToast("百度导航引擎初始化失败");

            }
        },  null/* null mTTSCallback */);
    }

    private void routePlanToNavi() {

        BNRoutePlanNode.CoordinateType coType = BNRoutePlanNode.CoordinateType.BD09LL;
        BDLocation bd = MainApplication.getLocation();
        if (bd != null){

            BNRoutePlanNode sNode = new BNRoutePlanNode(bd.getLongitude(), bd.getLatitude(), bd.getAddrStr(), null, coType);
            BNRoutePlanNode eNode = new BNRoutePlanNode(longitude, latidute, drivingName, null, coType);
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new SchoolRoutePlanListener(sNode));//  采用推荐算路的方式ROUTE_PLAN_MOD_RECOMMEND常量值 为1
        }
    }

    private void buy() {
        if (product != null) {
            User user = MainApplication.getUserInfo();
            if (user != null) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("product",product);
                intent.putExtras(bundle);
                intent.setClass(TrainingShow.this, PayType.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            } else {
                showToast("请先登录");
            }
        }
    }
    //  填充数据
    private void setData(SchoolDetail<List<DrivingComment>> data) {
        //过滤出所有img标签下的src地址
        List<String> img_url = Util.getImgSrc(data.getIntroduction());//驾校简介，包含有图片和文字
//        //使用正则表达式去除html标记
//        Pattern pattern = Pattern.compile("<.+?>",Pattern.DOTALL);
//        Matcher matcher = pattern.matcher(data.getIntroduction());
        Matcher matcher = Util.PATTERN.matcher(data.getIntroduction());

        String introduction = matcher.replaceAll("");                       // 提取驾校简介的文字内容
//        introduction = introduction.replaceAll("</?[^<]+>", "");
        introduction = introduction.replaceAll("&nbsp;", "");

        String address = data.getAddress();                                 // 驾校地址
        double highPraiseNum = data.getHighPraiseNum();                     // 好评数量
        String Characteristic = data.getCharacteristic();                   // 特色服务
        String mien = data.getMien();                                       // 驾校风采
        drivingName = data.getName();                                       // 驾校名称
        String phoneNum = data.getPhone();                                     // 驾校联系方式
        long registrationNum = data.getRegistrationNum();                   // 报名数量
        String Remarks = data.getRemarks();                                 // 备注
        List<DrivingComment> CommentList = data.getDrivingCommentList();    // 驾校评论列表

        LogUtils.e(introduction);
        this.introduction.setText(showHtmlOnTextView(introduction,this.Remarks));
        this.address.setText(address);
        this.highPraiseNum.setText(getResources().getString(R.string.highPraiseNum,highPraiseNum));
        this.name.setText(drivingName);
        this.phone.setText(getResources().getString(R.string.phone_info,phoneNum));
        this.registrationNum.setText(getResources().getString(R.string.registrationNum,registrationNum));
        this.tv_title.setText(drivingName);
        this.tv_pre.setText(address);
        this.mien.setText(showHtmlOnTextView(mien,this.Remarks));
        this.Characteristic.setText(showHtmlOnTextView(Characteristic,this.Remarks));
        this.Remarks.setText(showHtmlOnTextView(Remarks,this.Remarks));

        //
        latidute = data.getLatitude();
        longitude = data.getLongitude();

        if (bd != null) {
            LatLng v1 = new LatLng(bd.getLatitude(), bd.getLongitude());
            LatLng v2 = new LatLng(data.getLatitude(), data.getLongitude());
            double dis = DistanceUtil.getDistance(v1,v2) / 1000;
            if (dis > 0) {
                this.location.setText(getResources().getString(R.string.distances,Util.formatTwoPlace(dis)));
            } else if ((dis * 10) > 0){
                this.location.setText(getResources().getString(R.string.distances_m,Util.formatTwoPlace(dis * 10)));
            }
        }
        addDynamicView(img_url);

    }
    /**
     *
     * @param htmlString html 字符串
     * @return Spanned
     */
    private Spanned showHtmlOnTextView(String htmlString,TextView view){
        if (htmlString == null){
            htmlString = "";
        }
        return Html.fromHtml(htmlString,new MyImageGetter(htmlString,view),null);
    }

    private class MyImageGetter implements Html.ImageGetter,DownloadStateListener{

        private TextView textView;
        private List<String> imgUrl;
        public MyImageGetter(String htmlString,TextView view){
            this.textView = view;
            textView.setTag(htmlString);        //多个TextView同时显示带图片的HtmlString会出现错位，解决方法，通过tag的方式将对象与View绑定
            imgUrl = new ArrayList<>();
        }
        @Override
        public Drawable getDrawable(String source) {
            if (source != null) {
                LogUtils.e(source);
                //  对文件名采用MD5加密
                String fileName = Util.makeMD5(source.substring(source.lastIndexOf("/") + 1, source.length()));
                // 图片命名方式
                final File cacheFile = new File(Params.IMAGE_CACHE, fileName);
                /**
                 * 文件存在返回该文件
                 */
                if (cacheFile.exists()) {
                    // 如果文件已经存在，直接返回
                    Bitmap img = PhotoUtils.resizeImage(cacheFile.getAbsolutePath(), ScreenUtils.getScreenW(), 0);
                    ResizeDrawable d = new ResizeDrawable(img);
                    d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                    return d;
                }
                // 不存在文件时返回默认图片，并异步加载网络图片
                imgUrl.clear();
                imgUrl.add(source);
                Drawable d = ContextCompat.getDrawable(context, R.drawable.empty_photo);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                new PictureDownload(Params.IMAGE_CACHE,imgUrl,handler,this).startDownload();
                return d;
            } else {
                return null;
            }
        }

        @Override
        public void onFinish() {
            textView.setText(Html.fromHtml(textView.getTag().toString(), this, new MyTagHandler(context)));
        }

        @Override
        public void onFailed() {

        }
    }
    /**
     * Viewpager自动滚动的定时任务
     */
    private class ScrollTask implements Runnable {

        @Override
        public void run() {
            synchronized (adViewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 当Activity不可见的时候停止切换调用startAd之后才可用
        scheduledExecutorService.shutdown();
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        private int oldPosition = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position;
        }
    }

    //  路线计算回调
    public static List<Activity> activityList = new LinkedList<Activity>();
    public static String ROUTE_PLAN_NODE = "ROUTE_PLAN_NODE";
    private class SchoolRoutePlanListener implements BaiduNaviManager.RoutePlanListener {
        BNRoutePlanNode mBNRoutePlanNode;
        public SchoolRoutePlanListener(BNRoutePlanNode sNode) {
            this.mBNRoutePlanNode = sNode;
        }

        @Override
        public void onJumpToNavigator() {
            /*
			 * 设置途径点以及resetEndNode会回调该接口
			 */

            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("Navigation")) {

                    return;
                }
            }
            Intent intent = new Intent(TrainingShow.this, Navigation.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            showToast("算路失败");
        }
    }

    @Override
    protected void onDestroy() {
        activityList.remove(TrainingShow.this);
        if (BaiduNaviManager.isNaviInited()) BaiduNaviManager.getInstance().uninit();
        super.onDestroy();
    }
}
