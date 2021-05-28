package com.truedigital.vhealth.ui.appointment.list;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.model.appointment.ListAppointmentDao;
import com.truedigital.vhealth.ui.adapter.ListAppointmentAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.List;

public class ListAppointmentFragment extends BaseMvpFragment<ListAppointmentFragmentInterface.Presenter>
        implements ListAppointmentFragmentInterface.View {

    public static final String TAG = "ArticlesPatientDetailFragment";
    private RecyclerView recyclerView;
    private ListAppointmentAdapter adapter;
    private int position;
    private int typeAppointment;
    private TextView tv_notfound;

    public ListAppointmentFragment() {
        super();
    }

    public static ListAppointmentFragment newInstance(int position) {
        ListAppointmentFragment fragment = new ListAppointmentFragment();
        fragment.position = position;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListAppointmentFragmentInterface.Presenter createPresenter() {
        return ListAppointmentFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_search_doctor;
    }

    @Override
    public void bindView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        tv_notfound = view.findViewById(R.id.tv_notfound);
    }

    @Override
    public void setupInstance() {
        adapter = new ListAppointmentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener(new ListAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ImageView shareView) {
                openDetail(getAppointmentType(), adapter.getData(position).getAppointmentNumber());
            }

            @Override
            public void onFavoriteClick(View view, int position) {
            }
        });
    }

    @Override
    public void setupView() {
        getPresenter().loadData();
    }

    @Override
    public void initialize() {
    }

    @Override
    public int getSortOrder() {
        return 0;
    }

    @Override
    public int getAppointmentType() {
        return typeAppointment;
    }

    @Override
    public void openDetail(int appointmentType, String appointmentNumber) {
        ((MainActivity) getActivity()).openAppointmentDetail(appointmentType, appointmentNumber);
    }

    @Override
    public void setData(List<ItemAppointmentDao> listData) {
        adapter.setListData(listData);
        updateView();
    }

    private void updateView() {
        if (adapter.getListData().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            tv_notfound.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            tv_notfound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getDataFromFile(String filename) {
        return new LoadData(getActivity()).loadJSONFromAsset(filename);
    }

    @Override
    public List<ItemAppointmentDao> getData(String json, boolean isShowLog) {
        Gson gson = new Gson();
        ListAppointmentDao data = gson.fromJson(json, ListAppointmentDao.class);
        return data.getListData();
    }

    @Override
    public String getSortBy() {
        return "";
    }

    public void searchByTime(int sortby, int menuItem) {
        getPresenter().loadData(sortby, menuItem);
        typeAppointment = menuItem;
    }

    public void searchByDoctor(int sortby, int menuItem) {
        getPresenter().loadData(sortby, menuItem);
    }
}
