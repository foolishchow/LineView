package me.foolishchow.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.SubscriptSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.appcompat.widget.AppCompatTextView;

import me.foolishchow.android.subscriptedtextview.R;

public class SubScriptedTextView extends AppCompatTextView {
    public SubScriptedTextView(@NonNull Context context) {
        this(context, null);
    }

    public SubScriptedTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubScriptedTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs);
    }

    @Nullable
    private SpannableString mLeftSubScript;
    @Nullable
    private SpannableString mRightSubScript;


    private void initAttribute(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SubScriptedTextView);

        String leftSubscript = ta.getString(R.styleable.SubScriptedTextView_ss_left_text);
        if (!TextUtils.isEmpty(leftSubscript)) {

            mLeftSubScript = createSubscript(leftSubscript, ta,
                    R.styleable.SubScriptedTextView_ss_left_text_size,
                    R.styleable.SubScriptedTextView_ss_left_text_color);
        }

        String rightSubScript = ta.getString(R.styleable.SubScriptedTextView_ss_right_text);
        if (!TextUtils.isEmpty(rightSubScript)) {
            mRightSubScript = createSubscript(rightSubScript, ta,
                    R.styleable.SubScriptedTextView_ss_right_text_size,
                    R.styleable.SubScriptedTextView_ss_right_text_color);
        }
        ta.recycle();
        if (mLeftSubScript != null || mRightSubScript != null) {
            setText(getText());
        }
    }

    private SpannableString createSubscript(
            String subscript,
            TypedArray ta,
            @StyleableRes int textSizeIndex,
            @StyleableRes int textColorIndex
    ) {
        SpannableString spannableString = new SpannableString(subscript);
        int end = subscript.length();
        int textSize = (int) ta.getDimension(textSizeIndex, -1);
        int textColor = ta.getColor(textColorIndex, -1);
        if (textSize != -1) {
            spannableString.setSpan(new AbsoluteSizeSpan(textSize), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (textColor != -1) {
            spannableString.setSpan(new ForegroundColorSpan(textColor), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (mLeftSubScript != null || mRightSubScript != null) {
            super.setText(createText(text), BufferType.SPANNABLE);
        } else {
            super.setText(text, type);
        }
    }

    private CharSequence createText(CharSequence text) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        if (mLeftSubScript != null) {
            ssb.append(mLeftSubScript);
        }
        ssb.append(text);
        if (mRightSubScript != null) {
            ssb.append(mRightSubScript);
        }
        return ssb;
    }
}
