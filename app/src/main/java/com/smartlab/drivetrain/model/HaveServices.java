package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/12/10.
 * 用户购买的产品服务parcelable
 */
public class HaveServices implements Parcelable {
    private boolean base;
    private boolean km1;
    private boolean km2;
    private boolean km3;
    private boolean km4;
    private long drivingTimestamp;
    private long productTimestamp;

    public HaveServices(){

    }
    protected HaveServices(Parcel in) {
        base = in.readByte() != 0;
        km1 = in.readByte() != 0;
        km2 = in.readByte() != 0;
        km3 = in.readByte() != 0;
        km4 = in.readByte() != 0;
    }

    public static final Creator<HaveServices> CREATOR = new Creator<HaveServices>() {
        @Override
        public HaveServices createFromParcel(Parcel in) {
            return new HaveServices(in);
        }

        @Override
        public HaveServices[] newArray(int size) {
            return new HaveServices[size];
        }
    };

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public boolean isKm1() {
        return km1;
    }

    public void setKm1(boolean km1) {
        this.km1 = km1;
    }

    public boolean isKm2() {
        return km2;
    }

    public void setKm2(boolean km2) {
        this.km2 = km2;
    }

    public boolean isKm3() {
        return km3;
    }

    public void setKm3(boolean km3) {
        this.km3 = km3;
    }

    public boolean isKm4() {
        return km4;
    }

    public void setKm4(boolean km4) {
        this.km4 = km4;
    }

    public long getDrivingTimestamp() {
        return drivingTimestamp;
    }

    public void setDrivingTimestamp(long drivingTimestamp) {
        this.drivingTimestamp = drivingTimestamp;
    }

    public long getProductTimestamp() {
        return productTimestamp;
    }

    public void setProductTimestamp(long productTimestamp) {
        this.productTimestamp = productTimestamp;
    }

    @Override
    public String toString() {
        return "HaveServices{" +
                "base=" + base +
                ", km1=" + km1 +
                ", km2=" + km2 +
                ", km3=" + km3 +
                ", km4=" + km4 +
                ", drivingTimestamp=" + drivingTimestamp +
                ", productTimestamp=" + productTimestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (base ? 1 : 0));
        dest.writeByte((byte) (km1 ? 1 : 0));
        dest.writeByte((byte) (km2 ? 1 : 0));
        dest.writeByte((byte) (km3 ? 1 : 0));
        dest.writeByte((byte) (km4 ? 1 : 0));
    }
}
