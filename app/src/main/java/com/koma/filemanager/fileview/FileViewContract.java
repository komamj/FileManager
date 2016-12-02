package com.koma.filemanager.fileview;

import com.koma.filemanager.base.BaseFile;
import com.koma.filemanager.base.BasePresenter;
import com.koma.filemanager.base.BaseView;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by koma on 12/1/16.
 */

public interface FileViewContract {
    interface View extends BaseView<Presenter> {
        void refreshAdapter(ArrayList<BaseFile> files);

        void showLoadingView();

        void showEmptyView();

        String getPath();
    }

    interface Presenter extends BasePresenter {
        void getFiles(String path);
    }
}
