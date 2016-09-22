package com.smartlab.drivetrain.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.detail.discuss;
import com.smartlab.drivetrain.interfaces.ErrorEvent;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.model.CoachBrief;
import com.smartlab.drivetrain.model.Discuss;
import com.smartlab.drivetrain.model.networks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smartlab on 15/11/20.
 * Http请求
 */
public class HttpEngine {
//    private static final String CHARSET = "utf-8";
    private static HttpEngine instance = null;
    private final static String REQUEST_MOTHOD = "POST";
    private final static String end ="\r\n";
    private final static String twoHyphens ="--";
    private final static String boundary ="*****";
    /**
     * 获取服务端生成的BeanDefinitionReaderSid
     * @param headerFields 传入http头信息，拆包
     */
    private void getBeanDefinitionReaderSid(Map<String, List<String>> headerFields) {

        if (MainApplication.getBeanDefinitionReaderSid() == null) {
            if (headerFields != null) {
                List<String> BeanDefinitionReaderSid = headerFields.get("BeanDefinitionReaderSid");
                if (BeanDefinitionReaderSid != null){
                    String head = BeanDefinitionReaderSid.get(0);
                        MainApplication.loop();
                        MainApplication.setBeanDefinitionReaderSid(head);
                        LogUtils.e("sid" + head);
                }
            }
        } else {
            LogUtils.i(MainApplication.getBeanDefinitionReaderSid());
        }
    }

    /**
     * 得到一个HttpUrlConnection
     * @param path 地址
     * @return HttpURLConnection对象
     */
    private HttpURLConnection getConnection(String path){
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(path);
            LogUtils.i(path);
            urlConnection = (HttpURLConnection) url.openConnection();//
            //  post请求必要设置允许输入
            urlConnection.setDoInput(true);
            //  post请求必要设置允许输出
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod(REQUEST_MOTHOD);
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            //  设置特定的头部信息
            urlConnection.setRequestProperty("BeanDefinitionReaderSid", MainApplication.getBeanDefinitionReaderSid());
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setChunkedStreamingMode(0);

            urlConnection.setConnectTimeout(1000 * 8);
            urlConnection.setReadTimeout(300000);
        } catch (IOException e) {
            networks.networksss=10;
            e.printStackTrace();
        }
        return urlConnection;
    }
    /**
     * 得到一个HttpUrlConnection
     * @param path 地址
     * @return HttpURLConnection对象
     */
    private HttpURLConnection getFileConnection(String path){
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(path);
            LogUtils.i(path);
            urlConnection = (HttpURLConnection) url.openConnection();//
            //  post请求必要设置允许输入
            urlConnection.setDoInput(true);
            //  post请求必要设置允许输出
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod(REQUEST_MOTHOD);
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            //  设置特定的头部信息
            urlConnection.setRequestProperty("BeanDefinitionReaderSid", MainApplication.getBeanDefinitionReaderSid());
            //  上传文件时需要添加的
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary="+boundary);
            urlConnection.setChunkedStreamingMode(0);

            urlConnection.setConnectTimeout(1000 * 8);
            urlConnection.setReadTimeout(300000);
        } catch (IOException e) {
            networks.networksss=10;
            e.printStackTrace();
        }
        return urlConnection;
    }

    public <T> T postHandle(String json,String url, Type typeOfT) throws IOException {
        Gson gson = new Gson();
        String result = null;

        LogUtils.e("json url: " + url);
        LogUtils.e("json request: " + json);
        HttpURLConnection connection = getConnection(url);
        if (connection != null) {
            if (connection.getRequestProperty("BeanDefinitionReaderSid") != null)
                LogUtils.i(connection.getRequestProperty("BeanDefinitionReaderSid"));
            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            result = getResult(connection);
        }
        if (result == null) {
            result = "{event:" + ErrorEvent.TIME_OUT_EVENT +",msg:" + ErrorEvent.TIME_OUT_EVENT_MSG+"}";
            // 打印出结果
            LogUtils.e("uncaught type error server is not response any massage");
            return gson.fromJson(result, typeOfT);
        }

        try {
            // 打印出结果
            LogUtils.e("json response: " + result);
            MainApplication.MESSAGE=result;
            return gson.fromJson(result, typeOfT);
        } catch (JsonParseException e) {
            LogUtils.e(e.getMessage());
            networks.networksss=10;
            result = "{event:" + ErrorEvent.TIME_OUT_EVENT +",msg:"+ErrorEvent.TIME_OUT_EVENT_MSG+"}";
            // 打印出结果
            LogUtils.e("response: " + result);

            return gson.fromJson(result, typeOfT);
        }
    }

    private String getResult(HttpURLConnection connection) {
        String result;
        if (connection != null) {
            /**
             * 获取并设置BeanDefinitionReaderSid，
             */
            getBeanDefinitionReaderSid(connection.getHeaderFields());
            // 获取响应的输入流对象
            InputStream is;
            try {
                is = connection.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    baos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                baos.close();

                result = new String(baos.toByteArray());
            } catch (IOException e) {
                networks.networksss=10;
                LogUtils.i(e.getMessage());
                return null;
            }
            connection.disconnect();
            return result;
        } else {
            networks.networksss=10;
            return null;
        }
    }

    /**
     * 图片上传
     * @param filePath 文件路径
     * @param url 地 址
     * @param typeofT 返回类型
     * @param <T> 对象类型
     * @return T
     */
    public <T> T fileUpLoad(String filePath,String url,Type typeofT) throws IOException{
        Gson gson = new Gson();
        String result;
        Context context = null;
        HttpURLConnection connection = getFileConnection(url);
        byte[] bytes = new byte[1024];
        int len;
        try {
            File file = new File(filePath);
            LogUtils.i(filePath);
            if (file.exists()) {
                DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
                dataOut.writeBytes(twoHyphens + boundary + end);
                dataOut.writeBytes("Content-Disposition: form-data; " +
                        "name=\"file1\";filename=\"" +
                        file.getName() + "\"" + end);
                dataOut.writeBytes(end);

                FileInputStream is = new FileInputStream(filePath);
                while ((len = is.read(bytes)) != -1) {
                    dataOut.write(bytes, 0, len);
                }
                is.close();
                dataOut.writeBytes(end);
                dataOut.writeBytes(twoHyphens + boundary + twoHyphens + end);
                dataOut.flush();
                dataOut.close();
            } else {
                LogUtils.i("file not exist");
            }
        } catch (IOException e) {
            networks.networksss=10;
            LogUtils.e(e.getMessage());
        }
        result = getResult(connection);
        try {
            if (result != null){
                LogUtils.e(result);
            }
            return gson.fromJson(result,typeofT);
        } catch (JsonParseException e){
            networks.networksss=10;
            e.printStackTrace();

            Toast.makeText(context,String.valueOf( connection.getResponseCode()), Toast.LENGTH_LONG).show();

            result = "{event:" + ErrorEvent.TIME_OUT_EVENT +",msg:"+ErrorEvent.TIME_OUT_EVENT_MSG+"}";
            return gson.fromJson(result, typeofT);
        }
    }
    /**
     * HttpEngine 实例
     * @return HttpEngine
     */
    public static HttpEngine getInstance() {
        if (instance == null){
            instance = new HttpEngine();
        }
        return instance;
    }

    static List<CoachBrief> mspeakList = new ArrayList<>();
