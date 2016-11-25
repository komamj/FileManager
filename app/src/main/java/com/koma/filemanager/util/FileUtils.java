package com.koma.filemanager.util;

import android.text.format.Formatter;

import com.koma.filemanager.FilemanagerApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by koma on 11/24/16.
 */

public class FileUtils {
    public static String formatFileSize(long size) {
        return Formatter.formatFileSize(FilemanagerApplication.getContext(), size);
    }

    public static String formatFileModifiedTime(Date filetime) {
        SimpleDateFormat df = new SimpleDateFormat(android.text.format.DateFormat.getBestDateTimePattern(Locale.getDefault(),
                Constants.FILE_MODIFIED_TIME_FORMAT));
        return df.format(filetime);
    }
}
