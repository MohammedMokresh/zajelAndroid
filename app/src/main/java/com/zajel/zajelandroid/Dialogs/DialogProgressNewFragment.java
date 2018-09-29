package com.zajel.zajelandroid.Dialogs;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zajel.zajelandroid.R;


public class DialogProgressNewFragment extends DialogFragment {

    public ProgressBar mProgressBar;
    public TextView mTvDesc;
    public String mDesc;

    public static DialogProgressNewFragment mDialogProgressNewFragment;

    public static DialogProgressNewFragment newInstance(String desc) {

        Bundle args = new Bundle();

        mDialogProgressNewFragment = new DialogProgressNewFragment();
        args.putString("desc", desc);
        mDialogProgressNewFragment.setArguments(args);
        return mDialogProgressNewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!=null) {
            mDesc = getArguments().getString("desc");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_progress, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mTvDesc = (TextView) rootView.findViewById(R.id.tv_desc);

        mProgressBar.setIndeterminate(true);

        if(mDesc != null) mTvDesc.setText(mDesc);

        mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        setCancelable(false);

        getDialog().setCanceledOnTouchOutside(false);

        if(getDialog() != null) if(getDialog().getWindow() != null) {

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        return rootView;
    }

//
//    @Override
//    public void onResume() {
//        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//
//        super.onResume();
//    }

    @Override
    public void dismiss() {
        super.dismiss();
        mDialogProgressNewFragment = null;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
//        mDialogProgressNewFragment = null;

    }

}