//    private List<Discuss> getJsonData() {
//   static Discuss mspeakcontent = new Discuss();

    public static List<CoachBrief> getJsonData() {
        JSONArray jsonArray;
        JSONObject jsonObject;
        String jsonString;
        CoachBrief coachBrief ;
        String response = driving();
        jsonString = response;
        try {
            jsonObject = new JSONObject(jsonString);
            jsonArray = jsonObject.getJSONArray("coachList");
            for (int i = 0; i < jsonArray.length(); i++) {
//                mspeakcontent = new Discuss();
                coachBrief = new CoachBrief();
                try {
                    jsonObject = jsonArray.getJSONObject(i);
//                    Discuss.res=jsonObject.getString("res");
//                    Log.i("res", Discuss.res);
//                    Discuss.error=jsonObject.getString("error");
                    coachBrief.setCoachName(jsonObject.getString("realname"));//.setres(jsonObject.getString("res"));// = jsonObject.getString("imageUrl");
//                    mspeakcontent.seterror(jsonObject.getString("error"));//.newstime = jsonObject.getString("name");
//                    coachBrief.setBelongTo(jsonObject.getString("")).add(mspeakcontent);
                    coachBrief.setCoachTimestamp(jsonObject.getString("coachTimestamp"));
                    mspeakList.add(coachBrief);
//                    Log.i("res",mspeakcontent.getres());

                } catch (JSONException e) {
                    networks.networksss = 10;
//            Toast.makeText(context,String.valueOf(conn.getResponseCode()), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
//                }
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return  mspeakList;
    }

    public static String driving()
    {
        PrintWriter out=null;
        BufferedReader in=null;
        String url=Params.coachInfo;
        String result="";
        List<Discuss>   aa;
        try
        {
            URL realUrl=new URL(url);
            HttpURLConnection conn= (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(1000 * 8);
            conn.setReadTimeout(300000);
            conn.connect();
            out=new PrintWriter(conn.getOutputStream());
            JSONObject param = new JSONObject();
            param.put("cmd", "coachInfo");//mspeakcontent.getCmd()
            param.put("timestamp", "1444723934180");//mspeakcontent.getUserTimestamp()
//            param.put("drivingTimestamp", Discuss.drivingTimestamp);//mspeakcontent.getDrivingTimestamp()
//            param.put("coachTimestamp",Discuss.coachTimestamp );//mspeakcontent.getCoachTimestamp()
//            param.put("content",Discuss.content );//mspeakcontent.getContent()
//            param.put("grade", Discuss.grade); //mspeakcontent.getGrade()
            out.print(param);
            out.flush();
//            conn.getResponseCode();
            Log.i("aas", String.valueOf(conn.getResponseCode()));
            in=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line="";
            while((line=in.readLine())!=null)
            {
                result+=line;
            }

        } catch (IOException | JSONException e) {
            networks.networksss=10;
            e.printStackTrace();
        } finally {
            try {
                if(out!=null)
                {
                    out.close();
                }
                if(in!=null)
                {
                    in.close();
                }
            } catch (IOException e)
            {
                networks.networksss=10;
                e.printStackTrace();
            }
        }
        return result;
    }
    public static void getCoachlike(String json,String url){
        JSONArray jsonArray;
        JSONObject jsonObject;
        String jsonString;
        String  response = Coachlikess(json,url);
        jsonString = response;
        try {
            jsonObject = new JSONObject(jsonString);
//            jsonArray = jsonObject.getJSONArray("drivingList");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                mspeakcontent = new Discuss();
//                try {
//                    jsonObject = jsonArray.getJSONObject(i);
            Discuss.res=jsonObject.getString("res");
            Log.i("res",Discuss.res);
//                    Discuss.error=jsonObject.getString("error");
//                    mspeakcontent.setres(jsonObject.getString("res"));// = jsonObject.getString("imageUrl");
//                    mspeakcontent.seterror(jsonObject.getString("error"));//.newstime = jsonObject.getString("name");
//                    mspeakList.add(mspeakcontent);
//                    Log.i("res",mspeakcontent.getres());
        } catch (JSONException e) {
            networks.networksss=10;
//            Toast.makeText(context,String.valueOf(conn.getResponseCode()), Toast.LENGTH_LONG).show();
            e.printStackTrace();
//                }
//            }
        }
//        return mspeakList;
    }

    public static String Coachlikess(String json,String url)
    {
        PrintWriter out=null;
        BufferedReader in=null;
//        String url=Params.addUserCoach;
        String result="";
        List<Discuss>   aa;
        try
        {
            URL realUrl=new URL(url);
            HttpURLConnection conn= (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(1000 * 8);
            conn.setReadTimeout(300000);
            conn.connect();
            out=new PrintWriter(conn.getOutputStream());
//            JSONObject param = new JSONObject();
//            param.put("cmd", Discuss.cmd);//mspeakcontent.getCmd()
//            param.put("phone", Discuss.likephone);//mspeakcontent.getUserTimestamp()
//            param.put("name", Discuss.likename);//mspeakcontent.getDrivingTimestamp()
//            param.put("coachTimestamp",Discuss.coachTimestamp );//mspeakcontent.getCoachTimestamp()
            out.print(json);
            out.flush();
//            conn.getResponseCode();
            Log.i("aas", String.valueOf(conn.getResponseCode()));
            in=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line="";
            while((line=in.readLine())!=null)
            {
                result+=line;
            }

        } catch (IOException  e) {//| JSONException
            networks.networksss=10;
            e.printStackTrace();
        } finally {
            try {
                if(out!=null)
                {
                    out.close();
                }
                if(in!=null)
                {
                    in.close();
                }
            } catch (IOException e)
            {
                networks.networksss=10;
                e.printStackTrace();
            }
        }
        return result;
    }
    public void HttpGet(String url)
    {
        new Thread(){

            @Override
            public void run() {
                try {
                    URL url=new URL("http://www.baidu.com");
                    InputStream is=url.openStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(is));
                    StringBuffer sb=new StringBuffer();
                    String line=null;
                    try {
                        while ((line=reader.readLine())!=null)
                        {
                            sb.append(line+"/n");
                        }
                        MainApplication.Data=sb.toString();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }finally {
                        try {
                            is.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    Log.e("sys","MainApplication.Data="+MainApplication.Data);

                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

}
