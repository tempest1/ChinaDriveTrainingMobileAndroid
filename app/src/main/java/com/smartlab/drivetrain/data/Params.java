package com.smartlab.drivetrain.data;

import com.smartlab.drivetrain.util.FileUtils;

/**
 * Created by smartlab on 15/9/15.
 * 内部参数信息，包含有连接地址，文件保存路径等信息。
 *
 */
public class Params {
    /**
     * 正式地址
     */
     public static final String BASE = "http://139.129.96.134";
    /**
     * 本地测试地址
     */

    //
  //  public static final String BASE = "http://42.121.28.8";http://wxdhnuazhr.proxy.qqbrowser.cc
  //  public static final String BASE = "http://wxdhnuazhr.proxy.qqbrowser.cc";

    /**
     * 用户注册地址
     */
    public static final String REGISTER = Params.BASE + "/rest/userv2/regist";   //  "/user/regist";
    /**
     * 用户登录
     */
    public static final String LOGIN = Params.BASE + "/rest/userv2/login";   //  "/user/login";
    /**
    * 登出
    */
    public static final String LOGOUT = Params.BASE + "/rest/userv2/loginOut";  //   "/user/login";
    /**
     * 验证码
     */
    public static final String MsgCode_Register = Params.BASE +  "/rest/userv2/mesIdCode";//
    /**
     * 密码重置
     */
    public static final String ResetPass = Params.BASE +  "/rest/userv2/resetPasssword";
    /**
     * 获取用户详细信息
     */
    public static final String getUser = Params.BASE +  "/rest/userv2/getUserInfo";  //  "/user/getUserInfo"
    /**
     * 修改用户信息
     */
    public static final String ModifyUser = Params.BASE + "/rest/userv2/modifyUser"; //  "/user/modifyUser";
    /**
     * valid phone
     */
    public static final String validPhone = Params.BASE + "/rest/userv2/validPhone"; //  "/user/validPhone";

    /**
     * 新闻列表及的URL
     */
    public static final String INFO_URL = Params.BASE +"/rest/userinfov2/userQueryAllInfo";    //  "/userinfo/userQueryAllInfo";
    /**
     * 新闻详情页面URL
     */
    public static final String INFO_DETAIL = Params.BASE +"/rest/userinfov2/userQueryDetailsInfo";// "/userinfo/userQueryDetailsInfo";
    /**
     * 论坛板块地址
     */
    public static final String FORUM_URL =  Params.BASE + "/rest/plate_v2/showPlateList_v2"; // "/plate/showPlateList";
    /**
     * 帖子列表
     */
    public static final String CARD_LIST_URL = Params.BASE + "/rest/card_v2/showCardList_v2";    //"/card/showCardList";
    /**
     * 新建帖子
     */
    public static final String ADD_CARD = Params.BASE + "/rest/card_v2/addCard_v2";
    /**
     * 帖子详情
     */
    public static final String POST_LIST_URL = Params.BASE + "/rest/post_v2/showPostList_v2";    //"/post/showPostList";
    /**
     * 回复帖子
     */
    public static final String POST_SEND_TEXT = Params.BASE + "/rest/post_v2/addPost_v2";//    "/post/addPost";
    /**
     * 科目二短信验证
     */
    public static final String MsgCode_SubTwo = Params.BASE + "/rest/appointment_v2/mesIdCode_v2";
    /**
     * 科目二预约
     */
    public static final String SubjectTwoOrder = Params.BASE + "/rest/appointment_v2/appointTwo_v2";//   "/appointment/appointTwo";
    /**
     * 科目三短信验证码
     */
    public static final String MsgCode_SubThree = Params.BASE + "/rest/appointment_v2/mesIdCodeT_v2";
    /**
     *  科目三预约
     */
    public static final String SubjectThreeOrder = Params.BASE + "/rest/appointment_v2/appointThree_v2";//

