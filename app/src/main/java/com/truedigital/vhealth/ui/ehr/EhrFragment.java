package com.truedigital.vhealth.ui.ehr;


import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ChildGrowthCriteriaObject;
import com.truedigital.vhealth.model.EhrMenuObject;
import com.truedigital.vhealth.model.FoodAllergyImageObject;
import com.truedigital.vhealth.model.FoodAllergyObject;
import com.truedigital.vhealth.model.LaboratoryOtherImageObject;
import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.model.MedicationHistoryImageObject;
import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.model.MedicineAllergyImageObject;
import com.truedigital.vhealth.model.MedicineAllergyObject;
import com.truedigital.vhealth.model.PatientObject;
import com.truedigital.vhealth.model.PatientRelationshipObject;
import com.truedigital.vhealth.model.PregnantHistoryImageObject;
import com.truedigital.vhealth.model.PregnantHistoryObject;
import com.truedigital.vhealth.ui.adapter.ListAdapter;
import com.truedigital.vhealth.ui.adapter.ListFamilyMenuAdapter;
import com.truedigital.vhealth.ui.adapter.ListHealthRecordMenuAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.decoration.ListEhrMenuAdapterDecoration;
import com.truedigital.vhealth.ui.dialog.InputDialog;
import com.truedigital.vhealth.ui.dialog.ListDialog;
import com.truedigital.vhealth.ui.dialog.ListGridLayoutDialog;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.GlideConfig;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.ArrayList;

