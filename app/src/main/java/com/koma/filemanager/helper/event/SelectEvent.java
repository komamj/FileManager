package com.koma.filemanager.helper.event;

import com.koma.filemanager.base.BaseFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koma on 12/16/16.
 */

public class SelectEvent {
    private int mSelectMode;
    private List<BaseFile> mSelectedFiles;

    public SelectEvent(int selectMode) {
        this.mSelectMode = selectMode;
    }

    public SelectEvent(int selectMode, ArrayList<BaseFile> selectedFiles) {
        this.mSelectMode = selectMode;
        mSelectedFiles = selectedFiles;
    }

    public void setSelectMode(int selectMode) {
        this.mSelectMode = selectMode;
    }

    public int getSelectMode() {
        return mSelectMode;
    }

    public void setSelectedFiles(ArrayList<BaseFile> selectedFiles) {
        if (mSelectedFiles != null) {
            mSelectedFiles.clear();
        } else {
            mSelectedFiles = new ArrayList<>();
        }
        this.mSelectedFiles.addAll(selectedFiles);
    }

    public List<BaseFile> getSelectedFiles() {
        return mSelectedFiles;
    }
}
