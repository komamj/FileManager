package com.koma.filemanager.image;

import com.koma.filemanager.base.BaseFragment;
import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 2016/11/26.
 */

public class ImageFragment extends BaseFragment implements ImageConstract.View {
    private static final String TAG = "ImageFragment";
    @Override
    public void showEmptyView() {
        LogUtils.i(TAG,"showEmptyView");
    }

    @Override
    public void refreshAdapter() {
        LogUtils.i(TAG,"refreshAdapter");
    }

    @Override
    public void setPresenter(ImageConstract.Presenter presenter) {
        LogUtils.i(TAG,"setPresenter");
    }
}
