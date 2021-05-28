package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.model.DailyBloodPressureObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListDailyBloodPressureFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setListDailyBloodPressure(ArrayList<DailyBloodPressureObject> listData);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListDailyBloodPressureFragmentInterface.View>{
        void getListDailyBloodPressure(int patientId, int patientMenuId);
    }
}
