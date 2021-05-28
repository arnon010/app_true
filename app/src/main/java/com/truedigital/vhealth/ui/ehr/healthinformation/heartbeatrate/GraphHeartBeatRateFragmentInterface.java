package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import com.truedigital.vhealth.model.ChartObject;
import com.truedigital.vhealth.model.GraphHeartBeatRateCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;


public class GraphHeartBeatRateFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setGraphHeartBeatRate(ChartObject chart);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<GraphHeartBeatRateFragmentInterface.View>{
        void getGraphHeartBeatRate(GraphHeartBeatRateCriteriaObject criteria);
    }

}