public class EhrFragment extends BaseMvpFragment<EhrFragmentInterface.Presenter>
        implements EhrFragmentInterface.View {

    private int patientId;
    private boolean isChild;
    private String titleToolbar;

    private RecyclerView rvHrMenu;
    private LinearLayout linInfoMain;
    private LinearLayout linInfo;
    private ImageView imgInfo;
    private TextView txtInfo;

    private ListHealthRecordMenuAdapter hrMenuAdapter;
    private ListDialog ehrDialog;
    private ListGridLayoutDialog familyDialog;
    private InputDialog newFamilyDialog;
    private InputDialog newPregnantDialog;

    public EhrFragment() {
        super();
    }

    public static EhrFragment newInstance() {
        EhrFragment fragment = new EhrFragment();
        fragment.patientId = AppManager.getDataManager().getPatientId();
        fragment.isChild = false;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static EhrFragment newInstance(int relationshipPatientId) {
        EhrFragment fragment = new EhrFragment();
        fragment.patientId = relationshipPatientId;
        fragment.isChild = true;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public EhrFragmentInterface.Presenter createPresenter() {
        return EhrFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_ehr;
    }

    @Override
    public void bindView(View view) {
        rvHrMenu = view.findViewById(R.id.recycler_hr_menu);
        linInfoMain = view.findViewById(R.id.linInfoMain);
        linInfo = view.findViewById(R.id.linInfo);
        imgInfo = view.findViewById(R.id.imgInfo);
        txtInfo = view.findViewById(R.id.txtInfo);
    }

    @Override
    public void setupInstance() {
        setAdapter();
    }

    @Override
    public void setupView() {
    }

    @Override
    public void initialize() {
        try {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.setShowBackButton(this.isChild);
            mainActivity.setSwipeRefreshLayout(false);
            mainActivity.showToolbarMain(true);
        } catch (Exception e) {
        }

        /*
        if (this.titleToolbar == null || this.titleToolbar.equals("")) {
            getPresenter().getPatient(patientId);
        } else {
            this.setsetNameToolbar();
        }
        getPresenter().getHealthRecordMenu(patientId, this.isChild);
        */

        if (getPresenter() != null) {
            checkToken();
        }
    }

    private void updateView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        if (this.titleToolbar == null || this.titleToolbar.equals("")) {
            getPresenter().getPatient(patientId);
        } else {
            this.setsetNameToolbar();
        }
        getPresenter().getHealthRecordMenu(patientId, this.isChild);
    }

    private void checkToken() {
        Log.e("Ehr", "check token");
        String token = AppManager.getDataManager().getAccess_token();
        getPresenter().onCheckToken(token, new BaseMvpPresenter.OnTokenListener() {
            @Override
            public void onSuccess() {
                updateView();
            }

            @Override
            public void onFail() {

            }
        });
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
        rvHrMenu.setLayoutManager(gridLayoutManager);
        rvHrMenu.addItemDecoration(new ListEhrMenuAdapterDecoration(getActivity(), 2, (int) CommonUtils.convertDpToPixel(getActivity(), 3), R.drawable.line_divider_green));

        hrMenuAdapter = new ListHealthRecordMenuAdapter(getActivity(), new ArrayList<EhrMenuObject>());
        hrMenuAdapter.setOnClickListener(this.menuListOnClickListener());
        rvHrMenu.setAdapter(hrMenuAdapter);
    }

    @Override
    public void setPatient(PatientObject data) {
        titleToolbar = data.getUsername();
        this.setsetNameToolbar();
    }

    @Override
    public void setHealthRecordMenu(ArrayList<EhrMenuObject> dataMenuList) {

        EhrMenuObject info = new EhrMenuObject();
        for (EhrMenuObject menu : dataMenuList) {
            if (menu.getMenuCode().equals(AppConstants.EHR_MENU_INFORMATION)) {
                info = menu;
                txtInfo.setText(menu.getDisplayName());
                linInfo.setOnClickListener(this.infoOnClickListener());
                Glide.with(getContext())
                        .load(GlideConfig.getGlideHeader(menu.getImageUrl())).asBitmap()
                        .error(R.drawable.img_default)
                        .placeholder(R.drawable.img_default)
                        .into(imgInfo);

                linInfoMain.setVisibility(View.VISIBLE);
                break;
            }
        }

        dataMenuList.remove(info);
        hrMenuAdapter.setListData(dataMenuList);
    }

    @Override
    public void setHealthRecordMenuSelectList(ArrayList<EhrMenuObject> listData) {

        ListAdapter adapter = new ListAdapter(getActivity());
        adapter.setOnClickListener(menuSelectOnClickListener());
        adapter.setListData(listData);

        ehrDialog = new ListDialog(getActivity(), adapter);
        ehrDialog.showDialog();
    }

    @Override
    public void setFamilySelectList(ArrayList<PatientRelationshipObject> listData) {

        ListFamilyMenuAdapter adapterFamily = new ListFamilyMenuAdapter(getActivity());
        adapterFamily.setOnClickListener(familySelectOnClickListener());
        adapterFamily.setListData(listData);

        familyDialog = new ListGridLayoutDialog(getActivity(), getResources().getString(R.string.dialog_family_member_title), getResources().getString(R.string.dialog_family_member_desc), adapterFamily);
        familyDialog.showDialog();
    }

    private void setsetNameToolbar() {
        ((MainActivity) getActivity()).updateToolbar(titleToolbar, false, null, false);
    }

    private ListHealthRecordMenuAdapter.OnItemClickListener menuListOnClickListener() {

        return new ListHealthRecordMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(EhrMenuObject data) {
                if (data.getMenuCode().equals(AppConstants.EHR_MENU_EMPTY)) {

                    getPresenter().getHealthRecordMenuSelectList(patientId, isChild);

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_DOCTOR_NOTE)) {

                    ((MainActivity) getActivity()).openDoctorNote(patientId, data.getPatientMenuId());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_MEDICINE_ALLERGY)) {

                    ((MainActivity) getActivity()).openMedicineAllergy(patientId, data.getPatientMenuId());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_FOOD_ALLERGY)) {

                    ((MainActivity) getActivity()).openFoodAllergy(patientId, data.getPatientMenuId());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_PREGNANT_HISTORY)) {

                    ((MainActivity) getActivity()).openPregnantHistory(patientId, data.getPatientMenuId());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_MEDICATION_HISTORY)) {

                    ((MainActivity) getActivity()).openMedicationHistory(patientId, data.getPatientMenuId());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_VACINATION_HISTORY)) {

                    ((MainActivity) getActivity()).openVaccinationHistory(patientId, data.getPatientMenuId(), data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_LABORATORY)) {

                    ((MainActivity) getActivity()).openLaboratory(patientId, data.getPatientMenuId(), 0);

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_FAMILY)) {

                    getPresenter().getFamilySelectList();

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_CHILD_GROWTH_HISTORY)) {

                    ChildGrowthCriteriaObject criteria = new ChildGrowthCriteriaObject();
                    criteria.setPatientId(patientId);
                    criteria.setPatientMenuId(data.getPatientMenuId());
                    criteria.setMenuCode(data.getMenuCode());
                    ((MainActivity) getActivity()).openChildGrowth(criteria);
                }
            }
        };
    }

    private View.OnClickListener infoOnClickListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity) getActivity()).openHealthInformationMenu(patientId, isChild, titleToolbar);
            }
        };
    }

    private ListAdapter.OnItemClickListener menuSelectOnClickListener() {

        return new ListAdapter.OnItemClickListener<EhrMenuObject>() {
            @Override
            public void onItemClick(final EhrMenuObject data) {
                ehrDialog.dismiss();

                if (data.getMenuCode().equals(AppConstants.EHR_MENU_MEDICINE_ALLERGY)) {

                    MedicineAllergyObject obj = new MedicineAllergyObject();
                    obj.setPatientId(patientId);
                    obj.setMenuCode(data.getMenuCode());
                    obj.setAttachmentList(new ArrayList<MedicineAllergyImageObject>());
                    ((MainActivity) getActivity()).openNewFormMedicineAllergy(obj, true);

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_FOOD_ALLERGY)) {

                    FoodAllergyObject obj = new FoodAllergyObject();
                    obj.setPatientId(patientId);
                    obj.setMenuCode(data.getMenuCode());
                    obj.setAttachmentList(new ArrayList<FoodAllergyImageObject>());
                    ((MainActivity) getActivity()).openNewFormFoodAllergy(obj, true);

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_PREGNANT_HISTORY)) {

                    newPregnantDialog = new InputDialog(getActivity(), getResources().getString(R.string.new_pregnant_history), getResources().getString(R.string.new_pregnant_history_default_name));
                    newPregnantDialog.showDialog();
                    newPregnantDialog.setOnConfirmListener(new InputDialog.OnConfirmListener() {
                        @Override
                        public void onConfirm(String name) {
                            getPresenter().checkPatientEHRMenu(patientId, data.getMenuCode(), name);
                        }
                    });

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_MEDICATION_HISTORY)) {

                    MedicationHistoryObject obj = new MedicationHistoryObject();
                    obj.setPatientId(patientId);
                    obj.setMenuCode(data.getMenuCode());
                    obj.setAttachmentList(new ArrayList<MedicationHistoryImageObject>());
                    ((MainActivity) getActivity()).openNewFormMedicationHistory(obj, true);

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_VACINATION_HISTORY)) {

                    ((MainActivity) getActivity()).openVaccinationHistory(patientId, 0, data.getMenuCode());

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_LABORATORY)) {

                    LaboratoryOtherObject obj = new LaboratoryOtherObject();
                    obj.setPatientId(patientId);
                    obj.setMenuCode(data.getMenuCode());
                    obj.setAttachmentList(new ArrayList<LaboratoryOtherImageObject>());
                    ((MainActivity) getActivity()).openNewFormLaboratoryOther(obj, true);

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_FAMILY)) {

                    createFamily();

                } else if (data.getMenuCode().equals(AppConstants.EHR_MENU_CHILD_GROWTH_HISTORY)) {

                    ChildGrowthCriteriaObject criteria = new ChildGrowthCriteriaObject();
                    criteria.setPatientId(patientId);
                    criteria.setPatientMenuId(0);
                    criteria.setMenuCode(data.getMenuCode());
                    ((MainActivity) getActivity()).openChildGrowth(criteria);

                }
            }
        };
    }

    @Override
    public void checkPatientEHRMenuSuccess(String menuCode, String menuName) {
        if (menuCode.equals(AppConstants.EHR_MENU_PREGNANT_HISTORY)) {
            newPregnantDialog.dismiss();

            PregnantHistoryObject obj = new PregnantHistoryObject();
            obj.setPatientId(patientId);
            obj.setMenuCode(menuCode);
            obj.setPatientMenuName(menuName);
            obj.setAttachmentList(new ArrayList<PregnantHistoryImageObject>());
            ((MainActivity) getActivity()).openNewFormPregnantHistory(obj, true);
        }
    }

    @Override
    public void navigateToLoginEhr() {
        ((MainActivity) getActivity()).navigateToLoginEhr();
    }

    private ListFamilyMenuAdapter.OnItemClickListener familySelectOnClickListener() {
        return new ListFamilyMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PatientRelationshipObject data) {

                if (data.getRelationshipId() == 0) {
                    createFamily();
                } else {
                    familyDialog.dismiss();
                    ((MainActivity) getActivity()).openRelationshipMenu(data.getRelationshipPatientId());
                }
            }
        };
    }

    private void createFamily() {
        newFamilyDialog = new InputDialog(getActivity(), StringUtils.setNewLine(getResources().getString(R.string.dialog_new_family_title)));
        newFamilyDialog.showDialog();
        newFamilyDialog.setOnConfirmListener(new InputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String data) {
                getPresenter().createFamily(data);
            }
        });
    }

    @Override
    public void openRelationshipMenu(PatientRelationshipObject data) {

        newFamilyDialog.dismiss();
        if (familyDialog != null && familyDialog.isShowing()) {
            familyDialog.dismiss();
        }
        ((MainActivity) getActivity()).openRelationshipMenu(data.getRelationshipPatientId());
    }


}

