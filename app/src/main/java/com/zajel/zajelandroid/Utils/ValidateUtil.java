package com.zajel.zajelandroid.Utils;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.zajel.zajelandroid.R;

import static com.zajel.zajelandroid.Utils.ZajelUtils.isEmailValid;

public class ValidateUtil  {

    public static boolean validateEmail(EditText editText , TextInputLayout textInputLayout,Activity activity) {
        String email = editText.getText().toString().trim();

        if (email.isEmpty() || !isEmailValid(email)) {
            textInputLayout.setError(activity.getString(R.string.invalid_email));
            requestFocus(editText,activity);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    public static boolean validatePassword(EditText editText , TextInputLayout textInputLayout,Activity activity) {
        if (editText.getText().toString().trim().isEmpty() || editText.getText().toString().length() < 8) {


            textInputLayout.setError(activity.getString(R.string.password_not_right));
            requestFocus(editText,activity);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validateConfirmPassword(EditText editText , TextInputLayout textInputLayout,Activity activity,EditText editText1) {
        if (editText.getText().toString().trim().isEmpty() || !editText.getText().toString().equals(editText1.getText().toString())) {
            textInputLayout.setError(activity.getString(R.string.password_not_match));
            requestFocus(editText,activity);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validateusername(EditText editText , TextInputLayout textInputLayout,Activity activity) {
        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(activity.getString(R.string.empty_name));
            requestFocus(editText,activity);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validatedate(EditText editText , TextInputLayout textInputLayout,Activity activity) {
        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(activity. getString(R.string.empty_date));
            requestFocus(editText,activity);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validateLastName(EditText editText , TextInputLayout textInputLayout,Activity activity) {
        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError(activity.getString(R.string.empty_last_name));
            requestFocus(editText,activity);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private static void requestFocus(View view, Activity context) {
        if (view.requestFocus()) {
           context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
