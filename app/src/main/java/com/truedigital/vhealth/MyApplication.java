package com.truedigital.vhealth;

import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.notification.NotificationOpenedHandler;
import com.truedigital.vhealth.notification.OneSignalNotificationReceivedHandler;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.FirebaseApp;
import com.onesignal.OneSignal;
import com.orhanobut.hawk.Hawk;

import io.intercom.android.sdk.Intercom;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by songkrit on 12/21/2017 AD.
 */

public class MyApplication extends MultiDexApplication {
    LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate(this);

    @Override
    public void onCreate() {
        super.onCreate();

        setUpHawk();
        setUpTimber();

        //..
        FacebookSdk.isInitialized();
        AppEventsLogger.activateApp(this);


        //..
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        OneSignal.startInit(this)
                .setNotificationReceivedHandler(new OneSignalNotificationReceivedHandler(this))
                .setNotificationOpenedHandler(new NotificationOpenedHandler(this))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();

        AppManager.init(this);

        //..
        FirebaseApp.initializeApp(this);
        //..Intercom
        RegisterIntercom();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    private void RegisterIntercom() {
        Intercom.initialize(this, BuildConfig.APIKEY_INTERCOM, BuildConfig.APPID_INTERCOM);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localizationDelegate.onConfigurationChanged(this);
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }

    private void setUpHawk() {
        Hawk.init(this).build();
    }

    private void setUpTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
