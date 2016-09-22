package com.smartlab.drivetrain.evaluate;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.fragment.EvaluatePage;
import com.smartlab.drivetrain.fragment.Evaluates;
import com.smartlab.drivetrain.license.R;

/**
 * Created by smartlab on 15/12/26.
 * 评价
 */
public class Evaluate extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    private FragmentManager manager;
    private RadioButton complain;
    private RadioButton complains;
    private EvaluatePage evaluatePage;
    private Evaluates evaluates;
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
                if (evaluatePage == null){
                    evaluatePage = new EvaluatePage();
                    transaction.add(R.id.complain_content, evaluatePage,FRAGMENT_TAG_EvaluatePage);
                }
                transaction.show(evaluatePage);
                complain.setChecked(true);
                break;
            case 1:
                if (evaluates == null){
                    evaluates = new Evaluates();
                    transaction.add(R.id.complain_content, evaluates,FRAGMENT_TAG_Evaluates);
                }
                transaction.show(evaluates);
                complains.setChecked(true);
                break;
            default:break;
        }
        transaction.commit();

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (evaluatePage != null){
            transaction.hide(evaluatePage);
        }
        if (evaluates != null){
            transaction.hide(evaluates);
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
        complain.setText(getResources().getString(R.string.evaluate));
        complains.setText(getResources().getString(R.string.evaluates));
        radio_group.setOnCheckedChangeListener(this);
        detail_title.setText("我的评价");
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
