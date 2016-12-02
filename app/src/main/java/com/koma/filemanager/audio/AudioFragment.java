package com.koma.filemanager.audio;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseFragment;
import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 11/23/16.
 */

public class AudioFragment extends BaseFragment implements AudioConstract.View {
    private static final String TAG = "AudioFragment";
    private AudioConstract.Presenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private AudioAdapter mAdapter;
    private ArrayList<AudioFile> mAuidoFiles;

    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i(TAG, "onAttach");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate'");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAuidoFiles = new ArrayList<>();
        mAdapter = new AudioAdapter(mContext, mAuidoFiles);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "onViewCreated");
    }

    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(TAG, "onDestroyView");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    @Override
    public void setPresenter(@NonNull AudioConstract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void refreshAdapter(ArrayList<AudioFile> audioFiles) {
        LogUtils.i(TAG, "refreshAdapter");
        if (mAuidoFiles != null) {
            mAuidoFiles.clear();
        } else {
            mAuidoFiles = new ArrayList<>();
        }
        mAuidoFiles.addAll(audioFiles);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmpty() {
        LogUtils.i(TAG, "showEmpty");
    }
}
