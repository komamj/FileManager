package com.koma.filemanager.audio;

import android.os.Bundle;
import android.view.View;

import com.koma.filemanager.base.BaseSwipeBackActivity;
import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 11/29/16.
 */

public class AudioPlayerActivity extends BaseSwipeBackActivity {
    private static String TAG = "AudioPlayerActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
