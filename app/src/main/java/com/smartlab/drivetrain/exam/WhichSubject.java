package com.smartlab.drivetrain.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.license.R;

/**
 * Created by smartlab on 15/10/26.
 * 选择科目
 */
public class WhichSubject extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_layout);
        initView();
    }

    @Override
    protected void initData() {}

    @Override
    protected void initView() {
        findViewById(R.id.detail_back).setOnClickListener(this);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        findViewById(R.id.subject_four).setOnClickListener(this);
        findViewById(R.id.subject_one).setOnClickListener(this);
        detail_title.setVisibility(View.GONE);
        findViewById(R.id.error_one).setOnClickListener(this);
        findViewById(R.id.error_four).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.detail_back:
                this.finish();
                break;
            case R.id.subject_four:
                Exam(ExamFour.class);
                break;
            case R.id.subject_one:
                Exam(ExamOne.class);
                break;
            case R.id.error_one:
                Exam(MyError.class,1);
                break;
            case R.id.error_four:
                Exam(MyError.class,4);
                break;
            default:break;
        }
    }

    private void Exam(Class<?> Class) {
        this.Exam(Class,0);
    }

    private void Exam(Class<?> Class,int km) {
        Intent intent = new Intent();
        intent.setClass(this, Class);
        if (km != 0) intent.putExtra("title",km);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

}
