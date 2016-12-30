package com.koma.filemanager.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.koma.filemanager.util.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by koma on 12/26/16.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnLongClickListener {
    private static final String TAG = BaseViewHolder.class.getSimpleName();
    protected final BaseAdapter mAdapter;


    public BaseViewHolder(View view, BaseAdapter adapter) {
        super(view);
        this.mAdapter = adapter;
        ButterKnife.bind(this, view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    public View getItemView() {
        return itemView;
    }

    public int getBaseViewHolderPosition() {
        return getAdapterPosition();
    }


    @Override
    public void onClick(View v) {
        if (mAdapter != null) {
            mAdapter.mItemClickListener.onItemClick(getBaseViewHolderPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mAdapter != null) {
            LogUtils.i(TAG, "onItemLongClickListener position : " + getBaseViewHolderPosition());
            mAdapter.mItemLongClickListener.onItemLongClick(getBaseViewHolderPosition());
            return true;
        }
        return false;
    }
}
