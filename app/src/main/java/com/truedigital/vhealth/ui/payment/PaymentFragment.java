package com.truedigital.vhealth.ui.payment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.manager.AppointmentDataManager;
import com.truedigital.vhealth.model.ApiProductRequest;
import com.truedigital.vhealth.model.BankAccountObject;
import com.truedigital.vhealth.model.ItemCreditCardDao;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ItemListCreditCardDao;
import com.truedigital.vhealth.model.ItemPersonDao;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.ListPersonDao;
import com.truedigital.vhealth.model.PaymentDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.ui.adapter.ListCreditCardAdapter;
import com.truedigital.vhealth.ui.address.AddressActivity;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.MediaBottomSheetFragment;
import com.truedigital.vhealth.ui.dialog.PaymentBankTransferDialog;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.payment.promptpay.AppointmentUiModel;
import com.truedigital.vhealth.ui.payment.promptpay.PromptpayActivity;
import com.truedigital.vhealth.ui.setting.info.SettingAppFragment;
import com.truedigital.vhealth.ui.setting.testinsurance.ui.TestInsuranceActivity;
import com.truedigital.vhealth.ui.setting.testinsurance.ui.TestInsuranceHealthActivity;
import com.truedigital.vhealth.utils.AmountUtils;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.Camera;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.PickFile;
import com.truedigital.vhealth.utils.SpannableText;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.w3c.dom.Text;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import co.omise.android.Client;
import co.omise.android.TokenRequest;
import co.omise.android.TokenRequestListener;
import co.omise.android.models.Token;
import co.omise.android.ui.AuthorizingPaymentActivity;
import co.omise.android.ui.CreditCardActivity;
import io.intercom.com.bumptech.glide.Glide;
import okhttp3.MultipartBody;
import timber.log.Timber;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.truedigital.vhealth.api.RetrofitBuilder.addMultipartBody;

