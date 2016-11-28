package com.koma.filemanager.video;

import com.koma.filemanager.base.BasePresenter;
import com.koma.filemanager.base.BaseView;
import com.koma.filemanager.data.model.AudioFile;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by koma on 11/28/16.
 */

public interface VideoContract {
    interface View extends BaseView<Presenter> {
        void refreshAdapter(ArrayList<AudioFile> audioFiles);

        void showEmpty();
    }

    interface Presenter extends BasePresenter {
        Observable<ArrayList<AudioFile>> getVideoFiles();
    }
}
