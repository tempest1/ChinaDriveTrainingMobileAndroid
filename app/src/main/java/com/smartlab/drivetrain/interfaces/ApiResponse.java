package com.smartlab.drivetrain.interfaces;

/**
 * Created by smartlab on 15/11/20.
 * 服务器回应
 */
public class ApiResponse<T> {
    private String cmd;         //  返回请求类型
    private String event;       //  返回码，0为成功
    private String error;       //  错误信息
    private boolean res;        //  返回状态
    private T info;             //  新闻对象
    private String mesCode;     //  验证码
    private T plateList;        //  论坛板块
    private T cardList;         //  帖子列表
    private T postList;         //  回复列表
    private String mesIdCode;   //  注册短信验证
    private T exerciseList;     //  考试题目
    private T drivingList;      //  驾校列表
    private T serviceList;      //  学员服务
    private T sHtmlText;        //  支付宝回调的html
    private T orderList;        //  订单列表
    private T complainTypeList; //  评论类型
    private T complainList;     //  投诉列表
    private T evaluateList;     //  评论列表
    private String uuid;        //  图片的名称
    private T consultTypeList;  //  咨询类型
    private T consultList;      //  资讯历史
    private T appointmentList;  //  用户查询预约列表
    private String QRURL;       //  我的二维码地址

    private T advert;           //广告对象
    private T weiXinsend;       //检查用户是否已经被绑定

    public T getWeiXinsend() {
        return weiXinsend;
    }

    public void setWeiXinsend(T weiXinsend) {
        this.weiXinsend = weiXinsend;
    }

    public ApiResponse(String event, String error) {
        this.event = event;
        this.error = error;
    }

    public T getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(T appointmentList) {
        this.appointmentList = appointmentList;
    }

    public T getEvaluateList() {
        return evaluateList;
    }

    public void setEvaluateList(T evaluateList) {
        this.evaluateList = evaluateList;
    }

    public T getComplainTypeList() {
        return complainTypeList;
    }

    public void setComplainTypeList(T complainTypeList) {
        this.complainTypeList = complainTypeList;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess(){
        return res;
    }

    public boolean getRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public String getMesCode() {
        return mesCode;
    }

    public void setMesCode(String mesCode) {
        this.mesCode = mesCode;
    }

    public T getPlateList() {
        return plateList;
    }

    public void setPlateList(T plateList) {
        this.plateList = plateList;
    }

    public T getCardList() {
        return cardList;
    }

    public void setCardList(T cardList) {
        this.cardList = cardList;
    }

    public T getPostList() {
        return postList;
    }

    public void setPostList(T postList) {
        this.postList = postList;
    }

    public String getMesIdCode() {
        return mesIdCode;
    }

    public void setMesIdCode(String mesIdCode) {
        this.mesIdCode = mesIdCode;
    }

    public T getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(T exerciseList) {
        this.exerciseList = exerciseList;
    }

    public T getDrivingList() {
        return drivingList;
    }

    public void setDrivingList(T drivingList) {
        this.drivingList = drivingList;
    }

    public T getServiceList() {
        return serviceList;
    }

    public void setServiceList(T serviceList) {
        this.serviceList = serviceList;
    }

    public T getsHtmlText() {
        return sHtmlText;
    }

    public void setsHtmlText(T sHtmlText) {
        this.sHtmlText = sHtmlText;
    }

    public T getOrderList() {
        return orderList;
    }

    public void setOrderList(T orderList) {
        this.orderList = orderList;
    }

    public T getComplainList() {
        return complainList;
    }

    public void setComplainList(T complainList) {
        this.complainList = complainList;
    }

    public T getConsultTypeList() {
        return consultTypeList;
    }

    public void setConsultTypeList(T consultTypeList) {
        this.consultTypeList = consultTypeList;
    }

    public T getConsultList() {
        return consultList;
    }

    public void setConsultList(T consultList) {
        this.consultList = consultList;
    }

    public String getQRURL() {
        return QRURL;
    }

    public void setQRURL(String QRURL) {
        this.QRURL = QRURL;
    }

    public T getAdvert() {
        return advert;
    }

    public void setAdvert(T info) {
        this.advert = advert;
    }



    @Override
    public String toString() {
        return "ApiResponse{" +
                "cmd='" + cmd + '\'' +
                ", event='" + event + '\'' +
                ", error='" + error + '\'' +
                ", res=" + res +
                ", info=" + info +
                ", mesCode='" + mesCode + '\'' +
                ", plateList=" + plateList +
                ", cardList=" + cardList +
                ", postList=" + postList +
                ", mesIdCode='" + mesIdCode + '\'' +
                ", exerciseList=" + exerciseList +
                ", drivingList=" + drivingList +
                ", serviceList=" + serviceList +
                ", sHtmlText=" + sHtmlText +
                ", orderList=" + orderList +
                ", complainTypeList=" + complainTypeList +
                ", complainList=" + complainList +
                ", evaluateList=" + evaluateList +
                ", uuid='" + uuid + '\'' +
                ", consultTypeList=" + consultTypeList +
                ", consultList=" + consultList +
                ", appointmentList=" + appointmentList +

                ", advert='" + advert + '\'' +
                '}';
    }
}
