package com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BloodPressureObject;
import com.truedigital.vhealth.model.DailyBloodPressureObject;
import com.truedigital.vhealth.ui.adapter.ListTimeBloodPressureAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;
import com.truedigital.vhealth.ui.main.MainActivity;

import java.util.ArrayList;

public class ListTimeBloodPressureFragment extends BaseMvpFragment<ListTimeBloodPressureFragmentInterface.Presenter>
        implements ListTimeBloodPressureFragmentInterface.View {

    private DailyBloodPressureObject daily;

    private RecyclerView rvDaily;
    private ListTimeBloodPressureAdapter adapter;

    public ListTimeBloodPressureFragment() {
        super();
    }

    public static ListTimeBloodPressureFragment newInstance(DailyBloodPressureObject daily) {
        ListTimeBloodPressureFragment fragment = new ListTimeBloodPressureFragment();
        fragment.daily = daily;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListTimeBloodPressureFragmentInterface.Presenter createPresenter() {
        return ListTimeBloodPressureFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_time_blood_pressure;
    }

    @Override
    public void bindView(View view) {
        rvDaily = (RecyclerView) view.findViewById(R.id.recycler_data_list);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();
    }

    @Override
    public void initialize() {
        setAdapter();
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.blood_pressure_data), false,null, false);
        this.searchListBloodPressure();
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

    public void searchListBloodPressure() {
        getPresenter().getListBloodPressure(this.daily);
    }

    private void setAdapter() {
        adapter = new ListTimeBloodPressureAdapter(getActivity(), new ArrayList<BloodPressureObject>());
        adapter.setOnClickListener(this.onClickDaily());

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDaily.setLayoutManager(gridLayoutManager);
        rvDaily.addItemDecoration(new ListDividerItemDecoration(getActivity(), R.drawable.line_divider_green, true));
        rvDaily.setAdapter(adapter);
    }

    @Override
    public void setListBloodPressure(ArrayList<BloodPressureObject> dataList) {
        adapter.setListData(dataList);
    }

    private ListTimeBloodPressureAdapter.OnItemClickListener onClickDaily() {
        return new ListTimeBloodPressureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BloodPressureObject data) {
                getPresenter().getBloodPressure(daily.getPatientId(), data);
            }
        };
    }

    @Override
    public void setBloodPressure(BloodPressureObject data) {
        ((MainActivity) getActivity()).openUpdateFormBloodPressure(data);
    }

    @Override
    public void checkDataEmpty() {
        if (adapter.getItemCount() == 0) {
            ((MainActivity) getActivity()).onBackPressed();
        }
    }

}
