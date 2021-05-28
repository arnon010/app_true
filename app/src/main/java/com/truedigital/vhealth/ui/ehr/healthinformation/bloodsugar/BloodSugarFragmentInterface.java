package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.model.TodayBloodGlucoseObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class BloodSugarFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setTodayBloodGlucose(TodayBloodGlucoseObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<BloodSugarFragmentInterface.View>{
        void getTodayBloodGlucose(int patientId, int patientMenuId);
    }
}
