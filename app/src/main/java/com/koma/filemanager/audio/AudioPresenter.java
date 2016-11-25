package com.koma.filemanager.audio;


import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.koma.filemanager.FilemanagerApplication;
import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 11/24/16.
 */

public class AudioPresenter implements AudioConstract.Presenter {
    private static final String TAG = "AudioPresenter";
    private CompositeSubscription mSubscription;
    @NonNull
    private AudioConstract.View mView;
    private Subscriber mAuidoSubscriber = new Subscriber<ArrayList<AudioFile>>() {
        @Override
        public void onCompleted() {
            LogUtils.i(TAG, "onCompleted Thread id : " + Thread.currentThread().getId());
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.e(TAG, "error : " + e.toString());
        }

        @Override
        public void onNext(ArrayList<AudioFile> audioFiles) {
            LogUtils.i(TAG, "onNext Thread id : " + Thread.currentThread().getId());
            if (mView != null) {
                if (audioFiles.size() == 0) {
                    mView.showEmpty();
                } else {
                    mView.refreshAdapter(audioFiles);
                }
            }
        }
    };

    public AudioPresenter(@NonNull AudioConstract.View view) {
        mView = view;
        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
        Subscription subscription = getAudioFiles().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAuidoSubscriber);
        mSubscription.add(subscription);
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
        if (mSubscription != null) {
            mSubscription.clear();
        }
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
}
