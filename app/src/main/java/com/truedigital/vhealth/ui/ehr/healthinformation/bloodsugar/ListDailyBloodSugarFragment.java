package com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.ui.adapter.ListDailyBloodSugarAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;
import com.truedigital.vhealth.ui.main.MainActivity;

import java.util.ArrayList;

public class ListDailyBloodSugarFragment extends BaseMvpFragment<ListDailyBloodSugarFragmentInterface.Presenter>
        implements ListDailyBloodSugarFragmentInterface.View {

    private int patientMenuId;
    private int patientId;

    private RecyclerView rvDaily;
    private ListDailyBloodSugarAdapter adapter;

    public ListDailyBloodSugarFragment() {
        super();
    }

    public static ListDailyBloodSugarFragment newInstance(int patientId, int patientMenuId) {
        ListDailyBloodSugarFragment fragment = new ListDailyBloodSugarFragment();
        fragment.patientId = patientId;
        fragment.patientMenuId = patientMenuId;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListDailyBloodSugarFragmentInterface.Presenter createPresenter() {
        return ListDailyBloodSugarFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_daily_blood_sugar;
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
        this.searchDailyBloodSugar();
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

    public void searchDailyBloodSugar() {
        getPresenter().getListDailyBloodSugar(this.patientId, this.patientMenuId);
    }

    private void setAdapter() {
        adapter = new ListDailyBloodSugarAdapter(getActivity(), new ArrayList<DailyBloodSugarObject>());
        adapter.setOnClickListener(this.onClickDaily());

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDaily.setLayoutManager(gridLayoutManager);
        rvDaily.addItemDecoration(new ListDividerItemDecoration(getActivity(), R.drawable.line_divider_green, true));
        rvDaily.setAdapter(adapter);
    }

    @Override
    public void setListDailyBloodSugar(ArrayList<DailyBloodSugarObject> dataList) {
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

    private ListDailyBloodSugarAdapter.OnItemClickListener onClickDaily() {
        return new ListDailyBloodSugarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DailyBloodSugarObject data) {
                ((MainActivity) getActivity()).openTimeBloodSugar(data);
            }
        };
    }
}
