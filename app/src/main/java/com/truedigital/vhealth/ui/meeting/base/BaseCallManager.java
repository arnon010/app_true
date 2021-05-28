package com.truedigital.vhealth.ui.meeting.base;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.R;
import com.opentok.android.Connection;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by songkrit on 11/13/2018 AD.
 */

public class BaseCallManager implements
        Session.ConnectionListener,
        Session.SessionListener {

    private static final int RC_VIDEO_APP_PERM = 124;

    private Activity activity;

    //..tokbox
    private String TOKBOX_SESSION_ID = "";
    private String TOKBOX_TOKEN = "";
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;

    public BaseCallManager(Activity activity) {
        this.activity = activity;
    }

    private void openConnect(String tokbox_session, String tokbox_token) {
        TOKBOX_SESSION_ID = tokbox_session;
        TOKBOX_TOKEN = tokbox_token;
        openConnectCall();

    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void openConnectCall() {
        String[] perms = {
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };
        if (EasyPermissions.hasPermissions(activity, perms)) {
            initializeSession(BuildConfig.API_KEY_TOKBOX, TOKBOX_SESSION_ID,TOKBOX_TOKEN);
        } else {
            EasyPermissions.requestPermissions(activity, activity.getString(R.string.rationale_video_app),
                    RC_VIDEO_APP_PERM, perms);
        }
    }

    private void initializeSession(String apiKey, String sessionId, String token) {
        Log.e(TAG, "Initializing Session " + sessionId);
        Log.e(TAG, "Initializing Token " + token);
        if (mSession == null) {
            mSession = new Session.Builder(activity, apiKey, sessionId).build();
            mSession.setSessionListener(this);
            mSession.connect(token);
        }
    }

    @Override
    public void onConnectionCreated(Session session, Connection connection) {

    }

    @Override
    public void onConnectionDestroyed(Session session, Connection connection) {

    }

    @Override
    public void onConnected(Session session) {

    }

    @Override
    public void onDisconnected(Session session) {

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {

    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

}