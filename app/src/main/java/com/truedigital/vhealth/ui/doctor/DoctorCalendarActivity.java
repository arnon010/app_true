package com.truedigital.vhealth.ui.doctor;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.calendar.CalendarDoctorCustom;
import com.truedigital.vhealth.model.ItemDoctorScheduleTimeDao;
import com.truedigital.vhealth.model.ItemTimeSlotDao;
import com.truedigital.vhealth.ui.adapter.ListDoctorTimeSlotAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class DoctorCalendarActivity extends BaseMvpActivity<DoctorCalendarActivityInterface.Presenter>
        implements DoctorCalendarActivityInterface.View{

    private EditText edUserName;
    private EditText edEmail;
    private Button btnDone;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private NestedScrollView nestedScrollView;
    private boolean isScroll;
    private boolean appBarExpanded;
    private String dateSelect;
    private List<ItemTimeSlotDao> listTimeSlot;
    private RecyclerView rv_timeslot;
    private ListDoctorTimeSlotAdapter adapter_timeslot;
    private TextView txtDate;
    private TextView tvNotfound;
    private Button ic_back_button;
    private ImageView img_back;
    private TextView tv_title;

    @Override
    protected DoctorCalendarActivityInterface.Presenter createPresenter() {
        return DoctorCalendarActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.dialog_doctor_calendar;
    }

    @Override
    protected void bindView() {
        rv_timeslot = (RecyclerView) findViewById(R.id.recycler_view_timeslot);
        tv_title = (TextView) findViewById(R.id.tv_title);
        txtDate = (TextView) findViewById(R.id.tv_date);
        tvNotfound = (TextView) findViewById(R.id.tv_notfound);
        tvNotfound.setVisibility(View.GONE);

        img_back = (ImageView) findViewById(R.id.img_back);
    }


    @Override
    protected void initInstance() {

    }

    @Override
    protected void setupView() {

        setDoctorCalendar();
        setupRecycleViewTimeSlot();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(txtDate);
    }


    @Override
    protected void initialize() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public int getDoctorId() {
        return getIntent().getIntExtra(AppConstants.EXTRA_DOCTOR_ID,0);
    }

    private void setupRecycleViewTimeSlot() {
        adapter_timeslot = new ListDoctorTimeSlotAdapter(rv_timeslot.getContext(),true);
        rv_timeslot.setAdapter(adapter_timeslot);
        rv_timeslot.setHasFixedSize(true);

        rv_timeslot.setLayoutManager(new LinearLayoutManager(this));

        adapter_timeslot.setOnClickListener(new ListDoctorTimeSlotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //adapter_timeslot.setSelection(position);
                //dateSelect = adapter_timeslot.getData(position).getScheduleTime();
                //appointmentData.setAppointmentTime(adapter_timeslot.getData(position).getScheduleTime());
                Log.e("Slot","time select " + adapter_timeslot.getData(position).getScheduleTime());
                onSelectSlot(adapter_timeslot.getData(position).getScheduleTime());

            }
        });

    }

    private void onSelectSlot(String scheduleTime) {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.EXTRA_TIMESLOT_DATE, dateSelect);
        intent.putExtra(AppConstants.EXTRA_TIMESLOT_TIME, scheduleTime);

        setResult(RESULT_OK, intent);
        finish();
        finish();
    }

    @Override
    public void setDoctorCalendar() {
        final CalendarDoctorCustom calendarCustom = new CalendarDoctorCustom(this, getDoctorId());
        calendarCustom.setOnItemClickListener(new CalendarDoctorCustom.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                //setDateSelect( calendarCustom.getSelectedDate(position) );
                dateSelect = calendarCustom.getSelectedDate(position);
                Date dateSelectDate = calendarCustom.getSelectedDateToDate(position);
                //dateName = getDateName(dateSelectDate);
                txtDate.setText(ConvertDate.convertStringShow(dateSelect));
                //listTimeSlot = null;

                //doctorSchedulesData.setScheduledDate(dateSelect);
                //doctorSchedulesData.setSelectDate(dateSelectDate);
                if (calendarCustom.isHasAvailableSlot(position)) {
                    //.. cal api get time slot in day

                    //Calendar calendar = Calendar.getInstance();
                    //calendar.setTime(dateSelectDate);
                    //int year = calendar.get(Calendar.YEAR);
                    //int month = calendar.get(Calendar.MONTH);
                    //int day = calendar.get(Calendar.DAY_OF_MONTH);

                    //String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

                    getPresenter().loadTimeSlot();

                }
                else {
                    showNotFound();
                }
                //updateView();


            }
        });
    }


    @Override
    public String getSelectDate() {
        return dateSelect;
    }

    @Override
    public void setDataTimeSlot(List<ItemTimeSlotDao> listData) {
        listTimeSlot = listData;
        //adapter_timeslot.setListData(data);
        List<ItemDoctorScheduleTimeDao> listScheduleTime = new ArrayList<ItemDoctorScheduleTimeDao>();
        ItemDoctorScheduleTimeDao itemData;
        for(ItemTimeSlotDao item:listData) {
            if (item.isAvailable()) {
                itemData = new ItemDoctorScheduleTimeDao();

                itemData.setScheduleTime(item.getTimes());
                listScheduleTime.add(itemData);
            }
        }
        adapter_timeslot.setListData(listScheduleTime);

        if (listScheduleTime.size() <= 0) {
            tvNotfound.setVisibility(View.VISIBLE);
            rv_timeslot.setVisibility(View.GONE);
        }
        else {
            tvNotfound.setVisibility(View.GONE);
            rv_timeslot.setVisibility(View.VISIBLE);
        }
        //updateView();
    }

    private void showNotFound() {
        tvNotfound.setVisibility(View.VISIBLE);
        rv_timeslot.setVisibility(View.GONE);
    }
}
