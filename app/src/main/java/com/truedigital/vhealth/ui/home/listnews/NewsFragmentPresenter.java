package com.truedigital.vhealth.ui.home.listnews;

import com.truedigital.vhealth.database.UserManager;
import com.truedigital.vhealth.model.ApiNewsObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class NewsFragmentPresenter extends BaseMvpPresenter<NewsFragmentInterface.View>
        implements NewsFragmentInterface.Presenter{

    public static NewsFragmentInterface.Presenter create(){
        return new NewsFragmentPresenter();
    }

    @Override
    public void loadNews() {
        UserManager userManager = new UserManager();
        String access_token = userManager.getAccess_Token(userManager.getCurrentUserId());

        Call<ApiNewsObject> call = getRetrofitToken(access_token).getNewsList();
        call.enqueue(new Callback<ApiNewsObject>() {
            @Override
            public void onResponse(Call<ApiNewsObject> call, Response<ApiNewsObject> response) {
                if (response.isSuccessful()) {
                    ArrayList<ApiNewsObject.Lists> listData = new ArrayList<ApiNewsObject.Lists>();
                    listData = response.body().getNewsList();
                    getView().updateNews(listData);
                }
            }

            @Override
            public void onFailure(Call<ApiNewsObject> call, Throwable t) {

            }
        });

    }
}
