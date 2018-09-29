package com.zajel.zajelandroid.Dialogs;

import androidx.fragment.app.FragmentManager;


public class DialogUtil {


    public static void showCloseAppDialog(FragmentManager fragmentManager) {
        if (fragmentManager == null) return;
        if (DialogCloseAppFragment.mDialogCloseAppFragment == null) {
            DialogCloseAppFragment.newInstance().show(fragmentManager, "close_app");
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


}
