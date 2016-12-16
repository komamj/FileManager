package com.koma.filemanager.base;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.koma.filemanager.R;
import com.koma.filemanager.helper.RxBus;
import com.koma.filemanager.helper.event.SelectEvent;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by koma on 2016/12/4.
 */

public abstract class BaseMenuActivity extends BaseSwipeBackActivity implements ActionMode.Callback {
    private static final String TAG = "BaseMenuActivity";
    protected SearchView mSearchView;
    protected MenuItem mSortMenu, mSearchItem, mMoreMenu, mSelectMenu, mShareMenu, mCutMenu,
            mDeleteMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    protected ActionMode mActionMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private Subscription subscribeEvents() {
        return RxBus.getInstance().toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof SelectEvent) {
                            LogUtils.i(TAG, "SelectEvent");
                            if (mActionMode != null) {
                                return;
                            }
                            startActionMode();
                        }
                    }
                })
                .subscribe(RxBus.defaultSubscriber());
    }

    private void startActionMode() {
        startSupportActionMode(this);
    }

    private void init() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(mNavigationListener);
        addSubscription(subscribeEvents());
    }

    private View.OnClickListener mNavigationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LogUtils.i(TAG, "Navigation is clicked ");
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        LogUtils.i(TAG, "onCreateOptionsMenu called!");
        getMenuInflater().inflate(R.menu.common_menu, menu);
        mSortMenu = menu.findItem(R.id.action_sort);
        initSearchView(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        LogUtils.i(TAG, "onPrepareOptionsMenu called!");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.i(TAG, "onOptionsItemSelected called!");
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case R.id.action_sort:
                showSortPopUpMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSortPopUpMenu() {
        PopupMenu popup = new PopupMenu(mContext, findViewById(R.id.action_sort));
        popup.getMenuInflater().inflate(R.menu.menu_sort, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sort_by_type:
                        sortByType();
                        break;
                    case R.id.sort_by_name:
                        sortByName();
                        break;
                    case R.id.sort_by_size:
                        sortBySize();
                        break;
                    case R.id.sort_by_date:
                        sortByDate();
                        break;
                    default:
                }
                return true;
            }
        });

        popup.show();
    }

    private void initSearchView(final Menu menu) {
        //Associate searchable configuration with the SearchView
        LogUtils.i(TAG, "onCreateOptionsMenu setup SearchView!");
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchItem = menu.findItem(R.id.action_search);
        if (mSearchItem != null) {
            MenuItemCompat.setOnActionExpandListener(
                    mSearchItem, new MenuItemCompat.OnActionExpandListener() {
                        @Override
                        public boolean onMenuItemActionExpand(MenuItem item) {
                            LogUtils.i(TAG, "onMenuItemActionExpand");
                            if (mSortMenu != null) {
                                mSortMenu.setVisible(false);
                            }
                            if (mMoreMenu != null) {
                                mMoreMenu.setVisible(false);
                            }
                            return true;
                        }

                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem item) {
                            LogUtils.i(TAG, "onMenuItemActionCollapse");
                            if (mSortMenu != null) {
                                mSortMenu.setVisible(true);
                            }
                            if (mMoreMenu != null) {
                                mMoreMenu.setVisible(true);
                            }
                            return true;
                        }
                    });
            mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
            mSearchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
            mSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_FULLSCREEN);
            mSearchView.setQueryHint(getString(R.string.action_search));
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            //mSearchView.setOnQueryTextListener(this);
        }
    }

    @Override
    public void onBackPressed() {
        //If SearchView is visible, back key cancels search and iconify it
        if (mSearchView != null && !mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }

    protected abstract void sortByType();

    protected abstract void sortByName();

    protected abstract void sortBySize();

    protected abstract void sortByDate();

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        LogUtils.i(TAG, "onCreateActionMode");
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.select_mode_menu, menu);
        mSelectMenu = menu.findItem(R.id.action_select_all);
        mSelectMenu.setEnabled(false);
        mShareMenu = menu.findItem(R.id.action_share);
        mCutMenu = menu.findItem(R.id.menu_action_cut);
        mDeleteMenu = menu.findItem(R.id.action_delete);
        mMoreMenu = menu.findItem(R.id.action_more);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        LogUtils.i(TAG, "onPrepareActionMode");

        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        LogUtils.i(TAG, "onActionItemClicked");
        switch (item.getItemId()) {
            case R.id.action_select_all:
                mode.finish();
                break;
            case R.id.action_share:
                mode.finish();
                break;
            case R.id.action_delete:
                mode.finish();
                break;
            case R.id.action_more:
                mode.finish();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        LogUtils.i(TAG, "onDestroyActionMode");
    }
}
