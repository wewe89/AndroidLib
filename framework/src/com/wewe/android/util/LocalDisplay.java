package com.wewe.android.util;

import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.View;

public class LocalDisplay {

    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;

    public static void init() {
        DisplayMetrics dm= Resources.getSystem().getDisplayMetrics();
        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = dm.heightPixels;
        SCREEN_DENSITY = dm.density;
        SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PIXELS / dm.density);
        SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PIXELS / dm.density);
    }

    public static int dp2px(float dp) {
        final float scale = SCREEN_DENSITY;
        return (int) (dp * scale + 0.5f);
    }

    public static int designedDP2px(float desingDP) {
        if (SCREEN_WIDTH_DP != 320) {
            desingDP = desingDP * SCREEN_WIDTH_DP / 320f;
        }
        return dp2px(desingDP);
    }
}
