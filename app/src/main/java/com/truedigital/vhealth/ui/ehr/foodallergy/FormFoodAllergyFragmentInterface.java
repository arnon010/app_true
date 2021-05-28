package com.truedigital.vhealth.ui.ehr.foodallergy;

import android.app.Activity;

import com.truedigital.vhealth.model.FoodAllergyObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class FormFoodAllergyFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void onUpdateSuccess(FoodAllergyObject data);
        void onNewSuccess(FoodAllergyObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormFoodAllergyFragmentInterface.View>{
        void newFoodAllergy(Activity activity, FoodAllergyObject data);
        void updateFoodAllergy(Activity activity, FoodAllergyObject data);
    }

}
