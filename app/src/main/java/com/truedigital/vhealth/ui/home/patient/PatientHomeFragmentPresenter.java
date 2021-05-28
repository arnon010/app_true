package com.truedigital.vhealth.ui.home.patient;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ItemCategoryDao;
import com.truedigital.vhealth.model.ItemProductBannerDao;
import com.truedigital.vhealth.model.ItemSubCategoryDao;
import com.truedigital.vhealth.model.ListArticlesBannerDao;
import com.truedigital.vhealth.model.ListCategoryDao;
import com.truedigital.vhealth.model.ListProductBannerDao;
import com.truedigital.vhealth.model.ListSubCategoryDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class PatientHomeFragmentPresenter extends BaseMvpPresenter<PatientHomeFragmentInterface.View>
        implements PatientHomeFragmentInterface.Presenter {

    private List<ItemSubCategoryDao> itemCategoryDaoList;
    private List<ItemProductBannerDao> itemProductBannerDaoList;
    private List<ItemArticleDao> itemArticleDaoList;


    public static PatientHomeFragmentInterface.Presenter create() {
        return new PatientHomeFragmentPresenter();
    }

    @Override
    public void loadListHealthcare() {
        if (itemCategoryDaoList != null && !itemCategoryDaoList.isEmpty()) {
            getView().setDataHealthcare(itemCategoryDaoList);
            return;
        }

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        getView().showLoading();
        Call<ListSubCategoryDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token())
                .getListSubCategories("5445", true);
        call.enqueue(new Callback<ListSubCategoryDao>() {
            @Override
            public void onResponse(Call<ListSubCategoryDao> call, Response<ListSubCategoryDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    itemCategoryDaoList = response.body().getListData();
                    getView().setDataHealthcare(itemCategoryDaoList);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListSubCategoryDao> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }

    @Override
    public void loadDataPromotion() {
        callGetListProductBanner();
    }

    private void callGetListProductBanner() {
        if (itemProductBannerDaoList != null && !itemProductBannerDaoList.isEmpty()) {
            getView().setDataBannerProduct(itemProductBannerDaoList);
            return;
        }

        getView().showLoading();
        Call<ListProductBannerDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListProductBanner();
        call.enqueue(new Callback<ListProductBannerDao>() {
            @Override
            public void onResponse(Call<ListProductBannerDao> call, Response<ListProductBannerDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    itemProductBannerDaoList = response.body().getListData();
                    getView().setDataBannerProduct(itemProductBannerDaoList);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListProductBannerDao> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void loadDataArticle() {
        callGetListArticlesBanner();
    }

    private void callGetListArticlesBanner() {
        if (itemArticleDaoList != null && !itemArticleDaoList.isEmpty()) {
            getView().setDataBannerArticle(itemArticleDaoList);
            return;
        }

        getView().showLoading();
        Call<ListArticlesBannerDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListArticlesBanner();
        call.enqueue(new Callback<ListArticlesBannerDao>() {
            @Override
            public void onResponse(Call<ListArticlesBannerDao> call, Response<ListArticlesBannerDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    itemArticleDaoList = response.body().getListData();
                    getView().setDataBannerArticle(itemArticleDaoList);
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
