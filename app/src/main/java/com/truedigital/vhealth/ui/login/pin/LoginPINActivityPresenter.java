package com.truedigital.vhealth.ui.login.pin;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiPatientObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class LoginPINActivityPresenter extends BaseMvpPresenter<LoginPINActivityInterface.View>
        implements LoginPINActivityInterface.Presenter {

    public static LoginPINActivityInterface.Presenter create() {
        return new LoginPINActivityPresenter();
    }

    @Override
    public void onLogin(String pin) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiPatientObject> call = getRetrofitToken(access_token).postLoginEHR(pin);
        call.enqueue(new Callback<ApiPatientObject>() {
            @Override
            public void onResponse(Call<ApiPatientObject> call, Response<ApiPatientObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    AppManager.getDataManager().setPatientId(response.body().getData().getPatientId());
                    AppManager.getDataManager().setEhrToken(response.body().getToken());

                    getView().onLoginSuccess();

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
            public void onFailure(Call<ApiPatientObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }
}
