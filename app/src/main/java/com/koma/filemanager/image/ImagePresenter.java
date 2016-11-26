package com.koma.filemanager.image;

import com.koma.filemanager.data.model.ImageFile;
import com.koma.filemanager.util.LogUtils;

import java.util.List;

import rx.Observable;

/**
 * Created by koma on 2016/11/26.
 */

public class ImagePresenter implements ImageConstract.Presenter {
    private static final String TAG = "ImagePresenter";
    @Override
    public Observable<List<ImageFile>> getImageFiles() {
        LogUtils.i(TAG,"getImageFiles");
        return null;
    }

    @Override
    public void subscribe() {
        LogUtils.i(TAG,"subscribe");
    }

    @Override
    public void unSubscribe() {
        LogUtils.i(TAG,"unSubscribe");
    }
}
