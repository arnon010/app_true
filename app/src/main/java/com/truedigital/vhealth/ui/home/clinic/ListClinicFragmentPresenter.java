package com.truedigital.vhealth.ui.home.clinic;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ListClinicDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class ListClinicFragmentPresenter extends BaseMvpPresenter<ListClinicFragmentInterface.View>
        implements ListClinicFragmentInterface.Presenter{

    public static ListClinicFragmentInterface.Presenter create(){
        return new ListClinicFragmentPresenter();
    }

    @Override
    public void loadData() {
        callGetListClinics();
    }

    private void callGetListClinics() {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }
        getView().showLoading();
        Call<ListClinicDao> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListClinics(true,true);
        call.enqueue(new Callback<ListClinicDao>() {
            @Override
            public void onResponse(Call<ListClinicDao> call, Response<ListClinicDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().setData(response.body().getListData());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListClinicDao> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(""+t.getMessage());
            }
        });
    }

}
