package com.truedigital.vhealth.ui.login.otp;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginOTPActivityInterface {
    public interface View extends BaseMvpInterface.View {
        String getPhoneNumber();
        String getOTP();
        String getReferenceOTP();
        void setReferenceOTP(String referenceOTP);
        void onErrorOTP();
        boolean isValid();

        //void showSuccess();
        void openSignupSuccess();
        void openUserSetting();
        void openMainActivity();

    }

    public interface Presenter extends BaseMvpInterface.Presenter<View> {

        void onConfirmOtpClick();
        void onResendOtpClick();
        void callApiGetOTP(String telephoneNumber);
    }
}
