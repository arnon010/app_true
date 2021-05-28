package com.truedigital.vhealth.ui.home.symptomtype;


import com.truedigital.vhealth.model.ApiListSymptom;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class ListSymptomTypeFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void updateSymptom(ArrayList<ApiListSymptom.FilterList> listData);
        void setFavorite(int id, boolean isFavorite);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListSymptomTypeFragmentInterface.View>{
        void loadSymptom();
        void callApiAddFavorite(final int id);
        void callApiDelFavorite(final int id);
    }
}
