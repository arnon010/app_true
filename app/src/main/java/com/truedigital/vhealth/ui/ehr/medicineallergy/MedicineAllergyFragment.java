package com.truedigital.vhealth.ui.ehr.medicineallergy;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.MedicineAllergyCriteriaObject;
import com.truedigital.vhealth.model.MedicineAllergyImageObject;
import com.truedigital.vhealth.model.MedicineAllergyObject;
import com.truedigital.vhealth.ui.adapter.ListMedicineAllergyAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class MedicineAllergyFragment extends BaseMvpFragment<MedicineAllergyFragmentInterface.Presenter>
        implements MedicineAllergyFragmentInterface.View {

    private MedicineAllergyCriteriaObject criteria;
    private boolean canLoadMore;
    private boolean isMaxItem = false;

    private RecyclerView rvMedicineAllergy;
    private ListMedicineAllergyAdapter adapter;
    private List<MedicineAllergyObject> tempList = new ArrayList<>();

    public MedicineAllergyFragment() {
        super();
    }

    public static MedicineAllergyFragment newInstance(int patientId, int patientMenuId) {
        Bundle args = new Bundle();
        MedicineAllergyFragment fragment = new MedicineAllergyFragment();
        fragment.criteria = new MedicineAllergyCriteriaObject();
        fragment.criteria.setPatientId(patientId);
        fragment.criteria.setPatientMenuId(patientMenuId);
        fragment.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        fragment.criteria.setPageSize(AppConstants.PAGE_SIZE);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MedicineAllergyFragmentInterface.Presenter createPresenter() {
        return MedicineAllergyFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_medicine_allergy;
    }

    @Override
    public void bindView(View view) {
        rvMedicineAllergy = (RecyclerView) view.findViewById(R.id.recyclerMedicineAllergy);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        rvMedicineAllergy.addOnScrollListener(this.onScrollListener());

        ((MainActivity) getActivity()).setAddOnClickListener(new MainActivity.OnAddClickListener() {
            @Override
            public void onAddClick() {
                MedicineAllergyObject data = new MedicineAllergyObject();
                data.setPatientId(criteria.getPatientId());
                data.setPatientMenuId(criteria.getPatientMenuId());
                data.setAttachmentList(new ArrayList<MedicineAllergyImageObject>());

                ((MainActivity) getActivity()).openNewFormMedicineAllergy(data, false);
            }
        });
    }

    @Override
    public void initialize() {
        setAdapter();
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.medicine_allergy), false, null, true);

        this.setupInstance();
        if (criteria != null && adapter != null && adapter.getItemCount() == 0 && tempList.size() == 0) {
            this.searchMedicineAllergy();
        } else {
            adapter.setListData(tempList);
        }
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

    public void clearTempList() {
        tempList.clear();
    }

    public void searchMedicineAllergy() {
        adapter.getListData().clear();
        getPresenter().getMedicineAllergyList(criteria);
    }

    private void setAdapter() {
        adapter = new ListMedicineAllergyAdapter(rvMedicineAllergy.getContext());
        rvMedicineAllergy.setAdapter(adapter);

        rvMedicineAllergy.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvMedicineAllergy.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        adapter.setOnClickListener(this.medicineAllergyOnItemClickListener());
        adapter.setOnDeleteClickListener(this.onDeleteItem());
    }

    private ListMedicineAllergyAdapter.OnItemClickListener medicineAllergyOnItemClickListener() {
        return new ListMedicineAllergyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MedicineAllergyObject data) {
                getPresenter().getMedicineAllergyDetail(data);
            }
        };
    }

    private ListMedicineAllergyAdapter.OnDeleteClickListener onDeleteItem() {
        return new ListMedicineAllergyAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(final MedicineAllergyObject data) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.dialog_confirm_delete))
                        .setPositiveButton(getResources().getString(R.string.dialog_need_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getPresenter().deleteMedicineAllergy(data);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.dialog_un_need_button), null).show();
            }
        };
    }

    private RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(0)) {
                    if (!canLoadMore || isMaxItem) {
                        return;
                    }
                    canLoadMore = false;
                    criteria.setPageIndex(criteria.getPageIndex() + 1);
                    getPresenter().getMedicineAllergyList(criteria);
                }
            }
        };
    }

    @Override
    public void setMedicineAllergyList(ArrayList<MedicineAllergyObject> listData) {
        List<MedicineAllergyObject> rvDataList = adapter.getListData();

        if (listData.size() == 0) {
            this.isMaxItem = true;
            if (adapter.getListData().size() == 0) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.data_not_found))
                        .setPositiveButton(getResources().getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).show();
            }
        } else {
            this.isMaxItem = false;
            rvDataList.addAll(listData);
        }

        tempList = adapter.getListData();
        adapter.setListData(rvDataList);
        this.canLoadMore = true;
    }

    @Override
    public void setMedicineAllergyDetail(MedicineAllergyObject data) {
        ((MainActivity) getActivity()).openUpdateFormMedicineAllergy(data);
    }

    @Override
    public void onDeleteSuccess(MedicineAllergyObject data) {
        List<MedicineAllergyObject> rvDataList = adapter.getListData();
        rvDataList.clear();
        tempList = rvDataList;
        adapter.setListData(rvDataList);

        this.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        this.searchMedicineAllergy();
    }

}
