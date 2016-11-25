package com.koma.filemanager.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseActivity;
import com.koma.filemanager.util.FileCategoryUtils;
import com.koma.filemanager.util.LogUtils;
import com.koma.filemanager.widget.CategoryButton;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {
    private static final String TAG = "MainActivity";

    private MainContract.Presenter mPrenter;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindViews({R.id.audio_category, R.id.video_category, R.id.image_category,
            R.id.document_category, R.id.zip_category, R.id.apk_category})
    List<CategoryButton> mCategoryButtons;

    @OnClick({R.id.audio_category, R.id.video_category, R.id.image_category,
            R.id.document_category, R.id.zip_category, R.id.apk_category})
    void launchCategoryActivity(View view) {
        switch (view.getId()) {
            case R.id.audio_category:
                LogUtils.i(TAG, "launch AudioActivity");
                if (mPrenter != null) {
                    mPrenter.launchCategoryActivity(R.id.audio_category);
                }
                break;
            case R.id.video_category:
                LogUtils.i(TAG, "launch VideoActivity");
                break;
            case R.id.image_category:
                LogUtils.i(TAG, "launch ImageActivity");
                break;
            case R.id.document_category:
                LogUtils.i(TAG, "launch DocumentActivity");
                break;
            case R.id.zip_category:
                LogUtils.i(TAG, "launch ZipActivity");
                break;
            case R.id.apk_category:
                LogUtils.i(TAG, "launch ApkActivity");
                break;
            default:
                LogUtils.i(TAG, "default");
        }
    }

    @OnClick(R.id.fab)
    void showAction() {
        Snackbar.make(mFab, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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

        MainPresenter prenter = new MainPresenter(MainActivity.this, this);
    }

    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
        if (mPrenter != null) {
            mPrenter.subscribe();
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
        if (mPrenter != null) {
            mPrenter.unSubscribe();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
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
        mPrenter = presenter;
    }

    @Override
    public void refreshAudioCounts(String count) {
        LogUtils.i(TAG, "refreshAudioCounts count: " + count);
        mCategoryButtons.get(0).setCountText(Integer.parseInt(count));
    }

    @Override
    public void refreshVideoCounts(int count) {
        LogUtils.i(TAG, "refreshVideoCounts count: " + count);
        //mCategoryButtons.get(1).setCountText(count);
    }

    @Override
    public void refreshImageCounts(int count) {
        LogUtils.i(TAG, "refreshImageCounts count:" + count);
        mCategoryButtons.get(2).setCountText(count);
    }

    @Override
    public void refreshDocumentCounts(int count) {
        LogUtils.i(TAG, "refreshDocumentCounts count: " + count);
        mCategoryButtons.get(3).setCountText(count);
    }

    @Override
    public void refreshZipCounts(int count) {
        LogUtils.i(TAG, "refreshZipCounts count: " + count);
        mCategoryButtons.get(4).setCountText(count);
    }

    @Override
    public void refreshApkCounts(int count) {
        LogUtils.i(TAG, "refreshApkCounts count: " + count);
        mCategoryButtons.get(5).setCountText(count);
    }
}
