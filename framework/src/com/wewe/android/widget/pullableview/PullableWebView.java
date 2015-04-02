package com.wewe.android.widget.pullableview;

import android.view.View;
import android.webkit.WebView;

public class PullableWebView implements Pullable {
    private WebView mContent;

    public PullableWebView(View view) {
        mContent = (WebView) view;
    }

    @Override
    public boolean canPullDown() {
        return mContent.getScrollY() == 0;
    }

    @Override
    public boolean canPullUp() {
        return mContent.getScrollY() >= mContent.getContentHeight() * mContent.getScale()
                - mContent.getMeasuredHeight();
    }
}
