package com.truedigital.vhealth.ui.login.password;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginPasswordActivityInterface {
    public interface View extends BaseMvpInterface.View {
        String getPhoneNumber();
        String getPassword();
        void onErrorPassword();
        boolean isValid();

        void openMainActivity();
        void openForgotPasswordActivity();
    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onLoginButtonClick();
    }
}
