package com.truedigital.vhealth.ui.login.phone;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginPhoneActivityInterface {
    public interface View extends BaseMvpInterface.View {
        String getPhoneNumber();
        void onErrorPhoneNumber();

        String getReferenceCode();
        String getOtp();
        void setReferenceCode(String reference_code);
        void setOtp(String otp);
        boolean isValid();

        void openLoginWithPassword();
        void openLoginWithOtp(String reference_code);
        void openSignupSuccess();
        void openUserSetting();
    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onLoginButtonClick();
        //void onRenewOtpButtonClick();
        //void onVerifyOtp(String reference_code, String otp);
        //void onLoginWithOtp(String telephoneNumber, String reference_code, String otp);
    }
}
