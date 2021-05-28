package com.truedigital.vhealth.ui.setting.lang;


import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class SettingLangFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void onConfirmClick();
        void openLogin();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<SettingLangFragmentInterface.View>{

    }
}
