package com.truedigital.vhealth.ui.ehr.medicationhistory;

import android.app.Activity;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiMedicationHistoryObject;
import com.truedigital.vhealth.model.MedicationHistoryImageObject;
import com.truedigital.vhealth.model.MedicationHistoryObject;
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

public class FormMedicationHistoryFragmentPresenter extends BaseMvpPresenter<FormMedicationHistoryFragmentInterface.View>
        implements FormMedicationHistoryFragmentInterface.Presenter {

    public static FormMedicationHistoryFragmentInterface.Presenter create() {
        return new FormMedicationHistoryFragmentPresenter();
    }

    @Override
    public void newMedicationHistory(Activity activity, MedicationHistoryObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        MedicationHistoryObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<MedicationHistoryImageObject>());

        ArrayList<MedicationHistoryImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (MedicationHistoryImageObject attac : data.getAttachmentList()) {

            MedicationHistoryImageObject imgClone = attac.clone();
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

        Call<ApiMedicationHistoryObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).postMedicinationHistory(data.getPatientId(), addRequestBody(objJson), fileList);
        call.enqueue(new Callback<ApiMedicationHistoryObject>() {
            @Override
            public void onResponse(Call<ApiMedicationHistoryObject> call, Response<ApiMedicationHistoryObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    MedicationHistoryObject data = response.body().getData();

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
            public void onFailure(Call<ApiMedicationHistoryObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updateMedicationHistory(Activity activity, final MedicationHistoryObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        MedicationHistoryObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<MedicationHistoryImageObject>());

        ArrayList<MedicationHistoryImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (MedicationHistoryImageObject attac : data.getAttachmentList()) {

            MedicationHistoryImageObject imgClone = attac.clone();
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

        Call<NormalResponseObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).putMedicinationHistory(data.getPatientId(), addRequestBody(objJson), fileList);
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
