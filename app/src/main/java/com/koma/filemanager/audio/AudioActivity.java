package com.koma.filemanager.audio;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseMenuActivity;
import com.koma.filemanager.base.BaseSwipeBackActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindView;

/**
 * Created by koma on 11/19/16.
 */

public class AudioActivity extends BaseMenuActivity {
    private static final String TAG = "AudioActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    AudioPresenter mPresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
        setSupportActionBar(mToolBar);
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

    protected void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
    }

    protected void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
    }

    protected void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
    }

    protected void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.audio_activity;
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
