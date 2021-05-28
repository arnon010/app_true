package com.truedigital.vhealth.calendar;

import android.app.Activity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiDoctorSchedules;
import com.truedigital.vhealth.model.ApiMonthReadyTimeObject;
import com.truedigital.vhealth.model.CalendarObject;
import com.truedigital.vhealth.ui.adapter.CalendarDayOfWeekAdapter;
import com.truedigital.vhealth.ui.adapter.CalendarDoctorAdapter;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;


/**
 * Created by nilecon on 1/20/2017 AD.
 */

public class CalendarDoctorCustom implements View.OnClickListener {
    private static final String TAG = "CalendarDoctor";
    private ArrayList<CalendarObject> calendarArrayList = new ArrayList<>();
    private int doctorId = 0;

    private Activity activity;
    private Calendar calendarCurrent;
    private Calendar calendar;

    private RecyclerView rvCalendar;
    private RecyclerView rvDayOfWeek;
    private TextView txtDate;
    private ImageView imgPrevious;
    private ImageView imgNext;

    private CalendarDoctorAdapter calendarAdapter;
    private OnItemClickListener onItemClickListener;
    //private NetworkStatus networkStatus;

    /**
     * include layout calendar custom before
     **/
    public CalendarDoctorCustom(Activity activity, int doctorId) {
        rvCalendar = (RecyclerView) activity.findViewById(R.id.recycleviewDayOfMonth);
        rvDayOfWeek = (RecyclerView) activity.findViewById(R.id.recycleviewDayOfWeek);
        txtDate = (TextView) activity.findViewById(R.id.textviewDate);
        imgNext = (ImageView) activity.findViewById(R.id.imageviewNext);
        imgPrevious = (ImageView) activity.findViewById(R.id.imageviewPrevious);
        //networkStatus = new NetworkStatus(activity);

        imgNext.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);

        this.activity = activity;
        this.doctorId = doctorId;

        calendar = Calendar.getInstance();
        calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(ConvertDate.convertDateFormat(new Date()));
        calendar.setTime(ConvertDate.convertDateFormat(calendarCurrent.getTime()));

//        txtDate.setText(activity.getString(R.string.appointments_doctor_date_now
//                , ConvertDate.convertDateSimpleShow(calendarCurrent.getTime())));

        setRecycleViewDayOfWeek();

