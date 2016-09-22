package com.smartlab.drivetrain.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.license.R;

/**
 * Created by smartlab on 15/12/24.
 * vip专享
 */
public class VipExclusive extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vip_exclusive);
        initView();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.detail_back).setOnClickListener(this);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_title.setText(getResources().getString(R.string.vip));
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
            default:break;
        }
    }
}
