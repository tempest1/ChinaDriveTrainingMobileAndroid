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

import java.util.List;

/**
 * Created by smartlab on 15/11/19.
 * 应用程序接口
 */
public interface AppAction {
    /**
     * 注册
     * @param cmd 请求类型
     * @param phone 电话号码
     * @param password 密码
     */
    void registerNew(String cmd,String phone,String password,String url,ActionCallBackListener<Void> listener);

    /**
     * 电话号码是否有效
     * @param cmd 请求类型
     * @param phone 电话号码
     */
    void validPhone(String cmd,String phone,String url,ActionCallBackListener<Void> listener);
    /**
     * 电话号码是否有效
     * @param cmd 请求类型
     * @param phone 电话号码
     */
    void validPhones(String cmd,String phone,String url,ActionCallBackListener<Void> listener);
    /**
     * 登录
     * @param cmd 请求类型
     * @param phone 用户名
     * @param password 密码
     */
    void login(String cmd,String phone,String password,String url,ActionCallBackListener<Void> listener);

    /**
     *
     * 退出登录
     * @param cmd {"cmd":"loginOut"}
     * @param listener 监听
     */
    void loginOut(String cmd,String url,ActionCallBackListener<Void> listener);

    /**
     * 请求用户详细信息
     * @param cmd 请求类型 getUserInfo
     * @param url 请求地址
     * @param listener 回调监听
     */
    void getUserDetail(String cmd,String url,ActionCallBackListener<User> listener);
    /**
     *
     * 获取验证码,预约训练的验证码
     * @param cmd 请求类型
     * @param phone 电话号码
     * @param url 地址
     * @param listener 回调监听
     */
    void requestCode(String cmd,String phone,String url,ActionCallBackListener<String> listener);

    /**
     * 注册的验证码
     * @param cmd 请求类型registerCode
     * @param phone  电话号码
     * @param url 地址
     * @param listener 监听
     */
    void registerCode(String cmd,String phone,String url,ActionCallBackListener<String> listener);
    /**
     * 请求新闻
     * @param cmd 请求类型
     * @param pagination 页码
     * @param pageNum 每页数目
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 类型"news"
     */
    void loadInfo(String cmd,int pagination,int pageNum,String startTime,String endTime,String type,String url,ActionCallBackListener<List<Info>> listener);

    /**
     * 加载新闻详情
     * @param cmd 请求类型
     * @param timeStamp 新闻唯一标识
     */
    void loadInfoDetail(String cmd,String timeStamp,String url,ActionCallBackListener<List<InfoContent>> listener);
    /**
     *  加载板块
     * @param cmd "showPlateList"
     */
    void loadBank(String cmd,String url,ActionCallBackListener<List<Frunm>> listener);

    /**
     * 加载帖子列表
     * @param cmd 请求类型
     * @param pagination 页码
     * @param pageNum 每页数目
     * @param timestamp 板块的唯一标示，用于查询板块内的所有帖子
     * @param url 请求的地址
     * @param listener 监听回调
     */
    void loadCard(String cmd,int pagination,int pageNum,String timestamp,String url,ActionCallBackListener<List<Card>> listener);

    /**
     * 帖子的回复列表
     * @param cmd 请求类型
     * @param timestamp 帖子的唯一标示，类似于id的东西
     * @param pagination 页码
     * @param userName 登录的用户名
     * @param pageNum 每页条数
     * @param url 地址
     * @param listener 监听回调
     */
    void loadPostList(String cmd,String timestamp,int pagination,String userName,int pageNum,String url,ActionCallBackListener<List<CardResponse>> listener);

    /**
     *
     * 回复帖子
     * @param cmd 请求类型addPost
     * @param timestamp 帖子唯一标示
     * @param time 回帖时间
     * @param name 回帖人姓名
     * @param content 回帖内容
     * @param url 地址
     * @param listener 回调监听
     */
    void sendReply(String cmd,String timestamp,String time,String name,String content,String url,ActionCallBackListener<Void> listener);

    /**
     * 发送图片
     * @param filePath 文件路径列表
     * @param url 地址
     * @param listener 回调监听
     */
    void sendPicture(String filePath,String url,ActionCallBackListener<String> listener);
    /**
     *
     * @param cmd   请求类型
     * @param km    科目
     * @param name  真实姓名
     * @param IDcard    身份证号码
     * @param carType   考试车型
     * @param train     训练班次
     * @param url       请求地址
     * @param listener  回调监听
     */
    void sendAppointOrder(String cmd,String km,String name,String IDcard,String carType,String train,String coach,String appoinmentTime,String url,ActionCallBackListener<Void> listener);

