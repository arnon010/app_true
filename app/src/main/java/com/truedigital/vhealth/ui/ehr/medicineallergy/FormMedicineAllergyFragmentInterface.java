package com.truedigital.vhealth.ui.ehr.medicineallergy;

import android.app.Activity;

import com.truedigital.vhealth.model.MedicineAllergyObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class FormMedicineAllergyFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void onUpdateSuccess(MedicineAllergyObject data);
        void onNewSuccess(MedicineAllergyObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormMedicineAllergyFragmentInterface.View>{
        void newMedicineAllergy(Activity activity, MedicineAllergyObject data);
        void updateMedicineAllergy(Activity activity, MedicineAllergyObject data);
    }

}
