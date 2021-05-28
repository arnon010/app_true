package com.truedigital.vhealth.ui.home.doctorstandby;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemClinicDao;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ListDoctorDao;
import com.truedigital.vhealth.ui.adapter.ListDoctorAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.List;

public class ListDoctorStandbyFragment extends BaseMvpFragment<ListDoctorStandbyFragmentInterface.Presenter>
        implements ListDoctorStandbyFragmentInterface.View {

    public static final String TAG = "ProductConfirmFragment";

    private ItemClinicDao dataClinic;
    private RecyclerView recyclerView;
    private ListDoctorAdapter adapter;
    private int sortBy;
    private int categoryId;
    private int clinicId;

    private int pageIndex = AppConstants.START_PAGE_INDEX;
    private int pageSize = AppConstants.PAGE_SIZE_NORMAL;
    private boolean canLoadMore;

    public ListDoctorStandbyFragment() {
        super();
    }

    public static ListDoctorStandbyFragment newInstance() {
        ListDoctorStandbyFragment fragment = new ListDoctorStandbyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListDoctorStandbyFragment newInstance(int sortBy, ItemClinicDao dataClinic) {
        ListDoctorStandbyFragment fragment = new ListDoctorStandbyFragment();
        fragment.dataClinic = dataClinic;
        fragment.categoryId = 0;
        fragment.clinicId = dataClinic.getId();
        fragment.sortBy = sortBy;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListDoctorStandbyFragmentInterface.Presenter createPresenter() {
        return ListDoctorStandbyFragmentPresenter.create();
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
        getListDoctors(pageIndex);
    }

    private void getListDoctors(int pageIndex) {
        getPresenter().getListDoctors(getCategoryId(), getClinicId(), pageIndex, pageSize);
    }

    @Override
    public void initialize() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        adapter = new ListDoctorAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener((view, position) -> openDoctorDetail(adapter.getData(position).getDoctorId()));
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
                    }
                }
            }
        };
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

    @Override
    public void openDoctorDetail(int doctorId) {
        ((MainActivity) getActivity()).openDoctorDetail(doctorId);
    }

    @Override
    public void openListDoctor(int catId) {
    }

    @Override
    public void setData(List<ItemDoctorDao> listData) {
        if (listData.size() > 0) {
            canLoadMore = listData.size() == pageSize;
            adapter.addListData(listData);
        } else {
            canLoadMore = false;
        }

        if (listener != null) {
            listener.onUpdate(listData.size());
        }
    }

    public void setListener(addUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    public String getDataFromFile(String filename) {
        return new LoadData(getActivity()).loadJSONFromAsset(filename);
    }

    @Override
    public List<ItemDoctorDao> getData(String json, boolean isShowLog) {
        Gson gson = new Gson();
        ListDoctorDao data = gson.fromJson(json, ListDoctorDao.class);
        return data.getListData();
    }

    @Override
    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public int getClinicId() {
        return clinicId;
    }

    public interface addUpdateListener {
        void onUpdate(int count);
    }

    public addUpdateListener listener;
}
