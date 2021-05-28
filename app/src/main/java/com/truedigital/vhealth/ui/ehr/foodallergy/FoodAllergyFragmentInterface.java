package com.truedigital.vhealth.ui.ehr.foodallergy;

import com.truedigital.vhealth.model.FoodAllergyCriteriaObject;
import com.truedigital.vhealth.model.FoodAllergyObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class FoodAllergyFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setFoodAllergyList(ArrayList<FoodAllergyObject> listData);
        void setFoodAllergyDetail(FoodAllergyObject data);
        void onDeleteSuccess(FoodAllergyObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FoodAllergyFragmentInterface.View>{
        void getFoodAllergyList(FoodAllergyCriteriaObject criteria);
        void getFoodAllergyDetail(FoodAllergyObject criteria);
        void deleteFoodAllergy(FoodAllergyObject data);
    }

}
