package com.koma.filemanager.archive;

import android.net.Uri;

import com.koma.filemanager.base.BasePresenter;
import com.koma.filemanager.base.BaseView;
import com.koma.filemanager.data.model.Disk;

import java.util.ArrayList;

/**
 * Created by koma on 12/12/16.
 */

public interface ArchiveContract {
    interface View extends BaseView<Presenter> {
        void refreshAdapter(ArrayList<Disk> disks);

        void showCompleted();

        void showError();
    }

    interface Presenter extends BasePresenter {
        void loadDisks();

        void startArchive();

        void endArchive();

        String getPathFromUri(Uri uri);
    }
}
