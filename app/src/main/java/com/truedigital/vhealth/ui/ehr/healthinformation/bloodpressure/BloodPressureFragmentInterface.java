package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.model.TodayBloodPressureObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class BloodPressureFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setTodayBloodPressure(TodayBloodPressureObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<BloodPressureFragmentInterface.View>{
        void getTodayBloodPressure(int patientId, int patientMenuId);
    }
}
