package com.truedigital.vhealth.ui.ehr.doctornote;

import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.model.DoctorNoteCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListDoctorNoteFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setDoctorNoteList(ArrayList<DoctorNoteObject> listData);
        void setDoctorNoteDetail(DoctorNoteObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListDoctorNoteFragmentInterface.View>{
        void getDoctorNoteList(DoctorNoteCriteriaObject criteria);
        void getDoctorNoteDetail(DoctorNoteObject criteria);
    }
}
