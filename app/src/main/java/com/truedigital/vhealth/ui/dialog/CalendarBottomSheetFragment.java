package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.calendar.CalendarCustom;
import com.truedigital.vhealth.model.CalendarObject;

import java.util.Date;


public class CalendarBottomSheetFragment extends BottomSheetDialogFragment {

    private int height;
    private Date currentDate;

    private Context context;
    private CalendarCustom calendarCustom;
    private CalendarBottomSheetFragment.OnDateChangeListener onDateChangeListener;

    public static CalendarBottomSheetFragment newInstance(Context context, int height, Date currentDate) {
        CalendarBottomSheetFragment fragment = new CalendarBottomSheetFragment();
        fragment.height = height;
        fragment.context = context;
        fragment.currentDate = currentDate;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), R.layout.fragment_calendar_bottom_sheet, null);
        this.setupView(inflatedView);

        dialog.setContentView(inflatedView);

        View parent = (View) inflatedView.getParent();
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        bottomSheetBehavior.setPeekHeight(this.height);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        params.height = this.height;
        parent.setLayoutParams(params);
    }

    private void setupView(View view){
        calendarCustom = new CalendarCustom(context, view, currentDate, false, true);
        calendarCustom.setOnDateChangeListener(new CalendarCustom.OnDateChangeListener() {
            @Override
            public void onDateChangeListener(CalendarObject dateSelect) {
                onDateChangeListener.onDateChange(calendarCustom.getSelectedDate());
            }
        });
    }

    public interface OnDateChangeListener {
        void onDateChange(Date date);
    }

    public void setOnDateChangeListener(CalendarBottomSheetFragment.OnDateChangeListener OnDateChangeListener) {
        this.onDateChangeListener = OnDateChangeListener;
    }

}
