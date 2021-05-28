package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.model.BloodPressureObject;
import com.truedigital.vhealth.model.DailyBloodPressureObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListTimeBloodPressureFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setListBloodPressure(ArrayList<BloodPressureObject> listData);
        void setBloodPressure(BloodPressureObject data);
        void checkDataEmpty();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListTimeBloodPressureFragmentInterface.View>{
        void getListBloodPressure(DailyBloodPressureObject daily);
        void getBloodPressure(int patientId, BloodPressureObject data);
    }
}
