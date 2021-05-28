package com.truedigital.vhealth.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;

public class ViewAppointmentInfo extends RelativeLayout {

    private TextView tvDate;
    private TextView tvTime;
    private TextView tvAppointmentNumber;
    private TextView tvChanel;
    private ImageView img_channel;

    public ViewAppointmentInfo(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public ViewAppointmentInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public ViewAppointmentInfo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_appointment_info, this);
    }

    private void initInstances() {
        //tvName = (TextView) findViewById(R.id.tvName);
        //ivImage = (ImageView) findViewById(R.id.ivImage);

        tvDate = (TextView) findViewById(R.id.tv_appointment_date);
        tvTime = (TextView) findViewById(R.id.tv_appointment_time);
        tvAppointmentNumber = (TextView) findViewById(R.id.tv_appointment_number);
        tvChanel = (TextView) findViewById(R.id.tv_appointment_channel);
        img_channel = (ImageView) findViewById(R.id.img_channel);
    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setData(String appointmentDate, String appointmentTime, String appointmentNumber, String appointmentChannel, int resDrawable) {
        tvDate.setText(appointmentDate);
        tvTime.setText(appointmentTime);
        tvAppointmentNumber.setText(appointmentNumber);
        tvChanel.setText(appointmentChannel);
        img_channel.setImageResource(resDrawable);
    }

}
