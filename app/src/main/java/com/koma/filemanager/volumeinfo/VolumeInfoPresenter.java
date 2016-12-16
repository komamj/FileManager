package com.koma.filemanager.volumeinfo;

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
 * Created by koma on 12/13/16.
 */

public class VolumeInfoPresenter implements VolumeInfoContract.Presenter {
    private static final String TAG = "VolumeInfoPresenter";
    private VolumeInfoContract.View mView;
    private CompositeSubscription mSubsriptions;
    private Subscription mGetDisksSubscription;
    private FileRepository mRepository;

    public VolumeInfoPresenter(VolumeInfoContract.View view, FileRepository repository) {
        mView = view;
        mView.setPresenter(this);
        mSubsriptions = new CompositeSubscription();
        mRepository = repository;
    }

    @Override
    public synchronized void getDisks() {
        LogUtils.i(TAG, "getDisks");
        mGetDisksSubscription = mRepository.getDisks().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Disk>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(TAG, "onError error : " + e.toString());
                    }

                    @Override
                    public void onNext(ArrayList<Disk> disks) {
                        if (mView != null) {
                            onTaskCompleted(disks);
                        }
                    }
                });
        mSubsriptions.add(mGetDisksSubscription);
    }

    @Override
    public void onTaskCompleted(ArrayList<Disk> disks) {
        LogUtils.i(TAG, "onCompleted");
        if (mView != null) {
            mView.refreshAdapter(disks);
        }
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
        getDisks();
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubsribe");
        if (mSubsriptions != null) {
            mSubsriptions.clear();
        }
    }
}
