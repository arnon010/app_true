package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class VaccinationHistoryFragmentInterface {
    public interface View extends BaseMvpInterface.View{
    }

    public interface Presenter extends BaseMvpInterface.Presenter<VaccinationHistoryFragmentInterface.View>{
    }
}
