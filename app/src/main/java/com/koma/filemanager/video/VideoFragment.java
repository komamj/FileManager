package com.koma.filemanager.video;

import com.koma.filemanager.base.BaseFragment;
import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by koma on 11/28/16.
 */

public class VideoFragment extends BaseFragment implements VideoContract.View {
    private static final String TAG = "VideoFragment";

    @Override
    public void refreshAdapter(ArrayList<AudioFile> audioFiles) {
        LogUtils.i(TAG, "refreshAdapter");
    }

    @Override
    public void showEmpty() {
        LogUtils.i(TAG, "showEmpty");
    }

    @Override
    public void setPresenter(VideoContract.Presenter presenter) {
        LogUtils.i(TAG, "setPresenter");
    }
}
