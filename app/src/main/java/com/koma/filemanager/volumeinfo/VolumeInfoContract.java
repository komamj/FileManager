package com.koma.filemanager.volumeinfo;

import com.koma.filemanager.base.BasePresenter;
import com.koma.filemanager.base.BaseView;
import com.koma.filemanager.data.model.Disk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koma on 12/13/16.
 */

public interface VolumeInfoContract {
    interface View extends BaseView<Presenter> {
        void refreshAdapter(List<Disk> disks);
    }

    interface Presenter extends BasePresenter {
        void getDisks();

        void onTaskCompleted(ArrayList<Disk> disks);
    }
}
