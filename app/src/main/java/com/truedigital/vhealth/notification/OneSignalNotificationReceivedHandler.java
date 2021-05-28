package com.truedigital.vhealth.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.truedigital.vhealth.custom.ActivityRunning;
import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.manager.bus.MessageEvent;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.splashscreen.SplashScreenActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.PlaySound;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

public class OneSignalNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler,OneSignal.PostNotificationResponseHandler {


    private boolean isNotiCall = false;

    private int userType = 0;
    private int userId;
    private String appointmentNumber;
    private String email;
    private Context context;

    //private UserManager userManager;
    //private NotificationCountManager notificationCountManager;
    private String sound;
    //private CallingManager callingManager;
    //private NewsCountManager newsCountManager;
    private int mActivityId;
    private String mCategory;
    private String userName;
    private String token;
    private String contactTypeCode;
    private boolean isBookingCanceled;
    private boolean isShortNote;
    private boolean isShowList;
    private boolean isAlmostCall;

    public OneSignalNotificationReceivedHandler() {
    }

    public OneSignalNotificationReceivedHandler(Context context) {
        this.context = context;
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
        String customKey;
        Intent intent = null;

        sound = notification.payload.sound;
        if (data != null) {
            Log.e("Noti receive ",notification.payload.title);
            Log.e("Noti receive ",data.toString());
            isNotiCall = data.optBoolean("IsCall", false);
            contactTypeCode = data.optString("TypeCode");
            appointmentNumber = data.optString("AppointmentNumber");
            isBookingCanceled = data.optBoolean("IsBookingCanceled",false);
            isShortNote = data.optBoolean("IsShortNote",false);
            isShowList = data.optBoolean("IsShowList",false);
            isAlmostCall = data.optBoolean("IsAlmostCall",false);
            userId = data.optInt("UserId", 0);
            mActivityId = data.optInt("ActivityId", 0);

            /*
            switch (userType) {
                case 1:
                    userType = SelectUserActivity.TYPE_PATIENT;
                    break;
                case 2:
                    userType = SelectUserActivity.TYPE_DOCTOR;
                    break;
                default:
                    break;
            }
            */

            //userManager = new UserManager();
            //notificationCountManager = new NotificationCountManager();
            //newsCountManager = new NewsCountManager();
        }


        //if (userManager.isLogin()) {

            //notificationCountManager.addBadge(userManager.getCurrentUserId());
            //MyBroadcastReceiver.sendMessage(context, BroadcastMessage.MSG_UPDATE_BADGE);
        //}

        if (isNotiCall) {
            AppManager.getDataManager().setAppointmentNumber(appointmentNumber);
            AppManager.getDataManager().setContactTypeCode(contactTypeCode);
            Log.e("Noti ","contact " + contactTypeCode);
            AppManager.getDataManager().setTimeToCall(true);
            AppManager.getDataManager().setNotificationBody(notification.payload.body);

            EventBus.getDefault().post(new MessageEvent(MessageEvent.MSG_TIME_TO_CALL));

            PlaySound.getInstance().start();
            if (!ActivityRunning.isActivityRunning(context, MainActivity.class)) {
                restartApp();
            }
        }
    }

    @Override
    public void onSuccess(JSONObject response) {
        Log.e("Success", "" + response.toString());
    }

    @Override
    public void onFailure(JSONObject response) {

    }

    private void restartApp() {
        Intent intent = new Intent(context, SplashScreenActivity.class);
        intent.putExtra("contactTypeCode", contactTypeCode);
        intent.putExtra("userType", userType);
        intent.putExtra("userId", userId);
        intent.putExtra("isTimeToCall", isNotiCall);
        intent.putExtra("appointmentNumber", appointmentNumber);
        intent.putExtra("isInTime", isNotiCall);
        intent.putExtra("ActivityId", mActivityId);
        intent.putExtra("IsShowList", isShowList);

        intent.putExtra(AppConstants.EXTRA_USERNAME, userName);
        intent.putExtra(AppConstants.EXTRA_EMAIL, email);
        intent.putExtra(AppConstants.EXTRA_TOKEN, token);
        intent.putExtra(AppConstants.EXTRA_CATEGORY, mCategory);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}