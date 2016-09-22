package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 16/1/6.
 * 预约列表
 */
public class MyAppointment {
    private String coachName;
    private String id;
    private String time;
    private String createtime;

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "MyAppointment{" +
                "coachName='" + coachName + '\'' +
                ", id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
