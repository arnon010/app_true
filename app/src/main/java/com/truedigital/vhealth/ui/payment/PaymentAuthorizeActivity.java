package com.truedigital.vhealth.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.ClientCertRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.truedigital.vhealth.R;

import timber.log.Timber;

public class PaymentAuthorizeActivity extends AppCompatActivity {

    public static final String EXTRA_AUTHORIZED_URL = "AUTHORIZED_URL";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_authorize);

        String authorized_url = getIntent().getStringExtra(EXTRA_AUTHORIZED_URL);
        WebView myWebView = findViewById(R.id.webview);
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
                Timber.i("onReceive");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Timber.i("onPageFinish %s", url);
                if (url.contains("/success")) {
                    setResult(RESULT_OK);
                    finish();
                } else if (url.contains("/fail")) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }
        });

        myWebView.loadUrl(authorized_url);
    }

    private void displayDelay(Runnable runnable) {
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(runnable, 5000);
    }
}
