package com.truedigital.vhealth.ui.login.phone;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ApiCheckTelephone;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class LoginPhoneActivityPresenter extends BaseMvpPresenter<LoginPhoneActivityInterface.View>
        implements LoginPhoneActivityInterface.Presenter{

    private static final String TAG = LoginPhoneActivityPresenter.class.getName();

    public static LoginPhoneActivityInterface.Presenter create() {
        return new LoginPhoneActivityPresenter();
    }


    @Override
    public void onLoginButtonClick() {
        if (getView().isValid()) {
            callApiCheckPhone(getView().getPhoneNumber());
        }
    }


    private void callApiCheckPhone(final String telephoneNumber) {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }
        getView().showLoading();

        Call<ApiCheckTelephone> call = getRetrofitToken("").postCheckTelephone(telephoneNumber);
        call.enqueue(new Callback<ApiCheckTelephone>() {
            @Override
            public void onResponse(Call<ApiCheckTelephone> call, Response<ApiCheckTelephone> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ApiCheckTelephone apiCheckTelephone = response.body();
                    if (apiCheckTelephone.isHasAccount()) {
                        getView().openLoginWithPassword();
                    }
                    else {
                        getView().openLoginWithOtp("");
                    }
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiCheckTelephone> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage());
            }
        });
    }

}


