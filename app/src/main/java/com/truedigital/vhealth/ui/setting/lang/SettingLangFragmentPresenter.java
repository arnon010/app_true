package com.truedigital.vhealth.ui.setting.lang;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class SettingLangFragmentPresenter extends BaseMvpPresenter<SettingLangFragmentInterface.View>
        implements SettingLangFragmentInterface.Presenter{

    public static SettingLangFragmentInterface.Presenter create(){
        return new SettingLangFragmentPresenter();
    }

}
