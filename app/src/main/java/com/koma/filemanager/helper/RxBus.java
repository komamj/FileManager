package com.koma.filemanager.helper;

import com.koma.filemanager.util.LogUtils;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

/**
 * Created by koma on 12/14/16.
 */

public class RxBus {
    private static final String TAG = "RxBus";

    private static volatile RxBus sInstance;

    public static RxBus getInstance() {
        if (sInstance == null) {
            synchronized (RxBus.class) {
                if (sInstance == null) {
                    sInstance = new RxBus();
                }
            }
        }
        return sInstance;
    }

    /**
     * PublishSubject<Object> subject = PublishSubject.create();
     * // observer1 will receive all onNext and onCompleted events
     * subject.subscribe(observer1);
     * subject.onNext("one");
     * subject.onNext("two");
     * // observer2 will only receive "three" and onCompleted
     * subject.subscribe(observer2);
     * subject.onNext("three");
     * subject.onCompleted();
     */
    private PublishSubject<Object> mEventBus = PublishSubject.create();

    public void post(Object event) {
        mEventBus.onNext(event);
    }

    public Observable<Object> toObservable() {
        return mEventBus;
    }

    public static Subscriber<Object> defaultSubscriber() {
        return new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                LogUtils.i(TAG, "Duty off!!!");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "Throwable error : " + e.toString());
            }

            @Override
            public void onNext(Object o) {
                LogUtils.i(TAG, "New event received: " + o);
            }
        };
    }
}
