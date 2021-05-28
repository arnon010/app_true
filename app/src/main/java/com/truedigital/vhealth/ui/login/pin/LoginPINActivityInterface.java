package com.truedigital.vhealth.ui.login.pin;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class LoginPINActivityInterface {

    public interface View extends BaseMvpInterface.View {
        void onLoginSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<LoginPINActivityInterface.View> {
        void onLogin(String PIN);
    }

}
