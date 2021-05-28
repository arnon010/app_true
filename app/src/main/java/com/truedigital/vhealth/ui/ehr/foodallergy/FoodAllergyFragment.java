package com.truedigital.vhealth.ui.ehr.foodallergy;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.FoodAllergyCriteriaObject;
import com.truedigital.vhealth.model.FoodAllergyImageObject;
import com.truedigital.vhealth.model.FoodAllergyObject;
import com.truedigital.vhealth.ui.adapter.ListFoodAllergyAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FoodAllergyFragment extends BaseMvpFragment<FoodAllergyFragmentInterface.Presenter>
        implements FoodAllergyFragmentInterface.View {

    private FoodAllergyCriteriaObject criteria;
    private boolean canLoadMore;
    private boolean isMaxItem = false;

    private RecyclerView rvFoodAllergy;
    private ListFoodAllergyAdapter adapter;
    private List<FoodAllergyObject> tempList = new ArrayList<>();

    public FoodAllergyFragment() {
        super();
    }

    public static FoodAllergyFragment newInstance(int patientId, int patientMenuId) {
        Bundle args = new Bundle();
        FoodAllergyFragment fragment = new FoodAllergyFragment();
        fragment.criteria = new FoodAllergyCriteriaObject();
        fragment.criteria.setPatientId(patientId);
        fragment.criteria.setPatientMenuId(patientMenuId);
        fragment.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        fragment.criteria.setPageSize(AppConstants.PAGE_SIZE);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public FoodAllergyFragmentInterface.Presenter createPresenter() {
        return FoodAllergyFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_food_allergy;
    }

    @Override
    public void bindView(View view) {
        rvFoodAllergy = (RecyclerView) view.findViewById(R.id.recyclerFoodAllergy);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        rvFoodAllergy.addOnScrollListener(this.onScrollListener());

        ((MainActivity) getActivity()).setAddOnClickListener(new MainActivity.OnAddClickListener() {
            @Override
            public void onAddClick() {
                FoodAllergyObject data = new FoodAllergyObject();
                data.setPatientId(criteria.getPatientId());
                data.setPatientMenuId(criteria.getPatientMenuId());
                data.setAttachmentList(new ArrayList<FoodAllergyImageObject>());

                ((MainActivity) getActivity()).openNewFormFoodAllergy(data, false);
            }
        });
    }

    @Override
    public void initialize() {
        setAdapter();
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.food_allergy), false, null, true);

        this.setupInstance();
        if (criteria != null && adapter.getItemCount() == 0 && tempList.size() == 0) {
            this.searchFoodAllergy();
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

    public void searchFoodAllergy() {
        adapter.getListData().clear();
        getPresenter().getFoodAllergyList(criteria);
    }

    private void setAdapter() {
        adapter = new ListFoodAllergyAdapter(rvFoodAllergy.getContext());
        rvFoodAllergy.setAdapter(adapter);

        rvFoodAllergy.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvFoodAllergy.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        adapter.setOnClickListener(this.foodAllergyOnItemClickListener());
        adapter.setOnDeleteClickListener(this.onDeleteItem());
    }

    private ListFoodAllergyAdapter.OnItemClickListener foodAllergyOnItemClickListener() {
        return new ListFoodAllergyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FoodAllergyObject data) {
                getPresenter().getFoodAllergyDetail(data);
            }
        };
    }

    private ListFoodAllergyAdapter.OnDeleteClickListener onDeleteItem() {
        return new ListFoodAllergyAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(final FoodAllergyObject data) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.dialog_confirm_delete))
                        .setPositiveButton(getResources().getString(R.string.dialog_need_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getPresenter().deleteFoodAllergy(data);
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
                    getPresenter().getFoodAllergyList(criteria);
                }
            }
        };
    }

    @Override
    public void setFoodAllergyList(ArrayList<FoodAllergyObject> listData) {
        List<FoodAllergyObject> rvDataList = adapter.getListData();

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
    public void setFoodAllergyDetail(FoodAllergyObject data) {
        ((MainActivity) getActivity()).openUpdateFormFoodAllergy(data);
    }

    @Override
    public void onDeleteSuccess(FoodAllergyObject data) {
        List<FoodAllergyObject> rvDataList = adapter.getListData();
        rvDataList.clear();
        tempList = rvDataList;
        adapter.setListData(rvDataList);

        this.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        this.searchFoodAllergy();
    }

}
