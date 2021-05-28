package com.truedigital.vhealth.ui.ehr.medicationhistory;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

public class FormMedicationHistoryChiiwiiLiveFragmentPresenter
        extends BaseMvpPresenter<FormMedicationHistoryChiiwiiLiveFragmentInterface.View>
        implements FormMedicationHistoryChiiwiiLiveFragmentInterface.Presenter{

    public static FormMedicationHistoryChiiwiiLiveFragmentInterface.Presenter create(){
        return new FormMedicationHistoryChiiwiiLiveFragmentPresenter();
    }

}
