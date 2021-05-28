package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.model.BloodPressureObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class FormBloodPressureFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void onUpdateSuccess();
        void onNewSuccess(BloodPressureObject data);
        void onDeleteSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormBloodPressureFragmentInterface.View>{
        void newBloodPressure(BloodPressureObject data);
        void updateBloodPressure(BloodPressureObject data);
        void deleteBloodPressure(BloodPressureObject data);
    }
}
