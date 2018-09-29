package com.zajel.zajelandroid.Dialogs;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zajel.zajelandroid.R;


public class DialogCloseAppFragment extends DialogFragment implements Button.OnClickListener{

    TextView mTvDialogTitle;
    TextView mTvDialogMSg;
    Button mBtnYes;
    Button mBtnNo;

   public static DialogCloseAppFragment mDialogCloseAppFragment;

    public DialogCloseAppFragment(){

    }

    public static DialogCloseAppFragment newInstance() {
        mDialogCloseAppFragment = new DialogCloseAppFragment();
        return mDialogCloseAppFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_close_app, container, false);

        mBtnNo = (Button) rootView.findViewById(R.id.btn_no);
        mBtnYes = (Button) rootView.findViewById(R.id.btn_yes);

        mTvDialogTitle = (TextView) rootView.findViewById(R.id.tv_title);
        mTvDialogMSg = (TextView) rootView.findViewById(R.id.tv_msg);


        setCancelable(false);

        mBtnYes.setOnClickListener(this);
        mBtnNo.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int i=v.getId();
        switch (i){
            case R.id.btn_yes:
                dismiss();
                mDialogCloseAppFragment =null;
                getActivity().finish();
                break;
            case R.id.btn_no:
                dismiss();
                mDialogCloseAppFragment =null;
                break;
        }
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }

}
