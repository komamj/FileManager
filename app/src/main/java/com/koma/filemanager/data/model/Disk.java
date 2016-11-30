package com.koma.filemanager.data.model;

/**
 * Created by koma on 11/29/16.
 */

public class Disk {
    private String mMountPoint, mDescription;
    private long mTotalSpace, mVailableSpace;

    public Disk() {
        this("", "", 0, 0);
    }

    public Disk(String mountPoint, String description, long total, long vailableSpace) {
        mMountPoint = mountPoint;
        mDescription = description;
        mTotalSpace = total;
        mVailableSpace = vailableSpace;
    }

    public void setMountPoint(String mountPoint) {
        mMountPoint = mountPoint;
    }

    public String getMountPoint() {
        return mMountPoint;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setTotalSpace(long totalSpace) {
        mTotalSpace = totalSpace;
    }

    public long getTotalSpace() {
        return mTotalSpace;
    }

    public void setAvailableSpace(long availableSpace) {
        mVailableSpace = availableSpace;
    }

    public long getAvailavleSpace() {
        return mVailableSpace;
    }
}
