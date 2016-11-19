package com.koma.filemanager.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.koma.filemanager.util.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by koma on 11/16/16.
 */

public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "onViewCreated");
        ButterKnife.bind(view);
    }
}
