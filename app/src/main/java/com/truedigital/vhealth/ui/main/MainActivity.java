package com.truedigital.vhealth.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.custom.NetworkStatusReceiver;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.manager.bus.MessageEvent;
import com.truedigital.vhealth.model.BloodPressureObject;
import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.model.ChildGrowthCriteriaObject;
import com.truedigital.vhealth.model.CongenitalDiseaseObject;
import com.truedigital.vhealth.model.DailyBloodPressureObject;
import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.model.FoodAllergyObject;
import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ItemCategoryDao;
import com.truedigital.vhealth.model.ItemClinicDao;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ItemSubCategoryDao;
import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.model.MedicineAllergyObject;
import com.truedigital.vhealth.model.PregnantHistoryObject;
import com.truedigital.vhealth.model.RowHeaderVaccineObject;
import com.truedigital.vhealth.model.SystemConfigurationObject;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.address.AddressFragment;
import com.truedigital.vhealth.ui.appointment.AppointmentFragment;
import com.truedigital.vhealth.ui.appointment.cancel.AppointmentCancelFragment;
import com.truedigital.vhealth.ui.appointment.confirm.AppointmentConfirmFragment;
import com.truedigital.vhealth.ui.appointment.create.AppointmentCreateFragment;
import com.truedigital.vhealth.ui.appointment.createdetail.AppointmentCreateDetailFragment;
import com.truedigital.vhealth.ui.appointment.detail.AppointmentDetailFragment;
import com.truedigital.vhealth.ui.appointment.success.AppointmentSuccessFragment;
import com.truedigital.vhealth.ui.articles.ArticlesFragment;
import com.truedigital.vhealth.ui.articles.detail.ArticlesDetailFragment;
import com.truedigital.vhealth.ui.articles.patient.ArticlesPatientDetailFragment;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.doctor.DoctorCalendarFragment;
import com.truedigital.vhealth.ui.doctornote.confirm.DoctorNoteConfirmFragment;
import com.truedigital.vhealth.ui.ehr.EhrFragment;
import com.truedigital.vhealth.ui.ehr.doctornote.DoctorNoteDetailFragment;
import com.truedigital.vhealth.ui.ehr.doctornote.DoctorNoteFragment;
import com.truedigital.vhealth.ui.ehr.foodallergy.FoodAllergyFragment;
import com.truedigital.vhealth.ui.ehr.foodallergy.FormFoodAllergyFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.HealthInformationMenuFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure.BloodPressureFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure.FormBloodPressureFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure.ListDailyBloodPressureFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.bloodpressure.ListTimeBloodPressureFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar.BloodSugarFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar.FormBloodSugarFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar.ListDailyBloodSugarFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.bloodsugar.ListTimeBloodSugarFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.childgrowth.ChildGrowthFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.congenitaldisease.CongenitalDiseaseFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.congenitaldisease.FormCongenitalDiseaseFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate.FormHeartBeatRateFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate.HeartBeatRateFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate.ListDailyHeartBeatRateFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate.ListTimeHeartBeatRateFragment;
import com.truedigital.vhealth.ui.ehr.healthinformation.menstrualperiod.MenstrualPeriodFragment;
import com.truedigital.vhealth.ui.ehr.laboratory.FormLaboratoryOtherFragment;
import com.truedigital.vhealth.ui.ehr.laboratory.LaboratoryFragment;
import com.truedigital.vhealth.ui.ehr.medicationhistory.FormMedicationHistoryChiiwiiLiveFragment;
import com.truedigital.vhealth.ui.ehr.medicationhistory.FormMedicationHistoryFragment;
import com.truedigital.vhealth.ui.ehr.medicationhistory.MedicationHistoryFragment;
import com.truedigital.vhealth.ui.ehr.medicineallergy.FormMedicineAllergyFragment;
import com.truedigital.vhealth.ui.ehr.medicineallergy.MedicineAllergyFragment;
import com.truedigital.vhealth.ui.ehr.pregnanthistory.FormPregnantHistoryFragment;
import com.truedigital.vhealth.ui.ehr.pregnanthistory.PregnantHistoryFragment;
import com.truedigital.vhealth.ui.ehr.vaccinationhistory.VaccinationHistoryFragment;
import com.truedigital.vhealth.ui.ehr.vaccinationhistory.VaccineDetailFragment;
import com.truedigital.vhealth.ui.home.PatientHomeNewsFragment;
import com.truedigital.vhealth.ui.home.doctor.DoctorDetailFragment;
import com.truedigital.vhealth.ui.home.doctor.ListDoctorFragment;
import com.truedigital.vhealth.ui.home.patient.PatientHomeFragment;
import com.truedigital.vhealth.ui.home.subcategory.SubCategoryFragment;
import com.truedigital.vhealth.ui.login.LoginActivity;
import com.truedigital.vhealth.ui.login.createpin.CreatePINActivity;
import com.truedigital.vhealth.ui.login.pin.LoginPINActivity;
import com.truedigital.vhealth.ui.meeting.chat.ChatActivity;
import com.truedigital.vhealth.ui.meeting.videocall.VideoCallActivity;
import com.truedigital.vhealth.ui.meeting.voice.VoiceCallActivity;
import com.truedigital.vhealth.ui.payment.PaymentFragment;
import com.truedigital.vhealth.ui.product.ListProductsFragment;
import com.truedigital.vhealth.ui.product.confirm.ProductConfirmFragment;
import com.truedigital.vhealth.ui.product.detail.ProductDetailFragment;
import com.truedigital.vhealth.ui.setting.info.SettingAppFragment;
import com.truedigital.vhealth.ui.setting.lang.SettingLangFragment;
import com.truedigital.vhealth.ui.shipping.ShippingStatusFragment;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.AppointmentUtil;
import com.truedigital.vhealth.utils.ContactTypeUtil;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.ImageUtils;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.PlaySound;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.identity.Registration;
import timber.log.Timber;

