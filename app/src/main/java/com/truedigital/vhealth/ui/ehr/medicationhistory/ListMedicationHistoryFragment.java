package com.truedigital.vhealth.ui.ehr.medicationhistory;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.model.MedicationHistoryCriteriaObject;
import com.truedigital.vhealth.ui.adapter.ListMedicationHistoryAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListMedicationHistoryFragment extends BaseMvpFragment<ListMedicationHistoryFragmentInterface.Presenter>
        implements ListMedicationHistoryFragmentInterface.View {

    private MedicationHistoryCriteriaObject criteria;
    private boolean canLoadMore;
    private boolean isMaxItem = false;

    private RecyclerView recyclerMedicationHistory;
    private ListMedicationHistoryAdapter adapter;
    private ListMedicationHistoryFragment.OnLoadListener onLoadListener;
    private List<MedicationHistoryObject> tempList = new ArrayList<>();

    public interface OnLoadListener {
        void onLoadComplete();
    }

    public void setOnLoadListener(ListMedicationHistoryFragment.OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public ListMedicationHistoryFragment() {
        super();
    }

    public static ListMedicationHistoryFragment newInstance(MedicationHistoryCriteriaObject criteria) {
        ListMedicationHistoryFragment fragment = new ListMedicationHistoryFragment();
        fragment.criteria = criteria;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListMedicationHistoryFragmentInterface.Presenter createPresenter() {
        return ListMedicationHistoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_medication_history;
    }

    @Override
    public void bindView(View view) {
        recyclerMedicationHistory = view.findViewById(R.id.recyclerMedicationHistory);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        recyclerMedicationHistory.addOnScrollListener(this.onScrollListener());
    }

    @Override
    public void initialize() {
        setAdapter();
        if (criteria != null && criteria.getIsCanLoad() && adapter != null && adapter.getItemCount() == 0 && tempList.size() == 0) {
            searchMedicationHistory(criteria);
        } else {
            adapter.setListData(tempList);
        }
    }

    public void clearTempList() {
        tempList.clear();
    }

    private void setAdapter() {
        adapter = new ListMedicationHistoryAdapter(recyclerMedicationHistory.getContext());
        recyclerMedicationHistory.setAdapter(adapter);

        recyclerMedicationHistory.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerMedicationHistory.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        adapter.setOnClickListener(this.medicationHistoryOnItemClickListener());
        adapter.setOnDeleteClickListener(this.onDeleteItem());
    }

    public void searchMedicationHistory(MedicationHistoryCriteriaObject criteria) {
        adapter.getListData().clear();
        getPresenter().getMedicationHistoryList(criteria);
    }

    @Override
    public void setMedicationHistoryList(ArrayList<MedicationHistoryObject> listData) {
        List<MedicationHistoryObject> rvDataList = adapter.getListData();

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
        this.onLoadListener.onLoadComplete();
    }

    private ListMedicationHistoryAdapter.OnItemClickListener medicationHistoryOnItemClickListener() {
        return new ListMedicationHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MedicationHistoryObject data) {
                getPresenter().getMedicationHistoryDetail(data);
            }
        };
    }

    @Override
    public void setMedicationHistoryDetail(MedicationHistoryObject data) {

        if (!data.getIsChiiwiiProduct()) {
            ((MainActivity) getActivity()).openUpdateFormMedicationHistory(data);
        } else {
            ((MainActivity) getActivity()).openFormMedicationHistoryChiiwiiLive(data);
        }
    }

    private ListMedicationHistoryAdapter.OnDeleteClickListener onDeleteItem() {
        return new ListMedicationHistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(final MedicationHistoryObject data) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.dialog_confirm_delete))
                        .setPositiveButton(getResources().getString(R.string.dialog_need_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getPresenter().deleteMedicationHistory(data);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.dialog_un_need_button), null).show();
            }
        };
    }

    @Override
    public void onDeleteSuccess(MedicationHistoryObject data) {

        List<MedicationHistoryObject> rvDataList = adapter.getListData();
        rvDataList.clear();
        tempList = rvDataList;
        adapter.setListData(rvDataList);

        this.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        this.searchMedicationHistory(this.criteria);
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
                    getPresenter().getMedicationHistoryList(criteria);
                }
            }
        };
    }


}
