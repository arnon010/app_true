package com.truedigital.vhealth.ui.product;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ListProductBannerDao;
import com.truedigital.vhealth.model.ListProductGroupDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ProductFragmentPresenter extends BaseMvpPresenter<ProductFragmentInterface.View>
        implements ProductFragmentInterface.Presenter{

    public static ProductFragmentInterface.Presenter create(){
        return new ProductFragmentPresenter();
    }

    @Override
    public void loadData() {
        //callGetListProductBanner();
        callGetListProductGroups();
    }

    private void callGetListProductBanner() {
        getView().showLoading();
        //String token = "lw1TQMeBNswQUr-wHIWxRu4HaBeaMEEWh2GkyKXkcB4h-pUuGm0bbG7MYQxFOpfY6mqiljstILoXX1iX7uSXIG3aaRMAr3D3bWn0yPZW9Fthmijpt3RG3LEuYwTP8VmP6cBF9-aMfJ13Zolctk-H0Z5JpDkE0to1FuzkkcbAmkXs_QB_dv2XhDXK2Q1Kp-4ipuP1ZKy7YyOYgk4vU77seF82TDGDjQ0JEyhEaBe-IaZbdbF9SXVfSGNzKafxLb9rAjrdDxvAtVEDfxzd-FdqoIGdXNoU6lgc48ZzM3MuooJYZ1rdPjUQZkl4LMXwnU3t_UZP8QMcjVzwoeaJhFsvG651V4WPTyWRPmf8XiH_ZHWVcdPLspeqt22KF_1c5KixS7yRLIiHcxqGK7nFwze6JCi8N_KpFa4pJ-4dKeXUwNNvWX_jL7mEfONkKlEI0zMb1ONjxqo-ZlZoagxNMqxHR09meMKSX5K7LjuqqGmiEORRIJesYejEqxrzMsQH6p-KYGh9Qx1Kb4o6hKUYAH4cjqQG81z3qeMHyr3w14QLFCV2iSY4WJgMvLL7WuqkATgt-iXB_4RJ_NYJDnsuqh_zIbT75UgpGrb_2zz7YXT7q-s_nguwie2j5lYvHvkaA5rHu5pG2JSmcOcTFma3OBuUtfEQFaPVuDnHJ8rupSLs8ZRNUVI71eCf7fmnqiQpCJxS5maUHZnbLM73XYIx-SVo8hROijfrF8lEIIJp9NUgW9KnkGkgjW6E4pFYUOoUagDEdMqRIWf_17P8hvgB6nNU6w";
        Call<ListProductBannerDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListProductBanner();
        call.enqueue(new Callback<ListProductBannerDao>() {
            @Override
            public void onResponse(Call<ListProductBannerDao> call, Response<ListProductBannerDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setDataBanner(response.body().getListData());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListProductBannerDao> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

    private void callGetListProductGroups() {
        getView().showLoading();

        Call<ListProductGroupDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListProductGroups(true,true);
        call.enqueue(new Callback<ListProductGroupDao>() {
            @Override
            public void onResponse(Call<ListProductGroupDao> call, Response<ListProductGroupDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setData(response.body().getListData());
                }
                else {
                    getView().showMessage(response);
                    /*
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    }
                    */
                }
            }

            @Override
            public void onFailure(Call<ListProductGroupDao> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
