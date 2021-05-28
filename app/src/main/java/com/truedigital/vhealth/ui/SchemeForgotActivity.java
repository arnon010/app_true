package com.truedigital.vhealth.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.truedigital.vhealth.ui.password.create.PasswordCreateActivity;

public class SchemeForgotActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        if (uri != null) {

            //String userName =  uri.getQueryParameter("UserId");
            int userId = new Integer(uri.getQueryParameter("UserId"));
            String email = uri.getQueryParameter("Email");
            String token = uri.getQueryParameter("Token");

            Intent intent = new Intent(this, PasswordCreateActivity.class);
            intent.putExtra("UserId", userId);
            intent.putExtra("Email", email);
            intent.putExtra("Token", token);
            startActivity(intent);

            finish();
        }

    }
}
