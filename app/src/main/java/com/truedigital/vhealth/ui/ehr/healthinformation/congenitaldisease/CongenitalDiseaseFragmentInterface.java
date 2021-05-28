package com.truedigital.vhealth.ui.ehr.healthinformation.congenitaldisease;

import com.truedigital.vhealth.model.CongenitalDiseaseCriteriaObject;
import com.truedigital.vhealth.model.CongenitalDiseaseObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class CongenitalDiseaseFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setCongenitalDiseaseList(ArrayList<CongenitalDiseaseObject> listData);
        void setCongenitalDiseaseDetail(CongenitalDiseaseObject data);
        void onDeleteSuccess(CongenitalDiseaseObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<CongenitalDiseaseFragmentInterface.View>{
        void getCongenitalDiseaseList(CongenitalDiseaseCriteriaObject criteria);
        void getCongenitalDiseaseDetail(CongenitalDiseaseObject criteria);
        void deleteCongenitalDisease(CongenitalDiseaseObject data);
    }

}
