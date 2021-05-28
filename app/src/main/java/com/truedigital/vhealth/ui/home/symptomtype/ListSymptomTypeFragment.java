package com.truedigital.vhealth.ui.home.symptomtype;


import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ApiListSymptom;
import com.truedigital.vhealth.ui.adapter.ListSymptomTypeAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.home.patient.PatientHomeFragment;

import java.util.ArrayList;


public class ListSymptomTypeFragment extends BaseMvpFragment<ListSymptomTypeFragmentInterface.Presenter>
        implements ListSymptomTypeFragmentInterface.View{

    private RecyclerView recyclerView;
    private ListSymptomTypeAdapter adapter;

    public ListSymptomTypeFragment() {
        super();
    }

    public static ListSymptomTypeFragment newInstance(int position) {
        ListSymptomTypeFragment fragment = new ListSymptomTypeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListSymptomTypeFragmentInterface.Presenter createPresenter(){
        return ListSymptomTypeFragmentPresenter.create();
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
        adapter = new ListSymptomTypeAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        //recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        adapter.setOnClickListener(new ListSymptomTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openSymptom(adapter.getListData().get(position).getId());
            }

            @Override
            public void onFavoriteClick(View view, int position) {
                if (adapter.isFavorite(position)) {
                    getPresenter().callApiDelFavorite(position);
                }
                else {
                    getPresenter().callApiAddFavorite(position);
                }

            }
        });
    }

    private void openSymptom(int id) {
        PatientHomeFragment patientHomeFragment = ((PatientHomeFragment)ListSymptomTypeFragment.this.getParentFragment());
        //patientHomeFragment.openClinic(id);
    }

    @Override
    public void setupView(){
        getPresenter().loadSymptom();
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
    public void updateSymptom(ArrayList<ApiListSymptom.FilterList> listData) {
        adapter.setListData(listData);
    }

    @Override
    public void setFavorite(int id, boolean isFavorite) {
        adapter.setFavorite(id, isFavorite);
    }

}

