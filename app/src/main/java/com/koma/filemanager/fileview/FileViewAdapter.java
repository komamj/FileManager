package com.koma.filemanager.fileview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.koma.filemanager.R;
import com.koma.filemanager.base.BaseFile;
import com.koma.filemanager.util.FileUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 12/1/16.
 */

public class FileViewAdapter extends RecyclerView.Adapter<FileViewAdapter.ViewHolder> {
    private List<BaseFile> mData;
    private Context mContext;

    public FileViewAdapter(Context context, ArrayList<BaseFile> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BaseFile baseFile = mData.get(position);
        holder.mFileImageView.setImageResource(R.mipmap.item_folder);
        holder.mFileName.setText(baseFile.getFileName());
        holder.mFileSize.setText(FileUtils.formatFileSize(mData.get(position).getFileSize()));
        holder.mFileModifiedTime.setText(FileUtils.formatFileModifiedTime(mData.get(position).getFileModifiedTime()));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.select_checkbox)
        CheckBox mCheckBox;
        @BindView(R.id.iv_file_image)
        ImageView mFileImageView;
        @BindView(R.id.tv_file_name)
        TextView mFileName;
        @BindView(R.id.tv_file_size)
        TextView mFileSize;
        @BindView(R.id.tv_file_modified_time)
        TextView mFileModifiedTime;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
