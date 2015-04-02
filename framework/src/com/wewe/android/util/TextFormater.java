package com.wewe.android.util;

/**
 * Created by Administrator on 2015/1/15.
 */
public class TextFormater {
    public static String getDataSize(int i){
        return android.text.format.DateUtils.getRelativeTimeSpanString(i).toString();
    }
}
