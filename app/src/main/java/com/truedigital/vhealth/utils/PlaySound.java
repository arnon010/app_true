package com.truedigital.vhealth.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import com.truedigital.vhealth.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by songkrit on 5/7/2018 AD.
 */

public class PlaySound {
    private static final String TAG = "PlaySound";
    private static PlaySound instance;
    private static Ringtone ringtone;
    private static Vibrator vibrator;
    private static MediaPlayer mp;

    private boolean ignorePlay;

    public static PlaySound getInstance() {
        if (instance == null) {
            instance = new PlaySound();
        }
        return instance;
    }

    public PlaySound() {
        this.ignorePlay = false;
    }

    public boolean isIgnorePlay() {
        return ignorePlay;
    }

    public void setIgnorePlay(boolean ignorePlay) {
        this.ignorePlay = ignorePlay;
    }

    public void start() {
        if (!ignorePlay) {
            playVibrator();
            playSound();
        }
    }

    public void stop() {
        stopSound();
        stopVibrator();
        setIgnorePlay(false);
    }

    public void playRingtone() {
        try {
            final Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.notification_sound);
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), soundUri);
            ringtone.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRingtone() {
        if (ringtone == null) return;
        try {
            if (ringtone.isPlaying()) {
                ringtone.stop();
                ringtone = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        try {
            mp = MediaPlayer.create(getApplicationContext(),R.raw.notification_sound);
            mp.start();
            Log.e(TAG,"play sound...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopSound() {
        if (mp == null) return;
        try {
            if (mp.isPlaying()) {
                mp.stop();
                mp = null;
                Log.e(TAG,"stop sound...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playVibrator() {
        // Vibrate for 30 seconds
        try {
            vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(30000);
            Log.e(TAG,"play Vibrate...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stopVibrator() {
        try {
            if (vibrator.hasVibrator()) {
                vibrator.cancel();
                vibrator = null;
                Log.e(TAG,"stop Vibrate...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
