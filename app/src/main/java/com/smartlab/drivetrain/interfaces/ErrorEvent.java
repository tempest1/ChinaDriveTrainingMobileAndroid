package com.smartlab.drivetrain.interfaces;

/**
 * Created by smartlab on 15/11/20.
 * 异常错误
 */
public class ErrorEvent {
    static final public String PARAM_NULL = "PARAM_NULL"; // 参数为空
    static final public String PARAM_ILLEGAL = "PARAM_ILLEGAL"; // 参数不合法
    static final public String TIME_OUT_EVENT = "TIME_OUT_EVENT";
    static final public String TIME_OUT_EVENT_MSG = "网络连接超时";
    static final public String ERROR_REQUEST = "ERROR_REQUEST";
    static final public String ERROR_REQUEST_MSG = "请求参数有误";
    static final public String ERROR_SERVER = "ERROR_SERVER";
    static final public String ERROR_SERVER_MSG = "获取失败";
    static final public String ERROR_EVENT = "";
}
