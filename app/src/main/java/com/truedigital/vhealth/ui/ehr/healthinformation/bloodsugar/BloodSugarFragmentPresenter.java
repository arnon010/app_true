package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiTodayBloodGlucoseObject;
import com.truedigital.vhealth.model.TodayBloodGlucoseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class BloodSugarFragmentPresenter extends BaseMvpPresenter<BloodSugarFragmentInterface.View>
        implements BloodSugarFragmentInterface.Presenter {

    public static BloodSugarFragmentInterface.Presenter create() {
        return new BloodSugarFragmentPresenter();
    }

    @Override
    public void getTodayBloodGlucose(int patientId, int patientMenuId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiTodayBloodGlucoseObject> call = getRetrofitToken(access_token).getTodayBloodGlucose(patientId, patientMenuId);
        call.enqueue(new Callback<ApiTodayBloodGlucoseObject>() {
            @Override
            public void onResponse(Call<ApiTodayBloodGlucoseObject> call, Response<ApiTodayBloodGlucoseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    TodayBloodGlucoseObject data = response.body().getData();

                    getView().setTodayBloodGlucose(data);

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
            public void onFailure(Call<ApiTodayBloodGlucoseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
