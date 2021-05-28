package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.BankAccountObject;
import com.truedigital.vhealth.ui.adapter.ListBankAdapter;
import com.truedigital.vhealth.utils.StringUtils;

import java.util.List;

public class PaymentBankTransferDialog extends Dialog {

    private Context context;

    private String priceAmount;
    private String currency;

    private EditText edtCode;
    private LinearLayout btnDone;
    private TextView txtDone;
    private TextView tv_price;
    private TextView tv_currency;
    private TextView tv_title;
    private TextView tv_payment_channel;
    private RecyclerView rvBank;
    private ListBankAdapter bankAdapter;
    private List<BankAccountObject> bankList;

    private PaymentBankTransferDialog.OnConfirmListener onConfirmListener;

    public PaymentBankTransferDialog(@NonNull Context context, String priceAmount, String currency, List<BankAccountObject> bankList) {
        super(context);
        this.context = context;
        this.priceAmount = priceAmount;
        this.currency = currency;
        this.bankList = bankList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_payment_bank_transfer, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);

        this.show();
    }

    private void setupView(View view){
        edtCode = (EditText) view.findViewById(R.id.edtCode);
        btnDone = (LinearLayout) view.findViewById(R.id.btnDone);
        txtDone = (TextView) view.findViewById(R.id.txtDone);
        rvBank = (RecyclerView) view.findViewById(R.id.recycler_bank);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_currency = (TextView) view.findViewById(R.id.tv_currency);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_payment_channel = (TextView) view.findViewById(R.id.tv_payment_channel);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvBank.setLayoutManager(layoutManager);
        bankAdapter = new ListBankAdapter(context);
        bankAdapter.setListData(this.bankList);
        rvBank.setAdapter(bankAdapter);


        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(tv_payment_channel);
        tv_price.setText(this.priceAmount);
        tv_currency.setText(this.currency);

        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequire();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onConfirmListener.onConfirm(edtCode.getText().toString());
            }
        });

        checkRequire();
    }

    private void checkRequire()
    {
        String code = edtCode.getText().toString();

        if ((!code.isEmpty() && !code.trim().isEmpty())) {
            btnDone.setEnabled(true);
            txtDone.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            btnDone.setEnabled(false);
            txtDone.setTextColor(ContextCompat.getColor(context, R.color.color_gray_light_text));
        }
    }

    public interface OnConfirmListener {
        void onConfirm(String code);
    }

    public void setOnConfirmListener(PaymentBankTransferDialog.OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }
}
