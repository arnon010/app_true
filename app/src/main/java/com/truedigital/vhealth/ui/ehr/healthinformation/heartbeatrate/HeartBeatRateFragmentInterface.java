package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.model.TodayHeartRestingRateObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;


public class HeartBeatRateFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setTodayHeartRestingRate(TodayHeartRestingRateObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<HeartBeatRateFragmentInterface.View>{
        void getTodayHeartRestingRate(int patientId, int patientMenuId);
    }

}
