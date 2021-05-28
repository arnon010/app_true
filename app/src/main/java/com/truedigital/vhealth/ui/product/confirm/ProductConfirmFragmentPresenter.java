package com.truedigital.vhealth.ui.product.confirm;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.PaymentDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentResponse;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ProductConfirmFragmentPresenter extends BaseMvpPresenter<ProductConfirmFragmentInterface.View>
        implements ProductConfirmFragmentInterface.Presenter{

    private List<ItemProductDao> listData;

    public static ProductConfirmFragmentInterface.Presenter create(){
        return new ProductConfirmFragmentPresenter();
    }

    @Override
    public void loadData() {
        //setTempData();
        //
        callGetProduct(getView().getProductId());
    }

    @Override
    public void onPaymentButtonClick(PaymentDao paymentData) {
        //getView().showSuccess();
        callApiBuyProduct(paymentData);
    }

    private void callApiBuyProduct(PaymentDao paymentData) {
        getView().showLoading();

        String products = new Gson().toJson(getView().getProducts());
        String omiseToken = getView().getOmiseToken();
        String discountCode = paymentData.getDiscountCode();
        String paymentCode = paymentData.getPaymentCode();
        String shippingLocation = paymentData.getShippingLocation();
        String shippingName = paymentData.getShippingName();
        boolean isPaymentCode = paymentData.isPaymentCode();
        String appointmentNumber = paymentData.getAppointmentNumber();
        Call<ApiAppointmentResponse> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postBuyProducts(
                products,
                omiseToken,
                discountCode,
                paymentCode,
                shippingLocation,
                shippingName,
                isPaymentCode,
                appointmentNumber
        );
        call.enqueue(new Callback<ApiAppointmentResponse>() {
            @Override
            public void onResponse(Call<ApiAppointmentResponse> call, Response<ApiAppointmentResponse> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().showSuccess(response.body().getAurhorizeUri());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentResponse> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });

        /*
        Call<NormalResponseObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postBuyProducts(
                products,
                omiseToken,
                discountCode,
                paymentCode,
                shippingLocation,
                shippingName,
                isPaymentCode,
                appointmentNumber
        );
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().showSuccess();
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
        */
    }

    private void callGetProduct(int productId) {
        getView().showLoading();

        Call<ItemProductDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getProduct(productId);
        call.enqueue(new Callback<ItemProductDao>() {
            @Override
            public void onResponse(Call<ItemProductDao> call, Response<ItemProductDao> response) {
                getView().hideLoading();

                if (response.isSuccessful()) {

                    getView().setData(response.body());
                    //getView().setData(listData.get(1));
                }
                else {
                    getView().showMessage(response);
                }

                //getView().setData(listData.get(2));
            }

            @Override
            public void onFailure(Call<ItemProductDao> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

    private void setTempData() {
        String json = getView().getDataFromFile("seed/products.json");
        listData = getView().getData(json, true);
    }


}
