package com.smartlab.drivetrain.update;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.UpdateInfo;
import com.smartlab.drivetrain.model.networks;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by smartlab on 15/9/19.
 */
public class Check {
    public static final String APK_DOWNLOAD_URL = "url";
    public static final String APK_UPDATE_CONTENT = "updateInfo";
    private static final String TAG = "Check";
    private Thread mThread;
    /**
     * Heart of the library. Check if an update is available for download
     * parsing the desktop Play Store page of the app
     */
    private Activity mContext;

    public Check(Activity mContext) {
        this.mContext = mContext;
    }

    public void checkForUpdates(final String url) {
        mThread = new Thread() {
            @Override
            public void run() {
                //if (isNetworkAvailable(mContext)) {

                String json = sendPost(url);
                if (json != null) {
                    parseJson(json);
                } else {
                    Log.e(TAG, "can't get app update json");
                }
                //}
            }

        };
        mThread.start();
    }

    protected String sendPost(String urlStr) {
        HttpURLConnection uRLConnection = null;
        InputStream is = null;
        BufferedReader buffer = null;
        String result = null;
        try {
            URL url = new URL(urlStr);
            uRLConnection = (HttpURLConnection) url.openConnection();
//            uRLConnection.setDoInput(true);
//            uRLConnection.setDoOutput(true);
//            uRLConnection.setRequestMethod("POST");
//            uRLConnection.setUseCaches(false);
//            uRLConnection.setConnectTimeout(10 * 1000);
//            uRLConnection.setReadTimeout(10 * 1000);
//            uRLConnection.setInstanceFollowRedirects(false);
//            uRLConnection.setRequestProperty("Connection", "Keep-Alive");
//            uRLConnection.setRequestProperty("Charset", "UTF-8");
//            uRLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
//            uRLConnection.setRequestProperty("Content-Type", "text/json");
//            uRLConnection.connect();

            is = uRLConnection.getInputStream();

            String content_encode = uRLConnection.getContentEncoding();

            if (null != content_encode && !"".equals(content_encode) && content_encode.equals("gzip")) {
                is = new GZIPInputStream(is);
            }

            buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
            }
            result = strBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, "http post error", e);
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (uRLConnection != null) {
                uRLConnection.disconnect();
            }
        }
        if (!Util.isEmptyString(result))
            Log.e("result", result);
        return result;
    }


    private void parseJson(String json) {
        mThread.interrupt();
        Looper.prepare();
        LogUtils.e("new version is available");
        try {

            Gson gson = new Gson();
            Type type = new TypeToken<UpdateInfo>(){}.getType();
                UpdateInfo updateInfo = gson.fromJson(json, type);

                String updateMessage = mContext.getResources().getString(R.string.dialog_update_msg) + updateInfo.getVerName() + "\n" + updateInfo.getDescription();
                String apkUrl = updateInfo.getUrl();
                int apkCode = updateInfo.getVerCode();
                int versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;

                Log.e("VersionCode", versionCode + "当前版本");

                if (apkCode > versionCode) {
//                    showNotification(updateMessage,apkUrl);
                    showDialog(updateMessage, apkUrl);
                } else {
                    //Toast.makeText(mContext, mContext.getString(R.string.app_no_new_update), Toast.LENGTH_SHORT).show();
                }


        } catch (PackageManager.NameNotFoundException ignored) {
            Log.e(TAG, "parse json error", ignored);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * Show dialog
     */
    public void showDialog(String content, String apkUrl) {
        UpdateDialog d = new UpdateDialog();
        Bundle args = new Bundle();
        args.putString(APK_UPDATE_CONTENT, content);
        args.putString(APK_DOWNLOAD_URL, apkUrl);
        d.setArguments(args);
        d.show(mContext.getFragmentManager(), null);
    }

    /**
     * Show Notification
     */
    public void showNotification(String content, String apkUrl) {
        android.app.Notification noti;
        Intent myIntent = new Intent(mContext, DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = mContext.getApplicationInfo().icon;
        noti = new NotificationCompat.Builder(mContext).setTicker(mContext.getString(R.string.newUpdateAvailable))
                .setContentTitle(mContext.getString(R.string.newUpdateAvailable)).setContentText(content).setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent).build();

        noti.flags = android.app.Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

}
