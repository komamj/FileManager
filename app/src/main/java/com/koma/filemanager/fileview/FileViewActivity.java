package com.koma.filemanager.fileview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseMenuActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.helper.FileSortHelper;
import com.koma.filemanager.helper.RxBus;
import com.koma.filemanager.helper.event.SortEvent;
import com.koma.filemanager.util.Constants;
import com.koma.filemanager.util.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by koma on 11/30/16.
 */

public class FileViewActivity extends BaseMenuActivity {
    private static final String TAG = "FileViewActivity";
    FileViewPresenter mPresenter;
    private String mPath;
    @BindView(R.id.new_file)
    FloatingActionButton mNewFileBtn;

    @OnClick(R.id.new_file)
    public void newFile() {
        LogUtils.i(TAG, "newFile");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
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
    protected void sortByType() {
        LogUtils.i(TAG, "sortByType");
        RxBus.getInstance().post(new SortEvent(FileSortHelper.SortKey.type, FileSortHelper.SortMethod.asc));
    }

    @Override
    protected void sortByName() {
        LogUtils.i(TAG, "sortByName");
        RxBus.getInstance().post(new SortEvent(FileSortHelper.SortKey.name, FileSortHelper.SortMethod.asc));
    }

    @Override
    protected void sortBySize() {
        LogUtils.i(TAG, "sortBySize");
        RxBus.getInstance().post(new SortEvent(FileSortHelper.SortKey.size, FileSortHelper.SortMethod.asc));
    }

    @Override
    protected void sortByDate() {
        LogUtils.i(TAG, "sortByDate");
        RxBus.getInstance().post(new SortEvent(FileSortHelper.SortKey.date, FileSortHelper.SortMethod.asc));
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
    }
}
