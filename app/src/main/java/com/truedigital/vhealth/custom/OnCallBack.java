package com.truedigital.vhealth.custom;

import android.app.Fragment;

public interface OnCallBack {
    void replaceFragment(Fragment fragment);

    void setCurrentTab(int position);

    void showBackButton(Fragment fragment);

    void hideBackButton();

    void logout();

    void blurView(boolean isBlur);

    void showHelpSupport();

    void hideHelpSupport();

}