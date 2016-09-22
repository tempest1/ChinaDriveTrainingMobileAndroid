package com.smartlab.drivetrain.exam;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.detail.LoadUrl;
import com.smartlab.drivetrain.license.R;

/**
 * Created by smartlab on 15/12/25.
 * 考试秘籍
 */
public class Esoterica extends BaseActivity implements View.OnClickListener {

    static String subject_two = "keErSecrit.html";
    static String subject_three = "keSiSecrit.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esoterica_layout);
        initView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.subject_two).setOnClickListener(this);
        findViewById(R.id.subject_three).setOnClickListener(this);
        findViewById(R.id.subject_two_).setOnClickListener(this);
        findViewById(R.id.subject_three_).setOnClickListener(this);
        findViewById(R.id.detail_back).setOnClickListener(this);
        findViewById(R.id.exam_teach_one).setOnClickListener(this);
        findViewById(R.id.exam_teach_four).setOnClickListener(this);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText("考试秘籍");

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_back:
                close(this);
                break;
            case R.id.subject_two:
                esoterica(LoadUrl.class, Params.Exam_two_online);
                break;
            case R.id.subject_three:
                esoterica(LoadUrl.class, Params.Exam_three_online);
                break;
            case R.id.subject_two_:
                esoterica(LoadHtml.class,subject_two);
                break;
            case R.id.subject_three_:
                esoterica(LoadHtml.class,subject_three);
                break;
            case R.id.exam_teach_one:
                esoterica(LoadUrl.class,Params.Exam_One_online);
                break;
            case R.id.exam_teach_four:
                esoterica(LoadUrl.class,Params.Exam_four_online);
                break;
            default:break;
        }
    }
    private void esoterica(Class<?> Class,String fileName) {
        Intent intent = new Intent();
        intent.setClass(this, Class);
        if (!TextUtils.isEmpty(fileName)) intent.putExtra("url",fileName);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
