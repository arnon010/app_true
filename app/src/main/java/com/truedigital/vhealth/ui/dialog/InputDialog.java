package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.utils.StringUtils;

public class InputDialog extends Dialog {

    private Context context;

    private String title;
    private String defaultValue;

    private TextView txtMessage;
    private EditText edtInput;
    private TextView btnConfirm;

    private InputDialog.OnConfirmListener onConfirmListener;

    public InputDialog(@NonNull Context context, String title) {
        super(context);
        this.context = context;
        this.title = title;
    }

    public InputDialog(@NonNull Context context, String title, String defaultValue) {
        super(context);
        this.context = context;
        this.title = title;
        this.defaultValue = defaultValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_input, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);

        this.show();
    }

    private void setupView(View view) {
        txtMessage = (TextView) view.findViewById(R.id.txtMessage);
        edtInput = (EditText) view.findViewById(R.id.edtInput);
        btnConfirm = (TextView) view.findViewById(R.id.btnConfirm);

        txtMessage.setText(this.title);
        if (this.defaultValue != null && this.defaultValue != "") {
            edtInput.setText(this.defaultValue);
            edtInput.setSelection(edtInput.getText().length());
        }
        StringUtils.setUnderline(btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmListener.onConfirm(edtInput.getText().toString());
            }
        });
    }

    public interface OnConfirmListener {
        void onConfirm(String data);
    }

    public void setOnConfirmListener(InputDialog.OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

}
