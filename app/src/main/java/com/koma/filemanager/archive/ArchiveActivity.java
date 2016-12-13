package com.koma.filemanager.archive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseSwipeBackActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by koma on 12/12/16.
 */

public class ArchiveActivity extends BaseSwipeBackActivity {
    private static final String TAG = "ArchiveActivity";
    private ArchiveContract.Presenter mPresenter;
    private String mPath;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindString(R.string.uncompress_to)
    String mUnCompressToStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        String action = getIntent().getAction();
        // 检查action是否正确
        if (null == action || !TextUtils.equals(action, Intent.ACTION_VIEW)) {
            finish();
        }
        init();
    }

    private void init() {
        setSupportActionBar(mToolbar);
        ArchiveFragment archiveFragment = (ArchiveFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (archiveFragment == null) {
            archiveFragment = new ArchiveFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, archiveFragment).commit();
        }
        mPresenter = new ArchivePresenter(archiveFragment, FileRepository.getInstance());
        mPath = mPresenter.getPathFromUri(getIntent().getData());
        if (TextUtils.isEmpty(mPath)) {
            LogUtils.i(TAG, "the path is empty");
        }
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

    @Override
    protected int getLayoutId() {
        return R.layout.common_layout;
    }
}
