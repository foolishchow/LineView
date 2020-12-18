package me.foolishchow.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/12/2020 4:10 PM
 */
public class LinearLineView extends BaseLineView {
    public LinearLineView(Context context) {
        super(context);
    }

    public LinearLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LinearLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getLayout() {
        return R.layout.lv_linear_line_view;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        if (isContentLoaded()) {
            return create(getContext(), attrs);
        }
        return super.generateLayoutParams(attrs);
    }


    public LinearLayout.LayoutParams create(Context c, AttributeSet attrs) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(c, attrs);
        @SuppressLint("CustomViewStyleable")
        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.LinearLineView_Layout);
        lp.weight = a.getFloat(R.styleable.LinearLineView_Layout_android_layout_weight, 0);
        lp.gravity = a.getInt(R.styleable.LinearLineView_Layout_android_layout_gravity, -1);
        a.recycle();
        return lp;
    }
}