    /**
     * 通过城市编码获取驾校列表
     */
    public static final String Alliance = Params.BASE + "/rest/userinfov2/userQueryAllInfo";
    /**
     * 通过城市编码获取驾校列表
     */
    public static final String DrivingListByCityCode = Params.BASE + "/rest/userDrivingv2/getDrivingListByCityCode";
    /**
     * 通过城市编码获取驾校驾校经纬度，及基本信息
     */
    public static final String DrivingLatLngByCityCode = Params.BASE + "/rest/userDrivingv2/getDrivingLatLngByCityCode";
    /**
     * 获取驾校详情
     */
    public static final String Driving_Detail = Params.BASE + "/rest/userDrivingv2/getDriving";
    /**
     * 所有驾校信息
     */
    public static final String All_Driving_list = Params.BASE + "/rest/userDrivingv2/getDrivingList";
    /**
     * 支付接口
     */
//    public static final String ALI_PLAY = Params.BASE + "/product/pay"
    /**
     * 教练图片地址
     */
    public static String ImageCoach =  "http://139.129.96.117/coachImage/JwCoach/";
    /**
     * 教练信息
     */

    public static String coachInfo="http://139.129.96.134/rest/coachv2/coachInfo";



    /**
     * 用户与教练
     */
    public static String addUserCoach=Params.BASE+"/rest/user_coach/addUserCoach";
    /**
     * 支付接口
     */
    public static final String ALI_PLAY = Params.BASE + "/rest/product/paybyphone";
    /**
     * 科一视屏学习
     */
    public static final String Exam_One_online = "http://www.iqiyi.com/w_19rr0odhah.html#vfrm=2-3-0-1";
    /**
     * 科目二在线视屏学习
     */
    public static final String Exam_two_online = "http://www.iqiyi.com/v_19rro4c918.html#vfrm=2-3-0-1";
    /**
     * 科目三在线视屏学习
     */
    public static final String Exam_three_online = "http://www.iqiyi.com/w_19rs3hz9r1.html#vfrm=2-3-0-1";
    /**
     * 科目四在线视屏学习
     */
    public static final String Exam_four_online = "http://www.iqiyi.com/w_19rsdggn3h.html#vfrm=2-3-0-1";
    /**
     * 订单列表
     */
    public static final String Order_List = Params.BASE + "/rest/product/getOrderByUserId";

    /**
     * 通过驾校获取驾校产品id（）
     */
    public static final String GET_PRODUCT_ID = Params.BASE + "/rest/product/getProductByDrivingId";
    /**
     * 获取用户购买的服务
     */
    public static final String Get_SERVICE = Params.BASE + "/rest/product/getServiceByUserId";
    /**
     * 获取用户购买的服务
     */
    public static final String RemoveOrder = Params.BASE + "/rest/product/removeOrderByOrderId";
    /**
     * 咨询类型列表
     */
    public static final String ConsultTypeList = Params.BASE + "/rest/consult/getConsultTypeList";
    /**
     * 新建咨询
     */
    public static final String addConsult = Params.BASE + "/rest/consult/addConsult";
    /**
     * 获取用户咨询记录
     */
    public static final String ConsultByUserId = Params.BASE + "/rest/consult/getConsultByUserId";
    /**
     * 查询用户学籍
     */
    public static final String SchoolByUserId = Params.BASE + "/rest/school/getSchoolByUserId";
    /**
     * 预约教练
     */
    public static final String appoinmentCoach = Params.BASE + "/rest/appointmentCoach/appoinmentCoach";
    /**
     * 查询用户预约列表
     */
    public static final String AppointmentList = Params.BASE + "/rest/appointmentCoach/userAppointmentList";
    /**
     * 删除预约
     */
    public static final String deleteAppointment = Params.BASE + "/rest/appointmentCoach/deleteAppointment";
    /**
     * 文件上传
     */
    public static final String File_UpLoad = Params.BASE + "/file/UploadMobileImage";
    /**
     * apk远程更新地址
     */
    public static final String UPDATE_CHECK_URL = "http://139.129.96.117/appDownload/update.txt";

