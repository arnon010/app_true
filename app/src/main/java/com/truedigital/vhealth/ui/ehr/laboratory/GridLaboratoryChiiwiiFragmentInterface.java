package com.truedigital.vhealth.ui.ehr.laboratory;

import com.truedigital.vhealth.model.GridLaboratoryChiiwiiCriteriaObject;
import com.truedigital.vhealth.model.GridLaboratoryChiiwiiObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

public class GridLaboratoryChiiwiiFragmentInterface {
    public interface View extends BaseMvpInterface.View{
        void setGridDataLaboratoryChiiwii(GridLaboratoryChiiwiiObject data);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<GridLaboratoryChiiwiiFragmentInterface.View>{
        void getGridDataLaboratoryChiiwii(GridLaboratoryChiiwiiCriteriaObject criteria);
    }
}
