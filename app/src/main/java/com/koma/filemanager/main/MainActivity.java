package com.koma.filemanager.main;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.data.model.Disk;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;
import com.koma.filemanager.widget.CategoryButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {
    private static final String TAG = "MainActivity";

    private MainPresenter mPresenter;
    private DiskAdapter mAdapter;
    private ArrayList<Disk> mData;
    @BindView(R.id.volume_info_recyclerview)
    RecyclerView mVolumeInfoRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    private MenuItem mSearchItem;
    private SearchView mSearchView;
    @BindViews({R.id.audio_category, R.id.video_category, R.id.image_category,
            R.id.document_category, R.id.zip_category, R.id.apk_category})
    List<CategoryButton> mCategoryButtons;

    @OnClick({R.id.audio_category, R.id.video_category, R.id.image_category,
            R.id.document_category, R.id.zip_category, R.id.apk_category})
    void launchCategoryActivity(View view) {
        switch (view.getId()) {
            case R.id.audio_category:
                LogUtils.i(TAG, "launch AudioActivity");
                if (mPresenter != null) {
                    mPresenter.launchCategoryActivity(R.id.audio_category);
                }
                break;
            case R.id.video_category:
                if (mPresenter != null) {
                    //mPresenter.launchCategoryActivity(R.id.video_category);
                }
                break;
            case R.id.image_category:
                if (mPresenter != null) {
                    //mPresenter.launchCategoryActivity(R.id.image_category);
                }
                break;
            case R.id.document_category:
                if (mPresenter != null) {
                    // mPresenter.launchCategoryActivity(R.id.document_category);
                }
                break;
            case R.id.zip_category:
                if (mPresenter != null) {
                    mPresenter.launchCategoryActivity(R.id.zip_category);
                }
                break;
            case R.id.apk_category:
                if (mPresenter != null) {
                    //mPresenter.launchCategoryActivity(R.id.apk_category);
                }
                break;
            default:
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
        setSupportActionBar(mToolBar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        new MainPresenter(MainActivity.this, this, FileRepository.getInstance());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mVolumeInfoRecyclerView.setLayoutManager(linearLayoutManager);
        mData = new ArrayList<>();
        mAdapter = new DiskAdapter(mContext, mData);
        mVolumeInfoRecyclerView.setAdapter(mAdapter);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        filter.addDataScheme(ContentResolver.SCHEME_FILE);
        registerReceiver(mReceiver, filter);
        getContentResolver().registerContentObserver(FileCategoryUtils.getAudioUri(), true, mAudioObserver);
        LogUtils.i(TAG, "-----------" + FileCategoryUtils.getAudioUri());
        getContentResolver().registerContentObserver(FileCategoryUtils.getVideoUri(), true, mVideoObserver);
        LogUtils.i(TAG, "-----------" + FileCategoryUtils.getVideoUri());
        getContentResolver().registerContentObserver(FileCategoryUtils.getImageUri(), true, mImageObserver);
        LogUtils.i(TAG, "-----------" + FileCategoryUtils.getImageUri());
        getContentResolver().registerContentObserver(FileCategoryUtils.getFileUri(), true, mFileObserver);
        LogUtils.i(TAG, "-----------" + FileCategoryUtils.getFileUri());
    }

    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
    }

    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
    }

    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
        unregisterReceiver(mReceiver);
        getContentResolver().unregisterContentObserver(mAudioObserver);
        getContentResolver().unregisterContentObserver(mVideoObserver);
        getContentResolver().unregisterContentObserver(mImageObserver);
        getContentResolver().unregisterContentObserver(mFileObserver);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)) {
                LogUtils.i(TAG, "onReceive action : " + action);
                if (mPresenter != null) {
                    mPresenter.getDisks();
                }
            }
        }
    };

    private final ContentObserver mAudioObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "Audio uri change so refresh");
            if (mPresenter != null) {
                mPresenter.getAudioCounts();
            }
        }
    };

    private final ContentObserver mVideoObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "Video uri change so refresh");
            if (mPresenter != null) {
                mPresenter.getVideoCounts();
            }
        }
    };

    private final ContentObserver mImageObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "Image uri change so refresh");
            if (mPresenter != null) {
                mPresenter.getImageCounts();
            }
        }
    };

    private final ContentObserver mFileObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            LogUtils.i(TAG, "File uri change so refresh");
            if (mPresenter != null) {
                mPresenter.getDocumentCounts();
                mPresenter.getZipCounts();
                mPresenter.getApkCounts();
            }
        }
    };

    @Override
    public void onBackPressed() {
        //If SearchView is visible, back key cancels search and iconify it
        if (mSearchView != null && !mSearchView.isIconified()) {
            mSearchView.setIconified(true);
            return;
        }
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        initSearchView(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

                            return true;
                        }

                        @Override
                        public boolean onMenuItemActionCollapse(MenuItem item) {
                            LogUtils.i(TAG, "onMenuItemActionCollapse");

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setPresenter(@NonNull MainPresenter presenter) {
        LogUtils.i(TAG, "setPresenter");
        mPresenter = presenter;
    }

    @Override
    public void refreshAudioCounts(String count) {
        LogUtils.i(TAG, "refreshAudioCounts count: " + count);
        mCategoryButtons.get(0).setCountText(count);
    }

    @Override
    public void refreshVideoCounts(String count) {
        LogUtils.i(TAG, "refreshVideoCounts count: " + count);
        mCategoryButtons.get(1).setCountText(count);
    }

    @Override
    public void refreshImageCounts(String count) {
        LogUtils.i(TAG, "refreshImageCounts count:" + count);
        mCategoryButtons.get(2).setCountText(count);
    }

    @Override
    public void refreshDocumentCounts(String count) {
        LogUtils.i(TAG, "refreshDocumentCounts count: " + count);
        mCategoryButtons.get(3).setCountText(count);
    }

    @Override
    public void refreshZipCounts(String count) {
        LogUtils.i(TAG, "refreshZipCounts count: " + count);
        mCategoryButtons.get(4).setCountText(count);
    }

    @Override
    public void refreshApkCounts(String count) {
        LogUtils.i(TAG, "refreshApkCounts count: " + count);
        mCategoryButtons.get(5).setCountText(count);
    }

    @Override
    public void refreshAdapter(ArrayList<Disk> disks) {
        LogUtils.i(TAG, "refreshAdapter");
        for (int i = 0; i < disks.size(); i++)
            LogUtils.i(TAG, "refreshAdapter disks :" + disks.get(i).getDescription());
        if (mAdapter != null) {
            mAdapter.setData(disks);
        }
    }
}
