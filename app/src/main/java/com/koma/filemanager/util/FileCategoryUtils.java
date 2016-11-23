package com.koma.filemanager.util;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by koma on 11/23/16.
 */

public final class FileCategoryUtils {
    public static Uri getFileUri() {
        return MediaStore.Files.getContentUri(Constants.EXTERNAL);
    }

    public static Uri getAudioUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    public static Uri getVideoUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

    public static Uri getImageUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    public static String[] getMediaProjection() {
        return Constants.MEDIA_PROJECTION;
    }

    public static String getSelection() {
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(MediaStore.Files.FileColumns.TITLE);
        stringBuilder.append(" NOT LIKE '.%'");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
