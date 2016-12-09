package com.koma.filemanager.audio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koma.filemanager.R;
import com.koma.filemanager.data.model.AudioFile;
import com.koma.filemanager.helper.MeidaHelper;
import com.koma.filemanager.util.FileUtils;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by koma on 11/24/16.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private static final String TAG = "AudioAdapter";
    private ArrayList<AudioFile> mData;
    private Context mContext;

    public AudioAdapter(Context context, ArrayList<AudioFile> data) {
        mContext = context;
        mData = data;
    }

    public void setData(ArrayList<AudioFile> data) {
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        return new AudioViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(AudioViewHolder holder, int position) {
        LogUtils.i(TAG, "onBindViewHolder :" + mData.get(position).getFullPath());
        Glide.with(mContext).load(MeidaHelper.getAlbumArtUri(mData.get(position).getAlbumId()))
                .placeholder(R.mipmap.item_audio)
                .crossFade(1000).into(holder.mFileImage);
        holder.mFileName.setText(mData.get(position).getFileName());
        holder.mFileSize.setText(FileUtils.formatFileSize(mData.get(position).getFileSize()));
        holder.mFileModifiedTime.setText(FileUtils.formatFileModifiedTime(mData.get(position).getFileModifiedTime()));
    }

    public class AudioViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.select_checkbox)
        CheckBox mSelectBox;
        @BindView(R.id.iv_file_image)
        ImageView mFileImage;
        @BindView(R.id.tv_file_name)
        TextView mFileName;
        @BindView(R.id.tv_file_size)
        TextView mFileSize;
        @BindView(R.id.tv_file_modified_time)
        TextView mFileModifiedTime;

        public AudioViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
