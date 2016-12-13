package com.koma.filemanager.filecategory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koma.filemanager.R;
import com.koma.filemanager.data.model.ZipFile;
import com.koma.filemanager.util.FileUtils;
import com.koma.filemanager.util.LogUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by koma on 12/12/16.
 */

public class FileCategoryAdapter extends RecyclerView.Adapter<FileCategoryAdapter.ViewHolder> {
    private static final String TAG = "FileCategoryAdapter";
    private ArrayList<ZipFile> mData;
    private Context mContext;
    private boolean mSelectMode = false;

    public FileCategoryAdapter(Context context, ArrayList<ZipFile> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mContainView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mSelectMode) {
                    return true;
                }
                mSelectMode = true;
                notifyDataSetChanged();
                holder.mSelectBox.setChecked(true);
                LogUtils.i(TAG, "position : " + position);
                return true;
            }
        });
        holder.mContainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(mData.get(position).getFullPath())), "application/x-rar-compressed");
                mContext.startActivity(intent);
            }
        });
        Glide.with(mContext).load(R.mipmap.item_compress).into(holder.mFileImage);
        holder.mFileName.setText(mData.get(position).getFileName());
        if (mSelectMode) {
            holder.mSelectBox.setVisibility(View.VISIBLE);
            holder.mSelectBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    LogUtils.i(TAG, "onCheckedChanged isChecked : " + isChecked);
                }
            });
        }
        holder.mFileSize.setText(FileUtils.formatFileSize(mData.get(position).getFileSize()));
        holder.mFileModifiedTime.setText(FileUtils.formatFileModifiedTime(mData.get(position).getFileModifiedTime()));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.item_contain_view)
        RelativeLayout mContainView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
