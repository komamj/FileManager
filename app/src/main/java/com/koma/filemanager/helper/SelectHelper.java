package com.koma.filemanager.helper;

import com.koma.filemanager.base.BaseFile;

import java.util.List;

/**
 * Created by koma on 12/16/16.
 */

public class SelectHelper {
    private static final String TAG = "SelectHelper";
    //Select Mode
    public static final int MODE_IDLE = 0, MODE_SINGLE = 1, MODE_MULTI = 2;
    private static SelectHelper sInstance;
    private List<BaseFile> mClickedItem;

    private SelectHelper() {
    }

    public static SelectHelper getInstance() {
        if (sInstance == null) {
            sInstance = new SelectHelper();
        }
        return sInstance;
    }

    public void toggle(BaseFile baseFile) {
        if (mClickedItem.contains(baseFile)) {
            mClickedItem.remove(baseFile);
        } else {
            mClickedItem.add(baseFile);
        }
    }

    public void selectAll() {
    }

    public int getTotalCount() {
        return 0;
    }

    public int getSelectedCount() {
        return 0;
    }
    /*public void setDataSource(DataSource datasource) {
        mDataSource = datasource;
    }*/

    public List<BaseFile> getSelected() {
        return mClickedItem;
    }
}
