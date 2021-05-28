package com.truedigital.vhealth.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.truedigital.vhealth.ui.password.create.PasswordCreateActivity;

public class SchemeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        if (uri != null) {

            String userName = uri.getQueryParameter("UserName");
            String email = uri.getQueryParameter("Email");
            String token = uri.getQueryParameter("Token");

            Intent intent = new Intent(this, PasswordCreateActivity.class);
            intent.putExtra("UserName", userName);
            intent.putExtra("Email", email);
            intent.putExtra("Token", token);
            startActivity(intent);

            finish();
        }

    }
}
