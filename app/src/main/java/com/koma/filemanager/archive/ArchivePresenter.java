package com.koma.filemanager.archive;


import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;

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
 * Created by koma on 12/12/16.
 */

public class ArchivePresenter implements ArchiveContract.Presenter {
    private static final String TAG = "ArchivePresenter";
    @NonNull
    private ArchiveContract.View mView;
    @NonNull
    private FileRepository mRespository;
    private CompositeSubscription mSubsriptions;
    private Subscription mDisksSubscription;

    public ArchivePresenter(@NonNull ArchiveContract.View view, FileRepository repository) {
        mView = view;
        mView.setPresenter(this);
        mRespository = repository;
    }

    private Subscriber<ArrayList<Disk>> mDisksSubscriber = new Subscriber<ArrayList<Disk>>() {
        @Override
        public void onCompleted() {
            LogUtils.i(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.i(TAG, "onError :" + e.toString());
        }

        @Override
        public void onNext(ArrayList<Disk> disks) {
            LogUtils.i(TAG, "onNext");
            if (mView != null) {
                mView.refreshAdapter(disks);
            }
        }

    };

    @Override
    public void loadDisks() {
        if (mSubsriptions != null && mDisksSubscription != null) {
            mSubsriptions.remove(mDisksSubscription);
        }
        mDisksSubscription = mRespository.getDisks().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mDisksSubscriber);
        mSubsriptions.add(mDisksSubscription);
    }

    @Override
    public void startArchive() {
        LogUtils.i(TAG, "startArchive");
    }

    @Override
    public void endArchive() {
        LogUtils.i(TAG, "endArchive");
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
        if (mSubsriptions == null) {
            mSubsriptions = new CompositeSubscription();
        }
    }

    @Override
    public String getPathFromUri(Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String path = null;
        if (null == scheme || ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        }
        return path;
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
        if (mSubsriptions != null) {
            mSubsriptions.clear();
        }
    }
}
