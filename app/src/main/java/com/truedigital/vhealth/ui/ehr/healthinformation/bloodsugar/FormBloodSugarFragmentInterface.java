package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class FormBloodSugarFragmentInterface {
    public interface View extends BaseMvpInterface.View {
        void onUpdateSuccess();
        void onNewSuccess(BloodSugarObject data);
        void onDeleteSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormBloodSugarFragmentInterface.View> {
        void newBloodSugar(BloodSugarObject data);
        void updateBloodSugar(BloodSugarObject data);
        void deleteBloodSugar(BloodSugarObject data);
    }
}
