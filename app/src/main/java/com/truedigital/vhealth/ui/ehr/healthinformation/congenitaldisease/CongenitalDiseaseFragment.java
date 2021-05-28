package com.truedigital.vhealth.ui.ehr.healthinformation.congenitaldisease;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.CongenitalDiseaseCriteriaObject;
import com.truedigital.vhealth.model.CongenitalDiseaseImageObject;
import com.truedigital.vhealth.model.CongenitalDiseaseObject;
import com.truedigital.vhealth.ui.adapter.ListCongenitalDiseaseAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CongenitalDiseaseFragment extends BaseMvpFragment<CongenitalDiseaseFragmentInterface.Presenter>
        implements CongenitalDiseaseFragmentInterface.View {

    private CongenitalDiseaseCriteriaObject criteria;
    private boolean canLoadMore;
    private boolean isMaxItem = false;

    private RecyclerView rvCongenitalDisease;
    private ListCongenitalDiseaseAdapter adapter;
    private List<CongenitalDiseaseObject> tempList = new ArrayList<>();

    public CongenitalDiseaseFragment() {
        super();
    }

    public static CongenitalDiseaseFragment newInstance(int patientId, int patientMenuId) {
        Bundle args = new Bundle();
        CongenitalDiseaseFragment fragment = new CongenitalDiseaseFragment();
        fragment.criteria = new CongenitalDiseaseCriteriaObject();
        fragment.criteria.setPatientId(patientId);
        fragment.criteria.setPatientMenuId(patientMenuId);
        fragment.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        fragment.criteria.setPageSize(AppConstants.PAGE_SIZE);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CongenitalDiseaseFragmentInterface.Presenter createPresenter() {
        return CongenitalDiseaseFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_congenital_disease;
    }

    @Override
    public void bindView(View view) {
        rvCongenitalDisease = (RecyclerView) view.findViewById(R.id.recyclerCongenitalDisease);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        rvCongenitalDisease.addOnScrollListener(this.onScrollListener());

        ((MainActivity) getActivity()).setAddOnClickListener(new MainActivity.OnAddClickListener() {
            @Override
            public void onAddClick() {
                CongenitalDiseaseObject data = new CongenitalDiseaseObject();
                data.setPatientId(criteria.getPatientId());
                data.setPatientMenuId(criteria.getPatientMenuId());
                data.setAttachmentList(new ArrayList<CongenitalDiseaseImageObject>());

                ((MainActivity) getActivity()).openNewFormCongenitalDisease(data, false);
            }
        });
    }

    @Override
    public void initialize() {
        setAdapter();
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.congenital_disease), false, null, true);

        this.setupInstance();
        if (criteria != null && adapter != null && adapter.getItemCount() == 0 && tempList.size() == 0) {
            this.searchCongenitalDisease();
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

    public void searchCongenitalDisease() {
        adapter.getListData().clear();
        getPresenter().getCongenitalDiseaseList(criteria);
    }

    private void setAdapter() {
        adapter = new ListCongenitalDiseaseAdapter(rvCongenitalDisease.getContext());
        rvCongenitalDisease.setAdapter(adapter);

        rvCongenitalDisease.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvCongenitalDisease.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        adapter.setOnClickListener(this.congenitalDiseaseOnItemClickListener());
        adapter.setOnDeleteClickListener(this.onDeleteItem());
    }

    private ListCongenitalDiseaseAdapter.OnItemClickListener congenitalDiseaseOnItemClickListener() {
        return new ListCongenitalDiseaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CongenitalDiseaseObject data) {
                getPresenter().getCongenitalDiseaseDetail(data);
            }
        };
    }

    private ListCongenitalDiseaseAdapter.OnDeleteClickListener onDeleteItem() {
        return new ListCongenitalDiseaseAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(final CongenitalDiseaseObject data) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.dialog_confirm_delete))
                        .setPositiveButton(getResources().getString(R.string.dialog_need_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getPresenter().deleteCongenitalDisease(data);
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
                    getPresenter().getCongenitalDiseaseList(criteria);
                }
            }
        };
    }

    @Override
    public void setCongenitalDiseaseList(ArrayList<CongenitalDiseaseObject> listData) {
        List<CongenitalDiseaseObject> rvDataList = adapter.getListData();

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
    public void setCongenitalDiseaseDetail(CongenitalDiseaseObject data) {
        ((MainActivity) getActivity()).openUpdateFormCongenitalDisease(data);
    }

    @Override
    public void onDeleteSuccess(CongenitalDiseaseObject data) {
        List<CongenitalDiseaseObject> rvDataList = adapter.getListData();
        rvDataList.clear();
        tempList = rvDataList;
        adapter.setListData(rvDataList);

        this.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        this.searchCongenitalDisease();
    }

}
