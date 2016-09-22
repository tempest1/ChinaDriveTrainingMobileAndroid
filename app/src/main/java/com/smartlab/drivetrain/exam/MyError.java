package com.smartlab.drivetrain.exam;

import android.os.Bundle;
import android.view.View;

import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.util.LogUtils;

/**
 * Created by smartlab on 15/12/17.
 * 我的错题
 */
public class MyError extends ExamBase{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_on_line);
        initData();
        initView();
        initEvent();
        modifyView();

    }

    //  隐藏和现实部分视图
    private void modifyView() {
        int km = getIntent().getIntExtra("title",0);
        LogUtils.e("当前错题类型：" + km);
        if (km == 0){
            detail_title.setText("我的错题");
        } else {
            if (km == 1) {
                detail_title.setText(getString(R.string.exam_error_one));
            } else if (km == 4)  {
                detail_title.setText(getString(R.string.exam_error_four));
            }
            loadError(km);
        }
        last_time.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        LogUtils.e("activity is resume");
        if (error_layout != null){
            error_layout.setProgressLoad(true);
        }
        super.onResume();

    }
}
