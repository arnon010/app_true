package com.truedigital.vhealth.ui.doctornote.confirm;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRecommendMedicine;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRecommendProduct;
import com.truedigital.vhealth.model.appointment.ApiAppointmentShortnote;
import com.truedigital.vhealth.model.appointment.ItemAppointmentShortnoteDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.addRequestBody;
import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;

public class DoctorNoteConfirmFragmentPresenter extends BaseMvpPresenter<DoctorNoteConfirmFragmentInterface.View>
        implements DoctorNoteConfirmFragmentInterface.Presenter{

    public static DoctorNoteConfirmFragmentInterface.Presenter create(){
        return new DoctorNoteConfirmFragmentPresenter();
    }

    @Override
    public void loadData() {
        String appointmentNumber = getView().getAppointmentNumber();

        getAppointmentShortnote(appointmentNumber);
        //getAppointmentRecommendProduct(appointmentNumber);
        //getAppointmentRecommendMedicine(appointmentNumber);
    }

    @Override
    public void getAppointmentShortnote(final String appointmentNumber) {
        Call<ApiAppointmentShortnote> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getAppointmentShortnote(appointmentNumber);
        call.enqueue(new Callback<ApiAppointmentShortnote>() {
            @Override
            public void onResponse(Call<ApiAppointmentShortnote> call, Response<ApiAppointmentShortnote> response) {
                if (response.isSuccessful()) {
                    getView().setData(response.body().getData());

                    getAppointmentRecommendProduct(appointmentNumber);
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentShortnote> call, Throwable t) {

            }
        });
    }

    @Override
    public void updateAppointmentShortnote(String appointmentNumber, String shortNote) {
        getView().showLoading();

        List<Integer> deleteAttachments = new ArrayList<>();
        ItemAppointmentShortnoteDao request = new ItemAppointmentShortnoteDao();
        request.setShortNote(shortNote);
        //request.setDeleteAttachments(deleteAttachments);

        String body = new Gson().toJson(request);
        RequestBody requestBody = addRequestBody(body);

        Call<NormalResponseObject> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).updateAppointmentShortnote(appointmentNumber, addRequestBody(body));
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
            }
        });
    }

    @Override
    public void getAppointmentRecommendProduct(final String appointmentNumber) {
        getView().showLoading();
        Call<ApiAppointmentRecommendProduct> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getAppointmentRecommendProduct(appointmentNumber);
        call.enqueue(new Callback<ApiAppointmentRecommendProduct>() {
            @Override
            public void onResponse(Call<ApiAppointmentRecommendProduct> call, Response<ApiAppointmentRecommendProduct> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setDataProduct(response.body().getData());
                    getAppointmentRecommendMedicine(appointmentNumber);
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentRecommendProduct> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getAppointmentRecommendMedicine(String appointmentNumber) {
        getView().showLoading();
        Call<ApiAppointmentRecommendMedicine> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getAppointmentRecommendMedicine(appointmentNumber);
        call.enqueue(new Callback<ApiAppointmentRecommendMedicine>() {
            @Override
            public void onResponse(Call<ApiAppointmentRecommendMedicine> call, Response<ApiAppointmentRecommendMedicine> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setDataMedicine(response.body().getData());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentRecommendMedicine> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void sendDoctorNote(String appointmentNumber) {
        getView().showLoading();
        Call<NormalResponseObject> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).sendDoctorNote(appointmentNumber);
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
            }
        });
    }
}
