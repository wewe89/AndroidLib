package com.wewe.android.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import static com.wewe.android.util.LogUtils.LOGD;
import static com.wewe.android.util.LogUtils.LOGI;
import static com.wewe.android.util.LogUtils.LOGV;
import static com.wewe.android.util.LogUtils.makeLogTag;

/**
 * Created by Administrator on 2015/1/15.
 */

public class PathUtil {
    private static final String TAG = makeLogTag(PathUtil.class);
    private String pathPrefix;
    public final String historyPathName = "/chat/";
    public final String imagePathName = "/image/";
    public final String voicePathName = "/voice/";
    public final String filePathName = "/file/";
    public final String tempPathName = "/temp/";
    public final String videoPathName = "/video/";
    public final String netdiskDownloadPathName = "/netdisk/";
    private File storageDir;
    private static PathUtil instance;
    private File voicePath;
    private File tempPath;
    private File imagePath;
    private File historyPath;
    private File videoPath;
    private File filePath;

    private PathUtil() {
    }

    public static PathUtil getInstance() {
        if (instance == null) instance = new PathUtil();
        return instance;
    }

    public void initDirs(String pathPrefix, Context context) {
        this.pathPrefix = pathPrefix;
        storageDir = new File(getStorageDir(context), pathPrefix);
        storageDir.mkdirs();
    }

    public File getImagePath() {
        if (imagePath == null)
            imagePath = new File(storageDir, imagePathName);
        imagePath.mkdirs();
        return imagePath;
    }

    public File getVoicePath() {
        if (voicePath == null)
            voicePath = new File(storageDir, voicePathName);
        voicePath.mkdirs();
        return voicePath;
    }

    public File getFilePath() {
        if (filePath == null)
            filePath = new File(storageDir, filePathName);
        filePath.mkdirs();
        return filePath;
    }

    public File getVideoPath() {
        if (videoPath == null)
            videoPath = new File(storageDir, videoPathName);
        videoPath.mkdirs();
        return videoPath;
    }

    public File getHistoryPath() {
        if (historyPath == null)
            historyPath = new File(storageDir, historyPathName);
        historyPath.mkdirs();
        return historyPath;
    }

    private static File getStorageDir(Context context) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        } else sdDir = Environment.getDataDirectory();
        LOGD(TAG, sdDir.toString() + "");
        return sdDir;
    }

    private static File generateImagePath(String s, String s1, android.content.Context context) {
        return null;
    }

    private static File generateVoicePath(String s, String s1, android.content.Context context) {
        return null;
    }

    private static File generateFiePath(String s, String s1, android.content.Context context) {
        return null;
    }

    private static File generateVideoPath(String s, String s1, android.content.Context context) {
        return null;
    }

    private static File generateHistoryPath(String s, String s1, android.content.Context context) {
        return null;
    }

    private static File generateMessagePath(String s, String s1, android.content.Context context) {
        return null;
    }

    public File getTempPath(File file) {
        if (tempPath == null)
            tempPath = new File(storageDir, tempPathName);
        File path=tempPath;
        if(file!=null){
            path=new File(path,file.toString());
        }
        path.mkdirs();
        return path;
    }
}
