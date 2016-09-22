package com.smartlab.drivetrain.model;

import java.io.Serializable;

/**
 * Created by smartlab on 15/11/21.
 */
public class MoreInfo implements Serializable {
    // Parcelable
    private String registTime;

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    @Override
    public String toString() {
        return "MoreInfo{" +
                "registTime='" + registTime + '\'' +
                '}';
    }
}
