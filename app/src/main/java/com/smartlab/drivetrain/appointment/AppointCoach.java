package com.smartlab.drivetrain.appointment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.fragment.AppointPage;
import com.smartlab.drivetrain.fragment.Appoints;
import com.smartlab.drivetrain.license.R;

/**
 * Created by smartlab on 16/1/4.
 * 预约教练
 */
public class AppointCoach extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    private FragmentManager manager;
    private RadioButton complain;
    private RadioButton complains;
    private AppointPage appointPage;
    private Appoints appoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate_complain);
        initView();
        manager = getSupportFragmentManager();
        setTabSelection(0);
    }

    private void setTabSelection(int i) {
        // 每次选中之前先清楚掉上次的选中状态
        String FRAGMENT_TAG_COMPLAIN = "COMPLAIN";
        String FRAGMENT_TAG_COMPLAIN_LIST = "COMPLAIN_LIST";

        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (i){
            case 0:
                if (appointPage == null){
                    appointPage = new AppointPage();
                    transaction.add(R.id.complain_content, appointPage,FRAGMENT_TAG_COMPLAIN);
                }
                transaction.show(appointPage);
                complain.setChecked(true);
                break;
            case 1:
                if (appoints == null){
                    appoints = new Appoints();
                    transaction.add(R.id.complain_content, appoints,FRAGMENT_TAG_COMPLAIN_LIST);
                }
                transaction.show(appoints);
                complains.setChecked(true);
                break;
            default:break;
        }
        transaction.commit();

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (appointPage != null){
            transaction.hide(appointPage);
        }
        if (appoints != null){
            transaction.hide(appoints);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.detail_back).setOnClickListener(this);
        RadioGroup radio_group = (RadioGroup) findViewById(R.id.radio_group);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        complain = (RadioButton) findViewById(R.id.complain);
        complains = (RadioButton) findViewById(R.id.complains);
        radio_group.setOnCheckedChangeListener(this);
        detail_title.setText("找教练");
        complain.setText(getString(R.string.appoint));
        complains.setText(getString(R.string.appoints));
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.complain:
                setTabSelection(0);
                break;
            case R.id.complains:
                setTabSelection(1);
                break;
            default:break;
        }
    }

}
