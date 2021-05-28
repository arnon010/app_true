package com.truedigital.vhealth.ui.ehr.laboratory;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.model.LaboratoryOtherCriteriaObject;
import com.truedigital.vhealth.ui.adapter.ListLaboratoryOtherAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListLaboratoryOtherFragment extends BaseMvpFragment<ListLaboratoryOtherFragmentInterface.Presenter>
        implements ListLaboratoryOtherFragmentInterface.View {

    private LaboratoryOtherCriteriaObject criteria;
    private boolean canLoadMore;
    private boolean isMaxItem = false;

    private RecyclerView recyclerLaboratoryOther;
    private ListLaboratoryOtherAdapter adapter;
    private ListLaboratoryOtherFragment.OnLoadListener onLoadListener;
    private List<LaboratoryOtherObject> tempList = new ArrayList<>();

    public interface OnLoadListener {
        void onLoadComplete();
    }

    public void setOnLoadListener(ListLaboratoryOtherFragment.OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public ListLaboratoryOtherFragment() {
        super();
    }

    public static ListLaboratoryOtherFragment newInstance(LaboratoryOtherCriteriaObject criteria) {
        ListLaboratoryOtherFragment fragment = new ListLaboratoryOtherFragment();
        fragment.criteria = criteria;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListLaboratoryOtherFragmentInterface.Presenter createPresenter() {
        return new ListLaboratoryOtherFragmentPresenter();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_laboratory_other;
    }

    @Override
    public void bindView(View view) {
        recyclerLaboratoryOther = view.findViewById(R.id.recyclerLaboratoryOther);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        recyclerLaboratoryOther.addOnScrollListener(this.onScrollListener());
    }

    @Override
    public void initialize() {
        setAdapter();
        if (criteria != null && criteria.getIsCanLoad() && adapter != null && adapter.getItemCount() == 0 && tempList.size() == 0) {
            searchLaboratoryOther(criteria);
        } else {
            adapter.setListData(tempList);
        }
    }

    public void clearTempList() {
        tempList.clear();
    }

    private void setAdapter() {
        adapter = new ListLaboratoryOtherAdapter(recyclerLaboratoryOther.getContext());
        recyclerLaboratoryOther.setAdapter(adapter);

        recyclerLaboratoryOther.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerLaboratoryOther.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        adapter.setOnClickListener(this.laboratoryOtherOnItemClickListener());
        adapter.setOnDeleteClickListener(this.onDeleteItem());
    }

    public void searchLaboratoryOther(LaboratoryOtherCriteriaObject criteria) {
        adapter.getListData().clear();
        getPresenter().getLaboratoryOtherList(criteria);
    }

    @Override
    public void setLaboratoryOtherList(ArrayList<LaboratoryOtherObject> listData) {
        List<LaboratoryOtherObject> rvDataList = adapter.getListData();

        if (listData.size() == 0) {
            this.isMaxItem = true;
            if (adapter.getListData().size() == 0) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.data_not_found))
                        .setPositiveButton(getResources().getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) { }
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

    private ListLaboratoryOtherAdapter.OnItemClickListener laboratoryOtherOnItemClickListener() {
        return new ListLaboratoryOtherAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LaboratoryOtherObject data) {
                getPresenter().getLaboratoryOtherDetail(data);
            }
        };
    }

    @Override
    public void setLaboratoryOtherDetail(LaboratoryOtherObject data) {

        ((MainActivity) getActivity()).openUpdateFormLaboratoryOther(data);
    }

    private ListLaboratoryOtherAdapter.OnDeleteClickListener onDeleteItem() {
        return new ListLaboratoryOtherAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(final LaboratoryOtherObject data) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.dialog_default_title))
                        .setMessage(getResources().getString(R.string.dialog_confirm_delete))
                        .setPositiveButton(getResources().getString(R.string.dialog_need_button), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getPresenter().deleteLaboratoryOther(data);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.dialog_un_need_button), null).show();
            }
        };
    }

    @Override
    public void onDeleteSuccess(LaboratoryOtherObject data) {

        List<LaboratoryOtherObject> rvDataList = adapter.getListData();
        rvDataList.clear();
        tempList = rvDataList;
        adapter.setListData(rvDataList);

        this.criteria.setPageIndex(AppConstants.START_PAGE_INDEX);
        this.searchLaboratoryOther(this.criteria);
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
                    getPresenter().getLaboratoryOtherList(criteria);
                }
            }
        };
    }


}
