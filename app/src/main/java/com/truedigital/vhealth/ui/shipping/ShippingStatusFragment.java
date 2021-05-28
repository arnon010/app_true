package com.truedigital.vhealth.ui.shipping;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;

public class ShippingStatusFragment extends BaseMvpFragment<ShippingStatusFragmentInterface.Presenter>
        implements ShippingStatusFragmentInterface.View{


    private String invoiceNo;
    private String status;
    String appointmentNumber;

    private Button btnDone;
    private TextView tvInvoiceNo;
    private TextView tvStatus;

    public ShippingStatusFragment() {
        super();
    }

    public static ShippingStatusFragment newInstance(String appointmentNumber, String invoiceNo, String status) {
        ShippingStatusFragment fragment = new ShippingStatusFragment();

        Bundle args = new Bundle();
        fragment.invoiceNo = invoiceNo;
        fragment.status = status;
        fragment.appointmentNumber = appointmentNumber;

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public ShippingStatusFragmentInterface.Presenter createPresenter(){
        return ShippingStatusFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_shipping_status;
    }

    @Override
    public void bindView( View view ){
        tvInvoiceNo = (TextView) view.findViewById(R.id.tvInvoiceNo);
        tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        btnDone = (Button) view.findViewById(R.id.btn_done);
    }

    @Override
    public void setupInstance(){

    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.shipping_title_bar,true);
    }

    @Override
    public void setupView(){
        showToolbar();
        setupListener();
        tvInvoiceNo.setText(invoiceNo);
        tvStatus.setText(status);
        getPresenter().getShippingStatus(getAppointmentNumber());

    }

    private void setupListener() {
        btnDone.setOnClickListener(onDoneButtonClick());
    }

    private View.OnClickListener onDoneButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        };
    }

    @Override
    public void initialize(){

    }

    @Override
    public void updateStatus(String status) {
        tvStatus.setText(status);
    }

    private String getAppointmentNumber() {
        return appointmentNumber;
    }
}

