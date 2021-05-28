package com.truedigital.vhealth.ui.ehr.medicationhistory;

import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.model.MedicationHistoryCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListMedicationHistoryFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setMedicationHistoryList(ArrayList<MedicationHistoryObject> listData);
        void setMedicationHistoryDetail(MedicationHistoryObject data);
        void onDeleteSuccess(MedicationHistoryObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListMedicationHistoryFragmentInterface.View>{
        void getMedicationHistoryList(MedicationHistoryCriteriaObject criteria);
        void getMedicationHistoryDetail(MedicationHistoryObject criteria);
        void deleteMedicationHistory(MedicationHistoryObject data);
    }
}
