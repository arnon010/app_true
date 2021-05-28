package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TimePicker;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.utils.CustomTimePicker;

public class TimePickerBottomSheetFragment extends BottomSheetDialogFragment {

    private Context context;
    private int height;
    private int hour = -1;
    private int minute = -1;
    private CustomTimePicker timepicker;
    private TimePickerBottomSheetFragment.OnChangeListener onChangeListener;

    public static TimePickerBottomSheetFragment newInstance(Context context, int height) {
        TimePickerBottomSheetFragment fragment = new TimePickerBottomSheetFragment();
        fragment.context = context;
        fragment.height = height;

        return fragment;
    }

    public static TimePickerBottomSheetFragment newInstance(Context context, int height, int hour, int minute) {
        TimePickerBottomSheetFragment fragment = new TimePickerBottomSheetFragment();
        fragment.context = context;
        fragment.height = height;
        fragment.hour = hour;
        fragment.minute = minute;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), R.layout.fragment_time_picker_bottom_sheet, null);
        this.setupView(inflatedView);

        dialog.setContentView(inflatedView);

        View parent = (View) inflatedView.getParent();
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        bottomSheetBehavior.setPeekHeight(this.height);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        params.height = this.height;
        parent.setLayoutParams(params);
    }

    private void setupView(View view) {
        timepicker = view.findViewById(R.id.timepicker);

        if (this.hour != -1 && this.minute != -1) {
            timepicker.setTime(this.hour, this.minute);
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                onChangeListener.onTimeChange(timepicker.getHour(), timepicker.getMinute());
            } else {
                onChangeListener.onTimeChange(timepicker.getCurrentHour(), timepicker.getCurrentMinute());
            }
        }

        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                onChangeListener.onTimeChange(hourOfDay, minute);
            }
        });
    }

    public interface OnChangeListener {
        void onTimeChange(int hourOfDay, int minute);
    }

    public void setOnChangeListener(TimePickerBottomSheetFragment.OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }


}
