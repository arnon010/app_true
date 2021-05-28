package com.truedigital.vhealth.ui.setting;

import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import static com.truedigital.vhealth.api.RetrofitBuilder.addMultipartBody;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class SettingActivityPresenter extends BaseMvpPresenter<SettingActivityInterface.View>
        implements SettingActivityInterface.Presenter{

    private static final String TAG = SettingActivityPresenter.class.getName();


    public static SettingActivityInterface.Presenter create() {
        return new SettingActivityPresenter();
    }

    @Override
    public void onDoneButtonClick() {
        /*
        if (getView().isValid()) {
            callUpdateUser();
        }
        */

    }

    /*

    private void callUpdateUser() {
        callApiUpdateUser();
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

    private void showErrorMessage(ResponseBody errorBody) {
        try {
            JSONObject objError = new JSONObject(errorBody.string());
            getView().showMessage(objError.getString("ErrorMessages"));
            //getView().showMessage(objError.getString("error_description"));

        } catch (Exception e) {
            getView().showMessage(""+e.getMessage());
        }
    }
    */
}


