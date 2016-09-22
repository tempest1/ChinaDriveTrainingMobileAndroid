package com.smartlab.drivetrain.model;

import java.io.Serializable;

/**
 * Created by smartlab on 15/9/18.
 * 驾校详情信息
 */
public class SchoolDetail<T> implements Serializable{
    private String cmd;
    private boolean res;
    private String error;
    private String event;
    private String name;
    private String allPay;
    private String citycode;
    private double longitude;
    private double latitude;
    private String address;
    private String phone;
    private String introduction;
    private String mien;
    private String characteristic;
    private String remarks;
    private long registrationNum;
    private double highPraiseNum;
    private double highPraiseRate;
    private T drivingCommentList;

    public SchoolDetail(){}
    public SchoolDetail(String event,String error){
        this.event = event;
        this.error = error;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMien() {
        return mien;
    }

    public void setMien(String mien) {
        this.mien = mien;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getRegistrationNum() {
        return registrationNum;
    }

    public void setRegistrationNum(long registrationNum) {
        this.registrationNum = registrationNum;
    }

    public double getHighPraiseNum() {
        return highPraiseNum;
    }

    public void setHighPraiseNum(double highPraiseNum) {
        this.highPraiseNum = highPraiseNum;
    }

    public double getHighPraiseRate() {
        return highPraiseRate;
    }

    public void setHighPraiseRate(double highPraiseRate) {
        this.highPraiseRate = highPraiseRate;
    }

    public T getDrivingCommentList() {
        return drivingCommentList;
    }

    public void setDrivingCommentList(T drivingCommentList) {
        this.drivingCommentList = drivingCommentList;
    }

    public String getAllPay() {
        return allPay;
    }

    public void setAllPay(String allPay) {
        this.allPay = allPay;
    }

    @Override
    public String toString() {
        return "SchoolDetail{" +
                "cmd='" + cmd + '\'' +
                ", res=" + res +
                ", error='" + error + '\'' +
                ", event='" + event + '\'' +
                ", name='" + name + '\'' +
                ", allPay='" + allPay + '\'' +
                ", citycode='" + citycode + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", introduction='" + introduction + '\'' +
                ", mien='" + mien + '\'' +
                ", characteristic='" + characteristic + '\'' +
                ", remarks='" + remarks + '\'' +
                ", registrationNum=" + registrationNum +
                ", highPraiseNum=" + highPraiseNum +
                ", highPraiseRate=" + highPraiseRate +
                ", drivingCommentList=" + drivingCommentList +
                '}';
    }
}
