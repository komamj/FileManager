package com.koma.filemanager.helper.event;

/**
 * Created by koma on 12/16/16.
 */

public class SelectEvent {
    private boolean mSelectMode;

    public SelectEvent(boolean selectMode) {
        this.mSelectMode = selectMode;
    }

    public void setSelectMode(boolean selectMode) {
        this.mSelectMode = selectMode;
    }

    public boolean getSelectMode() {
        return mSelectMode;
    }
}
