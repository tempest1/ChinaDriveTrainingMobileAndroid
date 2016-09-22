package com.smartlab.drivetrain.interfaces;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.smartlab.drivetrain.model.WeiXinSend;
import com.smartlab.drivetrain.util.HttpEngine;
import com.smartlab.drivetrain.util.LogUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smartlab on 15/11/20.
 * 接口实现
 */
public class ApiImpl implements Api {
    private Gson gson = new Gson();
    private HttpEngine httpEngine;

    public ApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }
    /**
     * 请求验证码
     *
     * @param cmd   请求类型
     * @param phone 手机号码
     * @return {"cmd":"mesIdCode","mesCode":"31344af887d00c7a6d60dd025186d531","res":"true"}
     */
    @Override
    public ApiResponse<Void> validPhone(String cmd, String phone,String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("phone", phone);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT, ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }
    /**
     * 请求验证码
     *
     * @param cmd   请求类型
     * @param phone 手机号码
     * @return {"cmd":"mesIdCode","mesCode":"31344af887d00c7a6d60dd025186d531","res":"true"}
     */
    @Override
    public ApiResponse<Void> validPhones(String cmd, String phone,String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("phone", phone);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT, ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * 登录
     *{"phone":"13554555772","cmd":"login","password":"123456"}
     * @param cmd      请求类型
     * @param phone    用户名
     * @param password 密码
     * @param url      地址
     * @return {"cmd":"login","res":true}
     */
    @Override
    public ApiResponse<Void> login(String cmd, String phone, String password, String url) {

        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("phone",phone);
        map.put("password", password);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * 退出登录
     * @param cmd {"cmd":"loginOut"}
     * @return {"cmd":"loginOut","res":true}
     */
    @Override
    public ApiResponse<Void> loginOut(String cmd,String url) {

        Map<String ,String> map = new HashMap<>();
        map.put("cmd", cmd);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());

        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd        请求类型
     * @param type       信息类型
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param pageNum    每页显示的数量
     * @param pagination 当前页码
     * @return 返回Info对象
     */
    @Override
    public ApiResponse<List<Info>> loadInfo(String cmd, String type, String startTime, String endTime, int pageNum, int pagination,String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("type",type);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("pageNum",String.valueOf(pageNum));
        map.put("pagination",String.valueOf(pagination));

        Type type1 = new TypeToken<ApiResponse<List<Info>>>(){}.getType();
        LogUtils.i("tpye1" + type1.toString());
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type1);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd      请求类型
     * @param phone    电话号码
     * @param password 密码
     * @param url      地址
     * @return 返回Void Void is Type Null
     */
    @Override
    public ApiResponse<Void> registerNew(String cmd, String phone, String password, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("phone",phone);
        map.put("password", password);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd   请求类型
     * @param phone 电话号码
     * @param url   服务器地址
     * @return String
     */
    @Override
    public ApiResponse<String> requestCode(String cmd, String phone, String url) {

        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("phone", phone);
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd {"cmd":"getUserInfo"}
     * @param url 地址
     * @return 返回用户详细信息{"cmd":"getUserInfo","idCard":"null","mail":"null","password":"123456","phone":"18612343224","realName":"null","res":"true","timeStamp":1447930965398,"userInfo":{"registTime":"null"}}
     */
    @Override
    public User getUserDetail(String cmd, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);

        Type type = new TypeToken<User>(){}.getType();
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new User(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd       "cmd":"userQueryDetailsInfo"
     * @param timeStamp "timeStamp":1443175124283
     * @param url       地址
     * @return 新闻详情
     */
    @Override
    public ApiResponse<List<InfoContent>> loadInfoDetail(String cmd, String timeStamp, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timeStamp", timeStamp);

        Type type = new TypeToken<ApiResponse<List<InfoContent>>>(){}.getType();
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd {"cmd":"showPlateList"}
     * @param url 地址
     * @return {"cmd":"showPlateList","plateList":[{"createTime":"2015-09-19 13:00:05","description":"新手指南和交流","timestamp":101,"title":"新手专
     * "},{"createTime":"2015-09-19 16:00:08","description":"客服解答问题","timestamp":102,"title":"客服专区"},{"createTime":"2015-09-19 00:00:00"
     * "description":"意见与建议","timestamp":103,"title":"意见专区"},{"createTime":"2015-09-19 18:22:00","description":"畅所欲言","timestamp":104
     * "title":"交流专区"}],"res":"true"}
     */
    @Override
    public ApiResponse<List<Frunm>> loadBank(String cmd, String url) {
        Map<String ,String> map = new HashMap<>();
        map.put("cmd",cmd);

        Type type = new TypeToken<ApiResponse<List<Frunm>>>(){}.getType();

        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd        {"cmd":"showCardList","pagination":1,"pageNum":10,"timestamp":"101"}
     * @param pagination "pagination":1
     * @param pageNum    "pageNum":10
     * @param timestamp  "timestamp":"101"
     * @param url        地址
     * @return {"cardList":[{"createTime":"2015-03-13 00:00:00","name":"13997554860","timestamp":1001,"title":"新手学车应该注意什么?"},{"createTime":"2015-03-13 00:00:00","name":"13997554860","timestamp":1002,"title":"如何学好科目二?"},{"createTime":"2015-03-13 00:00:00","name":"13997554860","timestamp":1003,"title":"科目一怎么快速记忆?"},{"createTime":"2015-09-24 13:01:12","name":"13997554860","timestamp":1443070872568,"title":"下雨了如何考试"},{"createTime":"2015-09-24 13:04:25","name":"13997554860","timestamp":1443071065276,"title":"科三怎么一次过"},{"createTime":"2015-09-24 14:40:19","name":"13997554860","timestamp":1443076819021,"title":"求快速通过科目三"},{"createTime":"2015-09-24 14:56:49","name":"13997554860","timestamp":1443077809871,"title":"很开心，终于拿到驾照了"},{"createTime":"2015-09-24 14:58:46","name":"13997554860","timestamp":1443077926590,"title":"马上就要拿驾照了"},{"createTime":"2015-09-24 15:01:08","name":"13997554860","timestamp":1443078068281,"title":"科三顺利通过，求贺电"},{"createTime":"2015-09-24 16:19:02","name":"13997554860","timestamp":1443082742132,"title":"新手如和学车"}],"cmd":"showCardList","res":"true"}
     */
    @Override
    public ApiResponse<List<Card>> loadCard(String cmd, int pagination, int pageNum, String timestamp, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("pagination",String.valueOf(pagination));
        map.put("pageNum",String.valueOf(pageNum));
        map.put("timestamp",String.valueOf(timestamp));

        Type type = new TypeToken<ApiResponse<List<Card>>>(){}.getType();
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd        {"cmd":"showPostList","timestamp":"1001","pagination":1,"userName":"18612343224","pageNum":12}
     * @param timestamp  "timestamp":"1001"
     * @param pagination "pagination":1
     * @param userName   "userName":"18612343224"
     * @param pageNum    "pageNum":12
     * @param url        地址
     * @return {"cmd":"showPostList","postList":[{"content":"<p><span style=\"word-wrap: break-word; font-weight: 700; color: rgb(68, 68, 68);
     * font-family: Tahoma, &#39;Microsoft Yahei&#39;, Simsun; font-size: 14px; line-height: 21px; background-color: rgb(255, 255, 255);\">一戒急于
     * 干线</span></p>","createTime":"2015-09-25 15:07:21","name":"13997554860","timestamp":1443164841924}],"res":"true"}
     */
    @Override
    public ApiResponse<List<CardResponse>> loadPostList(String cmd, String timestamp, int pagination, String userName, int pageNum, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp",timestamp);
        map.put("pagination",String.valueOf(pagination));
        map.put("userName",userName);
        map.put("pageNum",String.valueOf(pageNum));

        Type type = new TypeToken<ApiResponse<List<CardResponse>>>(){}.getType();
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * 回复帖子
     *
     * @param cmd       "cmd":"addPost"
     * @param timestamp "timestamp":"1001"
     * @param time      "time":"1448096928"
     * @param name      "name":"18612343224"
     * @param content   ,"content":"123456"
     * @param url       地址
     * @return {"cmd":"addPost","res":"true"}
     */
    @Override
    public ApiResponse<Void> sendReply(String cmd, String timestamp, String time, String name, String content, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp",timestamp);
        map.put("time",time);
        map.put("name",name);
        map.put("content",content);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();

        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json, url, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<String> sendPicture(String filePath, String url) {
        Type type = new TypeToken<ApiResponse<String>>(){}.getType();
        try {
            return httpEngine.fileUpLoad(filePath, url, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param url     地址
     * @return {}
     */
    @Override
    public ApiResponse<Void> sendAppointOrder(String cmd,String km,String name,String IDcard,String carType,String train,String coach,String appoinmentTime,String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("km",km);
        map.put("name",name);
        map.put("IDcard", IDcard);
        map.put("carType",carType);
        map.put("train", train);
        map.put("coach",coach);
        map.put("appoinmentTime",appoinmentTime);

        String json = gson.toJson(map);
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type:" + type.toString());
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> modifyUserInfo(String jsonString, String url) {
        Type type = new TypeToken<ApiResponse<Void>>() {}.getType();
        try {
            return httpEngine.postHandle(jsonString,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param json Json字符串
     * @param url  地址
     * @return []
     */
    @Override
    public ApiResponse<List<Question>> getExams(String json, String url) {
        Type type = new TypeToken<ApiResponse<List<Question>>>(){}.getType();
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List<SchoolItem>> getDrivingList(String cmd,int citycode,int pagination,int pageNum,String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        String code = String.valueOf(citycode);
        map.put("citycode",code.substring(0,code.length()-2));
        map.put("pagination",String.valueOf(pagination));
        map.put("pageNum",String.valueOf(pageNum));

        String json = gson.toJson(map);
        Type type = new TypeToken<ApiResponse<List<SchoolItem>>>(){}.getType();
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public SchoolDetail<List<DrivingComment>> getDrivingDetail(String cmd,long timestamp,String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp",String.valueOf(timestamp));

        String json = gson.toJson(map);
        Type type = new TypeToken<SchoolDetail<List<DrivingComment>>>(){}.getType();
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new SchoolDetail< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List<SchoolItem>> getAll_DrivingList(String cmd, int pagination, int pageNum, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("pagination",String.valueOf(pagination));
        map.put("pageNum",String.valueOf(pageNum));
        String json = gson.toJson(map);
        Type type = new TypeToken<ApiResponse<List<SchoolItem>>>(){}.getType();

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd       cmd getServiceByUserId
     * @param timestamp 驾校时间戳
     * @param url       地址
     * @return 返回BuyService对象
     */
    @Override
    public ApiResponse<List<HaveServices>> getServiceByUserId(String cmd, String timestamp, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp",timestamp);
        String json = gson.toJson(map);

        Type type = new TypeToken<ApiResponse<List<HaveServices>>>(){}.getType();

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd       获取驾校的产品
     * @param timestamp 驾校的时间戳
     * @param url       地址
     * @return 返回产品对象Product
     */
    @Override
    public Product getProductByDrivingId(String cmd, String timestamp, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp",timestamp);

        String json = gson.toJson(map);
        Type type = new TypeToken<Product>(){}.getType();

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new Product(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd          cmd pay
     * @param timestamp    产品时间戳
     * @param allpay       全部支付
     * @param base         基础包
     * @param installment1 是否是分期一
     * @param installment2 是否是分期二
     * @param installment3 是否是分期三
     * @param url          地址
     * @return sHtmlText html对象
     */
    @Override
    public ApiResponse<String> pay(String cmd, String timestamp, boolean allpay, boolean base, boolean installment1, boolean installment2, boolean installment3, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp",timestamp);
        map.put("allpay",String.valueOf(allpay));
        map.put("base",String.valueOf(base));
        map.put("installment1",String.valueOf(installment1));
        map.put("installment2",String.valueOf(installment2));
        map.put("installment3",String.valueOf(installment3));

        String json = gson.toJson(map);
        Type type = new TypeToken<ApiResponse<String>>(){}.getType();

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd        getDrivingLatLngByCityCode
     * @param citycode   城市编码
     * @param pagination 页码
     * @param pageNum    每页数量
     * @param url        服务器地址
     * @return LatlngList 对象<br/>将驾校经纬度标注到地图上面
     */
    @Override
    public ApiResponse<List<LatlngList>> drivingLatLng(String cmd, String citycode, int pagination, int pageNum, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        String code = String.valueOf(citycode);
        map.put("citycode",code.substring(0, code.length() - 2));
        map.put("pagination",String.valueOf(pagination));
        map.put("pageNum",String.valueOf(pageNum));

        String json = gson.toJson(map);
        Type type = new TypeToken<ApiResponse<List<LatlngList>>>(){}.getType();
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd       getOrderByUserId
     * @param timestamp 用户timeStamp
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param url       服务端地址
     * @return List<Orders>
     */
    @Override
    public ApiResponse<List<Orders>> getOrderByUserId(String cmd, String timestamp, String startTime, String endTime, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp",timestamp);
        map.put("startTime",startTime);
        map.put("endTime",endTime);

        String json = gson.toJson(map);
        Type type = new TypeToken<ApiResponse<List<Orders>>>(){}.getType();

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param json json对象
     * @return 返回ApiResponse
     */
    @Override
    public ApiResponse<Void> addComplain(String json,String url) {
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }
    /**
     * 获取评论类型
     *
     * @param cmd 请求类型
     * @param url 地址
     * @return 返回 List<ComplainType>
     */
    @Override
    public ApiResponse<List<ComplainType>> getComplainTypeList(String cmd, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        String json = gson.toJson(map);
        Type type = new TypeToken<ApiResponse<List<ComplainType>>>(){}.getType();

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd     removeOrderByOrderId
     * @param orderId orderId
     * @param url     url
     * @return Void
     */
    @Override
    public ApiResponse<Void> removeOrderByOrderId(String cmd, String orderId, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("orderId",orderId);
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd getComplainList
     * @param url 地址
     * @return 返回
     */
    @Override
    public ApiResponse<List<FeedBack>> getComplainList(String cmd, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        Type type = new TypeToken<ApiResponse<List<FeedBack>>>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * 获取评论对象
     *
     * @param cmd           getEvaluateType
     * @param userTimestamp userTimestamp
     * @param url           地址
     * @return EvaluateType
     */
    @Override
    public EvaluateModel getEvaluateType(String cmd, String userTimestamp, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("userTimestamp",userTimestamp);
        String json = gson.toJson(map);
        Type type = new TypeToken<EvaluateModel>(){}.getType();
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new EvaluateModel(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * 添加评论
     *
     * @param json json对象
     * @param url  地址
     * @return ApiResponse
     */
    @Override
    public ApiResponse<Void> addEvaluate(String json, String url) {
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * 获取我的评论列表
     *
     * @param cmd getEvaluateByUserId
     * @param url 地址
     * @return ApiResponse
     */
    @Override
    public ApiResponse<List<EvaluateBack>> getEvaluateByUserId(String cmd, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);

        String json = gson.toJson(map);
        Type type = new TypeToken<ApiResponse<List<EvaluateBack>>>(){}.getType();
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd      addFeedback
     * @param feedback feedback
     * @param id       id
     * @param url      服务器地址
     * @return Void
     */
    @Override
    public ApiResponse<Void> addFeedback(String cmd, String feedback, String id, String url) {
        Map<String ,String > map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("feedback",feedback);
        map.put("id",id);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd getConsultTypeList
     * @param url 地址
     * @return 返回ComplainType
     */
    @Override
    public ApiResponse<List<ComplainType>> getConsultTypeList(String cmd, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        Type type = new TypeToken<ApiResponse<List<ComplainType>>>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param url 地址
     * @return 返回
     */
    @Override
    public ApiResponse<Void> addConsult(String json, String url) {
        Type type1 = new TypeToken<ApiResponse<Void>>(){}.getType();

        try {
            return httpEngine.postHandle(json,url,type1);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd getConsultByUserId
     * @param url 地址
     * @return 返回
     */
    @Override
    public ApiResponse<List<ConsultBean>> getConsultByUserId(String cmd, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);

        Type type = new TypeToken<ApiResponse<List<ConsultBean>>>(){}.getType();
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd       getSchoolByUserId
     * @param timestamp UserTimestamp
     * @param url       地址
     * @return StatusSchool
     */
    @Override
    public StatusSchool getSchoolByUserId(String cmd, String timestamp, String url) {
        Map<String ,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp", timestamp);
        Type type = new TypeToken<StatusSchool>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new StatusSchool(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd            appoinmentCoach
     * @param userTimestamp  userTimeStamp
     * @param coachTimestamp coachTimestamp
     * @param time           时间
     * @param url            地址
     * @return Void
     */
    @Override
    public ApiResponse<Void> appoinmentCoach(String cmd, String userTimestamp, String coachTimestamp, String time, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("userTimestamp",userTimestamp);
        map.put("coachTimestamp",coachTimestamp);
        map.put("time",time);
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd userAppointmentList
     * @param url 地址
     * @return List<MyAppointment>
     */
    @Override
    public ApiResponse<List<MyAppointment>> userAppointmentList(String cmd, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        Type type = new TypeToken<ApiResponse<List<MyAppointment>>>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd       getMyQRcode
     * @param timestamp 用户时间戳
     * @param url
     * @return 返回二维码地址
     */
    @Override
    public ApiResponse<Void> getMyQRCode(String cmd, String timestamp, String url) {
        Map<String, String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("timestamp",timestamp);
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse<>(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }
    /**添加广告
     * @param cmd        请求类型
     * @param type       信息类型
     * @param pageNum    每页显示的数量
     * @param pagination 当前页码
     * @return 返回Info对象
     * "userQueryAllInfo", pagination: 1, pageNum: 10, type: "ad"
     */
    @Override
    public ApiResponse<List<Info>> loadAdvert(String cmd, String type,int pageNum, int pagination,String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("type",type);
        map.put("pageNum",String.valueOf(pageNum));
        map.put("pagination",String.valueOf(pagination));

        Type type1 = new TypeToken<ApiResponse<List<Info>>>(){}.getType();
        LogUtils.i("tpye1" + type1.toString());
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type1);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     * @param cmd       请求类型
     * @param title     帖子内容
     * @param timestamp 帖子时间戳
     * @param url       服务器地址
     * @return 返回内容
     */
    @Override
    public ApiResponse<Void> addCard(String cmd, String title, String timestamp, String url) {
        Map<String, String> map = new HashMap<>();
        map.put("cmd", cmd);
        map.put("title", title);
        map.put("timestamp", timestamp);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        String json = gson.toJson(map);

        try {
            return httpEngine.postHandle(json, url, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT, ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**找回密码
     * @param cmd      请求类型
     * @param phone    电话号码
     * @param password 密码
     * @param url      地址
     * @return 返回Void Void is Type Null
     */
    @Override
    public ApiResponse<Void> reDiesgin(String cmd, String phone, String password, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("phone",phone);
        map.put("password", password);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());
        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }
    /**
     *  微信注入
     * @param cmd 请求类型
     * @param code
     * @return 返回Void Void is Type Null
     */
    public ApiResponse<Void> WeiXinInfoByCode(String cmd,String code,String url)
    {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("code", code);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());
        String json = gson.toJson(map);
        Log.e("sys","WeiXinInfoByCode");
        LogUtils.i("sys"+json);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }
    /** 检查用户是否已经被绑定
     * @param cmd      请求类型
     * @param openid

     * @return 返回Void Void is Type Null
     */
    @Override
    public ApiResponse<Void> WeiXinCheckUserhasBind(String cmd, String openid, String url) {
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("openid", openid);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());
        String json = gson.toJson(map);
        LogUtils.i("sys"+json);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }
    /**
     *  用户绑定
     * @param cmd 请求类型
     * @param openid
     * @param phone
     * @return 返回Void Void is Type Null
     */
    @Override
        public ApiResponse<Void> WeiXinMakeBinding(String cmd,String openid,String phone,String url){
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("openid",openid);
        map.put("phone", phone);
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());

        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }
    /**
     *  微信一键登录
     * @param cmd 请求类型
     * @param openid
     * @return 返回Void Void is Type Null
     */
    @Override
    public ApiResponse<Void> WeiXinLoginByOpenId(String cmd,String openid,String url){
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("openid",openid);
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());

        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

    /**
     *  检查Phone
     * @param cmd 请求类型
     * @param phone
     * @return 返回Void Void is Type Null
     */
    @Override
    public ApiResponse<Void> WeiXinCheckWeixinPhone(String cmd,String phone,String url){
        Map<String,String> map = new HashMap<>();
        map.put("cmd",cmd);
        map.put("phone",phone);
        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        LogUtils.i("type" + type.toString());

        String json = gson.toJson(map);
        try {
            return httpEngine.postHandle(json,url,type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse< >(ErrorEvent.TIME_OUT_EVENT,ErrorEvent.TIME_OUT_EVENT_MSG);
        }
    }

}
