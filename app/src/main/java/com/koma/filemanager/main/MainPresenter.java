package com.koma.filemanager.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.koma.filemanager.FilemanagerApplication;
import com.koma.filemanager.R;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 11/23/16.
 */

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";
    private int mAudioCounts;
    private Context mContext;
    @NonNull
    private MainContract.View mView;
    @NonNull
    private CompositeSubscription mSubscriptions;
    @NonNull private FileRepository mFileRepository;

    public MainPresenter(Context context,@NonNull MainContract.View view,@NonNull FileRepository fileRepository) {
        mContext = context;
        mFileRepository = fileRepository;
        mView = view;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getAudioCounts();
        getVideoCounts();
        getImageCounts();
        getDocumentCounts();
        getZipCounts();
        getApkCounts();
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
        mContext.startActivity(intent);
    }

    @Override
    public void getAudioCounts() {
        Subscription subscription = mFileRepository.getAuidoCounts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "onCompleted Thread id : "
                                + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG, "getAudioCounts error :" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        if (mView != null) {
                            mView.refreshAudioCounts(s);
                        }
                    }
                });
        mSubscriptions.add(subscription);
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