public class PaymentFragment extends BaseMvpFragment<PaymentFragmentInterface.Presenter>
        implements PaymentFragmentInterface.View {

    public static final int PAYMENT_MODE_APPOINTMENT = 0;
    public static final int PAYMENT_MODE_PRODUCT = 1;

    private static final String KEY_PAYMENT_MODE = "KEY_PAYMENT_MODE";
    private static final String KEY_PAYMENT_AMOUNT = "KEY_PAYMENT_AMOUNT";
    private static final String KEY_DOCTOR_ID = "KEY_DOCTOR_ID";
    private static final String KEY_DOCTOR_NAME = "KEY_DOCTOR_NAME";
    private static final String KEY_COUPON_CODE = "KEY_COUPON_CODE";
    private static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";
    private static final String KEY_DOCTOR_PRICE = "KEY_DOCTOR_PRICE";
    private static final String KEY_ISSOLD = "KEY_ISSOLD";
    private static final int REQUEST_ADDRESS = 202;

    private Button btnDone;

    private LinearLayout llo_price_confirm;
    private ExpandableLayout llo_price_detail;
    private boolean isPriceExpand;
    private ImageView img_condition;
    private boolean isConditionSelect;
    private TextView tv_condition;

    private ApiAppointmentRequest appointmentData = new ApiAppointmentRequest();

    private EditText edit_discount_code;
    private EditText edit_payment_code;
    private double discountPrice;
    private Button btn_coupon_confirm;
    private TextView tv_summaryPrice;

    private LinearLayout llo_discountPrice;
    private LinearLayout llo_priceNoVat;
    private LinearLayout llo_priceVat;
    private LinearLayout llo_summaryPrice;
    private LinearLayout llo_payment;
    private LinearLayout llo_bank_transfer;
    private Button btn_credit_card;
    private Button btn_bank_transfer;
    private Button btn_prompt_pay;
    private Button btn_payment_code_confirm;
    private double total_price;
    private String omiseToken;
    private LinearLayout llo_credit_card;

    private LinearLayout layoutAddInsurance;
    private LinearLayout layoutAddInsuranceSuccess;
    private TextView txtYourInsurance;
    private ImageView imgInsurance;

    private EditText edit_shipping_name;
    private EditText edit_shipping_phone;
    private EditText edit_shipping_address;
    private TextView tv_shipping_address;

    private EditText ed_card_number;
    private EditText ed_card_name;
    private EditText ed_card_expired_month;
    private EditText ed_card_expired_year;
    private EditText ed_card_cvv;

    private int payment_mode;

    private LinearLayout layout_appointment_condition;
    private LinearLayout layout_shipping_address;

    private OnCallback onCallback;
    private PaymentDao paymentData;

    private boolean isPayByCreditCard = false;
    private double originDiscountPrice = 0;
    private ImageView img_remember;
    private boolean isRememberSelect;
    private String omiseFingerprint;
    private RecyclerView recyclerView;
    private ListCreditCardAdapter adapter;
    private int cardId;
    private List<ItemProductDao> listProducts;
    private double shippingFee;

    private double latitude;
    private double longtitude;
    private LinearLayout layout_coupon;
    private Button btnAttachFile;
    private PaymentFragment fragment;
    private Uri uriImage;
    private boolean isAttachFile;
    private boolean isPayByBankTransfer;
    private boolean isPayByPromptPay;
    private String imageSlipUrl;
    private ImageView imgAttach;
    private FirebaseAnalytics mFirebaseAnalytics;

    private TextView txtTitleDialog1;
    private TextView txtTitleDialog2;
    private TextView txtTitleDialog3;

    private Button btnCancel;
    private Button btnContinue;

    public interface OnCallback {
        void onBuyProductSuccess(PaymentDao data);

        void onCreateAppointmentSuccess(ApiAppointmentRequest appointmentRequest, double discountPrice);
    }

    public void setOnCallback(OnCallback onCallback) {
        this.onCallback = onCallback;
    }

    public PaymentFragment() {
        super();
    }

    public static PaymentFragment newInstance(
            int payment_mode,
            List<ItemProductDao> listProducts,
            double payment_amount,
            String appointmentNumber,
            boolean isSold
    ) {
        PaymentFragment fragment = new PaymentFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_PAYMENT_MODE, payment_mode);
        args.putDouble(KEY_PAYMENT_AMOUNT, payment_amount);
        args.putString(AppConstants.EXTRA_APPOINTMENT_NUMBER, appointmentNumber);
        fragment.listProducts = listProducts;

        args.putBoolean(KEY_ISSOLD, isSold);
        fragment.setArguments(args);
        return fragment;
    }

    public static PaymentFragment newInstance(
            int payment_mode,
            int doctorId,
            String doctorName,
            double payment_amount,
            double doctorPrice,
            String couponCode
    ) {
        PaymentFragment fragment = new PaymentFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_PAYMENT_MODE, payment_mode);
        args.putDouble(KEY_PAYMENT_AMOUNT, payment_amount);
        args.putInt(KEY_DOCTOR_ID, doctorId);
        args.putString(KEY_DOCTOR_NAME, doctorName);
        args.putString(KEY_COUPON_CODE, couponCode);
        args.putDouble(KEY_DOCTOR_PRICE, doctorPrice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public PaymentFragmentInterface.Presenter createPresenter() {
        return PaymentFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_payment;
    }

    @Override
    public void bindView(View view) {
        //..coupon
        edit_discount_code = view.findViewById(R.id.edit_discount_code);
        btn_coupon_confirm = view.findViewById(R.id.btn_coupon_confirm);

        //..bank tranfer
        edit_payment_code = view.findViewById(R.id.edit_payment_code);
        btn_payment_code_confirm = view.findViewById(R.id.btn_payment_code_confirm);

        //..credit card
        ed_card_number = view.findViewById(R.id.ed_card_number);
        ed_card_name = view.findViewById(R.id.ed_card_name);
        ed_card_expired_month = view.findViewById(R.id.ed_card_expired_month);
        ed_card_expired_year = view.findViewById(R.id.ed_card_expired_year);
        ed_card_cvv = view.findViewById(R.id.ed_card_cvv);

        //..Shipping
        edit_shipping_name = view.findViewById(R.id.edit_shipping_name);
        edit_shipping_phone = view.findViewById(R.id.edit_shipping_phone);
        edit_shipping_address = view.findViewById(R.id.edit_shipping_address);
        tv_shipping_address = view.findViewById(R.id.tv_shipping_address);

        btn_credit_card = view.findViewById(R.id.btn_credit_card);
        btn_bank_transfer = view.findViewById(R.id.btn_bank_transfer);
        btn_prompt_pay = view.findViewById(R.id.btn_prompt_pay);
        llo_bank_transfer = view.findViewById(R.id.layout_bank_transfer);
        llo_credit_card = view.findViewById(R.id.layout_credit_card);

        llo_price_confirm = view.findViewById(R.id.item_confirm_price);
        llo_price_detail = view.findViewById(R.id.layout_price_detail);

        layout_coupon = view.findViewById(R.id.layout_coupon);

        //..price detail
        tv_summaryPrice = llo_price_confirm.findViewById(R.id.tv_summaryPrice);
        llo_priceNoVat = llo_price_detail.findViewById(R.id.priceNoVat);
        llo_priceVat = llo_price_detail.findViewById(R.id.priceVat);
        llo_discountPrice = llo_price_detail.findViewById(R.id.discountPrice);
        llo_summaryPrice = llo_price_detail.findViewById(R.id.summaryPrice);

        llo_payment = view.findViewById(R.id.layout_payment);
        layout_appointment_condition = view.findViewById(R.id.layout_appointment_condition);
        layout_shipping_address = view.findViewById(R.id.layout_shipping_address);

        //..condition
        tv_condition = view.findViewById(R.id.tv_condition);
        img_condition = view.findViewById(R.id.img_condition);

        imgAttach = view.findViewById(R.id.imgAttach);
        btnAttachFile = view.findViewById(R.id.btn_attach_file);
        btnDone = view.findViewById(R.id.btn_done);
        img_remember = view.findViewById(R.id.img_remember);

        recyclerView = view.findViewById(R.id.recycler_view_list_card);

        layoutAddInsurance = view.findViewById(R.id.layoutAddInsurance);
        layoutAddInsuranceSuccess = view.findViewById(R.id.layoutAddInsuranceSuccess);
        txtYourInsurance = view.findViewById(R.id.txtYourInsurance);
        imgInsurance = view.findViewById(R.id.imgInsurance);
    }

    @Override
    public void setupInstance() {
        this.fragment = this;
        hidePriceDetail();
    }

    @Override
    public void setupView() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());

        payment_mode = getPaymentMode();

        //Mock data
        String strYourInsurance = "AIA";
        String strNumberInsurance = "23466785";
        Integer intImgInsurance = R.drawable.aia;

        updateView();
        setupListener();
        setupRecycleView();

        llo_bank_transfer.setVisibility(View.GONE);
        llo_credit_card.setVisibility(View.GONE);

        if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
            loadDataAppointment();
            layout_appointment_condition.setVisibility(View.VISIBLE);
            layout_shipping_address.setVisibility(View.GONE);

            btnDone.setText(R.string.appointment_button_next);
            updateCondition();
        } else {
            btnDone.setText(R.string.product_buttun_payment);
            btnDone.setEnabled(false);
        }

        layoutAddInsurance.setOnClickListener(view ->
                startActivity(new Intent(getContext(), TestInsuranceHealthActivity.class)));


        //Gone when cannot get data from api
        if (strNumberInsurance.isEmpty() && strYourInsurance.isEmpty()) {
            layoutAddInsuranceSuccess.setVisibility(View.GONE);
        } else {
            layoutAddInsuranceSuccess.setVisibility(View.VISIBLE);
            txtYourInsurance.setText(getString(R.string.your_insurance)+ " " + strYourInsurance + ":" + strNumberInsurance);
            imgInsurance.setImageResource(intImgInsurance);
            layoutAddInsuranceSuccess.setOnClickListener(view ->
                    startActivity(new Intent(getContext(), TestInsuranceActivity.class)));
        }

        FirebaseAddEventsBeginCheckout();
    }

    private void setupRecycleView() {
        adapter = new ListCreditCardAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        adapter.setOnClickListener(new ListCreditCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ItemListCreditCardDao data) {
                cardId = data.getCardId();
            }

            @Override
            public void onItemRemove(View view, int position, ItemListCreditCardDao data) {
                cardId = data.getCardId();
                getPresenter().removeCreditCard(cardId, position);
            }
        });
    }

    private void setupListener() {
        btnDone.setOnClickListener(onDoneButtonClick());
        btn_coupon_confirm.setOnClickListener(onClickCouponButton());
        btn_payment_code_confirm.setOnClickListener(onPaymentCodeConfirmClick());
        llo_price_confirm.setOnClickListener(onClickPriceConfirm());
        btn_bank_transfer.setOnClickListener(onClickBankTransfer());
        btn_credit_card.setOnClickListener(onClickCreditCard());
        btn_prompt_pay.setOnClickListener(onClickPromptPay());
        img_condition.setOnClickListener(onClickCondition());
        img_remember.setOnClickListener(onClickRemember());
        setConditionClickableSpan();

        edit_discount_code.addTextChangedListener(onDiscountTextWatcher());
        edit_payment_code.addTextChangedListener(onPaymentCodeTextWatcher());
        ed_card_cvv.addTextChangedListener(onCVVTextWatcher());

        tv_shipping_address.setOnClickListener(onClickShipAddress());
        btnAttachFile.setOnClickListener(onAttachFileClickListener());
        imgAttach.setOnClickListener(onAttachFileClickListener());
    }

    private View.OnClickListener onAttachFileClickListener() {
        return v -> pickImage();
    }

    private View.OnClickListener onClickShipAddress() {
        return v -> openAddress();
    }

    private void openAddress() {
        Intent intent = new Intent(requireActivity(), AddressActivity.class);
        intent.putExtra(AddressActivity.KEY_LATITUDE, 0.00);
        intent.putExtra(AddressActivity.KEY_LONGTITUDE, 0.00);
        startActivityForResult(intent, REQUEST_ADDRESS);
    }

    private TextWatcher onDiscountTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (originDiscountPrice > 0) {
                    onDiscountSuccess(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    private TextWatcher onCVVTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnDone.setEnabled(s.length() >= 3);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    private TextWatcher onPaymentCodeTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnDone.setEnabled(s.length() >= 4);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    private View.OnClickListener onClickRemember() {
        return v -> {
            isRememberSelect = !isRememberSelect;
            updateRemember();
        };
    }

    private void updateRemember() {
        img_remember.setSelected(isRememberSelect);
    }

    private View.OnClickListener onClickCondition() {
        return v -> {
            isConditionSelect = !isConditionSelect;
            updateCondition();
        };
    }

    private void updateCondition() {
        img_condition.setSelected(isConditionSelect);
        btnDone.setEnabled(isConditionSelect);
    }

    private View.OnClickListener onDoneButtonClick() {
        return v -> {
            if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
                showDialogForCreateAppointment();
            } else {
                showDialogForBuyProduct();
            }
        };
    }

    public void showDialogForCreateAppointment(){
        Dialog dialog = new Dialog(getContext(), R.style.roundCornerDialog);
        dialog.setContentView(R.layout.custom_payment_dialog);
        txtTitleDialog1 = dialog.findViewById(R.id.txtTitleDialog1);
        txtTitleDialog2 = dialog.findViewById(R.id.txtTitleDialog2);
        txtTitleDialog3 = dialog.findViewById(R.id.txtTitleDialog3);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnContinue = dialog.findViewById(R.id.btnContinue);

        txtTitleDialog1.setText("สิทธิความคุ้มครองประกัน");
        txtTitleDialog2.setText("สิทธิประกันในการรับบริการได้ภายใต้ วงเงิน 2,000 บาท\n" +
                "\n" +
                "หากคุณยืนยันการนัดหมายแล้ว สิทธิประกันของคุณจะ\n" +
                "ถูกใช้และไม่สามารถขอคืนได้\n" +
                "\n" +
                "เพื่อรักษาสิทธิของคุณโปรดเข้าใช้ บริการปรึกษาแพทย์ตามเวลาที่คุณได้นัดหมายไว้");
        String redString = getResources().getString(R.string.condition_insurance);
        txtTitleDialog3.setText(Html.fromHtml(redString));
        txtTitleDialog3.setOnClickListener(view -> {
            openPopup(R.string.setting_app_term, BuildConfig.SERVER_BASE_INFO + SettingAppFragment.ENDPOINT_TERM);
        });
        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        btnContinue.setOnClickListener(view -> {
            dialog.dismiss();
            onCreateAppointment();
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowManager = window.getAttributes();
        windowManager.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowManager.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(windowManager);
        dialog.show();
    }

    public void showDialogForBuyProduct(){
        Dialog dialog = new Dialog(getContext(), R.style.roundCornerDialog);
        dialog.setContentView(R.layout.custom_payment_dialog);
        txtTitleDialog1 = dialog.findViewById(R.id.txtTitleDialog1);
        txtTitleDialog2 = dialog.findViewById(R.id.txtTitleDialog2);
        txtTitleDialog3 = dialog.findViewById(R.id.txtTitleDialog3);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        btnContinue = dialog.findViewById(R.id.btnContinue);

        txtTitleDialog1.setText("สิทธิความคุ้มครองประกัน");
        txtTitleDialog2.setText("สิทธิประกันในการรับบริการได้ภายใต้ วงเงิน 2,000 บาท\n" +
                "\n" +
                "หากคุณยืนยันการนัดหมายแล้ว สิทธิประกันของคุณจะ\n" +
                "ถูกใช้และไม่สามารถขอคืนได้\n" +
                "\n" +
                "เพื่อรักษาสิทธิของคุณโปรดเข้าใช้ บริการปรึกษาแพทย์ตามเวลาที่คุณได้นัดหมายไว้");
        String redString = getResources().getString(R.string.condition_insurance);
        txtTitleDialog3.setText(Html.fromHtml(redString));
        txtTitleDialog3.setOnClickListener(view -> {
            openPopup(R.string.setting_app_term, BuildConfig.SERVER_BASE_INFO + SettingAppFragment.ENDPOINT_TERM);
        });
        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        btnContinue.setOnClickListener(view -> {
            dialog.dismiss();
            onBuyProduct();
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams windowManager = window.getAttributes();
        windowManager.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowManager.height = WindowManager.LayoutParams.WRAP_CONTENT;
        windowManager.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(windowManager);
        dialog.show();
    }

    private void onCreateAppointment() {
        if (isValid()) {
            if (isPayByCreditCard) {
                if (getCardId() > 0) {
                    getPresenter().createAppointment();
                } else {
                    callOmise(getCreditCard());
                }
            } else if (isPayByBankTransfer) {
                getPresenter().createAppointmentWithSlip(imageSlipUrl);
            } else if (isPayByPromptPay) {
                getPresenter().createAppointmentPormptpay(total_price);
            } else {
                getPresenter().createAppointment();
            }

            FirebaseAddEvents();
        }
    }

    private void onBuyProduct() {
        if (isValid()) {
            if (isPayByCreditCard) {
                if (getCardId() > 0) {
                    getPresenter().buyProduct(getPaymentData());
                } else {
                    callOmise(getCreditCard());
                }
            } else if (isPayByBankTransfer) {
                getPresenter().buyProductWithSlip();
            } else if (isPayByPromptPay) {
                getPresenter().createAppointmentPormptpay(total_price);
            } else {
                if (total_price <= 0) {
                    getPresenter().buyProduct(getPaymentData());
                } else {
                    showMessage(R.string.error_invalid_payment);
                }
            }
        }
    }

    private View.OnClickListener onClickPriceConfirm() {
        return v -> {
            isPriceExpand = !isPriceExpand;
            if (!llo_price_detail.isExpanded()) {
                showPriceDetail();
            } else {
                hidePriceDetail();
            }
        };
    }

    private View.OnClickListener onClickCouponButton() {
        return v -> {
            if (isValidDiscountCode()) {
                getPresenter().checkDiscountCode(getDiscountCode(), getDoctorId(), false, getProductsJson());
            }
        };
    }

    private View.OnClickListener onPaymentCodeConfirmClick() {
        return v -> {
            if (isValidPaymentCode()) {
                getPresenter().checkPaymentCode(getPaymentCode(), getDoctorId(), true, getProductsJson());
            }
        };
    }

    private View.OnClickListener onClickCreditCard() {
        return v -> {
            isPayByCreditCard = true;
            isPayByBankTransfer = false;
            isPayByPromptPay = false;

            llo_credit_card.setVisibility(View.VISIBLE);
            llo_bank_transfer.setVisibility(View.GONE);

            btn_credit_card.setSelected(true);
            btn_bank_transfer.setSelected(false);
            btn_prompt_pay.setSelected(false);

            getPresenter().getListCreditCard();
            //showCreditCardForm();
        };
    }

    private View.OnClickListener onClickBankTransfer() {
        return v -> {
            isPayByCreditCard = false;
            isPayByBankTransfer = true;
            isPayByPromptPay = false;

            llo_credit_card.setVisibility(View.GONE);
            llo_bank_transfer.setVisibility(View.VISIBLE);

            btn_credit_card.setSelected(false);
            btn_bank_transfer.setSelected(true);
            btn_prompt_pay.setSelected(false);
        };
    }

    private View.OnClickListener onClickPromptPay() {
        return v -> {
            isPayByCreditCard = false;
            isPayByBankTransfer = false;
            isPayByPromptPay = true;

            llo_credit_card.setVisibility(View.GONE);
            llo_bank_transfer.setVisibility(View.GONE);

            btn_credit_card.setSelected(false);
            btn_bank_transfer.setSelected(false);
            btn_prompt_pay.setSelected(true);
        };
    }

    public ItemCreditCardDao getCreditCard() {
        ItemCreditCardDao creditCard = new ItemCreditCardDao();
        creditCard.setCreditCardNumber(getCard_number());
        creditCard.setCreditCardName(getCard_name());
        creditCard.setCreditCardExpired(getCard_expired());
        creditCard.setCreditCardExpiredMonth(getCard_expiredMonth());
        creditCard.setCreditCardExpiredYear(getCard_expiredYear());
        creditCard.setCreditCardCVV(getCard_cvv());
        return creditCard;
    }

    @Override
    public PaymentDao getPaymentData() {
        paymentData = new PaymentDao();
        paymentData.setDiscountCode(getDiscountCode());
        paymentData.setPaymentCode(getPaymentCode());
        paymentData.setShippingLocation(getShippingAddress());
        paymentData.setShippingName(getShippingName());
        paymentData.setContactNo(getShippingPhone());
        return paymentData;
    }

    @Override
    public ApiAppointmentRequest getAppointment() {
        appointmentData.setDiscountCode(getDiscountCode());
        appointmentData.setPaymentCode(getPaymentCode());
        appointmentData.setOmiseToken(getOmiseToken());
        appointmentData.setFingerprint(getOmiseFingerprint());
        appointmentData.setRememberCard(isRememberSelect);
        appointmentData.setCardId(getCardId());
        appointmentData.setSlipUrl(imageSlipUrl);
        return appointmentData;
    }

    @Override
    public ApiProductRequest getProductsRequest() {
        paymentData = getPaymentData();
        ApiProductRequest data = new ApiProductRequest();

        data.setPatientId(AppManager.getDataManager().getPatientId());

        String products = new Gson().toJson(getProducts());
        data.setProduct(products);

        data.setAppointmentNumber(getAppointmentNumber());

        data.setDiscountCode(paymentData.getDiscountCode());
        data.setPaymentCode(paymentData.getPaymentCode());
        data.setPaymentCode(paymentData.isPaymentCode());
        data.setShippingLocations(paymentData.getShippingLocation());
        data.setName(paymentData.getShippingName());
        data.setContactNo(paymentData.getContactNo());

        data.setOmiseToken(getOmiseToken());
        data.setFingerprint(getOmiseFingerprint());
        data.setRememberCard(isRememberSelect);
        data.setCardId(getCardId());

        data.setLatitude(latitude);
        data.setLongtitude(longtitude);
        data.setSlipUrl(imageSlipUrl);
        showLogProduct(data);
        return data;
    }


    private void showLogProduct(ApiProductRequest data) {
        if (!BuildConfig.DEBUG) return;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        Timber.e("Request Product data %s", gson.toJson(data));
    }

    public String getDataFromFile(String filename) {
        return new LoadData(requireActivity()).loadJSONFromAsset(filename);
    }

    public List<ItemPersonDao> getData(String json) {
        Gson gson = new Gson();
        ListPersonDao data = gson.fromJson(json, ListPersonDao.class);
        return data.getListData();
    }

    private void updateView() {
        updatePrice();
    }

    @SuppressLint("SetTextI18n")
    private void updatePrice() {
        total_price = getPaymentAmount() + getShippingFee() - getDiscountPrice();
        if (total_price <= 0) {
            total_price = 0;
            llo_payment.setVisibility(View.GONE);
            llo_bank_transfer.setVisibility(View.GONE);
            llo_credit_card.setVisibility(View.GONE);
            btnDone.setEnabled(true);
        } else {
            llo_payment.setVisibility(View.VISIBLE);
            btnDone.setEnabled(false);
        }
        tv_summaryPrice.setText(AmountUtils.decimalFormat(total_price) + " " + getCurrencySymbol());

        showPrice();
        if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
            priceVat();
            llo_priceVat.setVisibility(View.VISIBLE);
        } else {
            updateShippingFee(getShippingFee());
        }

        showDiscountPrice();
        showSummaryPrice();

        if (isSold()) {
            llo_payment.setVisibility(View.GONE);
            layout_appointment_condition.setVisibility(View.GONE);
            layout_shipping_address.setVisibility(View.GONE);
            layout_coupon.setVisibility(View.GONE);

            llo_bank_transfer.setVisibility(View.GONE);
            llo_credit_card.setVisibility(View.GONE);
            btnDone.setVisibility(View.INVISIBLE);
        }
    }

    private double getShowPrice() {
        if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
            return getDoctorPrice();
        } else {
            return getProductPrice();
        }
    }

    private double getProductPrice() {
        return getPaymentAmount();
    }

    private String getPriceTitle() {
        if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
            return getString(R.string.price_title_doctor);
        } else {
            return getString(R.string.product_confirm_price);
        }
    }

    private String getFeeTitle() {
        if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
            return getString(R.string.price_title_fee);
        } else {
            return getString(R.string.product_shipping);
        }
    }

    private double getFee() {
        if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
            return getPaymentAmount() - getDoctorPrice();
        } else {
            return 0;
        }
    }

    @SuppressLint("SetTextI18n")
    private void showPrice() {
        TextView tv_title = llo_priceNoVat.findViewById(R.id.tv_title);
        TextView tv_price = llo_priceNoVat.findViewById(R.id.tv_price);

        tv_title.setText(getPriceTitle());
        tv_price.setText(AmountUtils.decimalFormat(getShowPrice()) + " " + getCurrencySymbol());
    }

    @SuppressLint("SetTextI18n")
    private void priceVat() {
        TextView tv_title = llo_priceVat.findViewById(R.id.tv_title);
        TextView tv_price = llo_priceVat.findViewById(R.id.tv_price);
        tv_title.setText(getFeeTitle());
        tv_price.setText(AmountUtils.decimalFormat(getFee()) + " " + getCurrencySymbol());
    }

    @SuppressLint("SetTextI18n")
    private void updateShippingFee(double price) {
        TextView tv_title = llo_priceVat.findViewById(R.id.tv_title);
        TextView tv_price = llo_priceVat.findViewById(R.id.tv_price);
        tv_title.setText(getFeeTitle());
        tv_price.setText(AmountUtils.decimalFormat(price) + " " + getCurrencySymbol());
    }

    @SuppressLint("SetTextI18n")
    private void showDiscountPrice() {
        TextView tv_title = llo_discountPrice.findViewById(R.id.tv_title);
        TextView tv_price = llo_discountPrice.findViewById(R.id.tv_price);
        tv_title.setText(getString(R.string.price_title_discount));
        tv_price.setText(AmountUtils.decimalFormat(getDiscountPrice()) + " " + getCurrencySymbol());
    }

    @SuppressLint("SetTextI18n")
    private void showSummaryPrice() {
        String price = AmountUtils.decimalFormat(total_price) + " " + getCurrencySymbol();
        TextView tv_title = llo_summaryPrice.findViewById(R.id.tv_title);
        TextView tv_price = llo_summaryPrice.findViewById(R.id.tv_price);
        tv_title.setText(getString(R.string.price_title_total));
        tv_price.setText(price);
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
    public void openPaymentChannel() {
        MyDialog myDialog = new MyDialog(requireActivity());
        myDialog.showPaymentChannel("170", "THB", new MyDialog.OnPaymentListener() {
            @Override
            public void onCreditCard() {
                Timber.i("paid by credit card");
                openPaymentCreditCard();
            }

            @Override
            public void onBankTranfer() {
                Timber.i("paid by bank transfer");
                openPaymentBankTransfer();
            }
        });
    }

    @Override
    public void openPaymentCreditCard() {
        openPaymentBankTransfer();
    }

    @Override
    public void openPaymentBankTransfer() {
        //load data bank await
        List<BankAccountObject> bankList = new ArrayList<>();
        BankAccountObject b1 = new BankAccountObject();
        b1.setIcon("http://apps.softsquaregroup.com/ChiiwiiBO/Warehouse/Gallery/ic_bank_kbank.png");
        b1.setBankName("KBANK");
        b1.setAccountNumber("025-3-50878-7");

        BankAccountObject b3 = new BankAccountObject();
        b3.setIcon("http://apps.softsquaregroup.com/ChiiwiiBO/Warehouse/Gallery/ic_bank_bbl.png");
        b3.setBankName("BBL");
        b3.setAccountNumber("045-3-50878-7");

        BankAccountObject b2 = new BankAccountObject();
        b2.setIcon("http://apps.softsquaregroup.com/ChiiwiiBO/Warehouse/Gallery/ic_bank_scb.png");
        b2.setBankName("SCB");
        b2.setAccountNumber("088-3-50878-7");

        bankList.add(b1);
        bankList.add(b3);
        bankList.add(b2);

        PaymentBankTransferDialog paymentBankTransfer = new PaymentBankTransferDialog(requireActivity(), "170", "THB", bankList);
        paymentBankTransfer.showDialog();
        paymentBankTransfer.setOnConfirmListener(code -> {
            //openAppointmentSuccess(data);
        });
    }

    @Override
    public void openAppointmentSuccess(ItemDoctorDao data, ApiAppointmentRequest appointmentData, double discountPrice) {
        ((MainActivity) requireActivity()).openAppointmentSuccess(data, appointmentData, originDiscountPrice);
    }

    private void setConditionClickableSpan() {
        tv_condition.setText(SpannableText.setClickableSpan(getContext(),
                R.string.appointment_confirm_condition,
                R.string.appointment_confirm_condition_link,
                textView -> openPopup(R.string.setting_app_term, BuildConfig.SERVER_BASE_INFO + SettingAppFragment.ENDPOINT_TERM)));
        tv_condition.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void openPopup(int resIdTitle, String url) {
        String title = getString(resIdTitle);
        new MyDialog(getContext()).showWebview(title, url, new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    @Override
    public void onErrorDiscountCode() {
        edit_discount_code.setError(getString(R.string.error_invalid_discount));
        edit_discount_code.requestFocus();
    }

    @Override
    public void onErrorPaymentCode() {
        edit_payment_code.setError(getString(R.string.error_invalid_payment_code));
        edit_payment_code.requestFocus();
    }

    private void onErrorAttachFile() {
        showMessage(R.string.error_invalid_bank_attach_file);
    }

    @Override
    public void onErrorCardNumber() {
        ed_card_number.setError(getString(R.string.error_invalid_card_number));
        ed_card_number.requestFocus();
    }

    @Override
    public void onErrorCardName() {
        ed_card_name.setError(getString(R.string.error_invalid_card_name));
        ed_card_name.requestFocus();
    }

    @Override
    public void onErrorCardExpired() {
    }

    @Override
    public void onErrorCardExpiredMonth() {
        ed_card_expired_month.setError(getString(R.string.error_invalid_card_expired_month));
        ed_card_expired_month.requestFocus();
    }

    @Override
    public void onErrorCardExpiredYear() {
        ed_card_expired_year.setError(getString(R.string.error_invalid_card_expired_year));
        ed_card_expired_year.requestFocus();
    }

    @Override
    public void onErrorCardCVV() {
        ed_card_cvv.setError(getString(R.string.error_invalid_card_cvv));
        ed_card_cvv.requestFocus();
    }

    @Override
    public void onErrorShippingAddress() {
        showMessage(R.string.error_invalid_shipping_address);
    }

    @Override
    public void onErrorShippingName() {
        edit_shipping_name.setError(getString(R.string.error_invalid_shipping_name));
        edit_shipping_name.requestFocus();
    }

    @Override
    public void onErrorShippingPhone() {
        edit_shipping_phone.setError(getString(R.string.error_invalid_shipping_phone));
        edit_shipping_phone.requestFocus();
    }

    @Override
    public boolean isValidDiscountCode() {
        if (getDiscountCode().isEmpty()) {
            onErrorDiscountCode();
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidPaymentCode() {
        if (isAttachFile) {
            return true;
        } else {
            onErrorAttachFile();
            return false;
        }
    }

    @Override
    public boolean isValidCreditCard() {

        if (getCard_number().length() != 16) {
            onErrorCardNumber();
            return false;
        }

        if (getCard_name().length() < 3) {
            onErrorCardName();
            return false;
        }

        if (ed_card_expired_month.getText().length() < 2) {
            onErrorCardExpiredMonth();
            return false;
        }

        if (ed_card_expired_year.getText().length() < 4) {
            onErrorCardExpiredYear();
            return false;
        }
        if (ed_card_cvv.getText().length() < 3) {
            onErrorCardCVV();
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidShipping() {
        if (getShippingName().length() < 4) {
            onErrorShippingName();
            return false;
        }

        if (getShippingPhone().length() < 7) {
            onErrorShippingPhone();
            return false;
        }

        if (getShippingAddress().length() < 4) {
            onErrorShippingAddress();
            return false;
        }

        return true;
    }

    @Override
    public boolean isValid() {
        boolean result = true;
        if (payment_mode == PAYMENT_MODE_PRODUCT) {
            result = isValidShipping();
        }

        if (result) {
            if (isPayByCreditCard) {
                if (getCardId() > 0) {
                    result = true;
                } else {
                    result = isValidCreditCard();
                }
            }
            if (isPayByBankTransfer) {
                result = isValidPaymentCode();
            }
        }

        return result;
    }

    @Override
    public int getPaymentMode() {
        return getArguments().getInt(KEY_PAYMENT_MODE);
    }

    @Override
    public double getPaymentAmount() {
        return getArguments().getDouble(KEY_PAYMENT_AMOUNT, 0);
    }

    @Override
    public int getDoctorId() {
        return getArguments().getInt(KEY_DOCTOR_ID);
    }

    private String getDoctorName() {
        return getArguments().getString(KEY_DOCTOR_NAME);
    }

    private String getCouponCode() {
        return getArguments().getString(KEY_COUPON_CODE);
    }

    @Override
    public double getDoctorPrice() {
        return getArguments().getDouble(KEY_DOCTOR_PRICE, 0);
    }

    @Override
    public String getDiscountCode() {
        return edit_discount_code.getText().toString().trim();
    }

    @Override
    public String getPaymentCode() {
        return edit_payment_code.getText().toString().trim();
    }

    @Override
    public String getCurrencySymbol() {
        return getString(R.string.money_baht);
    }

    @Override
    public double getDiscountPrice() {
        return discountPrice;
    }

    @Override
    public double getPriceWithFee() {
        return 0;
    }

    @Override
    public String getOmiseToken() {
        return omiseToken;
    }

    private String getOmiseFingerprint() {
        return omiseFingerprint;
    }

    private int getCardId() {
        return cardId;
    }

    @Override
    public void setDiscountPrice(double discountPrice) {
        if (discountPrice > getPaymentAmount()) {
            discountPrice = getPaymentAmount();
        }

        this.discountPrice = discountPrice;
        updatePrice();
    }

    @Override
    public void onDiscountSuccess(double discountPrice) {
        originDiscountPrice = discountPrice;
        setDiscountPrice(discountPrice);
    }

    @Override
    public void onDiscountNotSuccess() {
        originDiscountPrice = 0;
        setDiscountPrice(0);
    }

    private int getProductId() {
        return getArguments().getInt(KEY_PRODUCT_ID);
    }

    @Override
    public List<ItemProductDao> getProducts() {
        return listProducts;
    }

    @Override
    public String getProductsJson() {
        return new Gson().toJson(getProducts());
    }

    @Override
    public String getShippingAddress() {
        return tv_shipping_address.getText().toString();
    }

    @Override
    public String getShippingName() {
        return edit_shipping_name.getText().toString();
    }

    @Override
    public String getShippingPhone() {
        return edit_shipping_phone.getText().toString();
    }

    @Override
    public String getCard_number() {
        return ed_card_number.getText().toString();
    }

    @Override
    public String getCard_name() {
        return ed_card_name.getText().toString();
    }

    @Override
    public String getCard_expired() {
        return ""; //ed_card_expired.getText().toString();
    }

    @Override
    public int getCard_expiredMonth() {
        return Integer.parseInt(ed_card_expired_month.getText().toString());
    }

    @Override
    public int getCard_expiredYear() {
        return Integer.parseInt(ed_card_expired_year.getText().toString());
    }

    @Override
    public String getCard_cvv() {
        return ed_card_cvv.getText().toString();
    }

    private String SerializedMedicineAllergy() {
        List<String> listMedicineAllergy = new ArrayList<>();
        listMedicineAllergy.add("ยา 1");
        listMedicineAllergy.add("ยา 2");

        return new Gson().toJson(listMedicineAllergy);
    }

    //..omise
    private static final String OMISE_PKEY = BuildConfig.OMISE_PKEY;
    private static final int REQUEST_CC = 100;

    private void showCreditCardForm() {
        Intent intent = new Intent(requireActivity(), CreditCardActivity.class);
        intent.putExtra(CreditCardActivity.EXTRA_PKEY, OMISE_PKEY);
        startActivityForResult(intent, REQUEST_CC);
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void callOmise(final ItemCreditCardDao creditCard) {
        showLoading();
        TokenRequest request = new TokenRequest();
        request.number = creditCard.getCreditCardNumber(); // "4242424242424242";
        request.name = creditCard.getCreditCardName(); // "JOHN SMITH";
        request.expirationMonth = creditCard.getCreditCardExpiredMonth(); // 10;
        request.expirationYear = creditCard.getCreditCardExpiredYear(); // 2020;
        request.securityCode = creditCard.getCreditCardCVV(); // "123";

        try {
            Client client = new Client(OMISE_PKEY);
            client.send(request, new TokenRequestListener() {
                @Override
                public void onTokenRequestSucceed(TokenRequest tokenRequest, Token token) {
                    hideLoading();
                    omiseToken = token.id;
                    Timber.i("callOmise onTokenRequestSucceed token => %s", omiseToken);
                    omiseFingerprint = token.card.fingerprint;
                    onOmiseGetTokenSuccess(token.id);
                }

                @Override
                public void onTokenRequestFailed(TokenRequest tokenRequest, Throwable throwable) {
                    Timber.e("callOmise onTokenRequestFailed => %s", throwable.getMessage());
                    hideLoading();
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            hideLoading();
        }
    }

    private void onOmiseGetTokenSuccess(String omiseToken) {
        if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
            getPresenter().createAppointment();
        } else {
            getPresenter().buyProduct(getPaymentData());
        }
    }

    @Override
    public void onฺBuyProductSuccess(String aurhorizeUrl) {
        if (isPayByCreditCard || isPayByPromptPay) {
            showAuthorize(aurhorizeUrl);
        } else {
            showBuyProductSuccess();
        }
    }

    @Override
    public void onฺCreateAppointmentSuccess(String aurhorizeUrl, String appointmentNumber) {
        appointmentData.setAppointmentNumber(appointmentNumber);
        if (isPayByCreditCard || isPayByPromptPay) {
            showAuthorize(aurhorizeUrl);
        } else {
            showCreateAppountmentSuccess();
        }
    }

    @Override
    public void showBuyProductSuccess() {
        showSuccess(getString(R.string.product_buy_success));
    }

    @Override
    public void showCreateAppountmentSuccess() {
        if (onCallback != null) {
            onCallback.onCreateAppointmentSuccess(appointmentData, getDiscountPrice());
        }
        addToCalendar();
    }

    private void addToCalendar() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        // String appointmentTime = "2020-03-03T16:00:00"; // => yyyy-MM-dd'T'HH:mm:ss
        String appointmentTime = appointmentData.getAppointmentTime();
        long startDate = ConvertDate.dateToMillis(appointmentTime);
        long endDate = startDate + (1000 * 15 * 60);

        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", startDate);
        intent.putExtra("endTime", endDate);
        intent.putExtra("title", getString(R.string.appointment_time));
        String toTime = ConvertDate.StringDateServiceFormatTime(appointmentTime);
        intent.putExtra("description", getString(R.string.appointment_time) + " " + toTime);
        intent.putExtra("hasAlarm", 1);
        startActivity(intent);
    }

    @Override
    public void showSuccess(String message) {
        final MyDialog myDialog = new MyDialog(requireActivity());
        myDialog.showSuccess(message, new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                requireActivity().onBackPressed();
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    @Override
    public void onAuthorizeSuccess() {
        if (payment_mode == PAYMENT_MODE_APPOINTMENT) {
            showCreateAppountmentSuccess();
        } else {
            showBuyProductSuccess();
        }
    }

    @Override
    public void onAuthorizeFail() {
    }

    @Override
    public void showAuthorize(String aurhorizeUrl) {
        Timber.e("showAuthorize: %s", aurhorizeUrl);
        String[] expect_url = {"http", "https"};
        Intent intent = new Intent(requireActivity(), PaymentAuthorizeActivity.class);
        intent.putExtra(PaymentAuthorizeActivity.EXTRA_AUTHORIZED_URL, aurhorizeUrl);
        intent.putExtra(AuthorizingPaymentActivity.EXTRA_EXPECTED_RETURN_URLSTRING_PATTERNS, expect_url);
        requireActivity().startActivityForResult(intent, AppConstants.REQUEST_CODE_AUTHORIZE_PAYMENT);
    }

    @Override
    public void showAuthorizingPayment(String aurhorizeUrl) {
        Timber.e("showAuthorizingPayment: %s", aurhorizeUrl);
        String[] expect_url = {"http", "https"};
        Intent intent = new Intent(requireActivity(), AuthorizingPaymentActivity.class);
        intent.putExtra(AuthorizingPaymentActivity.EXTRA_AUTHORIZED_URLSTRING, aurhorizeUrl);
        intent.putExtra(AuthorizingPaymentActivity.EXTRA_EXPECTED_RETURN_URLSTRING_PATTERNS, expect_url);
        requireActivity().startActivityForResult(intent, AppConstants.REQUEST_CODE_AUTHORIZE_PAYMENT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CC:
                if (resultCode == RESULT_CANCELED) {
                    return;
                }

                Token token = data.getParcelableExtra(CreditCardActivity.EXTRA_TOKEN_OBJECT);
                // process your token here.
                omiseToken = token.id;
                Timber.i("EXTRA_TOKEN_OBJECT token: %s", omiseToken);
                break;

            case AppConstants.REQUEST_CODE_AUTHORIZE_PAYMENT:
                if (resultCode == RESULT_OK) {
                    onAuthorizeSuccess();
                } else {
                    onAuthorizeFail();
                }
                break;

            case REQUEST_ADDRESS:

                if (resultCode == RESULT_OK) {
                    double latitude = data.getDoubleExtra(AddressActivity.KEY_LATITUDE, 0.00);
                    double longtitude = data.getDoubleExtra(AddressActivity.KEY_LONGTITUDE, 0.00);
                    String address = data.getStringExtra(AddressActivity.KEY_SHIPADDRESS);
                    Timber.i("latitude %s, longtitude: %s", latitude, longtitude);
                    onShipAddressSuccess(latitude, longtitude, address);
                }
                break;

            case AppConstants.REQUEST_PICK_IMAGE:
                isAttachFile = false;
                if (resultCode == RESULT_OK) {
                    if (data.getData() != null) {
                        uriImage = data.getData();
                        uploadSlip();
                    }
                }
                break;
            case AppConstants.REQUEST_CAMERA:
                isAttachFile = false;
                if (resultCode == RESULT_OK) {
                    uriImage = Camera.moveFile(getContext());
                    uploadSlip();
                }
                break;
        }
    }

    private void onShipAddressSuccess(double latitude, double longtitude, String address) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        tv_shipping_address.setText(address);
        edit_shipping_address.setText(address);
        getPresenter().getShippingFee(getAppointmentNumber(), latitude, longtitude);
    }

    @Override
    public String getAppointmentNumber() {
        return getArguments().getString(AppConstants.EXTRA_APPOINTMENT_NUMBER);
    }

    @Override
    public void setShippingFee(double fee) {
        shippingFee = fee;
        updateShippingFee(fee);
        updatePrice();
    }

    private int getShippingFee() {
        return (int) shippingFee;
    }

    @Override
    public void showListCreditCard(List<ItemListCreditCardDao> listData) {
        adapter.setListData(listData);
    }

    @Override
    public void removeCreditCardSuccess(int position) {
        adapter.removeData(position);
    }

    private boolean isSold() {
        return getArguments().getBoolean(KEY_ISSOLD);
    }

    private void pickImage() {
        final MediaBottomSheetFragment mediaBottomSheet = MediaBottomSheetFragment.newInstance((int) CommonUtils.convertDpToPixel(requireActivity(), 200));
        mediaBottomSheet.show(requireActivity().getSupportFragmentManager(), mediaBottomSheet.getTag());
        mediaBottomSheet.setOnClickListener(data -> {
            if (data.getMediaId() == AppConstants.REQUEST_PICK_IMAGE) {
                mediaBottomSheet.dismiss();
                PickFile.pickImageIntentWithPermission(requireActivity(), fragment, AppConstants.REQUEST_PICK_IMAGE);

            } else if (data.getMediaId() == AppConstants.REQUEST_CAMERA) {
                mediaBottomSheet.dismiss();
                Camera.takePhotosIntentWithPermission(requireActivity(), fragment, AppConstants.REQUEST_CAMERA);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstants.REQUEST_PICK_IMAGE_IMAGE_WRITE_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_PICK_IMAGE_IMAGE_READ_EXTERNAL_STORAGE:
                PickFile.pickImageIntentWithPermission(requireActivity(), fragment, AppConstants.REQUEST_PICK_IMAGE);
                break;
            case AppConstants.REQUEST_CAMERA_READ_WRITE_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_CAMERA_READ_READ_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_CAMERA:
                Camera.takePhotosIntentWithPermission(requireActivity(), fragment, AppConstants.REQUEST_CAMERA);
                break;
        }
    }

    private void uploadSlip() {
        getPresenter().uploadSlip();
    }

    @Override
    public MultipartBody.Part getImageBody() {
        return addMultipartBody(requireActivity(), "SlipImage", uriImage);
    }

    @Override
    public void uploadSlipSuccess(String slipUrl) {
        isAttachFile = true;
        imageSlipUrl = slipUrl;
        showImage(slipUrl);
    }

    @Override
    public void openPromptpayActivityt(AppointmentUiModel uiModel) {
        PromptpayActivity.startIntentForResult(requireActivity(), uiModel);
    }

    private void hideImage() {
        Glide.with(requireActivity()).asBitmap()
                .load("")
                .into(imgAttach);
    }

    private void showImage(String imageUrl) {
        Glide.with(requireActivity()).asBitmap()
                .load(uriImage)
                .into(imgAttach);
    }

    private void FirebaseAddEventsBeginCheckout() {
        Bundle items = new Bundle();
        items.putString(FirebaseAnalytics.Param.ITEM_ID, "" + getDoctorId());
        items.putString(FirebaseAnalytics.Param.ITEM_NAME, getDoctorName());
        items.putDouble(FirebaseAnalytics.Param.PRICE, getDoctorPrice());
        items.putString(FirebaseAnalytics.Param.CURRENCY, "THB");
        items.putDouble(FirebaseAnalytics.Param.QUANTITY, 1);

        Bundle bundle = new Bundle();
        bundle.putBundle("items", items);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, bundle);
    }

    private void FirebaseAddEvents() {
        String paymentType = "COUPON";
        if (isPayByCreditCard) {
            paymentType = "CREDIT_CARD";
        } else if (isPayByBankTransfer) {
            paymentType = "BANK";
        } else if (isPayByPromptPay) {
            paymentType = "PROMPTPAY";
        }

        Bundle items = new Bundle();
        items.putString(FirebaseAnalytics.Param.ITEM_ID, "" + getDoctorId());
        items.putString(FirebaseAnalytics.Param.ITEM_NAME, getDoctorName());
        items.putDouble(FirebaseAnalytics.Param.PRICE, total_price);
        items.putString(FirebaseAnalytics.Param.CURRENCY, "THB");
        items.putDouble(FirebaseAnalytics.Param.QUANTITY, 1);
        items.putString(FirebaseAnalytics.Param.PAYMENT_TYPE, paymentType);
        items.putString(FirebaseAnalytics.Param.COUPON, getCouponCode());

        Bundle bundle = new Bundle();
        bundle.putBundle("items", items);
        bundle.putString(FirebaseAnalytics.Param.PAYMENT_TYPE, paymentType);
        bundle.putString(FirebaseAnalytics.Param.COUPON, getCouponCode());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO, bundle);
        FirebaseAddEventsPurchase();
    }

    private void FirebaseAddEventsPurchase() {
        Bundle items = new Bundle();
        items.putString(FirebaseAnalytics.Param.ITEM_ID, "" + getDoctorId());
        items.putString(FirebaseAnalytics.Param.ITEM_NAME, getDoctorName());
        items.putDouble(FirebaseAnalytics.Param.PRICE, total_price);
        items.putString(FirebaseAnalytics.Param.CURRENCY, "THB");
        items.putDouble(FirebaseAnalytics.Param.QUANTITY, 1);

        Bundle bundle = new Bundle();
        bundle.putBundle("items", items);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE, bundle);
    }
}
