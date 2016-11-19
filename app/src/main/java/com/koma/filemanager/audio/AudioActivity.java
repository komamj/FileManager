package com.koma.filemanager.audio;

import android.os.Bundle;

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

    @Override
    protected int getLayoutId() {
        return 0;
    }

}
