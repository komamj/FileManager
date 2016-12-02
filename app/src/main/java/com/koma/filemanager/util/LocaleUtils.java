package com.koma.filemanager.util;

import com.koma.filemanager.FileManagerApplication;
import com.koma.filemanager.R;

import java.util.Locale;

/**
 * Created by koma on 11/30/16.
 */

public class LocaleUtils {
    private static final String TAG = "LocaleUtils";
    private static final String LANG_FARSI_FA = "fa";

    private static String getLocalLanguage() {
        Locale local = FileManagerApplication.getContext().getResources().getConfiguration().locale;
        return local.getLanguage();
    }

    public static String formatVolumeInfo(long totalSpace, long availSpace) {
        if (getLocalLanguage().endsWith(LANG_FARSI_FA)) {
            return String.format(FileManagerApplication.getContext().getString(R.string.volume_info_size),
                    FileUtils.formatFileSize(totalSpace),
                    FileUtils.formatFileSize(availSpace));
        } else {
            return String.format(FileManagerApplication.getContext().getString(R.string.volume_info_size),
                    FileUtils.formatFileSize(availSpace),
                    FileUtils.formatFileSize(totalSpace));
        }
    }

    public static String formatItemCount(int count) {
        if (count <= 1) {
            LogUtils.i(TAG, "formatItemCount count " + count + String.format(FileManagerApplication.getContext()
                    .getString(R.string.item_count), count));
            return String.format(FileManagerApplication.getContext()
                    .getString(R.string.item_count), count);
        } else {
            return String.format(FileManagerApplication.getContext()
                    .getString(R.string.item_counts), count);
        }

    }
}
