package com.smartlab.drivetrain.interfaces;

/**
 * Created by smartlab on 15/11/19.
 * 消息回调
 */
public interface ActionCallBackListener<T> {
    /**
     * 处理成功
     * @param data 返回数据
     */
    void onSuccess(T data);

    /**
     * 请求失败
     * @param errorEvent 错误码
     * @param message 错误详情
     */
    void onFailure(String errorEvent,String message);
}
