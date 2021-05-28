package com.truedigital.vhealth.ui.ehr;


import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.model.PatientObject;
import com.truedigital.vhealth.model.PatientRelationshipObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class EhrFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void setPatient(PatientObject data);
        void setHealthRecordMenu(ArrayList<EhrMenuObject> listData);
        void setHealthRecordMenuSelectList(ArrayList<EhrMenuObject> listData);
        void openRelationshipMenu(PatientRelationshipObject data);
        void setFamilySelectList( ArrayList<PatientRelationshipObject> listData);
        void checkPatientEHRMenuSuccess(String menuCode, String menuName);
        void navigateToLoginEhr();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<EhrFragmentInterface.View>{
        void getPatient(int patientId);
        void getHealthRecordMenu(int patientId, boolean isChild);
        void getHealthRecordMenuSelectList(int patientId, boolean isChild);
        void getFamilySelectList();
        void createFamily(String relationshipName);
        void checkPatientEHRMenu(int patientId, String menuCode, String menuName);
    }
}
