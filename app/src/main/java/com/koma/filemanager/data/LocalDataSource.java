package com.koma.filemanager.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.koma.filemanager.FilemanagerApplication;
import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.data.model.ImageFile;
import com.koma.filemanager.data.model.VideoFile;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by koma on 11/25/16.
 */

public class LocalDataSource implements FileDataSource {
    private static final String TAG = "LocalDataSource";
    private Context mContext;

    public LocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public Observable<String> getAuidoCounts() {
        return Observable.just(FileCategoryUtils.getAudioUri())
                .map(new Func1<Uri, String>() {
                    @Override
                    public String call(Uri uri) {
                        Cursor cursor = FilemanagerApplication.getContext().getContentResolver()
                                .query(uri, FileCategoryUtils.getMediaProjection(),
                                        FileCategoryUtils.getSelection(), null, null);
                        int count = cursor.getCount();
                        if (cursor != null) {
                            if (!cursor.isClosed()) {
                                cursor.close();
                            }
                        }
                        LogUtils.i(TAG, "getAudioCounts thread id : " + Thread.currentThread().getId());
                        return String.valueOf(count);
                    }
                });
    }

    @Override
    public Observable<String> getVideoCounts() {
        return null;
    }

    @Override
    public Observable<String> getImageCounts() {
        return null;
    }

    @Override
    public Observable<String> getDocumentsCounts() {
        return null;
    }

    @Override
    public Observable<String> getZipCounts() {
        return null;
    }

    @Override
    public Observable<String> getApkCounts() {
        return null;
    }

    @Override
    public Observable<ArrayList<AudioFile>> getAudioFiles() {
        return Observable.just(FileCategoryUtils.getAudioUri())
                .map(new Func1<Uri, ArrayList<AudioFile>>() {
                    @Override
                    public ArrayList<AudioFile> call(Uri uri) {
                        Cursor cursor = FilemanagerApplication.getContext().getContentResolver()
                                .query(uri, FileCategoryUtils.getAudioProjection(),
                                        FileCategoryUtils.getSelection(), null, null);
                        ArrayList<AudioFile> audioFiles = new ArrayList<>();
                        if (cursor != null) {
                            if (!cursor.isClosed()) {
                                while (cursor.moveToNext()) {
                                    File file = new File(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)));
                                    AudioFile audioFile = new AudioFile();
                                    audioFile.setFileName(file.getName());
                                    audioFile.setParent(file.getParent());
                                    audioFile.setDisplayName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
                                    audioFile.setFileSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE)));
                                    audioFile.setFileModifiedTime(new Date(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATE_MODIFIED)) * 1000));
                                    audioFiles.add(audioFile);
                                }
                                cursor.close();
                            }
                            LogUtils.i(TAG, "getAudioFiles Thread id: " + Thread.currentThread().getId());
                        }
                        return audioFiles;
                    }
                });
    }

    @Override
    public Observable<ArrayList<ImageFile>> getImageFiles() {
        return null;
    }

    @Override
    public Observable<ArrayList<VideoFile>> getVideoFiles() {
        return null;
    }
}
