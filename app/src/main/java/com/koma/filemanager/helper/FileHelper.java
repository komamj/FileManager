package com.koma.filemanager.helper;


import com.koma.filemanager.base.BaseFile;
import com.koma.filemanager.util.LogUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by koma on 12/2/16.
 */

public class FileHelper {
    private static final String TAG = "FileHelper";
    public static final String ROOT_DIRECTORY = "/";
    private static final String[] COMPRESSED_TAR = {
            "tar.gz", "tar.bz2", "tar.lzma"
    };
    public static final char[] LS_CHAR_LIMIT = {
            '#', '$', '(', ')', '&', '\'', ';', '{', '}', '\"', '~', '=', '`', ' ', '['
    };

    private static String[] SysFileDirs = new String[]{
            "miren_browser/imagecaches"
    };

    // 内置的存储卡
    public static String getPrimaryExternalStorageDirectory() {
        return android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static boolean isRootDirectory(BaseFile baseFile) {
        if (baseFile.getFileName() == null) {
            return true;
        }
        return baseFile.getFileName().compareTo(FileHelper.ROOT_DIRECTORY) == 0;
    }

    public static boolean isParentRootDirectory(BaseFile baseFile) {
        if (baseFile.getParent() == null) {
            return true;
        }
        return baseFile.getParent().compareTo(FileHelper.ROOT_DIRECTORY) == 0;
    }

    public static BaseFile createBaseFile(File file) {
        try {
            if (!file.exists()) {
                return null;
            }
            Date lastModified = new Date(file.lastModified());
            if (file.isDirectory()) {
                return new BaseFile(file.getName(), file.getParent(), 0, lastModified, true);
            } else {
                return new BaseFile(file.getName(), file.getParent(), file.length(), lastModified, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "create BaseFile error : " + e.toString());
        }
        return null;
    }

    public static String getExtension(BaseFile baseFile) {
        return getExtension(baseFile.getFileName());
    }

    public static String getExtension(String name) {
        final char dot = '.';
        int pos = name.lastIndexOf(dot);
        if (pos == -1 || pos == 0) { // Hidden files doesn't have extensions
            return null;
        }

        // Exceptions to the general extraction method
        int cc = COMPRESSED_TAR.length;
        for (int i = 0; i < cc; i++) {
            if (name.endsWith("." + COMPRESSED_TAR[i])) {
                return COMPRESSED_TAR[i];
            }
        }

        // General extraction method
        return name.substring(pos + 1);
    }

    public static int getSubFilesCount(File source) {
        try {
            return getSubFilesCountWithCmd(source.getCanonicalPath());
        } catch (Exception e) {
            e.printStackTrace();
            return getSubFilesCountWithListFiles(source);
        }
    }

    private static int getSubFilesCountWithCmd(String path) throws IOException,
            NumberFormatException {
        String[] dirExc = {
                "sh", "-c", "ls " + addEscapForPath(path) + " -l | grep ^[-d] | wc -l"
        };
        Integer num = Integer.valueOf(executeCommand(dirExc));
        return num.intValue();
    }

    private static String executeCommand(String[] command) {
        StringBuffer output = new StringBuffer();
        Process process = null;
        BufferedReader reader = null;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output.toString();
    }


    private static String addEscapForPath(String path) {
        StringBuilder sb = new StringBuilder(path.length());
        int queryLength = path.length();
        for (int i = 0; i < queryLength; ++i) {
            char ch = path.charAt(i);
            if (isShellExpChar(ch)) {
                sb.append("\\" + ch);
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    private static boolean isShellExpChar(char ch) {
        boolean result = false;
        for (int i = 0; i < LS_CHAR_LIMIT.length; i++) {
            if (ch == LS_CHAR_LIMIT[i]) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static int getSubFilesCountWithListFiles(File source) {
        Integer fileNum = Integer.valueOf(0);

        for (File file : source.listFiles()) {
            if (!shouldShowFile(file)) {
                continue;
            }
            fileNum++;
        }
        return fileNum.intValue();
    }


    public static boolean shouldShowFile(File file) {
        if (file.isHidden()) {
            return false;
        }
        if (file.getName().startsWith(".")) {
            return false;
        }
        String sdFolder = getPrimaryExternalStorageDirectory();
        for (String s : SysFileDirs) {
            if (file.getPath().startsWith(makePath(sdFolder, s))) {
                return false;
            }
        }

        return true;
    }

    public static String makePath(String path1, String path2) {
        if (path1.endsWith(File.separator)) {
            return path1 + path2;
        }
        return path1 + File.separator + path2;
    }
}
