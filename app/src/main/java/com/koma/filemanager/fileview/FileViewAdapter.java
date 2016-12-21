package com.koma.filemanager.fileview;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.koma.filemanager.helper.RxBus;
import com.koma.filemanager.helper.SelectHelper;
import com.koma.filemanager.helper.event.SelectEvent;
import com.koma.filemanager.util.FileUtils;
import com.koma.filemanager.util.LocaleUtils;
import com.koma.filemanager.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by koma on 12/1/16.
 */

public class FileViewAdapter extends RecyclerView.Adapter<FileViewAdapter.ViewHolder> {
    private static final String TAG = "FileViewAdapter";
    private List<BaseFile> mData;
    private Context mContext;
    private boolean mSelectMode = false;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> mCached = new HashMap<>();
    @NonNull
    private RecyclerViewOnItemClickListener mListener;
    private CompositeSubscription mSubsriptions;

    public FileViewAdapter(Context context, ArrayList<BaseFile> data) {
        mContext = context;
        mData = data;
        mSubsriptions = new CompositeSubscription();
        mSubsriptions.add(addSubscription());
        initCached();
    }

    private Subscription addSubscription() {
        return RxBus.getInstance().toObservable().observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof SelectEvent) {
                            LogUtils.i(TAG, "SelectEvent");
                            SelectEvent selectEvent = (SelectEvent) o;
                            if (selectEvent.getSelectMode() == SelectHelper.MODE_IDLE) {
                                mSelectMode = false;
                                if (mCached != null) {
                                    mCached.clear();
                                }
                                notifyDataSetChanged();
                            }

                        }
                    }
                }).subscribe(RxBus.defaultSubscriber());
    }

    public void setData(ArrayList<BaseFile> data) {
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
        mData.addAll(data);
        initCached();
        notifyDataSetChanged();
    }

    private void initCached() {
        if (mData == null) {
            return;
        }
        for (int i = 0; i < mData.size(); i++) {
            mCached.put(i, false);
        }
    }

    public void setRecyclerViewOnItemClickListener(@NonNull RecyclerViewOnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        //LogUtils.i(TAG, "onCreateViewHolder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // LogUtils.i(TAG, "onBindViewHolder");
        if (mCached.get(position) == null) {
            mCached.put(position, false);
        }
        BaseFile baseFile = mData.get(position);
        holder.mContainView.setTag(position);
        if (baseFile.getIsDirectory()) {
            Glide.with(mContext).load(R.mipmap.item_folder).into(holder.mFileImageView);
            try {
                FileCountHelper.getInstance().getFileCount(baseFile.getFullPath())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                LogUtils.i(TAG, "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.e(TAG, "onError : " + e.toString());
                            }

                            @Override
                            public void onNext(Integer integer) {
                                LogUtils.i(TAG, "onNext integer : " + integer.intValue());
                                holder.mFileSize.setText(LocaleUtils.formatItemCount(integer.intValue()));
                            }
                        });
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
                    mCached.put(position, isChecked);
                }
            });
            // 设置CheckBox的状态
            holder.mCheckBox.setChecked(mCached.get(position));
        }
        holder.mFileName.setText(baseFile.getFileName());
        holder.mFileModifiedTime.setText(FileUtils.formatFileModifiedTime(mData.get(position).getFileModifiedTime()));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public Map<Integer, Boolean> getCached() {
        return mCached;
    }

    public void setSelectItem(int position) {
        //对当前状态取反
        if (mCached.get(position)) {
            mCached.put(position, false);
        } else {
            mCached.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void setSelectMode(boolean mode) {
        if (mSelectMode != mode) {
            mSelectMode = mode;
        }
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
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

        @OnClick(R.id.item_contain_view)
        void itemOnClick(View view) {
            if (mSelectMode) {
                setSelectItem((Integer) view.getTag());
                return;
            }
            if (mListener != null) {
                mListener.onItemClickListener(view, (Integer) view.getTag());
                LogUtils.i(TAG, "onItemClickListener position : " + (Integer) view.getTag());
            }
        }

        @OnLongClick(R.id.item_contain_view)
        boolean itemLongClick(View view) {
            if (mSelectMode) {
                return true;
            }
            if (mListener != null) {
                LogUtils.i(TAG, "onItemLongClickListener position : " + (Integer) view.getTag());
                return mListener.onItemLongClickListener(view, (Integer) view.getTag());
            }
            return false;
        }

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
