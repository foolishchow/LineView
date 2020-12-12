package me.foolishchow.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.SubscriptSpan;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import me.foolishchow.android.subscriptedtextview.R;

public class SubScriptedTextView extends AppCompatTextView {
    public SubScriptedTextView(@NonNull Context context) {
        this(context,null);
    }

    public SubScriptedTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SubScriptedTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context,attrs);
    }

    @Nullable
    private CharSequence mLeftSubScript;
    @Nullable
    private CharSequence mRightSubScript;

    private void initAttribute(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SubScriptedTextView);
        mLeftSubScript = ta.getString(R.styleable.SubScriptedTextView_ss_left_text);
        mRightSubScript = ta.getString(R.styleable.SubScriptedTextView_ss_right_text);
        ta.recycle();
        if(mLeftSubScript != null || mRightSubScript != null){
            setText(getText());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if(mLeftSubScript != null || mRightSubScript != null){
            super.setText(createText(text),BufferType.SPANNABLE);
        }else{
            super.setText(text, type);
        }
    }

    private CharSequence createText(CharSequence text){
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        if(mLeftSubScript != null){
            ssb.append(mLeftSubScript);
            //SpannableString spannableString = new SpannableString();

        }
        ssb.append(text);
        if(mRightSubScript != null){
            ssb.append(mRightSubScript);
        }
        SubscriptSpan subscriptSpan = new SubscriptSpan();

        return ssb;
    }
}
