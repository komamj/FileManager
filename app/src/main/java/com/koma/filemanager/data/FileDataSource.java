package com.koma.filemanager.data;

import com.koma.filemanager.data.model.ApkFile;
import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.data.model.Disk;
import com.koma.filemanager.data.model.DocumentFile;
import com.koma.filemanager.data.model.ImageFile;
import com.koma.filemanager.data.model.VideoFile;
import com.koma.filemanager.data.model.ZipFile;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by koma on 11/25/16.
 */

public interface FileDataSource {
    Observable<String> getAuidoCounts();

    Observable<String> getVideoCounts();

    Observable<String> getImageCounts();

    Observable<String> getDocumentsCounts();

    Observable<String> getZipCounts();

    Observable<String> getApkCounts();

    Observable<ArrayList<Disk>> getDisks();

    Observable<ArrayList<AudioFile>> getAudioFiles();

    Observable<ArrayList<ImageFile>> getImageFiles();

    Observable<ArrayList<VideoFile>> getVideoFiles();

    Observable<ArrayList<DocumentFile>> getDocumentFiles();

    Observable<ArrayList<ZipFile>> getZipFiles();

    Observable<ArrayList<ApkFile>> getApkFiles();

}
