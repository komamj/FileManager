package com.koma.filemanager.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.koma.filemanager.R;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.data.model.Disk;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
    @NonNull
    private FileRepository mFileRepository;

    public MainPresenter(Context context, @NonNull MainContract.View view, @NonNull FileRepository fileRepository) {
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
        getDisks();
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
                        LogUtils.i(TAG, "getAudioCounts onCompleted Thread id : "
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
    public void getVideoCounts() {
        Subscription subscription = mFileRepository.getVideoCounts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getVideoCounts onCompleted Thread id : "
                                + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "getVideoCounts onError" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        if (mView != null) {
                            mView.refreshVideoCounts(s);
                        }
                    }
                });
        mSubscriptions.add(subscription);

    }

    @Override
    public void getImageCounts() {
        Subscription subscription = mFileRepository.getImageCounts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getImageCounts onCompleted Thread id : "
                                + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "getImageCounts onError" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        if (mView != null) {
                            mView.refreshImageCounts(s);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getDocumentCounts() {
        Subscription subscription = mFileRepository.getDocumentsCounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getDocumentsCounts onCompleted Thread id : "
                                + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "getDocumentsCounts onError" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        if (mView != null) {
                            mView.refreshDocumentCounts(s);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getZipCounts() {
        Subscription subscription = mFileRepository.getZipCounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getZipCounts onCompleted Thread id : "
                                + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "getZipCounts onError" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        if (mView != null) {
                            mView.refreshZipCounts(s);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getApkCounts() {
        Subscription subscription = mFileRepository.getApkCounts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getApkCounts onCompleted Thread id : "
                                + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "getApkCounts onError" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        if (mView != null) {
                            mView.refreshApkCounts(s);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getDisks() {
        Subscription subscription = mFileRepository.getDisks().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Disk>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "getDisks onCompleted Thread id : "
                                + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i(TAG, "getDisks error :" + e.toString());
                    }

                    @Override
                    public void onNext(ArrayList<Disk> disks) {
                        LogUtils.i(TAG, "getDisks onNext");
                        if (mView != null) {
                            mView.refreshAdapter(disks);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
