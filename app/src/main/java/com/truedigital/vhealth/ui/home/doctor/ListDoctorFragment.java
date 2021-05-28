package com.truedigital.vhealth.ui.home.doctor;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemCategoryDao;
import com.truedigital.vhealth.model.ItemClinicDao;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ItemSubCategoryDao;
import com.truedigital.vhealth.ui.adapter.ListDoctorAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.List;

import timber.log.Timber;

public class ListDoctorFragment extends BaseMvpFragment<ListDoctorFragmentInterface.Presenter>
        implements ListDoctorFragmentInterface.View {

    public static final String TAG = "ProductConfirmFragment";

    public static final int SORTBY_CATEGORY = 0;
    public static final int SORTBY_CLINIC = 1;
    public static final int SORTBY_SUB_CATEGARY = 2;

    private static final String KEY_CATEGORY = "KEY_CATEGORY";
    private static final String KEY_SUB_CATEGORY = "KEY_SUB_CATEGORY";
    private static final String KEY_CLINIC = "KEY_CLINIC";

    private ItemCategoryDao itemCategoryDao;
    private ItemSubCategoryDao itemSubCategoryDao;
    private ItemClinicDao dataClinic;
    private RecyclerView recyclerView;
    private ListDoctorAdapter adapter;
    private int sortBy;
    private int categoryId;
    private int clinicId;

    private int pageIndex = AppConstants.START_PAGE_INDEX;
    private final int pageSize = AppConstants.PAGE_SIZE_NORMAL;
    private boolean canLoadMore;

    public ListDoctorFragment() {
        super();
    }

    public static ListDoctorFragment newInstance(int sortBy, ItemCategoryDao data) {
        ListDoctorFragment fragment = new ListDoctorFragment();
        fragment.itemCategoryDao = data;
        fragment.categoryId = data.getId();
        fragment.clinicId = 0;
        fragment.sortBy = sortBy;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListDoctorFragment newInstance(int sortBy, ItemClinicDao dataClinic) {
        ListDoctorFragment fragment = new ListDoctorFragment();
        fragment.dataClinic = dataClinic;
        fragment.categoryId = 0;
        fragment.clinicId = dataClinic.getId();
        fragment.sortBy = sortBy;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListDoctorFragment newInstance(int sortBy, ItemSubCategoryDao data) {
        ListDoctorFragment fragment = new ListDoctorFragment();
        fragment.itemSubCategoryDao = data;
        fragment.categoryId = data.getId();
        fragment.clinicId = 0;
        fragment.sortBy = sortBy;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListDoctorFragmentInterface.Presenter createPresenter() {
        return ListDoctorFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_search_doctor;
    }

    @Override
    public void bindView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void setupInstance() {
    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuHomeSelected();

        showToolbar();
        setupRecyclerView();
        getListDoctors(pageIndex);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_CATEGORY, itemCategoryDao);
        outState.putParcelable(KEY_SUB_CATEGORY, itemSubCategoryDao);
        outState.putParcelable(KEY_CLINIC, dataClinic);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        itemCategoryDao = savedInstanceState.getParcelable(KEY_CATEGORY);
        itemSubCategoryDao = savedInstanceState.getParcelable(KEY_SUB_CATEGORY);
        dataClinic = savedInstanceState.getParcelable(KEY_CLINIC);
    }

    private void showToolbar() {
        switch (sortBy) {
            case SORTBY_CATEGORY:
                ((MainActivity) getActivity()).showToolbar(itemCategoryDao.getDescription(), true, itemCategoryDao.getLogoImageWithoutCaption());
                break;
            case SORTBY_CLINIC:
                ((MainActivity) getActivity()).showToolbar(dataClinic.getTitle(), true, dataClinic.getLogoImageWithoutCaption());
                break;
            case SORTBY_SUB_CATEGARY:
                ((MainActivity) getActivity()).showToolbar(itemSubCategoryDao.getDescription(), true, itemSubCategoryDao.getLogoImageWithoutCaption());
                break;
        }
    }

    private void getListDoctors(int pageIndex) {
        if (sortBy == SORTBY_SUB_CATEGARY) {
            getPresenter().getListDoctorsFromSubCategoryId(getCategoryId(), pageIndex, pageSize);
        } else {
            getPresenter().getListDoctors(getCategoryId(), getClinicId(), pageIndex, pageSize);
        }
    }

    @Override
    public void initialize() {
    }

    private void setupRecyclerView() {
        adapter = new ListDoctorAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener((view, position) ->
                openDoctorDetail(adapter.getData(position).getDoctorId())
        );
        recyclerView.addOnScrollListener(onScrollListener());
    }

    private RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(0)) {
                    if (canLoadMore) {
                        canLoadMore = false;
                        pageIndex++;
                        getListDoctors(pageIndex);
                        Timber.e("loag page : %s", pageIndex);
                    }
                }
            }
        };
    }

    @Override
    public void openDoctorDetail(int doctorId) {
        ((MainActivity) getActivity()).openDoctorDetail(doctorId);
    }

    @Override
    public void openListDoctor(int catId) {
    }

    @Override
    public void updateDoctor(List<ItemDoctorDao> listData) {
        adapter.setListData(listData);
    }

    @Override
    public void setData(List<ItemDoctorDao> listData) {
        if (listData.size() > 0) {
            canLoadMore = listData.size() == pageSize;
            adapter.addListData(listData);
        } else {
            canLoadMore = false;
        }
    }

    @Override
    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public int getClinicId() {
        return clinicId;
    }
}
