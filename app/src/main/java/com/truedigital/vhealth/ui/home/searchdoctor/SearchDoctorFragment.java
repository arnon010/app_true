package com.truedigital.vhealth.ui.home.searchdoctor;


import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.adapter.ListDoctorAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.detaildoctor.DetailDoctorActivity;
import com.truedigital.vhealth.utils.AppConstants;


public class SearchDoctorFragment extends BaseMvpFragment<SearchDoctorFragmentInterface.Presenter>
        implements SearchDoctorFragmentInterface.View{

    private RecyclerView recyclerView;
    private ListDoctorAdapter adapter;

    public SearchDoctorFragment() {
        super();
    }

    public static SearchDoctorFragment newInstance(int position) {
        SearchDoctorFragment fragment = new SearchDoctorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public SearchDoctorFragmentInterface.Presenter createPresenter(){
        return SearchDoctorFragmentPresenter.create();
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
        adapter = new ListDoctorAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnClickListener(new ListDoctorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openDoctor(adapter.getListData().get(position).getDoctorId());
            }

        });
    }

    private void openDoctor(int id) {
        //..Open Doctor Detail
        Intent intent = new Intent( getActivity(), DetailDoctorActivity.class);
        intent.putExtra(AppConstants.EXTRA_DOCTOR_ID, id);
        startActivity(intent);
        getActivity().overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }

    @Override
    public void setupView(){
        getPresenter().loadDoctor();
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
    public void setFavorite(int id, boolean isFavorite) {
        //adapter.setFavorite(id, isFavorite);
    }
}

