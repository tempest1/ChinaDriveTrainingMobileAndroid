package com.smartlab.drivetrain.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.smartlab.drivetrain.adapter.TextWatcherImp;
import com.smartlab.drivetrain.license.R;

/**
 * Created by yu on 2015/8/18.
 * 自动提示文本框
 */
public class MyAutocompleteTextView extends AutoCompleteTextView {
    private Context context;
    private Drawable mClearDrawable;
    private TextWatcherCallBack mCallback;
    public MyAutocompleteTextView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MyAutocompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public MyAutocompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        mClearDrawable = context.getResources().getDrawable(R.drawable.delete_selector);
        mCallback = null;
        //重写了TextWatcher，在具体实现时就不用每个方法都实现，减少代码量
        TextWatcher textWatcher = new TextWatcherImp() {
            @Override
            public void afterTextChanged(Editable s) {
                //更新状态，检查是否显示删除按钮
                updateCleanable(length(), true);
                //如果有在activity中设置回调，则此处可以触发
                if(mCallback != null)
                    mCallback.handleMoreTextChanged();
            }
        };
        this.addTextChangedListener(textWatcher);
        this.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //更新状态，检查是否显示删除按钮
                updateCleanable(length(), hasFocus);
            }


        });
    }

    public interface TextWatcherCallBack {
        public void handleMoreTextChanged();
    }

    private void updateCleanable(int length, boolean hasFocus) {
        if(length() > 0 && hasFocus)
            setCompoundDrawablesWithIntrinsicBounds(null, null, mClearDrawable, null);
        else
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        //可以获得上下左右四个drawable，右侧排第二。图标没有设置则为空。
        Drawable rightIcon = getCompoundDrawables()[DRAWABLE_RIGHT];
        if (rightIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            //检查点击的位置是否是右侧的删除图标
            //注意，使用getRwwX()是获取相对屏幕的位置，getX()可能获取相对父组件的位置
            int leftEdgeOfRightDrawable = getRight() - getPaddingRight()
                    - rightIcon.getBounds().width();
            if (event.getRawX() >= leftEdgeOfRightDrawable) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
}
