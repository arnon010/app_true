package com.truedigital.vhealth.template.fragment;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class CustomFragmentPresenter extends BaseMvpPresenter<CustomFragmentInterface.View>
        implements CustomFragmentInterface.Presenter{

    public static CustomFragmentInterface.Presenter create(){
        return new CustomFragmentPresenter();
    }

}
