package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiBloodPressureObject;
import com.truedigital.vhealth.model.BloodPressureObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class FormBloodPressureFragmentPresenter extends BaseMvpPresenter<FormBloodPressureFragmentInterface.View>
        implements FormBloodPressureFragmentInterface.Presenter{

    public static FormBloodPressureFragmentInterface.Presenter create(){
        return new FormBloodPressureFragmentPresenter();
    }

    @Override
    public void newBloodPressure(BloodPressureObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiBloodPressureObject> call = getRetrofitToken(access_token).postBloodPressure(data.getPatientId(), data.getPatientMenuId(), data.getMenuCode(), data.getRecordDateString(), data.getSYSValue(), data.getDIAValue());
        call.enqueue(new Callback<ApiBloodPressureObject>() {
            @Override
            public void onResponse(Call<ApiBloodPressureObject> call, Response<ApiBloodPressureObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    BloodPressureObject data = response.body().getData();

                    getView().onNewSuccess(data);

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiBloodPressureObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updateBloodPressure(BloodPressureObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).putBloodPressure(data.getPatientId(), data.getBloodPressureId(), data.getRecordDateString(), data.getSYSValue(), data.getDIAValue());
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().onUpdateSuccess();

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void deleteBloodPressure(BloodPressureObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteBloodPressure(data.getBloodPressureId());
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().onDeleteSuccess();

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
