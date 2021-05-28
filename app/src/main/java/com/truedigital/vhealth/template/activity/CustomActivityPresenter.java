package com.truedigital.vhealth.template.activity;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class CustomActivityPresenter extends BaseMvpPresenter<CustomActivityInterface.View>
        implements CustomActivityInterface.Presenter {

    public static CustomActivityInterface.Presenter create() {
        return new CustomActivityPresenter();
    }
}