package com.truedigital.vhealth.ui.ehr.medicationhistory;

import android.app.Activity;

import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class FormMedicationHistoryFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void onUpdateSuccess(MedicationHistoryObject data);
        void onNewSuccess(MedicationHistoryObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormMedicationHistoryFragmentInterface.View>{
        void newMedicationHistory(Activity activity, MedicationHistoryObject data);
        void updateMedicationHistory(Activity activity, MedicationHistoryObject data);
    }

}
