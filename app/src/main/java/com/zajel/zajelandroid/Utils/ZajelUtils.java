package com.zajel.zajelandroid.Utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;
import android.widget.DatePicker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ZajelUtils {

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static void openYearView(DatePicker datePicker) {
        try {
            Field mDelegateField = datePicker.getClass().getDeclaredField("mDelegate");
            mDelegateField.setAccessible(true);
            Object delegate = mDelegateField.get(datePicker);
            Method setCurrentViewMethod = delegate.getClass().getDeclaredMethod("setCurrentView", int.class);
            setCurrentViewMethod.setAccessible(true);
            setCurrentViewMethod.invoke(delegate, 1);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        }
    }

    public static int getScreenWidth(Context context) {
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        window.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static void replaceFragment(FragmentManager fragmentManager,
                                       @IdRes int containerID,
                                       Fragment newFragment) {

        androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, newFragment);
        fragmentTransaction.commit();
    }
}
