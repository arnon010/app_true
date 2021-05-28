package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListTimeBloodSugarFragmentInterface {

    public interface View extends BaseMvpInterface.View {
        void setListBloodSugar(ArrayList<BloodSugarObject> listData);
        void setBloodSugar(BloodSugarObject data);
        void checkDataEmpty();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListTimeBloodSugarFragmentInterface.View> {
        void getListBloodSugar(DailyBloodSugarObject daily);
        void getBloodSugar(int patientId, BloodSugarObject data);
    }
}
