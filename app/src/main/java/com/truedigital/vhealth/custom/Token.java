package com.truedigital.vhealth.custom;

import com.truedigital.vhealth.model.ApiAccessToken;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitServiceAuthen;

/**
 * Created by songkrit on 6/25/2018 AD.
 */

public class Token {

    private OnRefreshToken onRefreshToken;
    public interface OnRefreshToken {
        void onSuccess(ApiAccessToken data);
        void onFail();
    }

    public Token(OnRefreshToken onRefreshToken) {
        this.onRefreshToken = onRefreshToken;
    }

    public static void refreshToken(final String refresh_token, final OnRefreshToken onRefreshToken) {

        Call<ApiAccessToken> call = getRetrofitServiceAuthen().postRefreshToken(
                AppConstants.AUTHEN_CLIENT_ID,
                AppConstants.AUTHEN_CLIENT_SECRET,
                AppConstants.AUTHEN_GRANT_TYPE_REFRESH,
                refresh_token
        );
        call.enqueue(new Callback<ApiAccessToken>() {
            @Override
            public void onResponse(Call<ApiAccessToken> call, Response<ApiAccessToken> response) {
                if (response.isSuccessful()) {
                    ApiAccessToken data = response.body();
                    if (data.getAccess_token() != null) {
                        onRefreshToken.onSuccess(data);
                    }
                    else {
                        onRefreshToken.onFail();
                    }
                }
                else {
                    onRefreshToken.onFail();
                }
            }

            @Override
            public void onFailure(Call<ApiAccessToken> call, Throwable t) {
                onRefreshToken.onFail();
            }
        });

    }
}
