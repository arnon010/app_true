package com.truedigital.vhealth.ui.home.searchdoctor;

import com.truedigital.vhealth.database.UserManager;
import com.truedigital.vhealth.model.ApiFavoriteObject;
import com.truedigital.vhealth.model.ApiListDoctor;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class SearchDoctorFragmentPresenter extends BaseMvpPresenter<SearchDoctorFragmentInterface.View>
        implements SearchDoctorFragmentInterface.Presenter{

    public static SearchDoctorFragmentInterface.Presenter create(){
        return new SearchDoctorFragmentPresenter();
    }

    /*
    @Override
    public void loadDoctor() {
        getView().showLoading();
        UserManager userManager = new UserManager();
        String access_token = userManager.getAccess_Token(userManager.getCurrentUserId());

        Call<ApiSearchDoctorObject> call = getRetrofitToken(access_token).postSearchDoctor(0,"",0,0,"",1);
        //Call<ApiSearchDoctorObject> call = getRetrofitToken(access_token).getListDoctor(0,0);
        call.enqueue(new Callback<ApiSearchDoctorObject>() {
            @Override
            public void onResponse(Call<ApiSearchDoctorObject> call, Response<ApiSearchDoctorObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    ArrayList<ApiDoctorObject.AccountObject> listDoctor = new ArrayList<ApiDoctorObject.AccountObject>();
                    listDoctor = response.body().getDoctorArrayList();
                    getView().updateDoctor(listDoctor);
                }
                else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiSearchDoctorObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
    */

    @Override
    public void loadDoctor() {
        getView().showLoading();
        UserManager userManager = new UserManager();
        String access_token = userManager.getAccess_Token(userManager.getCurrentUserId());

        Call<ApiListDoctor> call = getRetrofitToken(access_token).getListDoctor(0,0);
        call.enqueue(new Callback<ApiListDoctor>() {
            @Override
            public void onResponse(Call<ApiListDoctor> call, Response<ApiListDoctor> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    //ArrayList<ApiListDoctor.FilterList> listDoctor = new ArrayList<ApiListDoctor.FilterList>();
                    //listDoctor = response.body().getFilterArrayList();
                    //getView().updateDoctor(listDoctor);
                }
                else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiListDoctor> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void callApiAddFavorite(final int id) {
        getView().showLoading();
        UserManager userManager = new UserManager();
        String access_token = userManager.getAccess_Token(userManager.getCurrentUserId());
        Call<ApiFavoriteObject> call = getRetrofitToken(access_token).postAddFavoriteDoctor(id);
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
        Call<ApiFavoriteObject> call = getRetrofitToken(access_token).postDelFavoriteDoctor(id);
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
