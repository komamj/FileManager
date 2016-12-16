package com.koma.filemanager.helper.event;

import com.koma.filemanager.helper.FileSortHelper;

/**
 * Created by koma on 12/14/16.
 */

public class SortEvent {
    private FileSortHelper.SortKey mSortKey;
    private FileSortHelper.SortMethod mSortMethod;

    public SortEvent(FileSortHelper.SortKey sortKey, FileSortHelper.SortMethod sortMethod) {
        this.mSortKey = sortKey;
        this.mSortMethod = sortMethod;
    }

    public void setSortKey(FileSortHelper.SortKey sortKey) {
        this.mSortKey = sortKey;
    }

    public FileSortHelper.SortKey getSortKey() {
        return mSortKey;
    }

    public void setSortMethod(FileSortHelper.SortMethod sortMethod) {
        this.mSortMethod = sortMethod;
    }

    public FileSortHelper.SortMethod getSortMethod() {
        return mSortMethod;
    }
}
