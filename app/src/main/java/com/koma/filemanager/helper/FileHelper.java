package com.koma.filemanager.helper;


import com.koma.filemanager.base.BaseFile;
import com.koma.filemanager.util.LogUtils;

import java.io.File;
import java.util.Date;

/**
 * Created by koma on 12/2/16.
 */

public class FileHelper {
    private static final String TAG = "FileHelper";

    public static BaseFile createBaseFile(File file) {
        try {
            if (!file.exists()) {
                return null;
            }
            Date lastModified = new Date(file.lastModified());
            if (file.isDirectory()) {
                return new BaseFile(file.getName(), file.getParent(), 0, lastModified);
            }
            return new BaseFile(file.getName(), file.getParent(), file.length(), lastModified);
        } catch (Exception e) {
            LogUtils.e(TAG, "create BaseFile error : " + e.toString());
        }
        return null;
    }
}