        callServiceMonthReadyTime(calendar);
    }

    public void setRecycleViewCalendar() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 7, LinearLayoutManager.VERTICAL, false);
        rvCalendar.setLayoutManager(gridLayoutManager);
        calendarAdapter = new CalendarDoctorAdapter(activity, calendarArrayList);
        rvCalendar.setAdapter(calendarAdapter);
        rvCalendar.addItemDecoration(new ListDividerItemDecoration(activity, R.drawable.line_divider_gray, false));

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendarArrayList.get(calendarArrayList.size() - 1).getDate());
        txtDate.setText(ConvertDate.convertDateMonthYearShow(calendarCurrent.getTime()));

        setOnDateChangeListener();

    }

    private void setOnDateChangeListener() {
        calendarAdapter.setOnClickListener(new CalendarDoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    private void setDayOfMonthList(Calendar calendarSelected, ArrayList<ApiMonthReadyTimeObject.DayOfMonth> readyTimeArrayList) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(ConvertDate.convertDateFormat(calendarSelected.getTime())); //set day of month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); //set first day of month
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendarArrayList = new ArrayList<>();
        for (int i = 0; i < maxDayOfMonth; i++) {
            if (i == 0) {
                if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    for (int j = 1; j < calendar.get(Calendar.DAY_OF_WEEK); j++) {
                        calendarArrayList.add(new CalendarObject()); //blank day
                    }
                }
            }
            if (calendar.get(Calendar.DAY_OF_MONTH) <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                //set free time
                CalendarObject calendarObject = new CalendarObject();
                calendarObject.setDate(calendar.getTime());
                if (i < readyTimeArrayList.size()) {
                    calendarObject.setFree(readyTimeArrayList.get(i).isHaveTimeReady());
                }
                calendarArrayList.add(calendarObject);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        setRecycleViewCalendar();
    }

    private void setDoctorSchedules(Calendar calendarSelected, List<ApiDoctorSchedules.Schedules> readyTimeArrayList) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(ConvertDate.convertDateFormat(calendarSelected.getTime())); //set day of month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); //set first day of month
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendarArrayList = new ArrayList<>();
        for (int i = 0; i < maxDayOfMonth; i++) {
            if (i == 0) {
                if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    for (int j = 1; j < calendar.get(Calendar.DAY_OF_WEEK); j++) {
                        calendarArrayList.add(new CalendarObject()); //blank day
                    }
                }
            }
            if (calendar.get(Calendar.DAY_OF_MONTH) <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                //set free time
                CalendarObject calendarObject = new CalendarObject();
                calendarObject.setDate(calendar.getTime());
                if (i < readyTimeArrayList.size()) {
                    calendarObject.setFree(readyTimeArrayList.get(i).isHasOpenSlot());
                    calendarObject.setHasOpenSlot(readyTimeArrayList.get(i).isHasOpenSlot());
                    calendarObject.setHasAvailableSlot(readyTimeArrayList.get(i).isHasAvailableSlot());
                }
                calendarArrayList.add(calendarObject);
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        setRecycleViewCalendar();

    }

    public void setRecycleViewDayOfWeek() {
        ArrayList<Date> dateArraylist = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        for (int i = 0; i < 7; i++) {
            dateArraylist.add(ConvertDate.convertStringToDate(ConvertDate.convertDateToString(calendar.getTime())));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 7, LinearLayoutManager.VERTICAL, false);
        rvDayOfWeek.setLayoutManager(gridLayoutManager);
        CalendarDayOfWeekAdapter calendarAdapter = new CalendarDayOfWeekAdapter(activity, dateArraylist);
        rvDayOfWeek.setAdapter(calendarAdapter);
        //rvDayOfWeek.addItemDecoration(new ListDividerItemDecoration(activity, R.drawable.line_divider_white, false));

    }

    private void callServiceMonthReadyTime(final Calendar calendar) {
        /*
        //if (networkStatus.checkNetworkConnection()) {
            Calendar calendarService = Calendar.getInstance();
            calendarService.setTime(calendar.getTime());
            calendarService.set(Calendar.DAY_OF_MONTH, 1);
            Call<ApiMonthReadyTimeObject> readyTimeObjectCall = getRetrofitToken("")
                    .postDoctorMonthReadyTime(doctorId, ConvertDate.convertDateToStringServer(calendarService.getTime()));
            readyTimeObjectCall.enqueue(new Callback<ApiMonthReadyTimeObject>() {
                @Override
                public void onResponse(Call<ApiMonthReadyTimeObject> call, Response<ApiMonthReadyTimeObject> response) {
                    ApiMonthReadyTimeObject readyTimeObject = response.body();
                    if (readyTimeObject.isSuccess()) {
                        setDayOfMonthList(calendar, readyTimeObject.getDayOfMonthArrayList());
                        txtDate.setText(ConvertDate.convertDateMonthYearShow(calendar.getTime()));

                    }
                }

                @Override
                public void onFailure(Call<ApiMonthReadyTimeObject> call, Throwable t) {

                }
            });
        //}
        */
        Calendar calendarService = Calendar.getInstance();
        calendarService.setTime(calendar.getTime());
        calendarService.set(Calendar.DAY_OF_MONTH, 1);

        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH)+1;
        if (thisMonth == 0) thisMonth = 1;
        String access_token = AppManager.getDataManager().getAccess_token();
        Call<ApiDoctorSchedules> call = getRetrofitToken(access_token).getDoctorSchedules(doctorId, thisYear, thisMonth);
        call.enqueue(new Callback<ApiDoctorSchedules>() {
            @Override
            public void onResponse(Call<ApiDoctorSchedules> call, Response<ApiDoctorSchedules> response) {
                if (response.isSuccessful()) {
                    setDoctorSchedules(calendar, response.body().getSchedules());
                    txtDate.setText(ConvertDate.convertDateMonthYearShow(calendar.getTime()));
                }
                else {
                    Log.e(TAG,"not success");
                }
            }

            @Override
            public void onFailure(Call<ApiDoctorSchedules> call, Throwable t) {
                Log.e(TAG,"Fail");
            }
        });
    }

    public boolean isOpenTimeSlot(int position) {
        return calendarAdapter.isOpenTimeSlot(position);
    }

    public boolean isHasAvailableSlot(int position) {
        return calendarArrayList.get(position).isHasAvailableSlot();
    }

    public Date getSelectedDateToDate(int position) {
        return calendarAdapter.getSelectedDateToDate(position);
    }
    //dd/MM/yyyy
    public String getSelectedDate(int position) {
        return calendarAdapter.getSelectedDate(position);
    }

    //arrow next button
    public void nextMonth() {
        calendar.add(Calendar.MONTH, 1);
        callServiceMonthReadyTime(calendar);
    }

    //arrow previous button
    public void previousMonth() {
        calendar.add(Calendar.MONTH, -1);
        callServiceMonthReadyTime(calendar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageviewPrevious:
                previousMonth();
                break;
            case R.id.imageviewNext:
                nextMonth();
                break;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
