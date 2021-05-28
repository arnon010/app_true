package com.truedigital.vhealth.ui.home.listnews;


import com.truedigital.vhealth.model.ApiNewsObject;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.ArrayList;

public class NewsFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void updateNews(ArrayList<ApiNewsObject.Lists> listNews);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<NewsFragmentInterface.View>{
        void loadNews();
    }
}
