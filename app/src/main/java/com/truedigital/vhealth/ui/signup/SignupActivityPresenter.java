package com.truedigital.vhealth.ui.signup;

import android.util.Log;

import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitService;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SignupActivityPresenter extends BaseMvpPresenter<SignupActivityInterface.View>
        implements SignupActivityInterface.Presenter{

    private static final String TAG = SignupActivityPresenter.class.getName();


    public static SignupActivityInterface.Presenter create() {
        return new SignupActivityPresenter();
    }

    @Override
    public void onDoneButtonClick() {
        if (getView().isValid()) {
            callRegisterUser();
        }

    }

    private void callRegisterUser() {
        getView().showLoading();
        String userName = getView().getUserName();
        String userEmail = getView().getEmail();
        String tokenOneSignal = getView().getTokenIdOneSignal();

        Call<NormalResponseObject> call = getRetrofitService().postRegisterUser(userName,userEmail,tokenOneSignal);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    Log.e(TAG,""+response.body().getErrorMessage());
                    if (response.body().isSuccess()) {
                        getView().openSignupSuccessActivity();
                    }
                    else {
                        Log.e(TAG,""+response.body().getErrorMessage());
                        getView().showMessage(response.body().getErrorMessage());
                    }
                }
                else {
                    Log.e(TAG,""+response.message());
                    getView().showMessage(response.raw().message());
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                //Log.e(TAG,"" + t.getMessage());
                getView().hideLoading();
                getView().showMessage(""+t.getMessage());
            }
        });
    }

}


