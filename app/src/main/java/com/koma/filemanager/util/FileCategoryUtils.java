package com.koma.filemanager.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;

import com.koma.filemanager.FileManagerApplication;
import com.koma.filemanager.R;
import com.koma.filemanager.data.model.Disk;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by koma on 11/23/16.
 */

public final class FileCategoryUtils {
    private static final String TAG = "FileCategoryUtils";

    public static Uri getFileUri() {
        return MediaStore.Files.getContentUri(Constants.EXTERNAL);
    }

    public static Uri getAudioUri() {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    }

    public static Uri getVideoUri() {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

    public static Uri getImageUri() {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    public static String[] getMediaProjection() {
        return Constants.MEDIA_PROJECTION;
    }

    public static String[] getFileProjection() {
        return Constants.FILE_PROJECTION;
    }

    public static String[] getAudioProjection() {
        return Constants.AUDIO_PROJECTION;
    }

    public static String getAudioFilesSelection() {
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(MediaStore.Files.FileColumns.DATA);
        stringBuilder.append(" NOT LIKE '.%'");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String buildDocSelection() {
        StringBuilder selection = new StringBuilder();
        String[] mimeTypes;
        String[] suffixOffice;
        Resources resources = FileManagerApplication.getContext().getResources();
        mimeTypes = resources.getStringArray(R.array.doc_mime_types);
        suffixOffice = resources.getStringArray(R.array.doc_office_suffix);
        for (int i = 0; i < mimeTypes.length; i++) {
            selection.append("(" + MediaStore.Files.FileColumns.MIME_TYPE + "=='" + mimeTypes[i] + "') OR ");
        }
        for (int i = 0; i < suffixOffice.length; i++) {
            selection.append("(" + MediaStore.Files.FileColumns.DATA + " LIKE '%." + suffixOffice[i] + "') OR ");
        }
        return selection.substring(0, selection.lastIndexOf(")") + 1);
    }

    public static String buildZipSelection() {
        StringBuilder selection = new StringBuilder();
        Resources resources = FileManagerApplication.getContext().getResources();
        String[] Suffix = resources.getStringArray(R.array.zip_suffix);
        for (int i = 0; i < Suffix.length; i++) {
            selection.append("(" + MediaStore.Files.FileColumns.DATA + " LIKE '%." + Suffix[i] + "') OR ");
        }
        return selection.substring(0, selection.lastIndexOf(")") + 1);
    }

    public static String buildApkSelection() {
        StringBuilder selection = new StringBuilder(MediaStore.Files.FileColumns.DATA);
        selection.append(" LIKE '%.apk'");
        return selection.toString();
    }

    public static ArrayList<Disk> getDisks() {
        ArrayList<Disk> disks = new ArrayList<>();
        StorageManager storageManager = (StorageManager) FileManagerApplication.getContext()
                .getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumes = StorageManager.class.getMethod("getVolumes");
            Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
            Method getBestVolumeDescription = StorageManager.class.getMethod("getBestVolumeDescription",
                    Class.forName("android.os.storage.VolumeInfo"));

            Object[] params = {};
            List<?> invokes = (List<?>) getVolumes.invoke(storageManager, params);
            if (invokes != null) {
                for (int i = 0; i < invokes.size(); i++) {
                    Object obj = invokes.get(i);
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    Method isMountedReadable = obj.getClass().getMethod("isMountedReadable", new Class[0]);
                    Method getType = obj.getClass().getMethod("getType", new Class[0]);
                    File file = (File) getPath.invoke(obj, new Object[0]);
                    long totalBytes = 0;
                    if (file != null) {
                        totalBytes = file.getTotalSpace();
                        LogUtils.i(TAG, "getDisks totalBytes : " + totalBytes);
                    }

                    boolean isMounted = (boolean) isMountedReadable.invoke(obj, new Object[0]);
                    LogUtils.i(TAG, "getDisks isMounted : " + isMounted);
                    if (isMounted) {
                        //TYPE_PRIVATE =1
                        int type = (int) getType.invoke(obj, new Object[0]);
                        LogUtils.i(TAG, "getDisks type : " + type);
                        if (type != 1) {
                            String description = (String) getBestVolumeDescription.invoke(storageManager, invokes.get(i));
                            LogUtils.i(TAG, "getDisks description : " + description);
                            //TYPE_EMULATED = 2
                            if (type == 2) {
                                file = new File(getPrimaryExternalStorageDirectory());
                            }
                            if (totalBytes > 0 && description != null) {
                                disks.add(createDisk(file, description));
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.i(TAG, "getDisks error :" + e.toString());
        }

        return disks;
    }

    /*public static synchronized List<DiskUsage> getMountedVolume(Context ctx) {
        StorageManager sm = null;
        ArrayList<DiskUsage> diskUsage = new ArrayList<DiskUsage>();
        sm = (StorageManager) ctx.getSystemService(Context.STORAGE_SERVICE);
        final List<VolumeInfo> volumes = sm.getVolumes();
        Collections.sort(volumes, VolumeInfo.getDescriptionComparator());
        try {
            for (VolumeInfo vol : volumes) {
                final File path = vol.getPath();
                long totalBytes = 0;
                if (path != null) {
                    totalBytes = path.getTotalSpace();
                }
                if (vol != null && vol.isMountedReadable() && path != null) {

                    File disk = path;

                    if (vol.getType() != VolumeInfo.TYPE_PRIVATE) {

                        String dsc = sm.getBestVolumeDescription(vol);

                        if (vol.getType() == VolumeInfo.TYPE_EMULATED) {
                            disk = new File(getPrimaryExternalStorageDirectory());
                        }

                        if (dsc == null) {
                            dsc = getStorageVolumeDescription(ctx, disk.getAbsolutePath());
                        }

                        if (totalBytes > 0) {
                            diskUsage.add(FileCategoryHelper.createDiskUsage(disk, dsc));
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diskUsage;
    }

    public static ArrayList<Disk> listAllStorage(Context context) {
        ArrayList<Disk> storages = new ArrayList<Disk>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);

            if (invokes != null) {
                Disk info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    info = new Disk(path);

                    Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                    String state = (String) getVolumeState.invoke(storageManager, info.path);
                    info.state = state;

                    Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
                    info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                    storages.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        storages.trimToSize();
        return storages;
    }*/

    // 内置的存储卡
    public static String getPrimaryExternalStorageDirectory() {
        return android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static Disk createDisk(File file, String description) {
        String path = file.getAbsolutePath();
        long totalSize = getTotalSize(path);
        long freeSpace = getAvailableSize(path);
        Disk disk = new Disk(file.getAbsolutePath(), description, totalSize, freeSpace);
        return disk;
    }

    public static long getTotalSize(String path) {
        StatFs fileStats = new StatFs(path);
        fileStats.restat(path);
        return fileStats.getBlockCountLong() * fileStats.getBlockSizeLong();
    }

    public static long getAvailableSize(String path) {
        StatFs fileStats = new StatFs(path);
        fileStats.restat(path);
        return fileStats.getAvailableBlocksLong() * fileStats.getBlockSizeLong();
    }
}
