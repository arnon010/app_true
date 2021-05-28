package com.truedigital.vhealth.ui.password.create;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class PasswordCreateActivityInterface {
    public interface View extends BaseMvpInterface.View {

        String getUserName();

        int getUserId();

        String getEmail();

        String getDeviceToken();

        String getPassword();

        String getConfirmPassword();

        void onErrorPassword();

        void onErrorConfirmPassword();

        boolean isValid();

        void openSignupSuccessActivity();
        void openLoginEmailActivity();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onGoliveButtonClick();

        void onCallRegisterUser();
    }
}
