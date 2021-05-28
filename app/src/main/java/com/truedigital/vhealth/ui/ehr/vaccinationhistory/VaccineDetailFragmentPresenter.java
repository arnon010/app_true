package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class VaccineDetailFragmentPresenter extends BaseMvpPresenter<VaccineDetailFragmentInterface.View>
        implements VaccineDetailFragmentInterface.Presenter{

    public static VaccineDetailFragmentInterface.Presenter create(){
        return new VaccineDetailFragmentPresenter();
    }
}
