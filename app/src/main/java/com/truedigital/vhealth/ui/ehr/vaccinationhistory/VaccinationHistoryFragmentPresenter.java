package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class VaccinationHistoryFragmentPresenter extends BaseMvpPresenter<VaccinationHistoryFragmentInterface.View>
        implements VaccinationHistoryFragmentInterface.Presenter{

    public static VaccinationHistoryFragmentInterface.Presenter create(){
        return new VaccinationHistoryFragmentPresenter();
    }
}
