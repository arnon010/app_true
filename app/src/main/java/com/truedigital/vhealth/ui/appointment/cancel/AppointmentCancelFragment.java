package com.truedigital.vhealth.ui.appointment.cancel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.MyDialog;

public class AppointmentCancelFragment extends BaseMvpFragment<AppointmentCancelFragmentInterface.Presenter>
        implements AppointmentCancelFragmentInterface.View {

    private ItemAppointmentDao data;
    private TextView tv_name;
    private Button btn_done;
    private TextView tv_appointment_time;
    private EditText edit_reason;
    private boolean isViewOnly;

    public AppointmentCancelFragment() {
        super();
    }

    public static AppointmentCancelFragment newInstance(ItemAppointmentDao data, boolean isViewOnly) {
        AppointmentCancelFragment fragment = new AppointmentCancelFragment();
        fragment.data = data;
        fragment.isViewOnly = isViewOnly;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public AppointmentCancelFragmentInterface.Presenter createPresenter() {
        return AppointmentCancelFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_appointment_cancel;
    }

    @Override
    public void bindView(View view) {
        tv_name = view.findViewById(R.id.tv_name);
        btn_done = view.findViewById(R.id.btn_done);
        tv_appointment_time = view.findViewById(R.id.tv_appointment_time);
        edit_reason = view.findViewById(R.id.edit_reason);
    }

    @Override
    public void setupInstance() {
    }

    @Override
    public void setupView() {
        showToolbar();
        getPresenter().getAppointmentInfo(getAppointmentNumber());
        if (isViewOnly) {
            edit_reason.setEnabled(false);
            btn_done.setText(R.string.appointment_cancel_button_back);
            btn_done.setOnClickListener(v -> onBackPress());
        } else {
            edit_reason.setEnabled(true);
            btn_done.setOnClickListener(v -> openCancelConfirm());
        }
    }

    private void onBackPress() {
        getActivity().onBackPressed();
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.appointment_cancel_title, true);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void openCancelConfirm() {
        MyDialog myDialog = new MyDialog(getActivity());
        myDialog.showCancenConfirm(new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                getPresenter().callCancelAppointment(getAppointmentNumber(), getReason());
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    @Override
    public void showSuccess() {
        final MyDialog myDialog = new MyDialog(getActivity());
        myDialog.showSuccess(getString(R.string.appointment_cancel_success), new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                onSuccess();
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    private void displayDelay(Runnable runnable) {
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onSuccess() {
        ((MainActivity) getActivity()).openAppointmentCancel(data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateView() {
        if (AppManager.getDataManager().isDoctor()) {
            showUserInfo();
        } else {
            showDoctorInfo();
        }
        edit_reason.setText(data.getCancelReason());
        tv_appointment_time.setText(data.getShowShortDate() + " / " + data.getShowShortTime() + " / " + data.getContactMinute() + " " + getString(R.string.time_minute));
    }

    private void showDoctorInfo() {
        tv_name.setText(data.getDoctorName());
    }

    private void showUserInfo() {
        tv_name.setText(data.getPatientUsername());
    }

    @Override
    public void setData(ItemAppointmentDao data) {
        if (data.getAppointmentNumber() == null) return;
        this.data = data;
        updateView();
    }

    @Override
    public String getAppointmentNumber() {
        return data.getAppointmentNumber();
    }

    @Override
    public String getReason() {
        return edit_reason.getText().toString();
    }
}
