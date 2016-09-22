package com.smartlab.drivetrain.interfaces;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Card;
import com.smartlab.drivetrain.model.CardResponse;
import com.smartlab.drivetrain.model.ComplainType;
import com.smartlab.drivetrain.model.ConsultBean;
import com.smartlab.drivetrain.model.DrivingComment;
import com.smartlab.drivetrain.model.EvaluateBack;
import com.smartlab.drivetrain.model.EvaluateModel;
import com.smartlab.drivetrain.model.FeedBack;
import com.smartlab.drivetrain.model.Frunm;
import com.smartlab.drivetrain.model.HaveServices;
import com.smartlab.drivetrain.model.Info;
import com.smartlab.drivetrain.model.InfoContent;
import com.smartlab.drivetrain.model.LatlngList;
import com.smartlab.drivetrain.model.MyAppointment;
import com.smartlab.drivetrain.model.Orders;
import com.smartlab.drivetrain.model.Product;
import com.smartlab.drivetrain.model.Question;
import com.smartlab.drivetrain.model.SchoolDetail;
import com.smartlab.drivetrain.model.SchoolItem;
import com.smartlab.drivetrain.model.StatusSchool;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.LogUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smartlab on 15/11/19.
 * 核心实现
 */
public class AppActionImp implements AppAction {

    private Context context;
    private Api api;
    public AppActionImp(Context context) {
        this.context = context;
        this.api = new ApiImpl();
    }

    public String getString(int resID){
        return context.getResources().getString(resID);
    }
    /**
     * 注册
     *
     * @param cmd      请求类型
     * @param phone    电话号码
     * @param password 密码
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void registerNew(final String cmd, final String phone, final String password,
                            final String url,final ActionCallBackListener<Void> listener) {

        if (listener == null){
            LogUtils.e("注册方法的，监听为空");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            LogUtils.e("注册cmd不能空！");
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            LogUtils.e("phone is null");
            listener.onFailure(ErrorEvent.PARAM_NULL,"手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)){
            LogUtils.e("password is null");
            listener.onFailure(ErrorEvent.PARAM_NULL,"密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            LogUtils.e("服务器地址不能为空");
            listener.onFailure(ErrorEvent.PARAM_NULL,"请求的地址不能为空");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.registerNew(cmd, phone, password, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {     //  注册成功返回TRUE
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 电话号码是否有效
     *
     * @param cmd      请求类型
     * @param phone    电话号码
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void validPhone(final String cmd, final String phone, final String url,final ActionCallBackListener<Void> listener) {
        //  参数检验
        if (TextUtils.isEmpty(phone)){
            if (listener != null){
                listener.onFailure(ErrorEvent.PARAM_NULL, "手机号为空");
            }
            return ;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.validPhone(cmd, phone, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    LogUtils.e(response.toString());
                    if (!response.isSuccess()) {    //  若返回true标示服务端数据库中存在该手机号码
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }
    /**
     * 电话号码在数据库中
     *
     * @param cmd      请求类型
     * @param phone    电话号码
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void validPhones(final String cmd, final String phone, final String url,final ActionCallBackListener<Void> listener) {
        //  参数检验
        if (TextUtils.isEmpty(phone)){
            if (listener != null){
                listener.onFailure(ErrorEvent.PARAM_NULL, "手机号为空");
            }
            return ;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.validPhones(cmd, phone, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null && response != null) {
                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {    //  若返回true标示服务端数据库中存在该手机号码
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }
    /**
     * 登录
     *
     * @param cmd      请求类型
     * @param phone 用户名
     * @param password 密码
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void login(final String cmd, final String phone, final String password,
                      final String url,final ActionCallBackListener<Void> listener) {
        //  参数检测
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"请求类型不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"账户名不能为空");
                return;
            } else {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(phone);
            if (!m.matches()){
                listener.onFailure(ErrorEvent.PARAM_ILLEGAL,"手机号格式不正确");
                return;
            }
        }
        if (TextUtils.isEmpty(password)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"地址不能为空");
            return;
        }
        LogUtils.e("准备请求网络");
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.login(cmd, phone, password, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();

    }

    @Override
    public void loginOut(final String cmd, final String url,final ActionCallBackListener<Void> listener) {
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型参数不能为空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"请求地址不能为空");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.loginOut(cmd, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();

    }

    /**
     * 请求用户详细信息
     *
     * @param cmd      请求类型 getUserInfo
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void getUserDetail(final String cmd, final String url,final ActionCallBackListener<User> listener) {
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型参数不能为空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"请求地址不能为空");
            return;
        }
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                return api.getUserDetail(cmd, url);
            }

            @Override
            protected void onPostExecute(User response) {
                if (response != null) {
                    LogUtils.e(response.toString());
                    if (response.isRes()) {
                        listener.onSuccess(response);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 获取验证码
     *
     * @param cmd      请求类型
     * @param phone    电话号码
     * @param url      地址
     * @param listener 回调监听
     */
    @Override
    public void requestCode(final String cmd, final String phone, final String url,final ActionCallBackListener<String> listener) {
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型参数不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"电话号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"请求地址不能为空");
            return;
        }

