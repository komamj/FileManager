package com.koma.filemanager.helper;


import java.io.File;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by koma on 12/2/16.
 */

public class FileCountHelper {
    private static final String TAG = "FileCountHelper";
    private static FileCountHelper mHelper;

    private FileCountHelper() {
    }

    public synchronized static FileCountHelper getInstance() {
        if (mHelper == null) {
            mHelper = new FileCountHelper();
        }
        return mHelper;
    }

    public Observable<Integer> getFileCount(final String fullPath) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                File file = new File(fullPath);
                int filesCount = FileHelper.getSubFilesCount(file);
                subscriber.onNext(Integer.valueOf(filesCount));
                subscriber.onCompleted();
            }
        });
    }
}
