package com.koma.filemanager.base;

import com.koma.filemanager.helper.FileHelper;

import java.io.File;
import java.util.Date;

/**
 * Created by koma on 11/23/16.
 */

public class BaseFile implements Comparable<BaseFile> {
    private String mFileName;
    private Date mFileModifiedTime;
    private long mFileSize;
    private String mParent;
    private boolean mIsDirectory;

    public BaseFile(String fileName, String parent, long fileSize, Date fileModifiedTime) {
        mFileName = fileName;
        mFileSize = fileSize;
        mFileModifiedTime = fileModifiedTime;
        mParent = parent;
        mIsDirectory = true;
    }

    public BaseFile(String fileName, String parent, long fileSize, Date fileModifiedTime, boolean isDirectory) {
        mFileName = fileName;
        mFileSize = fileSize;
        mFileModifiedTime = fileModifiedTime;
        mParent = parent;
        mIsDirectory = isDirectory;
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

    public boolean getIsDirectory() {
        return mIsDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        mIsDirectory = isDirectory;
    }

    public String getFullPath() {
        StringBuilder stringBuilder = new StringBuilder(mParent);
        if (FileHelper.isRootDirectory(this)) {
            return FileHelper.ROOT_DIRECTORY;
        } else if (FileHelper.isParentRootDirectory(this)) {
            if (this.mParent == null) {
                return FileHelper.ROOT_DIRECTORY + mFileName;
            }
            return stringBuilder.append(mFileName).toString();
        }
        return stringBuilder.append(File.separator).append(mFileName).toString();
    }

    @Override
    public int compareTo(BaseFile another) {
        String o1 = this.getFullPath();
        String o2 = another.getFullPath();
        return o1.compareTo(o2);
    }
}
