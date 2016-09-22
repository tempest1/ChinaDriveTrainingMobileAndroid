package com.smartlab.drivetrain.service;

import com.smartlab.drivetrain.util.ScreenUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by smartlab on 15/9/28.
 */
public class DetailService {
    public static String getNewsDetails(String content, String news_title){
        int width = ScreenUtils.getScreenW();
        String data = "<html><body><head><meta charset=\"UTF-8\"/><style type=\"text/css\">img{max-width:"+width+";display:block;}</style></head>" +
                "<center><h2 style='font-size:16px;'>" + news_title + "</h2></center><div style='width:" + width+"px;'>";

        data = data + "</div><p align='left' style='margin-left:10px'>"
                + "<span style='font-size:10px;'>"
                + "</span>"
                + "</p>";
        data = data + "<hr size='1' />";
        data = data + content;
        //获取到图片的信息
        data = data + "</body></html>";
        //手动设置img 的属性
        Document doc_Dis = Jsoup.parse(data);
        Elements ele_Img = doc_Dis.getElementsByTag("img");
        if (ele_Img.size() != 0){
            for (Element e_Img : ele_Img) {
                e_Img.attr("style", "width:100%");
            }
        }
        String newHtmlContent = doc_Dis.toString();
        return newHtmlContent;
    }
    //html方式播放视屏
    public static String getVideoWeb(String video_url){
        String data = "<html><head><meta charset=\"UTF-8\"></head>" + "<body>\n" +
                "    <video controls=\"controls\" loop = \"true\" width = \"100%\" height = \"100%\" autoplay = \"true\"" +
                "preload = \"true\">" +
                "<source src = '"+ video_url +"'/>"+"</video>\n" +
                "</body></html>";
        return data;
    }
}
