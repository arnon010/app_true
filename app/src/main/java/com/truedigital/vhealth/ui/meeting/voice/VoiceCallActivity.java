package com.truedigital.vhealth.ui.meeting.voice;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.custom.BubbleLayout;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.TimeLeftManager;
import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by songkrit on 11/14/2016 AD.
 */

public class VoiceCallActivity extends BaseMvpActivity<VoiceCallActivityInterface.Presenter>
        implements VoiceCallActivityInterface.View,
        EasyPermissions.PermissionCallbacks,
        Publisher.PublisherListener,
        Session.SessionListener,
        Session.ConnectionListener {


    private static final String TAG = "voice call " + VoiceCallActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VOICE_APP_PERM = 124;

    private static final String LOG_DISCONNECT = "01";
    private static final String LOG_CONNECTION_LOST = "02";
    private boolean isAddViewPublisher = false;

    private EditText ed_password;
    private ImageView btn_next;
    private TextView tv_title;
    private TextView tv_forgot_password;
    private Button btn_forgot_password;

    //..tokbox
    private String TOKBOX_SESSION_ID = "";
    private String TOKBOX_TOKEN = "";
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;

    private String mSubscriber_Name;
    private String mSubscriber_ImageUrl;

    private TextView txtName;
    private TextView txtTimeLeft;
    private ImageView imgStopCall;
    private ImageView imgMicrophone;
    private ImageView imgSwapCamera;
    private ImageView imgSubscriber;
    private RelativeLayout rloPublisherViewContainer;
    private RelativeLayout rloSubscriberViewContainer;
    private TextView txtNetworkStatus;
    private LinearLayout lloNetworkStatus;
    private BubbleLayout bubbleLayout;
    private ImageView imgPublisherProfile;
    private TimeLeftManager timeLeftManager;

    private LinearLayout toolbarMain;
    private ImageButton ic_button_back;
    private TextView tv_toolbar_name;
    private boolean isTimeOut;
    private int doctorId;
    private String doctorName;
    private boolean isStopCall;

    @Override
    protected VoiceCallActivityInterface.Presenter createPresenter() {
        return VoiceCallActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_voice;
    }

    @Override
    protected void bindView() {
        txtName = (TextView) findViewById(R.id.tv_name);
        txtTimeLeft = (TextView) findViewById(R.id.tv_timeleft);
        imgSubscriber = (ImageView) findViewById(R.id.imageviewSubscriber);
        rloSubscriberViewContainer = (RelativeLayout) findViewById(R.id.relativelayoutSubscriber);
        txtNetworkStatus = (TextView) findViewById(R.id.textviewNetworkStatus);
        lloNetworkStatus = (LinearLayout) findViewById(R.id.linearlayoutNetworkStatus);

        imgStopCall = (ImageView) findViewById(R.id.imgStopCall);
        imgMicrophone = (ImageView) findViewById(R.id.imgMicrophone);
        imgSwapCamera = (ImageView) findViewById(R.id.imgSwapCamera);

        bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.layout_publisher, null);
        rloPublisherViewContainer = (RelativeLayout) bubbleLayout.findViewById(R.id.publisherview);
        imgPublisherProfile = (ImageView) bubbleLayout.findViewById(R.id.imageviewPublisherProfile);
        //bubbleLayout.setRootView(rloSubscriberViewContainer);


        txtTimeLeft.setVisibility(View.INVISIBLE);
        //addViewPublisher(bubbleLayout);

        bindViewToolbar();
    }

    @Override
    protected void initInstance() {

    }

    @Override
    protected void setupView() {
        showToolbar(R.string.title_voice_activity,true);

        imgStopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onStopCallClick();
                //onBackPressed();
            }
        });

        imgSwapCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onCameraSwap();
                getPresenter().onCameraSwapClick();
            }
        });

        imgMicrophone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onMicroPhoneClick();
                getPresenter().onMicroPhoneClick();
            }
        });

        timeLeftManager = TimeLeftManager.getInstance(this);
        //
        getPresenter().apiGetAppointment();
        onWaiting();
    }

    @Override
    protected void initialize() {

    }

    private void bindViewToolbar() {
        toolbarMain = (LinearLayout) findViewById(R.id.toolbarMain);
        ic_button_back = (ImageButton) toolbarMain.findViewById(R.id.ic_button_back);
        tv_toolbar_name = (TextView) toolbarMain.findViewById(R.id.tvToolbarName);
    }

    public void showToolbar(int resId, boolean showHomeAsUp) {
        tv_toolbar_name.setText(resId);
        ic_button_back.setVisibility(showHomeAsUp ? View.VISIBLE : View.GONE);
        toolbarMain.setVisibility(View.VISIBLE);

        ic_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onTimeOut() {
        isTimeOut = true;
        //..fix bug
        if (isFinishing()) return;

        disconnectSession();


        getPresenter().callEndCallAppointment(getAppointmentNumber());
        new MyDialog(this).showEndCall(R.string.call_time_out,new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                onAfterEndCall();
            }

            @Override
            public void onClickCancel() {

            }
        });
    }

    @Override
    public void openStopConfirm() {
        MyDialog myDialog = new MyDialog(this);
        myDialog.showConfirm(R.string.room_exit_title, R.string.room_exit_no, R.string.room_exit_yes, new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                if (timeLeftManager != null) {
                    timeLeftManager.cancel();
                }

                isStopCall = true;
                disconnectSession();

                onUserEndCall();
            }

            @Override
            public void onClickCancel() {

            }
        });
    }

    private void onUserEndCall() {
        getPresenter().callEndCallAppointment(getAppointmentNumber());
        onAfterEndCall();
    }

    @Override
    public void onAfterEndCall() {
        AppManager.getDataManager().setTimeToCall(false);
        if (AppManager.getDataManager().isDoctor()) {
            openDoctorNote();
        }
        else {
            openRating();
        }
    }

    @Override
    public void openDoctorNote() {
        //((MainActivity) this).openAppointmentCancel();
        onSuccess();
    }

    @Override
    public void openRating() {
        MyDialog myDialog = new MyDialog(this);
        myDialog.showRating(5, getDoctorName(),"", new MyDialog.OnRatingListener() {
            @Override
            public void onRating(float rate, String suggesstion) {
                getPresenter().callRating(getDoctorId(),getAppointmentNumber(),rate,suggesstion);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void onShowSuccess() {
        MyDialog myDialog = new MyDialog(this);
        myDialog.showSuccess(getString(R.string.room_show_success), new MyDialog.OnSelectListener() {
            @Override
            public void onClickOK() {
                onSuccess();
            }

            @Override
            public void onClickCancel() {

            }
        });

    }

    private void onSuccess() {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.EXTRA_CONTACT_TYPE, AppConstants.CONTACT_VIDEO);
        intent.putExtra(AppConstants.EXTRA_APPOINTMENT_NUMBER, getAppointmentNumber());
        intent.putExtra(AppConstants.EXTRA_APPOINTMENT_STATUS, AppConstants.APPOINTMENT_STATUS_STOPCALL);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onStopCall() {
        openStopConfirm();
    }

    @Override
    public void onMicroPhoneSelect() {
        if (mPublisher == null) {
            return;
        }
        if (imgMicrophone.isSelected()) {
            imgMicrophone.setSelected(false);
            mPublisher.setPublishAudio(true);
        } else {
            imgMicrophone.setSelected(true);
            mPublisher.setPublishAudio(false);
        }
    }

    @Override
    public void onCameraSwap() {
        if (mPublisher == null) {
            return;
        }
        if (!imgSwapCamera.isSelected()) {
            imgSwapCamera.setSelected(true);
        } else {
            imgSwapCamera.setSelected(false);
        }
        //mPublisher.swapCamera();
        mPublisher.cycleCamera();
    }

    @Override
    public void openConnectVoiceCall(String tokbox_session, String tokbox_token) {
        TOKBOX_SESSION_ID = tokbox_session;
        TOKBOX_TOKEN = tokbox_token;
        connectVoice();
    }

    @Override
    public void setTokbox_Session(String tokbox_session) {
        TOKBOX_SESSION_ID = tokbox_session;
    }

    @Override
    public void setTokbox_Token(String tokbox_token) {
        TOKBOX_TOKEN = tokbox_token;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.e(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.e(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setRationale(getString(R.string.rationale_ask_again))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel))
                    .setRequestCode(RC_SETTINGS_SCREEN_PERM)
                    .build()
                    .show();
        }
    }

    @AfterPermissionGranted(RC_VOICE_APP_PERM)
    private void connectVoice() {
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            initializeSession(BuildConfig.API_KEY_TOKBOX, TOKBOX_SESSION_ID,TOKBOX_TOKEN);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_video_app),
                    RC_VOICE_APP_PERM, perms);
        }
    }

    private void initializeSession(String apiKey, String sessionId, String token) {
        Log.e(TAG, "Initializing Session " + sessionId);
        Log.e(TAG, "Initializing Token " + token);
        if (mSession == null) {
            mSession = new Session.Builder(this, apiKey, sessionId).build();
            mSession.setSessionListener(this);
            mSession.setConnectionListener(this);
            mSession.connect(token);
        }
    }

    private void initPublisher() {
        if (mPublisher == null) {
            mPublisher = new Publisher.Builder(this)
                    .videoTrack(false)
                    .build();
            mPublisher.setPublisherListener(this);
            mPublisher.setPublishVideo(false);
            mPublisher.setPublishAudio(true);
            mSession.publish(mPublisher);

        }
    }

    private void addViewPublisher(View view) {

        if (!isAddViewPublisher) {
            rloSubscriberViewContainer.addView(bubbleLayout);
            isAddViewPublisher = true;
        }

    }

    private void initSubscriber(Stream stream) {
        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();

            mSubscriber.setSubscribeToAudio(true);
            mSubscriber.setSubscribeToVideo(false); // video off
            mSession.subscribe(mSubscriber);
        }
    }

    @Override
    public void onConnected(Session session) {
        Log.e(TAG, "onConnected: Connected to session " + session.getSessionId());
        initPublisher();

        //..
        //
        //setInfo();
    }

    @Override
    public void onDisconnected(Session session) {
        Log.e(TAG, "onDisconnected: disconnected from session " + session.getSessionId());
        mSession = null;
        onWaiting();
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(TAG, "onError: Error (" + opentokError.getMessage() + ") in session " + session.getSessionId());
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(TAG, "onError: Error (" + opentokError.getMessage() + ") in publisher");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.e(TAG, "onStreamReceived: New stream " + stream.getStreamId() + " in session " + session.getSessionId());
        initSubscriber(stream);

        showConnecting();
        //getPresenter().apiPostLogConcurrent();
        getPresenter().apiGetAppointmentTimeLeft();
    }


    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.e(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " +
                session.getSessionId());

        if (mSubscriber != null) {
            rloSubscriberViewContainer.removeView(mSubscriber.getView());
            mSubscriber.destroy();
            mSubscriber = null;
        }

        onWaiting();
    }

    private void onWaiting() {
        txtName.setText(getResources().getString(R.string.wait_for_connector));
        txtTimeLeft.setVisibility(View.INVISIBLE);

        sendLog(LOG_CONNECTION_LOST);
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.e(TAG, "onStreamCreated: Own stream " + stream.getStreamId() + " created");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.e(TAG, "onStreamDestroyed: Own stream " + stream.getStreamId() + " destroyed");
    }

    @Override
    public void onConnectionCreated(Session session, Connection connection) {
        Log.e(TAG, "onConnectionCreated " + connection.getConnectionId());
        getPresenter().apiPostLogConcurrent();
        setupTracking(0);
    }

    @Override
    public void onConnectionDestroyed(Session session, Connection connection) {

    }

    @Override
    public void disconnectSession() {
        if (mSession != null) {

            if (mSubscriber != null) {
                mSession.unsubscribe(mSubscriber);
                rloSubscriberViewContainer.removeView(mSubscriber.getView());
                mSubscriber.destroy();
                mSubscriber = null;
            }
            if (mPublisher != null) {
                mSession.unpublish(mPublisher);
                rloPublisherViewContainer.removeView(mPublisher.getView());
                bubbleLayout.removeView(mPublisher.getView());
                mPublisher.destroy();
                mPublisher = null;
            }

            mSession.disconnect();
        }

        if (timeLeftManager != null) {
            timeLeftManager.cancel();
        }

        //..
        //sendLog(LOG_DISCONNECT);

        /*
        if (isTimeOut || isStopCall) {
            getPresenter().callEndCallAppointment(getAppointmentNumber());

            int resId = (isStopCall) ? R.string.room_show_success : R.string.call_time_out;
            new MyDialog(this).showEndCall(resId,new MyDialog.OnSelectListener() {
                @Override
                public void onClickOK() {
                    //getPresenter().callEndCallAppointment(getAppointmentNumber());
                    onAfterEndCall();
                }

                @Override
                public void onClickCancel() {

                }
            });
        }
        */
    }

    @Override
    protected void onStart() {
        super.onStart();

        //PlaySound.getInstance().setIgnorePlay(true);
        getPresenter().apiGetTokenTokbox();

        //..Log start
        getPresenter().callLogStart(getAppointmentNumber());
    }


    @Override
    protected void onStop() {
        super.onStop();

        //PlaySound.getInstance().setIgnorePlay(false);
        disconnectSession();

        //..Log stop
        getPresenter().callLogEnd(getAppointmentNumber());
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        disconnectSession();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSession != null) {
            mSession.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSession != null) {
            mSession.onPause();
        }
        if (isFinishing()) {
            disconnectSession();
        }
    }

    @Override
    protected void onDestroy() {
        disconnectSession();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        disconnectSession();
        super.onBackPressed();
    }

    @Override
    public void onStopCallApi() {
        onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        */
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void openTimeLeftStart(String strTime) {
        if (timeLeftManager != null) {
            timeLeftManager.cancel();
        }


        timeLeftManager.setTime(strTime,0);
        timeLeftManager.start();
        timeLeftManager.setOnTimerListener(new TimeLeftManager.OnTimerListener() {
            @Override
            public void onTimerFinish() {
                onTimeOut();
            }

            @Override
            public void onTimerTick(int minutes, int seconds, String time) {
                txtTimeLeft.setText(getString(R.string.timeleft,time));
                if (minutes <= 4) {
                    txtTimeLeft.setBackgroundColor(ContextCompat.getColor(VoiceCallActivity.this,R.color.color_red));
                }
            }
        });

    }

    @Override
    public void setInfo(ItemAppointmentDao data) {
        setDoctorName(data.getDoctorName());
        setDoctorId(data.getDoctorId());
        if (AppManager.getDataManager().isDoctor()) {
            //..get info patient
            mSubscriber_Name = data.getPatientUsername();
            Glide.with(this)
                    .load(data.getPatientProfileImage()).asBitmap()
                    .placeholder(R.drawable.img_iph_defaultimg_white_big2x)
                    .centerCrop()
                    .into(imgSubscriber);
        }
        else {
            //..get doctor info
            mSubscriber_Name = data.getDoctorName();
            Glide.with(this)
                    .load(data.getDoctorProfileImage()).asBitmap()
                    .placeholder(R.drawable.img_iph_defaultimg_white_big2x)
                    .centerCrop()
                    .into(imgSubscriber);
        }
    }

    @Override
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Override
    public int getDoctorId() {
        return doctorId;
    }

    @Override
    public String getDoctorName() {
        return doctorName;
    }

    private void showConnecting() {
        txtName.setText(mSubscriber_Name);
        txtTimeLeft.setVisibility(View.VISIBLE);
        Log.e(TAG,"Info Showing...");
    }


    private void setupTracking(int i) {
    }

    private void sendLog(String logConnectionLost) {
    }

    @Override
    public String getAppointmentNumber() {
        return getIntent().getStringExtra(AppConstants.EXTRA_APPOINTMENT_NO);
    }
}

