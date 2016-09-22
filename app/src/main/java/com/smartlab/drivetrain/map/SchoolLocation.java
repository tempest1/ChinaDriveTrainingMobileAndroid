package com.smartlab.drivetrain.map;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.database.SQLdm;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.detail.TrainingShow;
import com.smartlab.drivetrain.model.LatlngList;
import com.smartlab.drivetrain.util.LogUtils;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by smartlab on 15/9/23.
 * 在地图上显示驾校位置信息
 */
public class SchoolLocation extends BaseActivity implements View.OnClickListener{

    private MapView mMapView;
    private BDLocation location;
    private BaiduMap baiduMap;
    private CursorLoader cur;
    private Cursor cursor;
    private SQLdm dm;
    private int citycode = 0;
    //周围热点
    private BitmapDescriptor markerpoi = BitmapDescriptorFactory
            .fromResource(R.mipmap.jiaxiao_marker);
    //周围热点
    private ScheduledExecutorService scheduledExecutorService;

    //长按地图事件监听
    private BaiduMap.OnMapLongClickListener longClick = new BaiduMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            //经度30.362294纬度112.198708

            Log.e("Marker latutide", "经度" + latLng.longitude + "纬度" + latLng.latitude);
        }
    };
    private  Marker simpleMarker;
    // 点击覆盖物时间监听
    private BaiduMap.OnMarkerClickListener markerClick = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Bundle bundle = marker.getExtraInfo();
            LatlngList info = bundle.getParcelable("info");
            if (info != null) {
                LatLng lat = new LatLng(info.getLatitude(), info.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(lat);
                baiduMap.animateMapStatus(update);
                Toast.makeText(SchoolLocation.this, info.getName(), Toast.LENGTH_SHORT).show();
                simpleMarker = marker;
            }

            return false;
        }
    };
    private LocationClient mLocationClient;
    private boolean isFirstIn = true;
    private LatLng latold;
    private MyLocationListener myLocationListener;
    private BaiduMap.OnMapStatusChangeListener mapstus = new BaiduMap.OnMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            if (simpleMarker != null){
                showPop(simpleMarker);
            }
        }
    };

    private RelativeLayout info_window;
    private void getLocation(){
        // 获取定位信息
        location = MainApplication.getLocation();
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate mapstatus = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(mapstatus);//使用动画对地图进行更新
        }
        initData();
    }

    // 点击地图事件监听
    private BaiduMap.OnMapClickListener mapClick = new BaiduMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            if (info_window != null){
                if (info_window.isShown()){
                    info_window.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            return false;
        }
    };
    private int addTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_on_map);
        initView();
    }

    @Override
    protected void initData() {
        cur = new CursorLoader(this);
        dm = new SQLdm(this);
        // 查询数据库，城市编码
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return queryCityCode(location);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                citycode = integer;
                if (citycode != 0) aroundSchool();
            }
        }.execute();
    }

    private void aroundSchool(){
        appAction.drivingLatLng("getDrivingLatLngByCityCode", String.valueOf(citycode), 1, 10, Params.DrivingLatLngByCityCode, new ActionCallBackListener<List<LatlngList>>() {
            @Override
            public void onSuccess(List<LatlngList> data) {
                addPoiOverLay(data);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                showToast(message);
            }
        });
    }
    //  通过位置信息查询城市编码
    private int queryCityCode(BDLocation location) {
        int cityCode = 0;
        if (location != null){
            String city = location.getCity();
            if (!TextUtils.isEmpty(city)) {
                cursor = dm.queryCityCode(city);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        cityCode = cursor.getInt(cursor.getColumnIndex("CityCode"));
                    }
                    LogUtils.e(city + cityCode);
                    cursor.close();
                } else {
                    LogUtils.e("cursor is null");
                }
            }
        }
        LogUtils.e("citycode" + cityCode);
        return cityCode;
    }

    @Override
    protected void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();

        Button button_back = (Button) findViewById(R.id.detail_back);
        button_back.setOnClickListener(this);

        //地图状态的更新
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(13.0f);
        baiduMap.setMapStatus(msu);//设置地图当前显示的缩放级别
        baiduMap.setMaxAndMinZoomLevel(3.0f, 19.0f);//设置缩放级别
        baiduMap.setOnMapLongClickListener(longClick);//添加地图长按事件
        baiduMap.setOnMarkerClickListener(markerClick);
        baiduMap.setOnMapClickListener(mapClick);
        baiduMap.setOnMapStatusChangeListener(mapstus);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText("找驾校");
        //  获取我的位置信息
        getLocation();
        // 定位
        initLocation();

    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 高精度模式：
        // Battery_Saving:低精度模式
        option.setCoorType("bd09ll"); //设置坐标系类型
        option.setOpenGps(true);
        option.setScanSpan(2000);// 扫描时间间隔  scanSpan - 单位毫秒，当<1000(1s)时，定时定位无效
        option.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
        option.setNeedDeviceDirect(true);// 网络定位时，是否需要设备方向
        mLocationClient.setLocOption(option);
    }

    //添加覆盖物到地图上面
    public void addPoiOverLay(List<LatlngList> list) {
        addTimes++;
        if (list != null){
            baiduMap.clear();//看是否需要在刷新地图数据之前清楚所有数据
            for (LatlngList obj:list) {
                LatLng lat = new LatLng(obj.getLatitude(), obj.getLongitude());
                Log.i("OverLay", obj.getLongitude() + "" + obj.getLatitude() + "");
                Bundle bundle = new Bundle();
                bundle.putParcelable("info", obj);
                MarkerOptions mp = new MarkerOptions();
                mp.zIndex(20);
                OverlayOptions option = mp.icon(markerpoi)
                                        .position(lat)
                                        .extraInfo(bundle);
                baiduMap.addOverlay(option);
            }
        }

    }
    private TextView name,address;
    public void showPop(final Marker marker) {
        // 创建InfoWindow展示的view
        Bundle bundle = marker.getExtraInfo();
        final LatlngList info = bundle.getParcelable("info");
        if (info != null) {
            if (info_window == null){
                info_window = (RelativeLayout) findViewById(R.id.info_window);
                info_window.setVisibility(View.VISIBLE);
                name = (TextView) findViewById(R.id.tv_title);
                address = (TextView) findViewById(R.id.tv_content);
                findViewById(R.id.here).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(SchoolLocation.this, TrainingShow.class);
                        intent.putExtra("timestamp",String.valueOf(info.getTimestamp()));
                        startActivity(intent);
                    }
                });
            } else {
                if (!info_window.isShown()){
                    info_window.setVisibility(View.VISIBLE);
                }
            }
            name.setText(info.getName());
            address.setText(info.getAddress());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        Log.i("HomePage", "onStartRun");
    }

    @Override
    protected void onDestroy() {
        if (cur != null && cur.isStarted())
            //  关闭游标
            cur.onCanceled(cursor);
        // 关闭数据库对象
        if (dm != null)
            dm.closeDB();
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        if (mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_back:
                SchoolLocation.this.finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData locData = new MyLocationData.Builder()//
                    .accuracy(bdLocation.getRadius())//
                    .direction(bdLocation.getDirection())//
                    .latitude(bdLocation.getLatitude())//
                    .longitude(bdLocation.getLongitude())//
                    .build();
            location = bdLocation;

            baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, true, null));
            baiduMap.setMyLocationData(locData);//将数据设置给baidumap
            if (isFirstIn){
                latold = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latold);
                baiduMap.animateMapStatus(msu);//使用动画对地图进行更新
                isFirstIn = false;
            }

        }
    }
}
