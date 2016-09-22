package com.smartlab.drivetrain.interfaces;

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

import java.util.List;

/**
 * Created by smartlab on 15/11/20.
 * 数据接口
 */
public interface Api {
    /**
     * 手机号是否有效
     * @param cmd 请求类型
     * @param phone 手机号码
     * @return  {"cmd":"validPhone","res":"true"}
     */
    ApiResponse<Void> validPhone(String cmd ,String phone,String url);



    /**
     * 手机号是否有效
     * @param cmd 请求类型
     * @param phone 手机号码
     * @return  {"cmd":"validPhone","res":"true"}
     */
    ApiResponse<Void> validPhones(String cmd ,String phone,String url);

    /**
     * 登录
     * @param cmd 请求类型
     * @param phone 用户名
     * @param url 地址
     * @return {"cmd":"login","res":true}
     */
    ApiResponse<Void> login(String cmd,String phone,String password,String url);

    /**
     * 退出登录
     * @param cmd {"cmd":"loginOut"}
     * @return {"cmd":"loginOut","res":true}
     */
    ApiResponse<Void> loginOut(String cmd,String url);

    /**
     *获取新闻
     * @param cmd 请求类型
     * @param type 信息类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 每页显示的数量
     * @param pagination 当前页码
     * @return 返回Info对象
     */
    ApiResponse<List<Info>> loadInfo(String cmd,String type,String startTime,String endTime,int pageNum,int pagination,String url);

    /**
     *  注册新用户
     * @param cmd 请求类型
     * @param phone 电话号码
     * @param password 密码
     * @param url 地址
     * @return 返回Void Void is Type Null
     */
    ApiResponse<Void> registerNew(String cmd,String phone,String password,String url);

    /**
     * 请求验证码
     * @param cmd 请求类型 {"cmd":"mesIdCode","phone":"18612343224"}
     * @param phone 电话号码
     * @param url 服务器地址
     * @return {"cmd":"mesIdCode","mesCode":"31344af887d00c7a6d60dd025186d531","res":"true"}
     */
    ApiResponse<String> requestCode(String cmd,String phone,String url);

//    /**
//     * 注册短信验证
//     * @param cmd 请求类型
//     * @param phone 电话号码
//     * @param url 地址
//     * @return 返回加密后的验证码信息
//     */
//    ApiResponse<String> registerCode(String cmd,String phone,String url);
    /**
     *
     * @param cmd {"cmd":"getUserInfo"}
     * @param url 地址
     * @return 返回用户详细信息{"cmd":"getUserInfo","idCard":"null","mail":"null","password":"123456","phone":"18612343224","realName":"null","res":"true","timeStamp":1447930965398,"userInfo":{"registTime":"null"}}
     */
    User getUserDetail(String cmd,String url);

    /**
     *
     * @param cmd "cmd":"userQueryDetailsInfo"
     * @param timeStamp "timeStamp":1443175124283
     * @param url 地址
     * @return 新闻详情
     */
    ApiResponse<List<InfoContent>> loadInfoDetail(String cmd,String timeStamp,String url);

    /**
     *
     * @param cmd {"cmd":"showPlateList"}
     * @param url 地址
     * @return {"cmd":"showPlateList","plateList":[{"createTime":"2015-09-19 13:00:05","description":"新手指南和交流","timestamp":101,"title":"新手专区"},{"createTime":"2015-09-19 16:00:08","description":"客服解答问题","timestamp":102,"title":"客服专区"},{"createTime":"2015-09-19 00:00:00","description":"意见与建议","timestamp":103,"title":"意见专区"},{"createTime":"2015-09-19 18:22:00","description":"畅所欲言","timestamp":104,"title":"交流专区"}],"res":"true"}
     */
    ApiResponse<List<Frunm>> loadBank(String cmd,String url);

    /**
     *
     * @param cmd {"cmd":"showCardList","pagination":1,"pageNum":10,"timestamp":"101"}
     * @param pagination "pagination":1
     * @param pageNum "pageNum":10
     * @param timestamp "timestamp":"101"
     * @param url 地址
     * @return {"cardList":[{"createTime":"2015-03-13 00:00:00","name":"13997554860","timestamp":1001,"title":"新手学车应该注意什么?"}
     * {"createTime":"2015-03-13 00:00:00","name":"13997554860","timestamp":1002,"title":"如何学好科目二?"}],"cmd":"showCardList","res":"true"}
     */
    ApiResponse<List<Card>> loadCard(String cmd,int pagination,int pageNum,String timestamp,String url);

