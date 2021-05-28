package com.truedigital.vhealth.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.truedigital.vhealth.R;

import java.lang.reflect.Field;

public class CustomTimePicker extends TimePicker {

    private int textColor = R.color.color_white;
    private int dividerColour = R.color.color_white;
    private boolean is24HourView = true;

    public CustomTimePicker(Context context) {
        super(context);
        renderView();
    }

    public CustomTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.renderView();
    }

    public CustomTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.renderView();
    }

    public void setupView(int textColor, int dividerColour, boolean is24HourView) {
        this.textColor = textColor;
        this.dividerColour = dividerColour;
        this.is24HourView = is24HourView;
        this.renderView();
    }

    public void setTime(int hour, int minute)
    {
        if (Build.VERSION.SDK_INT >= 23) {
            this.setHour(hour);
            this.setMinute(minute);
        }
        else
        {
            this.setCurrentHour(hour);
            this.setCurrentMinute(minute);
        }
    }

    private void renderView()
    {
        try {
            this.setIs24HourView(this.is24HourView);

            Resources system = Resources.getSystem();
            int hourNumberPickerId = system.getIdentifier("hour", "id", "android");
            int minuteNumberPickerId = system.getIdentifier("minute", "id", "android");
            int ampmNumberPickerId = system.getIdentifier("amPm", "id", "android");
            int colonId = system.getIdentifier("divider", "id", "android");

            NumberPicker hourNumberPicker = this.findViewById(hourNumberPickerId);
            NumberPicker minuteNumberPicker = this.findViewById(minuteNumberPickerId);
            NumberPicker ampmNumberPicker = this.findViewById(ampmNumberPickerId);
            TextView colonText = this.findViewById(colonId);
            colonText.setTextColor(getResources().getColor(this.textColor));

            setNumberPickerDividerColour(hourNumberPicker);
            setNumberPickerDividerColour(minuteNumberPicker);
            setNumberPickerDividerColour(ampmNumberPicker);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNumberPickerDividerColour(NumberPicker number_picker){
        final int count = number_picker.getChildCount();
        final int color = getResources().getColor(this.textColor);

        for(int i = 0; i < count; i++){

            View child = number_picker.getChildAt(i);

            try{
                Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
                wheelpaint_field.setAccessible(true);
                ((Paint)wheelpaint_field.get(number_picker)).setColor(color);
                ((EditText)child).setTextColor(color);

                Field dividerField = number_picker.getClass().getDeclaredField("mSelectionDivider");
                dividerField.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(getContext().getResources().getColor(this.dividerColour));
                dividerField.set(number_picker, colorDrawable);

                number_picker.invalidate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
