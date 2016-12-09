package com.koma.filemanager.helper;

import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by koma on 12/5/16.
 */

public class MeidaHelper {
    private static final String TAG = "MediaHelper";
    public static Uri getAlbumArtUri(long albumId) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId);
    }

}
