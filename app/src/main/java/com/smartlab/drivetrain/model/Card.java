package com.smartlab.drivetrain.model;

import com.smartlab.drivetrain.util.Util;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by smartlab on 15/10/8.
 * 帖子列表
 */
public class Card extends Entity implements Serializable,Comparable<Card> {
    /**
     *  “title”:”xxx”   //主题
     ”answerNum“ :”18” //回复次数
     “name” : “admin”	  //发表人
     ”lastTime“:”2015-09-09  22:32:12”  //最后发表
     * */
    private String title;
    private int answerNum;
    private String name;
    private String createTime;
    private String timestamp;

    public Card(){}
    public Card(String title,int answerNum,String name,String createTime,String timestamp){
        this.title = title;
        this.answerNum = answerNum;
        this.name = name;
        this.createTime = createTime;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Card{" +
                "title='" + title + '\'' +
                ", answerNum=" + answerNum +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Card another) {
        Date d1 = Util.StringToDate(another.getCreateTime());
        Date d2 = Util.StringToDate(this.getCreateTime());
        if (d1 == null && d2 == null)
            return 0;
        if (d1 == null)
            return -1;
        if (d2 == null)
            return 1;
        return d1.compareTo(d2);
    }
}
