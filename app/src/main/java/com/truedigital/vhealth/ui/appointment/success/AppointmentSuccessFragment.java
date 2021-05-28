package com.truedigital.vhealth.ui.appointment.success;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppointmentDataManager;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ItemPersonDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.view.ViewAppointmentInfo;
import com.truedigital.vhealth.utils.AmountUtils;
import com.truedigital.vhealth.utils.ContactTypeUtil;
import com.truedigital.vhealth.utils.ImageUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class AppointmentSuccessFragment extends BaseMvpFragment<AppointmentSuccessFragmentInterface.Presenter>
        implements AppointmentSuccessFragmentInterface.View {

    private static final String TAG = "AppointmentSuccess";

    private TextView tvName;
    private TextView tvPrice;
    private TextView tvTitleBranch;
    private TextView tvSubTitleBranch;

    private ItemDoctorDao data;
    private Button btnDone;
    private LinearLayout lloThumbnail;
    private ImageView imageProfile;
    private RelativeLayout rloPrice;

    private LinearLayout llo_price_confirm;
    private ExpandableLayout llo_price_detail;
    private boolean isPriceExpand;
    private ApiAppointmentRequest appointmentData;
    private ViewAppointmentInfo viewAppointmentInfo;

    private LinearLayout llo_discountPrice;
    private LinearLayout llo_priceNoVat;
    private LinearLayout llo_priceVat;
    private LinearLayout llo_summaryPrice;
    private double discountPrice;
    private double total_price;
    private TextView tv_summaryPrice;

    public AppointmentSuccessFragment() {
        super();
    }

    public static AppointmentSuccessFragment newInstance(ItemDoctorDao data, ApiAppointmentRequest appointmentData, double discountPrice) {
        AppointmentSuccessFragment fragment = new AppointmentSuccessFragment();
        Bundle args = new Bundle();

        fragment.data = data;
        fragment.appointmentData = appointmentData;
        fragment.discountPrice = discountPrice;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSuccess();
    }

    @Override
    public AppointmentSuccessFragmentInterface.Presenter createPresenter() {
        return AppointmentSuccessFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_appointment_success;
    }

    @Override
    public void bindView(View view) {
        lloThumbnail = view.findViewById(R.id.layout_thumbnail);
        rloPrice = view.findViewById(R.id.layout_price);
        imageProfile = view.findViewById(R.id.card_image);
        tvName = view.findViewById(R.id.tv_name);
        tvPrice = view.findViewById(R.id.tv_price);
        tvTitleBranch = view.findViewById(R.id.tv_title);
        tvSubTitleBranch = view.findViewById(R.id.tv_subtitle);

        btnDone = view.findViewById(R.id.btn_done);

        llo_price_confirm = view.findViewById(R.id.item_confirm_price);
        llo_price_detail = view.findViewById(R.id.layout_price_detail);

        tv_summaryPrice = llo_price_confirm.findViewById(R.id.tv_summaryPrice);

        llo_priceNoVat = llo_price_detail.findViewById(R.id.priceNoVat);
        llo_priceVat = llo_price_detail.findViewById(R.id.priceVat);
        llo_discountPrice = llo_price_detail.findViewById(R.id.discountPrice);
        llo_summaryPrice = llo_price_detail.findViewById(R.id.summaryPrice);

        viewAppointmentInfo = view.findViewById(R.id.view_appoint_info);
    }

    @Override
    public void setupInstance() {
        hidePriceDetail();
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.appointment_success_title, false);
    }

    @Override
    public void setupView() {
        showToolbar();
        ((MainActivity) getActivity()).setMenuHomeSelected();

        lloThumbnail.setVisibility(View.VISIBLE);
        rloPrice.setVisibility(View.GONE);

        if (data.getName() != null) {
            tvName.setText(data.getName());
        } else {
            tvName.setText("");
        }

        tvPrice.setText(data.getPricePerMinuteFormat());
        ImageUtils.show(getActivity(), imageProfile, data.getProfileImage());

        setupBranch();
        setUpAppointmentInfo();
        btnDone.setOnClickListener(v -> openListAppointment());
        llo_price_confirm.setOnClickListener(v -> {
            isPriceExpand = !isPriceExpand;
            if (!llo_price_detail.isExpanded()) {
                showPriceDetail();
            } else {
                hidePriceDetail();
            }
        });

        updatePrice();
    }

    public void setDiscountPrice(double discountPrice) {
        if (discountPrice > data.getPriceWithFee()) {
            discountPrice = data.getPriceWithFee();
        }

        this.discountPrice = discountPrice;
    }

    @SuppressLint("SetTextI18n")
    private void updatePrice() {
        setDiscountPrice(getDiscountPrice());

        total_price = data.getPriceWithFee() - getDiscountPrice();
        if (total_price <= 0) {
            total_price = 0;
        }
        tv_summaryPrice.setText(AmountUtils.decimalFormat(total_price) + " " + data.getCurrencySymbol());

        priceNoVat();
        priceVat();
        showDiscountPrice();
        showSummaryPrice();
    }

    private void setUpAppointmentInfo() {
        ItemAppointmentDao data = new ItemAppointmentDao();
        data.setAppointmentTime(appointmentData.getAppointmentTime());
        data.setAppointmentNumber(appointmentData.getAppointmentNumber());
        data.setContactTypeName(new ContactTypeUtil().getTypeName(appointmentData.getTypeCode()));
        viewAppointmentInfo.setData(data.getShowShortDate(),
                data.getShowShortTime(),
                data.getAppointmentNumber(),
                data.getContactTypeName(),
                data.getShowChanelIcon());
    }

    @SuppressLint("SetTextI18n")
    private void priceNoVat() {
        TextView tv_title = llo_priceNoVat.findViewById(R.id.tv_title);
        TextView tv_price = llo_priceNoVat.findViewById(R.id.tv_price);
        tv_title.setText(getString(R.string.price_title_doctor));
        tv_price.setText(AmountUtils.decimalFormat(data.getPrice()) + " " + data.getCurrencySymbol());
    }

    @SuppressLint("SetTextI18n")
    private void priceVat() {
        TextView tv_title = llo_priceVat.findViewById(R.id.tv_title);
        TextView tv_price = llo_priceVat.findViewById(R.id.tv_price);
        tv_title.setText(getString(R.string.price_title_fee));
        double priceFee = data.getPriceWithFee() - data.getPrice();
        tv_price.setText(AmountUtils.decimalFormat(priceFee) + " " + data.getCurrencySymbol());
    }

    @SuppressLint("SetTextI18n")
    private void showDiscountPrice() {
        TextView tv_title = llo_discountPrice.findViewById(R.id.tv_title);
        TextView tv_price = llo_discountPrice.findViewById(R.id.tv_price);
        tv_title.setText(getString(R.string.price_title_discount));
        tv_price.setText(AmountUtils.decimalFormat(getDiscountPrice()) + " " + data.getCurrencySymbol());
    }

    @SuppressLint("SetTextI18n")
    private void showSummaryPrice() {
        TextView tv_title = llo_summaryPrice.findViewById(R.id.tv_title);
        TextView tv_price = llo_summaryPrice.findViewById(R.id.tv_price);
        tv_title.setText(getString(R.string.price_title_total));
        tv_price.setText(AmountUtils.decimalFormat(total_price) + " " + data.getCurrencySymbol());
    }

    private void setupBranch() {
        tvTitleBranch.setText(data.getCategoryName());
        tvSubTitleBranch.setText("N/A");
        String strSubtitle = "";
        if (data.getSubCategoryName() != null) {
            for (String msg : data.getSubCategoryName()) {
                if (strSubtitle.equals("")) {
                    strSubtitle = msg;
                } else {
                    strSubtitle = strSubtitle + " , " + msg;
                }
            }
            tvSubTitleBranch.setText("" + strSubtitle);
        }
    }

    @Override
    public void initialize() {
    }

    @Override
    public void loadDataAppointment() {
        appointmentData = AppointmentDataManager.getInstance().getData();
    }

    @Override
    public void showPriceDetail() {
        llo_price_detail.expand();
    }

    @Override
    public void hidePriceDetail() {
        llo_price_detail.collapse();
    }

    @Override
    public void openListAppointment() {
        ((MainActivity) getActivity()).gotoAppointment();
    }

    private void showSuccess() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_success, null);

        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    @Override
    public double getDiscountPrice() {
        return discountPrice;
    }
}
