package com.wewe.android;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wewe.android.string.StringUtil;
import com.wewe.android.util.LocalDisplay;
import com.wewe.android.util.NetworkStatusManager;
import com.wewe.android.util.PathUtil;

import java.util.UUID;

public class BaseApplication extends Application {
   protected static Context _context;
    static Resources _resource;
    private static String lastToast = "";
    private static long lastToastTime;

    private static boolean sIsAtLeastGB;

    //判断当前设备是否是模拟器。如果返回TRUE，则当前是模拟器，不是返回FALSE
    public boolean isEmulator(Context context){
        try{
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (imei != null && imei.equals("000000000000000")){
                return true;
            }
            return  (Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk"));
        }catch (Exception ioe) {

        }
        return false;
    }
    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }
    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }
    /**
     * 检测当前系统声音是否为正常模式
     *
     * @return
     */
    public boolean isAudioNormal() {
        AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();
        init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NetworkStatusManager.getInstance().stopListening();
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        return getNetworkType() != 0;
    }

    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public int getNetworkType() {
        int netType = 0;
        NetworkInfo networkInfo = NetworkStatusManager.getInstance().getNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!StringUtil.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    protected void init() {
        NetworkStatusManager.init(this);
        LocalDisplay.init();

    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    public static Resources resources() {
        return _resource;
    }

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0, GRAVITY);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, GRAVITY);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0,
                GRAVITY);
    }

    private static final int GRAVITY = Gravity.CENTER;

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, GRAVITY, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, GRAVITY);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon,
                                 int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {
                View view = LayoutInflater.from(context()).inflate(
                        R.layout.view_toast, null);
                TextView tv = ((TextView) view.findViewById(R.id.toast_tv));
                tv.setText(message);
                if (icon != 0) {
                    tv.setCompoundDrawables(context().getResources().getDrawable(icon), null, null, null);
                }
                tv.setHeight(LocalDisplay.dp2px(40));
                tv.setWidth(LocalDisplay.SCREEN_WIDTH_PIXELS - 60);
                Toast toast = new Toast(context());
                toast.setView(view);
                toast.setGravity(gravity, 0, 0);
                toast.setDuration(duration);
                toast.show();

                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }

}
