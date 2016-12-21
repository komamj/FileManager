package com.koma.filemanager.widget;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.koma.filemanager.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 12/21/16.
 */

public class LoadingView extends FrameLayout {
    private static final String TAG = "LoadingView";
    private Context mContext;
    @BindString(R.string.no_file)
    String mNoFileText;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar mProgressBar;
    @BindView(R.id.empty_icon)
    ImageView mEmptyImageView;
    @BindView(R.id.loading_text)
    TextView mLoadingText;

    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        inflater.inflate(R.layout.loading_view, this);
    }

    public LoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void hideLoading() {
        mProgressBar.hide();
        this.setVisibility(View.GONE);
    }

    public void showLoding() {
        mProgressBar.show();
        this.setVisibility(View.VISIBLE);
    }

    public void showEmpty() {
        mEmptyImageView.setVisibility(View.VISIBLE);
        mLoadingText.setText(mNoFileText);
    }
}
