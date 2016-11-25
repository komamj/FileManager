package com.koma.filemanager.data.model;

import com.koma.filemanager.base.BaseFile;

import java.util.Date;

/**
 * Created by koma on 11/23/16.
 */

public class AudioFile extends BaseFile {
    private String mDisplayName;

    public AudioFile() {
        super("", "", 0, null);
        mDisplayName = "";
    }

    public AudioFile(String fileName, String parent, long fileSize, Date fileModifiedTime, String displayName) {
        super(fileName, parent, fileSize, fileModifiedTime);
        mDisplayName = displayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayTime() {
        return mDisplayName;
    }
}
