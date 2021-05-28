package com.truedigital.vhealth.template.fragment;


import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class CustomFragmentInterface{


    public interface View extends BaseMvpInterface.View{
    }

    public interface Presenter extends BaseMvpInterface.Presenter<CustomFragmentInterface.View>{
    }
}
