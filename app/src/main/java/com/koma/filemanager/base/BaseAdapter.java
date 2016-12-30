package com.koma.filemanager.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by koma on 12/27/16.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = BaseAdapter.class.getSimpleName();
    private Context mContext;
    public OnItemClickListener mItemClickListener;
    public OnItemLongClickListener mItemLongClickListener;

    public BaseAdapter(Context context) {
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }

    public interface OnItemClickListener {
        boolean onItemClick(int position);
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }
}
