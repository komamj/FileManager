package com.koma.filemanager.widget;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.koma.filemanager.R;
import com.koma.filemanager.util.LogUtils;
import com.koma.filemanager.util.Utils;

/**
 * Created by koma on 12/14/16.
 */

public class InputNameDialog extends AppCompatDialogFragment {
    private static final String TAG = "InputNameDialog";
    public static final String ARG_TITLE = "title";
    public static final String ARG_ITEM_POSITION = "position";

    public interface OnEditItemListener {
        void onTitleModified(int position, String newTitle);
    }

    private String mTitle;
    private int mPosition;

    public InputNameDialog() {
    }

    public static InputNameDialog newInstance(String title, int position) {
        return newInstance(title, position, null);
    }

    public static InputNameDialog newInstance(String title, int position, Fragment fragment) {
        InputNameDialog dialog = new InputNameDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_ITEM_POSITION, position);
        dialog.setArguments(args);
        dialog.setArguments(args);
        dialog.setTargetFragment(fragment, 0);
        return dialog;
    }

    private OnEditItemListener getListener() {
        OnEditItemListener listener = (OnEditItemListener) getTargetFragment();
        if (listener == null) {
            listener = (OnEditItemListener) getActivity();
        }
        return listener;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ARG_TITLE, mTitle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i(TAG, "start");
    }

    @SuppressLint({"InflateParams", "HandlerLeak"})
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        //Pick up bundle parameters
        Bundle bundle;
        if (savedInstanceState == null) {
            bundle = getArguments();
        } else {
            bundle = savedInstanceState;
        }

        mTitle = bundle.getString(ARG_TITLE);
        mPosition = bundle.getInt(ARG_ITEM_POSITION);

        //Inflate custom view
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.input_name_dialog_layout, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.text_edit_title);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.edit_title)
                .setView(dialogView)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Utils.hideSoftInputFrom(getActivity(), editText);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getListener().onTitleModified(mPosition,
                                editText.getText().toString().trim());
                        Utils.hideSoftInputFrom(getActivity(), editText);
                        dialog.dismiss();
                    }
                });

        final AlertDialog editDialog = builder.create();

        editDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                updateOkButtonState(editDialog, null);
            }
        });

        if (mTitle != null) {
            editText.setText(mTitle);
            editText.selectAll();
        }
        editText.requestFocus();
        editText.addTextChangedListener(new TextWatcher() {
            private final static long DELAY = 400L;
            private final static int TRIGGER = 1;

            private Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == TRIGGER) {
                        updateOkButtonState(editDialog, editText);
                    }
                }
            };

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateOkButtonState(editDialog, null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHandler.removeMessages(TRIGGER);
                mHandler.sendEmptyMessageDelayed(TRIGGER, DELAY);
            }
        });

        editDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return editDialog;
    }

    private void updateOkButtonState(AlertDialog dialog, EditText editText) {
        Button buttonOK = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        if (editText == null || (editText.getText().toString().trim()).length() == 0) {
            buttonOK.setEnabled(false);
            return;
        }
        if (mTitle != null && !mTitle.equalsIgnoreCase(editText.getText().toString().trim())) {
            buttonOK.setEnabled(true);
        } else {
            editText.setError(getActivity().getString(R.string.err_no_edit));
            buttonOK.setEnabled(false);
        }
    }
}