    /**
     * 修改用户信息
     * @param jsonString User
     * @param url 地址
     * @param listener 回调监听
     */
    void modifyUserInfo(String jsonString,String url,ActionCallBackListener<Void> listener);

    /**
     * 获取考试题目信息
     * @param json 请求的json字符串
     * @param url 地址
     * @param listener 回调监听
     */
    void getExams(String json,String url,ActionCallBackListener<List<Question>> listener);

    /**
     * 获取驾校列表，通过城市编码获取，您所在城市的驾校
     * @param cmd cmd
     * @param pagination 页码
     * @param pageNum 每页数量
     * @param url url地址
     * @param listener 回调监听
     */
    void getDrivingList(String cmd,int citycode,int pagination,int pageNum,String url,ActionCallBackListener<List<SchoolItem>> listener);

    /**
     * 获取驾校详情
     * @param cmd cmd
     * @param timestamp 驾校唯一标示
     * @param url 地址
     */
    void getDrivingDetail(String cmd,long timestamp,String url,ActionCallBackListener<SchoolDetail<List<DrivingComment>>> listener);

    /**
     * 获取所有驾校
     * @param cmd getDrivingList
     * @param pagination 页码
     * @param pageNum 每页数量
     * @param url 地址
     * @param listener 监听
     */
    void getAll_DrivingList(String cmd,int pagination,int pageNum,String url,ActionCallBackListener<List<SchoolItem>> listener);

    /**
     * {"cmd":"getServiceByUserId","timestamp":1449556784374}
     * @param cmd cmd
     * @param timestamp 驾校时间戳
     * @param listener serviceList
     */
    void getServiceByUserId(String cmd,String timestamp,String url,ActionCallBackListener<List<HaveServices>> listener);

    /**
     * 通过驾校id获取驾校旗下的产品
     * @param cmd cmd
     * @param timestamp timestamp时间戳
     * @param url 地址
     * @param listener 回调
     */
    void getProductByDrivingId(String cmd,String timestamp,String url,ActionCallBackListener<Product> listener);

    /**
     *
     * @param cmd pay
     * @param timestamp 产品的时间戳
     * @param allpay 是否一口价
     * @param base 是否购买基础包
     * @param installment1 是否购买分期一
     * @param installment2 是否购买分期二
     * @param installment3 是否购买分期三
     */
    void pay(String cmd,String timestamp,boolean allpay,boolean base,boolean installment1,boolean installment2,boolean installment3,String url,ActionCallBackListener<String> listener);

    /**
     *
     * @param cmd getDrivingLatLngByCityCode
     * @param citycode 城市编码
     * @param pagination 页码
     * @param pageNum 每页数量
     * @param url 服务器地址
     * LatlngList 对象</br>将驾校经纬度标注到地图上面
     */
    void drivingLatLng(String cmd,String citycode,int pagination,int pageNum,String url,ActionCallBackListener<List<LatlngList>> listener);

    /**
     *
     * @param cmd getOrderByUserId
     * @param timestamp 用户timeStamp
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param url 服务端地址
     * @param listener 回调监听
     */
    void getOrderByUserId(String cmd,String timestamp,String startTime,String endTime,String url,ActionCallBackListener<List<Orders>> listener);

    /**
     *
     * @param json  json对象
     * @param url   地址
     * @param listener 回调监听
     */
    void addComplain(String json,String url,ActionCallBackListener<Void> listener);

    /**
     *
     * @param cmd getComplainTypeList
     * @param url 地址
     * @param listener 回调
     */
    void getComplainTypeList(String cmd,String url,ActionCallBackListener<List<ComplainType>> listener);

    /**
     *
     * @param cmd    removeOrderByOrderId
     * @param orderId orderId
     */
    void removeOrderByOrderId(String cmd,String orderId,String url,ActionCallBackListener<Void> listener);

    /**
     * 获取投诉列表
     * @param cmd getComplainList
     * @param url 地址
     * @param listener 回调监听
     */
    void getComplainList(String cmd,String url,ActionCallBackListener<List<FeedBack>> listener);

    /**
     *
     * @param cmd getEvaluateType
     * @param userTimestamp userTimestamp
     * @param url 地址
     * @param listener 监听
     */
    void getEvaluateType(String cmd,String userTimestamp,String url,ActionCallBackListener<EvaluateModel> listener);

