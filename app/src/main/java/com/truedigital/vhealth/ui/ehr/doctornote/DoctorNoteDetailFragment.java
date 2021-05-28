package com.truedigital.vhealth.ui.ehr.doctornote;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BankAccountObject;
import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.model.RecommendMedicationObject;
import com.truedigital.vhealth.model.RecommendProductObject;
import com.truedigital.vhealth.model.ShortNoteAttachmentObject;
import com.truedigital.vhealth.ui.adapter.ListImageAdapter;
import com.truedigital.vhealth.ui.adapter.ListRecommendMedicationAdapter;
import com.truedigital.vhealth.ui.adapter.ListRecommendProductAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.decoration.ListDividerItemDecoration;
import com.truedigital.vhealth.ui.dialog.ConfirmDialog;
import com.truedigital.vhealth.ui.dialog.DeliveryDetailDialog;
import com.truedigital.vhealth.ui.dialog.PaymentBankTransferDialog;
import com.truedigital.vhealth.ui.dialog.PaymentChannelDialog;
import com.truedigital.vhealth.ui.dialog.ShowImageDialog;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.SpacesItemDecoration;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

public class DoctorNoteDetailFragment extends BaseMvpFragment<DoctorNoteDetailFragmentInterface.Presenter>
        implements DoctorNoteDetailFragmentInterface.View {

    private DoctorNoteObject data;

    private LinearLayout linMainRecProduct;
    private LinearLayout linMainRecMedication;

    private TextView textDoctorName;
    private TextView txtAppointmentTime;
    private EditText edtNote;
    private TextView txtEndOfEffectiveDate;

    private RecyclerView rvImage;
    private RecyclerView rvRecommendProduct;
    private ListRecommendProductAdapter recommendProductAdapter;
    private RecyclerView rvRecommendMedication;
    private ListRecommendMedicationAdapter recommendMedicationAdapter;
    private ListImageAdapter listImageAdapter;

    private View productCoupons;
    private EditText edtProductCoupons;
    private LinearLayout btnDoneProductCoupons;
    private TextView txtDoneProductCoupons;
    private View medicationCoupons;
    private EditText edtMedicationCoupons;
    private LinearLayout btnDoneMedicationCoupons;
    private TextView txtDoneMedicationCoupons;


    private View priceRecommendProduct;
    private TextView txtProductSummaryPrice;
    private LinearLayout btnPriceRecommendProduct;
    private ImageView imgIcExpandPriceRecommendProduct;
    private ExpandableLayout layoutPriceRecommendProduct;
    private View priceNoVatRecommendProduct;
    private TextView lblPriceNoVatRecommendProduct;
    private TextView txtPriceNoVatRecommendProduct;
    private View priceVatRecommendProduct;
    private TextView lblPriceVatRecommendProduct;
    private TextView txtPriceVatRecommendProduct;
    private View discountPriceRecommendProduct;
    private TextView lblDiscountPriceRecommendProduct;
    private TextView txtDiscountPriceRecommendProduct;
    private View summaryPriceRecommendProduct;
    private TextView lblSummaryPriceRecommendProduct;
    private TextView txtSummaryPriceRecommendProduct;

    private View priceMedication;
    private TextView lblPriceNoVatMedication;
    private LinearLayout btnPriceMedication;
    private ImageView imgIcExpandPriceMedication;
    private ExpandableLayout layoutPriceMedication;
    private View priceNoVatMedication;
    private TextView txtMedicationSummaryPrice;
    private TextView txtPriceNoVatMedication;
    private View priceVatMedication;
    private TextView lblPriceVatMedication;
    private TextView txtPriceVatMedication;
    private View discountPriceMedication;
    private TextView lblDiscountPriceMedication;
    private TextView txtDiscountPriceMedication;
    private View summaryPriceMedication;
    private TextView lblSummaryPriceMedication;
    private TextView txtSummaryPriceMedication;

    private float medicationSummaryPrice;
    private float productSummaryPrice;

    private Button btnBackMainDoctorNote;

    public DoctorNoteDetailFragment() {
        super();
    }

    public static DoctorNoteDetailFragment newInstance(DoctorNoteObject data) {
        Bundle args = new Bundle();
        DoctorNoteDetailFragment fragment = new DoctorNoteDetailFragment();
        fragment.data = data;

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public DoctorNoteDetailFragmentInterface.Presenter createPresenter() {
        return DoctorNoteDetailFragmentPreenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_doctor_note_detail;
    }

    @Override
    public void bindView(View view) {
        linMainRecProduct = (LinearLayout) view.findViewById(R.id.linMainRecProduct);
        linMainRecMedication = (LinearLayout) view.findViewById(R.id.linMainRecMedication);

        textDoctorName = (TextView) view.findViewById(R.id.textDoctorName);
        txtAppointmentTime = (TextView) view.findViewById(R.id.txtAppointmentTime);
        edtNote = (EditText) view.findViewById(R.id.edtNote);

        rvRecommendProduct = (RecyclerView) view.findViewById(R.id.recycler_recommend_product);

        rvImage = (RecyclerView) view.findViewById(R.id.recycler_image);
        rvRecommendMedication = (RecyclerView) view.findViewById(R.id.recycler_recommend_medication);
        txtEndOfEffectiveDate = (TextView) view.findViewById(R.id.txtEndOfEffectiveDate);

        productCoupons = (View) view.findViewById(R.id.productCoupons);
        edtProductCoupons = (EditText) productCoupons.findViewById(R.id.edtCode);
        btnDoneProductCoupons = (LinearLayout) productCoupons.findViewById(R.id.btnDone);
        txtDoneProductCoupons = (TextView) productCoupons.findViewById(R.id.txtDone);

        priceRecommendProduct = (View) view.findViewById(R.id.priceRecommendProduct);
        btnPriceRecommendProduct = (LinearLayout) priceRecommendProduct.findViewById(R.id.item_confirm_price);
        imgIcExpandPriceRecommendProduct = (ImageView) priceRecommendProduct.findViewById(R.id.imgIcExpand);
        layoutPriceRecommendProduct = (ExpandableLayout) priceRecommendProduct.findViewById(R.id.layout_price_detail);
        txtProductSummaryPrice = (TextView) priceRecommendProduct.findViewById(R.id.tv_summaryPrice);

        priceNoVatRecommendProduct = (View) priceRecommendProduct.findViewById(R.id.priceNoVat);
        lblPriceNoVatRecommendProduct = (TextView) priceNoVatRecommendProduct.findViewById(R.id.tv_title);
        txtPriceNoVatRecommendProduct = (TextView) priceNoVatRecommendProduct.findViewById(R.id.tv_price);
        priceVatRecommendProduct = (View) priceRecommendProduct.findViewById(R.id.priceVat);
        lblPriceVatRecommendProduct = (TextView) priceVatRecommendProduct.findViewById(R.id.tv_title);
        txtPriceVatRecommendProduct = (TextView) priceVatRecommendProduct.findViewById(R.id.tv_price);
        discountPriceRecommendProduct = (View) priceRecommendProduct.findViewById(R.id.discountPrice);
        lblDiscountPriceRecommendProduct = (TextView) discountPriceRecommendProduct.findViewById(R.id.tv_title);
        txtDiscountPriceRecommendProduct = (TextView) discountPriceRecommendProduct.findViewById(R.id.tv_price);
        summaryPriceRecommendProduct = (View) priceRecommendProduct.findViewById(R.id.summaryPrice);
        lblSummaryPriceRecommendProduct = (TextView) summaryPriceRecommendProduct.findViewById(R.id.tv_title);
        txtSummaryPriceRecommendProduct = (TextView) summaryPriceRecommendProduct.findViewById(R.id.tv_price);

        medicationCoupons = (View) view.findViewById(R.id.medicationCoupons);
        edtMedicationCoupons = (EditText) medicationCoupons.findViewById(R.id.edtCode);
        btnDoneMedicationCoupons = (LinearLayout) medicationCoupons.findViewById(R.id.btnDone);
        txtDoneMedicationCoupons = (TextView) medicationCoupons.findViewById(R.id.txtDone);

        priceMedication = (View) view.findViewById(R.id.priceMedication);
        btnPriceMedication = (LinearLayout) priceMedication.findViewById(R.id.item_confirm_price);
        imgIcExpandPriceMedication = (ImageView) priceMedication.findViewById(R.id.imgIcExpand);
        layoutPriceMedication = (ExpandableLayout) priceMedication.findViewById(R.id.layout_price_detail);
        txtMedicationSummaryPrice = (TextView) priceMedication.findViewById(R.id.tv_summaryPrice);


        priceNoVatMedication = (View) priceMedication.findViewById(R.id.priceNoVat);
        lblPriceNoVatMedication = (TextView) priceNoVatMedication.findViewById(R.id.tv_title);
        txtPriceNoVatMedication = (TextView) priceNoVatMedication.findViewById(R.id.tv_price);
        priceVatMedication = (View) priceMedication.findViewById(R.id.priceVat);
        lblPriceVatMedication = (TextView) priceVatMedication.findViewById(R.id.tv_title);
        txtPriceVatMedication = (TextView) priceVatMedication.findViewById(R.id.tv_price);
        discountPriceMedication = (View) priceMedication.findViewById(R.id.discountPrice);
        lblDiscountPriceMedication = (TextView) discountPriceMedication.findViewById(R.id.tv_title);
        txtDiscountPriceMedication = (TextView) discountPriceMedication.findViewById(R.id.tv_price);
        summaryPriceMedication = (View) priceMedication.findViewById(R.id.summaryPrice);
        lblSummaryPriceMedication = (TextView) summaryPriceMedication.findViewById(R.id.tv_title);
        txtSummaryPriceMedication = (TextView) summaryPriceMedication.findViewById(R.id.tv_price);


        btnBackMainDoctorNote = (Button) view.findViewById(R.id.btnBackMainDoctorNote);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        lblPriceNoVatRecommendProduct.setText(getResources().getString(R.string.product_price));
        lblPriceVatRecommendProduct.setText(getResources().getString(R.string.vat));
        lblDiscountPriceRecommendProduct.setText(getResources().getString(R.string.discount_price));
        lblSummaryPriceRecommendProduct.setText(getResources().getString(R.string.summary_price));

        lblPriceNoVatMedication.setText(getResources().getString(R.string.drug_price));
        lblPriceVatMedication.setText(getResources().getString(R.string.vat));
        lblDiscountPriceMedication.setText(getResources().getString(R.string.discount_price));
        lblSummaryPriceMedication.setText(getResources().getString(R.string.summary_price));

        edtProductCoupons.setHint(getResources().getString(R.string.input_discount_code));
        edtMedicationCoupons.setHint(getResources().getString(R.string.input_discount_code));
    }

    @Override
    public void initialize() {
        setAdapter();
        ((MainActivity) getActivity()).setShowBackButton(true);
        ((MainActivity) getActivity()).showToolbarMain(true);
        ((MainActivity) getActivity()).updateToolbar(getResources().getString(R.string.doctor_note), false,null, false);

        textDoctorName.setText(data.getDoctorName());
        txtAppointmentTime.setText(ConvertDate.StringDateServiceFormatDate(data.getAppointmentTimeString()) + " / " +
                ConvertDate.StringDateServiceFormatTime(data.getAppointmentTimeString()) + " / " +
                data.getContactMinutes() + " " +
                getString(R.string.time_minute));
        edtNote.setText(data.getShortNote());

        if (data.getRecommendProductList() != null && data.getRecommendProductList().size() > 0) {
            recommendProductAdapter.setListData(data.getRecommendProductList());
            linMainRecProduct.setVisibility(View.VISIBLE);
        } else {
            recommendProductAdapter.setListData(new ArrayList<RecommendProductObject>());
            linMainRecProduct.setVisibility(View.GONE);
        }


        if (data.getRecommendMedicineList() != null && data.getRecommendMedicineList().size() > 0) {
            recommendMedicationAdapter.setListData(data.getRecommendMedicineList());
            linMainRecMedication.setVisibility(View.VISIBLE);
        } else {
            recommendMedicationAdapter.setListData(new ArrayList<RecommendMedicationObject>());
            linMainRecMedication.setVisibility(View.GONE);
        }

        if (data.getShortNoteAttachmentList() != null && data.getShortNoteAttachmentList().size() > 0) {
            listImageAdapter.setListData(data.getShortNoteAttachmentList());
            rvImage.setVisibility(View.VISIBLE);
        } else {
            listImageAdapter.setListData(new ArrayList<ShortNoteAttachmentObject>());
            rvImage.setVisibility(View.GONE);
        }


        btnDoneProductCoupons.setOnClickListener(this.OnConfirmProductCoupons());
        btnDoneMedicationCoupons.setOnClickListener(this.OnConfirmMedicationCoupons());

        setDisplayProductPrice();
        setDisplayMedicationPrice();

        checkRequireProductCoupons();
        edtProductCoupons.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequireProductCoupons();
            }
        });

        checkRequireMedicationCoupons();
        edtMedicationCoupons.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequireMedicationCoupons();
            }
        });

        txtEndOfEffectiveDate.setText("*จะหมดเวลาการซื้อยาภายใน " + ConvertDate.StringDateServiceFormat(data.getEndOfEffectiveDateString()));

        btnPriceRecommendProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutPriceRecommendProduct.isExpanded()) {
                    imgIcExpandPriceRecommendProduct.setImageResource(R.drawable.ic_action_expand_more);
                    layoutPriceRecommendProduct.collapse();
                } else {
                    imgIcExpandPriceRecommendProduct.setImageResource(R.drawable.ic_action_expand_less);
                    layoutPriceRecommendProduct.expand();
                }
            }
        });

        btnPriceMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutPriceMedication.isExpanded()) {
                    imgIcExpandPriceMedication.setImageResource(R.drawable.ic_action_expand_more);
                    layoutPriceMedication.collapse();
                } else {
                    imgIcExpandPriceMedication.setImageResource(R.drawable.ic_action_expand_less);
                    layoutPriceMedication.expand();
                }
            }
        });

        btnBackMainDoctorNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
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

    private void setAdapter() {
        FlexboxLayoutManager flexboxManager = new FlexboxLayoutManager(getActivity());
        flexboxManager.setFlexWrap(FlexWrap.WRAP);
        flexboxManager.setFlexDirection(FlexDirection.ROW);
        flexboxManager.setJustifyContent(JustifyContent.FLEX_START);
        rvImage.setLayoutManager(flexboxManager);
        rvImage.addItemDecoration(new SpacesItemDecoration((int) CommonUtils.convertDpToPixel(getActivity(), 3)));
        listImageAdapter = new ListImageAdapter(getActivity(), this, (int) CommonUtils.convertDpToPixel(getActivity(), 95), (int) CommonUtils.convertDpToPixel(getActivity(), 95));
        listImageAdapter.setOnClickListener(this.onImageListClickListener());
        rvImage.setAdapter(listImageAdapter);

        LinearLayoutManager medicationLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvRecommendMedication.setLayoutManager(medicationLayoutManager);
        rvRecommendMedication.addItemDecoration(new ListDividerItemDecoration(getActivity(), R.drawable.line_divider_black, false));
        recommendMedicationAdapter = new ListRecommendMedicationAdapter(getActivity());
        recommendMedicationAdapter.setOnClickListener(this.onMedicationConfirmClickListener());
        rvRecommendMedication.setAdapter(recommendMedicationAdapter);

        LinearLayoutManager productLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvRecommendProduct.setLayoutManager(productLayoutManager);
        rvRecommendProduct.addItemDecoration(new ListDividerItemDecoration(getActivity(), R.drawable.line_divider_black, false));
        recommendProductAdapter = new ListRecommendProductAdapter(getActivity());
        recommendProductAdapter.setOnClickListener(this.onProductConfirmClickListener());
        rvRecommendProduct.setAdapter(recommendProductAdapter);
    }

    private ListImageAdapter.OnClickListener<ShortNoteAttachmentObject> onImageListClickListener() {
        return new ListImageAdapter.OnClickListener<ShortNoteAttachmentObject>() {
            @Override
            public void onAddClick() {

            }

            @Override
            public void onViewClick(ShortNoteAttachmentObject data) {
                if (!data.getIsVideo()) {
                    ShowImageDialog fullImageDialog = new ShowImageDialog(getActivity(), data.getImageUrl());
                    fullImageDialog.showDialog();
                }
            }

            @Override
            public void onDelete(ShortNoteAttachmentObject data) {

            }
        };
    }

    private ListRecommendProductAdapter.OnConfirmClickListener onProductConfirmClickListener() {
        return new ListRecommendProductAdapter.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {

            }
        };
    }

    private ListRecommendMedicationAdapter.OnConfirmClickListener onMedicationConfirmClickListener() {
        return new ListRecommendMedicationAdapter.OnConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                //open ...
                ConfirmDialog confirmDialog = new ConfirmDialog(getActivity(), getResources().getString(R.string.dialog_accept_not_allergic_medication_title), getResources().getString(R.string.dialog_not_accept_medication), getResources().getString(R.string.dialog_accept_medication));
                confirmDialog.showDialog();
                confirmDialog.setOnClickListener(new ConfirmDialog.OnClickListener() {
                    @Override
                    public void onConfirm() {
                        openDeliveryDetailDialog();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        };
    }

    @Override
    public void setDetailCoupons(float discountPrice, boolean isProduct) {

        if (isProduct) {
            data.setIsConfirmProductCouponsCode(true);
            data.setDiscountCouponsPrice(discountPrice);
            setEnableDisableBtnCoupons(btnDoneProductCoupons, txtDoneProductCoupons, false);
            setDisplayProductPrice();
        } else {
            data.setIsConfirmMedicineCouponsCode(true);
            data.setDiscountMedicineCouponsPrice(discountPrice);
            setEnableDisableBtnCoupons(btnDoneMedicationCoupons, txtDoneMedicationCoupons, false);
            setDisplayMedicationPrice();
        }
    }

    private View.OnClickListener OnConfirmProductCoupons() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getDetailCoupons(data, edtProductCoupons.getText().toString(), true);
            }
        };
    }

    private View.OnClickListener OnConfirmMedicationCoupons() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().getDetailCoupons(data, edtMedicationCoupons.getText().toString(), false);
            }
        };
    }

    private void checkRequireProductCoupons() {
        data.setIsConfirmProductCouponsCode(false);
        data.setDiscountCouponsPrice(0);
        setDisplayProductPrice();

        String code = edtProductCoupons.getText().toString();

        if ((!code.isEmpty() && !code.trim().isEmpty())) {
            setEnableDisableBtnCoupons(btnDoneProductCoupons, txtDoneProductCoupons, true);
        } else {
            setEnableDisableBtnCoupons(btnDoneProductCoupons, txtDoneProductCoupons, false);
        }
    }

    private void checkRequireMedicationCoupons() {
        data.setIsConfirmMedicineCouponsCode(false);
        data.setDiscountMedicineCouponsPrice(0);
        setDisplayMedicationPrice();

        String code = edtMedicationCoupons.getText().toString();

        if ((!code.isEmpty() && !code.trim().isEmpty())) {
            setEnableDisableBtnCoupons(btnDoneMedicationCoupons, txtDoneMedicationCoupons, true);
        } else {
            setEnableDisableBtnCoupons(btnDoneMedicationCoupons, txtDoneMedicationCoupons, false);
        }
    }

    private void setEnableDisableBtnCoupons(View btnDone, TextView txtDone, boolean enabled) {
        if (enabled) {
            btnDone.setEnabled(true);
            txtDone.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        } else {
            btnDone.setEnabled(false);
            txtDone.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_gray_light_text));
        }
    }

    private void setDisplayProductPrice() {
        String priceNoVat = "0";
        String priceVat = "0";
        String priceDiscount = "0";
        String priceSummary = "0";

        if (data.getRecommendProductList() != null && data.getRecommendProductList().size() > 0) {
            float sumPriceNoVat = 0;
            float sumPriceVat = 0;
            float discount = data.getDiscountProductCouponsPrice();

            for (RecommendProductObject item : data.getRecommendProductList()) {
                float itemPriceNoVat = item.getNormalSellingPriceNoVat();
                float itemPriceVat = item.getNormalSellingPriceVat();

                if (item.getPromotionSellingPriceNoVat() != 0) {
                    itemPriceNoVat = item.getPromotionSellingPriceNoVat();
                    itemPriceVat = item.getPromotionSellingPriceVat();
                }

                sumPriceNoVat += itemPriceNoVat;
                sumPriceVat += itemPriceVat;
            }

            priceNoVat = String.format(getResources().getString(R.string.money_baht_double), sumPriceNoVat);
            priceVat = String.format(getResources().getString(R.string.money_baht_double), sumPriceVat);
            priceDiscount = String.format('-' + getResources().getString(R.string.money_baht_double), discount);
            productSummaryPrice = (sumPriceNoVat + sumPriceVat) - discount;
            priceSummary = String.format(getResources().getString(R.string.money_baht_double), productSummaryPrice);

        }

        txtPriceNoVatRecommendProduct.setText(priceNoVat);
        txtPriceVatRecommendProduct.setText(priceVat);
        txtDiscountPriceRecommendProduct.setText(priceDiscount);
        txtSummaryPriceRecommendProduct.setText(priceSummary);
        txtProductSummaryPrice.setText(priceSummary);
    }

    private void setDisplayMedicationPrice() {
        String priceNoVat = "0";
        String priceVat = "0";
        String priceDiscount = "0";
        String priceSummary = "0";

        if (data.getRecommendMedicineList() != null && data.getRecommendMedicineList().size() > 0) {
            float sumPriceNoVat = 0;
            float sumPriceVat = 0;
            float discount = data.getDiscountProductCouponsPrice();

            for (RecommendMedicationObject item : data.getRecommendMedicineList()) {
                float itemPriceNoVat = item.getNormalSellingPriceNoVat();
                float itemPriceVat = item.getNormalSellingPriceVat();

                if (item.getPromotionSellingPriceNoVat() != 0) {
                    itemPriceNoVat = item.getPromotionSellingPriceNoVat();
                    itemPriceVat = item.getPromotionSellingPriceVat();
                }

                sumPriceNoVat += itemPriceNoVat;
                sumPriceVat += itemPriceVat;
            }

            priceNoVat = String.format(getResources().getString(R.string.money_baht_double), sumPriceNoVat);
            priceVat = String.format(getResources().getString(R.string.money_baht_double), sumPriceVat);
            priceDiscount = String.format('-' + getResources().getString(R.string.money_baht_double), discount);
            medicationSummaryPrice = (sumPriceNoVat + sumPriceVat) - discount;
            priceSummary = String.format(getResources().getString(R.string.money_baht_double), medicationSummaryPrice);

        }

        txtPriceNoVatMedication.setText(priceNoVat);
        txtPriceVatMedication.setText(priceVat);
        txtDiscountPriceMedication.setText(priceDiscount);
        txtSummaryPriceMedication.setText(priceSummary);
        txtMedicationSummaryPrice.setText(priceSummary);
    }

    private void openDeliveryDetailDialog() {
        DeliveryDetailDialog deliveryDetail = new DeliveryDetailDialog(getActivity());
        deliveryDetail.showDialog();
        deliveryDetail.setOnConfirmListener(new DeliveryDetailDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String name, String lastName, String address) {
                openPaymentChannelDialog();
            }
        });
    }

    private void openPaymentChannelDialog() {
        PaymentChannelDialog deliveryDetail = new PaymentChannelDialog(getActivity(), String.format(getResources().getString(R.string.money_double), productSummaryPrice), getResources().getString(R.string.money_baht));
        deliveryDetail.showDialog();
        deliveryDetail.setOnPaymentListener(new PaymentChannelDialog.OnPaymentListener() {
            @Override
            public void onCreditCard() {

            }

            @Override
            public void onBankTranfer() {
                openPaymentBankTransferDialog();
            }
        });
    }

    private void openPaymentBankTransferDialog() {
        getPresenter().getBankAccountList();


    }

    @Override
    public void setBankAccountList(ArrayList<BankAccountObject> dataList) {
        PaymentBankTransferDialog paymentBankTransfer = new PaymentBankTransferDialog(getActivity(), String.format(getResources().getString(R.string.money_double), medicationSummaryPrice), getResources().getString(R.string.money_baht), dataList);
        paymentBankTransfer.showDialog();
        paymentBankTransfer.setOnConfirmListener(new PaymentBankTransferDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String code) {

            }
        });
    }


}
