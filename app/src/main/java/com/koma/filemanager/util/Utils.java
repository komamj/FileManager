package com.koma.filemanager.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by koma on 12/15/16.
 */

public class Utils {
    /**
     * Show Soft Keyboard with new Thread
     *
     * @param activity
     */
    public static void hideSoftInput(final Activity activity) {
        if (activity.getCurrentFocus() != null) {
            new Runnable() {
                public void run() {
                    InputMethodManager imm =
                            (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
            }.run();
        }
    }

    /**
     * Hide Soft Keyboard from Dialogs with new Thread
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputFrom(final Context context, final View view) {
        new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }.run();
    }

    /**
     * Show Soft Keyboard with new Thread
     *
     * @param context
     * @param view
     */
    public static void showSoftInput(final Context context, final View view) {
        new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm =
                        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }.run();
    }

    /**
     * Finds the layout orientation of the RecyclerView, no matter which LayoutManager is in use.
     *
     * @param layoutManager the LayoutManager instance in use by the RV
     * @return one of {@link OrientationHelper#HORIZONTAL}, {@link OrientationHelper#VERTICAL}
     */
    public static int getOrientation(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return OrientationHelper.HORIZONTAL;
    }
}
