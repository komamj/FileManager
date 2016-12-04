package com.koma.filemanager.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koma.filemanager.R;
import com.koma.filemanager.data.model.Disk;
import com.koma.filemanager.helper.BusProvider;
import com.koma.filemanager.helper.event.PathEvent;
import com.koma.filemanager.util.Constants;
import com.koma.filemanager.util.FileUtils;
import com.koma.filemanager.util.LocaleUtils;
import com.koma.filemanager.util.LogUtils;
import com.squareup.otto.Produce;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by koma on 11/29/16.
 */

public class
DiskAdapter extends RecyclerView.Adapter<DiskAdapter.VolumeInfoHolder> {
    private static final String TAG = "VolumeInfoAdapter";
    private ArrayList<Disk> mData;
    private Context mContext;

    public DiskAdapter(Context context, ArrayList<Disk> data) {
        mContext = context;
        mData = data;
    }

    public void setData(ArrayList<Disk> data) {
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public VolumeInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disk, null);
        return new VolumeInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(VolumeInfoHolder holder, final int position) {

        holder.mVolumeTitle.setText(mData.get(position).getDescription());
        holder.mVolumeAvailable.setText(LocaleUtils.formatVolumeInfo(
                mData.get(position).getTotalSpace(), mData.get(position).getAvailavleSpace()));
        long usedSpace = mData.get(position).getTotalSpace() - mData.get(position).getAvailavleSpace();
        LogUtils.i(TAG, "onBindViewHolder usedSpace : " + usedSpace);
        holder.mProgressBar.setProgress((int) (100 * usedSpace / mData.get(position).getTotalSpace()));
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.koma.filemanager",
                        "com.koma.filemanager.fileview.FileViewActivity");
                intent.setComponent(componentName);
                intent.putExtra(Constants.EXTRA_FILE_DIRECTORY, mData.get(position)
                        .getMountPoint());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class VolumeInfoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_volume_title)
        TextView mVolumeTitle;
        @BindView(R.id.tv_volume_available)
        TextView mVolumeAvailable;
        @BindView(R.id.pb_volume_available)
        ProgressBar mProgressBar;

        @BindView(R.id.layout_volume_info)
        View mItemView;

        public VolumeInfoHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
