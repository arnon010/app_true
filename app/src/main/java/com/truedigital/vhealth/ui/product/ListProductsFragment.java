package com.truedigital.vhealth.ui.product;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.ListProductDao;
import com.truedigital.vhealth.ui.adapter.ListProductAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.List;

public class ListProductsFragment extends BaseMvpFragment<ListProductsFragmentInterface.Presenter>
        implements ListProductsFragmentInterface.View {

    public static final String TAG = "ProductConfirmFragment";
    private static final String KEY_GROUP_PRODUCT_ID = "GROUP_PRODUCT_ID";
    private RecyclerView recyclerView;
    private ListProductAdapter adapter;
    private int id;

    public ListProductsFragment() {
        super();
    }

    public static ListProductsFragment newInstance(int id) {
        ListProductsFragment fragment = new ListProductsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_GROUP_PRODUCT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ListProductsFragmentInterface.Presenter createPresenter() {
        return ListProductsFragmentPresenter.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().loadData();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_list_products;
    }

    @Override
    public void bindView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void setupInstance() {
    }

    @Override
    public void setupView() {
        id = getArguments().getInt(KEY_GROUP_PRODUCT_ID, 0);
        showToolbar();
        setupRecycleView();
        getPresenter().loadData();
    }

    private void showToolbar() {
        if (AppManager.getDataManager().isDoctor()) {
            ((MainActivity) getActivity()).showToolbar(R.string.doctor_product_cart_title, true);
        } else {
            ((MainActivity) getActivity()).showToolbar(R.string.product_cart_title, true);
        }
    }

    private void setupRecycleView() {
        adapter = new ListProductAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener(new ListProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int productId = adapter.getData(position).getId();
                openProductDetail(productId);
            }

            @Override
            public void onFavoriteClick(View view, int position) {
            }

            @Override
            public void onItemAddCartClick(View view, int position) {
            }
        });
    }

    @Override
    public void initialize() {
    }

    @Override
    public int getProductGroupId() {
        return id;
    }

    @Override
    public void setData(List<ItemProductDao> listData) {
        adapter.setListData(listData);
    }

    @Override
    public void openListProducts(int id) {
    }

    @Override
    public void openProductDetail(int id) {
        ((MainActivity) getActivity()).openProductDetail(id);
    }

    @Override
    public String getDataFromFile(String filename) {
        return new LoadData(getActivity()).loadJSONFromAsset(filename);
    }

    @Override
    public List<ItemProductDao> getData(String json, boolean isShowLog) {
        Gson gson = new Gson();
        ListProductDao data = gson.fromJson(json, ListProductDao.class);
        return data.getListData();
    }
}
