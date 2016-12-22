package com.koma.filemanager.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.koma.filemanager.R;
import com.koma.filemanager.util.LogUtils;
import com.koma.filemanager.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 11/16/16.
 */

public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    protected Context mContext;
    private CompositeSubscription mSubscriptions;
    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtils.i(TAG, "onAttach");
        mContext = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }
    }

    protected void showLoadingView() {
        mLoadingView.post(new Runnable() {
            @Override
            public void run() {
                mLoadingView.showLoding();
            }
        });
    }

    protected void hideLodingView() {
        mLoadingView.post(new Runnable() {
            @Override
            public void run() {
                mLoadingView.hideLoading();
            }
        });
    }

    protected void showLoadingEmpty() {
        mLoadingView.post(new Runnable() {
            @Override
            public void run() {
                mLoadingView.showLoadingEmpty();
            }
        });
    }

    protected void addSubscription(Subscription subscription) {
        if (subscription == null) return;
        if (mSubscriptions == null) {
            mSubscriptions = new CompositeSubscription();
        }
        mSubscriptions.add(subscription);
    }
}
