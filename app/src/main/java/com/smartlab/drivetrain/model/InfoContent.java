package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 15/11/23.
 */
public class InfoContent {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "InfoContent{" +
                "content='" + content + '\'' +
                '}';
    }
}
