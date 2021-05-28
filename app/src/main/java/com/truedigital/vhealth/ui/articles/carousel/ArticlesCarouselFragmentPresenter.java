package com.truedigital.vhealth.ui.articles.carousel;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ListArticlesBannerDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ArticlesCarouselFragmentPresenter extends BaseMvpPresenter<ArticlesCarouselFragmentInterface.View>
        implements ArticlesCarouselFragmentInterface.Presenter {

    private List<ItemArticleDao> listData;

    public static ArticlesCarouselFragmentInterface.Presenter create() {
        return new ArticlesCarouselFragmentPresenter();
    }

    @Override
    public void loadData() {
        callGetListArticlesBanner();
    }

    private void callGetListArticlesBanner() {
        if (listData != null && !listData.isEmpty()) {
            getView().setDataBanner(listData);
            return;
        }

        getView().showLoading();
        Call<ListArticlesBannerDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListArticlesBanner();
        call.enqueue(new Callback<ListArticlesBannerDao>() {
            @Override
            public void onResponse(Call<ListArticlesBannerDao> call, Response<ListArticlesBannerDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    listData = response.body().getListData();
                    getView().setDataBanner(listData);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListArticlesBannerDao> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
