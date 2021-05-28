package com.truedigital.vhealth.ui.product;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemProductBannerDao;
import com.truedigital.vhealth.model.ItemProductGroupDao;
import com.truedigital.vhealth.ui.adapter.ListProductGroupAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.product.promotion.ProductPromotionFragment;
import com.truedigital.vhealth.utils.SpacesItemDecoration;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends BaseMvpFragment<ProductFragmentInterface.Presenter>
        implements ProductFragmentInterface.View{

    private ImageView card_image;
    private TextView tvLogin;
    private Button btnAppInfo;

    private RecyclerView recyclerView;
    private CarouselView carouselView;
    private ListProductGroupAdapter adapter;

    private List<String> bannerImages = new ArrayList<>();
    private List<ItemProductBannerDao> listDataBanner = new ArrayList<>();

    public ProductFragment() {
        super();
    }

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ProductFragmentInterface.Presenter createPresenter(){
        return ProductFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_product;
    }

    @Override
    public void bindView( View view ){
        //carouselView = view.findViewById(R.id.carouselView);
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void setupInstance(){

    }

    @Override
    public void setupView(){
        showToolbar();
    }

    private void updateView() {
        setupProductPromotion();
        setupRecycleView();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkToken();
    }

    private void checkToken() {
        Log.e("","check token");
        String token = AppManager.getDataManager().getAccess_token();
        getPresenter().onCheckToken(token, new BaseMvpPresenter.OnTokenListener() {
            @Override
            public void onSuccess() {
                getPresenter().loadData();
                updateView();
            }

            @Override
            public void onFail() {

            }
        });
    }

    private void showToolbar() {
        if (AppManager.getDataManager().isDoctor()) {
            ((MainActivity) getActivity()).showToolbar(R.string.doctor_product_cart_title,true);
        }
        else {
            ((MainActivity) getActivity()).showToolbar(R.string.product_cart_title,false);
        }
    }

    private void setupProductPromotion() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = ProductPromotionFragment.newInstance();

        if(fragment != null) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.content_promotion, fragment);
            ft.commitAllowingStateLoss();
        }
    }


    @Override
    public void setDataBanner(List<ItemProductBannerDao> listData) {

    }

    private void setupCarouseView() {
    }

    @Override
    public void openProductDetail(int id) {
        ((MainActivity)getActivity()).openProductDetail(id);
    }

    private void setupRecycleView() {
        adapter = new ListProductGroupAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener(new ListProductGroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //int id = adapter.getListData().get(position).getId();
                openListProducts( adapter.getData(position).getId());
                //Log.e(TAG,"product group id: " + id);
            }

            @Override
            public void onFavoriteClick(View view, int position) {

            }
        });

    }

    @Override
    public void initialize(){

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
    public void setData(List<ItemProductGroupDao> listData) {
        adapter.setListData(listData);
    }

    @Override
    public void openListProducts(int groupId) {
        ((MainActivity)getActivity()).openListProducts(groupId);
    }
}

