package com.zajel.zajelandroid.Dialogs;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.BookList.Borrow.BorrowAndCancelBookResponseBody.BorrowBookResponseBody;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.Requests.AcceptRejectRequestModel.AcceptRejectRequestRequestBody;
import com.zajel.zajelandroid.Requests.AcceptRejectRequestModel.BookActivity;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogAcceptRequestConfirmationFragment extends DialogFragment implements Button.OnClickListener,APIManager.AcceptRejectResponse {
    public static DialogAcceptRequestConfirmationFragment mDialogAcceptRequestConfirmationFragment;
  private   TextView mTvDialogTitle;
    private  TextView mTvDialogMSg;
    private  Button mBtnYes;
    private Button mBtnNo;
    private  Integer activityId;
    private String status;

    APIManager apiManager;
    public DialogAcceptRequestConfirmationFragment() {

    }

    public static DialogAcceptRequestConfirmationFragment newInstance(Integer activityId, String status) {
        mDialogAcceptRequestConfirmationFragment = new DialogAcceptRequestConfirmationFragment();

        Bundle args = new Bundle();
        args.putInt("activity_id", activityId);
        args.putString("status", status);
        mDialogAcceptRequestConfirmationFragment.setArguments(args);


        return mDialogAcceptRequestConfirmationFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_accept_request_confirmation, container, false);

        apiManager= new APIManager(getContext());
        apiManager.setAccepteRejectResponse(this);
        status = getArguments().getString("status");
        activityId = getArguments().getInt("activity_id");

        mBtnNo =  rootView.findViewById(R.id.btn_no);
        mBtnYes =  rootView.findViewById(R.id.btn_yes);

        mTvDialogTitle =  rootView.findViewById(R.id.tv_title);
        mTvDialogMSg =  rootView.findViewById(R.id.tv_msg);


        setCancelable(false);

        mBtnYes.setOnClickListener(this);
        mBtnNo.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.btn_yes:
                dismiss();
                apiManager.acceptRejectRequest(new AcceptRejectRequestRequestBody(new BookActivity(status)),activityId);
                mDialogAcceptRequestConfirmationFragment = null;
                break;
            case R.id.btn_no:
                dismiss();
                mDialogAcceptRequestConfirmationFragment = null;
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

    @Override
    public void requestStatusUpdate(BorrowBookResponseBody genresList) {
        Toast.makeText(getContext(),genresList.getStatus().getMessage(),Toast.LENGTH_LONG).show();
        broadcastIntent();
    }

    @Override
    public void errorOccureRequestStatus() {

    }


    public void broadcastIntent() {
        Intent intent = new Intent();
        intent.setAction("com.zajel.zajelandroid.updateRequestStatus");
        // We should use LocalBroadcastManager when we want INTRA app
        // communication
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

}
