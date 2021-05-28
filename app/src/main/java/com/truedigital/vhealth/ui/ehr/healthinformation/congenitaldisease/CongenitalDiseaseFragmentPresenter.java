package com.truedigital.vhealth.ui.ehr.healthinformation.congenitaldisease;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiCongenitalDiseaseObject;
import com.truedigital.vhealth.model.ApiListCongenitalDiseaseObject;
import com.truedigital.vhealth.model.CongenitalDiseaseCriteriaObject;
import com.truedigital.vhealth.model.CongenitalDiseaseObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class CongenitalDiseaseFragmentPresenter extends BaseMvpPresenter<CongenitalDiseaseFragmentInterface.View>
        implements CongenitalDiseaseFragmentInterface.Presenter {

    public static CongenitalDiseaseFragmentInterface.Presenter create() {
        return new CongenitalDiseaseFragmentPresenter();
    }


    @Override
    public void getCongenitalDiseaseList(CongenitalDiseaseCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListCongenitalDiseaseObject> call = getRetrofitToken(access_token).getCongenitalDiseaseList(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getPageIndex(), criteria.getPageSize());
        call.enqueue(new Callback<ApiListCongenitalDiseaseObject>() {
            @Override
            public void onResponse(Call<ApiListCongenitalDiseaseObject> call, Response<ApiListCongenitalDiseaseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<CongenitalDiseaseObject> dataList = response.body().getDataList();
                    getView().setCongenitalDiseaseList(dataList);

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
            public void onFailure(Call<ApiListCongenitalDiseaseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getCongenitalDiseaseDetail(CongenitalDiseaseObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiCongenitalDiseaseObject> call = getRetrofitToken(access_token).getCongenitalDiseaseDetail(criteria.getPatientId(), criteria.getCongenitalDiseaseId());
        call.enqueue(new Callback<ApiCongenitalDiseaseObject>() {
            @Override
            public void onResponse(Call<ApiCongenitalDiseaseObject> call, Response<ApiCongenitalDiseaseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    CongenitalDiseaseObject data = response.body().getData();
                    getView().setCongenitalDiseaseDetail(data);

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
            public void onFailure(Call<ApiCongenitalDiseaseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void deleteCongenitalDisease(final CongenitalDiseaseObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteCongenitalDisease(data.getCongenitalDiseaseId());
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().onDeleteSuccess(data);

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
