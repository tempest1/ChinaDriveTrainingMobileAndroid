package com.smartlab.drivetrain.data;

import com.smartlab.drivetrain.model.CoachBrief;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/26.
 */
public class Coach {
    public static List<CoachBrief> getCoachLocation(double latitude,double longitude){
        List<CoachBrief> coachBriefs = new ArrayList<>();
        for(int i = 0; i<4;i++) {
            CoachBrief info = new CoachBrief();
//            经度30.362294纬度112.198708
            info.setLatitude(latitude + 0.008 * (i+1));
            info.setLongitude(longitude + 0.009 * (i+1));
            info.setCoachName("张三");
            info.setPhone("13554555772");
            coachBriefs.add(info);
        }
        return coachBriefs;

    }
}
