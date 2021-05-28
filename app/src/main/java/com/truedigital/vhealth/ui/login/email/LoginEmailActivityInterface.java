package com.truedigital.vhealth.ui.login.email;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginEmailActivityInterface {
    public interface View extends BaseMvpInterface.View {
        String getEmail();
        void onErrorEmail();
        boolean isValid();

        void openLoginWithPassword();
    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onLoginButtonClick();
    }
}
