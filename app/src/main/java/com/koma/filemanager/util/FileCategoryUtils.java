package com.koma.filemanager.util;

import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;

import com.koma.filemanager.FilemanagerApplication;
import com.koma.filemanager.R;

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

    public static String[] getFileProjection() {
        return Constants.FILE_PROJECTION;
    }

    public static String[] getAudioProjection() {
        return Constants.AUDIO_PROJECTION;
    }

    public static String getAudioFilesSelection() {
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(MediaStore.Files.FileColumns.DATA);
        stringBuilder.append(" NOT LIKE '.%'");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String buildDocSelection() {
        StringBuilder selection = new StringBuilder();
        String[] mimeTypes;
        String[] suffixOffice;
        Resources resources = FilemanagerApplication.getContext().getResources();
        mimeTypes = resources.getStringArray(R.array.doc_mime_types);
        suffixOffice = resources.getStringArray(R.array.doc_office_suffix);
        for (int i = 0; i < mimeTypes.length; i++) {
            selection.append("(" + MediaStore.Files.FileColumns.MIME_TYPE + "=='" + mimeTypes[i] + "') OR ");
        }
        for (int i = 0; i < suffixOffice.length; i++) {
            selection.append("(" + MediaStore.Files.FileColumns.DATA + " LIKE '%." + suffixOffice[i] + "') OR ");
        }
        return selection.substring(0, selection.lastIndexOf(")") + 1);
    }

    public static String buildZipSelection() {
        StringBuilder selection = new StringBuilder();
        Resources resources = FilemanagerApplication.getContext().getResources();
        String[] Suffix = resources.getStringArray(R.array.zip_suffix);
        for (int i = 0; i < Suffix.length; i++) {
            selection.append("(" + MediaStore.Files.FileColumns.DATA + " LIKE '%." + Suffix[i] + "') OR ");
        }
        return selection.substring(0, selection.lastIndexOf(")") + 1);
    }

    public static String buildApkSelection() {
        StringBuilder selection = new StringBuilder(MediaStore.Files.FileColumns.DATA);
        selection.append(" LIKE '%.apk'");
        return selection.toString();
    }
}
