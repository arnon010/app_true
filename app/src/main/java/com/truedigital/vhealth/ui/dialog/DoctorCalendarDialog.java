package com.truedigital.vhealth.ui.dialog;

import android.app.Activity;
import android.app.Dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.calendar.CalendarDoctorCustom;

public class DoctorCalendarDialog extends Dialog {


    private Context context;
    private Activity activity;

    private int doctorId;

    private Button btn_credit_card;
    private Button btn_bank_transfer;
    private TextView tv_price;
    private TextView tv_currency;
    private TextView tv_title;
    private TextView tv_payment_channel;

    private DoctorCalendarDialog.OnSlotSelectedListener onSlotSelectedListener;

    public DoctorCalendarDialog(Activity activity, int doctorId) {
        super(activity);
        this.context = context;
        this.doctorId = doctorId;
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {

        final View view = getLayoutInflater().inflate(R.layout.dialog_doctor_calendar, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);
        this.show();
    }

    private void setupView(View view){

        /*
        btn_credit_card = (Button) view.findViewById(R.id.btn_credit_card);
        btn_bank_transfer = (Button) view.findViewById(R.id.btn_bank_transfer);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_currency = (TextView) view.findViewById(R.id.tv_currency);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_payment_channel = (TextView) view.findViewById(R.id.tv_payment_channel);

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(tv_payment_channel);
        tv_price.setText(this.priceAmount);
        tv_currency.setText(this.currency);

        btn_credit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onPaymentListener.onCreditCard();
            }
        });

        btn_bank_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onPaymentListener.onBankTranfer();
            }
        });
        */
        //DoctorCalendarFragment fragment = DoctorCalendarFragment.newInstance(doctorId);
        //replaceFragment(fragment);

        setupCalendar(view);
    }



    private void setupCalendar(View view) {
        final CalendarDoctorCustom calendarCustom = new CalendarDoctorCustom(activity, doctorId);
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

    public interface OnSlotSelectedListener {
        void onSelect();
    }

    public void setOnSlotSelectedListener(OnSlotSelectedListener onSlotSelectedListener) {
        this.onSlotSelectedListener = onSlotSelectedListener;
    }

}
