package com.truedigital.vhealth.calendar;


import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.CalendarObject;
import com.truedigital.vhealth.ui.adapter.CalendarAdapter;
import com.truedigital.vhealth.ui.adapter.CalendarDayOfWeekAdapter;
import com.truedigital.vhealth.utils.ConvertDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarCustom implements View.OnClickListener {

    private boolean noHaveCurrent;
    private ArrayList<CalendarObject> calendarArrayList = new ArrayList<>();

    private Context context;
    private Calendar calendarCurrent;
    private Calendar calendar;
    private boolean isSelectMultiple;
    private boolean isDisableFuture;
    private int textColorDayOfWeek;
    private int textColorDayOfMonth;
    private int textBackgroundDayOfMonth;
    private List<Date> activeDateList = new ArrayList<>();
    private List<Date> inActiveDateList = new ArrayList<>();

    private RecyclerView rvCalendar;
    private RecyclerView rvDayOfWeek;
    private TextView txtDate;
    private ImageView imgPrevious;
    private ImageView imgNext;

    private CalendarAdapter calendarAdapter;
    private OnItemClickListener onItemClickListener;
    private OnDateChangeListener onDateChangeListener;

    /**
     * include layout calendar custom before
     **/
    public CalendarCustom(Context context, View view, Date currentDate, boolean isSelectMultiple) {
        this.isSelectMultiple = isSelectMultiple;

        this.setupConstructor(context, view, currentDate);
    }

    public CalendarCustom(Context context, View view, Date currentDate, boolean isSelectMultiple, boolean isDisableFuture) {
        this.isSelectMultiple = isSelectMultiple;
        this.isDisableFuture = isDisableFuture;

        this.setupConstructor(context, view, currentDate);
    }

    public CalendarCustom(Context context, View view, Date currentDate, boolean isSelectMultiple, int textColorDayOfWeek, int textColorDayOfMonth, int textBackgroundDayOfMonth) {
        this.textColorDayOfWeek = textColorDayOfWeek;
        this.textColorDayOfMonth = textColorDayOfMonth;
        this.textBackgroundDayOfMonth = textBackgroundDayOfMonth;
        this.isSelectMultiple = isSelectMultiple;
        this.setupConstructor(context, view, currentDate);
    }

    public CalendarCustom(Context context, View view, Date currentDate, boolean isSelectMultiple, boolean isDisableFuture, int textColorDayOfWeek, int textColorDayOfMonth, int textBackgroundDayOfMonth) {
        this.textColorDayOfWeek = textColorDayOfWeek;
        this.textColorDayOfMonth = textColorDayOfMonth;
        this.textBackgroundDayOfMonth = textBackgroundDayOfMonth;
        this.isSelectMultiple = isSelectMultiple;
        this.isDisableFuture = isDisableFuture;
        this.setupConstructor(context, view, currentDate);
    }

    public CalendarCustom(Context context, View view, Date currentDate, boolean isSelectMultiple, List<Date> activeDateList, List<Date> inActiveDateList) {
        this.isSelectMultiple = isSelectMultiple;

        if (this.isSelectMultiple && activeDateList != null) {
            for (Date date : activeDateList) {
                this.activeDateList.add(ConvertDate.convertDateFormat(date));
            }
        }

        if (inActiveDateList != null) {
            for (Date date : inActiveDateList) {
                this.inActiveDateList.add(ConvertDate.convertDateFormat(date));
            }
        }

        this.setupConstructor(context, view, currentDate);
    }

    public CalendarCustom(Context context, View view, Date currentDate, boolean isSelectMultiple, List<Date> activeDateList, List<Date> inActiveDateList, int textColorDayOfWeek, int textColorDayOfMonth, int textBackgroundDayOfMonth) {
        this.isSelectMultiple = isSelectMultiple;

        if (this.isSelectMultiple && activeDateList != null) {
            for (Date date : activeDateList) {
                this.activeDateList.add(ConvertDate.convertDateFormat(date));
            }
        }

        if (inActiveDateList != null) {
            for (Date date : inActiveDateList) {
                this.inActiveDateList.add(ConvertDate.convertDateFormat(date));
            }
        }

        this.textColorDayOfWeek = textColorDayOfWeek;
        this.textColorDayOfMonth = textColorDayOfMonth;
        this.textBackgroundDayOfMonth = textBackgroundDayOfMonth;

        this.setupConstructor(context, view, currentDate);
    }

    private void setupConstructor(Context context, View view, Date currentDate) {
        this.context = context;

        rvCalendar = (RecyclerView) view.findViewById(R.id.recycleviewDayOfMonth);
        rvDayOfWeek = (RecyclerView) view.findViewById(R.id.recycleviewDayOfWeek);
        txtDate = (TextView) view.findViewById(R.id.textviewDate);
        imgNext = (ImageView) view.findViewById(R.id.imageviewNext);
        imgPrevious = (ImageView) view.findViewById(R.id.imageviewPrevious);

        imgNext.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);

        if (currentDate == null) {
            currentDate = new Date();
            noHaveCurrent = true;
        }

        calendar = Calendar.getInstance();
        calendarCurrent = Calendar.getInstance();
        calendarCurrent.setTime(currentDate);
        calendar.setTime(calendarCurrent.getTime());

        txtDate.setText(ConvertDate.convertDateMonthYearShow(calendarCurrent.getTime()));

        setRecycleViewDayOfWeek();
        setDayOfMonthList(calendar);
    }

    public void setRecycleViewCalendar() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false);
        rvCalendar.setLayoutManager(gridLayoutManager);
        if (this.textColorDayOfMonth != 0 || this.textBackgroundDayOfMonth != 0) {
            calendarAdapter = new CalendarAdapter(context, calendarCurrent, calendarArrayList, isSelectMultiple, this.textColorDayOfMonth, this.textBackgroundDayOfMonth);
        } else {
            calendarAdapter = new CalendarAdapter(context, calendarCurrent, calendarArrayList, isSelectMultiple);
        }
        rvCalendar.setAdapter(calendarAdapter);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendarArrayList.get(calendarArrayList.size() - 1).getDate());

        //selected current date
        if (calendar.get(Calendar.MONTH) == calendarCurrent.get(Calendar.MONTH)) {
            txtDate.setText(ConvertDate.convertDateMonthYearShow(calendarCurrent.getTime()));
            if (!noHaveCurrent) {
                calendarAdapter.setSelectedDate(calendarCurrent.get(Calendar.DAY_OF_MONTH));
            }
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            txtDate.setText(ConvertDate.convertDateMonthYearShow(calendar.getTime()));
            //if(!noHaveCurrent)
            //{
            //    calendarAdapter.setSelectedDate(1);
            //}
        }
        setOnDateChangeListener();
    }

    private void setOnDateChangeListener() {
        calendarAdapter.setOnClickListener(new CalendarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CalendarObject dateSelect = calendarArrayList.get(position);
                calendar.setTime(dateSelect.getDate());

                onDateChangeListener.onDateChangeListener(dateSelect);

                if (calendar.get(Calendar.MONTH) == calendarCurrent.get(Calendar.MONTH)) {
                    if (calendar.get(Calendar.DAY_OF_MONTH) == calendarCurrent.get(Calendar.DAY_OF_MONTH)) {
                        txtDate.setText(ConvertDate.convertDateMonthYearShow(calendarArrayList.get(position).getDate()));
                    } else {
                        txtDate.setText(ConvertDate.convertDateMonthYearShow(calendarArrayList.get(position).getDate()));
                    }
                } else {
                    txtDate.setText(ConvertDate.convertDateMonthYearShow(calendarArrayList.get(position).getDate()));
                }
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    private void setDayOfMonthList(Calendar calendarSelected) {
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

                if (isSelectMultiple && activeDateList.size() > 0 && activeDateList.contains(ConvertDate.convertDateFormat(calendar.getTime()))) {
                    calendarObject.setActive(true);
                } else {
                    calendarObject.setActive(false);
                }

                if (inActiveDateList.size() > 0 && inActiveDateList.contains(ConvertDate.convertDateFormat(calendar.getTime()))) {
                    calendarObject.setFree(false);
                } else {
                    calendarObject.setFree(true);
                }

                if (isDisableFuture && ConvertDate.convertDateFormat(calendar.getTime()).getTime() > ConvertDate.convertDateFormat(new Date()).getTime()) {
                    calendarObject.setFree(false);
                }

                calendarObject.setDate(calendar.getTime());
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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 7, LinearLayoutManager.VERTICAL, false);
        rvDayOfWeek.setLayoutManager(gridLayoutManager);
        CalendarDayOfWeekAdapter calendarAdapter;
        if (this.textColorDayOfWeek != 0) {
            calendarAdapter = new CalendarDayOfWeekAdapter(context, dateArraylist, this.textColorDayOfWeek);
        } else {
            calendarAdapter = new CalendarDayOfWeekAdapter(context, dateArraylist);
        }
        rvDayOfWeek.setAdapter(calendarAdapter);
    }

    //dd/MM/yyyy
    public String getSelectedDateString() {
        return calendarAdapter.getSelectedDateString();
    }

    public Date getSelectedDate() {
        return calendarAdapter.getSelectedDate();
    }

    //arrow next button
    public void nextMonth() {
        calendar.add(Calendar.MONTH, 1);
        setDayOfMonthList(calendar);
    }

    //arrow previous button
    public void previousMonth() {
        calendar.add(Calendar.MONTH, -1);
        setDayOfMonthList(calendar);
    }

    public int getCurrentMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public void setCalendarMonth(int month) {
        calendar.set(Calendar.MONTH, month);
        setDayOfMonthList(calendar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageviewPrevious:
                previousMonth();
                break;
            case R.id.imageviewNext:
                if(isDisableFuture) {
                    Calendar calendarCurrent = Calendar.getInstance();
                    if(calendar.get(Calendar.MONTH) ==  calendarCurrent.get(Calendar.MONTH)){
                        break;
                    }
                }
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

    public interface OnDateChangeListener {
        void onDateChangeListener(CalendarObject dateSelect);
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }
}
