package com.smartlab.drivetrain.picture;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/11/11.
 * 考试题目中所有的图片的地址，自动后台加载图片，提高用户体验，防止图片切换时，不能时时显示的问题
 * @author smartlab
 */
public class PictureUrl {

    public static List<String>  getPicture(List<Question> objects){
        List<String> urls = new ArrayList<>();
//            urls.clear();
        for (Question obj :objects){
            // 若该题目需要加载图片，则将图片地址获取出来
            if (obj.getStsx() == 2){
                String url = Params.EXAM_PIC+obj.getStxh()+".jpg";
                urls.add(url);
            }
        }
        return urls;
    }
}
