package com.truedigital.vhealth.ui.appointment.create;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.manager.AppointmentDataManager;
import com.truedigital.vhealth.model.ConsultDao;
import com.truedigital.vhealth.model.ItemDoctorContactTypeDao;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ItemDoctorScheduleTimeDao;
import com.truedigital.vhealth.model.ItemPersonDao;
import com.truedigital.vhealth.model.ListPersonDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.ui.adapter.ChannelAdapter;
import com.truedigital.vhealth.ui.adapter.ListDoctorTimeSlotAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.doctor.DoctorCalendarActivity;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.ImageUtils;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.MyDialog;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AppointmentCreateFragment extends BaseMvpFragment<AppointmentCreateFragmentInterface.Presenter>
        implements AppointmentCreateFragmentInterface.View {

    private static final String KEY_DOCTOR = "KEY_DOCTOR";

    private TextView tvName;
    private TextView tvPrice;
    private TextView tvTitleBranch;
    private TextView tvSubTitleBranch;

    private ItemDoctorDao data;
    private Button btnDone;
    private LinearLayout lloThumbnail;
    private ImageView imageProfile;
    private RelativeLayout rloPrice;

    private boolean isSelectTimeSlot;
    private boolean isSelectChannel;
    private List<ItemPersonDao> listData;
    private List<ItemDoctorContactTypeDao> listContact;
    private RecyclerView rv_consult;
    private ChannelAdapter adapter_channel;
    private ArrayList<ConsultDao> listDataConsult;
    private RecyclerView rv_timeslot;
    private ListDoctorTimeSlotAdapter adapter_timeslot;

    private ApiAppointmentRequest appointmentData = new ApiAppointmentRequest();
    private int channelSelected = -1;
    private LinearLayout layout_date_select;
    private TextView tv_appointment_date;
    private LinearLayout layout_notfound;
    private LinearLayout layout_haveslot;

    public AppointmentCreateFragment() {
        super();
    }

    public static AppointmentCreateFragment newInstance(ItemDoctorDao data) {
        AppointmentCreateFragment fragment = new AppointmentCreateFragment();
        Bundle args = new Bundle();
        fragment.data = data;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public AppointmentCreateFragmentInterface.Presenter createPresenter() {
        return AppointmentCreateFragmentPresenter.create();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_DOCTOR, data);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        data = savedInstanceState.getParcelable(KEY_DOCTOR);
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_appointment_create;
    }

    @Override
    public void bindView(View view) {
        lloThumbnail = view.findViewById(R.id.layout_thumbnail);
        rloPrice = view.findViewById(R.id.layout_price);
        imageProfile = view.findViewById(R.id.card_image);
        tvName = view.findViewById(R.id.tv_name);
        tvPrice = view.findViewById(R.id.tv_price);
        tvTitleBranch = view.findViewById(R.id.tv_title);
        tvSubTitleBranch = view.findViewById(R.id.tv_subtitle);

        layout_notfound = view.findViewById(R.id.layout_notfound);
        layout_haveslot = view.findViewById(R.id.layout_haveslot);

        rv_timeslot = view.findViewById(R.id.recycler_view_timeslot);
        rv_consult = view.findViewById(R.id.recycler_view_channel);

        layout_date_select = view.findViewById(R.id.layout_date_select);
        tv_appointment_date = view.findViewById(R.id.tv_appointment_date);
        btnDone = view.findViewById(R.id.btn_done);
    }

    @Override
    public void setupInstance() {
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.appointment_title_create, true);
    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuHomeSelected();
        showToolbar();
        showDoctorInfo();

        setupBranch();
        setupRecycleViewTimeSlot();
        setupRecycleViewChannel();
        updateView();
        loadData();
        setupListener();

        getPresenter().loadData();

        appointmentData.setDoctorId(getDoctorId());
        appointmentData.setPatientId(getPatientId());

        checkEnabled();
    }

    private void showDoctorInfo() {
        lloThumbnail.setVisibility(View.VISIBLE);
        rloPrice.setVisibility(View.GONE);

        ImageUtils.show(getActivity(), imageProfile, data.getProfileImage());
        tvName.setText(data.getName());
        tvPrice.setText(data.getPricePerMinuteFormat());

    }

    private void setupListener() {
        btnDone.setOnClickListener(v -> {
            openAppointmentCreateDetail(data);
        });

        layout_date_select.setOnClickListener(onDateSelectedClickListener());
    }

    private View.OnClickListener onDateSelectedClickListener() {
        return v -> openDoctorCalendar(getDoctorId());
    }

    private void setupRecycleViewTimeSlot() {
        adapter_timeslot = new ListDoctorTimeSlotAdapter(rv_timeslot.getContext());
        rv_timeslot.setAdapter(adapter_timeslot);
        rv_timeslot.setHasFixedSize(true);
        rv_timeslot.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter_timeslot.setOnClickListener((view, position) -> {
            appointmentData.setAppointmentTime(adapter_timeslot.getData(position).getScheduleTime());
            tv_appointment_date.setText(getString(R.string.appointment_date_select));
            layout_date_select.setSelected(false);
            checkEnabled();
        });
    }

    private void setupRecycleViewChannel() {
        if (data.getContactTypes() == null) {
            showMessage(R.string.appointment_channel_notfound);
            return;
        }

        adapter_channel = new ChannelAdapter(rv_consult.getContext());
        rv_consult.setAdapter(adapter_channel);
        rv_consult.setHasFixedSize(true);
        int size = 1;
        if (data.getContactTypes() != null) {
            size = data.getContactTypes().size();
        }
        rv_consult.setLayoutManager(new GridLayoutManager(getActivity(), size));

        adapter_channel.setOnClickListener(new ChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                channelSelected = position;
                adapter_channel.selectedItem(position);
                appointmentData.setTypeCode(adapter_channel.getData(position).getTypeCode());
                checkEnabled();
            }

            @Override
            public void onFavoriteClick(View view, int position) {
            }
        });

        initChannel();
    }

    private void initChannel() {
        listDataConsult = new ArrayList<>();
        listContact = data.getContactTypes();
        for (ItemDoctorContactTypeDao dataContact : listContact) {
            if (dataContact.getTypeCode().equals(ConsultDao.CHANNEL_CHAT)) {
                listDataConsult.add(new ConsultDao(0, dataContact.getTypeCode(), dataContact.getTypeName(), R.drawable.selector_channel_chat, false, data.getPricePerMinuteFormat()));
            }
            if (dataContact.getTypeCode().equals(ConsultDao.CHANNEL_VOICE)) {
                listDataConsult.add(new ConsultDao(1, dataContact.getTypeCode(), dataContact.getTypeName(), R.drawable.selector_channel_voice, false, data.getPricePerMinuteFormat()));
            }
        }
        for (ItemDoctorContactTypeDao dataContact : listContact) {
            if (dataContact.getTypeCode().equals(ConsultDao.CHANNEL_VIDEO)) {
                listDataConsult.add(new ConsultDao(2, dataContact.getTypeCode(), dataContact.getTypeName(), R.drawable.selector_channel_video, false, data.getPricePerMinuteFormat()));
            }
        }

        adapter_channel.setListData(listDataConsult);

        if (channelSelected > -1) {
            adapter_channel.selectedItem(channelSelected);
        }
    }

    public String getDataFromFile(String filename) {
        return new LoadData(getActivity()).loadJSONFromAsset(filename);
    }

    public List<ItemPersonDao> getData(String json, boolean isShowLog) {
        Gson gson = new Gson();
        ListPersonDao data = gson.fromJson(json, ListPersonDao.class);
        return data.getListData();
    }

    private void loadData() {
        String json = getDataFromFile("seed/doctor.json");
        listData = getData(json, false);
    }

    private void updateView() {
        isSelectTimeSlot = true;
        isSelectChannel = true;
        btnDone.setEnabled(isSelectTimeSlot && isSelectChannel);
    }

    private void setupBranch() {
        tvTitleBranch.setText(data.getCategoryName());
        tvSubTitleBranch.setText("N/A");
        StringBuilder strSubtitle = new StringBuilder();
        if (data.getSubCategoryName() != null) {
            for (String msg : data.getSubCategoryName()) {
                if (strSubtitle.toString().equals("")) {
                    strSubtitle = new StringBuilder(msg);
                } else {
                    strSubtitle.append(" , ").append(msg);
                }
            }
            tvSubTitleBranch.setText("" + strSubtitle);
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public void openPersonConsult() {
        MyDialog myDialog = new MyDialog(getActivity());
        myDialog.showPersonConsult(listData, new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                openAppointmentCreateDetail(data);
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    @Override
    public boolean isValid() {
        if (appointmentData.getAppointmentTime() == null || appointmentData.getAppointmentTime().equals("")) {
            showMessage("กรุณาเลือกวันที่พบแพทย์");
            return false;
        }
        if (appointmentData.getTypeCode() == null || appointmentData.getTypeCode().equals("")) {
            showMessage("กรุณาเลือกช่องทางการนัดพบ");
            return false;
        }
        return true;
    }

    @Override
    public void openAppointmentCreateDetail(ItemDoctorDao data) {
        AppointmentDataManager.getInstance().setData(appointmentData);
        if (isValid()) {
            ((MainActivity) getActivity()).openAppointmentCreateDetail(data);
        }
    }

    @Override
    public int getPatientId() {
        return AppManager.getDataManager().getPatientId();
    }

    @Override
    public int getDoctorId() {
        return data.getDoctorId();
    }

    @Override
    public void setDoctorAvailableTimes(List<ItemDoctorScheduleTimeDao> data) {
        adapter_timeslot.setListData(data);
        if (data.size() <= 0) {
            layout_notfound.setVisibility(View.VISIBLE);
            layout_haveslot.setVisibility(View.GONE);
        } else {
            layout_notfound.setVisibility(View.GONE);
            layout_haveslot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTimeSlot(String timeSlot) {
    }

    @Override
    public void setChannel(String channelCode) {
    }

    @Override
    public void openDoctorCalendar(int doctorId) {
        Intent intent = new Intent(getActivity(), DoctorCalendarActivity.class);
        intent.putExtra(AppConstants.EXTRA_DOCTOR_ID, getDoctorId());
        startActivityForResult(intent, AppConstants.REQUEST_TIMESLOT);
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.REQUEST_TIMESLOT && data != null) {
                String strDate = data.getStringExtra(AppConstants.EXTRA_TIMESLOT_DATE);
                String strTime = data.getStringExtra(AppConstants.EXTRA_TIMESLOT_TIME);
                updateSelceDate(strDate, strTime);
            }
        }
    }

    private void updateSelceDate(String strDate, String strTime) {
        String dateSelect = ConvertDate.convertStringShow(strDate);
        tv_appointment_date.setText(dateSelect + " / " + strTime);

        String appointmentTime = ConvertDate.convertStringAppointmentTime(strDate + " " + strTime);
        appointmentData.setAppointmentTime(appointmentTime);
        layout_date_select.setSelected(true);
        adapter_timeslot.clearSelected();
        checkEnabled();
    }

    public boolean isEnable() {
        if (appointmentData.getAppointmentTime() == null || appointmentData.getAppointmentTime().equals("")) {
            return false;
        }
        return appointmentData.getTypeCode() != null && !appointmentData.getTypeCode().equals("");
    }

    private void checkEnabled() {
        btnDone.setEnabled(isEnable());
    }
}
