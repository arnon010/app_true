package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.utils.StringUtils;

public class LoginOtpDialog extends Dialog {

    private Context context;
    private Button btn_confirm;
    private Button btn_renew;
    private TextView tv_title;
    private EditText ed_login_otp;

    private LoginOtpDialog.OnSelectListener onSelectListener;


    public LoginOtpDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_login_otp, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCanceledOnTouchOutside(false);
        this.setupView(view);

        this.show();
    }

    private void setupView(View view){
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_renew = (Button) view.findViewById(R.id.btn_renew_otp);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ed_login_otp = (EditText) view.findViewById(R.id.ed_login_otp);

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(btn_confirm);
        StringUtils.setUnderline(btn_renew);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectListener != null) {
                    onSelectListener.onClickOK();
                }
            }
        });

        btn_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectListener != null) {
                    onSelectListener.onClickRenew();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public interface OnSelectListener {
        void onClickOK(String ReferenceCode, String otp);
        void onClickOK();
        void onClickRenew();
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

}
