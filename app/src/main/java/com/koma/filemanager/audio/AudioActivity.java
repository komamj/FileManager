package com.koma.filemanager.audio;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseMenuActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindView;

/**
 * Created by koma on 11/19/16.
 */

public class AudioActivity extends BaseMenuActivity {
    private static final String TAG = "AudioActivity";
    AudioPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
        AudioFragment audioFragment = (AudioFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (audioFragment == null) {
            audioFragment = new AudioFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, audioFragment).commit();
        }
        mPresenter = new AudioPresenter(audioFragment, FileRepository.getInstance());
        mPresenter.subscribe();
        getContentResolver().registerContentObserver(FileCategoryUtils.getAudioUri(), true, mAudioObserver);
    }

    private final ContentObserver mAudioObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "Audio uri change so refresh");
            if (mPresenter != null) {
                mPresenter.getAudioFiles();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        if (mAudioObserver != null) {
            getContentResolver().unregisterContentObserver(mAudioObserver);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.category_activity;
    }

    @Override
    protected void sortByType() {

    }

    @Override
    protected void sortByName() {

    }

    @Override
    protected void sortBySize() {

    }

    @Override
    protected void sortByDate() {

    }
}
