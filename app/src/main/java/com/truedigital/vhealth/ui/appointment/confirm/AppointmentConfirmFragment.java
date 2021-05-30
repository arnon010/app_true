package com.truedigital.vhealth.ui.appointment.confirm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.manager.AppointmentDataManager;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ItemPersonDao;
import com.truedigital.vhealth.model.ListPersonDao;
import com.truedigital.vhealth.model.PaymentDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.password.create.PasswordCreateActivity;
import com.truedigital.vhealth.ui.payment.PaymentFragment;
import com.truedigital.vhealth.ui.setting.testinsurance.ui.TestInsuranceActivity;
import com.truedigital.vhealth.ui.setting.testinsurance.ui.TestInsuranceHealthActivity;
import com.truedigital.vhealth.ui.view.ViewAppointmentInfo;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ContactTypeUtil;
import com.truedigital.vhealth.utils.ImageUtils;
import com.truedigital.vhealth.utils.LoadData;

import java.util.ArrayList;
import java.util.List;

import co.omise.android.models.Token;
import co.omise.android.ui.CreditCardActivity;

import static android.app.Activity.RESULT_CANCELED;

public class AppointmentConfirmFragment extends BaseMvpFragment<AppointmentConfirmFragmentInterface.Presenter>
        implements AppointmentConfirmFragmentInterface.View {

    private static final String KEY_DOCTOR = "KEY_DOCTOR";

    private TextView tvName;
    private TextView tvPrice;
    private TextView tvTitleBranch;
    private TextView tvSubTitleBranch;

    private ItemDoctorDao data;
    private LinearLayout lloThumbnail;
    private ImageView imageProfile;
    private RelativeLayout rloPrice;

    private ApiAppointmentRequest appointmentData = new ApiAppointmentRequest();
    private ViewAppointmentInfo viewAppointmentInfo;
    private String omiseToken;
    private double originDiscountPrice = 0;
    private boolean payWithCoupon = false;

    public AppointmentConfirmFragment() {
        super();
    }

    public static AppointmentConfirmFragment newInstance(ItemDoctorDao data) {
        AppointmentConfirmFragment fragment = new AppointmentConfirmFragment();

        Bundle args = new Bundle();
        args.putParcelable(KEY_DOCTOR, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = getArguments().getParcelable(KEY_DOCTOR);
        checkToken();
    }

    @Override
    public AppointmentConfirmFragmentInterface.Presenter createPresenter() {
        return AppointmentConfirmFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_appointment_confirm;
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
        viewAppointmentInfo = view.findViewById(R.id.view_appoint_info);
    }

    @Override
    public void setupInstance() {
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.appointment_title_create, true);
    }

    private void checkToken() {
        String token = AppManager.getDataManager().getAccess_token();
        getPresenter().onCheckToken(token, new BaseMvpPresenter.OnTokenListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail() {
            }
        });
    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuHomeSelected();

        showToolbar();
        lloThumbnail.setVisibility(View.VISIBLE);
        rloPrice.setVisibility(View.GONE);

        tvName.setText(data.getName());
        tvPrice.setText(data.getPricePerMinuteFormat());
        ImageUtils.show(getActivity(), imageProfile, data.getProfileImage());
        setupBranch();
        loadDataAppointment();
        setUpAppointmentInfo();
        updateView();
    }

    private void setUpAppointmentInfo() {
        ItemAppointmentDao data = new ItemAppointmentDao();
        data.setAppointmentTime(appointmentData.getAppointmentTime());
        data.setAppointmentNumber("-");
        data.setContactTypeName(new ContactTypeUtil().getTypeName(appointmentData.getTypeCode()));
        viewAppointmentInfo.setData(data.getShowShortDate(),
                data.getShowShortTime(),
                data.getAppointmentNumber(),
                data.getContactTypeName(),
                data.getShowChanelIcon());
    }

    public String getDataFromFile(String filename) {
        return new LoadData(getActivity()).loadJSONFromAsset(filename);
    }

    public List<ItemPersonDao> getData(String json, boolean isShowLog) {
        Gson gson = new Gson();
        ListPersonDao data = gson.fromJson(json, ListPersonDao.class);
        return data.getListData();
    }

    private void updateView() {
        setupPayment(data.getPriceWithFee(), data.getPrice());
    }

    private void setupPayment(double amount, double doctorPrice) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        PaymentFragment fragmentPayment = PaymentFragment.newInstance(PaymentFragment.PAYMENT_MODE_APPOINTMENT, getDoctorId(), getDoctorName(), amount, doctorPrice, "");

        if (fragmentPayment != null) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.content_payment, fragmentPayment);
            ft.commitAllowingStateLoss();
        }

        fragmentPayment.setOnCallback(new PaymentFragment.OnCallback() {
            @Override
            public void onBuyProductSuccess(PaymentDao data) {
            }

            @Override
            public void onCreateAppointmentSuccess(ApiAppointmentRequest appointmentRequest, double discountPrice) {
                appointmentData = appointmentRequest;
                showSuccess(appointmentData.getAppointmentNumber(), discountPrice);
            }
        });
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
    public void showSuccess(String appointmentNumber) {
        showSuccess(appointmentNumber, 0);
    }

    @Override
    public void showSuccess(String appointmentNumber, double discountPrice) {
        appointmentData.setAppointmentNumber(appointmentNumber);
        openAppointmentSuccess(data, appointmentData, discountPrice);
    }

    @Override
    public void openAppointmentSuccess(ItemDoctorDao data, ApiAppointmentRequest appointmentData, double discountPrice) {
        ((MainActivity) getActivity()).openAppointmentSuccess(data, appointmentData, discountPrice);
    }

    @Override
    public int getDoctorId() {
        return data.getDoctorId();
    }

    private String getDoctorName() {
        return data.getName();
    }

    @Override
    public String getOmiseToken() {
        return "";
    }

    private String SerializedMedicineAllergy() {
        List<String> listMedicineAllergy = new ArrayList<>();
        listMedicineAllergy.add("ยา 1");
        listMedicineAllergy.add("ยา 2");

        return new Gson().toJson(listMedicineAllergy);
    }

    private static final int REQUEST_CC = 100;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CC) {
            if (resultCode == RESULT_CANCELED) {
                return;
            }

            Token token = data.getParcelableExtra(CreditCardActivity.EXTRA_TOKEN_OBJECT);
            // process your token here.
            omiseToken = token.id;
        }
    }
}
