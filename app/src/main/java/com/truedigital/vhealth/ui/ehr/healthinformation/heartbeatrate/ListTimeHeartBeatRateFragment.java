package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.ui.adapter.ListTimeHeartBeatRateAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;
import com.truedigital.vhealth.ui.main.MainActivity;

import java.util.ArrayList;

public class ListTimeHeartBeatRateFragment extends BaseMvpFragment<ListTimeHeartBeatRateFragmentInterface.Presenter>
        implements ListTimeHeartBeatRateFragmentInterface.View {

    private DailyHeartBeatRateObject daily;

    private RecyclerView rvDaily;
    private ListTimeHeartBeatRateAdapter adapter;

    public ListTimeHeartBeatRateFragment() {
        super();
    }

    public static ListTimeHeartBeatRateFragment newInstance(DailyHeartBeatRateObject daily) {
        ListTimeHeartBeatRateFragment fragment = new ListTimeHeartBeatRateFragment();
        fragment.daily = daily;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListTimeHeartBeatRateFragmentInterface.Presenter createPresenter() {
        return ListTimeHeartBeatRateFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_time_heart_beat_rate;
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
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.heart_beat_rate_data), false,null, false);
        this.searchListHeartBeatRate();
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

    public void searchListHeartBeatRate() {
        getPresenter().getListHeartBeatRate(this.daily);
    }

    private void setAdapter() {
        adapter = new ListTimeHeartBeatRateAdapter(getActivity(), new ArrayList<HeartBeatRateObject>());
        adapter.setOnClickListener(this.onClickDaily());

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDaily.setLayoutManager(gridLayoutManager);
        rvDaily.addItemDecoration(new ListDividerItemDecoration(getActivity(), R.drawable.line_divider_green, true));
        rvDaily.setAdapter(adapter);
    }

    @Override
    public void setListHeartBeatRate(ArrayList<HeartBeatRateObject> dataList) {
        adapter.setListData(dataList);
    }

    private ListTimeHeartBeatRateAdapter.OnItemClickListener onClickDaily() {
        return new ListTimeHeartBeatRateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HeartBeatRateObject data) {
                getPresenter().getHeartBeatRate(daily.getPatientId(), data);
            }
        };
    }

    @Override
    public void setHeartBeatRate(HeartBeatRateObject data) {
        ((MainActivity) getActivity()).openUpdateFormHeartBeatRate(data);
    }

    @Override
    public void checkDataEmpty() {
        if (adapter.getItemCount() == 0) {
            ((MainActivity) getActivity()).onBackPressed();
        }
    }

}
