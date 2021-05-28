package com.truedigital.vhealth.ui.home.clinic;


import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemClinicDao;
import com.truedigital.vhealth.ui.adapter.ListClinicAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.List;


public class ListClinicFragment extends BaseMvpFragment<ListClinicFragmentInterface.Presenter>
        implements ListClinicFragmentInterface.View{

    private static final String KEY_POSITION = "POSITION";
    private RecyclerView recyclerView;
    private ListClinicAdapter adapter;
    private int position;

    public ListClinicFragment() {
        super();
    }

    public static ListClinicFragment newInstance() {
        ListClinicFragment fragment = new ListClinicFragment();
        Bundle args = new Bundle();
        //args.putInt(KEY_POSITION,position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListClinicFragmentInterface.Presenter createPresenter(){
        return ListClinicFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_search_doctor;
    }

    @Override
    public void bindView( View view ){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    }

    @Override
    public void setupInstance(){
        position = getArguments().getInt(KEY_POSITION);

        adapter = new ListClinicAdapter(recyclerView.getContext(),position);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        int spacingInPixels = 24; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        adapter.setOnClickListener(new ListClinicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //openClinic(adapter.getListData().get(position).getId());
                openListDoctor(adapter.getData(position));
                Log.e("List Clinic ","" + adapter.getData(position).getId() + " clinicId "+ adapter.getData(position).getClinicId());
            }
            @Override
            public void onFavoriteClick(View view, int position) {

            }
        });

    }

    @Override
    public void setupView(){
        getPresenter().loadData();
    }

    @Override
    public void initialize(){

    }

    @Override
    public void restoreView( Bundle savedInstanceState ){

    }

    @Override
    public void onSaveInstanceState( Bundle outState ){
        super.onSaveInstanceState( outState );
    }

    @Override
    public void onRestoreInstanceState( Bundle savedInstanceState ){
        super.onRestoreInstanceState( savedInstanceState );
    }

    @Override
    public void openListDoctor(ItemClinicDao itemClinicDao) {
        ((MainActivity)getActivity()).openListDoctor(itemClinicDao);
    }

    @Override
    public void setData(List<ItemClinicDao> listData) {
        adapter.setListData(listData);
    }
}

