package com.truedigital.vhealth.ui.articles;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListArticles;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ArticlesFragmentPresenter extends BaseMvpPresenter<ArticlesFragmentInterface.View>
        implements ArticlesFragmentInterface.Presenter{

    private List<ItemAppointmentDao> listData;

    public static ArticlesFragmentInterface.Presenter create(){
        return new ArticlesFragmentPresenter();
    }

    @Override
    public void loadData() {
        callGetListArticles( getView().getDoctorId(), getView().getArticleGroupId());
    }

    private void callGetListArticles(int doctorId, int articleGroupId) {
        getView().showLoading();

        Call<ApiListArticles> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getArticles(doctorId, articleGroupId);
        call.enqueue(new Callback<ApiListArticles>() {
            @Override
            public void onResponse(Call<ApiListArticles> call, Response<ApiListArticles> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setData(response.body().getListData());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiListArticles> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
