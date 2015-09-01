package com.wewe.android.string;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String bitmaptoString(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    public static Bitmap stringtoBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 解码
     *
     * @param text
     * @return
     */
    public static String decode(String text) {
        try {
            return new String(Base64.decode(text, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //中国电话号码
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 编码
     * @param text
     * @return
     */
    public static String encode(String text) {
        try {
            return new String(Base64.encode(text.getBytes(), Base64.DEFAULT));
        } catch (Exception e) {
            return "";
        }
    }

    public static String iso2gb(String s) {
        if (s == null)
            return null;
        try {
            return new String(s.getBytes("ISO-8859-1"), "GBK").trim();
        } catch (Exception e) {
            return s;
        }
    }

    public static String gb2iso(String s) {
        if (s == null)
            return null;
        try {
            return new String(s.getBytes("GBK"), "ISO-8859-1").trim();
        } catch (Exception e) {
            return s;
        }
    }

    public static String iso2utf8(String s) {
        if (s == null)
            return null;
        try {
            return new String(s.getBytes("ISO-8859-1"), "UTF-8").trim();
        } catch (Exception e) {
            return s;
        }
    }

    public static String utf82iso(String s) {
        if (s == null)
            return null;
        try {
            return new String(s.getBytes("UTF-8"), "ISO-8859-1").trim();
        } catch (Exception e) {
            return s;
        }
    }

    public static String notNull(String s) {
        if (s == null)
            return "";
        else
            return s.trim();
    }

    /**
     * 判断一个字符串是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static String removeSpace(String s) {
        return s.replace(" ","");
    }

    /**
     * 判断一组字符串是否为空
     * @param s
     * @return
     */
    public static boolean isEmpty(String... s) {
        for (String str : s) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static String[] split(String sourse, String spliter) {
        sourse = notNull(sourse);
        int index = 0, nextIndex = 0, spliterLen = spliter.length();
        ArrayList<String> list = new ArrayList<String>();
        while ((nextIndex = sourse.indexOf(spliter, index)) != -1) {
            list.add(sourse.substring(index, nextIndex));
            index = nextIndex + spliterLen;
        }
        if (list.size() > 0)
            list.add(sourse.substring(
                    sourse.lastIndexOf(spliter) + spliter.length(),
                    sourse.length()));
        String splits[] = new String[list.size()];
        list.toArray(splits);

        return splits;
    }

    /**
     * String join
     *
     * @param s
     * @param delimiter 连接符
     * @return
     */
    public static String join(Collection s, String delimiter) {
        StringBuilder sb = new StringBuilder();
        Iterator iter = s.iterator();
        if (iter.hasNext()) {
            sb.append(iter.next());
            while (iter.hasNext()) {
                sb.append(delimiter);
                sb.append(iter.next());
            }
        }
        return sb.toString();
    }

    /**
     * 使用反射，效率比较差
     *
     * @param array
     * @param delimiter
     * @return
     */
    public static String join(Object array, String delimiter) {
        if (array.getClass().isArray()) {
            StringBuilder sb = new StringBuilder();
            int len = Array.getLength(array);

            if (len > 0) {
                sb.append(Array.get(array, 0));
            }

            for (int i = 1; i < len; i++) {
                sb.append(delimiter);
                sb.append(Array.get(array, i));
            }

            return sb.toString();
        } else {
            throw new IllegalArgumentException(array
                    + " is not a array.");
        }
    }

    public static String join(int[] arr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        int len = arr.length;

        if (len > 0) {
            sb.append(arr[0]);
        }

        for (int i = 1; i < len; i++) {
            sb.append(delimiter);
            sb.append(arr[i]);
        }

        return sb.toString();
    }

    /**
     * String join
     *
     * @param delimiter 连接符
     * @return
     */
    public static String join(String[] arr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        int len = arr.length;

        if (len > 0) {
            sb.append(arr[0]);
        }

        for (int i = 1; i < len; i++) {
            sb.append(delimiter);
            sb.append(arr[i]);
        }

        return sb.toString();
    }

    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(
                            src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     * 将str转成url地址
     *
     * @param str
     * @return
     */
    public static String utf8Url(String str) {
        if (isEmpty(str)) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将str转成gb2312地址
     *
     * @param str
     * @return
     */
    public static String gbUrl(String str) {
        try {
            return URLEncoder.encode(str, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 格式化字符串
     * <p/>
     * <code><pre>
     * format("ab{1}d{2}_{1}" , "c","e") // out put : abcde_c
     * </pre></code>
     * <p/>
     * 使用{ldelim}, {rdelim} 表示"{","}"
     *
     * @param str  使用{1},{2},...{n}占位符的字符串
     * @param args 填充参数数组
     * @return
     */
    public static String format(String str, Object... args) {
        if (isEmpty(str)) {
            return "";
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null)
                str = str.replace("{" + (i + 1) + "}", args[i].toString());
        }
        return str.replaceAll("\\{\\d\\}", "").replaceAll("\\{ldelim\\}", "{")
                .replaceAll("\\{rdelim\\}", "}");
    }

    private static final Pattern FORMAT_STRING = Pattern
            .compile("\\{([^\\s]+?)\\}");

    /**
     * <code><pre>
     * format("{name}:{sex}", Map {
     * 		name:"Tom",
     * 		sex:"boy"}) // out put : Tom:boy
     * </pre></code>
     * <p/>
     * 使用{ldelim}, {rdelim} 表示"{","}"
     *
     * @param str  使用{key}占位符的字符串
     * @param args 填充Map
     * @return
     */
    public static String format(String str, Map<String, ?> args) {
        if (isEmpty(str)) {
            return "";
        }
        str = str.replaceAll("\\{ldelim\\}", "{").replaceAll("\\{rdelim\\}",
                "}");
        Matcher matcher = FORMAT_STRING.matcher(str);
        while (matcher.find()) {
            String k = matcher.group(1);
            Object val = args.get(k);
            if (val != null) {
                String s = val.toString();
                str = str.replaceAll("\\{" + k + "\\}", s);
            } else {
                str = str.replaceAll("\\{" + k + "\\}", "");
            }
        }

        return str;
    }

    /**
     * 赋值初始值
     *
     * @param s
     * @param defaultv
     * @return
     */
    public static String forDefault(String s, String defaultv) {
        return isEmpty(s) ? defaultv : s;
    }

    /**
     * Match regex , not global match ,return the first match.
     *
     * @param s     input string
     * @param regex regex
     * @return No match return null,or index [0] store full input ,
     * index[1]...index[n] store group 1-n;
     * @author <a href="mailto:hb562100@163.com">HeBo</a>
     */
    public static String[] match(CharSequence s, String regex) {
        Pattern p = Pattern.compile(regex);
        return match(s, p);
    }

    /**
     * Match regex , not global match ,return the first match.
     *
     * @param s     input string
     * @param regex regex
     * @return No match return null,or index [0] store full input ,
     * index[1]...index[n] store group 1-n;
     * @author <a href="mailto:hb562100@163.com">HeBo</a>
     */
    public static String[] match(CharSequence s, Pattern regex) {
        Matcher m = regex.matcher(s);

        String[] l = null;

        if (m.find()) {
            l = new String[m.groupCount() + 1];

            for (int i = 0; i < l.length; i++)
                l[i] = m.group(i);
        }

        return l;
    }

    /**
     * bytes to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytes2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            int i = b & 0xFF;
            if (i <= 0xF) {
                sb.append("0");
            }

            sb.append(Integer.toHexString(i));
        }

        return sb.toString();
    }

    public static final List<Character> HEX_CHAR_LIST;

    static {
        HEX_CHAR_LIST = new ArrayList<Character>();
        HEX_CHAR_LIST.add(new Character('0'));
        HEX_CHAR_LIST.add(new Character('1'));
        HEX_CHAR_LIST.add(new Character('2'));
        HEX_CHAR_LIST.add(new Character('3'));
        HEX_CHAR_LIST.add(new Character('4'));
        HEX_CHAR_LIST.add(new Character('5'));
        HEX_CHAR_LIST.add(new Character('6'));
        HEX_CHAR_LIST.add(new Character('7'));
        HEX_CHAR_LIST.add(new Character('8'));
        HEX_CHAR_LIST.add(new Character('9'));
        HEX_CHAR_LIST.add(new Character('a'));
        HEX_CHAR_LIST.add(new Character('b'));
        HEX_CHAR_LIST.add(new Character('c'));
        HEX_CHAR_LIST.add(new Character('d'));
        HEX_CHAR_LIST.add(new Character('e'));
        HEX_CHAR_LIST.add(new Character('f'));
    }

    private static byte hex2Byte(String s) {
        int high = HEX_CHAR_LIST.indexOf(new Character(s.charAt(0))) << 4;
        int low = HEX_CHAR_LIST.indexOf(new Character(s.charAt(1)));

        return (byte) (high + low);
    }

    /**
     * hex string to bytes
     *
     * @return
     */
    public static byte[] hex2Bytes(String input) {
        int len = input.length() / 2;
        byte[] rtn = new byte[len];

        for (int i = 0; i < len; i++) {
            rtn[i] = hex2Byte(input.substring(i * 2, i * 2 + 2));
        }
        return rtn;
    }

    /**
     * Parses the string argument as a signed decimal integer.Default 0;
     *
     * @param o
     * @return
     */
    public static int parseInt(Object o) {
        return parseInt(o, 0);
    }

    /**
     * Parses the string argument as a signed decimal integer.
     *
     * @param o
     * @param defaultV
     * @return
     */
    public static int parseInt(Object o, int defaultV) {
        if (o == null)
            return defaultV;
        try {
            return Integer.parseInt(o.toString());
        } catch (NumberFormatException e) {
            return defaultV;
        }
    }

    /**
     * Parses the string argument as a signed decimal long.Default 0;
     *
     * @param o
     * @return
     */
    public static long parseLong(Object o) {
        return parseLong(o, 0);
    }

    /**
     * Parses the string argument as a signed decimal long.
     *
     * @param o
     * @param defaultV
     * @return
     */
    public static long parseLong(Object o, long defaultV) {
        if (o == null)
            return defaultV;
        try {
            return Long.parseLong(o.toString());
        } catch (NumberFormatException e) {
            return defaultV;
        }
    }

    /**
     * Parses the string argument as a boolean.Default false;
     *
     * @param o
     * @return
     */
    public static boolean parseBoolean(Object o) {
        return parseBoolean(o, false);
    }

    /**
     * Parses the string argument as a boolean
     *
     * @param o
     * @param defaultV
     * @return
     */
    public static boolean parseBoolean(Object o, boolean defaultV) {
        if (o == null)
            return defaultV;
        try {
            return Boolean.parseBoolean(o.toString());
        } catch (NumberFormatException e) {
            return defaultV;
        }
    }

    /**
     * Parses the string argument as a signed decimal short.Default 0;
     *
     * @param o
     * @return
     */
    public static short parseShort(Object o) {
        return parseShort(o, (short) 0);
    }

    /**
     * Parses the string argument as a signed decimal short.
     *
     * @param o
     * @param defaultV
     * @return
     */
    public static short parseShort(Object o, short defaultV) {
        if (o == null)
            return defaultV;
        try {
            return Short.parseShort(o.toString());
        } catch (NumberFormatException e) {
            return defaultV;
        }
    }

    /**
     * Parses the string argument as a signed float.Default 0f;
     *
     * @param o
     * @return
     */
    public static float parseFloat(Object o) {
        return parseFloat(o, 0f);
    }

    /**
     * Parses the string argument as a signed float.
     *
     * @param o
     * @param defaultV
     * @return
     */
    public static float parseFloat(Object o, float defaultV) {
        if (o == null)
            return defaultV;
        try {
            return Float.parseFloat(o.toString());
        } catch (NumberFormatException e) {
            return defaultV;
        }
    }

    /**
     * Parses the string argument as a signed double.Default 0;
     *
     * @param o
     * @return
     */
    public static double parseDouble(Object o) {
        return parseDouble(o, 0);
    }

    /**
     * Parses the string argument as a signed double.
     *
     * @param o
     * @param defaultV
     * @return
     */
    public static double parseDouble(Object o, double defaultV) {
        if (o == null)
            return defaultV;
        try {
            return Double.parseDouble(o.toString());
        } catch (NumberFormatException e) {
            return defaultV;
        }
    }

    /**
     * Decorate a string, return <i>decorator + s + decorator</i>
     *
     * @param s
     * @param decorator
     * @return
     */
    public static String wrap(Object s, String decorator) {
        return decorator + s + decorator;
    }

    /**
     * Return a integer
     *
     * @param number
     * @param defaultV
     * @return
     */
    public static int intValue(Number number, int defaultV) {
        if (number == null) {
            return defaultV;
        }

        return number.intValue();
    }

    /**
     * Return a integer , default 0
     * @param number
     * @return
     */
    public static int intValue(Number number) {
        return intValue(number, 0);
    }

    /**
     * Return a long
     *
     * @param number
     * @param defaultV
     * @return
     */
    public static long longValue(Number number, long defaultV) {
        if (number == null) {
            return defaultV;
        }

        return number.longValue();
    }

    /**
     * Return a long , default 0
     *
     * @param number
     * @return
     */
    public static long longValue(Number number) {
        return longValue(number, 0);
    }

    /**
     * Specailed for length ,return start...end. <code><pre>
     *
     * @return
     */
    public static String pad(int _length, String _str) {
        if (_str == null)
            return "";
        else {
            if (_str.length() > _length) {
                return _str.substring(0, _length * 3 / 4) + "..."
                        + _str.substring(_str.length() - _length / 4);
            } else {
                return _str;
            }
        }
    }

    /**
     * Specailed for length ,return start...end. <code><pre>
     * www.duxiu.com/readPage.jsp?dxid=123456789
     * if length = 20 get 'www.duxiu.com/r...56789'
     * </pre></code>
     * @return
     */
    public static String pad(int _length, CharSequence _str) {
        if (_str == null)
            return "";
        else {
            if (_str.length() > _length) {
                return _str.subSequence(0, _length * 3 / 4)
                        + "..."
                        + _str.subSequence(_str.length() - _length / 4,
                        _str.length());
            } else {
                return _str.toString();
            }
        }
    }

    // /**
    // * Escape html tag to entity reference.
    // * @param s
    // * @return string
    // */
    // public static String escapeHtml(String s){
    // return HtmlUtils.htmlEscape(s).replaceAll("\r\n",
    // "<BR/>").replaceAll("\n", "<BR/>").replace(" ","&nbsp;");
    // }
    //
    // /**
    // * 1 Escape html tag to entity reference.
    // * 2 Wrap url into anchor.
    // * @param s
    // * @return string
    // */
    // public static String escapeContent(String s){
    // return
    // escapeHtml(s).replaceAll("((?:http|https|ftp)://(?:[\\w-]+\\.)+[\\w-]+(?:/[\\w- ./?%&=]*)?)",
    // "<A href=\"$1\" target=_blank>$1</A>");
    // }
}
