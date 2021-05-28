package com.truedigital.vhealth.manager;

import android.util.Log;

import com.truedigital.vhealth.manager.prefs.PreferencesHelper;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppointmentDataManager {

    private static final String TAG = "AppointmentDataManager";
    private static AppointmentDataManager instance;
    private PreferencesHelper mPrefs;

    ApiAppointmentRequest data;

    public AppointmentDataManager() {
        mPrefs = new PreferencesHelper();
        data = new ApiAppointmentRequest();
    }

    public static AppointmentDataManager getInstance() {
        if (instance == null) instance = new AppointmentDataManager();
        return instance;
    }

    public ApiAppointmentRequest getData() {
        loadData();
        return data;
    }

    public void setData(ApiAppointmentRequest data) {
        this.data = data;
        saveData(data);
    }

    private void saveData(ApiAppointmentRequest data) {
        Gson gson = new Gson();

        String json = gson.toJson(data);
        Log.e(TAG,"Save data" + gson.toJson(data));
        mPrefs.setAppointmentRequest(json);
    }

    private void loadData() {

        String json = mPrefs.getAppointmentRequest();
        if (json.equals("")) {
            return;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        data = gson.fromJson(json, ApiAppointmentRequest.class);

        Log.e(TAG,"load data" + gson.toJson(data));
    }
}

