package com.smartlab.drivetrain.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoudi on 16/4/19.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();
    public static List<Activity> activitys = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
    public static void addActivitys(Activity activity) {
        activitys.add(activity);
    }

    public static void removeActivitys(Activity activity) {
        activitys.remove(activity);
    }

    public static void finishAlls() {
        for (Activity activity : activitys) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
