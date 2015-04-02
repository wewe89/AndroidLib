package com.wewe.android.util;

import android.graphics.Bitmap;

import com.wewe.android.string.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.wewe.android.util.LogUtils.*;

/**
 * Created by Administrator on 2015/1/29.
 */
public class FileUtil {
    private static final String TAG = makeLogTag(FileUtil.class);

    /**
     * 保存方法
     */
    public static void saveBitmap(String fileName, Bitmap source) {
        if (source == null) {
            return;
        }
        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            source.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            LOGD(TAG, "已经保存" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveBitmap(String fileName, String source) {
        Bitmap bm = StringUtil.stringtoBitmap(source);
        saveBitmap(fileName, bm);
    }
}
