package com.zajel.zajelandroid;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_NAME = "com.zajel.preference.PREF";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String CLIENT = "client";
    private static final String EXPIRY = "expiry";
    private static final String UID = "uid";
    private static final String TOKEN_TYPE = "token_type";
    private static final String CHOOSEN_CATEGORY = "choosen_category";


    private static PreferenceManager sInstance;
    private final SharedPreferences mPref;

    private PreferenceManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceManager(context);
        }
    }

    public static synchronized PreferenceManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferenceManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    /**
     * headers
     *
     */

    public void setAccessToken(String  value ) {
    mPref.edit().putString(ACCESS_TOKEN, value).apply();
    }
    public String getAccessToken() {
        return mPref.getString(ACCESS_TOKEN, "");
    }


    public void setClient(String  value ) {
        mPref.edit().putString(CLIENT, value).apply();
    }
    public String getClient() {
        return mPref.getString(CLIENT, "");
    }

    public void setExpiry(String  value ) {
        mPref.edit().putString(EXPIRY, value).apply();
    }
    public String getExpiry() {
        return mPref.getString(EXPIRY, "");
    }

    public void setUid(String  value ) {
        mPref.edit().putString(UID, value).apply();
    }
    public String getUid() {
        return mPref.getString(UID, "");
    }

    public void setTokenType(String  value ) {
        mPref.edit().putString(TOKEN_TYPE, value).apply();
    }
    public String getTokenType() {
        return mPref.getString(TOKEN_TYPE, "");
    }

    public void setChoosenCategory(String  value ) {
        mPref.edit().putString(CHOOSEN_CATEGORY, value).apply();
    }
    public String getChoosenCategory() {
        return mPref.getString(CHOOSEN_CATEGORY, "");
    }


    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }
}
