package com.koma.filemanager.filecategory;

import com.koma.filemanager.base.BasePresenter;
import com.koma.filemanager.base.BaseView;
import com.koma.filemanager.data.model.ZipFile;

import java.util.ArrayList;

/**
 * Created by koma on 12/12/16.
 */

public interface FileCategoryContract {
    interface View extends BaseView<Presenter> {
        void refreshAdapter(ArrayList<ZipFile> zipFiles);

        void showEmpty();

        void showLoadingView();
    }

    interface Presenter extends BasePresenter {
        void getZipFiles();
    }
}
