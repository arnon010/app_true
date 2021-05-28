package com.truedigital.vhealth.ui.setting;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SettingActivityInterface {
    public interface View extends BaseMvpInterface.View {
        /*
        String getUserName();
        String getEmail();
        String getPassword();
        String getPasswordConfirm();
        String getReferenceCode();
        String getOtp();

        MultipartBody.Part getImageBody();
        void onErrorUserName();
        void onErrorEmail();
        boolean isValid();
        void openSignupSuccessActivity();
        */
        void openMainActivity();

    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onDoneButtonClick();
    }
}
