package com.truedigital.vhealth.ui.ehr.pregnanthistory;

import android.app.Activity;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiPregnantHistoryObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.PregnantHistoryImageObject;
import com.truedigital.vhealth.model.PregnantHistoryObject;
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

public class FormPregnantHistoryFragmentPresenter extends BaseMvpPresenter<FormPregnantHistoryFragmentInterface.View>
        implements FormPregnantHistoryFragmentInterface.Presenter {

    public static FormPregnantHistoryFragmentInterface.Presenter create() {
        return new FormPregnantHistoryFragmentPresenter();
    }

    @Override
    public void newPregnantHistory(Activity activity, PregnantHistoryObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        PregnantHistoryObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<PregnantHistoryImageObject>());

        ArrayList<PregnantHistoryImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (PregnantHistoryImageObject attac : data.getAttachmentList()) {

            PregnantHistoryImageObject imgClone = attac.clone();
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

        Call<ApiPregnantHistoryObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).postPregnantHistory(data.getPatientId(), addRequestBody(objJson), fileList);
        call.enqueue(new Callback<ApiPregnantHistoryObject>() {
            @Override
            public void onResponse(Call<ApiPregnantHistoryObject> call, Response<ApiPregnantHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    PregnantHistoryObject data = response.body().getData();

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
            public void onFailure(Call<ApiPregnantHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updatePregnantHistory(Activity activity, final PregnantHistoryObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        PregnantHistoryObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<PregnantHistoryImageObject>());

        ArrayList<PregnantHistoryImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (PregnantHistoryImageObject attac : data.getAttachmentList()) {

            PregnantHistoryImageObject imgClone = attac.clone();
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

        Call<NormalResponseObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).putPregnantHistory(data.getPatientId(), addRequestBody(objJson), fileList);
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
