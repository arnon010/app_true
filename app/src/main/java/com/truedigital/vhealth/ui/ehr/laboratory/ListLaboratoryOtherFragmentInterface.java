package com.truedigital.vhealth.ui.ehr.laboratory;

import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.model.LaboratoryOtherCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListLaboratoryOtherFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setLaboratoryOtherList(ArrayList<LaboratoryOtherObject> listData);
        void setLaboratoryOtherDetail(LaboratoryOtherObject data);
        void onDeleteSuccess(LaboratoryOtherObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListLaboratoryOtherFragmentInterface.View>{
        void getLaboratoryOtherList(LaboratoryOtherCriteriaObject criteria);
        void getLaboratoryOtherDetail(LaboratoryOtherObject criteria);
        void deleteLaboratoryOther(LaboratoryOtherObject data);
    }
}
