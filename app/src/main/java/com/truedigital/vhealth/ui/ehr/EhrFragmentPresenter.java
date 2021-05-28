package com.truedigital.vhealth.ui.ehr;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiCurrentEhrMenuObject;
import com.truedigital.vhealth.model.ApiListEhrMenuObject;
import com.truedigital.vhealth.model.ApiListPatientRelationshipObject;
import com.truedigital.vhealth.model.ApiPatientObject;
import com.truedigital.vhealth.model.ApiPatientRelationshipObject;
import com.truedigital.vhealth.model.CurrentEhrMenuObject;
import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.PatientObject;
import com.truedigital.vhealth.model.PatientRelationshipObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.base.exception.MvpViewNotAttachedException;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class EhrFragmentPresenter extends BaseMvpPresenter<EhrFragmentInterface.View>
        implements EhrFragmentInterface.Presenter {

    public static EhrFragmentInterface.Presenter create() {
        return new EhrFragmentPresenter();
    }

    @Override
    public void getPatient(int patientId) {

        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiPatientObject> call = getRetrofitToken(access_token).getPatient(patientId);
        call.enqueue(new Callback<ApiPatientObject>() {
            @Override
            public void onResponse(Call<ApiPatientObject> call, Response<ApiPatientObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    PatientObject data = response.body().getData();

                    try {
                        getView().setPatient(data);
                    } catch (MvpViewNotAttachedException e) {
                        return;
                    }

                } else {
                    manageOnStatusNotSuccess(response);
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiPatientObject> call, Throwable t) {
                try {
                    getView().hideLoading();
                } catch (MvpViewNotAttachedException e) { }
            }
        });
    }

    @Override
    public void getHealthRecordMenu(int patientId, boolean isChild) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiCurrentEhrMenuObject> call = getRetrofitToken(access_token).getCurrentMenu(patientId, isChild);
        call.enqueue(new Callback<ApiCurrentEhrMenuObject>() {
            @Override
            public void onResponse(Call<ApiCurrentEhrMenuObject> call, Response<ApiCurrentEhrMenuObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    CurrentEhrMenuObject dataCurrent = response.body().getData();
                    ArrayList<EhrMenuObject> dataMenuList = dataCurrent.getMenuList();

                    if (dataCurrent.getCanAddNewMenu()) {
                        EhrMenuObject add = new EhrMenuObject();
                        add.setMenuCode(AppConstants.EHR_MENU_EMPTY);
                        add.setMenuName("");
                        add.setDisplayName("");
                        dataMenuList.add(add);
                    }

                    for (EhrMenuObject data : dataMenuList) {
                        data.setMenuName(StringUtils.setNewLine(data.getMenuName()));
                        data.setDisplayName(StringUtils.setNewLine(data.getDisplayName()));
                    }

                    getView().setHealthRecordMenu(dataMenuList);

                } else {
                    manageOnStatusNotSuccess(response);
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiCurrentEhrMenuObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    private void manageOnStatusNotSuccess(Response response) {
        int statusCode = response.raw().code();
        switch (statusCode) {
            case AppConstants.AUTHEN_UNAUTHORIZED:
                getView().token_expired();
                break;
            case AppConstants.AUTHEN_EHR_UNAUTHORIZED:
                getView().navigateToLoginEhr();
                break;
            default:
                getView().showMessage(response);
        }
    }

    @Override
    public void getHealthRecordMenuSelectList(int patientId, boolean isChild) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListEhrMenuObject> call = getRetrofitToken(access_token).getMenuList(patientId, isChild, "");
        call.enqueue(new Callback<ApiListEhrMenuObject>() {
            @Override
            public void onResponse(Call<ApiListEhrMenuObject> call, Response<ApiListEhrMenuObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<EhrMenuObject> dataMenuList = response.body().getMenuList();

                    for (EhrMenuObject data : dataMenuList) {
                        data.setMenuName(StringUtils.setNewLine(data.getMenuName()));
                        data.setDisplayName(StringUtils.setNewLine(data.getDisplayName()));

                        if (data.getMenuCode().equals(AppConstants.EHR_MENU_FAMILY)) {
                            data.setIsShowImageNext(true);
                        } else {
                            data.setIsShowImageNext(false);
                        }
                    }

                    getView().setHealthRecordMenuSelectList(dataMenuList);

                } else {
                    manageOnStatusNotSuccess(response);
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiListEhrMenuObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getFamilySelectList() {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();
        int currentPatientId = AppManager.getDataManager().getPatientId();

        Call<ApiListPatientRelationshipObject> call = getRetrofitToken(access_token).getPatientRelationshipList(currentPatientId);
        call.enqueue(new Callback<ApiListPatientRelationshipObject>() {
            @Override
            public void onResponse(Call<ApiListPatientRelationshipObject> call, Response<ApiListPatientRelationshipObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    ArrayList<PatientRelationshipObject> dataList = response.body().getRelationshipList();

                    PatientRelationshipObject m4 = new PatientRelationshipObject();
                    m4.setRelationshipId(0);
                    dataList.add(m4);

                    getView().setFamilySelectList(dataList);

                } else {
                    manageOnStatusNotSuccess(response);
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiListPatientRelationshipObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void createFamily(String relationshipName) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();
        int currentPatientId = AppManager.getDataManager().getPatientId();

        Call<ApiPatientRelationshipObject> call = getRetrofitToken(access_token).postPatientRelationship(currentPatientId, AppConstants.EHR_MENU_FAMILY, relationshipName);
        call.enqueue(new Callback<ApiPatientRelationshipObject>() {
            @Override
            public void onResponse(Call<ApiPatientRelationshipObject> call, Response<ApiPatientRelationshipObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    PatientRelationshipObject data = response.body().getRelationship();
                    getView().openRelationshipMenu(data);

                } else {
                    manageOnStatusNotSuccess(response);
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiPatientRelationshipObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void checkPatientEHRMenu(int patientId, final String menuCode, final String menuName) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).postCheckPatientEHRMenu(patientId, menuCode, menuName);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    getView().checkPatientEHRMenuSuccess(menuCode, menuName);

                } else {
                    manageOnStatusNotSuccess(response);
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
