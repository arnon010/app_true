package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.model.ChartObject;
import com.truedigital.vhealth.model.GraphBloodSugarCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class GraphBloodSugarFragmentInterface {

    public interface View extends BaseMvpInterface.View{
        void setGraphBloodSugar(ChartObject chart);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<GraphBloodSugarFragmentInterface.View>{
        void getGraphBloodSugar(GraphBloodSugarCriteriaObject criteria);
    }

}