        new AsyncTask<Void, Void, ApiResponse<String>>() {
            @Override
            protected ApiResponse<String> doInBackground(Void... voids) {
                return api.requestCode(cmd, phone, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<String> response) {
                if (response != null) {
                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getMesCode());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 注册的验证码
     *
     * @param cmd      请求类型registerCode
     * @param phone    电话号码
     * @param url      地址
     * @param listener 监听
     */
    @Override
    public void registerCode(final String cmd,final String phone,final String url,final ActionCallBackListener<String> listener) {
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型参数不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"电话号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"请求地址不能为空");
            return;
        }

        new AsyncTask<Void, Void, ApiResponse<String>>() {
            @Override
            protected ApiResponse<String> doInBackground(Void... voids) {
                return api.requestCode(cmd, phone, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<String> response) {
                if (response != null) {
                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getMesIdCode());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 请求新闻
     *
     * @param cmd        请求类型
     * @param pagination 页码
     * @param pageNum    每页数目
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param type       类型"news"
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void loadInfo(final String cmd, final int pagination, final int pageNum,
                         final String startTime, final String endTime, final String type,
                         final String url,final ActionCallBackListener<List<Info>> listener) {
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型参数不能为空");
            return;
        }
        if (pagination == 0){
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL,"页码不能为零");
            return;
        }
        if (pageNum == 0){
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL,"每页显示条数不能为零");
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<List<Info>>>() {
            @Override
            protected ApiResponse<List<Info>> doInBackground(Void... voids) {

                return api.loadInfo(cmd, type, startTime, endTime, pageNum, pagination, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Info>> response) {
                if (response != null) {
                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getInfo());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 加载新闻详情
     *
     * @param cmd       请求类型
     * @param timeStamp 新闻唯一标识
     */
    @Override
    public void loadInfoDetail(final String cmd,final String timeStamp,final String url,final ActionCallBackListener<List<InfoContent>> listener) {
        //  参数检验
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            LogUtils.e("cmd is null");
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型不能为空");
            return;
        }
        if (TextUtils.isEmpty(timeStamp)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"参数不能为空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"地址参数不能空");
            return;
        }

        new AsyncTask<Void, Void, ApiResponse<List<InfoContent>>>() {
            @Override
            protected ApiResponse<List<InfoContent>> doInBackground(Void... voids) {
                return api.loadInfoDetail(cmd, timeStamp, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<InfoContent>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getInfo());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 加载板块
     *
     * @param cmd      "showPlateList"
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void loadBank(final String cmd, final String url,final ActionCallBackListener<List<Frunm>> listener) {
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "参数不能空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"参数不能为空");
            return;
        }

        //
        new AsyncTask<Void, Void, ApiResponse<List<Frunm>>>() {
            @Override
            protected ApiResponse<List<Frunm>> doInBackground(Void... voids) {
                return api.loadBank(cmd, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Frunm>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getPlateList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 加载帖子列表
     *
     * @param cmd        请求类型
     * @param pagination 页码
     * @param pageNum    每页数目
     * @param timestamp  板块的唯一标示，用于查询板块内的所有帖子
     * @param url        请求的地址
     * @param listener   监听回调
     */
    @Override
    public void loadCard(final String cmd, final int pagination, final int pageNum,
                         final String timestamp, final String url,final ActionCallBackListener<List<Card>> listener) {
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "参数不能为空");
            return;
        }
        if (pagination <= 0){
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL,"每页显示数量不能小于0");
            return;
        }
        if (pageNum < 0){
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL, "页码不能小于0");
            return;
        }
        if (TextUtils.isEmpty(timestamp)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"参数不能空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            listener.onFailure(ErrorEvent.PARAM_NULL,"地址不能为空");
            return;
        }

        new AsyncTask<Void,Void,ApiResponse<List<Card>>>(){
            @Override
            protected ApiResponse<List<Card>> doInBackground(Void... voids) {
                return api.loadCard(cmd, pagination, pageNum, timestamp, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Card>> response) {
                if (response != null){
//                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getCardList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 帖子的回复列表
     *
     * @param cmd        请求类型
     * @param timestamp  帖子的唯一标示，类似于id的东西
     * @param pagination 页码
     * @param userName   登录的用户名
     * @param pageNum    每页条数
     * @param url        地址
     * @param listener   监听回调
     */
    @Override
    public void loadPostList(final String cmd, final String timestamp, final int pagination,
                             final String userName, final int pageNum, final String url,
                             final ActionCallBackListener<List<CardResponse>> listener) {

        new AsyncTask<Void,Void,ApiResponse<List<CardResponse>>>(){
            @Override
            protected ApiResponse<List<CardResponse>> doInBackground(Void... voids) {
                return api.loadPostList(cmd, timestamp, pagination, userName, pageNum, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<CardResponse>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getPostList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();

    }

    /**
     * 回复帖子
     *
     * @param cmd       请求类型addPost
     * @param timestamp 帖子唯一标示
     * @param time      回帖时间
     * @param name      回帖人姓名
     * @param content   回帖内容
     * @param url       地址
     * @param listener  回调监听
     */
    @Override
    public void sendReply(final String cmd,final  String timestamp, final String time,
                          final String name,final String content,final String url,final ActionCallBackListener<Void> listener) {

        new AsyncTask<Void,Void,ApiResponse<Void>>(){
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.sendReply(cmd, timestamp, time, name, content, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();

    }

    @Override
    public void sendPicture(final String filePath,final String url,final ActionCallBackListener<String> listener) {
        new AsyncTask<Void, Void, ApiResponse<String>>() {
            @Override
            protected ApiResponse<String> doInBackground(Void... params) {
                return api.sendPicture(filePath, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<String> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getUuid());
                    } else {
                        if (response.getError() != null) {
                            listener.onFailure(response.getError(), response.getError());
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd      请求类型
     * @param km       科目
     * @param name     真实姓名
     * @param IDcard   身份证号码
     * @param carType  考试车型
     * @param train    训练班次
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void sendAppointOrder(final String cmd, final String km, final String name, final String IDcard,
                                 final String carType, final String train, final String coach, final String appoinmentTime,
                                 final String url,final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void,Void,ApiResponse<Void>>(){
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.sendAppointOrder(cmd, km, name, IDcard, carType, train, coach, appoinmentTime, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();

    }

    /**
     * 修改用户信息
     *
     * @param jsonString jsonString
     * @param url        地址
     * @param listener   回调监听
     */
    @Override
    public void modifyUserInfo(final String jsonString,final String url, final ActionCallBackListener<Void> listener) {

        new AsyncTask<Void,Void,ApiResponse<Void>>(){
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.modifyUserInfo(jsonString, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 获取考试题目信息
     *
     * @param json     请求的json字符串
     * @param url      地址
     * @param listener 回调监听
     */
    @Override
    public void getExams(final String json,final String url,final ActionCallBackListener<List<Question>> listener) {
        new AsyncTask<Void,Void,ApiResponse<List<Question>>>(){
            @Override
            protected ApiResponse<List<Question>> doInBackground(Void... voids) {
                return api.getExams(json, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Question>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getExerciseList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getDrivingList(final String cmd,final int citycode,final int pagination,final int pageNum, final String url, final ActionCallBackListener<List<SchoolItem>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<SchoolItem>>>() {
            @Override
            protected ApiResponse<List<SchoolItem>> doInBackground(Void... params) {
                return api.getDrivingList(cmd, citycode, pagination, pageNum, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<SchoolItem>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getDrivingList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getDrivingDetail(final String cmd, final long timestamp, final String url, final ActionCallBackListener<SchoolDetail<List<DrivingComment>>> listener) {

        new AsyncTask<Void, Void, SchoolDetail<List<DrivingComment>>>() {
            @Override
            protected SchoolDetail<List<DrivingComment>> doInBackground(Void... params) {
                return api.getDrivingDetail(cmd, timestamp, url);
            }

            @Override
            protected void onPostExecute(SchoolDetail<List<DrivingComment>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isRes()){
                        listener.onSuccess(response);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getAll_DrivingList(final String cmd, final int pagination, final int pageNum, final String url, final ActionCallBackListener<List<SchoolItem>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<SchoolItem>>>() {
            @Override
            protected ApiResponse<List<SchoolItem>> doInBackground(Void... params) {
                return api.getAll_DrivingList(cmd, pagination, pageNum, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<SchoolItem>> response) {
                if (response != null){
                    LogUtils.i(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getDrivingList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    @Override
    public void getServiceByUserId(final String cmd, final String timestamp, final String url, final ActionCallBackListener<List<HaveServices>> listener) {

        new AsyncTask<Void, Void, ApiResponse<List<HaveServices>>>() {
            @Override
            protected ApiResponse<List<HaveServices>> doInBackground(Void... params) {
                return api.getServiceByUserId(cmd, timestamp, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<HaveServices>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getServiceList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 通过驾校id获取驾校旗下的产品
     *
     * @param cmd       cmd
     * @param timestamp timestamp时间戳
     * @param url       地址
     * @param listener  回调
     */
    @Override
    public void getProductByDrivingId(final String cmd, final String timestamp, final String url, final ActionCallBackListener<Product> listener) {

        new AsyncTask<Void, Void, Product>() {
            @Override
            protected Product doInBackground(Void... params) {
                return api.getProductByDrivingId(cmd, timestamp, url);
            }

            @Override
            protected void onPostExecute(Product response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isRes()){
                        listener.onSuccess(response);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                } else {
                    listener.onFailure(ErrorEvent.TIME_OUT_EVENT, ErrorEvent.TIME_OUT_EVENT_MSG);
                }
            }
        }.execute();
    }

    /**
     * @param url 地址
     * @param listener 回调
     */
    @Override
    public void pay(final String cmd, final String timestamp, final boolean allpay, final boolean base, final boolean installment1, final boolean installment2, final boolean installment3, final String url, final ActionCallBackListener<String> listener) {

        new AsyncTask<Void, Void, ApiResponse<String>>() {
            @Override
            protected ApiResponse<String> doInBackground(Void... params) {
                return api.pay(cmd, timestamp, allpay, base, installment1, installment2, installment3, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<String> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getsHtmlText());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd        getDrivingLatLngByCityCode
     * @param citycode   城市编码
     * @param pagination 页码
     * @param pageNum    每页数量
     * @param url        服务器地址
     * @param listener 回调
     * LatlngList 对象</br>将驾校经纬度标注到地图上面
     */
    @Override
    public void drivingLatLng(final String cmd, final String citycode, final int pagination, final int pageNum, final String url,final ActionCallBackListener<List<LatlngList>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<LatlngList>>>() {
            @Override
            protected ApiResponse<List<LatlngList>> doInBackground(Void... params) {
                return api.drivingLatLng(cmd, citycode, pagination, pageNum, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<LatlngList>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getDrivingList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd       getOrderByUserId
     * @param timestamp 用户timeStamp
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param url       服务端地址
     * @param listener  回调监听
     */
    @Override
    public void getOrderByUserId(final String cmd, final String timestamp, final String startTime, final String endTime, final String url, final ActionCallBackListener<List<Orders>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<Orders>>>() {
            @Override
            protected ApiResponse<List<Orders>> doInBackground(Void... params) {
                return api.getOrderByUserId(cmd, timestamp, startTime, endTime, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Orders>> response) {
                if (response != null) {
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getOrderList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    @Override
    public void addComplain(final String json, final String url, final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.addComplain(json, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,getString(R.string.commit_bad));
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd      getComplainTypeList
     * @param url      地址
     * @param listener 回调
     */
    @Override
    public void getComplainTypeList(final String cmd, final String url,final ActionCallBackListener<List<ComplainType>> listener) {

        new AsyncTask<Void, Void, ApiResponse<List<ComplainType>>>() {
            @Override
            protected ApiResponse<List<ComplainType>> doInBackground(Void... params) {
                return api.getComplainTypeList(cmd, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<ComplainType>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getComplainTypeList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd      removeOrderByOrderId
     * @param orderId  orderId
     * @param url URL
     * @param listener listener
     */
    @Override
    public void removeOrderByOrderId(final String cmd, final String orderId, final String url, final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.removeOrderByOrderId(cmd, orderId, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 获取投诉列表
     *
     * @param cmd      getComplainList
     * @param url      地址
     * @param listener 回调监听
     */
    @Override
    public void getComplainList(final String cmd, final String url, final ActionCallBackListener<List<FeedBack>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<FeedBack>>>() {
            @Override
            protected ApiResponse<List<FeedBack>> doInBackground(Void... params) {
                return api.getComplainList(cmd, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<FeedBack>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getComplainList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd           getEvaluateType
     * @param userTimestamp userTimestamp
     * @param url           地址
     * @param listener      监听
     */
    @Override
    public void getEvaluateType(final String cmd, final String userTimestamp, final String url, final ActionCallBackListener<EvaluateModel> listener) {
        new AsyncTask<Void, Void, EvaluateModel>() {
            @Override
            protected EvaluateModel doInBackground(Void... params) {
                return api.getEvaluateType(cmd, userTimestamp, url);
            }

            @Override
            protected void onPostExecute(EvaluateModel response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isRes()){
                        listener.onSuccess(response);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param json     json对象
     * @param url      地址
     * @param listener 回调
     */
    @Override
    public void addEvaluate(final String json, final String url, final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void,Void,ApiResponse<Void>>(){
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.addEvaluate(json, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,getString(R.string.commit_bad));
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 获取我的评论
     *
     * @param cmd      getEvaluateByUserId
     * @param url      地址
     * @param listener 回调
     */
    @Override
    public void getEvaluateByUserId(final String cmd, final String url,final ActionCallBackListener<List<EvaluateBack>> listener) {
        new AsyncTask<Void,Void,ApiResponse<List<EvaluateBack>>>(){

            @Override
            protected ApiResponse<List<EvaluateBack>> doInBackground(Void... params) {
                return api.getEvaluateByUserId(cmd, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<EvaluateBack>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getEvaluateList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 反馈
     *
     * @param cmd      addFeedback
     * @param feedback String
     * @param id       投诉id
     * @param url      地址
     * @param listener 回调
     */
    @Override
    public void addFeedback(final String cmd, final String feedback, final String id, final String url, final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void,Void,ApiResponse<Void>>(){
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.addFeedback(cmd, feedback, id, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    LogUtils.i(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd      getConsultTypeList
     * @param url      地址
     * @param listener 回调
     */
    @Override
    public void getConsultTypeList(final String cmd, final String url, final ActionCallBackListener<List<ComplainType>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<ComplainType>>>() {
            @Override
            protected ApiResponse<List<ComplainType>> doInBackground(Void... params) {
                return api.getConsultTypeList(cmd, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<ComplainType>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getConsultTypeList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     *
     * @param json AddConsult 对象
     * @param url 地址
     * @param listener 回调
     */
    @Override
    public void addConsult(final String json, final String url, final ActionCallBackListener<Void> listener) {

        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.addConsult(json, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd      getConsultByUserId
     * @param url      地 址
     * @param listener 回调
     */
    @Override
    public void getConsultByUserId(final String cmd, final String url, final ActionCallBackListener<List<ConsultBean>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<ConsultBean>>>() {
            @Override
            protected ApiResponse<List<ConsultBean>> doInBackground(Void... params) {
                return api.getConsultByUserId(cmd, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<ConsultBean>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getConsultList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 获取用户学籍
     *
     * @param cmd       getSchoolByUserId
     * @param timestamp UserTimestamp
     * @param url       地址
     * @param listener 回调
     */
    @Override
    public void getSchoolByUserId(final String cmd, final String timestamp, final String url, final ActionCallBackListener<StatusSchool> listener) {
        new AsyncTask<Void, Void, StatusSchool>() {
            @Override
            protected StatusSchool doInBackground(Void... params) {
                return api.getSchoolByUserId(cmd, timestamp, url);
            }

            @Override
            protected void onPostExecute(StatusSchool response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isRes()){
                        listener.onSuccess(response);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 用户学籍
     *
     * @param cmd            appoinmentCoach
     * @param userTimestamp  userTimeStamp
     * @param coachTimestamp coachTimestamp
     * @param time           时间
     * @param url            地址
     * @param listener
     */
    @Override
    public void appoinmentCoach(final String cmd, final String userTimestamp, final String coachTimestamp, final String time, final String url, final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.appoinmentCoach(cmd, userTimestamp, coachTimestamp, time, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd      userAppointmentList
     * @param url      地址
     * @param listener 回调
     */
    @Override
    public void userAppointmentList(final String cmd, final String url, final ActionCallBackListener<List<MyAppointment>> listener) {
        new AsyncTask<Void, Void, ApiResponse<List<MyAppointment>>>() {
            @Override
            protected ApiResponse<List<MyAppointment>> doInBackground(Void... params) {
                return api.userAppointmentList(cmd, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<MyAppointment>> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getAppointmentList());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * @param cmd       getMyQRcode
     * @param timestamp 用户时间戳
     * @param url       服务器地址
     * @param listener  回调监听
     */
    @Override
    public void getMyQRCode(final String cmd, final String timestamp, final String url, final ActionCallBackListener<String> listener) {
        new AsyncTask<Void,Void,ApiResponse<Void>>(){
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.getMyQRCode(cmd, timestamp, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null){
                    LogUtils.e(response.toString());
                    if (response.isSuccess()){
                        listener.onSuccess(response.getQRURL());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    /**
     * 请求广告
     *
     * @param cmd        请求类型
     * @param pagination 页码
     * @param pageNum    每页数目

     * @param type       类型"Advert"
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void loadAdvertisement(final String cmd, final int pagination, final int pageNum, final String type,
                                  final String url,final ActionCallBackListener<List<Info>> listener) {
        if (listener == null){
            LogUtils.e("listener is null");
            return;
        }
        if (TextUtils.isEmpty(cmd)){
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型参数不能为空");
            return;
        }
        if (pagination == 0){
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL,"页码不能为零");
            return;
        }
        if (pageNum == 0){
            listener.onFailure(ErrorEvent.PARAM_ILLEGAL,"每页显示条数不能为零");
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<List<Info>>>() {
            @Override
            protected ApiResponse<List<Info>> doInBackground(Void... voids) {

                return api.loadAdvert(cmd, type, pageNum, pagination, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<Info>> response) {

                if (response != null) {
                    try {
                        Log.e("onPostExecute", response.getInfo().get(0).getTitle());
                    }
                    catch (Exception e){
                        Log.e("sys","");
                    }


                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {
                        listener.onSuccess(response.getInfo());
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }
            }
        }.execute();
    }

    @Override
    public void addCard(final String cmd, final String title, final String timestamp, final String url, final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... params) {
                return api.addCard(cmd, title, timestamp, url);
            }
            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e("addcard" + response);
                    if (response.isSuccess()) {
                        LogUtils.e(response.toString());
                        if (response.isSuccess()) {     //  注册成功返回TRUE
                            listener.onSuccess(null);
                        } else {
                            if (response.getCmd() == null) {
                                if (response.getError() != null) {
                                    listener.onFailure(response.getError(), response.getError());
                                } else {
                                    listener.onFailure(ErrorEvent.ERROR_REQUEST,ErrorEvent.ERROR_REQUEST_MSG);
                                }
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_SERVER,ErrorEvent.ERROR_SERVER_MSG);
                            }
                        }
                    }
                }
            }
        }.execute();
    }
    /**
     * 找回密码
     *
     * @param cmd      请求类型
     * @param phone    电话号码
     * @param password 密码
     * @param url      请求地址
     * @param listener 回调监听
     */
    @Override
    public void reDiesgin(final String cmd, final String phone, final String password,
                            final String url,final ActionCallBackListener<Void> listener) {

        if (listener == null) {
            LogUtils.e("注册方法的，监听为空");
            return;
        }
        if (TextUtils.isEmpty(cmd)) {
            LogUtils.e("注册cmd不能空！");
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求类型不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            LogUtils.e("phone is null");
            listener.onFailure(ErrorEvent.PARAM_NULL, "手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            LogUtils.e("password is null");
            listener.onFailure(ErrorEvent.PARAM_NULL, "密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(url)) {
            LogUtils.e("服务器地址不能为空");
            listener.onFailure(ErrorEvent.PARAM_NULL, "请求的地址不能为空");
            return;
        }
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.reDiesgin(cmd, phone, password, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e("addcard" + response);
                    if (response.isSuccess()) {
                        LogUtils.e(response.toString());
                        if (response.isSuccess()) {     //  注册成功返回TRUE
                            listener.onSuccess(null);
                        } else {
                            if (response.getCmd() == null) {
                                if (response.getError() != null) {
                                    listener.onFailure(response.getError(), response.getError());
                                } else {
                                    listener.onFailure(ErrorEvent.ERROR_REQUEST, ErrorEvent.ERROR_REQUEST_MSG);
                                }
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_SERVER, ErrorEvent.ERROR_SERVER_MSG);
                            }
                        }
                    }
                }
            }
        }.execute();
    }
    @Override
    public void WeiXinInfoByCode(final String cmd, final String code, final String url,final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.WeiXinInfoByCode(cmd, code, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e("WeiXinInfoByCode" + response);

                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {     //  注册成功返回TRUE
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST, ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER, ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }

            }
        }.execute();
    }
    @Override
    public void WeiXinCheckUserhasBind(final String cmd, final String openid, final String url,final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.WeiXinCheckUserhasBind(cmd, openid, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e("WeiXinCheckUserhasBind" + response);

                        LogUtils.e(response.toString());
                        if (response.isSuccess()) {     //  注册成功返回TRUE
                            listener.onSuccess(null);
                        } else {
                            if (response.getCmd() == null) {
                                if (response.getError() != null) {
                                    listener.onFailure(response.getError(), response.getError());
                                } else {
                                    listener.onFailure(ErrorEvent.ERROR_REQUEST, ErrorEvent.ERROR_REQUEST_MSG);
                                }
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_SERVER, ErrorEvent.ERROR_SERVER_MSG);
                            }
                        }
                    }

            }
        }.execute();
    }
    @Override
    public void WeiXinMakeBinding(final String cmd,final String openid,final String phone,final String url,final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.WeiXinMakeBinding(cmd, openid, phone, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e("WeiXinCheckUserhasBind" + response);

                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {     //  注册成功返回TRUE
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST, ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {
                            listener.onFailure(ErrorEvent.ERROR_SERVER, ErrorEvent.ERROR_SERVER_MSG);
                        }
                    }
                }

            }
        }.execute();
    }
    @Override
    public void WeiXinLoginByOpenId(final String cmd,final String openid,final String url,final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.WeiXinLoginByOpenId(cmd, openid, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e("WeiXinCheckUserhasBind" + response);

                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {     //  注册成功返回TRUE
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST, ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {

                            listener.onFailure(ErrorEvent.ERROR_SERVER,  MainApplication.MESSAGE);
                        }
                    }
                }

            }
        }.execute();
    }
    @Override
    public void WeiXinCheckWeixinPhone(final String cmd,final String phone,final String url,final ActionCallBackListener<Void> listener) {
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                return api.WeiXinCheckWeixinPhone(cmd, phone, url);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (response != null) {
                    LogUtils.e("WeiXinCheckUserhasBind" + response);

                    LogUtils.e(response.toString());
                    if (response.isSuccess()) {     //  注册成功返回TRUE
                        listener.onSuccess(null);
                    } else {
                        if (response.getCmd() == null) {
                            if (response.getError() != null) {
                                listener.onFailure(response.getError(), response.getError());
                            } else {
                                listener.onFailure(ErrorEvent.ERROR_REQUEST, ErrorEvent.ERROR_REQUEST_MSG);
                            }
                        } else {

                            listener.onFailure(ErrorEvent.ERROR_SERVER,  MainApplication.MESSAGE);
                        }
                    }
                }

            }
        }.execute();
    }
}
