package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListDailyBloodSugarFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setListDailyBloodSugar(ArrayList<DailyBloodSugarObject> listData);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListDailyBloodSugarFragmentInterface.View>{
        void getListDailyBloodSugar(int patientId, int patientMenuId);
    }

}
