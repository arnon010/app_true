package com.truedigital.vhealth.ui.appointment.cancel;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.appointment.ApiAppointmentCancel;
import com.truedigital.vhealth.model.appointment.ApiItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;

public class AppointmentCancelFragmentPresenter extends BaseMvpPresenter<AppointmentCancelFragmentInterface.View>
        implements AppointmentCancelFragmentInterface.Presenter {

    public static AppointmentCancelFragmentInterface.Presenter create() {
        return new AppointmentCancelFragmentPresenter();
    }

    @Override
    public void getAppointmentInfo(String appointmentNumber) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }
        Call<ApiItemAppointmentDao> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getAppointmentInfo(appointmentNumber);
        call.enqueue(new Callback<ApiItemAppointmentDao>() {
            @Override
            public void onResponse(Call<ApiItemAppointmentDao> call, Response<ApiItemAppointmentDao> response) {
                if (response.isSuccessful()) {
                    getView().setData(response.body().getData());
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiItemAppointmentDao> call, Throwable t) {
                getView().showMessage("" + t.getMessage());
            }
        });
    }

    @Override
    public void callCancelAppointment(String appointmentNumber, String reason) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        getView().showLoading();
        ApiAppointmentCancel request = new ApiAppointmentCancel();
        if (AppManager.getDataManager().isDoctor()) {
            request.setPatient(false);
        } else {
            request.setPatient(true);
        }

        request.setReason(reason);
        request.setBefore24Hour(true);

        Call<NormalResponseObject> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).cancelAppointment(appointmentNumber, request);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().showSuccess();
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
