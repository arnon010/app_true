package com.truedigital.vhealth.ui.password.create;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitService;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class PasswordCreateActivityPresenter extends BaseMvpPresenter<PasswordCreateActivityInterface.View>
        implements PasswordCreateActivityInterface.Presenter {

    private static final String TAG = "PasswordCreateActivityPresenter";
    private String mUserName;
    private String mUserEmail;
    private String mToken;
    private String mPassword;
    private String mPasswordConfirm;

    public static PasswordCreateActivityInterface.Presenter create() {
        return new PasswordCreateActivityPresenter();
    }

    @Override
    public void onGoliveButtonClick() {
        mUserName = getView().getUserName();
        mUserEmail = getView().getEmail();
        mToken = getView().getDeviceToken();
        mPassword = getView().getPassword();
        mPasswordConfirm = getView().getConfirmPassword();

        if (getView().isValid()) {
            if (getView().getUserId() > 0) {
                //..Changed Password
                callChangePassword();
            }
            else {
                //..Create Password for Signup
                onCallRegisterUser();
            }
        }
    }

    @Override
    public void onCallRegisterUser() {
        getView().showLoading();
        Call<NormalResponseObject> call = getRetrofitService().postRegisterUserWithPassword(
                mUserName,
                mUserEmail,
                mToken,
                mPassword, mPasswordConfirm
        );
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().openSignupSuccessActivity();
                } else {
                    getView().showMessage(""+response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage().toString());
            }
        });
    }

    private void callChangePassword() {
        getView().showLoading();
        Call<NormalResponseObject> call = getRetrofitService().postChangePasswordForget(
                getView().getUserId(),
                mToken,
                mPassword, mPasswordConfirm
        );
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().openLoginEmailActivity();
                } else {
                    getView().showMessage(response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage().toString());
            }
        });
    }


}


