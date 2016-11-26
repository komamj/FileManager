package com.koma.filemanager.audio;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseSwipeBackActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindView;

/**
 * Created by koma on 11/19/16.
 */

public class AudioActivity extends BaseSwipeBackActivity {
    private static final String TAG = "AudioActivity";
    /*@BindView(R.id.toolbar)
    Toolbar mToolBar;*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
       // setSupportActionBar(mToolBar);
        AudioFragment audioFragment = (AudioFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (audioFragment == null) {
            audioFragment = new AudioFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, audioFragment).commit();
        }
        AudioPresenter audioPresenter = new AudioPresenter(audioFragment, FileRepository.getInstance());
        audioPresenter.subscribe();
    }

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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.audio_activity;
    }

}
