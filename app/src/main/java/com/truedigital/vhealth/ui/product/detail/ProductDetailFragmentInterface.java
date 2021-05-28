package com.truedigital.vhealth.ui.product.detail;

import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ProductDetailFragmentInterface {

    public interface View extends BaseMvpInterface.View {
        String getAppointmentNumber();

        int getProductId();

        void setData(List<ItemProductDao> listData);

        void setData(ItemProductDao data);

        void openListProducts(int id);

        void openProductConfirm(int id);

        String getDataFromFile(String filename);

        List<ItemProductDao> getData(String json, boolean isShowLog);

        void showSuccess();

        void onSuccess();

    }

    public interface Presenter extends BaseMvpInterface.Presenter<ProductDetailFragmentInterface.View> {
        void loadData();

        //ItemProductDao loadData(int position);
        void onBuyButtonClick();
    }
}
