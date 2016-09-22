package com.smartlab.drivetrain.service;

import android.os.Handler;
import android.util.Log;

import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * @Description: 后台下载图片
 * @author- 作者 smartLab
 * @create- net
 * @modify by:smartlab
 * @modify- time
 *
 */
public class PictureDownload {
    private static String TAG = "PictureDownload" ;
    public static final int IO_BUFFER_SIZE = 8 * 1024;
    private static ExecutorService LIMITED_TASK_EXECUTOR = null;
    private static final ExecutorService DEFAULT_TASK_EXECUTOR ;
    /**
     * 线程锁
     */
    private static Object lock = new Object();
    static {
        /**
         * 线程池管理
         */
        LIMITED_TASK_EXECUTOR = (ExecutorService) Executors
                .newFixedThreadPool(1);
        /**
         * 默认线程池大小
         */
        DEFAULT_TASK_EXECUTOR = LIMITED_TASK_EXECUTOR ;
    };
    private Handler handler;
    /**
     * 下载状态监听，提供回调
     */
    DownloadStateListener listener;
    /**
     * 图片保存路径，默认保存在全局配置参数中，可自定义，Params.IMAGE_CACHE
     */
    private String downloadPath;

    /**
     * 下载链接集合
     */
    private List<String> listURL;
    /**
     * 下载的图片数量个数
     */
    private int size = 0;

    /**
     * 下载完成回调接口
     */
    public interface DownloadStateListener {
        /**
         * 下载完成
         */
        void onFinish();

        /**
         * 下载失败
         */
        void onFailed();
    }

    /**
     * 构造函数
     * @param downloadPath  文件保存路径
     * @param listURL   下载地址
     * @param listener  下载监听回调
     */
    public PictureDownload(String downloadPath, List<String> listURL,
                           DownloadStateListener listener) {
        this.downloadPath = downloadPath;
        this.listURL = listURL;
        this.listener = listener;
    }
    /**
     * 构造函数
     * @param downloadPath  文件保存路径
     * @param listURL   下载地址
     * @param handler  下载监听回调
     */
    public PictureDownload(String downloadPath, List<String> listURL,
                           Handler handler,DownloadStateListener listener) {
        this.downloadPath = downloadPath;
        this.listURL = listURL;
        this.handler = handler;
        this.listener = listener;
    }

    /**
     * 开始下载
     */
    public void startDownload() {
        // 首先检测path是否存在
        File downloadDirectory = new File(downloadPath);
        if (!downloadDirectory.exists()) {
            downloadDirectory.mkdirs();
        }

        for (final String url : listURL) {
            //捕获线程池拒绝执行异常
            try {
                // 线程放入线程池
                DEFAULT_TASK_EXECUTOR.execute(new Runnable() {

                    @Override
                    public void run() {
                        downloadBitmap(url);
                    }
                });
            } catch (RejectedExecutionException e) {
                e.printStackTrace();
                Log. e(TAG, "thread pool rejected error");
                if (handler != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailed();
                        }
                    });
                } else {
                    if (listener != null) listener.onFailed(); // 下载成功回调
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (handler != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailed();
                        }
                    });
                } else {
                    if (listener != null) listener.onFailed(); // 下载成功回调
                }
            }

        }

    }

    /**
     * 下载图片
     *
     * @param urlString
     * @return cacheFile
     */
    private File downloadBitmap(String urlString) {
        //  对文件名采用MD5加密
        String fileName = Util.makeMD5(urlString.substring(urlString.lastIndexOf("/") + 1, urlString.length()));
        // 图片命名方式
        final File cacheFile = new File(downloadPath,fileName);
        /**
         * 文件存在返回该文件
         */
        if (cacheFile.exists()) return cacheFile;

        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            final InputStream in = new BufferedInputStream(
                    urlConnection.getInputStream(), IO_BUFFER_SIZE);

            out = new BufferedOutputStream(new FileOutputStream(cacheFile),
                    IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            // 每下载成功一个，统计一下图片个数
            statDownloadNum();
            return cacheFile;

        } catch (final IOException e) {
            // 有一个下载失败，则表示批量下载没有成功
            LogUtils. e("download " + urlString + " error:"+e.getMessage());
            if (handler != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailed();
                    }
                });
            } else {
                if (listener != null) listener.onFailed(); // 下载成功回调
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null ) {
                try {
                    out.close();
                } catch (final IOException e) {
                    Log. e(TAG, "Error in downloadBitmap - " + e);
                }
            }
        }

        return null ;
    }



    /**
     * 统计下载个数
     */
    private void statDownloadNum() {
        synchronized (lock) {
            size++;
            if (size == listURL .size()) {
                Log. d(TAG, "download finished total " + size);
                // 释放资源
                DEFAULT_TASK_EXECUTOR.shutdownNow();
                // 如果下载成功的个数与列表中 url个数一致，说明下载成功

                if (handler != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish();
                        }
                    });
                } else {
                    if (listener != null) listener.onFinish(); // 下载成功回调
                }
            }
        }
    }
    
}