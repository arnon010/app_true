package com.truedigital.vhealth.ui.home.category;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemCategoryDao;
import com.truedigital.vhealth.model.ListCategoryDao;
import com.truedigital.vhealth.ui.adapter.ListCategoryAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.List;


public class ListCategoryFragment extends BaseMvpFragment<ListCategoryFragmentInterface.Presenter>
        implements ListCategoryFragmentInterface.View {

    private RecyclerView recyclerView;
    private ListCategoryAdapter adapter;
    private int position;

    public ListCategoryFragment() {
        super();
    }

    public static ListCategoryFragment newInstance() {
        ListCategoryFragment fragment = new ListCategoryFragment();
        Bundle args = new Bundle();
        //args.putInt(KEY_POSITION,position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListCategoryFragmentInterface.Presenter createPresenter() {
        return ListCategoryFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_search_doctor;
    }

    @Override
    public void bindView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void setupInstance() {
        adapter = new ListCategoryAdapter(recyclerView.getContext(), position);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        int spacingInPixels = 24; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener(new ListCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                openListDoctor(adapter.getData(position));
            }

            @Override
            public void onFavoriteClick(View view, int position) {
            }
        });
    }

    @Override
    public void setupView() {
        getPresenter().loadData();
    }

    @Override
    public void initialize() {
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

    @Override
    public void openListDoctor(ItemCategoryDao itemCategoryDao) {
        ((MainActivity) getActivity()).openListDoctor(itemCategoryDao);
    }

    @Override
    public void setData(List<ItemCategoryDao> listData) {
        adapter.setListData(listData);
    }

    @Override
    public String getDataFromFile(String filename) {
        return new LoadData(getActivity()).loadJSONFromAsset(filename);
    }

    @Override
    public List<ItemCategoryDao> getData(String json, boolean isShowLog) {
        Gson gson = new Gson();
        ListCategoryDao data = gson.fromJson(json, ListCategoryDao.class);
        return data.getListData();
    }
}
