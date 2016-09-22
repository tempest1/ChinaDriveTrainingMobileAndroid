package com.smartlab.drivetrain.model;

/**
 * Created by smartlab on 15/9/15.
 * 应用程序版本信息model
 * 实现原理：在服务器上有一个存放应用程序版本信息的地址，每次启动应用程序时，会自动连接到该地址，读取版本信息，与本地版本信息，进行比对，从而判断是否需要更新
 */
public class UpdateInfo {

    private int verCode;
    private String verName;
    private String description;
    private String url;

    public int getVerCode() {
        return verCode;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VerInfo{" +
                "verCode=" + verCode +
                ", verName='" + verName + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

