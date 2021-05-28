package com.truedigital.vhealth.ui.login.pin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.utils.StringUtils;

public class LoginPINActivity extends BaseMvpActivity<LoginPINActivityInterface.Presenter>
        implements LoginPINActivityInterface.View {

    private TextView txtTitle;
    private EditText edtPIN;
    private ImageView btn_login;

    @Override
    protected LoginPINActivityInterface.Presenter createPresenter() {
        return LoginPINActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login_pin;
    }

    @Override
    protected void bindView() {
        txtTitle = findViewById(R.id.txtTitle);
        edtPIN = findViewById(R.id.edtPIN);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    protected void initInstance() {
    }

    @Override
    protected void setupView() {
        txtTitle.setText(StringUtils.setNewLine(getResources().getString(R.string.login_pin_title)));

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtPIN, InputMethodManager.SHOW_IMPLICIT);
        edtPIN.requestFocus();
        edtPIN.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                loginPIN();
                return true;
            }

            return false;
        });

        btn_login.setOnClickListener(v -> loginPIN());
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void loginPIN() {
        getPresenter().onLogin(edtPIN.getText().toString());
    }

    @Override
    public void onLoginSuccess() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
