package com.koma.filemanager.main;

import com.koma.filemanager.base.BasePresenter;
import com.koma.filemanager.base.BaseView;

/**
 * Created by koma on 11/23/16.
 */

public interface MainContract {
    interface View extends BaseView<MainPresenter> {
        void refreshAudioCounts(String count);

        void refreshVideoCounts(String count);

        void refreshImageCounts(String count);

        void refreshDocumentCounts(String count);

        void refreshZipCounts(String count);

        void refreshApkCounts(String count);
    }

    interface Presenter extends BasePresenter {
        void launchCategoryActivity(int resourceId);

        void getAudioCounts();

        void getVideoCounts();

        void getImageCounts();

        void getDocumentCounts();

        void getZipCounts();

        void getApkCounts();
    }
}
