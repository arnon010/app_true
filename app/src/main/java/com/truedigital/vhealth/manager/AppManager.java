package com.truedigital.vhealth.manager;


import android.content.Context;

import com.truedigital.vhealth.ui.base.exception.MvpNotInitAppManagerException;

public class AppManager extends RuntimeException implements AppManagerInterface{

    private static Context appContext;
    //private static PreferencesHelper mPrefs;
    private static DataManager dataManager;
    public static boolean activityVisible;

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
            appContext = context;
            //mPrefs = new PreferencesHelper(context);
            dataManager = new DataManager();
        }
    }

    public static Builder init(Context context) {
        return new Builder(context);
    }

    //public static PreferencesHelper getmPrefs() {
    //    return mPrefs;
    //}

    public static DataManager getDataManager() {

        if (appContext == null) {
            throw new MvpNotInitAppManagerException();
        }
        return dataManager;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void setActivityVisible(boolean activityVisible) {
        AppManager.activityVisible = activityVisible;
    }
}

