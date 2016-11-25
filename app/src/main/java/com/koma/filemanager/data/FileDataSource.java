package com.koma.filemanager.data;

import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.data.model.ImageFile;
import com.koma.filemanager.data.model.VideoFile;

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

    Observable<ArrayList<AudioFile>> getAudioFiles();

    Observable<ArrayList<ImageFile>> getImageFiles();

    Observable<ArrayList<VideoFile>> getVideoFiles();
}
