package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/10/21.
 * 模拟考试科目一model
 */
public class Question implements Comparable<Question> ,Parcelable {
    private int index; //索引1-100
    private int stxh; //总题号
    private int stsx; ////1表示普通，2表示需要加载图片，3表示需要加载视频
    private int sttx; ///1表示对错题（2个单选按钮），2表示单选题（4个单选按钮），3表示多选题（4个多选按钮）
    private String stnr;//题目内容
    private String stdaA;//A内容
    private String stdaB;//B内容
    private String stdaC;//C内容
    private String stdaD;//D内容
    private String stda;//答案（(A,B,C,D,N,Y)）

    public Question(){}
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getStxh() {
        return stxh;
    }

    public void setStxh(int stxh) {
        this.stxh = stxh;
    }

    public int getStsx() {
        return stsx;
    }

    public void setStsx(int stsx) {
        this.stsx = stsx;
    }

    public int getSttx() {
        return sttx;
    }

    public void setSttx(int sttx) {
        this.sttx = sttx;
    }

    public String getStnr() {
        return stnr;
    }

    public void setStnr(String stnr) {
        this.stnr = stnr;
    }

    public String getStdaA() {
        return stdaA;
    }

    public void setStdaA(String stdaA) {
        this.stdaA = stdaA;
    }

    public String getStdaB() {
        return stdaB;
    }

    public void setStdaB(String stdaB) {
        this.stdaB = stdaB;
    }

    public String getStdaC() {
        return stdaC;
    }

    public void setStdaC(String stdaC) {
        this.stdaC = stdaC;
    }

    public String getStdaD() {
        return stdaD;
    }

    public void setStdaD(String stdaD) {
        this.stdaD = stdaD;
    }

    public String getStda() {
        return stda;
    }

    public void setStda(String stda) {
        this.stda = stda;
    }

    @Override
    public String toString() {
        return "Question{" +
                "index=" + index +
                ", stxh=" + stxh +
                ", stsx=" + stsx +
                ", sttx=" + sttx +
                ", stnr='" + stnr + '\'' +
                ", stdaA='" + stdaA + '\'' +
                ", stdaB='" + stdaB + '\'' +
                ", stdaC='" + stdaC + '\'' +
                ", stdaD='" + stdaD + '\'' +
                ", stda='" + stda + '\'' +
                '}';
    }

    @Override
    public int compareTo(Question question) {
        if (index != question.index){
            return index - question.index;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Question){
            Question object = (Question) o;
            if ( (index == object.index) && (stxh == object.stxh)){
                return true;
            }else {
                return true;
            }

        }else {
            return false;
        }
    }

    //实现了序列化接口的
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(index);
        parcel.writeInt(stxh);
        parcel.writeInt(stsx);
        parcel.writeInt(sttx);

        parcel.writeString(stnr);
        parcel.writeString(stdaA);
        parcel.writeString(stdaB);
        parcel.writeString(stdaC);
        parcel.writeString(stdaD);
        parcel.writeString(stda);
    }

    /**interface must also have a static field called CREATOR, which is an object implementing the Parcelable.Creator interface.*/
    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel source) {
        index = source.readInt();
        stxh = source.readInt();
        stsx = source.readInt();
        sttx = source.readInt();

        stnr = source.readString();
        stdaA = source.readString();
        stdaB = source.readString();
        stdaC = source.readString();
        stdaD = source.readString();
        stda = source.readString();
    }
}
