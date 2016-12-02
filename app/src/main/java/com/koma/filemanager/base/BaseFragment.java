package com.koma.filemanager.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 11/16/16.
 */

public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i(TAG, "onAttach");
        mContext = context;
    }
}