    /**
     * @param cmd {"cmd":"showPostList","timestamp":"1001","pagination":1,"userName":"18612343224","pageNum":12}
     * @param timestamp "timestamp":"1001"
     * @param pagination "pagination":1
     * @param userName "userName":"18612343224"
     * @param pageNum "pageNum":12
     * @param url 地址
     * @return  {"cmd":"showPostList","postList":[{"content":"<p><span style=\"word-wrap: break-word; font-weight: 700; color: rgb(68, 68, 68);
     * font-family: Tahoma, &#39;Microsoft Yahei&#39;, Simsun; font-size: 14px; line-height: 21px; background-color: rgb(255, 255, 255);\">一戒急于上
     * 干线</span></p>","createTime":"2015-09-25 15:07:21","name":"13997554860","timestamp":1443164841924},{}]
     */
    ApiResponse<List<CardResponse>> loadPostList(String cmd,String timestamp,int pagination,String userName,int pageNum,String url);

    /**
     * 回复帖子
     * @param cmd  "cmd":"addPost"
     * @param timestamp "timestamp":"1001"
     * @param time "time":"1448096928"
     * @param name "name":"18612343224"
     * @param content ,"content":"123456"
     * @param url 地址
     * @return {"cmd":"addPost","res":"true"}
     */
    ApiResponse<Void> sendReply(String cmd,String timestamp,String time,String name,String content,String url);
    /**
     * 发送图片
     * @param filePath 文件路径列表
     * @param url 地址
     */
    ApiResponse<String> sendPicture(String filePath,String url);
    /**
     * @param url 地址
     * @return {}
     */
    ApiResponse<Void> sendAppointOrder(String cmd,String km,String name,String IDcard,String carType,String train,String coach,String appoinmentTime,String url);

    /**
     *
     * @param jsonString jsonString
     * @param url 地址
     * @return {}
     */
    ApiResponse<Void> modifyUserInfo(String jsonString,String url);

    /**
     *
     * @param json Json字符串
     * @param url 地址
     * @return {}
     */
    ApiResponse<List<Question>> getExams(String json,String url);

    /**
     *
     * @param cmd 请求类型
     * @param url 地址
     * @return []
     */
    ApiResponse<List<SchoolItem>> getDrivingList(String cmd,int citycode,int pagination,int pageNum,String url);

    /**
     *
     * @param cmd 请求类型
     * @param timestamp 驾校时间戳
     * @param url 地址
     * @return []
     */
    SchoolDetail<List<DrivingComment>> getDrivingDetail(String cmd,long timestamp,String url);

    /**
     *
     * @param cmd getDrivingList
     * @param pagination 页码
     * @param pageNum 每页数量
     * @param url 地址
     * @return 回调
     */
    ApiResponse<List<SchoolItem>> getAll_DrivingList(String cmd, int pagination, int pageNum, String url);

    /**
     *
     * @param cmd cmd getServiceByUserId
     * @param timestamp 驾校时间戳
     * @param url 地址
     * @return 返回BuyService对象
     */
    ApiResponse<List<HaveServices>> getServiceByUserId(String cmd,String timestamp,String url);

    /**
     *
     * @param cmd 获取驾校的产品
     * @param timestamp 驾校的时间戳
     * @param url 地址
     * @return 返回产品对象Product
     */
    Product getProductByDrivingId(String cmd,String timestamp,String url);

    /**
     *
     * @param cmd cmd pay
     * @param timestamp 产品时间戳
     * @param allpay 全部支付
     * @param base 基础包
     * @param installment1 是否是分期一
     * @param installment2 是否是分期二
     * @param installment3 是否是分期三
     * @param url 地址
     * @return sHtmlText html对象
     */
    ApiResponse<String> pay(String cmd,String timestamp,boolean allpay,boolean base,boolean installment1,boolean installment2,boolean installment3,String url);

    /**
     *
     * @param cmd getDrivingLatLngByCityCode
     * @param citycode 城市编码
     * @param pagination 页码
     * @param pageNum 每页数量
     * @param url 服务器地址
     * @return LatlngList 对象</br>将驾校经纬度标注到地图上面
     */
    ApiResponse<List<LatlngList>> drivingLatLng(String cmd,String citycode,int pagination,int pageNum,String url);

    /**
     *
     * @param cmd       getOrderByUserId
     * @param timestamp 用户timeStamp
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param url       服务端地址
     * @return List<Orders>
     */
    ApiResponse<List<Orders>> getOrderByUserId(String cmd, String timestamp, String startTime, String endTime, String url);

    /**
     *
     * @param json json对象
     * @return 返回ApiResponse
     */
    ApiResponse<Void> addComplain(String json,String url);

    /**
     * 获取评论类型
     * @param cmd 请求类型
     * @param url 地址
     * @return 返回 List<ComplainType>
     */
    ApiResponse<List<ComplainType>> getComplainTypeList(String cmd, String url);

    /**
     * 删除未付款订单
     * @param cmd removeOrderByOrderId
     * @param orderId orderId
     * @param url url
     * @return Void
     */
    ApiResponse<Void> removeOrderByOrderId(String cmd,String orderId,String url);

