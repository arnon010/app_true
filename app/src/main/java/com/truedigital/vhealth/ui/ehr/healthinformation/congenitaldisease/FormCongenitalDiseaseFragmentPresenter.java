package com.truedigital.vhealth.ui.ehr.healthinformation.congenitaldisease;

import android.app.Activity;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiCongenitalDiseaseObject;
import com.truedigital.vhealth.model.CongenitalDiseaseImageObject;
import com.truedigital.vhealth.model.CongenitalDiseaseObject;
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

public class FormCongenitalDiseaseFragmentPresenter extends BaseMvpPresenter<FormCongenitalDiseaseFragmentInterface.View>
        implements FormCongenitalDiseaseFragmentInterface.Presenter {

    public static FormCongenitalDiseaseFragmentInterface.Presenter create() {
        return new FormCongenitalDiseaseFragmentPresenter();
    }

    @Override
    public void newCongenitalDisease(Activity activity, CongenitalDiseaseObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        CongenitalDiseaseObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<CongenitalDiseaseImageObject>());

        ArrayList<CongenitalDiseaseImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (CongenitalDiseaseImageObject attac : data.getAttachmentList()) {

            CongenitalDiseaseImageObject imgClone = attac.clone();
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

        Call<ApiCongenitalDiseaseObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).postCongenitalDisease(data.getPatientId(), addRequestBody(objJson), fileList);
        call.enqueue(new Callback<ApiCongenitalDiseaseObject>() {
            @Override
            public void onResponse(Call<ApiCongenitalDiseaseObject> call, Response<ApiCongenitalDiseaseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    CongenitalDiseaseObject data = response.body().getData();

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
            public void onFailure(Call<ApiCongenitalDiseaseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updateCongenitalDisease(Activity activity, final CongenitalDiseaseObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        CongenitalDiseaseObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<CongenitalDiseaseImageObject>());

        ArrayList<CongenitalDiseaseImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (CongenitalDiseaseImageObject attac : data.getAttachmentList()) {

            CongenitalDiseaseImageObject imgClone = attac.clone();
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

        Call<NormalResponseObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).putCongenitalDisease(data.getPatientId(), addRequestBody(objJson), fileList);
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
