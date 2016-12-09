package com.koma.filemanager.video;

import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by koma on 11/28/16.
 */

public class VideoPresenter implements VideoContract.Presenter {
    private static final String TAG = "VideoPresenter";
    private VideoContract.View mView;
    FileRepository mRepository;

    public VideoPresenter(VideoFragment view, FileRepository repository) {
        mView = view;
        mView.setPresenter(this);
        mRepository = repository;
    }

    @Override
    public Observable<ArrayList<AudioFile>> getVideoFiles() {
        LogUtils.i(TAG, "getAudioFiles");
        return null;
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG, "subscribe");

    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG, "unSubscribe");

    }
}
