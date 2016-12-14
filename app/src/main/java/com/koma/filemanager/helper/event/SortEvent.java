package com.koma.filemanager.helper.event;

import com.koma.filemanager.helper.FileSortHelper;

/**
 * Created by koma on 12/14/16.
 */

public class SortEvent {
    public FileSortHelper.SortKey mSortKey;
    public FileSortHelper.SortMethod mSortMethod;

    public SortEvent(FileSortHelper.SortKey sortKey, FileSortHelper.SortMethod sortMethod) {
        this.mSortKey = sortKey;
        this.mSortMethod = sortMethod;
    }
}
