package com.truedigital.vhealth.ui.home.subcategory;


import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemCategoryDao;
import com.truedigital.vhealth.model.ItemSubCategoryDao;
import com.truedigital.vhealth.model.ListSubCategoryDao;
import com.truedigital.vhealth.ui.adapter.ListSubCategoryAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.SpacesItemDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends BaseMvpFragment<SubCategoryFragmentInterface.Presenter>
        implements SubCategoryFragmentInterface.View{

    public static final String TAG = "ProductConfirmFragment";
    private static final String KEY_POSITION = "POSITION";
    private RecyclerView recyclerView;
    private ListSubCategoryAdapter adapter;
    private int position;

    public SubCategoryFragment() {
        super();
    }

    public static SubCategoryFragment newInstance(int position) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public SubCategoryFragmentInterface.Presenter createPresenter(){
        return SubCategoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_search_doctor;
    }

    @Override
    public void bindView( View view ){
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void setupInstance(){
        adapter = new ListSubCategoryAdapter(recyclerView.getContext(),1);
        recyclerView.setAdapter(adapter);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener(new ListSubCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //openListDoctor(adapter.getData(position));
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
    public void openListDoctor(ItemCategoryDao itemCategoryDao) {
        //PatientHomeFragment patientHomeFragment = ((PatientHomeFragment) SubCategoryFragment.this.getParentFragment());
        //patientHomeFragment.openListDoctor(catId);
        ((MainActivity)getActivity()).openListDoctor(itemCategoryDao);
    }

    @Override
    public void updateCategory(List<ItemSubCategoryDao> listData) {
        adapter.setListData(listData);
    }

    @Override
    public String getDataFromFile(String filename) {
        String data = new LoadData(getActivity()).loadJSONFromAsset(filename);
        return data;
    }

    @Override
    public List<ItemSubCategoryDao> getData(String json, boolean isShowLog) {
        List<ItemSubCategoryDao> listData = new ArrayList<>();

        Gson gson = new Gson();
        ListSubCategoryDao data = gson.fromJson(json, ListSubCategoryDao.class);
        listData = data.getListData();

        if (isShowLog) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            gson = gsonBuilder.create();
            Log.d("", "load data" + gson.toJson(data));
        }

        return listData;
    }
}

