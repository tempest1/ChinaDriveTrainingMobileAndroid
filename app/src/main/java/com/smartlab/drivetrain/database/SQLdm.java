package com.smartlab.drivetrain.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 实现从assets目录读取数据库文件然后写入SDcard中,如果在SDcard中存在，就打开数据库，不存在就从assets目录下复制过去
 */
public class SQLdm {
    private Context context;
    private SQLiteDatabase db;
    public SQLdm(Context context){
        this.context = context;
        db = openDatabase();
    }
    public SQLiteDatabase openDatabase(){
        String filePath = Params.dbPath;
        String fileDir = Params.fileDir;
        System.out.println("filePath:"+filePath);
        File citypath = new File(filePath);
        //查看数据库文件是否存在
        if(citypath.exists()){
            LogUtils.i("存在数据库");
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(citypath, null);
        }else{
            //不存在先创建文件夹
            File path = new File(fileDir);
            LogUtils.i("pathStr="+path);
            if (path.mkdirs()){
                LogUtils.i("创建成功");
            }else{
                LogUtils.i("创建失败");
            };
            try {
                //得到资源
                AssetManager am= context.getAssets();
                //得到数据库的输入流
                InputStream is = am.open("citycode.db");
                LogUtils.i("city" +is+"");
                //用输出流写到SDcard上面
                FileOutputStream fos=new FileOutputStream(citypath);
                LogUtils.i("city"+"fos="+fos);
                LogUtils.i("city"+"citypath="+citypath);
                //创建byte数组  用于1KB写一次
                byte[] buffer=new byte[1024];
                int count = 0;
                while((count = is.read(buffer))>0){
                    fos.write(buffer,0,count);
                }
                //最后关闭就可以了
                LogUtils.i("city"+"得到");
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabase();
        }
    }

    /**
     * 通过城市名查询城市编码
     * @param cityName 城市名称
     * @return 城市编码
     */
    public Cursor queryCityCode(String cityName){
        Cursor c = null;
        if (db != null) {
            String sql = "select * from City where cityname = ?";
            c = db.rawQuery(sql, new String[]{cityName});
//            int i = c.getColumnIndex("CityCode");
//            if (i==-1){
//                LogUtils.e("cityCode is not exist:" + i);
//            }
//            i = c.getCount();
//            LogUtils.e(sql + "行数" + i);
//            String name [] = c.getColumnNames();
//            for (String names :
//                    name) {
//                LogUtils.e(names);
//            }
//            LogUtils.e(i + "城市编码");
        }
        return c;
    }
    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
