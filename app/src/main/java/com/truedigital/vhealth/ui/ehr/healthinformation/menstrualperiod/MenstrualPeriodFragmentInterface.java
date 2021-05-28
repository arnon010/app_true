package com.truedigital.vhealth.ui.ehr.healthinformation.menstrualperiod;

import com.truedigital.vhealth.model.MenstruationObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class MenstrualPeriodFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setMenstrualPeriodList(ArrayList<MenstruationObject> listData);
        void onNewSuccess(MenstruationObject data);
        void onNewError();
        void onDeleteSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<MenstrualPeriodFragmentInterface.View>{
        void getMenstrualPeriodList(int patientId, int patientMenuId);
        void newMenstrualPeriod(int patientId, int patientMenuId, String menuCode, MenstruationObject data);
        void deleteMenstrualPeriod(int patientId, MenstruationObject data);
    }

}
