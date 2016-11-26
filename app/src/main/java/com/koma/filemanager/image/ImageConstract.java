package com.koma.filemanager.image;

import com.koma.filemanager.base.BasePresenter;
import com.koma.filemanager.base.BaseView;
import com.koma.filemanager.data.model.ImageFile;

import java.util.List;

import rx.Observable;

/**
 * Created by koma on 11/16/16.
 */

public interface ImageConstract {
    interface View extends BaseView<Presenter> {
        void showEmptyView();
        void refreshAdapter();
    }

    interface Presenter extends BasePresenter {
        Observable<List<ImageFile>> getImageFiles();
    }
}
