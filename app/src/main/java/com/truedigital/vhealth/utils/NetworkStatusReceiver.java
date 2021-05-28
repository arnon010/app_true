package com.truedigital.vhealth.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.WeakReference;
import java.net.InetAddress;

public class NetworkStatusReceiver extends BroadcastReceiver {
    public interface Callbacks {
        void onNetworkUpdated(boolean status);
    }

    private final WeakReference<Callbacks> mCallback;

    public NetworkStatusReceiver() {
        this(null);
    }

    public NetworkStatusReceiver(Callbacks callback) {
        mCallback = new WeakReference<Callbacks>(callback);
    }

    public static boolean getConnectedStatus(Context context) {
        boolean status = false;
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
                status = (activeNetworkInfo != null && activeNetworkInfo.isConnected());
            }
        }
        return status;
    }

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("https://google.com"); //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Callbacks callback = mCallback.get();
        if (callback != null) {
            callback.onNetworkUpdated(getConnectedStatus(context));
        }
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

}
