package com.truedigital.vhealth.ui.setting;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiUserRequest;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.addRequestBody;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class SettingFragmentPresenter extends BaseMvpPresenter<SettingFragmentInterface.View>
        implements SettingFragmentInterface.Presenter {

    public static SettingFragmentInterface.Presenter create() {
        return new SettingFragmentPresenter();
    }

    @Override
    public void Logout() {
        onLogout();
        getView().openLogin();
    }

    @Override
    public void onConfirmButtonClick() {
        if (getView().isValid()) {
            if (getView().isEditMode()) {
                if (getView().isChangePassword()) {
                    getView().openOldPassword();
                } else {
                    updateAccount();
                }
            } else {
                updateAccount();
            }
        }
    }

    @Override
    public void updateAccount() {
        getView().showLoading();
        String access_token = AppManager.getDataManager().getAccess_token();
        String userName = getView().getUserName();
        String oldPassword = getView().getOldPassword();
        String password = getView().getPassword();
        String confirmPassword = getView().getPasswordConfirm();
        String referenceCode = getView().getReferenceCode();
        String otp = getView().getOTP();

        ApiUserRequest request = new ApiUserRequest();
        request.setUserName(getView().getUserName());
        request.setOldPassword(getView().getOldPassword());
        request.setPassword(getView().getPassword());
        request.setConfirmPassword(getView().getPasswordConfirm());
        request.setReferenceCode(getView().getReferenceCode());
        request.setOTP(getView().getOTP());

        MultipartBody.Part profileImage = getView().getImageBody();

        Call<NormalResponseObject> call = getRetrofitToken(access_token).patchUpdateUser(
                addRequestBody(userName),
                addRequestBody(oldPassword),
                addRequestBody(password),
                addRequestBody(confirmPassword),
                addRequestBody(referenceCode),
                addRequestBody(otp),
                profileImage
        );

        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    AppManager.getDataManager().setLogin();
                    getView().showSuccess();
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }
}
