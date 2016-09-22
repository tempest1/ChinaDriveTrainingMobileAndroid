package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/12/21.
 * 驾校经纬度
 */
public class LatlngList extends Entity implements Parcelable {
    private long timestamp;
    private String name;
    private String address;
    private double longitude;
    private double latitude;

    public LatlngList(){};
    protected LatlngList(Parcel in) {
        timestamp = in.readLong();
        name = in.readString();
        address = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<LatlngList> CREATOR = new Creator<LatlngList>() {
        @Override
        public LatlngList createFromParcel(Parcel in) {
            return new LatlngList(in);
        }

        @Override
        public LatlngList[] newArray(int size) {
            return new LatlngList[size];
        }
    };

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(timestamp);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }
}
