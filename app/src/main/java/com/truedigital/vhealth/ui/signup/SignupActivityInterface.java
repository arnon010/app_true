package com.truedigital.vhealth.ui.signup;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SignupActivityInterface {
    public interface View extends BaseMvpInterface.View {
        String getUserName();
        String getEmail();
        void onErrorUserName();
        void onErrorEmail();
        boolean isValid();
        void openSignupSuccessActivity();
    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onDoneButtonClick();
    }
}
