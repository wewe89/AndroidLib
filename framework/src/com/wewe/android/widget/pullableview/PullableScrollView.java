package com.wewe.android.widget.pullableview;

import android.view.View;
import android.widget.ScrollView;

public class PullableScrollView implements Pullable {
    private ScrollView mContent;

    public PullableScrollView(View view) {
        mContent = (ScrollView) view;
    }

    @Override
    public boolean canPullDown() {
        return mContent.getScrollY() == 0;
    }

    @Override
    public boolean canPullUp() {
        return mContent.getScrollY() >= (mContent.getChildAt(0).getHeight() - mContent.getMeasuredHeight());
    }

}
