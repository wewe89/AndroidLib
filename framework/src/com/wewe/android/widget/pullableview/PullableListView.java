package com.wewe.android.widget.pullableview;

import android.view.View;
import android.widget.ListView;

public class PullableListView implements Pullable {
    private ListView mListview;

    public PullableListView(View listView) {
        mListview = (ListView) listView;
    }

    @Override
    public boolean canPullDown() {
        if (mListview.getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (mListview.getFirstVisiblePosition() == 0
                && mListview.getChildAt(0).getTop() >= 0) {
            // 滑到ListView的顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (mListview.getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (mListview.getLastVisiblePosition() == (mListview.getCount() - 1)) {
            // 滑到底部了
            if (mListview.getChildAt(mListview.getLastVisiblePosition() - mListview.getFirstVisiblePosition()) != null
                    && mListview.getChildAt(
                    mListview.getLastVisiblePosition()
                            - mListview.getFirstVisiblePosition()).getBottom() <= mListview.getMeasuredHeight())
                return true;
        }
        return false;
    }
}
