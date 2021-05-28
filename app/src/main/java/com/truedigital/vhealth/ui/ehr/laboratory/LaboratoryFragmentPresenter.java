package com.truedigital.vhealth.ui.ehr.laboratory;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class LaboratoryFragmentPresenter extends BaseMvpPresenter<LaboratoryFragmentInterface.View>
        implements LaboratoryFragmentInterface.Presenter{

    public static LaboratoryFragmentInterface.Presenter create(){
        return new LaboratoryFragmentPresenter();
    }
}
