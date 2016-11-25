package com.koma.filemanager.base;

import java.util.Date;

/**
 * Created by koma on 11/23/16.
 */

public class BaseFile {
    private String mFileName;
    private Date mFileModifiedTime;
    private long mFileSize;
    private String mParent;

    public BaseFile(String fileName, String parent, long fileSize, Date fileModifiedTime) {
        mFileName = fileName;
        mFileSize = fileSize;
        mFileModifiedTime = fileModifiedTime;
        mParent = parent;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileSize(long fileSize) {
        mFileSize = fileSize;
    }

    public long getFileSize() {
        return mFileSize;
    }

    public void setFileModifiedTime(Date modifiedTime) {
        mFileModifiedTime = modifiedTime;
    }

    public Date getFileModifiedTime() {
        return mFileModifiedTime;
    }

    public void setParent(String parent) {
        mParent = parent;
    }

    public String getParent() {
        return mParent;
    }
}
