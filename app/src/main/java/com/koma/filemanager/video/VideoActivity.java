package com.koma.filemanager.video;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.koma.filemanager.R;
import com.koma.filemanager.audio.AudioFragment;
import com.koma.filemanager.audio.AudioPresenter;
import com.koma.filemanager.base.BaseMenuActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindView;

/**
 * Created by koma on 11/28/16.
 */

public class VideoActivity extends BaseMenuActivity {
    private static final String TAG = "VideoActivity";
    private VideoContract.Presenter mPresenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
        VideoFragment videoFragment = (VideoFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, videoFragment).commit();
        }
        mPresenter = new VideoPresenter(videoFragment, FileRepository.getInstance());
        mPresenter.subscribe();
        getContentResolver().registerContentObserver(FileCategoryUtils.getAudioUri(), true, mVideoObserver);
    }

    private final ContentObserver mVideoObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "Video uri change so refresh");
            if (mPresenter != null) {
                mPresenter.getVideoFiles();
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.category_activity;
    }

    @Override
    protected void sortByType() {
        LogUtils.i(TAG, "sortByType");
    }

    @Override
    protected void sortByName() {
        LogUtils.i(TAG, "sortByName");
    }

    @Override
    protected void sortBySize() {
        LogUtils.i(TAG, "sortBySize");
    }

    @Override
    protected void sortByDate() {
        LogUtils.i(TAG, "sortByDate");
    }
}
