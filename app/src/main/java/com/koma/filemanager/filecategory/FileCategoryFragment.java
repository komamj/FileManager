package com.koma.filemanager.filecategory;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseFragment;
import com.koma.filemanager.data.model.ZipFile;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 12/12/16.
 */

public class FileCategoryFragment extends BaseFragment implements FileCategoryContract.View {
    private static final String TAG = "FileCategoryFragment";
    private FileCategoryContract.Presenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private FileCategoryAdapter mAdapter;
    private ArrayList<ZipFile> mData;

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
        mData = new ArrayList<>();
        mAdapter = new FileCategoryAdapter(mContext, mData);
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
    public void refreshAdapter(ArrayList<ZipFile> zipFiles) {
        LogUtils.i(TAG, "refreshAdapter");
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
        mData.addAll(zipFiles);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmpty() {
        LogUtils.i(TAG, "showEmpty");
    }

    @Override
    public void showLoadingView() {
        LogUtils.i(TAG, "showLoadingView");
    }

    @Override
    public void setPresenter(FileCategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
