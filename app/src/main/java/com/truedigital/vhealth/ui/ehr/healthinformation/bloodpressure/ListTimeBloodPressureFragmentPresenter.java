package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiBloodPressureObject;
import com.truedigital.vhealth.model.ApiListBloodPressureObject;
import com.truedigital.vhealth.model.BloodPressureObject;
import com.truedigital.vhealth.model.DailyBloodPressureObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListTimeBloodPressureFragmentPresenter extends BaseMvpPresenter<ListTimeBloodPressureFragmentInterface.View>
        implements ListTimeBloodPressureFragmentInterface.Presenter {

    public static ListTimeBloodPressureFragmentInterface.Presenter create() {
        return new ListTimeBloodPressureFragmentPresenter();
    }

    @Override
    public void getListBloodPressure(DailyBloodPressureObject daily) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListBloodPressureObject> call = getRetrofitToken(access_token).getBloodPressureList(daily.getPatientId(), daily.getPatientMenuId(), daily.getDailyDateString());
        call.enqueue(new Callback<ApiListBloodPressureObject>() {
            @Override
            public void onResponse(Call<ApiListBloodPressureObject> call, Response<ApiListBloodPressureObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<BloodPressureObject> dataList = response.body().getDataList();

                    for (BloodPressureObject data : dataList) {

                        if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                            data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                        }
                    }

                    getView().setListBloodPressure(dataList);

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
                getView().checkDataEmpty();
            }

            @Override
            public void onFailure(Call<ApiListBloodPressureObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

    @Override
    public void getBloodPressure(int patientId, BloodPressureObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiBloodPressureObject> call = getRetrofitToken(access_token).getBloodPressure(patientId, data.getBloodPressureId());
        call.enqueue(new Callback<ApiBloodPressureObject>() {
            @Override
            public void onResponse(Call<ApiBloodPressureObject> call, Response<ApiBloodPressureObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    BloodPressureObject data = response.body().getData();

                    if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                        data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                    }

                    getView().setBloodPressure(data);

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

}
