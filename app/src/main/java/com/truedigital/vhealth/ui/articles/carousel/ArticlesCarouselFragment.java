package com.truedigital.vhealth.ui.articles.carousel;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ArticlesCarouselFragment extends BaseMvpFragment<ArticlesCarouselFragmentInterface.Presenter>
        implements ArticlesCarouselFragmentInterface.View {

    private CarouselView carouselView;

    private List<String> bannerImages = new ArrayList<>();
    private List<ItemArticleDao> listDataBanner = new ArrayList<>();
    private ProgressBar progressBar;

    public ArticlesCarouselFragment() {
        super();
    }

    public static ArticlesCarouselFragment newInstance() {
        ArticlesCarouselFragment fragment = new ArticlesCarouselFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ArticlesCarouselFragmentInterface.Presenter createPresenter() {
        return ArticlesCarouselFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_product_promotion;
    }

    @Override
    public void bindView(View view) {
        carouselView = view.findViewById(R.id.carouselView);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setupInstance() {
    }

    @Override
    public void setupView() {
        setupCarouseView();
        getPresenter().loadData();
    }

    @Override
    public void setDataBanner(List<ItemArticleDao> listData) {
        listDataBanner.clear();
        bannerImages.clear();

        listDataBanner = listData;
        for (ItemArticleDao data : listData) {
            bannerImages.add(data.getBannerImage());
        }
        setupCarouseView();
    }

    private void setupCarouseView() {
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(bannerImages.size());
        carouselView.setImageClickListener(position -> {
            openArticleDetail(listDataBanner.get(position).getArticleId());
        });
    }

    ImageListener imageListener = (position, imageView) -> Glide.with(getActivity())
            .load(bannerImages.get(position)).asBitmap()
            .placeholder(R.drawable.img_default)
            .listener(new RequestListener<String, Bitmap>() {
                @Override
                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            })
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
            .into(imageView);

    @Override
    public void initialize() {
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
    public void setData(List<ItemArticleDao> listData) {
        //adapter.setListData(listData);
    }

    @Override
    public void openListProducts(int groupId) {
        ((MainActivity) getActivity()).openListProducts(groupId);
    }

    @Override
    public void openArticleDetail(int id) {
        ((MainActivity) getActivity()).openArticleDetail(id);
    }

}
