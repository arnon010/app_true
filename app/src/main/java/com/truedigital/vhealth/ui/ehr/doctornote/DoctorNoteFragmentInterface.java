package com.truedigital.vhealth.ui.ehr.doctornote;

import com.truedigital.vhealth.model.BottomSheetObject;
import com.truedigital.vhealth.model.DoctorNoteCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class DoctorNoteFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setDefaultDateCriteria(DoctorNoteCriteriaObject criteria);
        void setDoctorSelectListCriteria(ArrayList<BottomSheetObject> criteria);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<DoctorNoteFragmentInterface.View>{
        void getDoctorSelectList(int patientId);
        void getDefaultDateCriteria(int patientId);
    }
}
