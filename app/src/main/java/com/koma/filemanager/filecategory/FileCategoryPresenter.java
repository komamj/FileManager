package com.koma.filemanager.filecategory;

import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.data.model.ZipFile;
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

public class FileCategoryPresenter implements FileCategoryContract.Presenter {
    private static final String TAG = "FileCategoryPresenter";
    private CompositeSubscription mSubscriptions;
    private FileRepository mRespository;
    private FileCategoryContract.View mView;
    private Subscription mZipFilesSubsription;

    public FileCategoryPresenter(FileCategoryContract.View view, FileRepository repository) {
        mView = view;
        mRespository = repository;
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    private Subscriber<ArrayList<ZipFile>> mZipSubsriber = new Subscriber<ArrayList<ZipFile>>() {
        @Override
        public void onCompleted() {
            LogUtils.i(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            LogUtils.i(TAG, "onError :" + e.toString());
        }

        @Override
        public void onNext(ArrayList<ZipFile> zipFiles) {
            LogUtils.i(TAG, "onNext");
            if (mView != null) {
                if (zipFiles.size() == 0) {
                    mView.showEmpty();
                } else {
                    mView.refreshAdapter(zipFiles);
                }
            }
        }
    };

    @Override
    public void getZipFiles() {
        LogUtils.i(TAG, "getZipFiles");
        if (mSubscriptions != null) {
            mSubscriptions.remove(mZipSubsriber);
        }
        if (mRespository != null) {
            mZipFilesSubsription = mRespository.getZipFiles()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mZipSubsriber);
        }
        mSubscriptions.add(mZipFilesSubsription);
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subsribe");
        getZipFiles();
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubsribe");
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }
}
