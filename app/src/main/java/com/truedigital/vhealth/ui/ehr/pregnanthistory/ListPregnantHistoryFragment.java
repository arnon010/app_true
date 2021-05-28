package com.truedigital.vhealth.ui.ehr.pregnanthistory;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.PregnantHistoryObject;
import com.truedigital.vhealth.model.PregnantHistoryCriteriaObject;
import com.truedigital.vhealth.ui.adapter.ListPregnantHistoryAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListPregnantHistoryFragment extends BaseMvpFragment<ListPregnantHistoryFragmentInterface.Presenter>
        implements ListPregnantHistoryFragmentInterface.View {

    private PregnantHistoryCriteriaObject criteria;
    private boolean canLoadMore;
    private boolean isMaxItem = false;

    private RecyclerView recyclerPregnantHistory;
    private ListPregnantHistoryAdapter adapter;
    private ListPregnantHistoryFragment.OnLoadListener onLoadListener;
    private List<PregnantHistoryObject> tempList = new ArrayList<>();

    public interface OnLoadListener {
        void onLoadComplete();
    }

    public void setOnLoadListener(ListPregnantHistoryFragment.OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public ListPregnantHistoryFragment() {
        super();
    }

    public static ListPregnantHistoryFragment newInstance(PregnantHistoryCriteriaObject criteria) {
        ListPregnantHistoryFragment fragment = new ListPregnantHistoryFragment();
        fragment.criteria = criteria;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListPregnantHistoryFragmentInterface.Presenter createPresenter() {
        return ListPregnantHistoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_pregnant_history;
    }

    @Override
    public void bindView(View view) {
        recyclerPregnantHistory = view.findViewById(R.id.recyclerPregnantHistory);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        recyclerPregnantHistory.addOnScrollListener(this.onScrollListener());
    }

    @Override
    public void initialize() {
        setAdapter();
        if (criteria != null && criteria.getIsCanLoad() && adapter != null && adapter.getItemCount() == 0 && tempList.size() == 0) {
            searchPregnantHistory(criteria);
        } else {
            adapter.setListData(tempList);
        }
    }

    public void clearTempList() {
        tempList.clear();
    }

    private void setAdapter() {
        adapter = new ListPregnantHistoryAdapter(recyclerPregnantHistory.getContext());
        recyclerPregnantHistory.setAdapter(adapter);

        recyclerPregnantHistory.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerPregnantHistory.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        adapter.setOnClickListener(this.pregnantHistoryOnItemClickListener());
        adapter.setOnDeleteClickListener(this.onDeleteItem());
    }

    public void searchPregnantHistory(PregnantHistoryCriteriaObject criteria) {
        adapter.getListData().clear();
        getPresenter().getPregnantHistoryList(criteria);
    }

    @Override
    public void setPregnantHistoryList(ArrayList<PregnantHistoryObject> listData) {
        List<PregnantHistoryObject> rvDataList = adapter.getListData();

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

    private ListPregnantHistoryAdapter.OnItemClickListener pregnantHistoryOnItemClickListener() {
        return new ListPregnantHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PregnantHistoryObject data) {
                getPresenter().getPregnantHistoryDetail(data);
            }
        };
    }

    @Override
    public void setPregnantHistoryDetail(PregnantHistoryObject data) {

        ((MainActivity) getActivity()).openUpdateFormPregnantHistory(data);
    }

    private ListPregnantHistoryAdapter.OnDeleteClickListener onDeleteItem() {
        return new ListPregnantHistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(final PregnantHistoryObject data) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.dialog_confirm_delete))
                        .setPositiveButton(getResources().getString(R.string.dialog_need_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getPresenter().deletePregnantHistory(data);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.dialog_un_need_button), null).show();
            }
        };
    }

    @Override
    public void onDeleteSuccess(PregnantHistoryObject data) {

        List<PregnantHistoryObject> rvDataList = adapter.getListData();
        rvDataList.clear();
        tempList = rvDataList;
        adapter.setListData(rvDataList);

        this.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        this.searchPregnantHistory(this.criteria);
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
                    getPresenter().getPregnantHistoryList(criteria);
                }
            }
        };
    }

}
