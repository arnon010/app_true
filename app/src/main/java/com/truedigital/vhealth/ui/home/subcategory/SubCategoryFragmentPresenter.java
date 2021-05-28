package com.truedigital.vhealth.ui.home.subcategory;

import com.truedigital.vhealth.model.ItemSubCategoryDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.List;

public class SubCategoryFragmentPresenter extends BaseMvpPresenter<SubCategoryFragmentInterface.View>
        implements SubCategoryFragmentInterface.Presenter{

    private List<ItemSubCategoryDao> listData;

    public static SubCategoryFragmentInterface.Presenter create(){
        return new SubCategoryFragmentPresenter();
    }

    @Override
    public void loadData() {
        String json = getView().getDataFromFile("seed/subcategory.json");
        listData = getView().getData(json,false);
        getView().updateCategory(listData);
    }
}
