package com.koma.filemanager.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.koma.filemanager.R;
import com.koma.filemanager.widget.FastScroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by koma on 12/26/16.
 */

public abstract class BaseSelectableAdapter extends RecyclerView.Adapter
        implements FastScroller.BubbleTextCreator, FastScroller.OnScrollStateChangeListener {
    private static final String TAG = BaseSelectableAdapter.class.getSimpleName();
    private Set<Integer> mSelectedPositions;
    private int mMode;
    public static final int MODE_IDLE = 0, MODE_MULTI = 2;
    protected RecyclerView mRecyclerView;
    protected FastScroller mFastScroller;

    /**
     * ActionMode selection flag SelectAll.
     * <p>Used when user click on selectAll action button in ActionMode.</p>
     */
    protected boolean mSelectAll = false;

    public BaseSelectableAdapter() {
        mSelectedPositions = new TreeSet<>();
        mMode = MODE_IDLE;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    /**
     * {@inheritDoc}
     *
     * @since 5.0.0-b6
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    /**
     * @return the RecyclerView instance
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * Helper method to return the number of the columns (span count) of the given LayoutManager.
     * <p>All Layouts are supported.</p>
     *
     * @param layoutManager the layout manager to check
     * @return the span count
     */
    public static int getSpanCount(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return 1;
    }

    /**
     * Sets the mode of the selection:
     * <ul>
     * <li>{@link #MODE_IDLE} Default. Configures the adapter so that no item can be selected;
     * (previous selection is cleared automatically);
     * <li>{@link #MODE_MULTI} configures the adapter to save the position to the list of the
     * selected items.
     * </ul>
     *
     * @param mode one of {@link #MODE_IDLE}, {@link #MODE_MULTI}
     */
    public void setMode(int mode) {
        if (mode == MODE_IDLE) {
            clearSelection();
        }
        this.mMode = mode;
    }

    public int getMode() {
        return mMode;
    }

    public boolean isSelectAll() {
        return mSelectAll;
    }

    public void resetActionModeFlags() {
        this.mSelectAll = false;
    }

    public boolean isSelected(int position) {
        return mSelectedPositions.contains(position);
    }

    /**
     * Checks if the current item has the property {@code selectable = true}.
     *
     * @param position the current position of the item to check
     * @return true if the item property </i>selectable</i> is true, false otherwise
     */
    public abstract boolean isSelectable(int position);

    /**
     * Toggles the selection status of the item at a given position.
     * <p>The behaviour depends on the selection mode previously set with {@link #setMode(int)}.</p>
     * The Activated State of the ItemView is automatically set in
     * <p><b>Usage:</b>
     * <ul>
     * <li>If you don't want any item to be selected/activated at all, just don't call this method.</li>
     * <li>To have actually the item visually selected you need to add a custom <i>Selector Drawable</i>
     * to your layout/view of the Item. It's preferable to set in your layout:
     * <i>android:background="?attr/selectableItemBackground"</i>, pointing to a custom Drawable
     * in the style.xml (note: prefix <i>?android:attr</i> <u>doesn't</u> work).</li>
     * <li>In <i>bindViewHolder</i>, adjust the selection status:
     * <i>holder.itemView.setActivated(isSelected(position));</i></li>
     * </ul></p>
     *
     * @param position Position of the item to toggle the selection status for.
     */
    public void toggleSelection(int position) {
        if (position < 0) return;
        boolean contains = mSelectedPositions.contains(position);
        if (contains) {
            removeSelection(position);
        } else {
            addSelection(position);
        }
    }

    /**
     * Adds the selection status for the given position without notifying the change.
     *
     * @param position Position of the item to add the selection status for.
     * @return true if the set is modified, false otherwise or position is not currently selectable
     * @see #isSelectable(int)
     */
    public boolean addSelection(int position) {
        return isSelectable(position) && mSelectedPositions.add(position);
    }

    /**
     * Removes the selection status for the given position without notifying the change.
     *
     * @param position Position of the item to remove the selection status for.
     * @return true if the set is modified, false otherwise
     */
    public boolean removeSelection(int position) {
        return mSelectedPositions.remove(position);
    }

    /**
     * Helper method to easily swap selection between 2 positions only if one of the positions
     * is <i>not</i> selected.
     *
     * @param fromPosition first position
     * @param toPosition   second position
     */
    protected void swapSelection(int fromPosition, int toPosition) {
        if (isSelected(fromPosition) && !isSelected(toPosition)) {
            removeSelection(fromPosition);
            addSelection(toPosition);
        } else if (!isSelected(fromPosition) && isSelected(toPosition)) {
            removeSelection(toPosition);
            addSelection(fromPosition);
        }
    }

    /**
     * Sets the selection status for all items which the ViewTypes are included in the specified array.
     * <p><b>Note:</b> All items are invalidated and rebound!</p>
     *
     * @param viewTypes The ViewTypes for which we want the selection, pass nothing to select all
     * @since 1.0.0
     */
    public void selectAll(Integer... viewTypes) {
        mSelectAll = true;
        List<Integer> viewTypesToSelect = Arrays.asList(viewTypes);
        int positionStart = 0, itemCount = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (isSelectable(i) &&
                    (viewTypesToSelect.isEmpty() || viewTypesToSelect.contains(getItemViewType(i)))) {
                mSelectedPositions.add(i);
                itemCount++;
            } else {
                //Optimization for ItemRangeChanged
                if (positionStart + itemCount == i) {
                    notifySelectionChanged(positionStart, itemCount);
                    itemCount = 0;
                    positionStart = i;
                }
            }
        }
        notifySelectionChanged(positionStart, getItemCount());
    }

    /**
     * Clears the selection status for all items one by one and it doesn't stop animations in the items.
     * <br/><br/>
     * <b>Note 1:</b> Items are invalidated and rebound!<br/>
     * <b>Note 2:</b> This method use java.util.Iterator to avoid java.util.ConcurrentModificationException.
     *
     * @since 1.0.0
     */
    public void clearSelection() {
        Iterator<Integer> iterator = mSelectedPositions.iterator();
        int positionStart = 0, itemCount = 0;
        //The notification is done only on items that are currently selected.
        while (iterator.hasNext()) {
            int position = iterator.next();
            iterator.remove();
            //Optimization for ItemRangeChanged
            if (positionStart + itemCount == position) {
                itemCount++;
            } else {
                //Notify previous items in range
                notifySelectionChanged(positionStart, itemCount);
                positionStart = position;
                itemCount = 1;
            }
        }
        //Notify remaining items in range
        notifySelectionChanged(positionStart, itemCount);
    }

    private void notifySelectionChanged(int positionStart, int itemCount) {
        if (itemCount > 0) notifyItemRangeChanged(positionStart, itemCount);
    }

    /**
     * Counts the selected items.
     *
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return mSelectedPositions.size();
    }

    /**
     * Retrieves the list of selected items.
     * <p>The list is a copy and it's sorted.</p>
     *
     * @return A copied List of selected items ids from the Set
     */
    public List<Integer> getSelectedPositions() {
        return new ArrayList<>(mSelectedPositions);
    }

    /**
     * Saves the state of the current selection on the items.
     *
     * @param outState Current state
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(TAG, new ArrayList<>(mSelectedPositions));
    }

    /**
     * Restores the previous state of the selection on the items.
     *
     * @param savedInstanceState Previous state
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mSelectedPositions.addAll(savedInstanceState.getIntegerArrayList(TAG));
        Log.d(TAG, "Restore selection " + mSelectedPositions);
    }

	/*---------------*/
    /* FAST SCROLLER */
    /*---------------*/

    /**
     * Displays or Hides the {@link FastScroller} if previously configured.
     *
     * @see #setFastScroller(FastScroller, int)
     */
    public void toggleFastScroller() {
        if (mFastScroller != null) {
            if (mFastScroller.getVisibility() != View.VISIBLE)
                mFastScroller.setVisibility(View.VISIBLE);
            else mFastScroller.setVisibility(View.GONE);
        }
    }

    /**
     * @return true if {@link FastScroller} is configured and shown, false otherwise
     * @since 5.0.0-b1
     */
    public boolean isFastScrollerEnabled() {
        return mFastScroller != null && mFastScroller.getVisibility() == View.VISIBLE;
    }

    /**
     * @return the current instance of the {@link FastScroller} object
     */
    public FastScroller getFastScroller() {
        return mFastScroller;
    }

    /**
     * Convenience method of {@link #setFastScroller(FastScroller, int, FastScroller.OnScrollStateChangeListener)}.
     * <p><b>IMPORTANT:</b> Call this method after the adapter is added to the RecyclerView.</p>
     *
     * @see #setFastScroller(FastScroller, int, FastScroller.OnScrollStateChangeListener)
     * @since 5.0.0-b1
     */
    public void setFastScroller(@NonNull FastScroller fastScroller, int accentColor) {
        setFastScroller(fastScroller, accentColor, null);
    }

    /**
     * Sets up the {@link FastScroller} with automatic fetch of accent color.
     * <p><b>IMPORTANT:</b> Call this method after the adapter is added to the RecyclerView.</p>
     * <b>NOTE:</b> If the device has at least Lollipop, the Accent color is fetched, otherwise
     * for previous version, the default value is used.
     *
     * @param fastScroller        instance of {@link FastScroller}
     * @param accentColor         the default value color if the accentColor cannot be fetched
     * @param stateChangeListener the listener to monitor when fast scrolling state changes
     */
    public void setFastScroller(@NonNull FastScroller fastScroller, int accentColor,
                                FastScroller.OnScrollStateChangeListener stateChangeListener) {
        if (mRecyclerView == null) {
            throw new IllegalStateException("RecyclerView cannot be null. Setup FastScroller after the Adapter has been added to the RecyclerView.");
        } else if (fastScroller == null) {
            throw new IllegalArgumentException("FastScroller cannot be null. Review the widget ID of the FastScroller.");
        }
        mFastScroller = fastScroller;
        mFastScroller.setRecyclerView(mRecyclerView);
        mFastScroller.addOnScrollStateChangeListener(stateChangeListener);
        mFastScroller.setViewsToUse(
                R.layout.library_fast_scroller_layout,
                R.id.fast_scroller_bubble,
                R.id.fast_scroller_handle, accentColor);
    }

    /**
     * @param position the position of the handle
     * @return the value of the item, default value is: position + 1
     */
    @Override
    public String onCreateBubbleText(int position) {
        return String.valueOf(position + 1);
    }

    /**
     * Triggered when FastScroller State is changed.
     *
     * @param scrolling true if the user is actively scrolling, false when idle
     */
    @Override
    public void onFastScrollerStateChange(boolean scrolling) {
        //nothing
    }

}
