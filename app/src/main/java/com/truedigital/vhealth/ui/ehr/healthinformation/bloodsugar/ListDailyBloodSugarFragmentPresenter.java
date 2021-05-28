package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListDailyBloodSugarObject;
import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListDailyBloodSugarFragmentPresenter extends BaseMvpPresenter<ListDailyBloodSugarFragmentInterface.View>
        implements ListDailyBloodSugarFragmentInterface.Presenter {

    public static ListDailyBloodSugarFragmentInterface.Presenter create() {
        return new ListDailyBloodSugarFragmentPresenter();
    }

    @Override
    public void getListDailyBloodSugar(int patientId, int patientMenuId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListDailyBloodSugarObject> call = getRetrofitToken(access_token).getDailyBloodSugar(patientId, patientMenuId);
        call.enqueue(new Callback<ApiListDailyBloodSugarObject>() {
            @Override
            public void onResponse(Call<ApiListDailyBloodSugarObject> call, Response<ApiListDailyBloodSugarObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<DailyBloodSugarObject> dataList = response.body().getDataList();

                    for (DailyBloodSugarObject data : dataList) {

                        if (data.getDailyDateString() != null && data.getDailyDateString() != "") {

                            data.setDailyDate(ConvertDate.StrToDateFormat(data.getDailyDateString()));
                        }
                    }

                    getView().setListDailyBloodSugar(dataList);

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
            public void onFailure(Call<ApiListDailyBloodSugarObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }


}
