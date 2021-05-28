package com.truedigital.vhealth.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.dialog.MediaBottomSheetFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.Camera;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.PickFile;

import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_OK;
import static com.truedigital.vhealth.api.RetrofitBuilder.addMultipartBody;

public class SettingFragment extends BaseMvpFragment<SettingFragmentInterface.Presenter>
        implements SettingFragmentInterface.View {

    private static final String KEY_EDIT_MODE = "KEY_EDIT_MODE";
    private static final String KEY_SIGNUP_MODE = "KEY_SIGNUP_MODE";
    private static final String KEY_FORGOT_PASSWORD_MODE = "KEY_FORGOT_PASSWORD_MODE";
    private TextView tvLogin;
    private Button btnAppInfo;
    private Button btnConfirm;
    private ImageView imgProfile;
    private EditText edPassword;
    private EditText edPasswordConfirm;
    private EditText edUserName;
    private Uri uriImage;
    private Fragment fg;

    private boolean isSignupMode;
    private boolean isEditMode;
    private boolean isForgotPassword;

    private String oldPassword = "";
    private TextView tv_password;
    private TextView tv_password_confirm;

    public SettingFragment() {
        super();
    }

    public static SettingFragment newInstance(
            boolean isSignupMode,
            boolean isEditMode,
            boolean isForgotPassword,
            String referenceCode,
            String otp
    ) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putBoolean(KEY_SIGNUP_MODE, isSignupMode);
        args.putBoolean(KEY_EDIT_MODE, isEditMode);
        args.putBoolean(KEY_FORGOT_PASSWORD_MODE, isForgotPassword);
        args.putString(AppConstants.EXTRA_SIGNUP_REFERENC_CODE, referenceCode);
        args.putString(AppConstants.EXTRA_SIGNUP_OTP, otp);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkToken();
    }

    @Override
    public SettingFragmentInterface.Presenter createPresenter() {
        return SettingFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_setting;
    }

    @Override
    public void bindView(View view) {
        imgProfile = view.findViewById(R.id.card_image);
        tvLogin = view.findViewById(R.id.tvLogin);

        tv_password = view.findViewById(R.id.tv_password);
        tv_password_confirm = view.findViewById(R.id.tv_password_confirm);
        edPassword = view.findViewById(R.id.edPassword);
        edPasswordConfirm = view.findViewById(R.id.edPasswordConfirm);
        edUserName = view.findViewById(R.id.edUserName);
        btnAppInfo = view.findViewById(R.id.btn_appinfo);
        btnConfirm = view.findViewById(R.id.btn_confirm);
    }

    @Override
    public void setupInstance() {
        this.fg = this;
        this.isEditMode = getArguments().getBoolean(KEY_EDIT_MODE);
        this.isForgotPassword = getArguments().getBoolean(KEY_FORGOT_PASSWORD_MODE);
        this.isSignupMode = getArguments().getBoolean(KEY_SIGNUP_MODE);
    }

    @Override
    public void setupView() {
        btnConfirm.setEnabled(false);
        if (isEditMode) {
            ((MainActivity) getActivity()).showToolbar(R.string.setting_profile_title, false);
            btnAppInfo.setVisibility(View.VISIBLE);
            tv_password.setText(R.string.setting_profile_password);
            tv_password_confirm.setText(R.string.setting_profile_password_confirm);
        } else {
            btnAppInfo.setVisibility(View.GONE);
            tv_password.setText(R.string.setting_profile_password_create);
            tv_password_confirm.setText(R.string.setting_profile_password_confirm_create);
        }

        btnAppInfo.setOnClickListener(v -> openSettingApp());

        btnConfirm.setOnClickListener(v -> getPresenter().onConfirmButtonClick());
        imgProfile.setOnClickListener(onImageProfileClickListener());

        edUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showConfirmButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showConfirmButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showConfirmButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (getPresenter() != null) {
            updateView();
        }
    }

    private void checkToken() {
        String token = AppManager.getDataManager().getAccess_token();
        getPresenter().onCheckToken(token, new BaseMvpPresenter.OnTokenListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail() {
            }
        });
    }

    private View.OnClickListener onImageProfileClickListener() {
        return v -> pickImage();
    }

    @Override
    public boolean isChangePassword() {
        if (edPassword.getText().length() >= 6) {
            return getPassword().equals(getPasswordConfirm());
        }
        return false;
    }

    private boolean isValidPassword() {
        if (edPassword.getText().length() < 6) {
            return false;
        }
        return edPasswordConfirm.getText().length() >= 6;
    }

    private void showConfirmButton(boolean isShow) {
        btnConfirm.setEnabled(isShow);
    }

    private void showConfirmButton() {
        btnConfirm.setEnabled(isValidPassword() || isChangeUseName());
    }

    private boolean isChangeUseName() {
        return !edUserName.getText().toString().equals(AppManager.getDataManager().getUserName());
    }

    private void updateView() {
        if (isEditMode || isForgotPassword) {
            Glide.with(getActivity())
                    .load(AppManager.getDataManager().getProfileImage()).asBitmap()
                    .error(R.drawable.img_default)
                    .placeholder(R.drawable.img_default)
                    .into(imgProfile);
            edUserName.setText(AppManager.getDataManager().getUserName());
        }

        displayUserName();
    }

    private void displayUserName() {
        String userLoginInfo = "";
        String phone = AppManager.getDataManager().getPhone();
        if (phone != null && !phone.isEmpty()) {
            userLoginInfo = AppManager.getDataManager().getPhone();
            String userEmail = AppManager.getDataManager().getUserEmail();
            if (userEmail != null && !userEmail.isEmpty()) {
                userLoginInfo = userLoginInfo + " / " + AppManager.getDataManager().getUserEmail();
            }
        }
        tvLogin.setText(userLoginInfo);
    }

    @Override
    public void initialize() {
    }

    @Override
    public String getOTP() {
        return getArguments().getString(AppConstants.EXTRA_SIGNUP_OTP);
    }

    @Override
    public String getReferenceCode() {
        return getArguments().getString(AppConstants.EXTRA_SIGNUP_REFERENC_CODE);
    }

    @Override
    public String getPassword() {
        return edPassword.getText().toString();
    }

    @Override
    public String getPasswordConfirm() {
        return edPasswordConfirm.getText().toString();
    }

    @Override
    public String getOldPassword() {
        return oldPassword;
    }

    @Override
    public boolean isValid() {
        return isEditMode ? isValidUpdate() : isValidCreate();
    }

    @Override
    public boolean isEditMode() {
        return isEditMode;
    }

    @Override
    public void onErrorPassword() {
        edPassword.setError(getString(R.string.error_password));
        edPassword.requestFocus();
    }

    @Override
    public void onErrorConfirmPassword() {
        edPasswordConfirm.setError(getString(R.string.error_password_confirmed));
        edPasswordConfirm.requestFocus();
    }

    @Override
    public boolean isValidCreate() {
        if (edPassword.getText().length() < 6) {
            onErrorPassword();
            return false;
        }
        if (edPasswordConfirm.getText().length() < 6) {
            onErrorConfirmPassword();
            return false;
        }
        if (!edPassword.getText().toString().equals(edPasswordConfirm.getText().toString())) {
            onErrorConfirmPassword();
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidUpdate() {
        if (edPassword.getText().toString().isEmpty() && edPasswordConfirm.getText().toString().isEmpty()) {
            return true;
        } else {
            if (edPassword.getText().length() < 6) {
                onErrorPassword();
                return false;
            }
            if (edPasswordConfirm.getText().length() < 6) {
                onErrorConfirmPassword();
                return false;
            }
            if (!edPassword.getText().toString().equals(edPasswordConfirm.getText().toString())) {
                onErrorConfirmPassword();
                return false;
            }
        }
        return true;
    }

    @Override
    public MultipartBody.Part getImageBody() {
        return addMultipartBody(getActivity(), "ProfileImage", uriImage);
    }

    @Override
    public String getUserName() {
        return edUserName.getText().toString();
    }

    @Override
    public Uri getUriImage() {
        return uriImage;
    }

    @Override
    public void openSettingApp() {
        ((MainActivity) getActivity()).openSettingApp();
    }

    @Override
    public void openLogin() {
        ((MainActivity) getActivity()).openLogin();
    }

    private void pickImage() {
        final MediaBottomSheetFragment mediaBottomSheet = MediaBottomSheetFragment.newInstance((int) CommonUtils.convertDpToPixel(getActivity(), 200));
        mediaBottomSheet.show(getActivity().getSupportFragmentManager(), mediaBottomSheet.getTag());
        mediaBottomSheet.setOnClickListener(data -> {
            if (data.getMediaId() == AppConstants.REQUEST_PICK_IMAGE) {
                mediaBottomSheet.dismiss();
                PickFile.pickImageIntentWithPermission(getActivity(), fg, AppConstants.REQUEST_PICK_IMAGE);

            } else if (data.getMediaId() == AppConstants.REQUEST_CAMERA) {
                mediaBottomSheet.dismiss();
                Camera.takePhotosIntentWithPermission(getActivity(), fg, AppConstants.REQUEST_CAMERA);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstants.REQUEST_PICK_IMAGE_IMAGE_WRITE_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_PICK_IMAGE_IMAGE_READ_EXTERNAL_STORAGE:
                PickFile.pickImageIntentWithPermission(getActivity(), fg, AppConstants.REQUEST_PICK_IMAGE);
                break;
            case AppConstants.REQUEST_CAMERA_READ_WRITE_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_CAMERA_READ_READ_EXTERNAL_STORAGE:
            case AppConstants.REQUEST_CAMERA:
                Camera.takePhotosIntentWithPermission(getActivity(), fg, AppConstants.REQUEST_CAMERA);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uriImage = data.getData();
                showImage(uriImage.toString());
                showConfirmButton(true);
            }
        }
        if (requestCode == AppConstants.REQUEST_CAMERA && resultCode == RESULT_OK) {
            uriImage = Camera.moveFile(getContext());
            showImage(uriImage.toString());
            showConfirmButton(true);
        }
    }

    private void showImage(String imageUrl) {
        AppManager.getDataManager().setProfileImage(imageUrl);
        Glide.with(getActivity())
                .load(imageUrl).asBitmap()
                .error(R.drawable.ic_profile_user)
                .placeholder(R.drawable.ic_profile_user)
                .into(imgProfile);
    }

    private String getTitleSuccess() {
        //..default string for edit mode
        int resString = R.string.setting_profile_success;
        if (isForgotPassword) {
            resString = R.string.forgot_password_success;
        }

        return getString(resString);
    }

    @Override
    public void showSuccess() {
        if (isSignupMode) {
            AppManager.getDataManager().setStatus(AppConstants.APP_STATUS_NORMAL);
            showSignupSuccess();
        } else {
            showEditSuccess();
        }
    }

    @Override
    public void showSignupSuccess() {
        new MyDialog(getActivity()).showSignupSuccess(new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                ((SettingActivity) getActivity()).openMainActivity();
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    @Override
    public void showEditSuccess() {
        new MyDialog(getActivity()).showSuccess(getTitleSuccess(), new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                if (isEditMode) {
                    if (getUriImage() != null) {
                        AppManager.getDataManager().setProfileImage(getUriImage().toString());
                    }

                    AppManager.getDataManager().setUserName(getUserName());
                    edPassword.setText("");
                    edPasswordConfirm.setText("");
                    oldPassword = "";
                    updateView();
                } else {
                    ((SettingActivity) getActivity()).openMainActivity();
                }
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    @Override
    public void openOldPassword() {
        new MyDialog(getActivity()).showDialogOldPassword(new MyDialog.OnPasswordSelectListener() {
            @Override
            public void onClickOK(String password) {
                oldPassword = password;
                getPresenter().updateAccount();
            }

            @Override
            public void onClickCancel() {
            }
        });
    }
}
