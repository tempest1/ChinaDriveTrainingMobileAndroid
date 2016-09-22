package com.smartlab.drivetrain.view;

import android.content.Context;

import com.smartlab.drivetrain.base.BaseDialog;
import com.smartlab.drivetrain.license.R;

/**
 * Created by smartlab on 15/11/24.
 */
public class FlippingLoading extends BaseDialog {
    private FlippingImageView imageView;
    private HandyTextView textView;
    private String mText;
    public FlippingLoading(Context context,String mText) {
        super(context);
        this.mText = mText;
        initView();
    }

    private void initView() {
        setContentView(R.layout.flipping_loading);
        imageView = (FlippingImageView) findViewById(R.id.fliImage);
        textView = (HandyTextView) findViewById(R.id.loadingMsg);
        imageView.startAnimation();
        textView.setText(mText);

    }

    public void setmText(String text) {
        mText = text;
        textView.setText(mText);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}
