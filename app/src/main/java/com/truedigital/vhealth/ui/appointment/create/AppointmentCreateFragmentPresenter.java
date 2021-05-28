package com.truedigital.vhealth.ui.appointment.create;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListDoctorAvailableTimes;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class AppointmentCreateFragmentPresenter extends BaseMvpPresenter<AppointmentCreateFragmentInterface.View>
        implements AppointmentCreateFragmentInterface.Presenter {

    private ApiListDoctorAvailableTimes apiListDoctorAvailableTimes;

    public static AppointmentCreateFragmentInterface.Presenter create() {
        return new AppointmentCreateFragmentPresenter();
    }

    @Override
    public void loadData() {
        callGetDoctorAvailableTimes(getView().getDoctorId());
    }

    private void callGetDoctorAvailableTimes(int doctorId) {
        if (apiListDoctorAvailableTimes != null) {
            getView().setDoctorAvailableTimes(apiListDoctorAvailableTimes.getListData());
            return;
        }

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        getView().showLoading();
        Call<ApiListDoctorAvailableTimes> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getDoctorAvailabelTimes(doctorId, 3);
        call.enqueue(new Callback<ApiListDoctorAvailableTimes>() {
            @Override
            public void onResponse(Call<ApiListDoctorAvailableTimes> call, Response<ApiListDoctorAvailableTimes> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    apiListDoctorAvailableTimes = response.body();
                    getView().setDoctorAvailableTimes(apiListDoctorAvailableTimes.getListData());
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiListDoctorAvailableTimes> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
