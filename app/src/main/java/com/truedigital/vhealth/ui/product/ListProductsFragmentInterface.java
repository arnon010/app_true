package com.truedigital.vhealth.ui.product;

import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ListProductsFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        int getProductGroupId();
        void setData(List<ItemProductDao> listData);
        void openListProducts(int id);
        void openProductDetail(int id);
        //..
        String getDataFromFile(String filename);
        List<ItemProductDao> getData(String json, boolean isShowLog);

    }

    public interface Presenter extends BaseMvpInterface.Presenter<ListProductsFragmentInterface.View>{
        void loadData();
        //ItemProductDao loadData(int position);
    }
}
