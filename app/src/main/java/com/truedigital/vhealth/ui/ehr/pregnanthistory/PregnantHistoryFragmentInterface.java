package com.truedigital.vhealth.ui.ehr.pregnanthistory;

import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.model.PregnantHistoryCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class PregnantHistoryFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setPatientMenu(EhrMenuObject menu);
        void setDefaultDateCriteria(PregnantHistoryCriteriaObject criteria);
        void setPlaceSelectListCriteria(ArrayList<BottomSheetObject> criteria);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<PregnantHistoryFragmentInterface.View>{
        void getPatientMenu(int patientMenuId);
        void getPlaceSelectList(int patientId, int patientMenuId);
        void getDefaultDateCriteria(int patientId, int patientMenuId);
    }
}
