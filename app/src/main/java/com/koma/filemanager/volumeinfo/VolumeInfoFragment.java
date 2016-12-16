package com.koma.filemanager.volumeinfo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseFragment;
import com.koma.filemanager.data.model.Disk;
import com.koma.filemanager.util.Constants;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 12/15/16.
 */

public class VolumeInfoFragment extends BaseFragment implements VolumeInfoContract.View {
    private static final String TAG = "VolumeInfoFragment";
    private VolumeInfoContract.Presenter mPresenter;
    private ArrayList<Disk> mData;
    private VolumeInfoAdapter mAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static VolumeInfoFragment newInstance(String title) {
        VolumeInfoFragment volumeInfoFragment = new VolumeInfoFragment();
        Bundle bundle = new Bundle();
        volumeInfoFragment.setArguments(bundle);
        return volumeInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        init();
    }

    private void init() {
        mData = new ArrayList<>();
        mAdapter = new VolumeInfoAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(new VolumeInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtils.i(TAG, "onItemClick");
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.koma.filemanager",
                        "com.koma.filemanager.fileview.FileViewActivity");
                intent.setComponent(componentName);
                intent.putExtra(Constants.EXTRA_FILE_DIRECTORY, mData.get(position)
                        .getMountPoint());
                mContext.startActivity(intent);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volume_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "onViewCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "onResume");
        if (mPresenter != null) {
            mPresenter.subscribe();
        }
    }

    @Override
    public void refreshAdapter(List<Disk> disks) {
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
        mData.addAll(disks);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(VolumeInfoContract.Presenter presenter) {
        LogUtils.i(TAG, "setPresenter");
        mPresenter = presenter;
    }
}
