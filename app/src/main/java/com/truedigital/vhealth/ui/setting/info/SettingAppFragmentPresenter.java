package com.truedigital.vhealth.ui.setting.info;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiCheckPinObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class SettingAppFragmentPresenter extends BaseMvpPresenter<SettingAppFragmentInterface.View>
        implements SettingAppFragmentInterface.Presenter{

    public static SettingAppFragmentInterface.Presenter create(){
        return new SettingAppFragmentPresenter();
    }

    @Override
    public void Logout() {
        onLogout();
        getView().openLogin();
    }

    @Override
    public void getPinStatus() {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        getView().showLoading();

        final String access_token = AppManager.getDataManager().getAccess_token();
        Call<ApiCheckPinObject> call = getRetrofitToken(access_token).getPINStatus();
        call.enqueue(new Callback<ApiCheckPinObject>() {
            @Override
            public void onResponse(Call<ApiCheckPinObject> call, Response<ApiCheckPinObject> response) {
                if (response.isSuccessful()) {
                    getView().onPinStatus(response.body().getHasPIN());
                }
                else {
                    getView().showMessage(response);
                }
                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiCheckPinObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