    /**
     *科目一考试题目
     */
    public static final String EXAM1 = Params.BASE + "/rest/exercise/getExamination";
    /**
     * 预约考试
     */
    public static final String Appoint_Exam = "http://www.jzjtgl.com:8080/preasign/net/menu/";
    /**
     * 科目四考试题目
     */
    public static final String EXAM4 = Params.BASE +"/rest/exercise/getExamination4";
    /**
     * 查询学时卡
     */
    public static final String Query_Time_Card = "http://116.204.106.81:8895/jscjsy/admin/xycx.jsp#";
    /**
     * 考题地址
     */
    public static final String EXAM_PIC = "http://139.129.96.117/exercise/";

    /**
     * 投诉类型列表
     */
    public static final String Complain_List = Params.BASE + "/rest/complain/getComplainTypeList";
    /**
     * 投诉信息列表
     */
    public static final String Complains = Params.BASE + "/rest/complain/getComplainList";
    /**
     * 添加反馈
     */
    public static final String addFeedback = Params.BASE + "/rest/complain/addFeedback";
    /**
     * 评论的类型
     */
    public static final String EvaluateType = Params.BASE + "/rest/evaluate/getEvaluateType";
    /**
     * 提交评论
     */
    public static final String addEvaluate = Params.BASE + "/rest/evaluate/addEvaluate";
    /**
     * 提交评价
     */
    public static final String adddiscuss = Params.BASE + "/rest/comment/addComment";
    /**
     * 查看我的评论
     */
    public static final String EvaluateByUserId = Params.BASE + "/rest/evaluate/getEvaluateByUserId";

    /**
     * 投诉
     */
    public static final String Complain = Params.BASE + "/rest/complain/addComplain";
    /**
     * 投诉
     */
    public static final String getMyQRCode = Params.BASE + "/rest/partner/getMyQRcode";

    /**
     * 数据文件保存路径
     */
    public static final String DATA_PATH = FileUtils.getRootPath() + "/Drive/";//应用程序文件保存路径
    /**
     *
     */
    public static final String IMAGE_CACHE = FileUtils.getRootPath() + "/Drive/image/cache/";
    /**
     * 图片缓存路径
     */
    public static final String IMAGE_LOADER = "/Drive/image/loader/";
    /**
     * 个人二维码路径
     */
    public static final String QrCode = "/Drive/image/QrCode/";//   FileUtils.getRootPath() +
    /**
     * 文件下载路径
     */
    public static final String DOWN_LOAD = "/Drive/download";
    public static final String dbPath = "data/data/com.smartlab.drivetrain.license/citycode.db";
    public static final String fileDir = "data/data/com.smartlab.drivetrain.license/";
    public static final String testDb = "local.db";//本地数据库
    public static final int testDB_version = 2;
    public static final String TableName = "exercise";// 错误试题表


    /*
 *广告路径
 */
    public static final String Advertisement = Params.BASE +"/rest/userinfov2/userQueryAllInfo";//


/*
 *微信接口
 */

    /*
 *微信注入
 * /rest/openweixin/getUserInfoByCode
 * cmd
 * code
 */
    public static final String WeiXinInfoByCode = Params.BASE +"/rest/openweixin/Android_getUserInfoByCode";//
/*
 *微信绑定
 * /rest/openweixin/makeBinding
 * cmd
 * openid
 * phone
 */
    public static final String WeiXinMakeBinding = Params.BASE +"/rest/openweixin/makeBinding";//
    /*
 *检查用户是否已经被绑定
 * /rest/openweixin/checkUserhasBinded
 * cmd
 * openid
 */
    public static final String WeiXinCheckUserhasBinded = Params.BASE +"/rest/openweixin/checkUserhasBinded";//
    /*
 *微信一键登录
 * /rest/openweixin/makeBinding
 * cmd
 * openid
 */
    public static final String WeiXinLoginByOpenId = Params.BASE +"/rest/openweixin/loginByOpenId";

    /**
     * 开始检查电话是否有效
     * 根据电话获取userId，没有则表示新用户，返回true
     * 有userId时，判断数据库weixin表中有没有这个userId，有则表示已绑定返回false，反之返回true
     *
     * checkWeixinPhone
     */
    public static final String WeiXinCheckWeixinPhone = Params.BASE +"/rest/openweixin/checkWeixinPhone";
}
