package com.truedigital.vhealth.ui.ehr.pregnanthistory;

import android.app.Activity;

import com.truedigital.vhealth.model.PregnantHistoryObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;



public class FormPregnantHistoryFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void onUpdateSuccess(PregnantHistoryObject data);
        void onNewSuccess(PregnantHistoryObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormPregnantHistoryFragmentInterface.View>{
        void newPregnantHistory(Activity activity, PregnantHistoryObject data);
        void updatePregnantHistory(Activity activity, PregnantHistoryObject data);
    }

}
