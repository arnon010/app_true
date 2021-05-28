package com.truedigital.vhealth.ui.ehr.laboratory;

import android.app.Activity;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiLaboratoryOtherObject;
import com.truedigital.vhealth.model.LaboratoryOtherImageObject;
import com.truedigital.vhealth.model.LaboratoryOtherObject;
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

public class FormLaboratoryOtherFragmentPresenter extends BaseMvpPresenter<FormLaboratoryOtherFragmentInterface.View>
        implements FormLaboratoryOtherFragmentInterface.Presenter{

    public static FormLaboratoryOtherFragmentInterface.Presenter create(){
        return new FormLaboratoryOtherFragmentPresenter();
    }

    @Override
    public void newLaboratoryOther(Activity activity, LaboratoryOtherObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        LaboratoryOtherObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<LaboratoryOtherImageObject>());

        ArrayList<LaboratoryOtherImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (LaboratoryOtherImageObject attac : data.getAttachmentList()) {

            LaboratoryOtherImageObject imgClone = attac.clone();
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

        Call<ApiLaboratoryOtherObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).postManualLab(data.getPatientId(), addRequestBody(objJson), fileList);
        call.enqueue(new Callback<ApiLaboratoryOtherObject>() {
            @Override
            public void onResponse(Call<ApiLaboratoryOtherObject> call, Response<ApiLaboratoryOtherObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    LaboratoryOtherObject data = response.body().getData();

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
            public void onFailure(Call<ApiLaboratoryOtherObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updateLaboratoryOther(Activity activity, final LaboratoryOtherObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        LaboratoryOtherObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<LaboratoryOtherImageObject>());

        ArrayList<LaboratoryOtherImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (LaboratoryOtherImageObject attac : data.getAttachmentList()) {

            LaboratoryOtherImageObject imgClone = attac.clone();
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

        Call<NormalResponseObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).putManualLab(data.getPatientId(), addRequestBody(objJson), fileList);
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
