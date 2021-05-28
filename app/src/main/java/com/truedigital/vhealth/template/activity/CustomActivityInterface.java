package com.truedigital.vhealth.template.activity;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class CustomActivityInterface {
    public interface View extends BaseMvpInterface.View {

    }
    public interface Presenter extends BaseMvpInterface.Presenter<CustomActivityInterface.View> {

    }
}
