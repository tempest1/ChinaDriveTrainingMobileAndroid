package com.smartlab.drivetrain.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.fragment.UserCenter;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.Modify;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.MoreInfo;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.FileUtils;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.PhotoUtils;
import com.smartlab.drivetrain.view.HandyTextView;
import com.smartlab.drivetrain.view.LoadingDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 用户详情页
 */
public class UserDetail extends BaseActivity implements View.OnClickListener,ActionCallBackListener<Void>{
    /**
     * 用户信心
     */
    private User info;
    /**
     * 用户真实姓名
     */
    private TextView detail_name;//用户真实姓名
    /**
     * 用户登录用户名
     */
    private TextView detail_phone;
    /**
     * 用户身份证信息
     **/
    private HandyTextView user_card;
    /**
     * 所在地区
     **/
    private HandyTextView user_loc;
//    /**
//     * 账户类型
//     **/
//    private HandyTextView user_type;
    /**
     * 注册时间
     */
    private HandyTextView register_time;
    /**
     * 用户性别
     * */
    private HandyTextView sex_text;
    private LoadingDialog loading;
    public final String cmd = "loginOut";
    private String location ;
    /**
     * 全局数据共享
     * */
    private SharedPreferences mpre = MainApplication.getPreferences();
    private View contentView;
    private PopupWindow popupWindow;
    private String fileName = "";
    private File tempFile;
    private int crop = 300;// 裁剪大小
    private static final String User = "user_head/";
    private ImageView iv_user_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(R.layout.userdetail,null);
        setContentView(contentView);
        initView();
        initFile();
        initData();
    }

    @Override
    protected void initData() {
        String defaultPath = Params.DATA_PATH + User + "user_head_photo.jpg";
        File file = new File(defaultPath);
        if (file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(defaultPath);
            iv_user_photo.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void initView() {
        detail_name = (TextView) findViewById(R.id.detail_name);
        detail_phone = (TextView) findViewById(R.id.detail_phone);
        user_card = (HandyTextView) findViewById(R.id.card_text);
        user_loc = (HandyTextView) findViewById(R.id.loc_text);
//        user_type = (HandyTextView) findViewById(R.id.type_text);
        register_time = (HandyTextView) findViewById(R.id.register_text);
        sex_text = (HandyTextView) findViewById(R.id.sex_text);
        Button detail_exit = (Button) findViewById(R.id.detail_exit);
        iv_user_photo = (ImageView) findViewById(R.id.iv_user_photo);
        Button back = (Button) findViewById(R.id.detail_back);
        detail_exit.setOnClickListener(UserDetail.this);
        back.setOnClickListener(UserDetail.this);
        loading = new LoadingDialog(this,R.drawable.loading);

        RelativeLayout icon = (RelativeLayout) findViewById(R.id.icon);
        RelativeLayout realName = (RelativeLayout) findViewById(R.id.realName);
        RelativeLayout id_Card = (RelativeLayout) findViewById(R.id.id_Card);
        RelativeLayout sex_layout = (RelativeLayout) findViewById(R.id.sex_layout);
        sex_layout.setOnClickListener(this);
        icon.setOnClickListener(this);
        realName.setOnClickListener(this);
        id_Card.setOnClickListener(this);
    }

    /**填充数据**/
    private void setData() {
        info = MainApplication.getUserInfo();
        BDLocation bdloc = MainApplication.getLocation();
        if (bdloc != null) {
            location = bdloc.getCity();
        } else {
            LogUtils.e("定位信息获取失败");
        }
        String temp = getResources().getString(R.string.not_write);
        if (info != null){
            temp = getResources().getString(R.string.click_write);
            // 真实姓名
            if (info.getRealName() != null){
                detail_name.setText(info.getRealName());
            } else {
                detail_name.setText(temp);
            }

            //手机号码
            if (info.getPhone() != null) {
                detail_phone.setText(info.getPhone());
            } else {
                detail_phone.setText(temp);
            }

            //身份证号码
            if (info.getIdCard() != null){
                user_card.setText(info.getIdCard());
            } else {
                user_card.setText(temp);
            }

            // 性别
            if (Boolean.valueOf(info.getSex())){
                sex_text.setText("女");
            } else {
                sex_text.setText("男");
            }
            //更多信息里面是用户的注册时间
            MoreInfo more = info.getUserInfo();
            if (more != null){

                if (more.getRegistTime() != null) {
                    String regText = more.getRegistTime();
                    String[] split = regText.split("\\s+");
                    LogUtils.i(split[0]);
                    register_time.setText(split[0]);
                } else {
                    register_time.setText("暂未记录");
                }
            } else {
                register_time.setText("暂未记录");
            }
        } else {
            detail_name.setText(temp);
            detail_phone.setText(temp);
            user_card.setText(temp);
        }

        if (!TextUtils.isEmpty(location)) {
            user_loc.setText(location);//通过定位来获取
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_back:
                close(UserDetail.this);
                break;
            case R.id.detail_exit:
                reLogin();
                break;
            //  点击修改用户头像
            case R.id.icon:
                pickPhoto();
                break;
            //  点击修改用户真实姓名
            case R.id.realName:
                editInfo(1);
                break;
            //  身份证号
            case R.id.id_Card:
                editInfo(2);
                break;
            case R.id.sex_layout:
                if (info != null) {
                    Intent intent = new Intent();
                    intent.setClass(this, ChooseSex.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info", info);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                }
                break;
            case R.id.camera:
                LogUtils.e("打开相机");
                openCamera();
                popupWindow.dismiss();
                break;
            case R.id.gallery:
                LogUtils.e("选择本机照片");
                PhotoUtils.selectPhoto(this);
                popupWindow.dismiss();
                break;
            case R.id.cancel:
                if (popupWindow != null) popupWindow.dismiss();
                break;
            default:break;
        }
    }

    public void initFile() {
        if(fileName.equals("")) {
            if(FileUtils.isSdcardExist()) {
                String path = Params.DATA_PATH + User;
                FileUtils.createDirFile(path);
                LogUtils.e("defaultPath:" + path);
                fileName = path + "user_head_photo.jpg";
                tempFile = new File(fileName);
            } else {
                showToast("请插入sd卡");
            }
        }
    }

    /**
     * 相册选择照片或者拍照上传，这里的上传只保留在本地的文件的某个路径下，再次进入则从指定的位置读取即可
     */
    private void pickPhoto() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.bottom_pop_carmer,null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.popuAnim);
        view.findViewById(R.id.camera).setOnClickListener(this);
        view.findViewById(R.id.gallery).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }
    /**
     * 调用相机
     */
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 打开相机
        intent.putExtra("output", Uri.fromFile(tempFile));
        startActivityForResult(intent, PhotoUtils.OPEN_CAMERA_CODE);
    }
    /**
     * 裁剪图片
     * @param uri Uri
     */
    public void cropPhoto(Uri uri) {
        LogUtils.e("-------------");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
//        intent.putExtra("output", Uri.fromFile(tempFile));
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", crop);
        intent.putExtra("outputY", crop);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PhotoUtils.CROP_PHOTO_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode){
            case PhotoUtils.OPEN_CAMERA_CODE:
                LogUtils.e("----------");
                cropPhoto(Uri.fromFile(tempFile));
                break;
            case PhotoUtils.OPEN_GALLERY_CODE:
                Uri uri = data.getData();
                if(uri != null) {
                    cropPhoto(uri);
                }
                break;
            case PhotoUtils.CROP_PHOTO_CODE:
                FileOutputStream out = null;
                try {
                    Bitmap bmap = data.getParcelableExtra("data");
                    if (!TextUtils.isEmpty(fileName)) out = new FileOutputStream(fileName);
                    if (bmap != null) {
                        iv_user_photo.setImageBitmap(bmap);
                        bmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }

    }

    private void editInfo(int i) {
        if (info == null) {
            showToast("信息为空");
            return;
        }
//        switch (i){
//            case 1:
//                if (!TextUtils.isEmpty(info.getRealName())){
//                    LogUtils.e(info.getRealName());
//                    return;
//                }
//                break;
//            case 2:
//                if (!TextUtils.isEmpty(info.getIdCard())){
//                    LogUtils.e(info.getIdCard());
//                    return;
//                }
//                break;
//            default:
//                LogUtils.e("信息：" + info.toString());
//                break;
//        }

        Intent intent = new Intent();
        intent.setClass(this, Modify.class);
        Bundle bundle = new Bundle();
        bundle.putInt("method", i);
        bundle.putSerializable("info", info);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
    }

    /**
     * 退出登录
     */
    private void reLogin() {
        loading.show();
        appAction.loginOut(cmd, Params.LOGOUT, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
        LogUtils.e("User detail is Resume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 处理成功
     *
     * @param data 返回数据
     */
    @Override
    public void onSuccess(Void data) {
        loading.dismiss();
        setResult(RESULT_OK);
        MainApplication.setBeanDefinitionReaderSid(null);
        UserCenter.alreadyLogin = false;
        //  退出登录的同时取消自动登录
        SharedPreferences.Editor editor = mpre.edit();
        editor.putBoolean("autoLogin", false);
        editor.apply();
        //  清除保存的用户名
        MainApplication.setUserInfo(null);
        close(this);
    }

    /**
     * 请求失败
     *
     * @param errorEvent 错误码
     * @param message    错误详情
     */
    @Override
    public void onFailure(String errorEvent, String message) {
        loading.dismiss();
        showToast(message);
    }
}
