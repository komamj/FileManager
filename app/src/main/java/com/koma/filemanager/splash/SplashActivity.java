package com.koma.filemanager.splash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseActivity;
import com.koma.filemanager.main.MainActivity;
import com.koma.filemanager.util.BlurUtils;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by koma on 11/17/16.
 */

public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private static final int TIME_TO_MAINACTIVITY = 2 * 1000;
    private Handler mHandler;
    @BindView(R.id.blur_image)
    ImageView mBlurImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate thread id :" + Thread.currentThread().getId());
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        mHandler = new Handler(Looper.myLooper());
        Glide.with(this).load(R.mipmap.ic_launcher).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(final Bitmap resource,
                                                GlideAnimation<? super Bitmap> glideAnimation) {
                        Observable.just(resource)
                                .map(new Func1<Bitmap, Drawable>() {
                                    @Override
                                    public Drawable call(Bitmap bitmap) {
                                        LogUtils.i(TAG, "call-------thread id :"
                                                + Thread.currentThread().getId());
                                        return BlurUtils.blurBitmap(mContext, bitmap);
                                    }
                                })
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Drawable>() {
                                    @Override
                                    public void onCompleted() {
                                        LogUtils.i(TAG, "onCompleted thread id :" + Thread.currentThread().getId());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        LogUtils.e(TAG, "error : " + e.toString());
                                    }

                                    @Override
                                    public void onNext(Drawable drawable) {
                                        LogUtils.i(TAG, "onNext thread id :" + Thread.currentThread().getId());
                                        mBlurImage.setImageDrawable(drawable);
                                    }
                                });
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.splashactivity_layout;
    }

    private Runnable mToMainActivityRunnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            SplashActivity.this.finish();
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_UP) && (event.getKeyCode() == KeyEvent.KEYCODE_BACK)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
        if (mHandler != null) {
            mHandler.postDelayed(mToMainActivityRunnable, TIME_TO_MAINACTIVITY);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
        if (mHandler != null) {
            mHandler.removeCallbacks(mToMainActivityRunnable);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }
}
