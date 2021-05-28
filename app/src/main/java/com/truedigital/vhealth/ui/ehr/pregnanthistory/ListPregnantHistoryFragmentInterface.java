package com.truedigital.vhealth.ui.ehr.pregnanthistory;

import com.truedigital.vhealth.model.PregnantHistoryObject;
import com.truedigital.vhealth.model.PregnantHistoryCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListPregnantHistoryFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setPregnantHistoryList(ArrayList<PregnantHistoryObject> listData);
        void setPregnantHistoryDetail(PregnantHistoryObject data);
        void onDeleteSuccess(PregnantHistoryObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListPregnantHistoryFragmentInterface.View>{
        void getPregnantHistoryList(PregnantHistoryCriteriaObject criteria);
        void getPregnantHistoryDetail(PregnantHistoryObject criteria);
        void deletePregnantHistory(PregnantHistoryObject data);
    }
}
