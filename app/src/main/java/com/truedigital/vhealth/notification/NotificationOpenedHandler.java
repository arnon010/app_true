package com.truedigital.vhealth.notification;

import android.content.Context;
import android.content.Intent;

import com.truedigital.vhealth.custom.ActivityRunning;
import com.truedigital.vhealth.database.UserManager;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.ui.password.create.PasswordCreateActivity;
import com.truedigital.vhealth.ui.splashscreen.SplashScreenActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import org.json.JSONObject;


public class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {

    private boolean isNotiCall = false;
    private boolean isInTime = false;
    private int contactTypeId = 0;
    private int userType = 0;
    private int userId;
    private String appointmentNo;
    private String email;
    private Context context;

    private UserManager userManager;
    private int mActivityId;
    private boolean isShowList;
    private String userName;
    private String token;
    private String mCategory;
    private String contactTypeCode;
    private String appointmentNumber;
    private boolean isBookingCanceled;
    private boolean isShortNote;
    private boolean isAlmostCall;
//    private NetworkStatus networkStatus;

    public NotificationOpenedHandler() {
    }

    public NotificationOpenedHandler(Context context) {
        this.context = context;
        userManager = new UserManager();
    }


    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        JSONObject data = result.notification.payload.additionalData;

        if (data != null) {
            //contactTypeId = data.optInt("ContactTypeId", 0);
            //isNotiCall = data.optBoolean("IsTimeToCall", false);
            userType = data.optInt("UserTypeId", 0);
            userId = data.optInt("UserId", 0);
            appointmentNo = data.optString("AppointmentNumber");
            //..flag for news article
            mActivityId = data.optInt("ActivityId", 0);
            //..flag for short note
            isShowList = data.optBoolean("IsShowList", false);

            email = data.optString("Email");
            userName = data.optString("UserName");
            token = data.optString("Token");
            mCategory = data.optString("Category");

            isNotiCall = data.optBoolean("IsCall", false);
            contactTypeCode = data.optString("TypeCode");
            appointmentNumber = data.optString("AppointmentNumber");
            isBookingCanceled = data.optBoolean("IsBookingCanceled",false);
            isShortNote = data.optBoolean("IsShortNote",false);
            isShowList = data.optBoolean("IsShowList",false);
            isAlmostCall = data.optBoolean("IsAlmostCall",false);
            userId = data.optInt("UserId", 0);
            mActivityId = data.optInt("ActivityId", 0);


            //if (mCategory.equals(AppConstants.MSG_CONFIRM_EMAIL)) {
            //    restartApp();
            //}
            if (ActivityRunning.isAppIsInBackground(context)) {
                restartApp();
            }
            else {
                if (!ActivityRunning.isActivityRunning(context, MainActivity.class)) {
                    restartApp();
                }
            }


        }

    }

    private void onSignupConfirmEmail() {
        Intent intent = new Intent(context, PasswordCreateActivity.class);
        intent.putExtra(AppConstants.EXTRA_USERNAME, userName);
        intent.putExtra(AppConstants.EXTRA_EMAIL, email);
        intent.putExtra(AppConstants.EXTRA_TOKEN, token);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void restartApp() {
        Intent intent = new Intent(context, SplashScreenActivity.class);
        intent.putExtra("contactTypeId", contactTypeId);
        intent.putExtra("userType", userType);
        intent.putExtra("userId", userId);
        intent.putExtra("isTimeToCall", isNotiCall);
        intent.putExtra("appointmentNumber", appointmentNo);
        intent.putExtra("isInTime", isInTime);
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