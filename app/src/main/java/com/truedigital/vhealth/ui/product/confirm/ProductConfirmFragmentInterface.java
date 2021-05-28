package com.truedigital.vhealth.ui.product.confirm;

import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.PaymentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

import java.util.List;

public class ProductConfirmFragmentInterface {


    public interface View extends BaseMvpInterface.View{
        int getProductId();
        void setData(List<ItemProductDao> listData);
        void setData(ItemProductDao data);
        void openListProducts(int id);
        void openProductConfirm(int id);

        void openPaymentChannel();
        void openPaymentCreditCard();
        void openPaymentBankTransfer();

        void openSendAddress();

        List<ItemProductDao> getProducts();
        String getShippingName();
        String getShippingAddress();
        //..
        String getDataFromFile(String filename);
        List<ItemProductDao> getData(String json, boolean isShowLog);


        void callOmise(PaymentDao paymentData);
        String getOmiseToken();
        void showSuccess(String AurhorizeUri);

    }

    public interface Presenter extends BaseMvpInterface.Presenter<ProductConfirmFragmentInterface.View>{
        void loadData();
        //ItemProductDao loadData(int position);
        void onPaymentButtonClick(PaymentDao paymentData);
    }
}
