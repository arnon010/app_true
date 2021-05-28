package com.truedigital.vhealth.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.manager.FacebookManager;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.login.email.LoginEmailActivity;
import com.truedigital.vhealth.ui.login.phone.LoginPhoneActivity;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.setting.info.SettingAppFragment;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CommonUtils;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.SpannableText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songkrit on 11/14/2016 AD.
 */

public class LoginActivity extends BaseMvpActivity<LoginActivityInterface.Presenter>
        implements LoginActivityInterface.View {

    private String TAG = "LoginActivity";
    private CallbackManager callbackManagerFb;
    private RelativeLayout layout_login_phone;
    private RelativeLayout layout_login_facebook;
    private RelativeLayout layout_login_email;
    private LoginButton loginButtonFacebook;
    private TextView tvConsentInfo;

    @Override
    protected LoginActivityInterface.Presenter createPresenter() {
        return LoginActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void bindView() {
        layout_login_phone = (RelativeLayout) findViewById(R.id.layout_login_phone);
        layout_login_facebook = (RelativeLayout) findViewById(R.id.layout_login_facebook);
        layout_login_email = (RelativeLayout) findViewById(R.id.layout_login_email);
        loginButtonFacebook = (LoginButton) findViewById(R.id.loginbuttonFacebook);
        tvConsentInfo = (TextView) findViewById(R.id.tv_login_consent_info);
    }

    @Override
    protected void initInstance() {
        initFacebookCallBack();
    }

    @Override
    protected void setupView() {
        Log.e(TAG, "Locale lang " + CommonUtils.getLocalLanguage());
        if (!AppManager.getDataManager().isSetLocalFirst()) {
            AppManager.getDataManager().setLocalFirst(true);
            onSetLanguage();
        }

        if (AppManager.getDataManager().isDoctor()) {
            layout_login_email.setVisibility(View.VISIBLE);
            layout_login_phone.setVisibility(View.GONE);
        } else {
            layout_login_email.setVisibility(View.GONE);
            layout_login_phone.setVisibility(View.VISIBLE);
        }

        layout_login_phone.setOnClickListener(v -> openLoginWithPhone());
        layout_login_email.setOnClickListener(v -> openLoginWithEmail());
        layout_login_facebook.setOnClickListener(v -> openLoginFacebook());

        setUpConsentTextClickable();
    }

    private void setUpConsentTextClickable() {
        tvConsentInfo.setText(
                SpannableText.setClickableSpan(
                        this,
                        R.string.login_consent_info,
                        R.string.login_consent_info_link,
                        R.string.login_consent_privacy_link,
                        R.color.color_red,
                        textView -> {
                            String title = getString(R.string.setting_app_term);
                            String urlTerm = BuildConfig.SERVER_BASE_INFO + SettingAppFragment.ENDPOINT_TERM;
                            new MyDialog(this).showWebview(title, urlTerm, new MyDialog.OnSelectListener() {
                                @Override
                                public void onClickOK() {
                                }

                                @Override
                                public void onClickCancel() {
                                }
                            });
                        },
                        textView -> {
                            String title = getString(R.string.setting_app_privacy);
                            String urlTerm = BuildConfig.SERVER_BASE_INFO + SettingAppFragment.ENDPOINT_PRIVACY;
                            new MyDialog(this).showWebview(title, urlTerm, new MyDialog.OnSelectListener() {
                                @Override
                                public void onClickOK() {
                                }

                                @Override
                                public void onClickCancel() {
                                }
                            });
                        }
                )
        );

        tvConsentInfo.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void onSetLanguage() {
        String lang = CommonUtils.getLocalLanguage();
        if (lang.contains(AppConstants.LOCAL_LANG_THAI)) {
            setLanguage(AppConstants.LOCAL_LANG_THAI);
        } else {
            setLanguage(AppConstants.LOCAL_LANG_ENG);
        }
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void openLoginWithPhone() {
        Intent intent = new Intent(this, LoginPhoneActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void openLoginWithEmail() {
        Intent intent = new Intent(this, LoginEmailActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void openLoginFacebook() {
        if (FacebookManager.isLoginFacebook()) {
            FacebookManager.logoutFacebook();
        }
        FacebookManager.loginFacebook(loginButtonFacebook);
    }

    @Override
    public void openLoginOTP(String referenceOTP) {

    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void LoginOtpClick(View view) {
        getPresenter().onRequestOTPClick();
    }

    public void LoginEmailClick(View view) {
        getPresenter().onEmailLoginClick();
    }

    public void loginFacebookButtonClick(View view) {

    }

    private void initFacebookCallBack() {
        FacebookSdk.isInitialized();
        callbackManagerFb = FacebookManager.initFacebookLogin(loginButtonFacebook, new FacebookManager.OnCallbackFacebook() {
            String email;
            String facebook_id;
            String facebook_token;
            String firstName;
            String lastname;

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Insert your code here

                                try {

                                    AccessToken token = AccessToken.getCurrentAccessToken();
                                    Log.d("access only Token is", String.valueOf(token.getToken()));
                                    facebook_token = String.valueOf(token.getToken());

                                    facebook_id = object.getString("id");
                                    firstName = object.getString("first_name");
                                    lastname = object.getString("last_name");
                                    JSONObject profile = object.getJSONObject("picture");
                                    JSONObject data = profile.getJSONObject("data");
                                    String url = data.getString("url");
                                    Log.e(TAG, email + " : " + facebook_id + " : " + firstName + " : " + lastname);
                                    email = object.getString("email");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                getPresenter().callLoginFacebook(facebook_id, facebook_token);
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,id,first_name,last_name, picture{url}");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.e(TAG, "facebook cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG, "facebook error" + exception.getMessage());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // result facebook
        callbackManagerFb.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
