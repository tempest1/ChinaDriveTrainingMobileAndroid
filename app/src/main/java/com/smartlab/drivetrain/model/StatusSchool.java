package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 16/1/6.
 * 学籍
 */
public class StatusSchool {
    private String cmd;
    private String phone;
    private String realname;
    private String mail;
    private String sex;
    private String shoolTimestamp;
    private String drivingTimestamp;
    private String coachTimestamp1;
    private String coachName1;
    private String coachNumber1;
    private String coachTimestamp2;
    private String coachName2;
    private String coachNumber2;
    private String type;
    private String km1;
    private String km2;
    private String km3;
    private String km4;
    private String startTime;
    private String endTime;
    private boolean res;
    private String error;
    private String event;
    public StatusSchool(){
    }
    public StatusSchool(String error,String event){
        this.error = error;
        this.event = event;
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

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getShoolTimestamp() {
        return shoolTimestamp;
    }

    public void setShoolTimestamp(String shoolTimestamp) {
        this.shoolTimestamp = shoolTimestamp;
    }

    public String getDrivingTimestamp() {
        return drivingTimestamp;
    }

    public void setDrivingTimestamp(String drivingTimestamp) {
        this.drivingTimestamp = drivingTimestamp;
    }

    public String getCoachTimestamp1() {
        return coachTimestamp1;
    }

    public void setCoachTimestamp1(String coachTimestamp1) {
        this.coachTimestamp1 = coachTimestamp1;
    }

    public String getCoachName1() {
        return coachName1;
    }

    public void setCoachName1(String coachName1) {
        this.coachName1 = coachName1;
    }

    public String getCoachNumber1() {
        return coachNumber1;
    }

    public void setCoachNumber1(String coachNumber1) {
        this.coachNumber1 = coachNumber1;
    }

    public String getCoachTimestamp2() {
        return coachTimestamp2;
    }

    public void setCoachTimestamp2(String coachTimestamp2) {
        this.coachTimestamp2 = coachTimestamp2;
    }

    public String getCoachName2() {
        return coachName2;
    }

    public void setCoachName2(String coachName2) {
        this.coachName2 = coachName2;
    }

    public String getCoachNumber2() {
        return coachNumber2;
    }

    public void setCoachNumber2(String coachNumber2) {
        this.coachNumber2 = coachNumber2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKm1() {
        return km1;
    }

    public void setKm1(String km1) {
        this.km1 = km1;
    }

    public String getKm2() {
        return km2;
    }

    public void setKm2(String km2) {
        this.km2 = km2;
    }

    public String getKm3() {
        return km3;
    }

    public void setKm3(String km3) {
        this.km3 = km3;
    }

    public String getKm4() {
        return km4;
    }

    public void setKm4(String km4) {
        this.km4 = km4;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "StatusSchool{" +
                "cmd='" + cmd + '\'' +
                ", phone='" + phone + '\'' +
                ", realname='" + realname + '\'' +
                ", mail='" + mail + '\'' +
                ", sex='" + sex + '\'' +
                ", shoolTimestamp='" + shoolTimestamp + '\'' +
                ", drivingTimestamp='" + drivingTimestamp + '\'' +
                ", coachTimestamp1='" + coachTimestamp1 + '\'' +
                ", coachName1='" + coachName1 + '\'' +
                ", coachNumber1='" + coachNumber1 + '\'' +
                ", coachTimestamp2='" + coachTimestamp2 + '\'' +
                ", coachName2='" + coachName2 + '\'' +
                ", coachNumber2='" + coachNumber2 + '\'' +
                ", type='" + type + '\'' +
                ", km1='" + km1 + '\'' +
                ", km2='" + km2 + '\'' +
                ", km3='" + km3 + '\'' +
                ", km4='" + km4 + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", res=" + res +
                ", error='" + error + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
