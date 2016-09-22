package com.smartlab.drivetrain.map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
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
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.KaoChang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* Created by smartlab on 15/9/26.
*/
public class KaoChangLocation extends Activity implements View.OnClickListener{
    private MapView mMapView;
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private BDLocation location;
    private BaiduMap baiduMap;
    private boolean isFirstIn = true;
    private List<Marker> markerList = new ArrayList<>();
    private InfoWindow mInfoWindow;
    //周围热点
    private BitmapDescriptor markerpoi = BitmapDescriptorFactory
            .fromResource(R.mipmap.kaochang_marker);
    //周围热点
    private BitmapDescriptor markers = BitmapDescriptorFactory
            .fromResource(R.mipmap.icon_focus_mark);
    private List<KaoChang> kaoChangList;
    //长按地图事件监听
    private BaiduMap.OnMapLongClickListener longClick = new BaiduMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(LatLng latLng) {
            //经度30.362294纬度112.198708
            Log.e("Marker latutide", "经度" + latLng.longitude + "纬度" + latLng.latitude);
        }
    };
    // 点击覆盖物时间监听
    private BaiduMap.OnMarkerClickListener markerClick = new BaiduMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Bundle bundle = marker.getExtraInfo();
            KaoChang info = bundle.getParcelable("info");
            if (info != null) {
                LatLng lat = new LatLng(info.getKaoChangLatitude(), info.getKaoChangLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(lat);
                baiduMap.animateMapStatus(update);
                Toast.makeText(KaoChangLocation.this, info.getKaoChangName(), Toast.LENGTH_SHORT).show();
                showPop(marker);
            }
            return false;
        }
    };

    // 点击地图事件监听
    private BaiduMap.OnMapClickListener mapClick = new BaiduMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            baiduMap.hideInfoWindow();
        }

        @Override
        public boolean onMapPoiClick(MapPoi mapPoi) {
            return false;
        }
    };
    // 地图状态更新监听
    private BaiduMap.OnMapStatusChangeListener mapStatus = new BaiduMap.OnMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
        }
    };
    private LatLng latold;
    private int addTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_on_map);
        initView();
        initLocation();
    }

    //初始化定位信息，定位到用户当前位置
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

    private void initView() {
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
        baiduMap.setOnMapStatusChangeListener(mapStatus);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText("找考场");

//        // 获取定位信息
//        BDLocation loc = MainApplication.getLocation();
//        if (location != null) {
//            LatLng latLng = new LatLng(loc.getLatitude(),loc.getLongitude());
//            MapStatusUpdate location = MapStatusUpdateFactory.newLatLng(latLng);
//            baiduMap.animateMapStatus(location);//使用动画对地图进行更新
//            isFirstIn = false;
//        }
        // 晶葳驾驶员考训中心经纬度 LatLng examLoc = new LatLng(30.329561,112.168642);112.168642,30.329561
        LatLng examLoc = new LatLng(30.329561,112.168642);
        MarkerOptions mp = new MarkerOptions();
        mp.zIndex(20);
        KaoChang kao = new KaoChang();
        kao.setKaoChangLatitude(30.329561);
        kao.setKaoChangLongitude(112.168642);
        kao.setKaoChangName("晶葳驾驶员考训中心");
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", kao);
        OverlayOptions option = mp.icon(markerpoi)
                .position(examLoc)
                .extraInfo(bundle);
        baiduMap.addOverlay(option);
        MapStatusUpdate location = MapStatusUpdateFactory.newLatLng(examLoc);
        baiduMap.animateMapStatus(location);//使用动画对地图进行更新
    }

    //添加覆盖物到地图上面
    public void addPoiOverLay(List<KaoChang> list) {
        addTimes++;
        if (list != null){
            baiduMap.clear();//看是否需要在刷新地图数据之前清楚所有数据
            for (int i = 0; i < list.size(); i++) {
                KaoChang obj = list.get(i);
                LatLng lat = new LatLng(obj.getKaoChangLatitude(), obj.getKaoChangLongitude());
                Log.i("OverLay", obj.getKaoChangLatitude() + "" + obj.getKaoChangLongitude() + "");
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", (Serializable) list.get(i));
                MarkerOptions mp = new MarkerOptions();
                mp.zIndex(20);
                OverlayOptions option = mp.icon(markerpoi)
                        .position(lat)
                        .extraInfo(bundle);
                baiduMap.addOverlay(option);
            }
        }

    }

    public void showPop(Marker marker) {
        // 创建InfoWindow展示的view
        Bundle bundle = marker.getExtraInfo();
        final KaoChang info = bundle.getParcelable("info");
        if (info != null) {
            TextView title = new TextView(this);
            int res = (int)getResources().getDimension(R.dimen.login_edit_padding);
            title.setPadding(res,res,res,res);
            title.setBackgroundResource(R.drawable.bg_popwindow);
            title.setText(info.getKaoChangName());
            // 定义用于显示该InfoWindow的坐标点
            LatLng pt = new LatLng(info.getKaoChangLatitude(), info.getKaoChangLongitude());
            // 创建InfoWindow
            mInfoWindow = new InfoWindow(title, pt, -50);
            // 显示InfoWindow
            baiduMap.showInfoWindow(mInfoWindow);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
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
                KaoChangLocation.this.finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            }
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
//                latold = new LatLng(location.getLatitude(), location.getLongitude());
//                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latold);
//                baiduMap.animateMapStatus(msu);//使用动画对地图进行更新
//                if (latold != null) {
//                    List<KaoChang> info = KaoChangData.getKaoChangLocation(location.getLatitude(), location.getLongitude());
//                    Log.e("info",info.toString());
//                    addPoiOverLay(info);
//                }
                isFirstIn = false;
            }

        }
    }
}

