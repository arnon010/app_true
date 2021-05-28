package com.truedigital.vhealth.ui.doctor;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ListItemTimeSlotDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class DoctorCalendarActivityPresenter extends BaseMvpPresenter<DoctorCalendarActivityInterface.View>
        implements DoctorCalendarActivityInterface.Presenter{

    private static final String TAG = DoctorCalendarActivityPresenter.class.getName();


    public static DoctorCalendarActivityInterface.Presenter create() {
        return new DoctorCalendarActivityPresenter();
    }

    @Override
    public void loadTimeSlot() {
        if (!getView().isNetworkConnected()) {
            getView().showMessage(R.string.network_disconnect);
            return ;
        }

        int doctorId = getView().getDoctorId();
        String dateSelect = getView().getSelectDate();
        String[] listStrDate = dateSelect.split("/");
        int year = new Integer(listStrDate[2]);
        int month = new Integer(listStrDate[1]);
        int day = new Integer(listStrDate[0]);

        callApiGetSchedulesOpenTimeSlot(doctorId,year,month,day);
    }

    private void callApiGetSchedulesOpenTimeSlot(int doctorId, int year, int month, int day) {
        String access_token = AppManager.getDataManager().getAccess_token();
        Call<ListItemTimeSlotDao> call = getRetrofitToken(access_token).getDoctorSchedules(doctorId, year, month, day);
        call.enqueue(new Callback<ListItemTimeSlotDao>() {
            @Override
            public void onResponse(Call<ListItemTimeSlotDao> call, Response<ListItemTimeSlotDao> response) {
                if (response.isSuccessful()) {
                    //getView().setData(response.body().getListData());
                    getView().setDataTimeSlot(response.body().getListData());
                }
                else {
                    //getView().setData(response.body().getListData());
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ListItemTimeSlotDao> call, Throwable t) {
                getView().showMessage(""+t.getMessage());
            }
        });
    }
}


