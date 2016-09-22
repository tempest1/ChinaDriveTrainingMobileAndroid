package com.smartlab.drivetrain.exam;

import android.os.Bundle;

import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.util.LogUtils;

/**
 * Created by smartlab on 15/12/17.
 * 科目四考试
 */
public class ExamFour extends ExamBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_on_line);
        initData();
        initView();
        modifyView();
        initEvent();
        loadExamFour();
    }

    private void modifyView() {
        detail_title.setText(getResources().getString(R.string.exam,"四"));
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
