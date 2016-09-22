package com.smartlab.drivetrain.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.fragment.ConsultPage;
import com.smartlab.drivetrain.fragment.Consults;
import com.smartlab.drivetrain.license.R;

/**
 * Created by smartlab on 16/1/5.
 * 咨询
 */
public class Consult extends BaseActivity implements View.OnClickListener ,RadioGroup.OnCheckedChangeListener{
    private FragmentManager manager;
    private RadioButton complain;
    private RadioButton complains;
    private ConsultPage consultPage;
    private Consults consults;
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
        String FRAGMENT_TAG_EvaluatePage = "EvaluatePage";
        String FRAGMENT_TAG_Evaluates = "Evaluates";

        FragmentTransaction transaction = manager.beginTransaction();
        hideFragments(transaction);
        switch (i){
            case 0:
                if (consultPage == null){
                    consultPage = new ConsultPage();
                    transaction.add(R.id.complain_content, consultPage,FRAGMENT_TAG_EvaluatePage);
                }
                transaction.show(consultPage);
                complain.setChecked(true);
                break;
            case 1:
                if (consults == null){
                    consults = new Consults();
                    transaction.add(R.id.complain_content, consults,FRAGMENT_TAG_Evaluates);
                }
                transaction.show(consults);
                complains.setChecked(true);
                break;
            default:break;
        }
        transaction.commit();

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (consultPage != null){
            transaction.hide(consultPage);
        }
        if (consults != null){
            transaction.hide(consults);
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
        complain.setText(getResources().getString(R.string.consult));
        complains.setText(getResources().getString(R.string.consults));
        radio_group.setOnCheckedChangeListener(this);
        detail_title.setText("我的咨询");


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
