package com.smartlab.drivetrain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by smartlab on 15/12/13.
 * 购买服务model
 */
public class PayServices implements Parcelable{
    private boolean base;
    private boolean installment1;
    private boolean installment2;
    private boolean installment3;
    private boolean allpay;
    private String cmd;

    public PayServices(){
    }

    protected PayServices(Parcel in) {
        base = in.readByte() != 0;
        installment1 = in.readByte() != 0;
        installment2 = in.readByte() != 0;
        installment3 = in.readByte() != 0;
        allpay = in.readByte() != 0;
        cmd = in.readString();
    }

    public static final Creator<PayServices> CREATOR = new Creator<PayServices>() {
        @Override
        public PayServices createFromParcel(Parcel in) {
            return new PayServices(in);
        }

        @Override
        public PayServices[] newArray(int size) {
            return new PayServices[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (base ? 1 : 0));
        dest.writeByte((byte) (installment1 ? 1 : 0));
        dest.writeByte((byte) (installment2 ? 1 : 0));
        dest.writeByte((byte) (installment3 ? 1 : 0));
        dest.writeByte((byte) (allpay ? 1 : 0));
        dest.writeString(cmd);
    }

    public boolean isBase() {
        return base;
    }

    public void setBase(boolean base) {
        this.base = base;
    }

    public boolean isInstallment1() {
        return installment1;
    }

    public void setInstallment1(boolean installment1) {
        this.installment1 = installment1;
    }

    public boolean isInstallment2() {
        return installment2;
    }

    public void setInstallment2(boolean installment2) {
        this.installment2 = installment2;
    }

    public boolean isInstallment3() {
        return installment3;
    }

    public void setInstallment3(boolean installment3) {
        this.installment3 = installment3;
    }

    public boolean isAllpay() {
        return allpay;
    }

    public void setAllpay(boolean allpay) {
        this.allpay = allpay;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "PayServices{" +
                "base=" + base +
                ", installment1=" + installment1 +
                ", installment2=" + installment2 +
                ", installment3=" + installment3 +
                ", allpay=" + allpay +
                ", cmd='" + cmd + '\'' +
                '}';
    }
}
