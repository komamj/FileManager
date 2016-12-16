package com.koma.filemanager.volumeinfo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseActivity;
import com.koma.filemanager.data.FileRepository;
import com.koma.filemanager.util.Constants;
import com.koma.filemanager.util.LogUtils;


/**
 * Created by koma on 12/13/16.
 */

public class VolumeInfoActivity extends BaseActivity {
    private static final String TAG = "VolumeInfoActivity";
    private VolumeInfoContract.Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        String title = getIntent().getStringExtra(Constants.OPERATION_ACTION);
        if (title != null) {
            setTitle(title);
        }
        VolumeInfoFragment fragment = (VolumeInfoFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (fragment == null) {
            fragment = VolumeInfoFragment.newInstance(title);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment).commit();
        }
        mPresenter = new VolumeInfoPresenter(fragment, FileRepository.getInstance());
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
    }

    @Override
    public void onBackPressed() {
        /*ActionSelectionManager.getInstance().clear();
        BusProvider.getInstance().post(
                new MessageEvent(MessageEvent.EVENT_CANCEL_FILE_OPERATION));*/
        super.onBackPressed();
        LogUtils.i(TAG, "onBackPressed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_layout;
    }
}
