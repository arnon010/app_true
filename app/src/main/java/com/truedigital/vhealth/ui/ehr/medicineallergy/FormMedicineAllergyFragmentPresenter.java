package com.truedigital.vhealth.ui.ehr.medicineallergy;

import android.app.Activity;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiMedicineAllergyObject;
import com.truedigital.vhealth.model.MedicineAllergyImageObject;
import com.truedigital.vhealth.model.MedicineAllergyObject;
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

public class FormMedicineAllergyFragmentPresenter extends BaseMvpPresenter<FormMedicineAllergyFragmentInterface.View>
        implements FormMedicineAllergyFragmentInterface.Presenter {

    public static FormMedicineAllergyFragmentInterface.Presenter create() {
        return new FormMedicineAllergyFragmentPresenter();
    }

    @Override
    public void newMedicineAllergy(Activity activity, MedicineAllergyObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        MedicineAllergyObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<MedicineAllergyImageObject>());

        ArrayList<MedicineAllergyImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (MedicineAllergyImageObject attac : data.getAttachmentList()) {

            MedicineAllergyImageObject imgClone = attac.clone();
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

        Call<ApiMedicineAllergyObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).postMedicineAllergy(data.getPatientId(), addRequestBody(objJson), fileList);
        call.enqueue(new Callback<ApiMedicineAllergyObject>() {
            @Override
            public void onResponse(Call<ApiMedicineAllergyObject> call, Response<ApiMedicineAllergyObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    MedicineAllergyObject data = response.body().getData();

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
            public void onFailure(Call<ApiMedicineAllergyObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void updateMedicineAllergy(Activity activity, final MedicineAllergyObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        String objJson;
        ArrayList<MultipartBody.Part> fileList = new ArrayList<>();

        MedicineAllergyObject tempData = data.clone();
        if (tempData == null) {
            tempData = data;
        }

        tempData.setAttachmentList(new ArrayList<MedicineAllergyImageObject>());

        ArrayList<MedicineAllergyImageObject> tempAttachmentList = new ArrayList<>();
        int i = 0;
        for (MedicineAllergyImageObject attac : data.getAttachmentList()) {

            MedicineAllergyImageObject imgClone = attac.clone();
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

        Call<NormalResponseObject> call = getRetrofitToken(access_token, AppConstants.TIMEOUT_SECONDS).putMedicineAllergy(data.getPatientId(), addRequestBody(objJson), fileList);
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
