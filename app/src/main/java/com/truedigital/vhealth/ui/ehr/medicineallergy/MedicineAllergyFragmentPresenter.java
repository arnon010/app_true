package com.truedigital.vhealth.ui.ehr.medicineallergy;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListMedicineAllergyObject;
import com.truedigital.vhealth.model.ApiMedicineAllergyObject;
import com.truedigital.vhealth.model.MedicineAllergyCriteriaObject;
import com.truedigital.vhealth.model.MedicineAllergyObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class MedicineAllergyFragmentPresenter extends BaseMvpPresenter<MedicineAllergyFragmentInterface.View>
        implements MedicineAllergyFragmentInterface.Presenter {

    public static MedicineAllergyFragmentInterface.Presenter create() {
        return new MedicineAllergyFragmentPresenter();
    }

    @Override
    public void getMedicineAllergyList(MedicineAllergyCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListMedicineAllergyObject> call = getRetrofitToken(access_token).getMedicineAllergyList(criteria.getPatientId(), criteria.getPatientMenuId(), criteria.getPageIndex(), criteria.getPageSize());
        call.enqueue(new Callback<ApiListMedicineAllergyObject>() {
            @Override
            public void onResponse(Call<ApiListMedicineAllergyObject> call, Response<ApiListMedicineAllergyObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<MedicineAllergyObject> dataList = response.body().getDataList();
                    getView().setMedicineAllergyList(dataList);

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
            public void onFailure(Call<ApiListMedicineAllergyObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getMedicineAllergyDetail(MedicineAllergyObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiMedicineAllergyObject> call = getRetrofitToken(access_token).getMedicineAllergyDetail(criteria.getPatientId(), criteria.getMedicineAllergyId());
        call.enqueue(new Callback<ApiMedicineAllergyObject>() {
            @Override
            public void onResponse(Call<ApiMedicineAllergyObject> call, Response<ApiMedicineAllergyObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    MedicineAllergyObject data = response.body().getData();
                    getView().setMedicineAllergyDetail(data);

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
    public void deleteMedicineAllergy(final MedicineAllergyObject data) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).deleteMedicineAllergy(data.getMedicineAllergyId());
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
