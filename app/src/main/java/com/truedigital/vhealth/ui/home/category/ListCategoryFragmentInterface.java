package com.truedigital.vhealth.ui.home.category;

import com.truedigital.vhealth.model.ItemCategoryDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ListCategoryFragmentInterface {

    public interface View extends BaseMvpInterface.View {
        void openListDoctor(ItemCategoryDao itemCategoryDao);

        void setData(List<ItemCategoryDao> listData);

        String getDataFromFile(String filename);

        List<ItemCategoryDao> getData(String json, boolean isShowLog);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListCategoryFragmentInterface.View> {
        void loadData();
    }
}
