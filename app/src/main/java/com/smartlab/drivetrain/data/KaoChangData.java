package com.smartlab.drivetrain.data;

import com.smartlab.drivetrain.model.KaoChang;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by smartlab on 15/9/26.
 */
public class KaoChangData {

    public static List<KaoChang> getKaoChangLocation(double latitude,double longitude){
        List<KaoChang> kaoChangs = new ArrayList<>();
        for (int i = 0;i< 2;i++){
            KaoChang kaoChang = new KaoChang();
            kaoChang.setKaoChangName("工院考场");
            kaoChang.setKaoChangLatitude(latitude + 0.07 * (i + 1));
            kaoChang.setKaoChangLongitude(longitude + 0.07 * (i + 1));

            kaoChangs.add(kaoChang);
        }
        return kaoChangs;
    }
}
