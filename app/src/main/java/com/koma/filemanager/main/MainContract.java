package com.koma.filemanager.main;

import com.koma.filemanager.base.BasePresenter;
import com.koma.filemanager.base.BaseView;

/**
 * Created by koma on 11/23/16.
 */

public interface MainContract {
    interface View extends BaseView<MainPresenter> {
        void refreshAudioCounts(int count);

        void refreshVideoCounts(int count);

        void refreshImageCounts(int count);

        void refreshDocumentCounts(int count);

        void refreshZipCounts(int count);

        void refreshApkCounts(int count);
    }

    interface Presenter extends BasePresenter {
        void launchCategoryActivity(int resourceId);

        int getAudioCounts();

        int getVideoCounts();

        int getImageCounts();

        int getDocumentCounts();

        int getZipCounts();

        int getApkCounts();
    }
}
