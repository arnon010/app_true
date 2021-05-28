package com.truedigital.vhealth.ui.ehr.healthinformation;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiCurrentEhrMenuObject;
import com.truedigital.vhealth.model.ApiListEhrMenuObject;
import com.truedigital.vhealth.model.CurrentEhrMenuObject;
import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class HealthInformationMenuFragmentPresenter extends BaseMvpPresenter<HealthInformationMenuFragmentInterface.View>
        implements HealthInformationMenuFragmentInterface.Presenter {

    public static HealthInformationMenuFragmentInterface.Presenter create() {
        return new HealthInformationMenuFragmentPresenter();
    }

    @Override
    public void getHealthInformationMenu(int patientId, boolean isChild) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiCurrentEhrMenuObject> call = getRetrofitToken(access_token).getCurrentSubMenu(patientId, isChild, AppConstants.EHR_MENU_INFORMATION);
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

                    getView().setHealthInformationMenu(dataMenuList);

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
            public void onFailure(Call<ApiCurrentEhrMenuObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void getHealthInformationMenuSelectList(int patientId, boolean isChild) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiListEhrMenuObject> call = getRetrofitToken(access_token).getMenuList(patientId, isChild, AppConstants.EHR_MENU_INFORMATION);
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

                    getView().setHealthInformationMenuSelectList(dataMenuList);

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
            public void onFailure(Call<ApiListEhrMenuObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
