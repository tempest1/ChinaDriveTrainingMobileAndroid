package com.smartlab.drivetrain.frum;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartlab.drivetrain.base.BaseActivity;
import com.smartlab.drivetrain.data.Params;
import com.smartlab.drivetrain.interfaces.ActionCallBackListener;
import com.smartlab.drivetrain.license.MainApplication;
import com.smartlab.drivetrain.license.R;
import com.smartlab.drivetrain.model.Card;
import com.smartlab.drivetrain.model.User;
import com.smartlab.drivetrain.util.LogUtils;
import com.smartlab.drivetrain.util.Util;
import com.smartlab.drivetrain.view.EmoticonsEditText;
import com.smartlab.drivetrain.view.LoadingDialog;

public class AddCard extends BaseActivity implements View.OnClickListener{
    public static String KEY_CARD_ID = "KEY_CARD_ID";
    private EmoticonsEditText edit_text;
    private LoadingDialog loading;
    private String plateId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        plateId = getIntent().getStringExtra("plateId");
    }

    @Override
    protected void initView() {
        Button detail_back = (Button) findViewById(R.id.detail_back);
        TextView detail_title = (TextView) findViewById(R.id.detail_title);
        detail_back.setOnClickListener(this);
        detail_title.setText("发帖");
        edit_text = (EmoticonsEditText) findViewById(R.id.edit_card);
        loading = new LoadingDialog(this, R.drawable.loading);
        Button send_card = (Button) findViewById(R.id.send_card);
        send_card.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                close(this);
                break;
            case R.id.send_card:
                addCard();
                break;
        }
    }

    /**
     *
     */
    private void addCard() {
       final String text = edit_text.getText().toString().trim();

        if (!TextUtils.isEmpty(text)) {
            loading.show();
            LogUtils.e("-------");
            if (TextUtils.isEmpty(plateId)) return;
            appAction.addCard("addCard", text, plateId, Params.ADD_CARD, new ActionCallBackListener<Void>() {
                @Override
                public void onSuccess(Void data) {
                    loading.dismiss();
                    showToast("发表成功...");

                    updateCardList(text);
                }

                @Override
                public void onFailure(String errorEvent, String message) {
                    loading.dismiss();
                    showToast(message);
                }
            });
        }
    }

    /**
     * 更新上一界面的cardList
     * @param text
     */
    private void updateCardList(String text) {
        Card card = new Card();
        User info = MainApplication.getUserInfo();
        card.setCreateTime(Util.getSystemCurrentTime());
        card.setName(info.getPhone());
        card.setTitle(text);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CARD_ID, card);
        intent.putExtras(bundle);
        AddCard.this.setResult(RESULT_OK, intent);
        AddCard.this.finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
    }

}
