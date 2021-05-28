package com.truedigital.vhealth.ui.ehr.healthinformation;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.CongenitalDiseaseImageObject;
import com.truedigital.vhealth.model.CongenitalDiseaseObject;
import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.ui.adapter.ListAdapter;
import com.truedigital.vhealth.ui.adapter.ListHealthInformationMenuAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.decoration.ListEhrMenuAdapterDecoration;
import com.truedigital.vhealth.ui.dialog.ListDialog;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;

import java.util.ArrayList;

public class HealthInformationMenuFragment extends BaseMvpFragment<HealthInformationMenuFragmentInterface.Presenter>
        implements HealthInformationMenuFragmentInterface.View {

    private int patientId;
    private boolean isChild;
    private String titleToolbar;

    private RecyclerView recyclerHealthInformationMenu;

    private ListHealthInformationMenuAdapter menuAdapter;
    private ListDialog ehrDialog;

    public HealthInformationMenuFragment() {
        super();
    }

    public static HealthInformationMenuFragment newInstance(int patientId, boolean isChild, String titleToolbar) {
        HealthInformationMenuFragment fragment = new HealthInformationMenuFragment();
        fragment.patientId = patientId;
        fragment.isChild = isChild;
        fragment.titleToolbar = titleToolbar;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public HealthInformationMenuFragmentInterface.Presenter createPresenter() {
        return HealthInformationMenuFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_health_information_menu;
    }

    @Override
    public void bindView(View view) {
        recyclerHealthInformationMenu = (RecyclerView) view.findViewById(R.id.recyclerHealthInformationMenu);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();
    }

    @Override
    public void initialize() {
        setAdapter();
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(titleToolbar, false,null, false);
        getPresenter().getHealthInformationMenu(this.patientId, this.isChild);
    }

    @Override
    public void restoreView(Bundle savedInstanceState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void setAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        recyclerHealthInformationMenu.setLayoutManager(gridLayoutManager);
        recyclerHealthInformationMenu.addItemDecoration(new ListEhrMenuAdapterDecoration(getActivity(), 2, (int) CommonUtils.convertDpToPixel(getActivity(), 3), R.drawable.line_divider_green));

        menuAdapter = new ListHealthInformationMenuAdapter(getActivity(), new ArrayList<EhrMenuObject>());
        menuAdapter.setOnClickListener(this.menuListOnClickListener());
        recyclerHealthInformationMenu.setAdapter(menuAdapter);
    }

    @Override
    public void setHealthInformationMenu(ArrayList<EhrMenuObject> dataMenuList) {
        menuAdapter.setListData(dataMenuList);
    }

    @Override
    public void setHealthInformationMenuSelectList(ArrayList<EhrMenuObject> listData) {

        ListAdapter adapter = new ListAdapter(getActivity());
        adapter.setOnClickListener(menuSelectOnClickListener());

        adapter.setListData(listData);

        ehrDialog = new ListDialog(getActivity(), adapter);
        ehrDialog.showDialog();
    }


    private ListHealthInformationMenuAdapter.OnItemClickListener menuListOnClickListener() {

        return new ListHealthInformationMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EhrMenuObject data) {
                if (data.getMenuCode().equals(AppConstants.EHR_MENU_EMPTY)) {

                    getPresenter().getHealthInformationMenuSelectList(patientId, isChild);

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_HEART_BEAT_RATE)) {

                    ((MainActivity) getActivity()).openHeartBeatRate(patientId, data.getPatientMenuId(), data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_BLOOD_PRESSURE)) {

                    ((MainActivity) getActivity()).openBloodPressure(patientId, data.getPatientMenuId(), data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_BLOOD_SUGAR)) {

                    ((MainActivity) getActivity()).openBloodSugar(patientId, data.getPatientMenuId(), data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_MENSTRUAL_PERIOD)) {

                    ((MainActivity) getActivity()).openMenstrualPeriod(patientId, data.getPatientMenuId(), data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_MEDICAL_HISTORY)) {

                    ((MainActivity) getActivity()).openCongenitalDisease(patientId, data.getPatientMenuId());

                }
            }
        };
    }

    private ListAdapter.OnItemClickListener menuSelectOnClickListener() {

        return new ListAdapter.OnItemClickListener<EhrMenuObject>() {
            @Override
            public void onItemClick(EhrMenuObject data) {
                ehrDialog.dismiss();

                if (data.getMenuCode().equals(AppConstants.EHR_MENU_HEART_BEAT_RATE)) {

                    ((MainActivity) getActivity()).openHeartBeatRate(patientId, 0, data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_BLOOD_PRESSURE)) {

                    ((MainActivity) getActivity()).openBloodPressure(patientId, 0, data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_BLOOD_SUGAR)) {

                    ((MainActivity) getActivity()).openBloodSugar(patientId, 0, data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_MENSTRUAL_PERIOD)) {

                    ((MainActivity) getActivity()).openMenstrualPeriod(patientId, 0, data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_MEDICAL_HISTORY)) {

                    CongenitalDiseaseObject obj = new CongenitalDiseaseObject();
                    obj.setPatientId(patientId);
                    obj.setMenuCode(data.getMenuCode());
                    obj.setAttachmentList(new ArrayList<CongenitalDiseaseImageObject>());
                    ((MainActivity) getActivity()).openNewFormCongenitalDisease(obj, true);

                }

            }
        };
    }


}
