package com.truedigital.vhealth.ui.appointment.list;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.model.appointment.ListAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.utils.AppointmentUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;

public class ListAppointmentFragmentPresenter extends BaseMvpPresenter<ListAppointmentFragmentInterface.View>
        implements ListAppointmentFragmentInterface.Presenter {

    private List<ItemAppointmentDao> listData;
    private int tempSortBy = -1;
    private int tempMenuItem = -1;

    public static ListAppointmentFragmentInterface.Presenter create() {
        return new ListAppointmentFragmentPresenter();
    }

    @Override
    public void loadData() {
        int appointment_type = -1; //getView().getAppointmentType();
        callGetListAppointments(0, appointment_type);
    }

    private void callGetListAppointments(int sortBy, int menuItem) {
        if (listData != null && !listData.isEmpty() && tempSortBy == sortBy && tempMenuItem == menuItem) {
            getView().setData(listData);
            return;
        }

        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return;
        }

        getView().showLoading();
        String access_token = AppManager.getDataManager().getAccess_token();
        int appointment_type;
        String strSortBy;
        if (sortBy == 0) {
            strSortBy = "AppointmentTime";
            appointment_type = menuItem;
        } else {
            strSortBy = "DoctorName";
            appointment_type = menuItem;
        }
        boolean isOnlyNotEndAppointment = (appointment_type == AppointmentUtil.APPOINTMENT_INCOMING);
        boolean IsOnlyEndAppointment = (appointment_type == AppointmentUtil.APPOINTMENT_HISTORY);
        boolean IsOnlyCancel = (appointment_type == AppointmentUtil.APPOINTMENT_CANCEL);
        String strSortDirection = "DESC";
        if (isOnlyNotEndAppointment) {
            strSortDirection = "ASC";
        }

        Call<ListAppointmentDao> call = getAppointmentService(access_token).getListAppointments(isOnlyNotEndAppointment, IsOnlyEndAppointment, IsOnlyCancel, strSortBy, strSortDirection);
        call.enqueue(new Callback<ListAppointmentDao>() {
            @Override
            public void onResponse(Call<ListAppointmentDao> call, Response<ListAppointmentDao> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    listData = response.body().getListData();
                    tempSortBy = sortBy;
                    tempMenuItem = menuItem;
                    getView().setData(listData);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListAppointmentDao> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void loadData(int sortBy, int menu) {
        callGetListAppointments(sortBy, menu);
    }
}
