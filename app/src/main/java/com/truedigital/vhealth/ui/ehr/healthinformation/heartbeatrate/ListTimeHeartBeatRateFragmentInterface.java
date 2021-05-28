package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListTimeHeartBeatRateFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setListHeartBeatRate(ArrayList<HeartBeatRateObject> listData);
        void setHeartBeatRate(HeartBeatRateObject data);
        void checkDataEmpty();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListTimeHeartBeatRateFragmentInterface.View>{
        void getListHeartBeatRate(DailyHeartBeatRateObject daily);
        void getHeartBeatRate(int patientId, HeartBeatRateObject data);
    }
}
