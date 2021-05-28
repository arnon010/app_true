package com.truedigital.vhealth.ui.login.createpin;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class CreatePINActivityInterface {

    public interface View extends BaseMvpInterface.View {
        void onLoginSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<CreatePINActivityInterface.View> {
        void onLogin(String PIN, String confirmPIN);
    }

}
