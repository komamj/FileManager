package com.koma.filemanager.fileview;

import android.support.annotation.NonNull;

import com.koma.filemanager.base.BaseFile;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 12/1/16.
 */

public class FileViewPresenter implements FileViewContract.Presenter {
    private static final String TAG = "FileViewPresenter";
    private FileViewContract.View mView;
    private FileRepository mRepository;
    private CompositeSubscription mSubscriptions;
    private Subscription mGetFilesSubscription;
    private Subscriber<ArrayList<BaseFile>> mFilesSubsriber = new Subscriber<ArrayList<BaseFile>>() {
        @Override
        public void onCompleted() {
            LogUtils.i(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.i(TAG, "onError error : " + e.toString());
        }

        @Override
        public void onNext(ArrayList<BaseFile> baseFiles) {
            if (mView != null) {
                if (baseFiles != null) {
                    if (baseFiles.size() == 0) {
                        mView.showEmptyView();
                    } else {
                        mView.refreshAdapter(baseFiles);
                    }
                }
            }
        }
    };

    public FileViewPresenter(@NonNull FileViewFragment fragment, @NonNull FileRepository repository) {
        mView = fragment;
        mView.setPresenter(this);
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void getFiles(String path) {
        LogUtils.i(TAG, "getFiles");
        if (mSubscriptions != null && mGetFilesSubscription != null) {
            mSubscriptions.remove(mGetFilesSubscription);
        }
        mGetFilesSubscription = mRepository.getFiles(path).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mFilesSubsriber);
        mSubscriptions.add(mGetFilesSubscription);
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
        if (mView != null) {
            mView.showLoadingView();
        }
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
        mSubscriptions.clear();
    }
}
