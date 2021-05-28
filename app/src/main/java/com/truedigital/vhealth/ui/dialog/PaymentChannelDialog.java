package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.utils.StringUtils;

public class PaymentChannelDialog extends Dialog {

    private Context context;

    private String priceAmount;
    private String currency;

    private Button btn_credit_card;
    private Button btn_bank_transfer;
    private TextView tv_price;
    private TextView tv_currency;
    private TextView tv_title;
    private TextView tv_payment_channel;

    private PaymentChannelDialog.OnPaymentListener onPaymentListener;

    public PaymentChannelDialog(@NonNull Context context, String priceAmount, String currency) {
        super(context);
        this.context = context;
        this.priceAmount = priceAmount;
        this.currency = currency;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_payment_channel, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);

        this.show();
    }

    private void setupView(View view){
        btn_credit_card = (Button) view.findViewById(R.id.btn_credit_card);
        btn_bank_transfer = (Button) view.findViewById(R.id.btn_bank_transfer);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_currency = (TextView) view.findViewById(R.id.tv_currency);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_payment_channel = (TextView) view.findViewById(R.id.tv_payment_channel);

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(tv_payment_channel);
        tv_price.setText(this.priceAmount);
        tv_currency.setText(this.currency);

        btn_credit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onPaymentListener.onCreditCard();
            }
        });

        btn_bank_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onPaymentListener.onBankTranfer();
            }
        });
        
    }

    public interface OnPaymentListener {
        void onCreditCard();
        void onBankTranfer();
    }

    public void setOnPaymentListener(PaymentChannelDialog.OnPaymentListener onPaymentListener) {
        this.onPaymentListener = onPaymentListener;
    }
}
