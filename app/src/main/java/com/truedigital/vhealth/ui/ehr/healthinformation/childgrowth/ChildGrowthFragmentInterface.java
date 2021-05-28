package com.truedigital.vhealth.ui.ehr.healthinformation.childgrowth;

import com.truedigital.vhealth.model.ChildGrowthCriteriaObject;
import com.truedigital.vhealth.model.ChildGrowthHistoryObject;
import com.truedigital.vhealth.model.GridChildGrowthHistoryObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class ChildGrowthFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setGridDataChildGrowthHistory(GridChildGrowthHistoryObject data);
        void onActiveSuccess(ChildGrowthHistoryObject data);
        void onInActiveSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ChildGrowthFragmentInterface.View>{
        void getGridDataChildGrowthHistory(ChildGrowthCriteriaObject criteria);
        void activeDataChildGrowthHistory(ChildGrowthHistoryObject data, int patientId, int patientMenuId, String menuCode);
        void inActiveDataChildGrowthHistory(int patientChildGrowthId);
    }
}
