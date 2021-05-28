package com.truedigital.vhealth.ui.appointment.detail;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.appointment.ApiItemAppointmentDao;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;

public class AppointmentDetailFragmentPresenter extends BaseMvpPresenter<AppointmentDetailFragmentInterface.View>
        implements AppointmentDetailFragmentInterface.Presenter {

    private ItemAppointmentDao appointmentDao;

    public static AppointmentDetailFragmentInterface.Presenter create() {
        return new AppointmentDetailFragmentPresenter();
    }

    @Override
    public void getAppointmentInfo(String appointmentNumber) {
        if (appointmentDao != null) {
            getView().setData(appointmentDao);
            return;
        }

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        Call<ApiItemAppointmentDao> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getAppointmentInfo(appointmentNumber);
        call.enqueue(new Callback<ApiItemAppointmentDao>() {
            @Override
            public void onResponse(Call<ApiItemAppointmentDao> call, Response<ApiItemAppointmentDao> response) {
                if (response.isSuccessful()) {
                    appointmentDao = response.body().getData();
                    getView().setData(appointmentDao);
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
}
