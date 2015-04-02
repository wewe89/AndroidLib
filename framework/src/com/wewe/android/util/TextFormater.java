package com.wewe.android.util;

import android.text.format.*;
import android.util.TimeUtils;

/**
 * Created by Administrator on 2015/1/15.
 */
public class TextFormater {
    public static String getDataSize(int i){
        return android.text.format.DateUtils.getRelativeTimeSpanString(i).toString();
    }
}
