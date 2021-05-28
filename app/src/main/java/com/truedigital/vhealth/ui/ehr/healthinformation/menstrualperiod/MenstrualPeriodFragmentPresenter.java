package com.truedigital.vhealth.ui.ehr.healthinformation.menstrualperiod;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListMenstruationObject;
import com.truedigital.vhealth.model.ApiMenstruationObject;
import com.truedigital.vhealth.model.MenstruationObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class MenstrualPeriodFragmentPresenter extends BaseMvpPresenter<MenstrualPeriodFragmentInterface.View>
        implements MenstrualPeriodFragmentInterface.Presenter {

    public static MenstrualPeriodFragmentInterface.Presenter create() {
        return new MenstrualPeriodFragmentPresenter();
    }

    @Override
    public void getMenstrualPeriodList(int patientId, int patientMenuId) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListMenstruationObject> call = getRetrofitToken(access_token).getMenstrualPeriodList(patientId, patientMenuId);
        call.enqueue(new Callback<ApiListMenstruationObject>() {
            @Override
            public void onResponse(Call<ApiListMenstruationObject> call, Response<ApiListMenstruationObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<MenstruationObject> dataList = response.body().getDataList();

                    for (MenstruationObject data : dataList) {

                        if (data.getMenstruationDateString() != null && data.getMenstruationDateString() != "") {

                            data.setMenstruationDate(ConvertDate.StrToDateFormat(data.getMenstruationDateString()));
                        }
                    }

                    getView().setMenstrualPeriodList(dataList);

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
            public void onFailure(Call<ApiListMenstruationObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void newMenstrualPeriod(int patientId, int patientMenuId, String menuCode, MenstruationObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiMenstruationObject> call = getRetrofitToken(access_token).postMenstrualPeriod(patientId, patientMenuId, menuCode, data.getMenstruationDateString());
        call.enqueue(new Callback<ApiMenstruationObject>() {
            @Override
            public void onResponse(Call<ApiMenstruationObject> call, Response<ApiMenstruationObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    MenstruationObject data = response.body().getData();

                    getView().onNewSuccess(data);

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().onNewError();
                        getView().showMessage(response);
                    }

                    getView().hideLoading();
                }

            }

            @Override
            public void onFailure(Call<ApiMenstruationObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void deleteMenstrualPeriod(int patientId, MenstruationObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteMenstrualPeriod(data.getMenstruationId());
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

                    getView().hideLoading();
                }

            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }


}
