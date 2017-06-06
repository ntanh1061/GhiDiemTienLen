package com.nonamejx.ghidiemtienlen.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by noname
 * on 22/10/2016.
 */
public class SharedPrefsManager {
    private static final String PREF_NAME = "PlayCardTrackingPrefs";
    private static final int PREF_PRIVATE_MODE = 0;

    private static SharedPrefsManager mInstance;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    private SharedPrefsManager(Context context) {
        mPrefs = context.getSharedPreferences(PREF_NAME, PREF_PRIVATE_MODE);
        mEditor = mPrefs.edit();
    }

    public static SharedPrefsManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefsManager(context);
        }
        return mInstance;
    }

    public void setupSetting(String setting, boolean value) {
        switch (setting) {
            case Setting.SHOW_CURRENT_RESULT:
                mEditor.putBoolean(Setting.SHOW_CURRENT_RESULT, value);
                mEditor.commit();
                break;
            case Setting.SHOW_NUMBER_OF_TURNS:
                mEditor.putBoolean(Setting.SHOW_NUMBER_OF_TURNS, value);
                mEditor.commit();
                break;
            default:
                break;
        }
    }

    public boolean getSetting(String setting) {
        switch (setting) {
            case Setting.SHOW_CURRENT_RESULT:
                return mPrefs.getBoolean(Setting.SHOW_CURRENT_RESULT, true);
            case Setting.SHOW_NUMBER_OF_TURNS:
                return mPrefs.getBoolean(Setting.SHOW_NUMBER_OF_TURNS, true);
            default:
                return false;
        }
    }

}
