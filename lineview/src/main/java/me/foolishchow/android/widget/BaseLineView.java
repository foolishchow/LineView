package me.foolishchow.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/12/2020 4:10 PM
 */
public abstract class BaseLineView extends ViewGroup {
    public BaseLineView(Context context) {
        super(context);
        initLayout(context);
        mLabelTextColor = Color.rgb(0x99, 0x99, 0x99);
        mLabelTextSize = SizeUtils.sp2px(14);
        setUp();
    }

    public BaseLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
        initAttribute(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout(context);
        initAttribute(context, attrs);
    }

    private static final int LABEL_TEXT_ALIGN_START = 0;
    private static final int LABEL_TEXT_ALIGN_CENTER = 1;
    private static final int LABEL_TEXT_ALIGN_END = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LABEL_TEXT_ALIGN_START, LABEL_TEXT_ALIGN_CENTER, LABEL_TEXT_ALIGN_END})
    public @interface LabelTextAlign {
    }

    public static final int BORDER_ADJUST_PADDING_NONE = 0;
    public static final int BORDER_ADJUST_PADDING_LEFT = 1;
    public static final int BORDER_ADJUST_PADDING_RIGHT = 2;
    public static final int BORDER_ADJUST_PADDING_ALL = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BORDER_ADJUST_PADDING_NONE, BORDER_ADJUST_PADDING_LEFT, BORDER_ADJUST_PADDING_RIGHT, BORDER_ADJUST_PADDING_ALL})
    public @interface BorderAdjustPadding {
    }

    private float mLeftIconWidth;
    private float mLeftIconHeight;
    private int mLeftIconSrc = -1;


    private int mRightIconSrc = -1;

    @Nullable
    private String mLabelText = "";
    private int mLabelTextColor;
    private int mLabelWidth;
    @LabelTextAlign
    private int mLabelAlign;
    private int mLabelTextSize;
    private int mLabelPadding;

    private int mContentMarginLeft = 0;
    private int mContentMarginRight = 0;
    private int mContentPaddingLeft = 0;
    private int mContentPaddingTop = 0;
    private int mContentPaddingRight = 0;
    private int mContentPaddingBottom = 0;
    private int mContentHeight = LayoutParams.WRAP_CONTENT;
    private int mContentBackground = -1;

    @BorderAdjustPadding
    private int mBorderTopAdjustPadding = BORDER_ADJUST_PADDING_NONE;
    private int mBorderTopWidth = 0;
    private int mBorderTopColor = Color.rgb(0xee, 0xee, 0xee);

    @BorderAdjustPadding
    private int mBorderBottomAdjustPadding = BORDER_ADJUST_PADDING_NONE;
    private int mBorderBottomWidth = 0;
    private int mBorderBottomColor = Color.rgb(0xee, 0xee, 0xee);


    private void initAttribute(Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseLineView);

        mLeftIconWidth = ta.getDimension(R.styleable.BaseLineView_lv_left_icon_width, LayoutParams.WRAP_CONTENT);
        mLeftIconHeight = ta.getDimension(R.styleable.BaseLineView_lv_left_icon_height, LayoutParams.WRAP_CONTENT);
        mLeftIconSrc = ta.getResourceId(R.styleable.BaseLineView_lv_left_icon, -1);

        mRightIconSrc = ta.getResourceId(R.styleable.BaseLineView_lv_right_icon, -1);

        mLabelText = ta.getString(R.styleable.BaseLineView_lv_label);
        mLabelTextSize = ta.getDimensionPixelSize(R.styleable.BaseLineView_lv_label_text_size, SizeUtils.sp2px(14));
        mLabelPadding = ta.getDimensionPixelSize(R.styleable.BaseLineView_lv_label_padding, 0);
        mLabelTextColor = ta.getInt(R.styleable.BaseLineView_lv_label_color, Color.rgb(0x99, 0x99, 0x99));
        mLabelWidth = ta.getDimensionPixelSize(R.styleable.BaseLineView_lv_label_width, LayoutParams.WRAP_CONTENT);
        mLabelAlign = ta.getInt(R.styleable.BaseLineView_lv_label_align, LABEL_TEXT_ALIGN_START);

        mContentMarginLeft = (int) ta.getDimension(R.styleable.BaseLineView_lv_content_margin_left, 0);
        mContentMarginRight = (int) ta.getDimension(R.styleable.BaseLineView_lv_content_margin_right, 0);

        mContentPaddingLeft = (int) ta.getDimension(R.styleable.BaseLineView_lv_content_padding_left, 0);
        mContentPaddingTop = (int) ta.getDimension(R.styleable.BaseLineView_lv_content_padding_top, 0);
        mContentPaddingRight = (int) ta.getDimension(R.styleable.BaseLineView_lv_content_padding_right, 0);
        mContentPaddingBottom = (int) ta.getDimension(R.styleable.BaseLineView_lv_content_padding_bottom, 0);

        mContentHeight = (int) ta.getDimension(R.styleable.BaseLineView_lv_content_height, LayoutParams.WRAP_CONTENT);
        mContentBackground = ta.getResourceId(R.styleable.BaseLineView_lv_content_background, -1);

        mBorderTopColor = ta.getColor(R.styleable.BaseLineView_border_top_color, mBorderTopColor);
        mBorderTopWidth = (int) ta.getDimension(R.styleable.BaseLineView_border_top_width, 0);
        mBorderBottomAdjustPadding = ta.getInt(R.styleable.BaseLineView_border_bottom_adjust_padding, BORDER_ADJUST_PADDING_NONE);

        mBorderBottomWidth = (int) ta.getDimension(R.styleable.BaseLineView_border_bottom_width, 0);
        mBorderBottomColor = ta.getColor(R.styleable.BaseLineView_border_bottom_color, mBorderBottomColor);
        mBorderTopAdjustPadding = ta.getInt(R.styleable.BaseLineView_border_top_adjust_padding, BORDER_ADJUST_PADDING_NONE);

        ta.recycle();
        setUp();
    }


    private void setUp() {
        setWillNotDraw(false);
        if (mLeftIconSrc != -1) {
            mLeftIcon.setImageResource(mLeftIconSrc);
        } else {
            mLeftIcon.setVisibility(GONE);
        }
        if (mRightIconSrc != -1) {
            mRightIcon.setImageResource(mRightIconSrc);
            mRightIcon.setVisibility(VISIBLE);
        } else {
            mRightIcon.setVisibility(GONE);
        }
        mLabel.setTextColor(mLabelTextColor);
        mLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLabelTextSize);
        setLabelText(mLabelText);
        if (mLabelAlign == LABEL_TEXT_ALIGN_CENTER) {
            mLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        } else if (mLabelAlign == LABEL_TEXT_ALIGN_END) {
            mLabel.setGravity(Gravity.END);
        } else {
            mLabel.setGravity(Gravity.START);
        }

        if (mLabelPadding > 0) {
            mLabel.setPadding(mLabelPadding, 0, 0, 0);
        }
        if (mContentBackground != -1) {
            mContent.setBackground(ContextCompat.getDrawable(getContext(), mContentBackground));
        }
        LayoutParams lp = mContent.getLayoutParams();
        lp.height = mContentHeight;
        mContent.setLayoutParams(lp);
        mContent.setPadding(mContentPaddingLeft, mContentPaddingTop, mContentPaddingRight, mContentPaddingBottom);
    }

    public void setLabelText(@Nullable String labelText) {
        if (labelText != null && mLabel != null) {
            mLabel.setText(labelText);
        }
    }

    private TextView mLabel;
    private ImageView mLeftIcon;
    private ViewGroup mContent;
    private ImageView mRightIcon;
    private Paint mBorderPaint;


    @LayoutRes
    public abstract int getLayout();

    private void initLayout(Context context) {
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);    // 只描边，不填充
        mBorderPaint.setAntiAlias(true);              // 设置抗锯齿
        mBorderPaint.setStrokeCap(Paint.Cap.BUTT);   // 设置圆角
        mBorderPaint.setDither(true);                 // 设置抖动

        View inflate = LayoutInflater.from(context).inflate(getLayout(), this, true);
        mContent = inflate.findViewById(R.id.line_view_content);
        mLabel = inflate.findViewById(R.id.line_view_label);
        mLeftIcon = inflate.findViewById(R.id.line_view_icon_left);
        mRightIcon = inflate.findViewById(R.id.line_view_icon_right);
    }


    private int mWidth = 0;
    private int mHeight = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        if (mBorderTopWidth > 0) {
            top += mBorderTopWidth;
        }
        int innerHeight = mHeight - top - getPaddingBottom() - mBorderBottomWidth;
        //Log.e(TAG, String.format("onLayout left %d top %d right %d bottom %d", l, t, r, b));
        if (mLeftIcon.getVisibility() != GONE) {
            int measuredHeight = mLeftIcon.getMeasuredHeight();
            int measuredWidth = mLeftIcon.getMeasuredWidth();
            int pad = (int) ((innerHeight - measuredHeight) * .5f);
            mLeftIcon.layout(left, pad + top, left + measuredWidth, measuredHeight + pad + top);
            left += measuredWidth;
        }

        if (mLabel.getVisibility() != GONE) {
            int measuredHeight = mLabel.getMeasuredHeight();
            int measuredWidth = mLabel.getMeasuredWidth();
            int pad = (int) ((innerHeight - measuredHeight) * .5f);
            mLabel.layout(left, pad + top, left + measuredWidth, measuredHeight + pad + top);
            left += measuredWidth;
        }

        if (mContent.getVisibility() != GONE) {
            int measuredHeight = mContent.getMeasuredHeight();
            int measuredWidth = mContent.getMeasuredWidth();
            int pad = (int) ((innerHeight - measuredHeight) * .5f);
            mContent.layout(left + mContentMarginLeft, pad + top, left + mContentMarginLeft + measuredWidth, measuredHeight + pad + top);
            left += measuredWidth + mContentMarginLeft + mContentMarginRight;
        }
        if (mRightIcon.getVisibility() != GONE) {
            int measuredHeight = mRightIcon.getMeasuredHeight();
            int measuredWidth = mRightIcon.getMeasuredWidth();
            int pad = (int) ((innerHeight - measuredHeight) * .5f);
            mRightIcon.layout(left, pad + top, left + measuredWidth, measuredHeight + pad + top);
            //left += measuredWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        //Log.e(TAG, String.format("width %s height %s", CustomViewUtils.getMeasureModeName(widthMeasureSpec), CustomViewUtils.getMeasureModeName(heightMeasureSpec)));
        //Log.e(TAG, String.format("width %d height %d", widthSize, heightSize));


        int leftIconHeight = 0;
        int leftIconWidth = 0;
        int widthSpec;
        int heightSpec;
        if (mLeftIcon.getVisibility() != GONE) {
            widthSpec = makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            if (mLeftIconWidth != LayoutParams.WRAP_CONTENT) {
                widthSpec = makeMeasureSpec((int) mLeftIconWidth, MeasureSpec.EXACTLY);
            }
            heightSpec = makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            if (mLeftIconHeight != LayoutParams.WRAP_CONTENT) {
                heightSpec = makeMeasureSpec((int) mLeftIconHeight, MeasureSpec.EXACTLY);
            }
            mLeftIcon.measure(widthSpec, heightSpec);
            leftIconHeight = mLeftIcon.getMeasuredHeight();
            leftIconWidth = mLeftIcon.getMeasuredHeight();
        }

        int labelWidth = 0;
        int labelHeight = 0;
        if (mLabel.getVisibility() != GONE) {
            widthSpec = makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            if (mLabelWidth != LayoutParams.WRAP_CONTENT) {
                widthSpec = makeMeasureSpec((int) mLabelWidth, MeasureSpec.EXACTLY);
            }
            heightSpec = makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            mLabel.measure(widthSpec, heightSpec);
            labelHeight = mLabel.getMeasuredHeight();
            labelWidth = mLabel.getMeasuredWidth();
        }

        int rightIconWidth = 0;
        int rightIconHeight = 0;
        if (mRightIcon.getVisibility() != GONE) {
            widthSpec = makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            //if(mLabelWidth != LayoutParams.WRAP_CONTENT){
            //    widthSpec = makeMeasureSpec((int) mLabelWidth,MeasureSpec.EXACTLY);
            //}
            heightSpec = makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            mRightIcon.measure(widthSpec, heightSpec);
            rightIconHeight = mRightIcon.getMeasuredHeight();
            rightIconWidth = mRightIcon.getMeasuredHeight();
        }


        if (widthSpecMode == MeasureSpec.EXACTLY) {
            int contentWidth = widthSize
                    - leftIconWidth - labelWidth - rightIconWidth
                    - getPaddingLeft() - getPaddingRight()
                    - mContentMarginLeft - mContentMarginRight;
            widthSpec = makeMeasureSpec(contentWidth, MeasureSpec.EXACTLY);
        } else {
            widthSpec = makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }

        if (mContentHeight >= 0) {
            heightSpec = makeMeasureSpec(mContentHeight, MeasureSpec.EXACTLY);
        } else if (heightSpecMode == MeasureSpec.EXACTLY) {
            heightSpec = makeMeasureSpec(heightSize - getPaddingTop() - getPaddingBottom() - mBorderTopWidth - mBorderBottomWidth, MeasureSpec.AT_MOST);
        } else {
            heightSpec = makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        mContent.measure(widthSpec, heightSpec);

        //Log.e(TAG, String.format("icon %d label %d content %d icon %d", leftIconWidth, labelWidth, mContent.getMeasuredWidth(), rightIconWidth));
        int measuredWidth;
        int measuredHeight;
        if (widthSpecMode == MeasureSpec.EXACTLY) {
            measuredWidth = widthSize;
        } else {
            measuredWidth = leftIconWidth + labelWidth + mContent.getMeasuredWidth() + rightIconWidth + getPaddingLeft() + getPaddingRight();
        }
        if (heightSpecMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else {
            measuredHeight = getPaddingBottom() + getPaddingTop() + mBorderTopWidth + mBorderBottomWidth + max(leftIconHeight, labelHeight, mContent.getMeasuredHeight(), rightIconHeight);
        }
        mWidth = measuredWidth;
        mHeight = measuredHeight;
        setMeasuredDimension(resolveSize(measuredWidth, widthMeasureSpec), resolveSize(measuredHeight, heightMeasureSpec));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBorderTopWidth > 0) {
            drawBorderTop(canvas);
        }
        if (mBorderBottomWidth > 0) {
            drawBorderBottom(canvas);
        }
        super.onDraw(canvas);
    }

    private void drawBorderTop(Canvas canvas) {
        mBorderPaint.setColor(mBorderTopColor);
        mBorderPaint.setStrokeWidth(mBorderTopWidth);
        float y = mBorderTopWidth * .5f;
        float startX = 0;
        float endX = mWidth;
        if (mBorderTopAdjustPadding == BORDER_ADJUST_PADDING_ALL) {
            startX = getPaddingStart();
            endX = mWidth - getPaddingEnd();
        } else if (mBorderTopAdjustPadding == BORDER_ADJUST_PADDING_LEFT) {
            startX = getPaddingStart();
        } else if (mBorderTopAdjustPadding == BORDER_ADJUST_PADDING_RIGHT) {
            endX = mWidth - getPaddingEnd();
        }
        canvas.drawLine(startX, y, endX, y, mBorderPaint);
    }

    private void drawBorderBottom(Canvas canvas) {
        mBorderPaint.setColor(mBorderBottomColor);
        mBorderPaint.setStrokeWidth(mBorderBottomWidth);
        float y = mHeight - mBorderBottomWidth * .5f;
        float startX = 0;
        float endX = mWidth;
        if (mBorderBottomAdjustPadding == BORDER_ADJUST_PADDING_ALL) {
            startX = getPaddingStart();
            endX = mWidth - getPaddingEnd();
        } else if (mBorderBottomAdjustPadding == BORDER_ADJUST_PADDING_LEFT) {
            startX = getPaddingStart();
        } else if (mBorderBottomAdjustPadding == BORDER_ADJUST_PADDING_RIGHT) {
            endX = mWidth - getPaddingEnd();
        }
        canvas.drawLine(startX, y, endX, y, mBorderPaint);
    }

    private int max(int... sizes) {
        int max = 0;
        for (int size : sizes) {
            max = Math.max(max, size);
        }
        return max;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isContentLoaded()) {
            mContent.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    public boolean isContentLoaded() {
        return mContent != null;
    }


}
