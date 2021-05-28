package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.utils.StringUtils;

public class DeliveryDetailDialog extends Dialog {

    private Context context;

    private TextView txtMessage;
    private EditText edtName;
    private EditText edtLastName;
    private EditText edtAddress;
    private TextView btnConfirm;

    private DeliveryDetailDialog.OnConfirmListener onConfirmListener;

    public DeliveryDetailDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_delivery_detail, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);

        this.show();
    }

    private void setupView(View view) {
        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        edtName = (EditText) view.findViewById(R.id.edtName);
        edtLastName = (EditText) view.findViewById(R.id.edtLastName);
        edtAddress = (EditText) view.findViewById(R.id.edtAddress);
        btnConfirm = (TextView) view.findViewById(R.id.btnConfirm);

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequire();
            }
        });

        edtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequire();
            }
        });

        edtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRequire();
            }
        });

        StringUtils.setUnderline(txtMessage);
        StringUtils.setUnderline(btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onConfirmListener.onConfirm(edtName.getText().toString(), edtLastName.getText().toString(), edtAddress.getText().toString());
            }
        });

        checkRequire();
    }

    private void checkRequire() {
        String name = edtName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String address = edtAddress.getText().toString();

        if ((!name.isEmpty() && !name.trim().isEmpty()) && (!lastName.isEmpty() && !lastName.trim().isEmpty()) && (!address.isEmpty() && !address.trim().isEmpty())) {
            btnConfirm.setEnabled(true);
            btnConfirm.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            btnConfirm.setEnabled(false);
            btnConfirm.setTextColor(ContextCompat.getColor(context, R.color.color_gray_light_text));
        }

    }

    public interface OnConfirmListener {
        void onConfirm(String name, String lastName, String address);
    }

    public void setOnConfirmListener(DeliveryDetailDialog.OnConfirmListener OnConfirmListener) {
        this.onConfirmListener = OnConfirmListener;
    }
}
