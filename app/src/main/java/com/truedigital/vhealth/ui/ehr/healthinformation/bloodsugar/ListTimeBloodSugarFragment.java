package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.ui.adapter.ListTimeBloodSugarAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;
import com.truedigital.vhealth.ui.main.MainActivity;

import java.util.ArrayList;

public class ListTimeBloodSugarFragment extends BaseMvpFragment<ListTimeBloodSugarFragmentInterface.Presenter>
        implements ListTimeBloodSugarFragmentInterface.View {

    private DailyBloodSugarObject daily;

    private RecyclerView rvDaily;
    private ListTimeBloodSugarAdapter adapter;

    public ListTimeBloodSugarFragment() {
        super();
    }

    public static ListTimeBloodSugarFragment newInstance(DailyBloodSugarObject daily) {
        ListTimeBloodSugarFragment fragment = new ListTimeBloodSugarFragment();
        fragment.daily = daily;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListTimeBloodSugarFragmentInterface.Presenter createPresenter() {
        return ListTimeBloodSugarFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_time_blood_sugar;
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
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.blood_sugar_data), false,null, false);
        this.searchListBloodSugar();
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

    public void searchListBloodSugar() {
        getPresenter().getListBloodSugar(this.daily);
    }

    private void setAdapter() {
        adapter = new ListTimeBloodSugarAdapter(getActivity(), new ArrayList<BloodSugarObject>());
        adapter.setOnClickListener(this.onClickDaily());

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDaily.setLayoutManager(gridLayoutManager);
        rvDaily.addItemDecoration(new ListDividerItemDecoration(getActivity(), R.drawable.line_divider_green, true));
        rvDaily.setAdapter(adapter);
    }

    @Override
    public void setListBloodSugar(ArrayList<BloodSugarObject> dataList) {
        adapter.setListData(dataList);
    }

    private ListTimeBloodSugarAdapter.OnItemClickListener onClickDaily() {
        return new ListTimeBloodSugarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BloodSugarObject data) {
                getPresenter().getBloodSugar(daily.getPatientId(), data);
            }
        };
    }

    @Override
    public void setBloodSugar(BloodSugarObject data) {
        ((MainActivity) getActivity()).openUpdateFormBloodSugar(data);
    }

    @Override
    public void checkDataEmpty() {
        if (adapter.getItemCount() == 0) {
            ((MainActivity) getActivity()).onBackPressed();
        }
    }

}
