package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiBloodSugarObject;
import com.truedigital.vhealth.model.ApiListBloodSugarObject;
import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListTimeBloodSugarFragmentPresenter extends BaseMvpPresenter<ListTimeBloodSugarFragmentInterface.View>
        implements ListTimeBloodSugarFragmentInterface.Presenter {

    public static ListTimeBloodSugarFragmentInterface.Presenter create() {
        return new ListTimeBloodSugarFragmentPresenter();
    }

    @Override
    public void getListBloodSugar(DailyBloodSugarObject daily) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListBloodSugarObject> call = getRetrofitToken(access_token).getBloodSugarList(daily.getPatientId(), daily.getPatientMenuId(), daily.getDailyDateString());
        call.enqueue(new Callback<ApiListBloodSugarObject>() {
            @Override
            public void onResponse(Call<ApiListBloodSugarObject> call, Response<ApiListBloodSugarObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<BloodSugarObject> dataList = response.body().getDataList();

                    for (BloodSugarObject data : dataList) {

                        if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                            data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                        }
                    }

                    getView().setListBloodSugar(dataList);

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
            public void onFailure(Call<ApiListBloodSugarObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

    @Override
    public void getBloodSugar(int patientId, BloodSugarObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiBloodSugarObject> call = getRetrofitToken(access_token).getBloodSugar(patientId, data.getBloodSugarId());
        call.enqueue(new Callback<ApiBloodSugarObject>() {
            @Override
            public void onResponse(Call<ApiBloodSugarObject> call, Response<ApiBloodSugarObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    BloodSugarObject data = response.body().getData();

                    if (data.getRecordDateString() != null && data.getRecordDateString() != "") {

                        data.setRecordDate(ConvertDate.StrToDateFormat(data.getRecordDateString()));
                    }

                    getView().setBloodSugar(data);

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
            public void onFailure(Call<ApiBloodSugarObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
