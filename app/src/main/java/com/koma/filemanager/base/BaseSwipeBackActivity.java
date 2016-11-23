package com.koma.filemanager.base;

import android.os.Bundle;

import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 11/16/16.
 */

public abstract class BaseSwipeBackActivity extends BaseActivity {
    private static final String TAG = "BaseSwipeBackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
    }

}
