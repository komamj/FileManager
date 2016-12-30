package com.koma.filemanager.helper;

import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.koma.filemanager.base.BaseSelectableAdapter;

/**
 * Created by koma on 12/26/16.
 */

public class ActionModeHelper implements ActionMode.Callback {
    public static final String TAG = ActionModeHelper.class.getSimpleName();

    private int defaultMode = BaseSelectableAdapter.MODE_IDLE;
    @MenuRes
    private int mCabMenu;
    private BaseSelectableAdapter mAdapter;
    private ActionMode.Callback mCallback;
    protected ActionMode mActionMode;

    /**
     * Default constructor with internal callback.
     *
     * @param adapter the FlexibleAdapter instance
     * @param cabMenu the menu resourceId
     * @see #ActionModeHelper(BaseSelectableAdapter, int, ActionMode.Callback)
     */
    public ActionModeHelper(@NonNull BaseSelectableAdapter adapter, @MenuRes int cabMenu) {
        this.mAdapter = adapter;
        this.mCabMenu = cabMenu;
    }

    /**
     * Constructor with internal callback + custom callback.
     *
     * @param adapter  the FlexibleAdapter instance
     * @param cabMenu  the menu resourceId
     * @param callback the custom {@link android.support.v7.view.ActionMode.Callback}
     * @see #ActionModeHelper(BaseSelectableAdapter, int)
     */
    public ActionModeHelper(@NonNull BaseSelectableAdapter adapter, @MenuRes int cabMenu,
                            @NonNull ActionMode.Callback callback) {
        this(adapter, cabMenu);
        this.mCallback = callback;
    }

    /**
     * Changes the default mode to apply when the ActionMode is destroyed and normal selection is
     * again active.
     * <p>Default value is {@link BaseSelectableAdapter#MODE_IDLE}.</p>
     *
     * @param defaultMode the new default mode when ActionMode is off, accepted values:
     *                    {@code MODE_IDLE, MODE_SINGLE}
     * @return this object, so it can be chained
     */
    public final ActionModeHelper withDefaultMode(int defaultMode) {
        if (defaultMode == BaseSelectableAdapter.MODE_IDLE)
            this.defaultMode = defaultMode;
        return this;
    }

    /**
     * @return the current instance of the ActionMode, {@code null} if ActionMode is off.
     */
    public ActionMode getActionMode() {
        return mActionMode;
    }

    /**
     * Implements the basic behavior of a CAB and multi select behavior.
     *
     * @param position the current item position
     * @return true if selection is changed, false if the click event should ignore the ActionMode
     * and continue
     * @since 5.0.0-b6
     */
    public boolean onClick(int position) {
        if (position != RecyclerView.NO_POSITION) {
            toggleSelection(position);
            return true;
        }
        return false;
    }

    /**
     * Implements the basic behavior of a CAB and multi select behavior onLongClick.
     *
     * @param activity the current Activity
     * @param position the position of the clicked item
     * @return the initialized ActionMode or null if nothing was done
     */
    @NonNull
    public ActionMode onLongClick(AppCompatActivity activity, int position) {
        //Activate ActionMode
        if (mActionMode == null) {
            mActionMode = activity.startSupportActionMode(this);
        }
        //we have to select this on our own as we will consume the event
        toggleSelection(position);
        return mActionMode;
    }

    /**
     * Toggle the selection state of an item.
     * <p>If the item was the last one in the selection and is unselected, the selection is stopped.
     * Note that the selection must already be started (actionMode must not be null).</p>
     *
     * @param position position of the item to toggle the selection state
     */
    public void toggleSelection(int position) {
        if (position >= 0 && (mAdapter.getMode() == BaseSelectableAdapter.MODE_MULTI)) {
            mAdapter.toggleSelection(position);
        }
        //If MODE_SINGLE is active then ActionMode can be null
        if (mActionMode == null) return;

        int count = mAdapter.getSelectedItemCount();
        if (count == 0) {
            mActionMode.finish();
        } else {
            updateContextTitle(count);
        }
    }

    /**
     * Updates the title of the Context Menu.
     * <p>Override to customize the title and subtitle.</p>
     *
     * @param count the current number of selected items
     */
    public void updateContextTitle(int count) {
        if (mActionMode != null) {
            mActionMode.setTitle(String.valueOf(count));
        }
    }

    /**
     * Helper method to restart the action mode after a restoration of deleted items. The
     * ActionMode will be activated only if {@link BaseSelectableAdapter#getSelectedItemCount()}
     * has selection.
     * <p>To be called in the <i>onUndo</i> method after the restoration is done or in the
     * <i>onRestoreInstanceState</i>.</p>
     *
     * @param activity the current Activity
     */
    public void restoreSelection(AppCompatActivity activity) {
        if ((defaultMode == BaseSelectableAdapter.MODE_IDLE && mAdapter.getSelectedItemCount() > 0) ||
                (mAdapter.getSelectedItemCount() > 1)) {
            onLongClick(activity, -1);
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        //Inflate the Context Menu
        actionMode.getMenuInflater().inflate(mCabMenu, menu);
        //Activate the ActionMode Multi
        mAdapter.setMode(BaseSelectableAdapter.MODE_MULTI);
        //Notify the provided callback
        return mCallback == null || mCallback.onCreateActionMode(actionMode, menu);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return mCallback != null && mCallback.onPrepareActionMode(actionMode, menu);
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
        boolean consumed = false;
        if (mCallback != null) {
            consumed = mCallback.onActionItemClicked(actionMode, item);
        }
        if (!consumed) {
            //Finish the actionMode
            actionMode.finish();
        }
        return consumed;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        //Change mode and deselect everything
        mAdapter.setMode(defaultMode);
        mAdapter.clearSelection();
        mActionMode = null;
        //Notify the provided callback
        if (mCallback != null) {
            mCallback.onDestroyActionMode(actionMode);
        }
    }

    /**
     * Utility method to be called from Activity in many occasions such as: <i>onBackPressed</i>,
     * <i>onRefresh</i> for SwipeRefreshLayout, after <i>deleting</i> all selected items.
     *
     * @return true if ActionMode was active (in case it is also terminated), false otherwise
     */
    public boolean destroyActionModeIfCan() {
        if (mActionMode != null) {
            mActionMode.finish();
            return true;
        }
        return false;
    }
}
