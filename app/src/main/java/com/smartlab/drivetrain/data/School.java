package com.smartlab.drivetrain.data;

import com.smartlab.drivetrain.model.LatlngList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/24.
 */
public class School {

    public static List<LatlngList> getSchool(double latitude,double longitude){

        List<LatlngList> infoDatas = new ArrayList<>();

        for(int i = 0; i<4;i++) {
            LatlngList info = new LatlngList();
//            经度30.362294纬度112.198708
            info.setLatitude(latitude + 0.02 * (i+1));
            info.setLongitude(longitude + 0.01 * (i+1));
            info.setName("工院驾校"+1);
            info.setAddress("长江大学工程技术学院"+1);
            infoDatas.add(info);
        }
        return infoDatas;

    }

}
