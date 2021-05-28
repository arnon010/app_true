package com.truedigital.vhealth.ui.ehr.medicineallergy;

import com.truedigital.vhealth.model.MedicineAllergyCriteriaObject;
import com.truedigital.vhealth.model.MedicineAllergyObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class MedicineAllergyFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setMedicineAllergyList(ArrayList<MedicineAllergyObject> listData);
        void setMedicineAllergyDetail(MedicineAllergyObject data);
        void onDeleteSuccess(MedicineAllergyObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<MedicineAllergyFragmentInterface.View>{
        void getMedicineAllergyList(MedicineAllergyCriteriaObject criteria);
        void getMedicineAllergyDetail(MedicineAllergyObject criteria);
        void deleteMedicineAllergy(MedicineAllergyObject data);
    }

}