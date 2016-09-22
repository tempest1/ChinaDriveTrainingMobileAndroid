package com.smartlab.drivetrain.model;

import java.io.Serializable;

/**
 * Created by smartlab on 15/10/10.
 */
public class CardAnswer implements Serializable{
    /**
     * “cmd”:”addPost”,
     “timestamp”:”xxx”,
     “time”:”xxx”,
     “name”:”xxx” ,  //回帖人名字
     // “title”:”xxxx”,    //帖子标题
     “content”:”xxx”   //回帖内容
     * */

    private String cmd;
    private String timestamp;
    private String time;
    private String name;
    private String content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CardAnswer{" +
                "cmd='" + cmd + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
