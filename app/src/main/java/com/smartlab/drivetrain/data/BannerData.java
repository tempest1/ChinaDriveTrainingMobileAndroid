package com.smartlab.drivetrain.data;

import com.smartlab.drivetrain.model.AdTrain;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/15.
 */
public class BannerData {
    /**
     * 轮播广播模拟数据
     *
     * @return
     */
    public static List<AdTrain> getBannerAd() {
        List<AdTrain> adList = new ArrayList<AdTrain>();
        AdTrain AdTrain = new AdTrain();
        AdTrain.setId("108078");
        AdTrain.setDate("3月4日");
        AdTrain.setTitle("吉祥驾校");
        AdTrain.setTopicFrom("阿宅");
        AdTrain.setTopic("驾考新政策出台");
        AdTrain.setImgUrl("http://139.129.96.117/appDownload/homepage/1.jpg");
        AdTrain.setAd(false);
        adList.add(AdTrain);

        AdTrain AdTrain2 = new AdTrain();
        AdTrain2.setId("108078");
        AdTrain2.setDate("3月5日");
        AdTrain2.setTitle("驾考改革");
        AdTrain2.setTopicFrom("考驾照会越来越难");
        AdTrain2.setTopic("中国交通安全越来越引起人们的注意");
        AdTrain2.setImgUrl("http://139.129.96.117/appDownload/homepage/2.jpg");
        AdTrain2.setAd(false);
        adList.add(AdTrain2);

        AdTrain AdTrain3 = new AdTrain();
        AdTrain3.setId("108078");
        AdTrain3.setDate("3月6日");
        AdTrain3.setTitle("工院驾校");
        AdTrain3.setTopicFrom("近日开业招新");
        AdTrain3.setTopic("新店开张，更多优惠等你来");
        AdTrain3.setImgUrl("http://139.129.96.117/appDownload/homepage/3.jpg");
        AdTrain3.setAd(false);
        adList.add(AdTrain3);

        AdTrain AdTrain4 = new AdTrain();
        AdTrain4.setId("108078");
        AdTrain4.setDate("3月7日");
        AdTrain4.setTitle("医专驾校");
        AdTrain4.setTopicFrom("转为妹子服务");
        AdTrain4.setTopic("提供一对一指导，包教包会");
        AdTrain4.setImgUrl("http://139.129.96.117/appDownload/homepage/4.jpg");
        AdTrain4.setAd(false);
        adList.add(AdTrain4);

        AdTrain AdTrain5 = new AdTrain();
        AdTrain5.setId("108078");
        AdTrain5.setDate("3月8日");
        AdTrain5.setTitle("中国驾校");
        AdTrain5.setTopicFrom("大熊");
        AdTrain5.setTopic("拥有权威专业的教练，动态模拟指导，讲解生动，上手快，精通快！您的不二选择!");
        AdTrain5
                .setImgUrl("http://139.129.96.117/appDownload/homepage/5.jpg");
        AdTrain5.setAd(true); // 代表是广告
        adList.add(AdTrain5);

        return adList;
    }
}
