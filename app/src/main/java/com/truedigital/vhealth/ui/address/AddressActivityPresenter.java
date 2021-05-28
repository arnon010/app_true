package com.truedigital.vhealth.ui.address;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class AddressActivityPresenter extends BaseMvpPresenter<AddressActivityInterface.View>
        implements AddressActivityInterface.Presenter {

    public static AddressActivityInterface.Presenter create(){
        return new AddressActivityPresenter();
    }
}
