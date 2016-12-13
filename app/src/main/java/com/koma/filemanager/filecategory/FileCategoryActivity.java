package com.koma.filemanager.filecategory;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseMenuActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.data.model.ZipFile;
import com.koma.filemanager.util.Constants;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by koma on 12/12/16.
 */

public class FileCategoryActivity extends BaseMenuActivity {
    private static final String TAG = "FileCategoryActivity";
    private String mCategory;
    private FileCategoryContract.Presenter mPresenter;
    private FileCategoryAdapter mAdapter;
    private ArrayList<ZipFile> mData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        if (getIntent().getExtras() != null) {
            mCategory = getIntent().getStringExtra(Constants.FILE_CATEGORY);
        }
        if (mCategory == null) {
            finish();
        }
        LogUtils.i(TAG, "mCategory : " + mCategory);
        init();
    }

    private void init() {
        FileCategoryFragment fileCategoryFragment = (FileCategoryFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (fileCategoryFragment == null) {
            fileCategoryFragment = new FileCategoryFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fileCategoryFragment).commit();
        }
        mPresenter = new FileCategoryPresenter(fileCategoryFragment, FileRepository.getInstance());
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
        if (TextUtils.equals(mCategory, "zip")) {
            if (mPresenter != null) {
                mPresenter.getZipFiles();
            }
        }
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
    protected void sortByType() {
        LogUtils.i(TAG, "sortbyType");
    }

    @Override
    protected void sortByName() {
        LogUtils.i(TAG, "sortByName");
    }

    @Override
    protected void sortBySize() {
        LogUtils.i(TAG, "sortBySize");
    }

    @Override
    protected void sortByDate() {
        LogUtils.i(TAG, "sortByDate");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.category_activity;
    }
}
