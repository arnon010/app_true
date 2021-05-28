package com.truedigital.vhealth.ui.login.createpin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.utils.StringUtils;

public class CreatePINActivity extends BaseMvpActivity<CreatePINActivityInterface.Presenter>
        implements CreatePINActivityInterface.View {

    private TextView txtTitle;
    private EditText edtPIN;
    private EditText edtConfirmPIN;
    private Button btnConfirm;

    @Override
    protected CreatePINActivityInterface.Presenter createPresenter() {
        return CreatePINActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login_createpin;
    }

    @Override
    protected void bindView() {
        txtTitle = findViewById(R.id.txtTitle);
        edtPIN = findViewById(R.id.edtPIN);
        edtConfirmPIN = findViewById(R.id.edtConfirmPIN);
        btnConfirm = findViewById(R.id.btnConfirm);
    }

    @Override
    protected void initInstance() {
    }

    @Override
    protected void setupView() {
        txtTitle.setText(StringUtils.setNewLine(getResources().getString(R.string.create_pin_title)));

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(edtPIN, InputMethodManager.SHOW_IMPLICIT);
        edtPIN.requestFocus();

        btnConfirm.setOnClickListener(view -> loginPIN());
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
        getPresenter().onLogin(edtPIN.getText().toString(), edtConfirmPIN.getText().toString());
    }

    @Override
    public void onLoginSuccess() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
