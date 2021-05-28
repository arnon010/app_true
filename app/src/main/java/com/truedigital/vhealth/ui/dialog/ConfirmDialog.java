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

public class ConfirmDialog extends Dialog {

    private Context context;

    private String messageTitle;
    private String textNo;
    private String textYes;

    private TextView txtMessageTitle;
    private Button btnNo;
    private Button btnYes;

    private ConfirmDialog.OnClickListener onClickListener;

    public ConfirmDialog(@NonNull Context context, String messageTitle, String textNo, String textYes) {
        super(context);
        this.context = context;
        this.messageTitle = messageTitle;
        this.textNo = textNo;
        this.textYes = textYes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_confirm, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        this.setupView(view);

        this.show();
    }

    private void setupView(View view){
        txtMessageTitle = (TextView) view.findViewById(R.id.txtMessageTitle);
        btnNo = (Button) view.findViewById(R.id.btnNo);
        btnYes = (Button) view.findViewById(R.id.btnYes);

        txtMessageTitle.setText(this.messageTitle);
        StringUtils.setUnderline(txtMessageTitle);
        btnNo.setText(this.textNo);
        btnYes.setText(this.textYes);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onClickListener.onConfirm();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                onClickListener.onCancel();
            }
        });

    }

    public interface OnClickListener {
        void onConfirm();
        void onCancel();
    }

    public void setOnClickListener(ConfirmDialog.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
