package com.truedigital.vhealth.ui.login;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginActivityInterface {
    public interface View extends BaseMvpInterface.View {

        void openLoginWithPhone();
        void openLoginWithEmail();
        void openLoginFacebook();
        void openLoginOTP(String referenceOTP);
        void openMainActivity();
    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {

        void onRequestOTPClick();
        void onEmailLoginClick();
        void onFacebookLoginClick();
        void callLoginFacebook(String facebookId, String facebookToken);
        //void callLoginFacebook(String email,String userName,String facebookId, String facebookToken,String firstName, String lastname);
    }
}
