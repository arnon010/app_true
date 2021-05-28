package com.truedigital.vhealth.custom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatusReceiver extends BroadcastReceiver {
    public interface Callbacks {
        public void onNetworkUpdated(boolean status);
    }

    private Callbacks callback;
    private Context context;

    public NetworkStatusReceiver(Context context) {
        this.context = context;
    }

    public void setCallback(Callbacks callback) {
        this.callback = callback;
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

    @Override
    public void onReceive(Context context, Intent intent) {
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
