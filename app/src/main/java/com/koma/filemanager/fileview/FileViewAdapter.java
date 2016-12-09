package com.koma.filemanager.fileview;

import android.content.Context;
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
import com.koma.filemanager.base.BaseFile;
import com.koma.filemanager.helper.FileCountHelper;
import com.koma.filemanager.util.FileUtils;
import com.koma.filemanager.util.LocaleUtils;
import com.koma.filemanager.util.LogUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;

/**
 * Created by koma on 12/1/16.
 */

public class FileViewAdapter extends RecyclerView.Adapter<FileViewAdapter.ViewHolder> {
    private static final String TAG = "FileViewAdapter";
    private List<BaseFile> mData;
    private Context mContext;
    private boolean mSelectMode = false;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mContainView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mSelectMode) {
                    return true;
                }
                mSelectMode = true;
                notifyDataSetChanged();
                holder.mCheckBox.setChecked(true);
                LogUtils.i(TAG, "position : " + position);
                return true;
            }
        });
        BaseFile baseFile = mData.get(position);
        if (baseFile.getIsDirectory()) {
            Glide.with(mContext).load(R.mipmap.item_folder).into(holder.mFileImageView);
            try {
                File file = new File(baseFile.getFullPath());
                holder.mFileSize.setText(LocaleUtils.formatItemCount(file.listFiles().length));
            } catch (Exception e) {
                LogUtils.e(TAG, "onBindViewHolder error : " + e.toString());
            }
        } else {
            Glide.with(mContext).load(R.mipmap.item_folder).into(holder.mFileImageView);
            holder.mFileSize.setText(FileUtils.formatFileSize(mData.get(position).getFileSize()));
        }
        if (mSelectMode) {
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    LogUtils.i(TAG, "onCheckedChanged isChecked : " + isChecked);
                }
            });
        }
        holder.mFileName.setText(baseFile.getFileName());
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

        @BindView(R.id.item_contain_view)
        RelativeLayout mContainView;

        /*public boolean switchSelectMode() {
            LogUtils.i(TAG, "OnLongClick");
            if (mSelectMode) {
                return true;
            }
            mSelectMode = true;
            notifyDataSetChanged();
            LogUtils.i(TAG, "item count : " + (getItemCount() - 1));

            return true;
        }
*/
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
