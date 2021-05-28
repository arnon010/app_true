package com.truedigital.vhealth.ui.ehr.vaccinationhistory;

import com.truedigital.vhealth.model.GridVaccinationHistoryCriteriaObject;
import com.truedigital.vhealth.model.GridVaccinationHistoryObject;
import com.truedigital.vhealth.model.VaccinationHistoryObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;


public class GridVaccinationHistoryFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setGridDataVaccinationHistory(GridVaccinationHistoryObject data);
        void onActiveSuccess(VaccinationHistoryObject data);
        void onInActiveSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<GridVaccinationHistoryFragmentInterface.View>{
        void getGridDataVaccinationHistory(GridVaccinationHistoryCriteriaObject criteria);
        void activeDataVaccinationHistory(VaccinationHistoryObject data, int patientId, int patientMenuId, String menuCode);
        void inActiveDataVaccinationHistory(int patientVaccinationId);
    }
}
