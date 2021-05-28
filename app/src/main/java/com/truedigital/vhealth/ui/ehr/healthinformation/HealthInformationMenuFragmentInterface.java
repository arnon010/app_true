package com.truedigital.vhealth.ui.ehr.healthinformation;

import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class HealthInformationMenuFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setHealthInformationMenu(ArrayList<EhrMenuObject> listData);
        void setHealthInformationMenuSelectList(ArrayList<EhrMenuObject> listData);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<HealthInformationMenuFragmentInterface.View>{
        void getHealthInformationMenu(int patientId, boolean isChild);
        void getHealthInformationMenuSelectList(int patientId, boolean isChild);
    }

}
