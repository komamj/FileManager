package com.koma.filemanager.audio;

import android.os.Bundle;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseSwipeBackActivity;
import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 11/19/16.
 */

public class AudioActivity extends BaseSwipeBackActivity {
    private static final String TAG = "AudioActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
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
