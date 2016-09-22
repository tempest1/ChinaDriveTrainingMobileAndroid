package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/9/26.
 */
public class KaoChang implements Parcelable{
    private String kaoChangName;
    private double kaoChangLatitude;
    private double KaoChangLongitude;

    public KaoChang(){}
    protected KaoChang(Parcel in) {
        kaoChangName = in.readString();
        kaoChangLatitude = in.readDouble();
        KaoChangLongitude = in.readDouble();
    }

    public static final Creator<KaoChang> CREATOR = new Creator<KaoChang>() {
        @Override
        public KaoChang createFromParcel(Parcel in) {
            return new KaoChang(in);
        }

        @Override
        public KaoChang[] newArray(int size) {
            return new KaoChang[size];
        }
    };

    public String getKaoChangName() {
        return kaoChangName;
    }

    public void setKaoChangName(String kaoChangName) {
        this.kaoChangName = kaoChangName;
    }

    public double getKaoChangLatitude() {
        return kaoChangLatitude;
    }

    public void setKaoChangLatitude(double kaoChangLatitude) {
        this.kaoChangLatitude = kaoChangLatitude;
    }

    public double getKaoChangLongitude() {
        return KaoChangLongitude;
    }

    public void setKaoChangLongitude(double kaoChangLongitude) {
        KaoChangLongitude = kaoChangLongitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kaoChangName);
        dest.writeDouble(kaoChangLatitude);
        dest.writeDouble(KaoChangLongitude);
    }
}
