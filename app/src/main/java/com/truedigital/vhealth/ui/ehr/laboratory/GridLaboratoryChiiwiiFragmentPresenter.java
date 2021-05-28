package com.truedigital.vhealth.ui.ehr.laboratory;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiGridLaboratoryChiiwiiObject;
import com.truedigital.vhealth.model.ColumnHeaderChiiwiiLabObject;
import com.truedigital.vhealth.model.GridMatrixDataListChiiwiiLabObject;
import com.truedigital.vhealth.model.RowHeaderChiiwiiLabObject;
import com.truedigital.vhealth.model.GridLaboratoryChiiwiiCriteriaObject;
import com.truedigital.vhealth.model.LaboratoryChiiwiiObject;
import com.truedigital.vhealth.model.GridLaboratoryChiiwiiObject;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class GridLaboratoryChiiwiiFragmentPresenter extends BaseMvpPresenter<GridLaboratoryChiiwiiFragmentInterface.View>
        implements GridLaboratoryChiiwiiFragmentInterface.Presenter {

    public static GridLaboratoryChiiwiiFragmentInterface.Presenter create() {
        return new GridLaboratoryChiiwiiFragmentPresenter();
    }

    public void getGridDataLaboratoryChiiwii(GridLaboratoryChiiwiiCriteriaObject criteria) {
        getView().showLoading();

        String access_token = AppManager.getDataManager().getAccess_token();

        Call<ApiGridLaboratoryChiiwiiObject> call = getRetrofitToken(access_token).getGridChiiwiiLab(criteria.getPatientId(), criteria.getPatientMenuId());
        call.enqueue(new Callback<ApiGridLaboratoryChiiwiiObject>() {
            @Override
            public void onResponse(Call<ApiGridLaboratoryChiiwiiObject> call, Response<ApiGridLaboratoryChiiwiiObject> response) {
                if (response.isSuccessful() && response.body().isSuccess()) {

                    GridLaboratoryChiiwiiObject data = response.body().getData();
                    if (data != null) {
                        data.setColumnHeaderListTemp(new ArrayList<String>());

                        for (RowHeaderChiiwiiLabObject row : data.getRowHeaderList()) {
                            row.setText(row.getName());
                        }

                        for (ColumnHeaderChiiwiiLabObject column : data.getColumnHeaderList()) {
                            if (column.getLabDateString() != null && column.getLabDateString() != "") {

                                column.setLabDate(ConvertDate.StrToDateFormat(column.getLabDateString()));
                            }

                            data.getColumnHeaderListTemp().add(ConvertDate.convertDateSimpleShow(column.getLabDate()));
                        }

                        for (GridMatrixDataListChiiwiiLabObject matrixDataList : data.getRowDataList()) {
                            for (LaboratoryChiiwiiObject item : matrixDataList.getDataItemList()) {

                                item.setText(item.getValue());

                                if (item.getLabDateString() != null && item.getLabDateString() != "") {
                                    item.setLabDate(ConvertDate.StrToDateFormat(item.getLabDateString()));
                                }
                            }
                        }
                    }

                    getView().setGridDataLaboratoryChiiwii(data);

                } else {
                    if (response.raw().code() == AppConstants.AUTHEN_UNAUTHORIZED) {
                        getView().token_expired();
                    } else {
                        getView().showMessage(response);
                    }
                }

                getView().hideLoading();
            }

            @Override
            public void onFailure(Call<ApiGridLaboratoryChiiwiiObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

}
