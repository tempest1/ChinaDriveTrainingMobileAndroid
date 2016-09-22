package com.smartlab.drivetrain.data;

import com.smartlab.drivetrain.model.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartlab on 15/9/21.
 *  新闻信息来源
 * @author yu
 */

public class InfoData {


    public static List<Info> getInfoList(){
        List<Info> infosList = new ArrayList<>();

        Info info1 = new Info();
        info1.setTitle("驾考新规");

        Info info2 = new Info();
        info2.setTitle("驾考新规");

        Info info3 = new Info();
        info3.setTitle("驾考新规");

        Info info4 = new Info();
        info4.setTitle("驾考新规");

        infosList.add(info1);
        infosList.add(info2);
        infosList.add(info3);
        infosList.add(info4);

        return infosList;
    }
}
