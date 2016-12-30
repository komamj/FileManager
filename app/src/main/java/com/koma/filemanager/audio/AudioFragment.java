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
import com.koma.filemanager.widget.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate'");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtils.i(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.i(TAG, "onActivityCreated");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtils.i(TAG, "onViewCreated");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, linearLayoutManager.getOrientation()));
        mAuidoFiles = new ArrayList<>();
        mAdapter = new AudioAdapter(mContext, mAuidoFiles);
        mRecyclerView.setAdapter(mAdapter);
        showLoadingView();
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
        if (mPresenter != null) {
            mPresenter.subscribe();
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
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.i(TAG, "onDestroyView");
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.i(TAG, "onDetach");
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
    public void hideLoadingView() {
        LogUtils.i(TAG, "hideLoadingView");
        super.hideLodingView();
    }

    @Override
    public void showLoadingView() {
        LogUtils.i(TAG, "showLoadingView");
        super.showLoadingView();
    }

    @Override
    public void showEmptyView() {
        LogUtils.i(TAG, "showEmptyView");
        super.showLoadingEmpty();
    }

}
