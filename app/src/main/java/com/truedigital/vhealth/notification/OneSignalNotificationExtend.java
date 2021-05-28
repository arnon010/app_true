package com.truedigital.vhealth.notification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.truedigital.vhealth.R;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by songkrit on 10/2/2017 AD.
 */

public class OneSignalNotificationExtend extends NotificationExtenderService {

    private int mActivityId;
    private boolean isNotiCall;

    @Override
    protected boolean onNotificationProcessing(final OSNotificationReceivedResult notification) {
        //final Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.notification_sound);
        final Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.ic_app_logo);

        JSONObject data = notification.payload.additionalData;
        if (data != null) {
            isNotiCall = data.optBoolean("IsCall", false);
        }

        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                // Sets the background notification color to Green on Android 5.0+ devices.
                //builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000, 1000,1000});
                //builder.setSound(soundUri);

                //builder.setAutoCancel(false);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                if (isNotiCall) {
                    builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                    builder.setColor(new BigInteger("FF00FF00", 16).intValue());
                    //builder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    //builder.setOngoing(true);

                    builder.setContentTitle(notification.payload.title);
                    builder.setContentText(notification.payload.body);
                    builder.setLargeIcon(myBitmap);
                    builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(myBitmap).bigLargeIcon(null));
                    builder.setStyle(new NotificationCompat.BigTextStyle().bigText(notification.payload.body));
                    //builder.setChannelId("my_channel_01");

                    builder.setOnlyAlertOnce(true);
                    builder.setWhen(System.currentTimeMillis());
                    //builder.setOngoing(true);
                    //builder.setCategory(NotificationCompat.CATEGORY_ALARM);
                    //builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
                }

                return builder;
            }
        };

        Log.e("Noti Extend ", notification.payload.body);

        /*
        JSONObject data = notification.payload.additionalData;
        if (data != null) {
            boolean isNotiCall = data.optBoolean("IsCall", false);
            if (isNotiCall) {
                OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.InAppAlert);
            }
            else {
                OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);
            }

        }
        */

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);

        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);
        return true;
    }


}
