package com.truedigital.vhealth.ui.ehr.foodallergy;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiFoodAllergyObject;
import com.truedigital.vhealth.model.ApiListFoodAllergyObject;
import com.truedigital.vhealth.model.FoodAllergyCriteriaObject;
import com.truedigital.vhealth.model.FoodAllergyObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class FoodAllergyFragmentPresenter extends BaseMvpPresenter<FoodAllergyFragmentInterface.View>
        implements FoodAllergyFragmentInterface.Presenter {

    public static FoodAllergyFragmentInterface.Presenter create() {
        return new FoodAllergyFragmentPresenter();
    }


    @Override
    public void getFoodAllergyList(FoodAllergyCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListFoodAllergyObject> call = getRetrofitToken(access_token).getFoodAllergyList(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getPageIndex(), criteria.getPageSize());
        call.enqueue(new Callback<ApiListFoodAllergyObject>() {
            @Override
            public void onResponse(Call<ApiListFoodAllergyObject> call, Response<ApiListFoodAllergyObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<FoodAllergyObject> dataList = response.body().getDataList();
                    getView().setFoodAllergyList(dataList);

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
            public void onFailure(Call<ApiListFoodAllergyObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getFoodAllergyDetail(FoodAllergyObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiFoodAllergyObject> call = getRetrofitToken(access_token).getFoodAllergyDetail(criteria.getPatientId(), criteria.getFoodAllergyId());
        call.enqueue(new Callback<ApiFoodAllergyObject>() {
            @Override
            public void onResponse(Call<ApiFoodAllergyObject> call, Response<ApiFoodAllergyObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    FoodAllergyObject data = response.body().getData();
                    getView().setFoodAllergyDetail(data);

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
            public void onFailure(Call<ApiFoodAllergyObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void deleteFoodAllergy(final FoodAllergyObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteFoodAllergy(data.getFoodAllergyId());
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
