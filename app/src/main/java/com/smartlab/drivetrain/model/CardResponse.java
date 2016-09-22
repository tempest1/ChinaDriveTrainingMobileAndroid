package com.smartlab.drivetrain.model;

import java.io.Serializable;

/**
 * Created by smartlab on 15/10/9.
 * 帖子回复model
 */
public class CardResponse extends Entity implements Serializable {
    private int cardNum;//发布的帖子次数
    private int answerCardNun;//回复的帖子数
    private String title;//帖子的标题
    private String role;//用户身份
    private String name;//用户姓名
    private String url;//用户头像的url地址
    private String publishTime;//发布时间
    private String content;//发布内容或者评论的内容
    private int answerTotal;//回复总数
    private int skimTotal;//浏览总数
    private String createTime;
    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public int getAnswerCardNun() {
        return answerCardNun;
    }

    public void setAnswerCardNun(int answerCardNun) {
        this.answerCardNun = answerCardNun;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAnswerTotal() {
        return answerTotal;
    }

    public void setAnswerTotal(int answerTotal) {
        this.answerTotal = answerTotal;
    }

    public int getSkimTotal() {
        return skimTotal;
    }

    public void setSkimTotal(int skimTotal) {
        this.skimTotal = skimTotal;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CardResponse{" +
                "cardNum=" + cardNum +
                ", answerCardNun=" + answerCardNun +
                ", title='" + title + '\'' +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", content='" + content + '\'' +
                ", answerTotal=" + answerTotal +
                ", skimTotal=" + skimTotal +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
