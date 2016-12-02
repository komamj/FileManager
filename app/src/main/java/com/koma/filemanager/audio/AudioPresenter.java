package com.koma.filemanager.audio;

import android.support.annotation.NonNull;

import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
    @NonNull
    private FileRepository mFileRepository;
    private Subscription mAudioFilesSubsription;
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

    public AudioPresenter(@NonNull AudioConstract.View view, @NonNull FileRepository repository) {
        mFileRepository = repository;
        mView = view;
        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");
        getAudioFiles();
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");
        if (mSubscription != null) {
            mSubscription.clear();
        }
    }

    @Override
    public void getAudioFiles() {
        if (mSubscription != null) {
            mSubscription.remove(mAudioFilesSubsription);
        }
        mAudioFilesSubsription = mFileRepository.getAudioFiles().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAuidoSubscriber);
        mSubscription.add(mAudioFilesSubsription);
    }
}
