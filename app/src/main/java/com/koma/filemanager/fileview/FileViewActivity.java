package com.koma.filemanager.fileview;

import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseSwipeBackActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.helper.BusProvider;
import com.koma.filemanager.helper.event.PathEvent;
import com.koma.filemanager.util.Constants;
import com.koma.filemanager.util.LogUtils;
import com.squareup.otto.Subscribe;

import butterknife.BindView;

/**
 * Created by koma on 11/30/16.
 */

public class FileViewActivity extends BaseSwipeBackActivity {
    private static final String TAG = "FileViewActivity";
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    FileViewPresenter mPresenter;
    private String mPath;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
        setSupportActionBar(mToolBar);// 传递默认路径，如果没有，则以默认的内置存储卡的地址
        if (getIntent().getExtras() != null) {
            mPath = getIntent().getStringExtra(Constants.EXTRA_FILE_DIRECTORY);
            LogUtils.i(TAG, "init mPath : " + mPath);
        }
        FileViewFragment fileViewFragment = (FileViewFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (fileViewFragment == null) {
            fileViewFragment = FileViewFragment.newInstance(mPath);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fileViewFragment).commit();
        }
        mPresenter = new FileViewPresenter(fileViewFragment, FileRepository.getInstance());
        mPresenter.subscribe();
    }


    public String getPath() {
        return mPath;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.file_view_activity;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.i(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }
}
