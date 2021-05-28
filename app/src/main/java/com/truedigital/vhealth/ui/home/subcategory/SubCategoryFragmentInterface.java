package com.truedigital.vhealth.ui.home.subcategory;

import com.truedigital.vhealth.model.ItemCategoryDao;
import com.truedigital.vhealth.model.ItemSubCategoryDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class SubCategoryFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        void openListDoctor(ItemCategoryDao itemCategoryDao);
        void updateCategory(List<ItemSubCategoryDao> listData);
        String getDataFromFile(String filename);
        List<ItemSubCategoryDao> getData(String json, boolean isShowLog);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<SubCategoryFragmentInterface.View>{
        void loadData();
    }
}
