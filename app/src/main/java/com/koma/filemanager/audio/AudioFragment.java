package com.koma.filemanager.audio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseFragment;
import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 11/23/16.
 */

public class AudioFragment extends BaseFragment implements AudioConstract.View {
    private static final String TAG = "AudioFragment";
    private AudioConstract.Presenter mPresenter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.audio_fragment, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "onViewCreated");
    }

    @Override
    public void setPresenter(@NonNull AudioConstract.Presenter presenter) {
        mPresenter = presenter;
    }
}
