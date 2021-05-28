package com.truedigital.vhealth.ui.ehr.healthinformation.congenitaldisease;

import android.app.Activity;

import com.truedigital.vhealth.model.CongenitalDiseaseObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class FormCongenitalDiseaseFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void onUpdateSuccess(CongenitalDiseaseObject data);
        void onNewSuccess(CongenitalDiseaseObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormCongenitalDiseaseFragmentInterface.View>{
        void newCongenitalDisease(Activity activity, CongenitalDiseaseObject data);
        void updateCongenitalDisease(Activity activity, CongenitalDiseaseObject data);
    }
}
