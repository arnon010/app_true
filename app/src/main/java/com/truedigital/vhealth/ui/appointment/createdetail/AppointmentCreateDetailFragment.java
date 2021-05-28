package com.truedigital.vhealth.ui.appointment.createdetail;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.manager.AppointmentDataManager;
import com.truedigital.vhealth.model.ItemAntiMedicine;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.ui.adapter.ListAntiMedicineAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.setting.info.SettingAppFragment;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.SpannableText;
import com.truedigital.vhealth.utils.ValidUtils;

import java.util.ArrayList;
import java.util.List;

public class AppointmentCreateDetailFragment extends BaseMvpFragment<AppointmentCreateDetailFragmentInterface.Presenter>
        implements AppointmentCreateDetailFragmentInterface.View {

    private ItemDoctorDao data;
    private Button btnDone;
    private ImageView img_condition;
    private boolean isConditionSelect;
    private TextView tv_condition;
    private ApiAppointmentRequest appointmentData;
    private EditText edSymptom;
    private EditText edEmail;
    private EditText edTelephone;
    private RecyclerView recyclerView;
    private ListAntiMedicineAdapter adapter;
    private List<ItemAntiMedicine> listItemAntiMedicine;
    private ImageView imgAdd;

    public AppointmentCreateDetailFragment() {
        super();
    }

    public static AppointmentCreateDetailFragment newInstance(ItemDoctorDao data) {
        AppointmentCreateDetailFragment fragment = new AppointmentCreateDetailFragment();
        Bundle args = new Bundle();
        fragment.data = data;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public AppointmentCreateDetailFragmentInterface.Presenter createPresenter() {
        return AppointmentCreateDetailFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_appointment_create_detail;
    }

    @Override
    public void bindView(View view) {
        tv_condition = view.findViewById(R.id.tv_condition);
        img_condition = view.findViewById(R.id.img_condition);
        edSymptom = view.findViewById(R.id.edSymptom);
        edEmail = view.findViewById(R.id.edEmail);
        edTelephone = view.findViewById(R.id.edTelephone);
        btnDone = view.findViewById(R.id.btn_done);
        recyclerView = view.findViewById(R.id.recycler_view_anti_medicine);
        imgAdd = view.findViewById(R.id.imgAdd);
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
        loadDataAppointment();
        setupRecycleView();

        edEmail.setText(AppManager.getDataManager().getUserEmail());
        edTelephone.setText(AppManager.getDataManager().getPhone());
        btnDone.setEnabled(true);

        updateView();

        img_condition.setOnClickListener(v -> {
            isConditionSelect = !isConditionSelect;
            updateView();
        });

        imgAdd.setOnClickListener(v -> adapter.addItem(new ItemAntiMedicine()));
        btnDone.setOnClickListener(v -> openAppointmentConfirm(data));
        setConditionClickableSpan();
    }

    private void setupRecycleView() {
        adapter = new ListAntiMedicineAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnClickListener(new ListAntiMedicineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemAddClick(View view, int position, String detail) {
                onItemAdd(position + 1, detail);
            }

            @Override
            public void onItemRemoveClick(View view, int position) {
                onItemRemove(position);
            }
        });

        onInitItem();
    }

    private void onInitItem() {
        if (appointmentData.getListItemAntiMedicine() == null) {
            onItemAdd(0, "");
        } else {
            listItemAntiMedicine = new ArrayList<>();
            listItemAntiMedicine = appointmentData.getListItemAntiMedicine();
            adapter.setListData(listItemAntiMedicine);
        }
    }

    private void onItemAdd(int position, String detail) {
        adapter.addItem(new ItemAntiMedicine(0, 0, "", true, true));
    }

    private void updateItem(int position, ItemAntiMedicine data) {
        adapter.updateItem(position, data);
    }

    private void onItemRemove(int position) {
        adapter.removeItem(position);
    }

    private void updateView() {
        img_condition.setSelected(isConditionSelect);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void loadDataAppointment() {
        appointmentData = AppointmentDataManager.getInstance().getData();
    }

    private List<String> getListAntiMedicine() {
        List<String> listMedicineAllergy = new ArrayList<>();
        for (int i = 0; i < adapter.getListData().size(); i++) {
            listMedicineAllergy.add(adapter.getListData().get(i).getDescription());
        }
        return listMedicineAllergy;
    }

    private String SerializedMedicineAllergy(List<String> listMedicineAllergy) {
        return new Gson().toJson(listMedicineAllergy);
    }

    @Override
    public void setDataAppointment() {
        appointmentData.setContactEmail(getEmail());
        appointmentData.setContactTelephone(getTelephone());
        appointmentData.setListItemAntiMedicine(adapter.getListData());
        appointmentData.setListMedicineAllergy(getListAntiMedicine());
        appointmentData.setMedicineAllergy(SerializedMedicineAllergy(appointmentData.getListMedicineAllergy()));
        appointmentData.setAllowEHR(isConditionSelect);
        appointmentData.setDetail(getSymptom());
    }

    @Override
    public String getSymptom() {
        return edSymptom.getText().toString();
    }

    @Override
    public String getEmail() {
        return edEmail.getText().toString();
    }

    @Override
    public String getTelephone() {
        return edTelephone.getText().toString();
    }

    @Override
    public void onErrorEmail() {
        edEmail.setError(getString(R.string.error_invalid_email));
        edEmail.requestFocus();
    }

    @Override
    public void onErrorTelephone() {
        edTelephone.setError(getString(R.string.error_invalid_telephone));
        edTelephone.requestFocus();
    }

    @Override
    public boolean isValid() {
        if (!ValidUtils.isEmailValid(getEmail())) {
            onErrorEmail();
            return false;
        }
        if (!ValidUtils.isTelephoneValid(getTelephone())) {
            onErrorTelephone();
            return false;
        }
        return true;
    }

    @Override
    public void openAppointmentConfirm(ItemDoctorDao data) {
        if (isValid()) {
            setDataAppointment();
            AppointmentDataManager.getInstance().setData(appointmentData);
            ((MainActivity) getActivity()).openAppointmentConfirm(data);
        }
    }

    private void setConditionClickableSpan() {
        tv_condition.setText(SpannableText.setClickableSpan(getContext(),
                R.string.appointment_create_condition,
                R.string.appointment_create_condition_link,
                textView -> {
                    openPopup(R.string.setting_app_term, BuildConfig.SERVER_BASE_INFO + SettingAppFragment.ENDPOINT_TERM);
                }));
        tv_condition.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void openPopup(int resIdTitle, String url) {
        String title = getString(resIdTitle);
        new MyDialog(getContext()).showWebview(title, url, new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
            }

            @Override
            public void onClickCancel() {
            }
        });
    }
}
