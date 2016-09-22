package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/12/10.
 * 产品model
 */
public class Product implements Parcelable {

    private String cmd;
    private long timestamp;
    private double photoexamFee;
    private double km1;
    private double km2;
    private double km3;
    private double km4;
    private double hoursCardFee;
    private double allPay;
    private double installment1;
    private double installment2;
    private double installment3;
    private boolean isInstallment;
    private boolean res;

    private String event;    // 返回码，0为成功
    private String error;      // 错误信息

    public Product(){ }
    public Product(String event,String error){
        this.event = event;
        this.error = error;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getPhotoexamFee() {
        return photoexamFee;
    }

    public void setPhotoexamFee(double photoexamFee) {
        this.photoexamFee = photoexamFee;
    }

    public double getKm1() {
        return km1;
    }

    public void setKm1(double km1) {
        this.km1 = km1;
    }

    public double getKm2() {
        return km2;
    }

    public void setKm2(double km2) {
        this.km2 = km2;
    }

    public double getKm3() {
        return km3;
    }

    public void setKm3(double km3) {
        this.km3 = km3;
    }

    public double getKm4() {
        return km4;
    }

    public void setKm4(double km4) {
        this.km4 = km4;
    }

    public double getHoursCardFee() {
        return hoursCardFee;
    }

    public void setHoursCardFee(double hoursCardFee) {
        this.hoursCardFee = hoursCardFee;
    }

    public double getAllPay() {
        return allPay;
    }

    public void setAllPay(double allPay) {
        this.allPay = allPay;
    }

    public double getInstallment1() {
        return installment1;
    }

    public void setInstallment1(double installment1) {
        this.installment1 = installment1;
    }

    public double getInstallment2() {
        return installment2;
    }

    public void setInstallment2(double installment2) {
        this.installment2 = installment2;
    }

    public double getInstallment3() {
        return installment3;
    }

    public void setInstallment3(double installment3) {
        this.installment3 = installment3;
    }

    public boolean isInstallment() {
        return isInstallment;
    }

    public void setIsInstallment(boolean isInstallment) {
        this.isInstallment = isInstallment;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    protected Product(Parcel in) {
        cmd = in.readString();
        timestamp = in.readLong();
        photoexamFee = in.readDouble();
        km1 = in.readDouble();
        km2 = in.readDouble();
        km3 = in.readDouble();
        km4 = in.readDouble();
        hoursCardFee = in.readDouble();
        allPay = in.readDouble();
        installment1 = in.readDouble();
        installment2 = in.readDouble();
        installment3 = in.readDouble();
        isInstallment = in.readByte() != 0;
        res = in.readByte() != 0;
        event = in.readString();
        error = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cmd);
        dest.writeLong(timestamp);
        dest.writeDouble(photoexamFee);
        dest.writeDouble(km1);
        dest.writeDouble(km2);
        dest.writeDouble(km3);
        dest.writeDouble(km4);
        dest.writeDouble(hoursCardFee);
        dest.writeDouble(allPay);
        dest.writeDouble(installment1);
        dest.writeDouble(installment2);
        dest.writeDouble(installment3);
        dest.writeByte((byte) (isInstallment ? 1 : 0));
        dest.writeByte((byte) (res ? 1 : 0));
        dest.writeString(event);
        dest.writeString(error);
    }
}
