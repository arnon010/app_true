package com.truedigital.vhealth.ui.appointment.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.view.ViewAppointmentInfo;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.AppointmentUtil;

public class AppointmentDetailFragment extends BaseMvpFragment<AppointmentDetailFragmentInterface.Presenter>
        implements AppointmentDetailFragmentInterface.View {

    private ItemAppointmentDao data;
    private TextView tv_name;
    private Button btn_next;
    private Button btn_opd_card;
    private ImageView imageEhr;
    private Button btn_cancel;
    private ImageView imgProfile;
    private ViewAppointmentInfo viewAppointmentInfo;
    private int appointmentType;
    private TextView tv_title;
    private TextView tv_subtitle;
    private RelativeLayout rloPrice;
    private LinearLayout layout_doctor;
    private Button btn_send_status;
    private LinearLayout layout_appointment;
    private ProgressBar progress_bar;


    public AppointmentDetailFragment() {
        super();
    }

    public static AppointmentDetailFragment newInstance(int appointmentType, String appointmentNumber) {
        AppointmentDetailFragment fragment = new AppointmentDetailFragment();
        Bundle args = new Bundle();
        args.putInt(AppConstants.EXTRA_APPOINTMENT_TYPE, appointmentType);
        args.putString(AppConstants.EXTRA_APPOINTMENT_NUMBER, appointmentNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public AppointmentDetailFragmentInterface.Presenter createPresenter() {
        return AppointmentDetailFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_appointment_detail;
    }

    @Override
    public void bindView(View view) {
        layout_appointment = view.findViewById(R.id.layout_appointment);
        progress_bar = view.findViewById(R.id.progress_bar);

        layout_doctor = view.findViewById(R.id.layout_doctor);
        imgProfile = view.findViewById(R.id.card_image);
        tv_name = view.findViewById(R.id.tv_name);
        tv_title = view.findViewById(R.id.tv_title);
        tv_subtitle = view.findViewById(R.id.tv_subtitle);
        rloPrice = view.findViewById(R.id.layout_price);
        rloPrice.setVisibility(View.GONE);

        imageEhr = view.findViewById(R.id.imageEhr);
        btn_next = view.findViewById(R.id.btn_next);
        btn_opd_card = view.findViewById(R.id.btn_opd_card);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_send_status = view.findViewById(R.id.btn_send_status);
        viewAppointmentInfo = view.findViewById(R.id.view_appoint_info);
    }

    @Override
    public void setupInstance() {
        layout_appointment.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuAppointmentSelected();

        appointmentType = getAppointmentType();
        imageEhr.setOnClickListener(v -> showMessage("Open EHR"));

        btn_next.setOnClickListener(v -> openTarget());

        btn_cancel.setOnClickListener(v -> openAppointmentCancel(data));
        btn_send_status.setOnClickListener(v -> openShippingStatus());

        getPresenter().getAppointmentInfo(getAppointmentNumber());
    }

    private void openTarget() {
        switch (appointmentType) {
            case AppointmentUtil.APPOINTMENT_INCOMING:
                openRoom();
                break;
            case AppointmentUtil.APPOINTMENT_HISTORY:
                openDoctorNoteConfirm();
                break;
            case AppointmentUtil.APPOINTMENT_CANCEL:
                openAppointmentCancel(data);
        }
    }

    @Override
    public void updateView() {
        if (AppManager.getDataManager().isDoctor()) {
            showUserInfo();
        } else {
            showDoctorInfo();
        }

        showAppointmentInfo();
        showAppointmentButton();
    }

    private void showDoctorInfo() {
        tv_name.setText(data.getDoctorName());
        tv_title.setText(data.getCategoryFullName());
        tv_subtitle.setText(data.getSubCategoryFullName());
        Glide.with(getActivity())
                .load(data.getDoctorProfileImage()).asBitmap()
                .placeholder(R.drawable.ic_profile_user)
                .into(imgProfile);

        layout_doctor.setVisibility(View.VISIBLE);
    }

    private void showUserInfo() {
        tv_name.setText(data.getPatientUsername());
        tv_title.setText("");
        tv_subtitle.setText("");
        Glide.with(getActivity())
                .load(data.getPatientProfileImage()).asBitmap()
                .placeholder(R.drawable.ic_profile_user)
                .into(imgProfile);
        layout_doctor.setVisibility(View.GONE);
    }

    private void showAppointmentInfo() {
        viewAppointmentInfo.setData(
                data.getShowShortDate(),
                data.getShowShortTime(),
                data.getAppointmentNumber(),
                data.getContactTypeName(),
                data.getShowChanelIcon()
        );
    }

    private void hideAppointmentButton() {
        btn_next.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        btn_opd_card.setVisibility(View.GONE);
    }

    private void showAppointmentButton() {
        hideAppointmentButton();
        int position = 0;
        if (data.isCanCancel() || data.isCanStart()) {
            position = 0;
            appointmentType = AppointmentUtil.APPOINTMENT_INCOMING;
            if (data.isCanStart()) {
                btn_next.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.GONE);
            } else {
                btn_next.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.VISIBLE);
                setUnderLine(btn_cancel);
            }

        } else {
            position = 1;
            appointmentType = AppointmentUtil.APPOINTMENT_HISTORY;

            btn_next.setEnabled(false);
            if (data.isSentShortNote()) {
                btn_next.setEnabled(true);
            }
            btn_next.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.GONE);

            if (AppManager.getDataManager().isDoctor()) {
                btn_opd_card.setVisibility(View.VISIBLE);
            } else {
                btn_opd_card.setVisibility(View.GONE);
            }
        }
        if (data.isCanceled()) {
            position = 2;
            appointmentType = AppointmentUtil.APPOINTMENT_CANCEL;
            btn_next.setEnabled(true);
            if (data.getCancelUserId() == AppManager.getDataManager().getUserId()) {
                btn_next.setVisibility(View.VISIBLE);
            } else {
                btn_next.setVisibility(View.GONE);
            }

            btn_cancel.setVisibility(View.GONE);
        }
        if (data.isSold()) {
            btn_next.setText("" + getString(AppointmentUtil.getButtonTitle(AppManager.getDataManager().isDoctor(), position, true)));
            btn_send_status.setVisibility(View.VISIBLE);
        } else {
            btn_next.setText("" + getString(AppointmentUtil.getButtonTitle(AppManager.getDataManager().isDoctor(), position, false)));
            btn_send_status.setVisibility(View.GONE);
        }

        //..refresh toolbar title again
        showToolbar();
        layout_appointment.setVisibility(View.VISIBLE);
        progress_bar.setVisibility(View.GONE);
    }

    private void setUnderLine(Button button) {
        button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private int getTitleToolbar() {
        int[] resId = {R.string.appointment_tag_new, R.string.appointment_tag_history, R.string.appointment_tag_cancel};
        int position = 0;
        switch (appointmentType) {
            case AppointmentUtil.APPOINTMENT_INCOMING:
                position = 0;
                break;
            case AppointmentUtil.APPOINTMENT_HISTORY:
                position = 1;
                break;
            case AppointmentUtil.APPOINTMENT_CANCEL:
                position = 2;
        }
        return resId[position];
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(getTitleToolbar(), true);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void openAppointmentCancel(ItemAppointmentDao data) {
        if (appointmentType == AppointmentUtil.APPOINTMENT_INCOMING) {
            ((MainActivity) getActivity()).openAppointmentCancel(data, false);
        } else if (appointmentType == AppointmentUtil.APPOINTMENT_CANCEL) {
            ((MainActivity) getActivity()).openAppointmentCancel(data, true);
        }
    }

    @Override
    public void setData(ItemAppointmentDao data) {
        if (data.getAppointmentNumber() == null) return;
        this.data = data;
        updateView();
    }

    @Override
    public int getAppointmentType() {
        return getArguments().getInt(AppConstants.EXTRA_APPOINTMENT_TYPE);
    }

    @Override
    public String getAppointmentNumber() {
        return getArguments().getString(AppConstants.EXTRA_APPOINTMENT_NUMBER);
    }

    @Override
    public String getAppointmentContactType() {
        return data.getContactTypeName();
    }

    @Override
    public void openRoom() {
        if (data == null) return;
        ((MainActivity) getActivity()).openRoom(getAppointmentNumber(), getAppointmentContactType());
    }

    @Override
    public void openDoctorNoteConfirm() {
        boolean isViewOnly = true;
        ((MainActivity) getActivity()).openDoctorNoteConfirm(data, isViewOnly);
    }

    private void openShippingStatus() {
        ((MainActivity) getActivity()).openShippingStatus(getAppointmentNumber(), getAppointmentNumber(), "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AppConstants.REQUEST_CODE_ROOM == requestCode && resultCode == Activity.RESULT_OK) {

            String appointmentNumber = data.getStringExtra(AppConstants.EXTRA_APPOINTMENT_NUMBER);
            String appointmentStatus = data.getStringExtra(AppConstants.EXTRA_APPOINTMENT_STATUS);
            if (appointmentStatus.equals(AppConstants.APPOINTMENT_STATUS_STOPCALL)) {
                reset_callingBar();
                openAppointmentHistory(appointmentNumber);
            }
        }
    }

    private void reset_callingBar() {
        AppManager.getDataManager().setTimeToCall(false);
        ((MainActivity) getActivity()).hideCallingBar();
    }

    private void openAppointmentHistory(String appointmentNumber) {
        ((MainActivity) getActivity()).openAppointmentHistory(appointmentNumber);
    }
}
