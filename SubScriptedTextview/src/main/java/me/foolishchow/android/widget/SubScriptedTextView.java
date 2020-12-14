package me.foolishchow.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

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

    public static final int VERTICAL_ALIGN_TOP = 2;
    public static final int VERTICAL_ALIGN_CENTER = 1;
    public static final int VERTICAL_ALIGN_BOTTOM = 0;

    @IntDef({VERTICAL_ALIGN_BOTTOM, VERTICAL_ALIGN_CENTER, VERTICAL_ALIGN_TOP})
    public @interface VerticalAlign {
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
                    R.styleable.SubScriptedTextView_ss_left_text_color,
                    R.styleable.SubScriptedTextView_ss_left_text_padding,
                    R.styleable.SubScriptedTextView_ss_left_font_family,
                    R.styleable.SubScriptedTextView_ss_left_text_align
            );
        }

        String rightSubScript = ta.getString(R.styleable.SubScriptedTextView_ss_right_text);
        if (!TextUtils.isEmpty(rightSubScript)) {
            mRightSubScript = createSubscript(rightSubScript, ta,
                    R.styleable.SubScriptedTextView_ss_right_text_size,
                    R.styleable.SubScriptedTextView_ss_right_text_color,
                    R.styleable.SubScriptedTextView_ss_right_text_padding,
                    R.styleable.SubScriptedTextView_ss_right_font_family,
                    R.styleable.SubScriptedTextView_ss_right_text_align
            );
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
            @StyleableRes int textColorIndex,
            @StyleableRes int textPadding,
            @StyleableRes int textFontFamily,
            @StyleableRes int textVerticalAlign
    ) {
        SpannableString spannableString = new SpannableString(subscript);
        int end = subscript.length();

        int textSize = (int) ta.getDimension(textSizeIndex, -1);
        if (textSize != -1) {
            spannableString.setSpan(new AbsoluteSizeSpan(textSize), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        int textColor = ta.getColor(textColorIndex, -1);
        if (textColor != -1) {
            spannableString.setSpan(new ForegroundColorSpan(textColor), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        int fontFamily = -1;
        Typeface font = null;
        try {
            fontFamily = ta.getResourceId(textFontFamily, -1);
            if (fontFamily != -1) {
                font = ResourcesCompat.getFont(getContext(), fontFamily);
                spannableString.setSpan(new CustomTypefaceSpan(font), 0, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception e) {

        }


        int align = ta.getInt(textVerticalAlign, VERTICAL_ALIGN_BOTTOM);
        if (align != VERTICAL_ALIGN_BOTTOM) {
            float size = textSize;
            if (size == -1) {
                size = getTextSize();
            }
            spannableString.setSpan(new VerticalAlignSpan(font, align, size), 0, end,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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

    private SpannableStringBuilder mTextBuilder;

    private CharSequence createText(CharSequence text) {
        if (mTextBuilder == null) {
            mTextBuilder = new SpannableStringBuilder();
        } else {
            mTextBuilder.clearSpans();
            mTextBuilder.clear();
        }
        if (mLeftSubScript != null) {
            mTextBuilder.append(mLeftSubScript);
        }
        mTextBuilder.append(text);
        if (mRightSubScript != null) {
            mTextBuilder.append(mRightSubScript);
        }
        return mTextBuilder;
    }


    @SuppressLint("ParcelCreator")
    static class CustomTypefaceSpan extends TypefaceSpan {

        private final Typeface newType;

        private CustomTypefaceSpan(final Typeface type) {
            super("");
            newType = type;
        }

        @Override
        public void updateDrawState(final TextPaint textPaint) {
            apply(textPaint, newType);
        }

        @Override
        public void updateMeasureState(final TextPaint paint) {
            apply(paint, newType);
        }

        private void apply(final Paint paint, final Typeface tf) {
            int oldStyle;
            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            int fake = oldStyle & ~tf.getStyle();
            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.getShader();

            paint.setTypeface(tf);
        }
    }


    /**
     * 使TextView中不同大小字体垂直居中
     */
    static class VerticalAlignSpan extends ReplacementSpan {
        private float mFontSize;    //字体大小sp
        @VerticalAlign
        private int mAlign;
        @Nullable
        private Typeface mTypeface;

        public VerticalAlignSpan(
                @Nullable Typeface typeface,
                @VerticalAlign int align,
                float fontSizeSp
        ) {
            mTypeface = typeface;
            mAlign = align;
            mFontSize = fontSizeSp;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            text = text.subSequence(start, end);
            Paint p = getCustomTextPaint(paint);
            return (int) p.measureText(text.toString());
        }

        @Override
        public void draw(Canvas canvas, CharSequence text,
                         int start, int end, float x, int top, int y, int bottom,
                         Paint paint) {
            text = text.subSequence(start, end);
            Paint p = getCustomTextPaint(paint);
            Paint.FontMetricsInt fm = p.getFontMetricsInt();
            if (mAlign == VERTICAL_ALIGN_CENTER) {
                canvas.drawText(text.toString(), x,
                        y - ((y + fm.descent + y + fm.ascent) - (bottom + top)) * .5f, p);
            } else {
                canvas.drawText(text.toString(), x,
                        y  + fm.ascent, p);
            }
            //此处重新计算y坐标，使字体居中
        }

        private TextPaint getCustomTextPaint(Paint srcPaint) {
            TextPaint paint = new TextPaint(srcPaint);
            paint.setTextSize(mFontSize);   //设定字体大小
            if (mTypeface != null) {
                paint.setTypeface(mTypeface);
            }
            return paint;
        }
    }
}
