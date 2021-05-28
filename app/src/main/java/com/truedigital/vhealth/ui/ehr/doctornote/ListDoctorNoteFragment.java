package com.truedigital.vhealth.ui.ehr.doctornote;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.model.DoctorNoteCriteriaObject;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.adapter.ListDoctorNoteAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListDoctorNoteFragment extends BaseMvpFragment<ListDoctorNoteFragmentInterface.Presenter>
        implements ListDoctorNoteFragmentInterface.View {

    private DoctorNoteCriteriaObject criteria;
    private boolean canLoadMore;
    private boolean isMaxItem = false;

    private RecyclerView recyclerDoctor;
    private ListDoctorNoteAdapter adapter;
    private ListDoctorNoteFragment.OnLoadListener onLoadListener;
    private List<DoctorNoteObject> tempList = new ArrayList<>();

    public interface OnLoadListener {
        void onLoadComplete();
    }

    public void setOnLoadListener(ListDoctorNoteFragment.OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public ListDoctorNoteFragment() {
        super();
    }

    public static ListDoctorNoteFragment newInstance(DoctorNoteCriteriaObject criteria) {
        ListDoctorNoteFragment fragment = new ListDoctorNoteFragment();
        fragment.criteria = criteria;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListDoctorNoteFragmentInterface.Presenter createPresenter() {
        return ListDoctorNoteFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_doctor_note;
    }

    @Override
    public void bindView(View view) {
        recyclerDoctor = view.findViewById(R.id.recyclerDoctor);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        recyclerDoctor.addOnScrollListener(this.onScrollListener());
    }

    @Override
    public void initialize() {
        setAdapter();
        if (criteria != null && criteria.getIsCanLoad() && adapter != null && adapter.getItemCount() == 0 && tempList.size() == 0) {
            searchDoctor(criteria);
        } else {
            adapter.setListData(tempList);
        }
    }

    private void setAdapter() {
        adapter = new ListDoctorNoteAdapter(recyclerDoctor.getContext());
        recyclerDoctor.setAdapter(adapter);

        recyclerDoctor.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerDoctor.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        adapter.setOnClickListener(this.doctorOnItemClickListener());
    }

    public void searchDoctor(DoctorNoteCriteriaObject criteria) {
        adapter.getListData().clear();
        getPresenter().getDoctorNoteList(criteria);
    }

    @Override
    public void setDoctorNoteList(ArrayList<DoctorNoteObject> listData) {
        List<DoctorNoteObject> rvDataList = adapter.getListData();

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

    private ListDoctorNoteAdapter.OnItemClickListener doctorOnItemClickListener() {
        return new ListDoctorNoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DoctorNoteObject data) {
                boolean isViewOnly = true;
                ItemAppointmentDao appointmentItem = new ItemAppointmentDao();
                appointmentItem.setAppointmentNumber(data.getAppointmentNumber());
                appointmentItem.setAppointmentTime(data.getAppointmentTimeString());
                appointmentItem.setContactMinute(data.getContactMinutes());
                appointmentItem.setDoctorName(data.getDoctorName());
                ((MainActivity) getActivity()).openDoctorNoteConfirm(appointmentItem, isViewOnly, false);
//                getPresenter().getDoctorNoteDetail(data);
            }
        };
    }

    @Override
    public void setDoctorNoteDetail(DoctorNoteObject data) {

        ((MainActivity) getActivity()).openDoctorNoteDetail(data);
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
                    getPresenter().getDoctorNoteList(criteria);
                }
            }
        };
    }

}
