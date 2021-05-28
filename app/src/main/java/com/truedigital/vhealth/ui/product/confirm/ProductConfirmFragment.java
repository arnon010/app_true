package com.truedigital.vhealth.ui.product.confirm;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BankAccountObject;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.ListProductDao;
import com.truedigital.vhealth.model.PaymentDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.ui.adapter.ListProductAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.dialog.PaymentBankTransferDialog;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.payment.PaymentAuthorizeActivity;
import com.truedigital.vhealth.ui.payment.PaymentFragment;
import com.truedigital.vhealth.utils.LoadData;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import co.omise.android.ui.AuthorizingPaymentActivity;

public class ProductConfirmFragment extends BaseMvpFragment<ProductConfirmFragmentInterface.Presenter>
        implements ProductConfirmFragmentInterface.View{

    public static final String TAG = "ProductConfirmFragment";
    private static final String KEY_PRODUCT_ID = "PRODUCT_ID";
    private static final int AUTHORIZING_PAYMENT_REQUEST_CODE = 10;
    private RecyclerView recyclerView;
    private ListProductAdapter adapter;
    private int position;
    private int id;
    private TextView tvTitle;
    private ItemProductDao data;
    private TextView tvSubTitle;
    private TextView tvDescription;
    private TextView tvPrice;
    private Button btnPayment;
    private ImageView imgProfile;
    private TextView tvNormalPrice;

    private int selling_price;
    private String omiseToken;
    private EditText edit_shipping_name;
    private EditText edit_shipping_address;
    private LinearLayout llo_credit_card;
    private PaymentDao paymentData = new PaymentDao();

    public static ProductConfirmFragment newInstance(int id) {
        ProductConfirmFragment fragment = new ProductConfirmFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_ID,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public ProductConfirmFragmentInterface.Presenter createPresenter(){
        return ProductConfirmFragmentPresenter.create();
    }

    @Override
    public int getLayoutView(){
        return R.layout.fragment_product_confirm;
    }

    @Override
    public void bindView( View view ){
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvSubTitle = (TextView) view.findViewById(R.id.tvSubTitle);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        tvNormalPrice = (TextView) view.findViewById(R.id.tvNormalPrice);
    }

    @Override
    public void setupInstance(){

    }


    private void setupPayment(int amount) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragmentPayment = PaymentFragment.newInstance(PaymentFragment.PAYMENT_MODE_PRODUCT, getProducts(), amount, "",false);

        if(fragmentPayment != null) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.content_payment, fragmentPayment);
            ft.commitAllowingStateLoss();
        }

        ((PaymentFragment) fragmentPayment).setOnCallback(new PaymentFragment.OnCallback() {
            @Override
            public void onBuyProductSuccess(PaymentDao data) {
                Log.e(TAG,"Callback Payment Click !!!!");
                onPayment(paymentData);
            }

            @Override
            public void onCreateAppointmentSuccess(ApiAppointmentRequest appointmentRequest, double discountPrice) {

            }
        });

    }


    private void onPayment(PaymentDao paymentData) {
        /*
        if (paymentData.isPayByCreditCard()) {
            callOmise(paymentData);
        }
        else {
            getPresenter().onPaymentButtonClick(paymentData);
        }
        */
    }


    //..omise
    private static final String OMISE_PKEY = "pkey_test_5ccbsnxpx7om0sw2nq1";
    private static final int REQUEST_CC = 100;
    @Override
    public void callOmise(final PaymentDao paymentData) {
        /*
        TokenRequest request = new TokenRequest();
        request.number = paymentData.getCreditCardNumber(); //"4242424242424242";
        request.name = paymentData.getCreditCardName(); //"JOHN SMITH";
        request.expirationMonth = 10;
        request.expirationYear = 2020;
        request.securityCode = paymentData.getCreditCardCVV(); //"123";

        try {
            Client client = new Client(OMISE_PKEY);
            client.send(request, new TokenRequestListener() {
                @Override
                public void onTokenRequestSucceed(TokenRequest tokenRequest, Token token) {
                    omiseToken = token.id;
                    Log.e("Omise "," token" + omiseToken);

                    getPresenter().onPaymentButtonClick(paymentData);

                }

                @Override
                public void onTokenRequestFailed(TokenRequest tokenRequest, Throwable throwable) {
                    Log.e("Omise ","fail");
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        */

    }


    @Override
    public void setupView(){
        id = getArguments().getInt(KEY_PRODUCT_ID,0);
        showToolbar();
        getPresenter().loadData();
    }

    private void showToolbar() {
        ((MainActivity) getActivity()).showToolbar(R.string.product_cart_title,true);
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
    public int getProductId() {
        return id;
    }

    @Override
    public String getOmiseToken() {
        return omiseToken;
    }

    @Override
    public void setData(List<ItemProductDao> listData) {
        //adapter.setListData(listData);
    }

    @Override
    public void setData(ItemProductDao data) {
        this.data = data;
        updateView(data);
    }

    private void updateView(ItemProductDao data) {

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

        if (data.getPromotionSellingPrice() != 0) {
            tvPrice.setText(""+data.getPromotionSellingPrice());
            tvNormalPrice.setText(""+data.getNormalSellingPrice());
            StringUtils.setStrikeline(tvNormalPrice);
            selling_price = (int) data.getPromotionSellingPrice();
        }
        else {
            tvPrice.setText(""+data.getNormalSellingPrice());
            tvNormalPrice.setText("");
            selling_price = (int) data.getNormalSellingPrice();
        }

        setupPayment(selling_price);
    }

    @Override
    public void openListProducts(int id) {

    }

    @Override
    public List<ItemProductDao> getProducts() {
        List<ItemProductDao> listProducts = new ArrayList<>();
        ItemProductDao item = new ItemProductDao();
        item.setProductId( getProductId());
        item.setQuantity(1);
        listProducts.add(item);
        return listProducts;
    }

    @Override
    public String getShippingName() {
        return ""; //edit_shipping_name.getText().toString();
    }

    @Override
    public String getShippingAddress() {
        return "";//edit_shipping_address.getText().toString();
    }

    @Override
    public String getDataFromFile(String filename) {
        String data = new LoadData(getActivity()).loadJSONFromAsset(filename);
        return data;
    }

    @Override
    public List<ItemProductDao> getData(String json, boolean isShowLog) {
        List<ItemProductDao> listData = new ArrayList<>();

        Gson gson = new Gson();
        ListProductDao data = gson.fromJson(json, ListProductDao.class);
        listData = data.getListData();

        if (isShowLog) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            gson = gsonBuilder.create();
            Log.d("", "load data" + gson.toJson(data));
        }
        return listData;
    }

    @Override
    public void openProductConfirm(int id) {
        ((MainActivity)getActivity()).openProductConfirm(id);
    }

    @Override
    public void openPaymentChannel() {
        MyDialog myDialog = new MyDialog(getActivity());
        myDialog.showPaymentChannel("170", "THB", new MyDialog.OnPaymentListener() {
            @Override
            public void onCreditCard() {
                Log.e(TAG,"paid by credit card");
                openPaymentCreditCard();
            }

            @Override
            public void onBankTranfer() {
                Log.e(TAG,"paid by bank transfer");
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

        PaymentBankTransferDialog paymentBankTransfer = new PaymentBankTransferDialog(getActivity(), "170", "THB", bankList);
        paymentBankTransfer.showDialog();
        paymentBankTransfer.setOnConfirmListener(new PaymentBankTransferDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String code) {
                //openAppointmentSuccess(data);
                openSendAddress();
            }
        });
    }

    @Override
    public void openSendAddress() {
        new MyDialog(getContext()).showSendAddress(new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {

            }

            @Override
            public void onClickCancel() {

            }
        });
    }

    @Override
    public void showSuccess(final String AurhorizeUri) {
        final MyDialog myDialog = new MyDialog(getActivity());
        myDialog.showSuccess(getString(R.string.product_buy_success), new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {

                //showAuthorize(AurhorizeUri);
                showAuthorizingPaymentForm(AurhorizeUri);

                //((MainActivity)getActivity()).onBackPressed();
            }

            @Override
            public void onClickCancel() {

            }
        });
    }

    private void showAuthorize(String AUTHORIZED_URL){
        Log.e("Omise Authorize", ""+ AUTHORIZED_URL);
        Intent intent = new Intent(getActivity(), PaymentAuthorizeActivity.class);
        intent.putExtra(PaymentAuthorizeActivity.EXTRA_AUTHORIZED_URL,AUTHORIZED_URL);
        getActivity().startActivityForResult(intent, AUTHORIZING_PAYMENT_REQUEST_CODE);
    }

    private void showAuthorizingPaymentForm(String AUTHORIZED_URL) {
        Log.e("Omise Authorize", ""+ AUTHORIZED_URL);
        String[] expect_url = {"http","https"};
        Intent intent = new Intent(getActivity(), AuthorizingPaymentActivity.class);
        intent.putExtra(AuthorizingPaymentActivity.EXTRA_AUTHORIZED_URLSTRING, AUTHORIZED_URL);
        intent.putExtra(AuthorizingPaymentActivity.EXTRA_EXPECTED_RETURN_URLSTRING_PATTERNS, expect_url );
        getActivity().startActivityForResult(intent, AUTHORIZING_PAYMENT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Omise return","" );
    }

}

