package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.ui.adapter.ListDailyHeartBeatRateAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;
import com.truedigital.vhealth.ui.main.MainActivity;

import java.util.ArrayList;

public class ListDailyHeartBeatRateFragment extends BaseMvpFragment<ListDailyHeartBeatRateFragmentInterface.Presenter>
        implements ListDailyHeartBeatRateFragmentInterface.View {

    private int patientMenuId;
    private int patientId;

    private RecyclerView rvDaily;
    private ListDailyHeartBeatRateAdapter adapter;

    public ListDailyHeartBeatRateFragment() {
        super();
    }

    public static ListDailyHeartBeatRateFragment newInstance(int patientId, int patientMenuId) {
        ListDailyHeartBeatRateFragment fragment = new ListDailyHeartBeatRateFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListDailyHeartBeatRateFragmentInterface.Presenter createPresenter() {
        return ListDailyHeartBeatRateFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_daily_heart_beat_rate;
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
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.heart_beat_rate_data), false, null, false);
        this.searchDailyHeartBeatRate();
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

    public void searchDailyHeartBeatRate() {
        getPresenter().getListDailyHeartBeatRate(this.patientId, this.patientMenuId);
    }

    private void setAdapter() {
        adapter = new ListDailyHeartBeatRateAdapter(getContext(), new ArrayList<DailyHeartBeatRateObject>());
        adapter.setOnClickListener(this.onClickDaily());

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDaily.setLayoutManager(gridLayoutManager);
        rvDaily.addItemDecoration(new ListDividerItemDecoration(getActivity(), R.drawable.line_divider_green, true));
        rvDaily.setAdapter(adapter);
    }

    @Override
    public void setListDailyHeartBeatRate(ArrayList<DailyHeartBeatRateObject> dataList) {
        if (dataList.size() == 0) {
            new AlertDialog.Builder(getActivity())
                    .setTitle(getResources().getString(R.string.dialog_default_title))
                    .setMessage(getResources().getString(R.string.data_not_found))
                    .setPositiveButton(getResources().getString(R.string.dialog_ok_button), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    }).show();
        } else {
            adapter.setListData(dataList);
        }
    }

    private ListDailyHeartBeatRateAdapter.OnItemClickListener onClickDaily() {
        return new ListDailyHeartBeatRateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DailyHeartBeatRateObject data) {
                ((MainActivity) getActivity()).openTimeHeartBeatRate(data);
            }
        };
    }


}
