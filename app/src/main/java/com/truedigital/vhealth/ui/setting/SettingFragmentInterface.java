package com.truedigital.vhealth.ui.setting;

import android.net.Uri;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import okhttp3.MultipartBody;

public class SettingFragmentInterface {

    public interface View extends BaseMvpInterface.View {
        String getOTP();

        String getReferenceCode();

        String getUserName();

        String getPassword();

        String getPasswordConfirm();

        String getOldPassword();

        Uri getUriImage();

        void onErrorPassword();

        void onErrorConfirmPassword();

        boolean isValid();

        boolean isValidCreate();

        boolean isValidUpdate();

        boolean isEditMode();

        boolean isChangePassword();

        void showSuccess();

        void showSignupSuccess();

        void showEditSuccess();

        void openLogin();

        void openSettingApp();

        void openOldPassword();

        MultipartBody.Part getImageBody();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<SettingFragmentInterface.View> {
        void Logout();

        void onConfirmButtonClick();

        void updateAccount();
    }
}
