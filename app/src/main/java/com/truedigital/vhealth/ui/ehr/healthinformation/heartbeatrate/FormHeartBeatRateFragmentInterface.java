package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.model.HeartBeatRateTypeObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class FormHeartBeatRateFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setTypeList(ArrayList<HeartBeatRateTypeObject> data);
        void onUpdateSuccess();
        void onNewSuccess(HeartBeatRateObject data);
        void onDeleteSuccess();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<FormHeartBeatRateFragmentInterface.View>{
        void getTypeList();
        void newHeartBeatRate(HeartBeatRateObject data);
        void updateHeartBeatRate(HeartBeatRateObject data);
        void deleteHeartBeatRate(HeartBeatRateObject data);
    }
}
