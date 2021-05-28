package com.truedigital.vhealth.ui.home.patient;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jama.carouselview.CarouselView;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ItemProductBannerDao;
import com.truedigital.vhealth.model.ItemSubCategoryDao;
import com.truedigital.vhealth.ui.adapter.ViewPagerAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.home.category.ListCategoryFragment;
import com.truedigital.vhealth.ui.home.doctorstandby.ListDoctorStandbyFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class PatientHomeFragment extends BaseMvpFragment<PatientHomeFragmentInterface.Presenter>
        implements PatientHomeFragmentInterface.View {

    private ViewPager viewPagerDoctor;
    private TextView tv_category;
    private TextView tv_clinic;

    private int tagPosition = 0;
    private LinearLayout layoutDoctorStandby;
    private RelativeLayout layoutPromotion;
    private RelativeLayout layoutFeatured;
    private RecyclerView rvHealthcare;

    public PatientHomeFragment() {
        super();
    }

    public static PatientHomeFragment newInstance() {
        PatientHomeFragment fragment = new PatientHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public PatientHomeFragmentInterface.Presenter createPresenter() {
        return PatientHomeFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_patient_home;
    }

    @Override
    public void bindView(View view) {
        viewPagerDoctor = view.findViewById(R.id.viewPagerDoctor);
        tv_category = view.findViewById(R.id.tv_category);
        tv_clinic = view.findViewById(R.id.tv_clinic);
        layoutDoctorStandby = view.findViewById(R.id.layout_doctor_standby);
        layoutPromotion = view.findViewById(R.id.lytPromotion);
        layoutFeatured = view.findViewById(R.id.lytFeatured);
        rvHealthcare = view.findViewById(R.id.rvHealthcare);
    }

    @Override
    public void setupInstance() {
    }

    @Override
    public void setupView() {
        MainActivity mainActivity = ((MainActivity) getActivity());
        mainActivity.hideToolbar();
        mainActivity.setMenuHomeSelected();

        if (getPresenter() != null) {
            updateView();
        }
    }

    private void updateView() {
        setupViewPagerDoctor(viewPagerDoctor);

        //setupDoctorStandby();

        getPresenter().loadListHealthcare();
        getPresenter().loadDataPromotion();
        getPresenter().loadDataArticle();

        setupListener();
        updateTagButtonDoctor(tagPosition);
    }

    private void setupListener() {
        tv_category.setOnClickListener(onCategoryClickListener());
        tv_clinic.setOnClickListener(onClinicClickListener());
        viewPagerDoctor.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateTagButtonDoctor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private View.OnClickListener onCategoryClickListener() {
        return v -> updateSelectViewPagerDoctor(0);
    }

    private View.OnClickListener onClinicClickListener() {
        return v -> updateSelectViewPagerDoctor(1);
    }

    private void updateTagButtonDoctor(int position) {
        tagPosition = position;
        if (position == 0) {
            tv_category.setSelected(true);
            tv_clinic.setSelected(false);
        } else {
            tv_category.setSelected(false);
            tv_clinic.setSelected(true);
        }
    }

    private void updateSelectViewPagerDoctor(int position) {
        updateTagButtonDoctor(position);
        viewPagerDoctor.setCurrentItem(position);
    }

    private void setupViewPagerDoctor(ViewPager viewPager) {
        String[] tagName = getResources().getStringArray(R.array.tag_type);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(ListCategoryFragment.newInstance(), "" + tagName[0]);
//        adapter.addFragment(ListClinicFragment.newInstance(),""+tagName[1]);

        viewPager.setAdapter(adapter);
    }

    @Override
    public void initialize() {
    }

    private void setupDoctorStandby() {
        ListDoctorStandbyFragment listDoctorStandbyFragment = ListDoctorStandbyFragment.newInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.content_doctor_standby, listDoctorStandbyFragment);
        ft.commitAllowingStateLoss();
        listDoctorStandbyFragment.setListener(count -> {
            if (count > 0) {
                layoutDoctorStandby.setVisibility(View.VISIBLE);
            } else {
                layoutDoctorStandby.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void openListProducts(int groupId) {
        ((MainActivity) getActivity()).openListProducts(groupId);
    }

    @Override
    public void setDataHealthcare(List<ItemSubCategoryDao> listData) {
        HealthcareAdapter adapter = new HealthcareAdapter();
        rvHealthcare.setAdapter(adapter);
        rvHealthcare.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        int spacingInPixels = 24; //getResources().getDimensionPixelSize(R.dimen.spacing);
        rvHealthcare.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter.setOnClickListener(item -> {
            ((MainActivity) getActivity()).openListDoctor(item);
        });

        adapter.setListData(listData);
    }

    @Override
    public void setDataBannerProduct(List<ItemProductBannerDao> listData) {
        List<String> bannerImages = new ArrayList<>();
        for (ItemProductBannerDao data : listData) {
            bannerImages.add(data.getBannerImage());
        }

        layoutPromotion.findViewById(R.id.progressBar).setVisibility(View.GONE);

        CarouselView carouselView = layoutPromotion.findViewById(R.id.carouselView);
        carouselView.setSize(bannerImages.size());
        carouselView.setCarouselViewListener((view, position) -> {
            view.setOnClickListener(v -> openProductDetail(listData.get(position).getProductId()));

            ImageView imageView = view.findViewById(R.id.imageView);
            Glide.with(getActivity())
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
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
        });
        carouselView.show();
        carouselView.setVisibility(View.VISIBLE);
    }

    @Override
    public void openProductDetail(int id) {
        ((MainActivity) getActivity()).openProductDetail(id);
    }

    @Override
    public void setDataBannerArticle(List<ItemArticleDao> listData) {
        List<String> bannerImages = new ArrayList<>();
        for (ItemArticleDao data : listData) {
            bannerImages.add(data.getBannerImage());
        }

        layoutFeatured.findViewById(R.id.progressBar).setVisibility(View.GONE);
        CarouselView carouselView = layoutFeatured.findViewById(R.id.carouselView);
        carouselView.setSize(bannerImages.size());
        carouselView.setCarouselViewListener((view, position) -> {
            view.setOnClickListener(v -> openArticleDetail(listData.get(position).getArticleId()));

            ImageView imageView = view.findViewById(R.id.imageView);
            Glide.with(getActivity())
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
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(imageView);
        });

        carouselView.show();
        carouselView.setVisibility(View.VISIBLE);
    }

    @Override
    public void openArticleDetail(int id) {
        ((MainActivity) getActivity()).openArticleDetail(id);
    }
}
