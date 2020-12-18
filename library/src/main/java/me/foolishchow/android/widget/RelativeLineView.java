package me.foolishchow.android.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import static android.widget.RelativeLayout.TRUE;

/**
 * Description:
 * Author: foolishchow
 * Date: 11/12/2020 4:10 PM
 */
public class RelativeLineView extends BaseLineView {
    public RelativeLineView(Context context) {
        super(context);
    }

    public RelativeLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RelativeLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getLayout() {
        return R.layout.lv_relative_line_view;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        if (isContentLoaded()) {
            return create(getContext(), attrs);
        }
        return super.generateLayoutParams(attrs);
    }


    public RelativeLayout.LayoutParams create(Context c, AttributeSet attrs) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(c, attrs);
        @SuppressLint("CustomViewStyleable")
        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.RelativeLineView_Layout);
        final int[] rules = lp.getRules();
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignWithParentIfMissing) {
                lp.alignWithParent = a.getBoolean(attr, false);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_toLeftOf) {
                rules[RelativeLayout.LEFT_OF] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.LEFT_OF, rules[RelativeLayout.LEFT_OF]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_toRightOf) {
                rules[RelativeLayout.RIGHT_OF] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.RIGHT_OF, rules[RelativeLayout.RIGHT_OF]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_above) {
                rules[RelativeLayout.ABOVE] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.ABOVE, rules[RelativeLayout.ABOVE]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_below) {
                rules[RelativeLayout.BELOW] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.BELOW, rules[RelativeLayout.BELOW]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignBaseline) {
                rules[RelativeLayout.ALIGN_BASELINE] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.ALIGN_BASELINE, rules[RelativeLayout.ALIGN_BASELINE]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignLeft) {
                rules[RelativeLayout.ALIGN_LEFT] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.ALIGN_LEFT, rules[RelativeLayout.ALIGN_LEFT]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignTop) {
                rules[RelativeLayout.ALIGN_TOP] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.ALIGN_TOP, rules[RelativeLayout.ALIGN_TOP]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignRight) {
                rules[RelativeLayout.ALIGN_RIGHT] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.ALIGN_RIGHT, rules[RelativeLayout.ALIGN_RIGHT]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignBottom) {
                rules[RelativeLayout.ALIGN_BOTTOM] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.ALIGN_BOTTOM, rules[RelativeLayout.ALIGN_BOTTOM]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignParentLeft) {
                rules[RelativeLayout.ALIGN_PARENT_LEFT] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, rules[RelativeLayout.ALIGN_PARENT_LEFT]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignParentTop) {
                rules[RelativeLayout.ALIGN_PARENT_TOP] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP, rules[RelativeLayout.ALIGN_PARENT_TOP]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignParentRight) {
                rules[RelativeLayout.ALIGN_PARENT_RIGHT] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, rules[RelativeLayout.ALIGN_PARENT_RIGHT]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignParentBottom) {
                rules[RelativeLayout.ALIGN_PARENT_BOTTOM] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, rules[RelativeLayout.ALIGN_PARENT_BOTTOM]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_centerInParent) {
                rules[RelativeLayout.CENTER_IN_PARENT] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, rules[RelativeLayout.CENTER_IN_PARENT]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_centerHorizontal) {
                rules[RelativeLayout.CENTER_HORIZONTAL] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, rules[RelativeLayout.CENTER_HORIZONTAL]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_centerVertical) {
                rules[RelativeLayout.CENTER_VERTICAL] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.CENTER_VERTICAL, rules[RelativeLayout.CENTER_VERTICAL]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_toStartOf) {
                rules[RelativeLayout.START_OF] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.START_OF, rules[RelativeLayout.START_OF]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_toEndOf) {
                rules[RelativeLayout.END_OF] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.END_OF, rules[RelativeLayout.END_OF]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignStart) {
                rules[RelativeLayout.ALIGN_START] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.ALIGN_START, rules[RelativeLayout.ALIGN_START]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignEnd) {
                rules[RelativeLayout.ALIGN_END] = a.getResourceId(attr, 0);
                lp.addRule(RelativeLayout.ALIGN_END, rules[RelativeLayout.ALIGN_END]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignParentStart) {
                rules[RelativeLayout.ALIGN_PARENT_START] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_START, rules[RelativeLayout.ALIGN_PARENT_START]);
            } else if (attr == R.styleable.RelativeLineView_Layout_android_layout_alignParentEnd) {
                rules[RelativeLayout.ALIGN_PARENT_END] = a.getBoolean(attr, false) ? TRUE : 0;
                lp.addRule(RelativeLayout.ALIGN_PARENT_END, rules[RelativeLayout.ALIGN_PARENT_END]);
            }
        }
        a.recycle();

        return lp;
    }
}
