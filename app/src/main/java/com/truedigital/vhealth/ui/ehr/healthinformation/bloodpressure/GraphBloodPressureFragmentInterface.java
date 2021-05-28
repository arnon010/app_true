package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.model.ChartObject;
import com.truedigital.vhealth.model.GraphBloodPressureCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class GraphBloodPressureFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setGraphBloodPressure(ChartObject chart);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<GraphBloodPressureFragmentInterface.View>{
        void getGraphBloodPressure(GraphBloodPressureCriteriaObject criteria);
    }

}
