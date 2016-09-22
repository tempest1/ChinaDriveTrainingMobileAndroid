package com.smartlab.drivetrain.model;

import java.io.Serializable;

/**
 * Created by smartlab on 15/9/26.
 *论坛板块
 */
public class Frunm implements Serializable{
    private String timestamp;
    private String title;
    private String description;
    private String lastAnswerNum;
    private int postTotal;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastAnswerNum() {
        return lastAnswerNum;
    }

    public void setLastAnswerNum(String lastAnswerNum) {
        this.lastAnswerNum = lastAnswerNum;
    }

    public int getPostTotal() {
        return postTotal;
    }

    public void setPostTotal(int postTotal) {
        this.postTotal = postTotal;
    }

    @Override
    public String toString() {
        return "Frunm{" +
                "timestamp='" + timestamp + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", lastAnswerNum='" + lastAnswerNum + '\'' +
                ", postTotal=" + postTotal +
                '}';
    }
}
