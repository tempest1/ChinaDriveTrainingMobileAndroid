package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 15/12/26.
 * 投诉与评价model
 */
public class Complain {
    private String cmd;             //  请求类型
    private String userTimestamp;   //  用户时间戳
    private String type;            //  类型
    private String grade;           //  等级 1 差,2 很差,3 非常差
    private String content;         //  内容
    private String imageUrl;        //  图片地址

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Complain{" +
                "cmd='" + cmd + '\'' +
                ", userTimestamp='" + userTimestamp + '\'' +
                ", type='" + type + '\'' +
                ", grade='" + grade + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
