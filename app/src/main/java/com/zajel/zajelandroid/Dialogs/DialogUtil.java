package com.zajel.zajelandroid.Dialogs;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;


public class DialogUtil {


    public static void showCloseAppDialog(FragmentManager fragmentManager) {
        if (fragmentManager == null) return;
        if (DialogCloseAppFragment.mDialogCloseAppFragment == null) {
            DialogCloseAppFragment.newInstance().show(fragmentManager, "close_app");
        }
    }

    public static void showRequestConfirmation(FragmentManager fragmentManager,Integer activityId, String status) {
        if (fragmentManager == null) return;
        if (DialogAcceptRequestConfirmationFragment.mDialogAcceptRequestConfirmationFragment == null) {
            DialogAcceptRequestConfirmationFragment.newInstance(activityId,status).show(fragmentManager, "show_request_dialog");
        }
    }

    public static void removeProgressDialog() {
        try {
            if (DialogProgressNewFragment.mDialogProgressNewFragment != null)
                DialogProgressNewFragment.mDialogProgressNewFragment.dismiss();
        } catch (Exception e) {
        }

    }

    public static void showProgressDialog(String desc, FragmentManager fragmentManager) {
        try {
            if (DialogProgressNewFragment.mDialogProgressNewFragment == null) {
                DialogProgressNewFragment.newInstance(desc).show(fragmentManager, "progress");

            } else {
                DialogProgressNewFragment.mDialogProgressNewFragment.dismiss();
                DialogProgressNewFragment.newInstance(desc).show(fragmentManager, "progress");
            }

        } catch (Exception e) {
        }
    }
    public static void showChooseCategory(FragmentManager fragmentManager, ArrayList<String> data, ArrayList<String> id) {
        if (fragmentManager == null) return;

        try {
            BooksCategoryBottomSheetFragment fragment = new BooksCategoryBottomSheetFragment();
            Bundle args = new Bundle();
            args.putStringArrayList("dataFromInstance", data);
            args.putStringArrayList("dataFromId", id);
            fragment.setArguments(args);
            fragment.show(fragmentManager, "show_categories_bootomSheet");
        } catch (Exception ignore) {
        }

    }


}
