package com.koma.filemanager.image;

import android.os.Bundle;

import com.koma.filemanager.base.BaseSwipeBackActivity;
import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 11/16/16.
 */

public class ImageActivity extends BaseSwipeBackActivity {
    private static final String TAG = "ImageActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
    }

    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
    }

    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }
}
