package com.truedigital.vhealth.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.truedigital.vhealth.R;

public class SuccessDialog extends Dialog {

    private int showTimeMillis = 5000;
    private Context context;

    private CountDownTimer countTime;
    private SuccessDialog.OnShowFinishListener onShowFinishListener;

    public SuccessDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showDialog(String message) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_success, null);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(view);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        this.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextView txtMessage = (TextView)view.findViewById(R.id.txtMessage);
        txtMessage.setText(message);

        this.show();
        this.CountDownFinish(this, this.showTimeMillis);
    }

    @Override
    public void onBackPressed(){
        countTime.cancel();
        onShowFinishListener.onShowFinish();
        super.onBackPressed();
    }

    private void CountDownFinish(final Dialog dialog, int millis){
        countTime = new CountDownTimer(millis, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                onShowFinishListener.onShowFinish();
                dialog.dismiss();
            }
        };
        countTime.start();
    }

    public interface OnShowFinishListener {
        void onShowFinish();
    }

    public void setOnShowFinishListener(SuccessDialog.OnShowFinishListener onShowFinishListener) {
        this.onShowFinishListener = onShowFinishListener;
    }
}

