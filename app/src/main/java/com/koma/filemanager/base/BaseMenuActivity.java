package com.koma.filemanager.base;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.koma.filemanager.R;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindView;

/**
 * Created by koma on 2016/12/4.
 */

public abstract class BaseMenuActivity extends BaseSwipeBackActivity {
    private static final String TAG = "BaseMenuActivity";
    protected SearchView mSearchView;
    protected MenuItem mSortMenu, mSearchItem, mMoreMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(mNavigationListener);
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
        getMenuInflater().inflate(R.menu.common_menu, menu);
        mSortMenu = menu.findItem(R.id.action_sort);
        mMoreMenu = menu.findItem(R.id.action_more);
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

    protected abstract void sortByType();

    protected abstract void sortByName();

    protected abstract void sortBySize();

    protected abstract void sortByDate();
}
