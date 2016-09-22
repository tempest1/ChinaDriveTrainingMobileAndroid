package com.smartlab.drivetrain.data;

import com.smartlab.drivetrain.model.CoachBrief;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/27.
 */
public class CoachData  {

    private String CoachSchool [] = {"荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司"
            ,"荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司"
            ,"荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市金鹏机动车驾驶员培训有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司"
            ,"荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市金鹏机动车驾驶员培训有限公司","荆州市天合驾驶员培训有限公司","荆州市兴成驾驶员培训学校有限公司","荆州市兴成驾驶员培训学校有限公司"};
    private String coachName [] = {"文厚华","周海峰","王志强","刘学庆","许涛","薛磊","吴振强","沈志超","谢天","陈超","陈新国","宋祖星","邓永志","韩小青","王羿明","宋远红","靳燕丽","刘其凯","刘仁忠","张葆","吴燕峰","曹军林","刘吉付","李平","叶洪敬","李伟","曾建超","何凯","喻鹏","刘云华","赵春敏","王开亮"};

    private static String coach [] = {"李爱明C1.jpg","李晓冬C1.jpg","理论-何琴琴-JK0007.jpg","谭勇B2.jpg","肖典军--B2.jpg","许明华---A1.jpg","邹晓华C1.jpg",
            "A1-陈亮-JK0081.jpg","A1-李先平-JK0003.jpg","A1-王立银-JK0230.jpg","A1-熊永华-JK0237.jpg","A1-余新普-JK0239.jpg","A2付大军-教练.jpg",
            "B2-达勇-JK0231.jpg","B2-董义华-JK0182.jpg","B2-何军-JK0020.jpg","B2-刘卫丰-JK0023.jpg","B2-王玲龙-JK0233.jpg","B2-姚爱忠-JK0232.jpg",
            "B2-邹强-JK0076.jpg","C1-郭飞飞-JK0147.jpg","C1-郭世明-JK0145.jpg","C1-何斌-JK0066.jpg","C1-黄东华-JK0128.jpg","C1-刘强-JK0022.jpg",
            "C1-龙光明-JK0157.jpg","C1-罗亭-JK0082.jpg","C1-彭均茹-JK0059.jpg","C1-万方-JK0030.jpg","C1-王平-JK0137.jpg","C1-谢荣-JK0017.jpg"
    };//,"C1-尹成龙-JK0031.jpg",
//    "C1-张潮-JK0065.jpg","C1-张荣-JK0149.jpg","C1-张晓强-JK0148.jpg","C1-赵云-JK0168.jpg","C1-钟祥-JK0187.jpg"
    public static String coachTimespase[]={"1459048310691","1459048637252","1459048694490","1459048766164","1459048809269","1459050575547","1459050887982","1459051093190",
       "1459051099254","1459051109028","1459051116527","1459051123281","1459051131618","1459051138397","1459051146601","1459051153871","1459051175743","1459051183532",
    "1459051190325","1459051202221","1459051209164","1459051215413","1459051223214","1459051229665","1459051236494","1459051242650","1459051249008","1459051256204","1459051263704",
      "1459051270318","1459051277274"
    };
    private static String coach2 [] = {"李爱明","李晓冬","何琴琴","谭勇","肖典军","许明华","邹晓华",
            "陈亮","李先平","王立银","熊永华","余新普","付大军",
            "达勇","董义华","何军","刘卫丰","王玲龙","姚爱忠",
            "邹强","郭飞飞","郭世明","何斌","黄东华","刘强",
            "龙光明","罗亭","彭均茹","万方","王平","谢荣","尹成龙",
            "张潮","张荣","张晓强","赵云","钟祥"
    };
//   static String path =  Environment.getExternalStorageDirectory() + Params.Coach;


    public static List<CoachBrief> getCoachBrief(){
        List<CoachBrief> coachBriefs = new ArrayList<>();
//        String imagePath = Params.ImageCoach + name;
        StringBuilder imagePath = new StringBuilder(Params.ImageCoach);
        for (String name : coach){
            imagePath.append(name);
            CoachBrief brief = new CoachBrief();
            brief.setCoachName(name.replaceAll("[^\\u4e00-\\u9fa5]", ""));//    过滤中文字符
            brief.setCoachPic(imagePath.toString());
            brief.setBelongTo("所属驾校：" + "晶崴驾校");
            coachBriefs.add(brief);
            imagePath.delete(imagePath.indexOf(name), imagePath.length());
        }
        return coachBriefs;
    }
}
