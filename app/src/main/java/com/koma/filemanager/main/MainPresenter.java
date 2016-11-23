package com.koma.filemanager.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.koma.filemanager.FilemanagerApplication;
import com.koma.filemanager.R;
import com.koma.filemanager.util.Constants;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 11/23/16.
 */

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";
    private Context mContext;
    @NonNull
    private MainContract.View mView;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public MainPresenter(@NonNull Context context, @NonNull MainContract.View view) {
        mContext = context;
        mView = view;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void launchCategoryActivity(int resourceId) {
        Intent intent = new Intent();
        switch (resourceId) {
            case R.id.audio_category:
                ComponentName componentName = new ComponentName("com.koma.filemanager",
                        "com.koma.filemanager.audio.AudioActivity");
                intent.setComponent(componentName);
                break;
            case R.id.video_category:
                LogUtils.i(TAG, "launch VideoActivity");
                break;
            case R.id.image_category:
                LogUtils.i(TAG, "launch ImageActivity");
                break;
            case R.id.document_category:
                LogUtils.i(TAG, "launch DocumentActivity");
                break;
            case R.id.zip_category:
                LogUtils.i(TAG, "launch ZipActivity");
                break;
            case R.id.apk_category:
                LogUtils.i(TAG, "launch ApkActivity");
                break;
            default:
                LogUtils.i(TAG, "launchCategoryActivity  no match resourceId");
        }

    }

    @Override
    public int getAudioCounts() {
        Observable.just(FileCategoryUtils.getAudioUri()).map(new Func1<Uri, Cursor>() {
            @Override
            public Cursor call(Uri uri) {
                Cursor cursor;
                cursor = FilemanagerApplication.getContext().getContentResolver().query(uri,
                        FileCategoryUtils.getMediaProjection(),
                        FileCategoryUtils.getSelection(), null, null);
                return cursor;
            }
        }).observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Cursor>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Cursor cursor) {
                if (cursor != null && !cursor.isClosed()) {
                    int count = cursor.getCount();
                    cursor.close();
                }


            }
        });
        return 0;
    }

    @Override
    public int getVideoCounts() {
        return 0;
    }

    @Override
    public int getImageCounts() {
        return 0;
    }

    @Override
    public int getDocumentCounts() {
        return 0;
    }

    @Override
    public int getZipCounts() {
        return 0;
    }

    @Override
    public int getApkCounts() {
        return 0;
    }
}
