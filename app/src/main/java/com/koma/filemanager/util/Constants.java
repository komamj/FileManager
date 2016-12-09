package com.koma.filemanager.util;

import android.Manifest;
import android.provider.MediaStore;

/**
 * Created by koma on 11/19/16.
 */

public final class Constants {
    public static final int IN_SAMPLE_SIZE = 4;
    public static final int COMPRESS_QUALITY = 100;
    // 25f is the max blur radius.
    public static final float BLUR_RADIUS = 10f;

    //Permissions
    public static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    //CategoryFiles
    public static final String EXTERNAL = "external";
    public static final String[] MEDIA_PROJECTION = new String[]{"_id"};
    public static final String[] FILE_PROJECTION = new String[]{
            MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE
    };

    public static final String[] AUDIO_PROJECTION = new String[]{
            MediaStore.Audio.AudioColumns.ALBUM_ID, MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.DISPLAY_NAME,
            MediaStore.Audio.AudioColumns.SIZE, MediaStore.Audio.AudioColumns.DATE_MODIFIED
    };

    //Formate Date
    public static final String FILE_MODIFIED_TIME_FORMAT = "yyyyMMdd";

    //FileViewActivity
    public static final String EXTRA_FILE_DIRECTORY = "extra_file_directory";
}