import static com.truedigital.vhealth.api.RetrofitBuilder.setCurrentPage;

public class MainActivity extends BaseMvpActivity<MainActivityInterface.Presenter>
        implements MainActivityInterface.View,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final String KEY_TAB_POSITION = "KEY_TAB_POSITION";

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BottomNavigationView bottomNavigationView;
    private View toolbarMain;
    private ImageButton ic_button_back;
    private ImageButton ic_button_add;
    private int tabPosition = -1;

    private Button btn_callingBar;
    private BottomSheetBehavior mBottomSheetBehavior;
    private LinearLayout callingBar;

    private SystemConfigurationObject configuration;

    private MainActivity.OnAddClickListener onAddClickListener;
    private TextView tv_toolbar_name;
    private RelativeLayout toolbarImage;
    private ImageView ivToolbarImage;
    //private LinearLayout layout_thumbnail;

    private static boolean createAppointment;
    private TextView tv_callingbar_detail;
    private FloatingActionButton fab_call_center;
    private ImageView ivChat;

    private NetworkStatusReceiver mNetworkStatusReceiver;
    private FirebaseAnalytics mFirebaseAnalytics;

    public static void startintent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public interface OnAddClickListener {
        void onAddClick();
    }

    public void setAddOnClickListener(MainActivity.OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
    }

    @Override
    protected MainActivityInterface.Presenter createPresenter() {
        return MainActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            tabPosition = savedInstanceState.getInt(KEY_TAB_POSITION, -1);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void bindView() {
        appBarLayout = findViewById(R.id.app_bar);
        //collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);

        bindViewToolbar();

        toolbarMain = findViewById(R.id.toolbarMain);
        ic_button_back = toolbarMain.findViewById(R.id.ic_button_back);
        ic_button_add = toolbarMain.findViewById(R.id.ic_button_add);
        //mToolbar = findViewById(R.id.toolBar);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        setSupportActionBar(toolbar);

        callingBar = findViewById(R.id.calling_bar);
        tv_callingbar_detail = findViewById(R.id.tv_callingbar_detail);
        btn_callingBar = callingBar.findViewById(R.id.btn_calling);

        //..intercom
        fab_call_center = findViewById(R.id.fab_call_center);
        ivChat = findViewById(R.id.ivChat);

        //mBottomSheetBehavior = BottomSheetBehavior.from(callingBar);
    }

    @SuppressLint("RestrictedApi")
    public void showFabCallCenter() {
        fab_call_center.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    public void hideFabCallCenter() {
        fab_call_center.setVisibility(View.GONE);
    }

    private void bindViewToolbar() {
        toolbarMain = findViewById(R.id.toolbarMain);
        ic_button_back = toolbarMain.findViewById(R.id.ic_button_back);
        tv_toolbar_name = toolbarMain.findViewById(R.id.tvToolbarName);
        toolbarImage = toolbarMain.findViewById(R.id.toolbar_image);
        ivToolbarImage = toolbarImage.findViewById(R.id.ivToolbarImage);

        //layout_thumbnail = (LinearLayout) toolbarMain.findViewById(R.id.layout_thumbnail);
    }

    @Override
    protected void initInstance() {
        regisIntercomUser();
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);
    }

    private void regisIntercomUser() {
        Registration registration = Registration.create().withUserId(AppManager.getDataManager().getUserName());
        Intercom.client().registerIdentifiedUser(registration);
        Intercom.client().setInAppMessageVisibility(Intercom.Visibility.GONE);
        Intercom.client().hideMessenger();
    }

    private View.OnClickListener onClickLineChat() {
        return v -> openLineChat();
    }

    private void openLineChat() {
        Uri uri = Uri.parse(BuildConfig.LINE_SOCIAL_ACCOUNT);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void refreshData() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_TAB_POSITION, tabPosition);
    }

    @Override
    protected void setupView() {
        //setupDrawer();
        //setupToolbar();
        //setupSlideNews();
        setupBottomNavigation();
        showToolbarMain(false);
        //hideCallingBar();
        ic_button_back.setOnClickListener(v -> onBackPressed());
        ic_button_add.setOnClickListener(v -> onAddClickListener.onAddClick());
        btn_callingBar.setOnClickListener(v -> {
            try {
                PlaySound.getInstance().stop();
            } catch (Exception ignored) {
            }

            openRoom(AppManager.getDataManager().getAppointmentNumber(),
                    AppManager.getDataManager().getContactTypeCode());
        });

        fab_call_center.setOnClickListener(onClickLineChat());
        ivChat.setOnClickListener(onClickLineChat());

        boolean isNotificationEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled();
        if (!isNotificationEnabled) {
            goToNotificationSettings(null, this);
        }

        mNetworkStatusReceiver = new NetworkStatusReceiver(this);
        mNetworkStatusReceiver.setCallback(isConnected -> {
            if (AppManager.isActivityVisible()) {
                if (!isConnected) {
                    showMessage(R.string.network_disconnect);
                }
            }
        });
        if (!isNetworkConnected()) {
            showMessage(R.string.network_disconnect);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (tabPosition == -1) {
            checkToken();
        } else {
            selectMenu(tabPosition);
        }
    }

    private void checkToken() {
        String token = AppManager.getDataManager().getAccess_token();
        getPresenter().onCheckToken(token, new BaseMvpPresenter.OnTokenListener() {
            @Override
            public void onSuccess() {
                checkVersion();
                getPresenter().getSystemConfigurationApi();
                selectMenu(MENU_HOME);
            }

            @Override
            public void onFail() {
                getPresenter().onLogout();
                openLogin();
            }
        });
    }

    public void navigateToLoginEhr() {
        removeBackStack();
        selectMenu(MENU_HOME);
        checkTokenForEhr();
    }

    private void checkTokenForEhr() {
        String token = AppManager.getDataManager().getAccess_token();
        getPresenter().onCheckToken(token, new BaseMvpPresenter.OnTokenListener() {
            @Override
            public void onSuccess() {
                getPresenter().getPINStatus();
            }

            @Override
            public void onFail() {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        registerNetwork();

        boolean isTimeToCall = AppManager.getDataManager().isTimeToCall();
        if (isTimeToCall) {
            checkAppointment();
            //showCallingBar();
        } else {
            hideCallingBar();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetwork();
    }

    private void registerNetwork() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mNetworkStatusReceiver, filter);
    }

    private void unregisterNetwork() {
        if (mNetworkStatusReceiver != null) {
            unregisterReceiver(mNetworkStatusReceiver);
        }
    }

    private void checkAppointment() {
        getPresenter().callServiceGetAppointment(AppManager.getDataManager().getAppointmentNumber());
    }

    @Override
    public void canStartAppointment(boolean canStart) {
        if (canStart) {
            showCallingBar();
        } else {
            AppManager.getDataManager().setTimeToCall(false);
            hideCallingBar();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void checkVersion() {
        getPresenter().getApplicationVersion();
    }

    @Override
    public void foundNewVersion() {
        new MyDialog(this).showUpdateApp(new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                updateApp();
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    private void updateApp() {
        String appUrl = BuildConfig.APPLICATION_URL;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
        startActivity(browserIntent);
    }

    public void goToNotificationSettings(String channel, Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (channel != null) {
                intent.setAction(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
            } else {
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            }
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSetLanguage(String language) {
        setLanguage(language);
    }

    @Override
    public void showToolbarMain(boolean isShow) {
        toolbarMain.setVisibility(isShow ? View.VISIBLE : View.GONE);
        toolbarMain.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_toolbar));
        toolbarImage.setVisibility(View.GONE);
    }

    public void showToolbarMain(String title) {
        toolbarMain.setVisibility(View.VISIBLE);
        TextView tvTitle = toolbarMain.findViewById(R.id.tvToolbarName);
        tvTitle.setText(title);
        toolbarMain.findViewById(R.id.toolbar_image).setVisibility(View.GONE);
        setShowBackButton(false);
        toolbarMain.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_toolbar));
        toolbarImage.setVisibility(View.GONE);

    }

    @Override
    public void showToolbar(int resId, boolean showHomeAsUp) {
        showToolbar(getString(resId), showHomeAsUp);
        toolbarMain.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_toolbar));
        toolbarImage.setVisibility(View.GONE);
    }

    @Override
    public void showToolbar(String title, boolean showHomeAsUp) {
        tv_toolbar_name.setText(title);
        setShowBackButton(showHomeAsUp);
        toolbarMain.setVisibility(View.VISIBLE);
        toolbarMain.findViewById(R.id.ivToolbarDoctorImage).setVisibility(View.GONE);
        toolbarMain.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_toolbar));
        toolbarImage.setVisibility(View.GONE);
    }

    @Override
    public void showToolbar(String title, boolean showHomeAsUp, String urlImage) {
        tv_toolbar_name.setText(title);
        setShowBackButton(showHomeAsUp);
        toolbarMain.setVisibility(View.VISIBLE);
        toolbarMain.findViewById(R.id.ivToolbarDoctorImage).setVisibility(View.GONE);
        toolbarMain.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_toolbar));

        ImageUtils.show(this, ivToolbarImage, urlImage);
        toolbarImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToolbar(boolean showHomeAsUp, String image) {
        tv_toolbar_name.setText("");
        setShowBackButton(showHomeAsUp);
        toolbarMain.setVisibility(View.VISIBLE);

        ImageView imgProfile = toolbarMain.findViewById(R.id.ivToolbarDoctorImage);
        imgProfile.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(image).asBitmap()
                .error(R.drawable.ic_profile_user)
                .placeholder(R.drawable.ic_profile_user)
                .into(imgProfile);

        toolbarMain.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_toolbar));
        toolbarImage.setVisibility(View.GONE);
    }

    @Override
    public void showToolbar(int resId, boolean showHomeAsUp, boolean hideBgBottom) {
        showToolbar(getString(resId), showHomeAsUp, hideBgBottom);
    }

    @Override
    public void showToolbar(String title, boolean showHomeAsUp, boolean hideBgBottom) {
        tv_toolbar_name.setText(title);
        setShowBackButton(showHomeAsUp);
        toolbarMain.setVisibility(View.VISIBLE);
        if (hideBgBottom) {
            toolbarMain.setBackground(null);
        }
        //layout_thumbnail.setVisibility(View.GONE);
        toolbarImage.setVisibility(View.GONE);
    }

    @Override
    public void setShowBackButton(boolean isShow) {
        ic_button_back.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setSwipeRefreshLayout(boolean isEnabled) {
        this.swipeRefreshLayout.setEnabled(isEnabled);
    }

    @Override
    public void hideToolbar() {
        toolbarMain.setVisibility(View.GONE);
    }

    @Override
    public void updateToolbar(String title, boolean showImage, String urlImageDoctor, boolean showBtnAdd) {
        TextView tvToolbarName = toolbarMain.findViewById(R.id.tvToolbarName);
        ImageView imgDoctor = toolbarMain.findViewById(R.id.ivToolbarDoctorImage);
        RelativeLayout layout_imgProfile = toolbarMain.findViewById(R.id.toolbar_image);
        ImageButton button_add = toolbarMain.findViewById(R.id.ic_button_add);

        tvToolbarName.setText(title);

        if (showBtnAdd) {
            button_add.setVisibility(View.VISIBLE);
        } else {
            button_add.setVisibility(View.GONE);
        }

        if (showImage) {
            if (urlImageDoctor == null || urlImageDoctor.equals("")) {
                layout_imgProfile.setVisibility(View.VISIBLE);
                imgDoctor.setVisibility(View.GONE);
            } else {
                layout_imgProfile.setVisibility(View.GONE);
                imgDoctor.setVisibility(View.VISIBLE);
                showImage(urlImageDoctor);
            }
        } else {
            layout_imgProfile.setVisibility(View.GONE);
            imgDoctor.setVisibility(View.GONE);
        }
    }

    private void showImage(String imageUrl) {
        ImageView imgDoctor = toolbarMain.findViewById(R.id.ivToolbarDoctorImage);
        final ProgressBar progressBar = toolbarMain.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.image_doctor_test)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .override(100, 100)
                .placeholder(R.drawable.img_iph_defaultimg2x)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgDoctor);
    }

    private void showImageDoctor(String urlImage) {
        ImageView imgDoctor = toolbarMain.findViewById(R.id.ivToolbarDoctorImage);
        Glide.with(this)
                .load(urlImage).asBitmap()
                .dontTransform()
                .error(R.drawable.ic_profile_user)
                .placeholder(R.drawable.ic_profile_user)
                .into(imgDoctor);
        imgDoctor.setVisibility(View.VISIBLE);
    }

    private void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int position = MENU_HOME;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    position = MENU_HOME;
                    break;
                case R.id.navigation_heart:
                    position = MENU_EHR;
                    break;
                case R.id.navigation_time:
                    position = MENU_APPOINTMENT;
                    break;
                case R.id.navigation_setting:
                    position = MENU_SETTING;
                    break;
            }

            selectMenu(position);
            return false;
        });
    }

    private void removeBackStack() {
        int fragmentCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < fragmentCount; i++) {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void clearBackStack() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void selectMenu(final int position) {
        if (tabPosition == position) return;

        Fragment fragment = null;
        switch (position) {
            case MENU_HOME:
                fragment = PatientHomeFragment.newInstance();
                break;
            case MENU_EHR:
                checkTokenForEhr();
                break;
            case MENU_APPOINTMENT:
                fragment = AppointmentFragment.newInstance();
                break;
            case MENU_SETTING:
                fragment = SettingAppFragment.newInstance();
                break;
        }

        if (fragment != null) {
            replaceFragment(fragment);
            tabPosition = position;
        }

        bottomNavigationView.getMenu().getItem(tabPosition).setChecked(true);

        if (AppManager.getDataManager().isMustRefreshToken()) {
            token_expired();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AppConstants.REQUEST_CODE_EHR_PIN == requestCode && resultCode == Activity.RESULT_OK) {
            tabPosition = MENU_EHR;
            bottomNavigationView.getMenu().getItem(tabPosition).setChecked(true);
            replaceFragment(EhrFragment.newInstance());
        }

        if (AppConstants.REQUEST_CODE_ROOM == requestCode && resultCode == Activity.RESULT_OK) {
            //tabPosition = 2;
            //bottomNavigationView.getMenu().getItem(tabPosition).setChecked(true);
            String appointmentNumber = data.getStringExtra(AppConstants.EXTRA_APPOINTMENT_NUMBER);
            String appointmentStatus = data.getStringExtra(AppConstants.EXTRA_APPOINTMENT_STATUS);
            if (appointmentStatus.equals(AppConstants.APPOINTMENT_STATUS_STOPCALL)) {
                reset_callingBar();
                openAppointmentHistory(appointmentNumber);
            }
        }
        if (AppConstants.REQUEST_CODE_AUTHORIZE_PAYMENT == requestCode && resultCode == Activity.RESULT_OK) {
            Timber.e("onActivityResult %s", requestCode);
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof PaymentFragment) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    private void reset_callingBar() {
        AppManager.getDataManager().setTimeToCall(false);
        hideCallingBar();
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            setCurrentPage(fragment.getClass().getName());
            //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.content_main, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commitAllowingStateLoss();
        }
    }

    public void addFragment(Fragment fragment) {
        if (fragment != null) {
            setCurrentPage(fragment.getClass().getName());
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.content_main, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commitAllowingStateLoss();
        }
    }

    public void addFragment(Fragment fragment, String fragmentTag) {
        if (fragment != null) {
            setCurrentPage(fragment.getClass().getName());
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.content_main, fragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .commitAllowingStateLoss();
        }
    }

    private void addtoFragment(Fragment fragment, String fragmentTag) {
        if (fragment != null) {
            setCurrentPage(fragment.getClass().getName());
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .add(R.id.content_main, fragment, fragmentTag)
                    .addToBackStack(fragmentTag)
                    .commitAllowingStateLoss();
        }
    }

    public void removeToFragment(String fragmentTag) {
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentByTag(fragmentTag);
        if (fragment != null) {
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
            fm.popBackStack(fragmentTag, 1);
        }
    }

    @Override
    protected void initialize() {
    }

    private void addToCalendar() {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        String appointmentTime = "2020-03-03T16:00:00";//yyyy-MM-dd'T'HH:mm:ss
        long startDate = ConvertDate.dateToMillis(appointmentTime);
        //long endTime = startDate + 1000 * 15 * 60;
        long endDate = startDate + (1000 * 15 * 60); // For next 1hr

        intent.setType("vnd.android.cursor.item/event");

        //intent.putExtra("allDay", true);
        //intent.putExtra("rrule", "FREQ=YEARLY");

        intent.putExtra("beginTime", startDate);
        intent.putExtra("endTime", endDate);

        //intent.putExtra("dtstart", startDate);
        //intent.putExtra("dtend", endDate);

        intent.putExtra("title", getString(R.string.appointment_time));
        String toTime = ConvertDate.StringDateServiceFormatTime(appointmentTime);
        intent.putExtra("description", getString(R.string.appointment_time) + " " + toTime);
        intent.putExtra("hasAlarm", 1);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 1) {
            setCurrentPage(fragmentManager.getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 2).getName());
            fragmentManager.popBackStackImmediate();
            Timber.e("Fragment backStack %s", getSupportFragmentManager().getBackStackEntryCount());
        }
    }

    private void exitConfirm() {
        String title = "";
        String message = getString(R.string.dialog_exit_conversation);
        new MyDialog(this).showMessage(title, message, new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                finish();
            }

            @Override
            public void onClickCancel() {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //getPresenter().onLogout();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        return true;
    }


    @Override
    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void setupSlideNews() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = PatientHomeNewsFragment.newInstance(0);

        if (fragment != null) {
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.content_home_news, fragment);
            ft.commitAllowingStateLoss();
        }
    }

    public void newsCollapse(Boolean isExpand) {
        //appBarLayout.setExpanded(isExpand,true);
    }

    @Override
    public void openAddress() {
        addFragment(AddressFragment.newInstance("", 0, 0));
    }

    @Override
    public void openSettingApp() {
        addFragment(SettingAppFragment.newInstance());
        //showToolbar(R.string.setting_app_title,true);
    }

    @Override
    public void openSettingAppLanguage() {
        addFragment(SettingLangFragment.newInstance());
        //showToolbar(R.string.setting_lang_title,true);
    }

    @Override
    public void openListProducts(int groupId) {
        addFragment(ListProductsFragment.newInstance(groupId));
    }

    @Override
    public void openProductDetail(int id) {
        addFragment(ProductDetailFragment.newInstance(id));
    }

    @Override
    public void openProductConfirm(int id) {
        addFragment(ProductConfirmFragment.newInstance(id));
    }

    public void openListSubCategory(int id) {
        addFragment(SubCategoryFragment.newInstance(id));
        //showToolbarMain(true);
        //updateToolbar("","");
    }

    @Override
    public void openListArticles(int doctorId, String doctorName, int articleGroupId) {
        addFragment(ArticlesFragment.newInstance(doctorId, doctorName, articleGroupId));
    }

    @Override
    public void openArticleDetail(int articleId) {
        if (AppManager.getDataManager().isDoctor()) {
            addFragment(ArticlesDetailFragment.newInstance(articleId));
        } else {
            openArticlePatientDetail(articleId);
        }
    }

    @Override
    public void openArticleDetail(ItemArticleDao data) {
        if (AppManager.getDataManager().isDoctor()) {
            //
        } else {
            openArticlePatientDetail(data);
        }
    }

    @Override
    public void openArticlePatientDetail(int articleId) {
        addFragment(ArticlesPatientDetailFragment.newInstance(articleId));
    }

    @Override
    public void openSimilarArticle(int articleId) {
        replaceFragment(ArticlesPatientDetailFragment.newInstance(articleId));
    }

    @Override
    public void openArticlePatientDetail(ItemArticleDao data) {
        addFragment(ArticlesPatientDetailFragment.newInstance(data));
    }

    public void openListDoctor(ItemCategoryDao itemCategoryDao) {
        addFragment(ListDoctorFragment.newInstance(ListDoctorFragment.SORTBY_CATEGORY, itemCategoryDao));
    }

    public void openListDoctor(ItemClinicDao itemClinicDao) {
        addFragment(ListDoctorFragment.newInstance(ListDoctorFragment.SORTBY_CLINIC, itemClinicDao));
    }

    public void openListDoctor(ItemSubCategoryDao itemSubCategoryDao) {
        addFragment(ListDoctorFragment.newInstance(ListDoctorFragment.SORTBY_SUB_CATEGARY, itemSubCategoryDao));
    }

    @Override
    public void openDoctorDetail(int doctorId) {
        addFragment(DoctorDetailFragment.newInstance(doctorId));
    }

    @Override
    public void openDoctorDetail(ItemDoctorDao data) {
        //addFragment(DoctorDetailFragment.newInstance(data));
    }

    @Override
    public void openDoctorCalendar(int doctorId) {
        addFragment(DoctorCalendarFragment.newInstance(doctorId));
    }

    public static boolean isCreateAppointment() {
        return createAppointment;
    }

    public static void setCreateAppointment(boolean createAppointment) {
        MainActivity.createAppointment = createAppointment;
    }

    @Override
    public void gotoAppointment() {
        selectMenu(MENU_APPOINTMENT);
    }

    @Override
    public void openAppointmentCreateFromArticle(int doctorId) {
        getSupportFragmentManager().popBackStackImmediate(2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        addFragment(DoctorDetailFragment.newInstance(doctorId));
    }

    @Override
    public void openAppointmentCreate(ItemDoctorDao data) {
        addFragment(AppointmentCreateFragment.newInstance(data));
        FirebaseAddEventsAddToCart(data.getDoctorId(), data.getName(), data.getPrice());
    }

    private void FirebaseAddEventsAddToCart(int id, String itemName, double price) {
        Bundle items = new Bundle();
        items.putString(FirebaseAnalytics.Param.ITEM_ID, "" + id);
        items.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        items.putDouble(FirebaseAnalytics.Param.PRICE, price);
        items.putString(FirebaseAnalytics.Param.CURRENCY, "THB");
        items.putDouble(FirebaseAnalytics.Param.QUANTITY, 1);

        Bundle bundle = new Bundle();
        bundle.putBundle("items", items);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);
    }

    @Override
    public void openAppointmentCreateDetail(ItemDoctorDao data) {
        addFragment(AppointmentCreateDetailFragment.newInstance(data));
    }

    @Override
    public void openAppointmentConfirm(ItemDoctorDao data) {
        addFragment(AppointmentConfirmFragment.newInstance(data));
    }

    @Override
    public void openAppointmentSuccess(ItemDoctorDao data, ApiAppointmentRequest appointmentData, double discountPrice) {
        replaceFragment(AppointmentSuccessFragment.newInstance(data, appointmentData, discountPrice));
    }

    @Override
    public void openAppointmentDetail(int appointmentType, String appointmentNumber) {
        addFragment(AppointmentDetailFragment.newInstance(appointmentType, appointmentNumber));
    }

    @Override
    public void openAppointmentDetail(int position, ItemAppointmentDao data, ImageView shareView) {
        /* TODO chang mAppointmentData to save data */
        // mAppointmentData = data;
        addFragment(AppointmentDetailFragment.newInstance(position, data.getAppointmentNumber()));
    }


    @Override
    public void openAppointmentHistory(final String appointmentNumber) {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        selectMenu(MENU_APPOINTMENT);
        Handler handle = new Handler();
        handle.postDelayed(() -> addFragment(AppointmentDetailFragment.newInstance(AppointmentUtil.APPOINTMENT_HISTORY, appointmentNumber)), 3000);
    }

    @Override
    public void openAppointmentCancel(ItemAppointmentDao data) {
        onBackPressed();
//        addFragment(AppointmentDetailFragment.newInstance(AppointmentUtil.APPOINTMENT_CANCEL, data.getAppointmentNumber()));
    }

    @Override
    public void openAppointmentCancel(ItemAppointmentDao data, boolean isViewOnly) {
        addFragment(AppointmentCancelFragment.newInstance(data, isViewOnly));
    }

    @Override
    public void openDoctorNoteConfirm(ItemAppointmentDao data, boolean isViewOnly) {
        addFragment(DoctorNoteConfirmFragment.newInstance(data, isViewOnly));
    }

    @Override
    public void openDoctorNoteConfirm(ItemAppointmentDao data, boolean isViewOnly, boolean isShowPayment) {
        addFragment(DoctorNoteConfirmFragment.newInstance(data, isViewOnly, isShowPayment));
    }

    @Override
    public void openShippingStatus(String appointmentNumber, String invoiceNo, String status) {
        addFragment(ShippingStatusFragment.newInstance(appointmentNumber, invoiceNo, status));
    }

    @Override
    public void openRoom(String appointmentNumber, String contactTypeCode) {
        Intent intent = new Intent(this, VideoCallActivity.class);

        if (ContactTypeUtil.isChat(contactTypeCode)) {
            intent = new Intent(this, ChatActivity.class);
        } else if (ContactTypeUtil.isVoice(contactTypeCode)) {
            intent = new Intent(this, VoiceCallActivity.class);
        } else if (ContactTypeUtil.isVideo(contactTypeCode)) {
            intent = new Intent(this, VideoCallActivity.class);
        }

        intent.putExtra(AppConstants.EXTRA_APPOINTMENT_NO, appointmentNumber);
        startActivityForResult(intent, AppConstants.REQUEST_CODE_ROOM);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void openRelationshipMenu(int patientId) {
        addFragment(EhrFragment.newInstance(patientId));
    }

    @Override
    public void openHealthInformationMenu(int patientId, boolean isChild, String titleToolbar) {
        addFragment(HealthInformationMenuFragment.newInstance(patientId, isChild, titleToolbar), HealthInformationMenuFragment.class.toString());
    }

    @Override
    public void openDoctorNote(int patientId, int patientMenuId) {
        addFragment(DoctorNoteFragment.newInstance(patientId, patientMenuId), DoctorNoteFragment.class.toString());
    }

    @Override
    public void openDoctorNoteDetail(DoctorNoteObject data) {
        addFragment(DoctorNoteDetailFragment.newInstance(data), DoctorNoteDetailFragment.class.toString());
    }

    @Override
    public void openPregnantHistory(int patientId, int patientMenuId) {
        addFragment(PregnantHistoryFragment.newInstance(patientId, patientMenuId), PregnantHistoryFragment.class.toString());
    }

    @Override
    public void openNewFormPregnantHistory(PregnantHistoryObject data, boolean isNewFromMenu) {
        addFragment(FormPregnantHistoryFragment.newInstance(data, true, isNewFromMenu), FormPregnantHistoryFragment.class.toString());
    }

    @Override
    public void openUpdateFormPregnantHistory(PregnantHistoryObject data) {
        addFragment(FormPregnantHistoryFragment.newInstance(data, false, false), FormPregnantHistoryFragment.class.toString());
    }

    @Override
    public void openMedicationHistory(int patientId, int patientMenuId) {
        addFragment(MedicationHistoryFragment.newInstance(patientId, patientMenuId), MedicationHistoryFragment.class.toString());
    }

    @Override
    public void openNewFormMedicationHistory(MedicationHistoryObject data, Boolean isNewFromMenu) {
        addFragment(FormMedicationHistoryFragment.newInstance(data, true, isNewFromMenu), FormMedicationHistoryFragment.class.toString());
    }

    @Override
    public void openUpdateFormMedicationHistory(MedicationHistoryObject data) {
        addFragment(FormMedicationHistoryFragment.newInstance(data, false, false), FormMedicationHistoryFragment.class.toString());
    }

    @Override
    public void openFormMedicationHistoryChiiwiiLive(MedicationHistoryObject data) {
        addFragment(FormMedicationHistoryChiiwiiLiveFragment.newInstance(data), FormMedicationHistoryChiiwiiLiveFragment.class.toString());
    }

    @Override
    public void openVaccinationHistory(int patientId, int patientMenuId, String menuCode) {
        addFragment(VaccinationHistoryFragment.newInstance(patientId, patientMenuId, menuCode), VaccinationHistoryFragment.class.toString());
    }

    @Override
    public void openVaccineDetail(RowHeaderVaccineObject data) {
        addFragment(VaccineDetailFragment.newInstance(data), VaccineDetailFragment.class.toString());
    }

    @Override
    public void openLaboratory(int patientId, int patientMenuId, int defaultTap) {
        addFragment(LaboratoryFragment.newInstance(patientId, patientMenuId, defaultTap), LaboratoryFragment.class.toString());
    }

    @Override
    public void openNewFormLaboratoryOther(LaboratoryOtherObject data, boolean isNewFromMenu) {
        addFragment(FormLaboratoryOtherFragment.newInstance(data, true, isNewFromMenu), FormLaboratoryOtherFragment.class.toString());
    }

    @Override
    public void openUpdateFormLaboratoryOther(LaboratoryOtherObject data) {
        addFragment(FormLaboratoryOtherFragment.newInstance(data, false, false), FormLaboratoryOtherFragment.class.toString());
    }

    @Override
    public void showCallingBar() {
        tv_callingbar_detail.setText(AppManager.getDataManager().getNotificationBody());
        callingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCallingBar() {
        callingBar.setVisibility(View.GONE);
        //mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @Override
    public void openVideoCall() {
        //Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        //startActivity(intent);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void setSystemConfigurationApi(SystemConfigurationObject data) {
        configuration = data;
    }

    @Override
    public void onNavigateEHR(boolean hasPIN) {
        Intent intent;
        if (hasPIN) {
            intent = new Intent(this, LoginPINActivity.class);
        } else {
            intent = new Intent(this, CreatePINActivity.class);
        }
        startActivityForResult(intent, AppConstants.REQUEST_CODE_EHR_PIN);
    }

    @Override
    public SystemConfigurationObject getSystemConfiguration() {
        if (configuration == null) {
            configuration = new SystemConfigurationObject();
        }
        return configuration;
    }

    @Override
    public void openHeartBeatRate(int patientId, int patientMenuId, String menuCode) {
        addFragment(HeartBeatRateFragment.newInstance(patientId, patientMenuId, menuCode), HeartBeatRateFragment.class.toString());
    }

    @Override
    public void openViewAllHeartBeatRate(int patientId, int patientMenuId) {
        addFragment(ListDailyHeartBeatRateFragment.newInstance(patientId, patientMenuId), ListDailyHeartBeatRateFragment.class.toString());
    }

    @Override
    public void openTimeHeartBeatRate(DailyHeartBeatRateObject data) {
        addFragment(ListTimeHeartBeatRateFragment.newInstance(data), ListTimeHeartBeatRateFragment.class.toString());
    }

    @Override
    public void openNewFormHeartBeatRate(HeartBeatRateObject data) {
        addFragment(FormHeartBeatRateFragment.newInstance(data, true), FormHeartBeatRateFragment.class.toString());
    }

    @Override
    public void openUpdateFormHeartBeatRate(HeartBeatRateObject data) {
        addFragment(FormHeartBeatRateFragment.newInstance(data, false), FormHeartBeatRateFragment.class.toString());
    }

    @Override
    public void openBloodPressure(int patientId, int patientMenuId, String menuCode) {
        addFragment(BloodPressureFragment.newInstance(patientId, patientMenuId, menuCode), BloodPressureFragment.class.toString());
    }

    @Override
    public void openViewAllBloodPressure(int patientId, int patientMenuId) {
        addFragment(ListDailyBloodPressureFragment.newInstance(patientId, patientMenuId), ListDailyBloodPressureFragment.class.toString());
    }

    @Override
    public void openTimeBloodPressure(DailyBloodPressureObject data) {
        addFragment(ListTimeBloodPressureFragment.newInstance(data), ListTimeBloodPressureFragment.class.toString());
    }

    @Override
    public void openNewFormBloodPressure(BloodPressureObject data) {
        addFragment(FormBloodPressureFragment.newInstance(data, true), FormBloodPressureFragment.class.toString());
    }

    @Override
    public void openUpdateFormBloodPressure(BloodPressureObject data) {
        addFragment(FormBloodPressureFragment.newInstance(data, false), FormBloodPressureFragment.class.toString());
    }

    @Override
    public void openBloodSugar(int patientId, int patientMenuId, String menuCode) {
        addFragment(BloodSugarFragment.newInstance(patientId, patientMenuId, menuCode), BloodSugarFragment.class.toString());
    }

    @Override
    public void openViewAllBloodSugar(int patientId, int patientMenuId) {
        addFragment(ListDailyBloodSugarFragment.newInstance(patientId, patientMenuId), ListDailyBloodSugarFragment.class.toString());
    }

    @Override
    public void openTimeBloodSugar(DailyBloodSugarObject data) {
        addFragment(ListTimeBloodSugarFragment.newInstance(data), ListTimeBloodSugarFragment.class.toString());
    }

    @Override
    public void openNewFormBloodSugar(BloodSugarObject data) {
        addFragment(FormBloodSugarFragment.newInstance(data, true), FormBloodSugarFragment.class.toString());
    }

    @Override
    public void openUpdateFormBloodSugar(BloodSugarObject data) {
        addFragment(FormBloodSugarFragment.newInstance(data, false), FormBloodSugarFragment.class.toString());
    }

    @Override
    public void openMenstrualPeriod(int patientId, int patientMenuId, String menuCode) {
        addFragment(MenstrualPeriodFragment.newInstance(patientId, patientMenuId, menuCode), MenstrualPeriodFragment.class.toString());
    }

    @Override
    public void openChildGrowth(ChildGrowthCriteriaObject criteria) {
        addFragment(ChildGrowthFragment.newInstance(criteria), ChildGrowthFragment.class.toString());
    }

    @Override
    public void openMedicineAllergy(int patientId, int patientMenuId) {
        addFragment(MedicineAllergyFragment.newInstance(patientId, patientMenuId), MedicineAllergyFragment.class.toString());
    }

    @Override
    public void openNewFormMedicineAllergy(MedicineAllergyObject data, Boolean isNewFromMenu) {
        addFragment(FormMedicineAllergyFragment.newInstance(data, true, isNewFromMenu), FormMedicineAllergyFragment.class.toString());
    }

    @Override
    public void openUpdateFormMedicineAllergy(MedicineAllergyObject data) {
        addFragment(FormMedicineAllergyFragment.newInstance(data, false, false), FormMedicineAllergyFragment.class.toString());
    }

    @Override
    public void openFoodAllergy(int patientId, int patientMenuId) {
        addFragment(FoodAllergyFragment.newInstance(patientId, patientMenuId), FoodAllergyFragment.class.toString());
    }

    @Override
    public void openNewFormFoodAllergy(FoodAllergyObject data, Boolean isNewFromMenu) {
        addFragment(FormFoodAllergyFragment.newInstance(data, true, isNewFromMenu), FormFoodAllergyFragment.class.toString());
    }

    @Override
    public void openUpdateFormFoodAllergy(FoodAllergyObject data) {
        addFragment(FormFoodAllergyFragment.newInstance(data, false, false), FormFoodAllergyFragment.class.toString());
    }

    @Override
    public void openCongenitalDisease(int patientId, int patientMenuId) {
        addFragment(CongenitalDiseaseFragment.newInstance(patientId, patientMenuId), CongenitalDiseaseFragment.class.toString());
    }

    @Override
    public void openNewFormCongenitalDisease(CongenitalDiseaseObject data, Boolean isNewFromMenu) {
        addFragment(FormCongenitalDiseaseFragment.newInstance(data, true, isNewFromMenu), FormCongenitalDiseaseFragment.class.toString());
    }

    @Override
    public void openUpdateFormCongenitalDisease(CongenitalDiseaseObject data) {
        addFragment(FormCongenitalDiseaseFragment.newInstance(data, false, false), FormCongenitalDiseaseFragment.class.toString());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        //..update fragment
        if (event.getMessage().equals(MessageEvent.MSG_TIME_TO_CALL)) {
            showCallingBar();
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            //bottomNavigationView.setSelectedItemId(R.id.navigation_time);
        } else if (event.getMessage().equals(MessageEvent.MSG_NOTIFICATION)) {
            //bottomNavigationView.getMenu().getItem(0).setChecked(true);
            //bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public void setMenuHomeSelected() {
        tabPosition = MENU_HOME;
        setMenuSelect(R.id.navigation_home);
        showFabCallCenter();
    }

    @Override
    public void setMenuEhrSelected() {
        tabPosition = MENU_EHR;
        setMenuSelect(R.id.navigation_heart);
        hideFabCallCenter();
    }

    @Override
    public void setMenuAppointmentSelected() {
        tabPosition = MENU_APPOINTMENT;
        setMenuSelect(R.id.navigation_time);
        showFabCallCenter();
    }

    @Override
    public void setMenuSettingSelected() {
        tabPosition = MENU_SETTING;
        setMenuSelect(R.id.navigation_setting);
        showFabCallCenter();
    }

    private void setMenuSelect(@IdRes int id) {
        MenuItem item = bottomNavigationView.getMenu().findItem(id);
        if (item != null) {
            item.setChecked(true);
        }
    }

    private final static int MENU_HOME = 0;
    private final static int MENU_EHR = 1;
    private final static int MENU_APPOINTMENT = 2;
    private final static int MENU_SETTING = 3;
}
