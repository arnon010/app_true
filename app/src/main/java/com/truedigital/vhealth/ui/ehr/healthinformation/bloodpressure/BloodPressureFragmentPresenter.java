package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiTodayBloodPressureObject;
import com.truedigital.vhealth.model.TodayBloodPressureObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class BloodPressureFragmentPresenter extends BaseMvpPresenter<BloodPressureFragmentInterface.View>
        implements BloodPressureFragmentInterface.Presenter{

    public static BloodPressureFragmentInterface.Presenter create(){
        return new BloodPressureFragmentPresenter();
    }

    @Override
    public void getTodayBloodPressure(int patientId, int patientMenuId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiTodayBloodPressureObject> call = getRetrofitToken(access_token).getTodayBloodPressure(patientId, patientMenuId);
        call.enqueue(new Callback<ApiTodayBloodPressureObject>() {
            @Override
            public void onResponse(Call<ApiTodayBloodPressureObject> call, Response<ApiTodayBloodPressureObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    TodayBloodPressureObject data = response.body().getData();

                    getView().setTodayBloodPressure(data);

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
            public void onFailure(Call<ApiTodayBloodPressureObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }


}
