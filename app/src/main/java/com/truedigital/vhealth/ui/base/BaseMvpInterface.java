package com.truedigital.vhealth.ui.base;

import androidx.annotation.StringRes;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by songkrit on 10/30/2017 AD.
 */

public interface BaseMvpInterface {
    interface View{
        Presenter getPresenter();
        void showLoading();
        void hideLoading();
        void onError(@StringRes int resId);
        void onError(String message);
        void showMessage(@StringRes int resId);
        void showMessage(String message);
        void showMessage(Response response);
        void showErrorMessage(ResponseBody errorBody);
        boolean isNetworkConnected();
        void hideKeyboard();
        String getDeviceId();
        void token_expired();
        void token_refresh_error();
        String getTokenIdOneSignal();
    }


    interface Presenter<V extends BaseMvpInterface.View>{
        void attachView( V mvpView );

        void detachView();

        V getView();

        void onViewCreate();

        void onViewDestroy();

        void onViewStart();

        void onViewStop();

        void onViewResume();

        void onViewPause();

        void onLogout();

        void onRefresh_token(String refresh_token);

        void onRefresh_token(String refresh_token, BaseMvpPresenter.OnRefreshListener listener);

        void onCheckToken(String token, final BaseMvpPresenter.OnTokenListener listener);
    }
}
