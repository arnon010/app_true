package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListDailyHeartBeatRateFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setListDailyHeartBeatRate(ArrayList<DailyHeartBeatRateObject> listData);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListDailyHeartBeatRateFragmentInterface.View>{
        void getListDailyHeartBeatRate(int patientId, int patientMenuId);
    }
}
