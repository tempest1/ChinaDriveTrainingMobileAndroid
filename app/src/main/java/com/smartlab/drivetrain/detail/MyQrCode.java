package com.smartlab.drivetrain.detail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.qrcode.QRCodeUtil;
import com.smartlab.drivetrain.util.FileUtils;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.PhotoUtils;
import com.smartlab.drivetrain.util.ScreenUtils;
import com.smartlab.drivetrain.util.Util;

import java.io.File;

/**
 * Created by smartlab on 16/1/23.
 * 我的二维码图片
 */
public class MyQrCode extends BaseActivity implements View.OnClickListener{
    private ImageView qr_code;
    private String qrCodePath;
    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_qr_code);
        initView();
    }

    @Override
    protected void initData() {
        User user = MainApplication.getUserInfo();
        if (user != null) {

            appAction.getMyQRCode("getMyQRcode", user.getTimeStamp(), Params.getMyQRCode, new ActionCallBackListener<String>() {
                @Override
                public void onSuccess(String data) {
                    LogUtils.e(data);
                    createQrCode(data);
                }

                @Override
                public void onFailure(String errorEvent, String message) {
                    showToast(message);
                }
            });
        }
    }

    @Override
    protected void initView() {
        findViewById(R.id.detail_back).setOnClickListener(this);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText(getString(R.string.my_qr_code));
        qr_code = (ImageView) findViewById(R.id.qr_code);
        readQeCode();
    }

    private void readQeCode() {
        /**
         * 创建个人二维码存储路径
         */
        FileUtils.createNewFile(this, Params.QrCode);
        User user = MainApplication.getUserInfo();
        if (user != null){
            String qr_Code_path = user.getPhone();
            qr_Code_path = Util.makeMD5(qr_Code_path);

            qrCodePath = FileUtils.getRootPath() + Params.QrCode + qr_Code_path;
            LogUtils.e(qrCodePath);
            File file = new File(qrCodePath);
            if (file.exists()){
                Bitmap bitmap = PhotoUtils.getBitmapFromFile(qrCodePath);
                qr_code.setImageBitmap(bitmap);
                LogUtils.e("");
//                if (!bitmap.isRecycled()) {
//                    bitmap.recycle();
//                }
            } else {
                initData();
            }
        }
    }

    //
    private void createQrCode(final String url) {
        if (!TextUtils.isEmpty(url)){
            //  兼容包
            if (!TextUtils.isEmpty(qrCodePath)) {
                final BitmapDrawable bitmap = (BitmapDrawable) ContextCompat.getDrawable(this, R.mipmap.app_iconss);
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        LogUtils.e("--begin----");
                        QRCodeUtil.createQRImage(url, ScreenUtils.getScreenW() - 100, ScreenUtils.getScreenW() - 100, bitmap.getBitmap(), qrCodePath);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        if (flag) readQeCode();
                        flag = false;
                    }
                }.execute();

            }
        } else {
            showToast("您的地址有误");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
                close(this);
                break;
            default:break;
        }
    }
}
