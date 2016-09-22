package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 15/9/18.
 * 用户对驾校的评价信息
 */
public class Evaluate{
    private String cmd;                 //评价用户
    private String userTimestamp;     //评论内容
    private String drivingTimestamp;          //评价时间
    private String coachTimestamp;          //评价时间
    private String km;          //评价时间
    private String grade;          //评价时间
    private String content;          //评价时间

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getUserTimestamp() {
        return userTimestamp;
    }

    public void setUserTimestamp(String userTimestamp) {
        this.userTimestamp = userTimestamp;
    }

    public String getDrivingTimestamp() {
        return drivingTimestamp;
    }

    public void setDrivingTimestamp(String drivingTimestamp) {
        this.drivingTimestamp = drivingTimestamp;
    }

    public String getCoachTimestamp() {
        return coachTimestamp;
    }

    public void setCoachTimestamp(String coachTimestamp) {
        this.coachTimestamp = coachTimestamp;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Evaluate{" +
                "cmd='" + cmd + '\'' +
                ", userTimestamp='" + userTimestamp + '\'' +
                ", drivingTimestamp='" + drivingTimestamp + '\'' +
                ", coachTimestamp='" + coachTimestamp + '\'' +
                ", km='" + km + '\'' +
                ", grade='" + grade + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
