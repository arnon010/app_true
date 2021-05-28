package com.truedigital.vhealth.custom;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import com.truedigital.vhealth.R;

/**
 * Created by nilecon on 2/22/2017 AD.
 */

public class DoubleBackPressed {
    private static boolean doubleBackToExitPressedOnce = false;

    public static void setDoubleBackPressedToExit(Activity activity, OnDoubleBackPressedListener onDoubleBackPressedListener) {
        //finish app
        if (activity.getFragmentManager().getBackStackEntryCount() == 0) {
            if (doubleBackToExitPressedOnce) {
                onDoubleBackPressedListener.onDoublePressed();
            }
            doubleBackToExitPressedOnce = true;
            Toast.makeText(activity, R.string.double_back_pressed, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;

                }
            }, 1000);
        } else {
            activity.getFragmentManager().popBackStack();
        }
    }

    public interface OnDoubleBackPressedListener {
        void onDoublePressed();

    }
}
