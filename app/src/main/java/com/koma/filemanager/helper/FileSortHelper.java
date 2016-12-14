package com.koma.filemanager.helper;

import com.koma.filemanager.base.BaseFile;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by koma on 12/14/16.
 */

public class FileSortHelper {
    public enum SortKey {
        type, name, size, date
    }

    public enum SortMethod {
        asc, desc
    }

    public static void sortBaseFile(List<BaseFile> files, final SortKey sortKey,
                                    final SortMethod sortMethod) {
        Collections.sort(files, new Comparator<BaseFile>() {
            @Override
            public int compare(BaseFile fileL, BaseFile fileR) {
                // 排序规则，文件夹放前面，文件放后面
                boolean isLhsDirectory = fileL.getIsDirectory();
                boolean isRhsDirectory = fileR.getIsDirectory();
                if (isLhsDirectory || isRhsDirectory) {
                    if (isLhsDirectory && isRhsDirectory) {
                        return doCompare(fileL, fileR, sortKey, sortMethod);
                    }
                    return (isLhsDirectory) ? -1 : 1;
                }
                return doCompare(fileL, fileR, sortKey, sortMethod);
            }
        });
    }

    public static int doCompare(final BaseFile file1, final BaseFile file2,
                                SortKey sortKey,
                                SortMethod sortMethod) {
        int sortFactor = 1;// 先确定是升序，还是降序
        if (sortMethod == SortMethod.desc) {
            sortFactor = -1;
        }
        if (sortKey == SortKey.name) {
            return Collator.getInstance(java.util.Locale.CHINA).compare(file1.getFileName(),
                    file2.getFileName()) * sortFactor;
        }
        if (sortKey == SortKey.date) {
            return file1.getFileModifiedTime().compareTo(file2.getFileModifiedTime()) * sortFactor;
        }
        if (sortKey == SortKey.size) {
            return Long.compare(file1.getFileSize(), file2.getFileSize()) * sortFactor;
        }
        return file1.compareTo(file2);
    }
}
