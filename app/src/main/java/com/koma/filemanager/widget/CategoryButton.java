package com.koma.filemanager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koma.filemanager.R;
import com.koma.filemanager.util.LogUtils;

/**
 * Created by koma on 11/19/16.
 */

public class CategoryButton extends LinearLayout {
    private static final String TAG = "CategoryButton";
    private static final String DEFAULT_COUNT_TEXT = "...";
    private static final int DEFAULT_COUNT = 0;
    private ImageView mImageView;
    private TextView mTitleTextView;
    private TextView mCountTextView;
    private int mCount;
    private Context mContext;

    public CategoryButton(Context context) {
        this(context, null);
        init(context, null);
    }

    public CategoryButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CategoryButton(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, 0);
        init(context, attributeSet);
    }


    private void init(Context context, AttributeSet attributeSet) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.category_button_layout, this);
        mCount = DEFAULT_COUNT;
        mContext = context;
        mImageView = (ImageView) findViewById(R.id.iv_category_button);
        mTitleTextView = (TextView) findViewById(R.id.tv_category_button_title);
        mCountTextView = (TextView) findViewById(R.id.tv_category_button_count);
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CategoryButton);
        try {
            setTitleText(typedArray.getString(R.styleable.CategoryButton_category_text));
            setImageView(typedArray.getDrawable(R.styleable.CategoryButton_category_image));
            setCountText(mCount);
        } catch (Exception e) {
            LogUtils.e(TAG, "error :" + e.toString());
        } finally {
            typedArray.recycle();
        }
    }

    public void setImageView(Drawable drawable) {
        this.mImageView.setImageDrawable(drawable);
    }

    public void setTitleText(CharSequence title) {
        this.mTitleTextView.setText(title);
    }

    // 此处的输入为个数
    public void setCountText(int count) {
        mCount = count;
        if (mCount < 0) {
            this.mCountTextView.setText(DEFAULT_COUNT_TEXT);
        } else {
            this.mCountTextView.setText(mContext.getString(R.string.category_num, mCount));
        }
    }

    public int getCount() {
        return mCount;
    }
}
