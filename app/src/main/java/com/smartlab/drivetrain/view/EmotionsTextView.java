package com.smartlab.drivetrain.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;

import com.smartlab.drivetrain.license.MainApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by smartlab on 15/10/13.
 */
public class EmotionsTextView extends HandyTextView {
    public EmotionsTextView(Context context) {
        super(context);
    }

    public EmotionsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmotionsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)){
            super.setText(replace(text),type);
        }
        super.setText(text, type);
    }


    private Pattern buildPattern() {
        StringBuilder patternString = new StringBuilder(
                MainApplication.mEmoticons.size() * 3);
        patternString.append('(');
        for (int i = 0; i < MainApplication.mEmoticons.size(); i++) {
            String s = MainApplication.mEmoticons.get(i);
            patternString.append(Pattern.quote(s));
            patternString.append('|');
        }
        patternString.replace(patternString.length() - 1,
                patternString.length(), ")");
        return Pattern.compile(patternString.toString());
    }

    private CharSequence replace(CharSequence text) {
        try {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            Pattern pattern = buildPattern();
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                if (MainApplication.mEmoticonsId.containsKey(matcher.group())) {
                    int id = MainApplication.mEmoticonsId.get(matcher.group());
                    Bitmap bitmap = BitmapFactory.decodeResource(
                            getResources(), id);
                    if (bitmap != null) {
                        ImageSpan span = new ImageSpan(getContext(), bitmap);
                        builder.setSpan(span, matcher.start(), matcher.end(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
            return builder;
        } catch (Exception e) {
            return text;
        }
    }
}
