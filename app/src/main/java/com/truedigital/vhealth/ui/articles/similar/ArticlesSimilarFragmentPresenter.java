package com.truedigital.vhealth.ui.articles.similar;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListArticles;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ArticlesSimilarFragmentPresenter extends BaseMvpPresenter<ArticlesSimilarFragmentInterface.View>
        implements ArticlesSimilarFragmentInterface.Presenter{

    private List<ItemAppointmentDao> listData;

    public static ArticlesSimilarFragmentInterface.Presenter create(){
        return new ArticlesSimilarFragmentPresenter();
    }

    @Override
    public void loadData() {
        getArticlesSimilar(getView().getArticleId());
    }

    @Override
    public void getArticlesSimilar(int articleId) {
        getView().showLoading();
        Call<ApiListArticles> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getArticlesSimilar(articleId);
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
