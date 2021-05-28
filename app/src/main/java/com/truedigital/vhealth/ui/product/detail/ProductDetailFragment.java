package com.truedigital.vhealth.ui.product.detail;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.ListProductDao;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.MyDialog;

import java.util.List;

public class ProductDetailFragment extends BaseMvpFragment<ProductDetailFragmentInterface.Presenter>
        implements ProductDetailFragmentInterface.View {

    public static final String TAG = "ProductConfirmFragment";
    private static final String KEY_PRODUCT_ID = "PRODUCT_ID";

    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvDescription;
    private ImageView imgProfile;

    public static ProductDetailFragment newInstance(int id) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ProductDetailFragmentInterface.Presenter createPresenter() {
        return ProductDetailFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_product_detail;
    }

    @Override
    public void bindView(View view) {
        imgProfile = view.findViewById(R.id.imgProfile);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvSubTitle = view.findViewById(R.id.tvSubTitle);
        tvDescription = view.findViewById(R.id.tvDescription);
    }

    @Override
    public void setupInstance() {
    }

    @Override
    public void setupView() {
        if (AppManager.getDataManager().isDoctor()) {
            showDoctorProduct();
        } else {
            showUserProduct();
        }
        getPresenter().loadData();
    }

    private void showDoctorProduct() {
        ((MainActivity) getActivity()).showToolbar(R.string.doctor_product_cart_title, true);
    }

    private void showUserProduct() {
        ((MainActivity) getActivity()).showToolbar(R.string.product_cart_title, true);
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
    public int getProductId() {
        return getArguments().getInt(KEY_PRODUCT_ID);
    }

    @Override
    public void setData(List<ItemProductDao> listData) {
        //adapter.setListData(listData);
    }

    @Override
    public void setData(ItemProductDao data) {
        updateView(data);
    }

    private void updateView(ItemProductDao data) {
        if (data.getCoverImage() == null) data.setCoverImage("");
        Glide.with(getActivity())
                .load(data.getCoverImage()).asBitmap()
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(imgProfile);

        tvTitle.setText(data.getTitle());
        tvSubTitle.setText(data.getShortDescription());

        //tvDescription.setText(data.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescription.setText(Html.fromHtml(data.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescription.setText(Html.fromHtml(data.getDescription()));
        }
    }

    @Override
    public void openListProducts(int id) {
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

    @Override
    public void openProductConfirm(int id) {
        ((MainActivity) getActivity()).openProductConfirm(id);
    }

    @Override
    public void showSuccess() {
        final MyDialog myDialog = new MyDialog(getActivity());
        myDialog.showSuccess(getString(R.string.doctor_suggess_product_success), new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                onSuccess();
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    @Override
    public void onSuccess() {
        //((MainActivity) getActivity()).backToAppointmentDetail();
    }

    @Override
    public String getAppointmentNumber() {
        return AppManager.getDataManager().getAppointmentNumber();
    }
}
