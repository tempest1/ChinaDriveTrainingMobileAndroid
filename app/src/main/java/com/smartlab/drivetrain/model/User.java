package com.smartlab.drivetrain.model;

import java.io.Serializable;

/**
 * Created by smartlab on 15/11/21.
 *
 */
public class User implements Serializable{
    private String cmd;
    private String idCard;
    private String mail;
    private String password;
    private String phone;
    private String realName;
    private boolean sex;
    private boolean res;
    private String timeStamp;
    private MoreInfo userInfo;
    private String error;
    private String event;

    public User(String event,String error){
        this.event = event;
        this.error = error;
    }
    public User(){};

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public MoreInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(MoreInfo userInfo) {
        this.userInfo = userInfo;
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

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "cmd='" + cmd + '\'' +
                ", idCard='" + idCard + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", sex='" + sex + '\'' +
                ", res=" + res +
                ", timeStamp='" + timeStamp + '\'' +
                ", userInfo=" + userInfo +
                ", error='" + error + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
