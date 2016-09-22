package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 15/11/14.
 * 考试进度，数据实体
 */
public class TlineEntity extends Entity {
    /**
     * time line point
     */
    private String time_index;
    /**
     * time line tips
     */
    private String time_tips;
    /**
     * time line title
     */
    private String time_title;

    /**
     * weather is doing
     */
    private boolean isDoing;

    /**
     * weather is over
     */
    private boolean isDone;

    /**
     * weather is pass
     */
    private boolean isPass;

    public String getTime_index() {
        return time_index;
    }

    public void setTime_index(String time_index) {
        this.time_index = time_index;
    }

    public String getTime_tips() {
        return time_tips;
    }

    public void setTime_tips(String time_tips) {
        this.time_tips = time_tips;
    }

    public String getTime_title() {
        return time_title;
    }

    public void setTime_title(String time_title) {
        this.time_title = time_title;
    }

    public boolean isDoing() {
        return isDoing;
    }

    public void setIsDoing(boolean isDoing) {
        this.isDoing = isDoing;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setIsPass(boolean isPass) {
        this.isPass = isPass;
    }

    @Override
    public String toString() {
        return "TlineEntity{" +
                "time_index='" + time_index + '\'' +
                ", time_tips='" + time_tips + '\'' +
                ", time_title='" + time_title + '\'' +
                ", isDoing=" + isDoing +
                ", isDone=" + isDone +
                ", isPass=" + isPass +
                '}';
    }
}
