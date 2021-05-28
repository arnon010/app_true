package com.truedigital.vhealth.ui.ehr.laboratory;

import android.app.Activity;

import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class FormLaboratoryOtherFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void onUpdateSuccess(LaboratoryOtherObject data);
        void onNewSuccess(LaboratoryOtherObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormLaboratoryOtherFragmentInterface.View>{
        void newLaboratoryOther(Activity activity, LaboratoryOtherObject data);
        void updateLaboratoryOther(Activity activity, LaboratoryOtherObject data);
    }

}