    /**
     *
     * @param json json对象
     * @param url 地址
     * @param listener 回调
     */
    void addEvaluate(String json,String url,ActionCallBackListener<Void> listener);

    /**
     * 获取我的评论
     * @param cmd getEvaluateByUserId
     * @param url 地址
     * @param listener 回调
     */
    void getEvaluateByUserId(String cmd,String url,ActionCallBackListener<List<EvaluateBack>> listener);

    /**
     * 反馈
     * @param cmd addFeedback
     * @param feedback String
     * @param id 投诉id
     * @param url 地址
     * @param listener 回调
     */
    void addFeedback(String cmd,String feedback,String id,String url,ActionCallBackListener<Void> listener);

    /**
     *
     * @param cmd getConsultTypeList
     * @param url 地址
     * @param listener 回调
     */
    void getConsultTypeList(String cmd,String url,ActionCallBackListener<List<ComplainType>> listener);

    /**
     *
     * @param json AddConsult 对象
     * @param url 地址
     * @param listener 回调
     */
    void addConsult(String json,String url,ActionCallBackListener<Void> listener);

    /**
     *
     * @param cmd getConsultByUserId
     * @param url 地 址
     * @param listener 回调
     */
    void getConsultByUserId(String cmd,String url,ActionCallBackListener<List<ConsultBean>> listener);

    /**
     * 获取用户学籍
     * @param cmd getSchoolByUserId
     * @param timestamp UserTimestamp
     * @param url 地址
     */
    void getSchoolByUserId(String cmd,String timestamp,String url,ActionCallBackListener<StatusSchool> listener);

    /**
     * 用户学籍
     * @param cmd appoinmentCoach
     * @param userTimestamp userTimeStamp
     * @param coachTimestamp coachTimestamp
     * @param time 时间
     * @param url 地址
     */
    void appoinmentCoach(String cmd,String userTimestamp,String coachTimestamp,String time,String url,ActionCallBackListener<Void> listener);

    /**
     *
     * @param cmd userAppointmentList
     * @param url 地址
     * @param listener 回调
     */
    void userAppointmentList(String cmd,String url,ActionCallBackListener<List<MyAppointment>> listener);

    /**
     *
     * @param cmd getMyQRcode
     * @param timestamp 用户时间戳
     * @param url 服务器地址
     * @param listener 回调监听
     */
    void getMyQRCode(String cmd,String timestamp,String url,ActionCallBackListener<String> listener);
    /**
     * 请求广告
     * @param cmd 请求类型
     * @param pagination 页码
     * @param pageNum 每页数目
     * @param type 类型"ad"
     */
    void loadAdvertisement(String cmd,int pagination,int pageNum,String type,String url,ActionCallBackListener<List<Info>> listener);

    /**
     *
     * @param cmd 请求类型 addCard
     * @param title 内容
     * @param timestamp 当前时间戳
     * @param url 地址
     * @param listener 回调监听
     */
    void addCard(String cmd, String title, String timestamp, String url, ActionCallBackListener<Void> listener);
    /*
     * 找回密码
     * @param cmd 请求类型
     * @param phone 电话号码
     * @param password 密码
     */
    void reDiesgin(String cmd,String phone,String password,String url,ActionCallBackListener<Void> listener);


    /*
    * 微信注入
    * @param cmd 请求类型
    * @param code
    */
    void WeiXinInfoByCode(String cmd,String code, String url,ActionCallBackListener<Void> listener);
    /*
     * 检查用户是否已经被绑定
     * @param cmd 请求类型
     * @param openid 电话号码
     */
    void WeiXinCheckUserhasBind(String cmd,String openid, String url,ActionCallBackListener<Void> listener);
    /*
   * 用户绑定
   * @param cmd 请求类型
   * @param openid
   * @param phone 电话号码
   */
    void WeiXinMakeBinding(String cmd,String openid,String phone,String url,ActionCallBackListener<Void> listener);
    /*
* 微信一键登录
* @param cmd 请求类型
* @param openid
* @param phone 电话号码
*/
    void WeiXinLoginByOpenId(String cmd,String openid,String url,ActionCallBackListener<Void> listener);
    /*
* 检查Phone
* @param cmd 请求类型
* @param phone
* @param phone 电话号码
*/
    void WeiXinCheckWeixinPhone(String cmd,String phone,String url,ActionCallBackListener<Void> listener);
}
