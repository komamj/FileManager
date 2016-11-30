package com.koma.filemanager.fileview;

import android.os.Bundle;

import com.koma.filemanager.base.BaseSwipeBackActivity;
import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 11/30/16.
 */

public class FileViewActivity extends BaseSwipeBackActivity {
    private static final String TAG = "FileViewActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
