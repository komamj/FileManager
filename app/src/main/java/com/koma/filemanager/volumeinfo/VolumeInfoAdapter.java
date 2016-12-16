package com.koma.filemanager.volumeinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koma.filemanager.R;
import com.koma.filemanager.data.model.Disk;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by koma on 12/15/16.
 */

public class VolumeInfoAdapter extends RecyclerView.Adapter<VolumeInfoAdapter.ViewHolder> {
    private static final String TAG = "VolumeInfoAdapter";
    private ArrayList<Disk> mData;
    private Context mContext;
    private OnItemClickListener mListener;

    public VolumeInfoAdapter(Context context, ArrayList<Disk> data) {
        this.mData = data;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_volume_info, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setTag(position);
        holder.mTitle.setText(mData.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @OnClick(R.id.volume_info_layout)
        void onClick(View view) {
            LogUtils.i(TAG, "onItemClick");
            if (mListener != null) {
                mListener.onItemClick(view, (Integer) view.getTag());
            }
        }

        @BindView(R.id.volume_info_title)
        TextView mTitle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
