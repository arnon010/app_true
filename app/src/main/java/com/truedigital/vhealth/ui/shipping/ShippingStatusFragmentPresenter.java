package com.truedigital.vhealth.ui.shipping;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiShippingStatus;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ShippingStatusFragmentPresenter extends BaseMvpPresenter<ShippingStatusFragmentInterface.View>
        implements ShippingStatusFragmentInterface.Presenter{

    public static ShippingStatusFragmentInterface.Presenter create(){
        return new ShippingStatusFragmentPresenter();
    }


    @Override
    public void getShippingStatus(String appointmentNumber) {
        getView().showLoading();
        Call<ApiShippingStatus> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getShippingStatus(appointmentNumber);

        call.enqueue(new Callback<ApiShippingStatus>() {
            @Override
            public void onResponse(Call<ApiShippingStatus> call, Response<ApiShippingStatus> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        getView().updateStatus(response.body().getStatus());
                    }
                    else {
                        getView().showMessage(response);
                    }
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiShippingStatus> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

}
