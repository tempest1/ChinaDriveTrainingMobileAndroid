package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 16/1/5.
 * 添加新建咨询
 */
public class AddConsult {
    private String cmd;
    private String userTimestamp;
    private String type;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
