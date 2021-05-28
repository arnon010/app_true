package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class VaccineDetailFragmentInterface {
    public interface View extends BaseMvpInterface.View{
    }

    public interface Presenter extends BaseMvpInterface.Presenter<VaccineDetailFragmentInterface.View>{
    }
}
