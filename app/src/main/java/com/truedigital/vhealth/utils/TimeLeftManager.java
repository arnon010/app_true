package com.truedigital.vhealth.utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by songkrit on 4/30/2018 AD.
 */

public class TimeLeftManager {
    private final String TAG = "TimeLeftManager";
    private OnTimerListener onTimerListener;
    private long timeRemain;
    private TimeLeft timeLeft;
    private final Context context;
    private Activity activity;
    private boolean isSend;
    private boolean showRateApp;


    private static TimeLeftManager instance;

    public static TimeLeftManager getInstance(Context context) {
        if (instance == null) {
            instance = new TimeLeftManager(context);
        }
        return instance;
    }


    public TimeLeftManager(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public interface OnTimerListener {
        void onTimerFinish();
        void onTimerTick(int minutes, int seconds, String time);
    }


    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.timeRemain = calendar.get(Calendar.MILLISECOND) + 6000;
    }

    public void setOnTimerListener(OnTimerListener onTimerListener) {
        this.onTimerListener = onTimerListener;
    }

    //..strTime = HH:MM:SS
    public void setTime(String strTime, int addSecond) {
        String[] timeArray = strTime.split(":");
        int HH = Integer.parseInt(timeArray[0]);
        int mm = Integer.parseInt(timeArray[1]);
        int ss = Integer.parseInt(timeArray[2]);
        this.timeRemain = (HH * 60 * 6000) + (mm * 60000) + (ss * 1000) + (addSecond * 1000);
        Log.e(TAG,"Timer " + timeRemain);
    }

    public void setTime(long millisecond) {
        this.timeRemain = millisecond;
        Log.e(TAG,"Timer " + timeRemain);
    }

    public long getTimeRemain() {
        return timeRemain;
    }

    public void setTimeRemain(long timeRemain) {
        this.timeRemain = timeRemain + 6000;
    }

    public boolean isShowRateApp() {
        return showRateApp;
    }

    public void setShowRateApp(boolean showRateApp) {
        this.showRateApp = showRateApp;
    }

    public void start() {
        cancel();
        timeLeft = new TimeLeft(timeRemain,1000);
        timeLeft.start();
    }

    public void cancel() {
        if (timeLeft != null) timeLeft.cancel();
    }

    public class TimeLeft extends CountDownTimer {
        public TimeLeft(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long milliseconds) {
            int seconds, minutes, hours;

            seconds = (int) milliseconds / 1000;

            minutes = seconds / 60;
            seconds = seconds % 60;
            hours = minutes / 60;
            minutes = minutes % 60;

            String time = String.format("%02d:%02d:%02d",hours,minutes,seconds);
            Log.e(TAG,"Timer " + time);
            //..
            if (onTimerListener != null) {
                onTimerListener.onTimerTick(minutes, seconds, time);
            }
        }

        @Override
        public void onFinish() {
            Log.e(TAG,"Timeleft Finish....");
            if (onTimerListener != null) {
                onTimerListener.onTimerFinish();
            }
        }
    }
}
