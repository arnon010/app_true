package com.truedigital.vhealth.ui.home.symptomtype;

import com.truedigital.vhealth.database.UserManager;
import com.truedigital.vhealth.model.ApiFavoriteObject;
import com.truedigital.vhealth.model.ApiListSymptom;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListSymptomTypeFragmentPresenter extends BaseMvpPresenter<ListSymptomTypeFragmentInterface.View>
        implements ListSymptomTypeFragmentInterface.Presenter{

    public static ListSymptomTypeFragmentInterface.Presenter create(){
        return new ListSymptomTypeFragmentPresenter();
    }

    @Override
    public void loadSymptom() {
        UserManager userManager = new UserManager();
        String access_token = userManager.getAccess_Token(userManager.getCurrentUserId());

        Call<ApiListSymptom> call = getRetrofitToken(access_token).getListSymptomType();
        call.enqueue(new Callback<ApiListSymptom>() {
            @Override
            public void onResponse(Call<ApiListSymptom> call, Response<ApiListSymptom> response) {
                if (response.isSuccessful()) {
                    ArrayList<ApiListSymptom.FilterList> listData = new ArrayList<ApiListSymptom.FilterList>();
                    listData = response.body().getFilterArrayList();
                    getView().updateSymptom(listData);
                }
                else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiListSymptom> call, Throwable t) {

            }
        });
    }

    @Override
    public void callApiAddFavorite(final int id) {
        getView().showLoading();
        UserManager userManager = new UserManager();
        String access_token = userManager.getAccess_Token(userManager.getCurrentUserId());
        Call<ApiFavoriteObject> call = getRetrofitToken(access_token).postAddFavoriteSymptom(id);
        call.enqueue(new Callback<ApiFavoriteObject>() {
            @Override
            public void onResponse(Call<ApiFavoriteObject> call, Response<ApiFavoriteObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        getView().setFavorite(id, true);
                        getView().hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiFavoriteObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void callApiDelFavorite(final int id) {
        getView().showLoading();
        UserManager userManager = new UserManager();
        String access_token = userManager.getAccess_Token(userManager.getCurrentUserId());
        Call<ApiFavoriteObject> call = getRetrofitToken(access_token).postDelFavoriteSymptom(id);
        call.enqueue(new Callback<ApiFavoriteObject>() {
            @Override
            public void onResponse(Call<ApiFavoriteObject> call, Response<ApiFavoriteObject> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        getView().setFavorite(id, false);
                        getView().hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiFavoriteObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
