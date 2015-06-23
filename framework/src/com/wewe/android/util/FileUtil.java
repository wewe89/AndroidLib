package com.wewe.android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.wewe.android.string.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public static boolean copyAssets2SDCard(Context context, String sourse, String distFile) {
        InputStream inputStream;
        try {
            inputStream = context.getResources().getAssets().open(sourse);
            File file = new File(distFile);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(distFile+"/"+sourse);
            byte[] buffer = new byte[512];
            int count = 0;
            while ((count = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            Log.i("fileutis","success");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
