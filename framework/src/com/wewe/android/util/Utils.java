package com.wewe.android.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.StrictMode;
import android.util.Log;

import com.wewe.android.string.StringUtil;

import java.util.Comparator;
import java.util.List;

public class Utils {
    public static boolean isScreenLocked(Context context) {
        KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return !mKeyguardManager.inKeyguardRestrictedInputMode();
    }

    /**
     * 程序是否在前台运行
     *
     * @param context
     * @return
     */
    public static boolean isAppRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!StringUtil.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }
        return false;
    }

    private Utils() {
    }

    ;

    /**
     * @param cl 调用的activity
     */
    @SuppressLint("NewApi")
    public static void enableStrictMode(Class cl) {
        if (Utils.hasGingerbread()) {
            StrictMode.ThreadPolicy.Builder threadPolicyBuilder =
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog();
            StrictMode.VmPolicy.Builder vmPolicyBuilder =
                    new StrictMode.VmPolicy.Builder()
                            .detectAll()
                            .penaltyLog();

            if (Utils.hasHoneycomb()) {
                threadPolicyBuilder.penaltyFlashScreen();
                vmPolicyBuilder
                        .setClassInstanceLimit(cl, 1);
            }
            StrictMode.setThreadPolicy(threadPolicyBuilder.build());
            StrictMode.setVmPolicy(vmPolicyBuilder.build());
        }


    }

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;

    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static List<Size> getResolutionList(Camera camera) {
        Parameters parameters = camera.getParameters();
        List<Size> previewSizes = parameters.getSupportedPreviewSizes();
        return previewSizes;
    }

    public static class ResolutionComparator implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            if (lhs.height != rhs.height)
                return lhs.height - rhs.height;
            else
                return lhs.width - rhs.width;
        }

    }


}