    /**
     *
     * @param cmd getComplainList
     * @param url 地址
     * @return 返回
     */
    ApiResponse<List<FeedBack>> getComplainList(String cmd,String url);

    /**
     * 获取评论对象
     * @param cmd getEvaluateType
     * @param userTimestamp userTimestamp
     * @param url 地址
     * @return EvaluateType
     */
    EvaluateModel getEvaluateType(String cmd,String userTimestamp,String url);

    /**
     * 添加评论
     * @param json json对象
     * @param url 地址
     * @return ApiResponse
     */
    ApiResponse<Void> addEvaluate(String json,String url);

    /**
     * 获取我的评论列表
     * @param cmd getEvaluateByUserId
     * @param url 地址
     * @return ApiResponse
     */
    ApiResponse<List<EvaluateBack>> getEvaluateByUserId(String cmd,String url);

    /**
     *
     * @param cmd addFeedback
     * @param feedback feedback
     * @param id id
     * @param url 服务器地址
     * @return Void
     */
    ApiResponse<Void> addFeedback(String cmd,String feedback,String id,String url);

    /**
     *
     * @param cmd getConsultTypeList
     * @param url 地址
     * @return 返回ComplainType
     */
    ApiResponse<List<ComplainType>> getConsultTypeList(String cmd,String url);

    /**
     * @param json 对象
     * @param url 地址
     * @return 返回
     */
    ApiResponse<Void> addConsult(String json,String url);

    /**
     *
     * @param cmd getConsultByUserId
     * @param url 地址
     * @return 返回
     */
    ApiResponse<List<ConsultBean>> getConsultByUserId(String cmd,String url);

    /**
     *
     * @param cmd getSchoolByUserId
     * @param timestamp UserTimestamp
     * @param url 地址
     * @return StatusSchool
     */
    StatusSchool getSchoolByUserId(String cmd,String timestamp,String url);

    /**
     *
     * @param cmd appoinmentCoach
     * @param userTimestamp userTimeStamp
     * @param coachTimestamp coachTimestamp
     * @param time 时间
     * @param url 地址
     * @return Void
     */
    ApiResponse<Void> appoinmentCoach(String cmd,String userTimestamp,String coachTimestamp,String time,String url);

    /**
     *
     * @param cmd userAppointmentList
     * @param url 地址
     * @return  List<MyAppointment>
     */
    ApiResponse<List<MyAppointment>> userAppointmentList(String cmd,String url);

    /**
     *
     * @param cmd getMyQRcode
     * @param timestamp 用户时间戳
     * @return 返回二维码地址
     */
    ApiResponse<Void> getMyQRCode(String cmd,String timestamp,String url);
    /**
     *获取广告
     * @param cmd 请求类型
     * @param type 信息类型
     * @param pageNum 每页显示的数量
     * @param pagination 当前页码
     * @return 返回Info对象
     */
    ApiResponse<List<Info>> loadAdvert(String cmd,String type,int pageNum,int pagination,String url);

    /**
     *
     * @param cmd 请求类型
     * @param title 帖子内容
     * @param timestamp 帖子时间戳
     * @param url 服务器地址
     * @return 返回内容
     */
    ApiResponse<Void> addCard(String cmd, String title, String timestamp, String url);

    /**
     *  找回密码
     * @param cmd 请求类型
     * @param phone 电话号码
     * @param password 密码
     * @param url 地址
     * @return 返回Void Void is Type Null
     */
    ApiResponse<Void> reDiesgin(String cmd,String phone,String password,String url);

    /**
     *  微信注入
     * @param cmd 请求类型
     * @param code
     * @return 返回Void Void is Type Null
     */
    ApiResponse<Void> WeiXinInfoByCode(String cmd,String code,String url);

    /**
     *  检查用户是否已经被绑定
     * @param cmd 请求类型
     * @param openid
     * @return 返回Void Void is Type Null
     */
    ApiResponse<Void> WeiXinCheckUserhasBind(String cmd,String openid,String url);
    /**
     *  用户绑定
     * @param cmd 请求类型
     * @param openid
     * @param phone
     * @return 返回Void Void is Type Null
     */
    ApiResponse<Void> WeiXinMakeBinding(String cmd,String openid,String phone,String url);
    /**
     *  微信一键登录
     * @param cmd 请求类型
     * @param openid
     * @return 返回Void Void is Type Null
     */
    ApiResponse<Void> WeiXinLoginByOpenId(String cmd,String openid,String url);

    /**
     *  检查Phone
     * @param cmd 请求类型
     * @param phone
     * WeiXinCheckWeixinPhone
     * @return 返回Void Void is Type Null
     */
    ApiResponse<Void> WeiXinCheckWeixinPhone(String cmd,String phone,String url);
}