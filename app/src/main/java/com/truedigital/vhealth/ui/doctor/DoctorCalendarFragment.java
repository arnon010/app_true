package com.truedigital.vhealth.ui.doctor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.calendar.CalendarDoctorCustom;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;

public class DoctorCalendarFragment extends BaseMvpFragment<DoctorCalendarFragmentInterface.Presenter>
        implements DoctorCalendarFragmentInterface.View{


    private Button btnDone;
    private int doctorId;

    public DoctorCalendarFragment() {
        super();
    }

    public static DoctorCalendarFragment newInstance(int doctorId) {
        DoctorCalendarFragment fragment = new DoctorCalendarFragment();
        Bundle args = new Bundle();
        fragment.doctorId = doctorId;
        //args.putParcelable(KEY_PARCELABLE,data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public DoctorCalendarFragmentInterface.Presenter createPresenter(){
        return DoctorCalendarFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_doctor_calendar;
    }

    @Override
    public void bindView( View view ){
        btnDone = (Button) view.findViewById(R.id.btn_done);
    }

    @Override
    public void setupInstance(){

    }

    private void hideToolbar() {
        ((MainActivity) getActivity()).hideToolbar();
    }

    @Override
    public void setupView(){
        hideToolbar();
        setupCalendar();
    }

    private void setupCalendar() {

        int doctorId = getDoctorId();
        final CalendarDoctorCustom calendarCustom = new CalendarDoctorCustom(getActivity(), doctorId);
        calendarCustom.setOnItemClickListener(new CalendarDoctorCustom.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                /*
                setDateSelect( calendarCustom.getSelectedDate(position) );
                dateSelect = calendarCustom.getSelectedDate(position);
                Date dateSelectDate = calendarCustom.getSelectedDateToDate(position);
                dateName = getDateName(dateSelectDate);
                txtDate.setText(ConvertDate.convertStringShow(dateSelect));
                listTimeSlot = null;

                doctorSchedulesData.setScheduledDate(dateSelect);
                doctorSchedulesData.setSelectDate(dateSelectDate);
                if (calendarCustom.isOpenTimeSlot(position)) {
                    //.. cal api get time slot in day

                    //Calendar calendar = Calendar.getInstance();
                    //calendar.setTime(dateSelectDate);
                    //int year = calendar.get(Calendar.YEAR);
                    //int month = calendar.get(Calendar.MONTH);
                    //int day = calendar.get(Calendar.DAY_OF_MONTH);

                    //String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

                    getPresenter().loadTimeSlot();

                }
                updateView();
                */

            }
        });

    }


    private void setupListener() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void initialize(){

    }

    @Override
    public void restoreView( Bundle savedInstanceState ){

    }

    @Override
    public void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
    }

    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
    }

    @Override
    public int getDoctorId() {
        return doctorId;
    }

    @Override
    public void setDoctorCalendar() {
        getPresenter().getDoctorTimeSchedules( getDoctorId());
    }
}

