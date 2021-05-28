package com.truedigital.vhealth.ui.ehr.foodallergy;

import android.app.Activity;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiFoodAllergyObject;
import com.truedigital.vhealth.model.FoodAllergyImageObject;
import com.truedigital.vhealth.model.FoodAllergyObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.addMultipartBody;
import static com.truedigital.vhealth.api.RetrofitBuilder.addRequestBody;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class FormFoodAllergyFragmentPresenter extends BaseMvpPresenter<FormFoodAllergyFragmentInterface.View>
        implements FormFoodAllergyFragmentInterface.Presenter {

    public static FormFoodAllergyFragmentInterface.Presenter create() {
        return new FormFoodAllergyFragmentPresenter();
    }

    @Override
    public void newFoodAllergy(Activity activity, FoodAllergyObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        FoodAllergyObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<FoodAllergyImageObject>());

        ArrayList<FoodAllergyImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (FoodAllergyImageObject attac : data.getAttachmentList()) {

            FoodAllergyImageObject imgClone = attac.clone();
            tempData.getAttachmentList().add(imgClone);

            if (attac.getIsNew()) {

                fileList.add(addMultipartBody(activity, String.valueOf(++i), attac.getImageUri()));
            }

            if (attac.getIsDelete() == null || !attac.getIsDelete()) {
                tempAttachmentList.add(imgClone);
            }
        }

        tempData.getAttachmentList().removeAll(tempAttachmentList); //delete because this file not delete

        objJson = new Gson().toJson(tempData);

        Call<ApiFoodAllergyObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).postFoodAllergy(data.getPatientId(), addRequestBody(objJson), fileList);
        call.enqueue(new Callback<ApiFoodAllergyObject>() {
            @Override
            public void onResponse(Call<ApiFoodAllergyObject> call, Response<ApiFoodAllergyObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    FoodAllergyObject data = response.body().getData();

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
            public void onFailure(Call<ApiFoodAllergyObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updateFoodAllergy(Activity activity, final FoodAllergyObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        FoodAllergyObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<FoodAllergyImageObject>());

        ArrayList<FoodAllergyImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (FoodAllergyImageObject attac : data.getAttachmentList()) {

            FoodAllergyImageObject imgClone = attac.clone();
            tempData.getAttachmentList().add(imgClone);

            if (attac.getIsNew()) {

                fileList.add(addMultipartBody(activity, String.valueOf(++i), attac.getImageUri()));
            }

            if (attac.getIsDelete() == null || !attac.getIsDelete()) {
                tempAttachmentList.add(imgClone);
            }
        }

        tempData.getAttachmentList().removeAll(tempAttachmentList); //delete because this file not delete

        objJson = new Gson().toJson(tempData);

        Call<NormalResponseObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).putFoodAllergy(data.getPatientId(), addRequestBody(objJson), fileList);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().onUpdateSuccess(data);

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
