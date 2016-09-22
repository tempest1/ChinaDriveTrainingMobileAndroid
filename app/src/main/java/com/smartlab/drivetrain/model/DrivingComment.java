package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 15/12/8.
 * DrivingComment
 */
public class DrivingComment {
    private long timestamp;
    private long user;
    private String content;
    private String createTime;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DrivingComment{" +
                "timestamp=" + timestamp +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
