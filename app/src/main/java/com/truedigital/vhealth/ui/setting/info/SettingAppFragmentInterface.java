package com.truedigital.vhealth.ui.setting.info;


import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class SettingAppFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void onLogoutClick();
        void openLogin();
        void openSettingLanguage();
        void onPinStatus(boolean hasPin);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<SettingAppFragmentInterface.View>{
        void Logout();

        void getPinStatus();
    }
}
