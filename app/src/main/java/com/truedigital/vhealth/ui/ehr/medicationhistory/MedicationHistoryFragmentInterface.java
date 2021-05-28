package com.truedigital.vhealth.ui.ehr.medicationhistory;

import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.MedicationHistoryCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class MedicationHistoryFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setDefaultDateCriteria(MedicationHistoryCriteriaObject criteria);
        void setMedicationFromSelectListCriteria(ArrayList<BottomSheetObject> criteria);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<MedicationHistoryFragmentInterface.View>{
        void getMedicationFromSelectList(int patientId, int patientMenuId);
        void getDefaultDateCriteria(int patientId, int patientMenuId);
    }

}
