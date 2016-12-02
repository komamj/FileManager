package com.koma.filemanager.helper;

import com.koma.filemanager.util.LogUtils;

import java.io.File;

/**
 * Created by koma on 12/2/16.
 */

public class FileCountHelper {
    private static final String TAG = "FileCountHelper";

    public static int getFileCount(String fullPath) {
        try {
            File file = new File(fullPath);
            int filesCount = FileHelper.getSubFilesCount(file);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "getFileCount error : " + e.toString());
        }
        return 0;
    }
}
