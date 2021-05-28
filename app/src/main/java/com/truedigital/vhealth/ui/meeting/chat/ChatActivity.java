package com.truedigital.vhealth.ui.meeting.chat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.R;
import com.truedigital.vhealth.custom.BubbleLayout;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiChatListObject;
import com.truedigital.vhealth.model.ApiChatObject;
import com.truedigital.vhealth.model.ChatMessageObject;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.adapter.ChatAdapter;
import com.truedigital.vhealth.ui.base.BaseMvpActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.Camera;
import com.truedigital.vhealth.utils.ConvertDate;
import com.truedigital.vhealth.utils.MyDialog;
import com.truedigital.vhealth.utils.PickFile;
import com.truedigital.vhealth.utils.TimeLeftManager;
import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by songkrit on 11/14/2016 AD.
 */

public class ChatActivity extends BaseMvpActivity<ChatActivityInterface.Presenter>
        implements ChatActivityInterface.View,
        EasyPermissions.PermissionCallbacks,
        Publisher.PublisherListener,
        Session.SessionListener,
        Session.ConnectionListener,
        Session.SignalListener {


    private static final String TAG = ChatActivity.class.getSimpleName();
    public static final String SIGNAL_TYPE_CHAT = "chat";

    private static final String LOG_DISCONNECT = "01";
    private static final String LOG_CONNECTION_LOST = "02";
    private static final String DEFAULT_SIGNAL = "startcalltokbox";
    private static final String DISCONNECT_SIGNAL = "tokbox_disconnect";


    private static final int RC_SETTINGS_SCREEN_PERM = 123;

    //private static final int RC_VIDEO_APP_PERM = 124;
    private static final int RC_APP_PERM = 124;
    private boolean isAddViewPublisher = false;

    private Activity activity;

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
    private EditText edMessage;
    private Button btnSend;
    private String imageReciever;
    private String imageSender;
    private RecyclerView rvChat;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private ItemAppointmentDao data;
    private boolean isSendDefaultMessage;
    private ImageView img_gallery;
    private ImageView img_camera;
    private ImageView img_ehr;
    private ImageView img_button_close;

    @Override
    protected ChatActivityInterface.Presenter createPresenter() {
        return ChatActivityPresenter.create();
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void bindView() {
        txtName = findViewById(R.id.tv_name);
        txtTimeLeft = findViewById(R.id.tv_timeleft);
        edMessage = findViewById(R.id.edMessage);

        img_ehr = findViewById(R.id.img_ehr);
        img_gallery = findViewById(R.id.img_gallery);
        img_camera = findViewById(R.id.img_camera);
        btnSend = findViewById(R.id.btnSend);

        rvChat = findViewById(R.id.recycler_view);

        //txtNetworkStatus = (TextView) findViewById(R.id.textviewNetworkStatus);
        //lloNetworkStatus = (LinearLayout) findViewById(R.id.linearlayoutNetworkStatus);

        imgStopCall = findViewById(R.id.imgStopCall);
        //imgMicrophone = (ImageView) findViewById(R.id.imgMicrophone);
        //imgSwapCamera = (ImageView) findViewById(R.id.imgSwapCamera);

        txtTimeLeft.setVisibility(View.INVISIBLE);
        bindViewToolbar();
    }

    @Override
    protected void initInstance() {
        this.activity = this;
    }

    @Override
    protected void setupView() {
        showToolbar(R.string.title_chat_activity,true);

        imgStopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onStopCallClick();
            }
        });

        img_button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onStopCallClick();
            }
        });

        if (AppManager.getDataManager().isDoctor()) {
            img_ehr.setVisibility(View.VISIBLE);
        }
        else {
            img_ehr.setVisibility(View.INVISIBLE);
        }

        img_ehr.setOnClickListener(OnImageEhrListener());
        img_gallery.setOnClickListener(OnImageGalleryListener());
        img_camera.setOnClickListener(OnImageCameraListener());
        btnSend.setOnClickListener(OnSendMessageListener());

        //imageSender = AppManager.getDataManager().getProfileImage();
        timeLeftManager = TimeLeftManager.getInstance(this);
        //
        getPresenter().apiGetAppointment();
        //..
        setupChat();
        onWaiting();
    }


    @Override
    protected void initialize() {

    }

    private void bindViewToolbar() {
        toolbarMain = findViewById(R.id.toolbarMain);
        ic_button_back = toolbarMain.findViewById(R.id.ic_button_back);
        img_button_close = toolbarMain.findViewById(R.id.ic_button_close);
        tv_toolbar_name = toolbarMain.findViewById(R.id.tvToolbarName);
    }

    public void showToolbar(int resId, boolean showHomeAsUp) {
        tv_toolbar_name.setText(resId);
        ic_button_back.setVisibility(showHomeAsUp ? View.VISIBLE : View.GONE);
        img_button_close.setVisibility(View.VISIBLE);
        toolbarMain.setVisibility(View.VISIBLE);

        ic_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupChat() {
        ArrayList<ChatMessageObject> chatMessageArrayList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(layoutManager);

        chatAdapter = new ChatAdapter(this, chatMessageArrayList);
        rvChat.setAdapter(chatAdapter);

        /*
        chatAdapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                reSendUpload(position);
            }
        });
        */

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
                onShowSuccess();
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
        intent.putExtra(AppConstants.EXTRA_CONTACT_TYPE, AppConstants.CONTACT_CHAT);
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
    public void openConnect(String tokbox_session, String tokbox_token) {
        TOKBOX_SESSION_ID = tokbox_session;
        TOKBOX_TOKEN = tokbox_token;
        connectCall();
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
                    .setRequestCode(RC_APP_PERM)
                    .build()
                    .show();
        }
    }

    @AfterPermissionGranted(RC_APP_PERM)
    private void connectCall() {
        initializeSession(BuildConfig.API_KEY_TOKBOX, TOKBOX_SESSION_ID,TOKBOX_TOKEN);
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_video_app),
                    RC_APP_PERM, perms);
        }
    }

    private void initializeSession(String apiKey, String sessionId, String token) {
        Log.e(TAG, "Initializing Session " + sessionId);
        Log.e(TAG, "Initializing Token " + token);
        if (mSession == null) {
            mSession = new Session.Builder(this, apiKey, sessionId).build();
            mSession.setSessionListener(this);
            mSession.setSignalListener(this);
            mSession.setConnectionListener(this);
            mSession.connect(token);
        }
    }

    private void initPublisher() {
        /*
        if (mPublisher == null) {
            mPublisher = new Publisher.Builder(this).build();
            mPublisher.setPublisherListener(this);
            mPublisher.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            mSession.publish(mPublisher);
            //..
            rloPublisherViewContainer.addView(mPublisher.getView());
            if (mPublisher.getView() instanceof GLSurfaceView) {
                ((GLSurfaceView) mPublisher.getView()).setZOrderOnTop(true);
            }
        }
        */
    }


    private void initSubscriber(Stream stream) {
        /*
        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            //mSubscriber.setSubscriberListener(this);
            mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            mSession.subscribe(mSubscriber);
            //..
            rloSubscriberViewContainer.addView(mSubscriber.getView());

            mSubscriber.setVideoListener(new SubscriberKit.VideoListener() {
                @Override
                public void onVideoDataReceived(SubscriberKit subscriberKit) {

                }

                @Override
                public void onVideoDisabled(SubscriberKit subscriberKit, String reason) {

                }

                @Override
                public void onVideoEnabled(SubscriberKit subscriberKit, String s) {

                }

                @Override
                public void onVideoDisableWarning(SubscriberKit subscriberKit) {

                }

                @Override
                public void onVideoDisableWarningLifted(SubscriberKit subscriberKit) {

                }
            });
        }
        */
    }

    @Override
    public void onConnected(Session session) {
        Log.e(TAG, "onConnected: Connected to session " + session.getSessionId());
        initPublisher();

        //..
        getPresenter().getChatMessageList(getAppointmentNumber());

        //..Log start
        getPresenter().callLogStart(getAppointmentNumber());
    }

    @Override
    public void onDisconnected(Session session) {
        Log.e(TAG, "onDisconnected: disconnected from session " + session.getSessionId());
        mSession = null;
        onWaiting();

        //..Log stop
        getPresenter().callLogEnd(getAppointmentNumber());
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
    }


    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.e(TAG, "onStreamDropped: Stream " + stream.getStreamId() + " dropped from session " +
                session.getSessionId());

    }

    private void onWaiting() {
        txtName.setText(getResources().getString(R.string.wait_for_connector));
        txtTimeLeft.setVisibility(View.INVISIBLE);

        //sendLog(LOG_CONNECTION_LOST);
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
        //..Show User info
        //..can send message
        showConnecting();
        //..Log Concurrent
        getPresenter().apiPostLogConcurrent();

        /*
        getPresenter().callLogConcurrent(getAppointmentNumber());

        delay(new Runnable() {
            @Override
            public void run() {
                getPresenter().apiGetAppointmentTimeLeft();
            }
        });
        */

    }

    private void delay(Runnable runnable) {
        android.os.Handler handle = new android.os.Handler();
        handle.postDelayed(runnable,2000);
    }

    @Override
    public void onConnectionDestroyed(Session session, Connection connection) {
        //.. Waiting
        onWaiting();
    }

    @Override
    public void disconnectSession() {
        if (mSession != null) {

            if (mSubscriber != null) {
                mSession.unsubscribe(mSubscriber);
                //rloSubscriberViewContainer.removeView(mSubscriber.getView());
                mSubscriber.destroy();
                mSubscriber = null;
            }
            if (mPublisher != null) {
                mSession.unpublish(mPublisher);
                //rloPublisherViewContainer.removeView(mPublisher.getView());
                //bubbleLayout.removeView(mPublisher.getView());
                mPublisher.destroy();
                mPublisher = null;
            }

            mSession.disconnect();
        }

        if (timeLeftManager != null) {
            timeLeftManager.cancel();
        }

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
        //EventBus.getDefault().register(this);
    }


    @Override
    protected void onStop() {
        super.onStop();

        //PlaySound.getInstance().setIgnorePlay(false);
        //disconnectSession();
        //EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        //disconnectSession();
        //finish();
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
                    txtTimeLeft.setBackgroundColor(ContextCompat.getColor(ChatActivity.this,R.color.color_red));
                }
            }
        });

    }

    @Override
    public void setData(ItemAppointmentDao data) {
        this.data = data;
        setUserInfo();
    }

    @Override
    public void setInfo(ItemAppointmentDao data) {

        this.data = data;
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

    void setUserInfo() {
        setDoctorName(data.getDoctorName());
        setDoctorId(data.getDoctorId());
        if (AppManager.getDataManager().isDoctor()) {
            //..get info patient
            mSubscriber_Name = data.getPatientUsername();
            imageSender = data.getDoctorProfileImage();
            imageReciever = data.getPatientProfileImage();
        }
        else {
            //..get doctor info
            mSubscriber_Name = data.getDoctorName();
            imageSender = data.getPatientProfileImage();
            imageReciever = data.getDoctorProfileImage();
        }
        Log.e(TAG,"Set Info..." + mSubscriber_Name);
    }

    private void showConnecting() {
        txtName.setText(mSubscriber_Name);
        txtTimeLeft.setVisibility(View.VISIBLE);
        btnSend.setEnabled(true);
        Log.e(TAG,"Info Showing..." + mSubscriber_Name);
    }

    private void setupTracking(int i) {
    }

    private void sendLog(String logConnectionLost) {
    }

    @Override
    public String getAppointmentNumber() {
        return getIntent().getStringExtra(AppConstants.EXTRA_APPOINTMENT_NO);
    }

    private View.OnClickListener OnImageEhrListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private View.OnClickListener OnImageCameraListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera.takePhotosIntentWithPermission(activity,  AppConstants.REQUEST_CAMERA);
            }
        };
    }

    private View.OnClickListener OnImageGalleryListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickFile.pickImageIntentWithPermission(activity, AppConstants.REQUEST_PICK_IMAGE);
            }
        };
    }

    private View.OnClickListener OnSendMessageListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageButton();
            }
        };
    }

    public void sendMessageButton() {
        String messageStr = edMessage.getText().toString().trim();
        if (!isTimeOut) {
            if (!messageStr.isEmpty()) {
                sendMessage(messageStr);
            }
        }
    }

    private void sendMessage(String message) {
        if (mSession != null) {
            mSession.sendSignal(SIGNAL_TYPE_CHAT, message);
            edMessage.setText("");
            getPresenter().callSendMessage(AppManager.getDataManager().getUserId(), getAppointmentNumber(), message);
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void sendAttachFile(Uri uriImage) {

        File finalFile = new File(getRealPathFromURI(uriImage));
        String imgFilename = finalFile.getAbsolutePath();

        String message = "image " + imgFilename + " " + imgFilename;

        showMessage(message, false);

        //mSession.sendSignal(SIGNAL_TYPE_CHAT, message);
        //edMessage.setText("");
        getPresenter().callSendImage(this.activity, getAppointmentNumber(), uriImage);
    }

    @Override
    public void onSendMessageSuccess(ApiChatObject data) {

    }

    @Override
    public void onSendImageSuccess(ApiChatObject data) {
        String message = "image " + data.getAttachmentThumbnailUrl() + " " + data.getAttachmentUrl();
        if (mSession != null) {
            mSession.sendSignal(SIGNAL_TYPE_CHAT, message);
        }
    }

    @Override
    public void onGetMessageListSuccess(ApiChatListObject listData) {
        if (listData.getListData().size() > 0) {
            ArrayList<ChatMessageObject> chatMessageArrayList = new ArrayList<>();
            ChatMessageObject chatObject;
            for (ApiChatObject chatList : listData.getListData()) {
                chatObject = new ChatMessageObject();

                chatObject.setMessageText(chatList.getMessage());
                chatObject.setTimeMessage(chatList.getMessageTime());
                chatObject.setAttachmentUrl(chatList.getAttachmentUrl());
                chatObject.setAttachmentThumbnailUrl(chatList.getAttachmentThumbnailUrl());
                if (AppManager.getDataManager().getUserId() == chatList.getUserId()) {
                    chatObject.setImageProfile(imageSender);
                    chatObject.setType(ChatAdapter.TYPE_SENDER);
                }
                else {
                    chatObject.setImageProfile(imageReciever);
                    chatObject.setType(ChatAdapter.TYPE_RECEIVED);
                }

                chatMessageArrayList.add(chatObject);
            }

            chatAdapter.setChatMessageArrayList(chatMessageArrayList);
            layoutManager.smoothScrollToPosition(rvChat, null, chatAdapter.getItemCount());
        }
        else {
            sendGreeting();
        }
    }

    private void sendGreeting() {
        // send default message
        if (isSendDefaultMessage) return ;
        if (AppManager.getDataManager().isDoctor()) {
            String defaultMessage = "สวัสดีค่ะ มีอะไรให้หมอช่วยค่ะ";//getString(R.string.call_default_message, nameUser, nameDoctor);
            sendMessage(defaultMessage);
            isSendDefaultMessage = true;
        }
    }

    @Override
    public void onSignalReceived(Session session, String type, String data, Connection connection) {
        boolean remote = !connection.equals(mSession.getConnection());


        if (!data.equals(DEFAULT_SIGNAL)) {
            //..if image success
            //..show image on subcriber remote only
            String strUrlImage = getAttachmentUrl(data,false);
            if (strUrlImage.isEmpty()) {
                showMessage(data, remote);
                Log.e(TAG, "Signal Received msg " + remote + " " + data);
            }
            else {
                if (remote) {
                    showMessage(data, remote);
                    Log.e(TAG, "Signal Received image " + remote + " " + data);
                }
            }
        }

    }

    private String getAttachmentUrl(String messageData,boolean isThumbnail) {
        if (messageData.length() > 10) {
            if (messageData.substring(0,6).equals("image ")) {
                messageData = messageData.substring(6);
                String[] strUrl = messageData.split(" ");
                return isThumbnail ? strUrl[0] : strUrl[1];
            }
        }
        return "";
    }

    private void showMessage(String messageData, boolean remote) {
        final ChatMessageObject chatMessageObject = new ChatMessageObject();

        String strUrlImageThumbnail = getAttachmentUrl(messageData,true);
        String strUrlImage = getAttachmentUrl(messageData,false);
        if (!strUrlImage.isEmpty()) {
            chatMessageObject.setAttachFile(true);
            chatMessageObject.setAttachmentThumbnailUrl(strUrlImageThumbnail);
            chatMessageObject.setAttachmentUrl(strUrlImage);
            chatMessageObject.setMessageText("");
            chatMessageObject.setSending(true);
            //chatMessageObject.setImageUri(mUriPhoto);
            Log.e(TAG,strUrlImage);
        }
        else {
            chatMessageObject.setAttachFile(false);
            chatMessageObject.setAttachmentThumbnailUrl("");
            chatMessageObject.setAttachmentUrl("");
            chatMessageObject.setMessageText(messageData);
            Log.e(TAG,messageData);
        }
        chatMessageObject.setTimeMessage(ConvertDate.convertDateToServiceFormat(new Date()));
        chatMessageObject.setType((remote ? ChatAdapter.TYPE_RECEIVED : ChatAdapter.TYPE_SENDER));
        chatMessageObject.setImageProfile((remote ? imageReciever : imageSender));
        chatAdapter.addMessage(chatMessageObject);
        layoutManager.smoothScrollToPosition(rvChat, null, chatAdapter.getItemCount());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uriImage = null;
        if (requestCode == AppConstants.REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                uriImage = data.getData();
                //showImage(uriImage.toString());

                sendAttachFile(uriImage);
            }
        }
        if (requestCode == AppConstants.REQUEST_CAMERA && resultCode == RESULT_OK) {
            uriImage = Camera.moveFile(this);
            //showImage(uriImage.toString());
            sendAttachFile(uriImage);
        }
    }

}

