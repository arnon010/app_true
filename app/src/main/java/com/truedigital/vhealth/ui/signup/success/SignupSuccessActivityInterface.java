package com.truedigital.vhealth.ui.signup.success;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SignupSuccessActivityInterface {
    public interface View extends BaseMvpInterface.View {
        String getUserName();
        String getEmail();
        void openMainActivity();
    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onDoneButtonClick();
        void onResendEmailClick();
    }
}
