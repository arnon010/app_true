package com.truedigital.vhealth.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.ClientCertRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ItemPersonDao;
import com.truedigital.vhealth.ui.adapter.ListPersonAdapter;

import java.util.List;

/**
 * Created by songkrit on 5/28/2018 AD.
 */

public class MyDialog extends Dialog {

    private Context context;

    public MyDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void show() {
        super.show();
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void showWebview(String title, String url, final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_webview, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextView tv_title = view.findViewById(R.id.tv_title);
        final ProgressBar progressBar = view.findViewById(R.id.progress_bar);

        tv_title.setText(title);
        progressBar.setVisibility(View.VISIBLE);
        WebView myWebView = view.findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });

        myWebView.loadUrl(url);

        Button btnClose = view.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(v -> {
            onSelectListener.onClickCancel();
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showMessage(String title, String message, final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_message, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView iv_home_back = view.findViewById(R.id.iv_home_back);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_text = view.findViewById(R.id.tv_text);
        tv_title.setText(title);
        tv_text.setText(message);

        Button btnClose = view.findViewById(R.id.btn_close);
        Button btnDone = view.findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                dialog.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        iv_home_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        btnDone.setVisibility(View.INVISIBLE);
        btnClose.setVisibility(View.INVISIBLE);

        tv_text.setMovementMethod(new ScrollingMovementMethod());

        dialog.show();
    }

    public void showMessage(int resIdTitle, int resIdMessage, final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_message, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ImageView iv_home_back = view.findViewById(R.id.iv_home_back);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_text = view.findViewById(R.id.tv_text);
        tv_title.setText(context.getResources().getString(resIdTitle));
        tv_text.setText(context.getResources().getString(resIdMessage));

        Button btnClose = view.findViewById(R.id.btn_close);
        Button btnDone = view.findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                dialog.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        iv_home_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        tv_text.setMovementMethod(new ScrollingMovementMethod());

        dialog.show();
    }

    public void showUpdateApp(final OnSelectListener onSelectListener) {

        final View view = getLayoutInflater().inflate(R.layout.dialog_update_app, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Button btnDone = view.findViewById(R.id.btn_done);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void showTimeout(final OnSelectListener onSelectListener) {

        final View view = getLayoutInflater().inflate(R.layout.dialog_timeout, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Button btnDone = view.findViewById(R.id.btn_done);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void showEndCall(int title, final OnSelectListener onSelectListener) {

        final View view = getLayoutInflater().inflate(R.layout.dialog_timeout, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView tv_title = view.findViewById(R.id.tv_message);
        Button btnDone = view.findViewById(R.id.btn_done);

        tv_title.setText(title);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void showPersonConsult(List<ItemPersonDao> listData, final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_person_consult, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //..
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ListPersonAdapter adapter = new ListPersonAdapter(recyclerView.getContext(), listData);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        int spacingInPixels = 8; //getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        //..

        Button btnClose = view.findViewById(R.id.btn_close);
        Button btnDone = view.findViewById(R.id.btn_done);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                dialog.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showPaymentChannel(String priceAmount, String currency, final OnPaymentListener onPaymentListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_payment_channel, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Button btn_credit_card = view.findViewById(R.id.btn_credit_card);
        Button btn_bank_transfer = view.findViewById(R.id.btn_bank_transfer);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_currency = view.findViewById(R.id.tv_currency);

        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_payment_channel = view.findViewById(R.id.tv_payment_channel);

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(tv_payment_channel);

        tv_price.setText(priceAmount);
        tv_currency.setText(currency);

        btn_credit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPaymentListener.onCreditCard();
                dialog.dismiss();
            }
        });

        btn_bank_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPaymentListener.onBankTranfer();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showPaymentBankTransfer(String priceAmount, String currency, final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_payment_bank_transfer, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout btnDone = view.findViewById(R.id.btnDone);
        //Button btn_bank_transfer = (Button) view.findViewById(R.id.btn_bank_transfer);
        TextView tv_price = view.findViewById(R.id.tv_price);
        TextView tv_currency = view.findViewById(R.id.tv_currency);

        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_payment_channel = view.findViewById(R.id.tv_payment_channel);

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(tv_payment_channel);

        tv_price.setText(priceAmount);
        tv_currency.setText(currency);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showDialogOTPNew(final String reference_code, final OnOTPSelectListener onOTPSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_layout_test, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void showDialogOTP(final String reference_code, final OnOTPSelectListener onOTPSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_login_otp, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Button btn_confirm = view.findViewById(R.id.btn_confirm);
        Button btn_renew = view.findViewById(R.id.btn_renew_otp);

        final EditText ed_login_otp = view.findViewById(R.id.ed_login_otp);

        //TextView tv_currency = (TextView) view.findViewById(R.id.tv_currency);

        TextView tv_title = view.findViewById(R.id.tv_title);
        //TextView tv_payment_channel = (TextView) view.findViewById(R.id.tv_payment_channel);

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(btn_confirm);
        StringUtils.setUnderline(btn_renew);

        //tv_price.setText(priceAmount);
        //tv_currency.setText(currency);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOTPSelectListener.onClickOK(reference_code, ed_login_otp.getText().toString());
                dialog.dismiss();
            }
        });

        btn_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOTPSelectListener.onClickRenew();
                //dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showDialogOldPassword(final OnPasswordSelectListener onPasswordSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_old_password, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Button btn_close = view.findViewById(R.id.btn_close);
        Button btn_confirm = view.findViewById(R.id.btn_confirm);

        final EditText ed_password = view.findViewById(R.id.ed_old_password);
        TextView tv_title = view.findViewById(R.id.tv_title);


        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(btn_confirm);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPasswordSelectListener.onClickOK(ed_password.getText().toString());
                dialog.dismiss();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPasswordSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        setCancelable(false);
        dialog.show();
    }

    public void showSendAddress(final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_send_address, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Button btnClose = view.findViewById(R.id.btn_close);
        Button btnDone = view.findViewById(R.id.btn_done);
        TextView tv_title = view.findViewById(R.id.tv_title);

        EditText edName = view.findViewById(R.id.edName);
        EditText edLastName = view.findViewById(R.id.edLastName);
        EditText edAddress = view.findViewById(R.id.edAddress);

        StringUtils.setUnderline(tv_title);
        StringUtils.setUnderline(btnDone);

        //tv_price.setText(priceAmount);
        //tv_currency.setText(currency);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                dialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showCancenConfirm(final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_cancel_confirm, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        Button btn_ok = view.findViewById(R.id.btn_ok);
        TextView tv_title = view.findViewById(R.id.tv_title);

        StringUtils.setUnderline(tv_title);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showConfirm(int title, int strNo, int strYes, final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_cancel_confirm, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        Button btn_ok = view.findViewById(R.id.btn_ok);
        TextView tv_title = view.findViewById(R.id.tv_title);

        tv_title.setText(title);
        btn_cancel.setText(strNo);
        btn_ok.setText(strYes);
        StringUtils.setUnderline(tv_title);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickCancel();
                dialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectListener.onClickOK();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showRating(float rate, String doctorName, String suggestion, final OnRatingListener onRatingListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_doctor_rating, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_submit = view.findViewById(R.id.btn_submit);
        final RatingBar ratingBar = view.findViewById(R.id.ratetingBar);
        final EditText edSuggestion = view.findViewById(R.id.ed_suggestion);
        final TextView tvName = view.findViewById(R.id.tv_name);

        edSuggestion.setText(suggestion);
        ratingBar.setRating(rate);
        tvName.setText(doctorName);
        StringUtils.setUnderline(tvName);
        StringUtils.setUnderline(btn_submit);
        /*
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRatingListener.onCancel();
                dialog.dismiss();
            }
        });
        */
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRatingListener.onRating(ratingBar.getRating(), edSuggestion.getText().toString());
                dialog.dismiss();
            }
        });

        setCancelable(false);
        dialog.show();
    }

    public void showSuccess(String message, final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_success, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextView tv_message = view.findViewById(R.id.txtMessage);
        tv_message.setText(message);

        dialog.show();
        displayDelay(new Runnable() {
            @Override
            public void run() {
                onSelectListener.onClickOK();
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
    }

    public void showSignupSuccess(final OnSelectListener onSelectListener) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_signup_success, null);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.background_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.show();
        displayDelay(new Runnable() {
            @Override
            public void run() {
                onSelectListener.onClickOK();
                dialog.dismiss();
            }
        });
    }

    private void displayDelay(Runnable runnable) {
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(runnable, 3000);
    }

    public interface OnSelectListener {
        void onClickOK();

        void onClickCancel();
    }

    public interface OnPaymentListener {
        void onCreditCard();

        void onBankTranfer();
    }

    public interface OnOTPSelectListener {
        void onClickOK(String ReferenceCode, String otp);

        void onClickRenew();
    }

    public interface OnPasswordSelectListener {
        void onClickOK(String password);

        void onClickCancel();
    }

    public interface OnRatingListener {
        void onRating(float rate, String suggesstion);

        void onCancel();
    }
}
