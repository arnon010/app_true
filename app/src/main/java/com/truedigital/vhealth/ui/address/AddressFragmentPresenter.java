package com.truedigital.vhealth.ui.address;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class AddressFragmentPresenter extends BaseMvpPresenter<AddressFragmentInterface.View>
        implements AddressFragmentInterface.Presenter{

    public static AddressFragmentInterface.Presenter create(){
        return new AddressFragmentPresenter();
    }


}
