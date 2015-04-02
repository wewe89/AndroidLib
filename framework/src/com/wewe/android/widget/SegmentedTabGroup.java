package com.wewe.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wewe.android.R;

import java.util.ArrayList;
import java.util.List;


public class SegmentedTabGroup extends LinearLayout implements View.OnClickListener {
    public SegmentedTabGroup(Context context) {
        super(context);
    }

    public static interface OnTabClickListener {
        public void onTabClick(int index);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize
    };
    private Context mContext;
    private int mTextSize = 12;

    public void setOnTabClickListener(OnTabClickListener listener) {
        mListener = listener;
    }

    private OnTabClickListener mListener;

    public SegmentedTabGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = getContext();
        setOrientation(LinearLayout.HORIZONTAL);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, dm);

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        mTextSize = a.getDimensionPixelSize(0, mTextSize);
        a.recycle();

        a = context.obtainStyledAttributes(attrs, R.styleable.SegmentedTabGroup);
        dividerColor = a.getColor(R.styleable.SegmentedTabGroup_stgDividerColor, dividerColor);
        dividerWidth = a.getDimensionPixelSize(R.styleable.SegmentedTabGroup_stgDividerWidth, dividerWidth);
        selectTabTextColor = a.getColor(R.styleable.SegmentedTabGroup_stgSelectedTabTextColor, selectTabTextColor);
        radius = a.getDimensionPixelSize(R.styleable.SegmentedTabGroup_stgRadius, (int) radius);
        mTextSize = a.getDimensionPixelSize(R.styleable.SegmentedTabGroup_stgTextSize, mTextSize);
        a.recycle();

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke(dividerWidth, dividerColor);
        drawable.setColor(selectTabTextColor);
        setBackgroundDrawable(drawable);

        mBgFirstTabSelect = new GradientDrawable();
        float[] r = {radius, radius, 0f, 0f, 0f, 0f, radius, radius};
        mBgFirstTabSelect.setCornerRadii(r);
        mBgFirstTabSelect.setStroke(dividerWidth, dividerColor);
        mBgFirstTabSelect.setColor(dividerColor);

        float[] d = {0f, 0f, radius, radius, radius, radius, 0f, 0f};
        mBgLastTabSelect = new GradientDrawable();
        mBgLastTabSelect.setCornerRadii(d);
        mBgLastTabSelect.setStroke(dividerWidth, dividerColor);
        mBgLastTabSelect.setColor(dividerColor);
    }

    private GradientDrawable mBgFirstTabSelect, mBgLastTabSelect;
    private float radius = 5;
    private int dividerWidth = 1;
    private int dividerColor = Color.parseColor("#0000ff");
    private int selectTabTextColor = Color.parseColor("#ffffff");

    public void setTextSize(int textSizePx) {
        this.mTextSize = textSizePx;
        updateTabStyles();
    }

    public void addTabs(final String... tabs) {
        mTabs = null;
        mTabs = new ArrayList<TextView>();
        mTabs.clear();
        removeAllViews();
        for (int index = 0; index < tabs.length; index++) {
            addTab(tabs[index], index);
        }
        updateTabStyles();
    }

    private List<TextView> mTabs;

    private void addTab(final String text, int index) {
        if (index != 0) {
            addView(genDivider());
        }
        TextView tv = new TextView(mContext);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        tv.setGravity(Gravity.CENTER);
        tv.setSingleLine();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
        tv.setLayoutParams(params);
        tv.setTag(index);

        tv.setOnClickListener(this);
        mTabs.add(tv);
        addView(tv);
    }

    private void updateTabStyles() {
        for (int index = 0; index < mTabs.size(); index++) {
            TextView tv = mTabs.get(index);
            if (mSelectedPosition == index) {
                if (index == 0) {
                    tv.setBackgroundDrawable(mBgFirstTabSelect);
                } else if (index == (mTabs.size() - 1)) {
                    tv.setBackgroundDrawable(mBgLastTabSelect);
                } else {
                    tv.setBackgroundColor(dividerColor);
                }
                tv.setTextColor(selectTabTextColor);
            } else {
                tv.setTextColor(dividerColor);
                tv.setBackgroundDrawable(null);
            }
        }
    }

    private int mSelectedPosition = 0;

    private void setSelectedPosition(int position) {
        mSelectedPosition = position;
    }

    public View genDivider() {
        View divider = new View(mContext);
        LayoutParams params = new LayoutParams(dividerWidth, LayoutParams.MATCH_PARENT);
        divider.setLayoutParams(params);
        divider.setBackgroundColor(dividerColor);
        return divider;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        if (position == mSelectedPosition)
            return;
        mSelectedPosition = position;
        updateTabStyles();

        if (mListener != null) {
            mListener.onTabClick(position);
        }
    }
    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mSelectedPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = mSelectedPosition;
        return savedState;
    }
    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
