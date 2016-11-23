package com.koma.filemanager;

import android.app.Application;
import android.content.Context;

/**
 * Created by koma on 11/17/16.
 */

public class FilemanagerApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static synchronized Context getContext() {
        return sContext;
    }
}
