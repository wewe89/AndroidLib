package com.wewe.android.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import  static com.wewe.android.util.LogUtils.*;
/**
 * Created by Administrator on 2015/1/15.
 */
public class DateUtils {
    private static final String TAG=makeLogTag(DateUtils.class);
    public static String toTime(int i){
        return android.text.format.DateUtils.formatElapsedTime(i);
    }
    private static GregorianCalendar mCurrentTime;
    static {
        Calendar calendar = Calendar.getInstance();
        mCurrentTime = new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(mCurrentTime.getTime().toLocaleString());
    }

    public static String getTimestampString(Date date) {
        if (isSameDay(date)) {
            return genDayString(date);
        } else if (isYesterday(date)) {
            return "昨天 " + genDayString(date);
        } else if (isSameWeek(date)) {
            return getWeekDay(date) + genDayString(date);
        } else if (isSameYear(date)) {
            return genMonthStr(date)+genDayString(date);
        } else
            return genYearStr(date)+genMonthStr(date)+genDayString(date);
    }

    public static String genYearStr(Date date) {
        StringBuilder builder = new StringBuilder();
        builder.append(date.getYear()+1900).append("年");
        return builder.toString();
    }
    public static String genMonthStr(Date date) {
        StringBuilder builder = new StringBuilder();
        builder.append(date.getMonth() + 1).append("月").append(date.getDate())
                .append("日 ");
        return builder.toString();
    }
    public static boolean isSameYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return mCurrentTime.get(Calendar.YEAR) == calendar.get(Calendar.YEAR);
    }

    public static boolean isSameWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.get(Calendar.DAY_OF_MONTH) - 1);
        return mCurrentTime.get(Calendar.WEEK_OF_YEAR) == calendar
                .get(Calendar.WEEK_OF_YEAR);
    }

    private static String getWeekDay(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int day = calendar.get(GregorianCalendar.DAY_OF_WEEK);
        String str = null;
        switch (day) {
            case Calendar.SUNDAY:
                str = "周日";
                break;
            case Calendar.MONDAY:
                str = "周一";
                break;
            case Calendar.TUESDAY:
                str = "周二";
                break;
            case Calendar.WEDNESDAY:
                str = "周三";
                break;
            case Calendar.THURSDAY:
                str = "周四";
                break;
            case Calendar.FRIDAY:
                str = "周五";
                break;
            case Calendar.SATURDAY:
                str = "周六";
                break;
            default:
                break;
        }
        str += " ";
        return str;
    }

    public static boolean isCloseEnough(long lastTime,long preTime) {
//        LOGI(TAG,new Date(lastTime).toLocaleString()+"--"+new Date(preTime).toLocaleString());
        return lastTime-preTime<60000;
    }

    private static String genDayString(Date date) {
        StringBuilder builder = new StringBuilder();
        int hours = date.getHours();
        if (hours < 12) {
            builder.append("早上");
        } else if (hours < 13) {
            builder.append("中午");
        } else if (hours < 18)
            builder.append("下午");
        else
            builder.append("晚上");
        builder.append(hours < 10 ? "0" + hours : hours).append(":")
                .append(date.getMinutes());
        return builder.toString();
    }

    private static boolean isSameDay(Date date) {
        Date currentDate = mCurrentTime.getTime();
        return currentDate.getDate() == date.getDate()
                && currentDate.getMonth() == date.getMonth()
                && currentDate.getYear() == date.getYear();
    }

    private static boolean isYesterday(Date date) {
        Date currentDate = mCurrentTime.getTime();
        return currentDate.getDate() - 1 == date.getDate()
                && currentDate.getMonth() == date.getMonth()
                && currentDate.getYear() == date.getYear();
    }
    public static String toTimeBySecond(int length) {
        return length+"秒";
    }

}
