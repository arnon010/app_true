package com.truedigital.vhealth.ui.ehr.healthinformation.menstrualperiod;

import android.os.Bundle;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.calendar.CalendarCustom;
import com.truedigital.vhealth.model.CalendarObject;
import com.truedigital.vhealth.model.MenstruationObject;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MenstrualPeriodFragment extends BaseMvpFragment<MenstrualPeriodFragmentInterface.Presenter>
        implements MenstrualPeriodFragmentInterface.View {

    private int patientMenuId;
    private int patientId;
    private String menuCode;

    private View calendar;
    private CalendarCustom calendarCustom;
    private ArrayList<Date> activeDateList;
    private ArrayList<MenstruationObject> dataList;

    private int defaultMonth;

    public MenstrualPeriodFragment() {
        super();
    }

    public static MenstrualPeriodFragment newInstance(int patientId, int patientMenuId, String menuCode) {
        MenstrualPeriodFragment fragment = new MenstrualPeriodFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;
        fragment.menuCode = menuCode;
        Calendar calendar = Calendar.getInstance();
        fragment.defaultMonth = calendar.get(Calendar.MONTH);

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MenstrualPeriodFragmentInterface.Presenter createPresenter() {
        return MenstrualPeriodFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_menstrual_period;
    }

    @Override
    public void bindView(View view) {
        calendar = (View) view.findViewById(R.id.calendar);
    }

    @Override
    public void setupInstance() {
    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();
    }

    @Override
    public void initialize() {
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.menstrual_period), false,null, false);
        this.setupCanlendar();
    }

    @Override
    public void restoreView(Bundle savedInstanceState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setupCanlendar() {
        getPresenter().getMenstrualPeriodList(patientId, patientMenuId);
    }

    private CalendarCustom.OnDateChangeListener onDateSelect() {
        return new CalendarCustom.OnDateChangeListener() {
            @Override
            public void onDateChangeListener(CalendarObject dateSelect) {
                if (activeDateList.contains(dateSelect.getDate()) && !dateSelect.isActive()) {
                    int index = activeDateList.indexOf(dateSelect.getDate());
                    MenstruationObject deleteData = dataList.get(index);
                    getPresenter().deleteMenstrualPeriod(patientId, deleteData);

                } else if (!activeDateList.contains(dateSelect.getDate()) && dateSelect.isActive()) {
                    MenstruationObject newData = new MenstruationObject();
                    newData.setMenstruationDate(dateSelect.getDate());
                    getPresenter().newMenstrualPeriod(patientId, patientMenuId, menuCode, newData);
                }
            }
        };
    }

    @Override
    public void setMenstrualPeriodList(ArrayList<MenstruationObject> listData) {
        activeDateList = new ArrayList<>();
        for (MenstruationObject data : listData) {
            data.setMenstruationDate(ConvertDate.convertDateFormat(data.getMenstruationDate()));
            activeDateList.add(data.getMenstruationDate());
        }

        this.dataList = listData;

        if(calendarCustom != null)
        {
            this.defaultMonth = calendarCustom.getCurrentMonth();
        }
        calendarCustom = new CalendarCustom(getActivity(), calendar, null, true, activeDateList, null, R.color.black, R.color.color_text_white_green_selector, R.drawable.circle_red_selector);
        calendarCustom.setCalendarMonth(this.defaultMonth);
        calendarCustom.setOnDateChangeListener(this.onDateSelect());
    }

    @Override
    public void onNewSuccess(MenstruationObject data) {
        this.patientMenuId = data.getPatientMenuId();
        this.setupCanlendar();
    }

    @Override
    public void onNewError() {
        this.setupCanlendar();
    }

    @Override
    public void onDeleteSuccess() {
        this.setupCanlendar();
    }

}
