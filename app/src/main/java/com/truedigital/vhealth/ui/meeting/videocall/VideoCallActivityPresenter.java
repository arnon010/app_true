package com.truedigital.vhealth.ui.meeting.videocall;

import android.util.Log;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiTokenTokboxObject;
import com.truedigital.vhealth.model.DoctorRatingRequest;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.appointment.ApiAppointmentTimeLeft;
import com.truedigital.vhealth.model.appointment.ApiItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class VideoCallActivityPresenter extends BaseMvpPresenter<VideoCallActivityInterface.View>
        implements VideoCallActivityInterface.Presenter{

    private static final String TAG = "VideoCall";
    private int countCallApi;

    public static VideoCallActivityInterface.Presenter create() {
        return new VideoCallActivityPresenter();
    }

    @Override
    public void onStopCallClick() {
        getView().onStopCall();
    }

    @Override
    public void onCameraSwapClick() {
        getView().onCameraSwap();
    }

    @Override
    public void onMicroPhoneClick() {
        getView().onMicroPhoneSelect();
    }

    @Override
    public void apiGetTokenTokbox() {
        //.. Api v2
        String appointmentNumber = getView().getAppointmentNumber();
        callServiceTokenTokbox(appointmentNumber);

        //.. call Api V1
        //callServiceCreateTokenTokbox(AppManager.getDataManager().getAppointmentNumber(),
        //        AppManager.getDataManager().getUserId());
    }

    @Override
    public void apiPostLogConcurrent() {
        countCallApi = 0;
        //callServiceLogConcurrent(AppManager.getDataManager().getAppointmentNumber());
        callLogConcurrent(getView().getAppointmentNumber());
    }

    private void re_apiPostLogConcurrent() {
        countCallApi++;
        if (countCallApi > 2) {
            getView().onStopCallApi();
        }
        else {
            //callServiceLogConcurrent(AppManager.getDataManager().getAppointmentNumber());
        }
    }

    @Override
    public void callLogStart(String appointmentNumber) {
        Call<NormalResponseObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postLogStart(appointmentNumber);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Log Start success");
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                Log.e(TAG, "Log Start fail ");
            }
        });
    }

    @Override
    public void callLogEnd(String appointmentNumber) {
        Call<NormalResponseObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postLogEnd(appointmentNumber);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Log End success");
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                Log.e(TAG, "Log End fail ");
            }
        });
    }

    @Override
    public void callLogConcurrent(final String appointmentNumber) {
        Call<NormalResponseObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postLogConcurrent(appointmentNumber);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Log Concurrent success");
                    callServiceGetAppointmentTimeLeft(appointmentNumber);
                }
                else {
                    re_apiPostLogConcurrent();
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                Log.e(TAG, "Log Concurrent fail ");
            }
        });
    }


    @Override
    public void apiGetAppointmentTimeLeft() {
        countCallApi = 0;
        callServiceGetAppointmentTimeLeft(getView().getAppointmentNumber());
    }

    private void re_apiGetAppointmentTimeLeft() {
        countCallApi++;
        if (countCallApi > 2) {
            getView().onStopCallApi();
        }
        else {
            callServiceGetAppointmentTimeLeft(getView().getAppointmentNumber());
        }
    }

    @Override
    public void apiGetAppointment() {
        callServiceGetAppointment(getView().getAppointmentNumber());
    }

    @Override
    public void apiLogStartCall() {
        //callServiceLogStartCall(AppManager.getDataManager().getAppointmentNumber());
    }

    @Override
    public void apiLogEndCall() {
        //callServiceLogEndCall(AppManager.getDataManager().getAppointmentNumber());
    }

    private void callServiceTokenTokbox(String appointmentNumber) {
        String access_token = AppManager.getDataManager().getAccess_token();
        Call<ApiTokenTokboxObject> call = getAppointmentService(access_token).createTokboxToken(appointmentNumber);
        call.enqueue(new Callback<ApiTokenTokboxObject>() {
            @Override
            public void onResponse(Call<ApiTokenTokboxObject> call, Response<ApiTokenTokboxObject> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Success ");
                    ApiTokenTokboxObject apiTokenTokboxObject = response.body();
                    if (apiTokenTokboxObject.isSuccess()) {
                        getView().openConnectVideoCall(apiTokenTokboxObject.getSessionId(),apiTokenTokboxObject.getToken());
                    }
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiTokenTokboxObject> call, Throwable t) {
                Log.e(TAG,"Fail ");
                getView().showMessage(t.getMessage());
            }
        });
    }

    private void callServiceGetAppointment(final String appointmentNumber) {
        Call<ApiItemAppointmentDao> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getAppointmentInfo(appointmentNumber);
        call.enqueue(new Callback<ApiItemAppointmentDao>() {
            @Override
            public void onResponse(Call<ApiItemAppointmentDao> call, Response<ApiItemAppointmentDao> response) {
                if (response.isSuccessful()) {
                    getView().setInfo(response.body().getData());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiItemAppointmentDao> call, Throwable t) {
                getView().showMessage(""+t.getMessage());
            }
        });

    }

    /*
    @Override
    public void stopAppointment(String appointmentNumber) {
        String access_token = AppManager.getDataManager().getAccess_token();
        Call<NormalResponseObject> call = getAppointmentService(access_token).stopAppointment(appointmentNumber);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    //..
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().showMessage(t.getMessage());
            }
        });

    }
    */

    private void callServiceGetAppointmentTimeLeft(final String appointmentNumber) {
        Call<ApiAppointmentTimeLeft> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getAppointmentTimeLeft(appointmentNumber);
        call.enqueue(new Callback<ApiAppointmentTimeLeft>() {
            @Override
            public void onResponse(Call<ApiAppointmentTimeLeft> call, Response<ApiAppointmentTimeLeft> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG,"TimeLeft Success "+response.body().getTimeLeft());
                    getView().openTimeLeftStart(response.body().getTimeLeft());
                }
                else {
                    re_apiGetAppointmentTimeLeft();
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentTimeLeft> call, Throwable t) {
                Log.e(TAG,"TimeLeft Fail");
                re_apiGetAppointmentTimeLeft();
            }
        });
    }

    /*
    private void callServiceCreateTokenTokbox(String appointmentNumber,int userId) {

        Call<ApiTokenTokboxObject> call = getRetrofitService().postCreateTokenTokbox(
                appointmentNumber, userId);
        call.enqueue(new Callback<ApiTokenTokboxObject>() {
            @Override
            public void onResponse(Call<ApiTokenTokboxObject> call, Response<ApiTokenTokboxObject> response) {

                ApiTokenTokboxObject apiTokenTokboxObject = response.body();
                if (apiTokenTokboxObject == null) {
                    return;
                }
                if (apiTokenTokboxObject.isSuccess()) {
                    getView().openConnectVideoCall(apiTokenTokboxObject.getSessionId(),apiTokenTokboxObject.getToken());
                }
            }

            @Override
            public void onFailure(Call<ApiTokenTokboxObject> call, Throwable t) {
                //..
            }
        });
    }


    private void callServiceLogConcurrent(final String appointmentNumber) {
        Call<NormalResponseObject> call = getRetrofitToken("").postLogConcurrentConnections(appointmentNumber,appointmentNumber);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Concurrent Success ");
                    //callServiceGetAppointmentTimeLeft(appointmentNumber);
                }
                else {
                    re_apiPostLogConcurrent();
                }

            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                Log.e(TAG,"Concurrent Fail ");
                re_apiPostLogConcurrent();
            }
        });
    }


    private void callServiceLogStartCall(String appointmentNumber) {
        Call<NormalResponseObject> call = getRetrofitToken("").postLogStartCall(appointmentNumber,appointmentNumber);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Log Start call Success ");
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                Log.e(TAG, "Log Start call Fail ");
            }
        });
    }

    private void callServiceLogEndCall(String appointmentNumber) {
        Call<NormalResponseObject> call = getRetrofitToken("").postLogEndCall(appointmentNumber,appointmentNumber);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Log End call Success ");
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                Log.e(TAG, "Log End call Fail ");
            }
        });
    }


    private void callServiceGetAppointment(final String appointmentNumber) {
        Call<Appointment> call = getRetrofitToken("").getAppointment(appointmentNumber);
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG,"Appointment "+response.body().getAppointmentNumber());
                    getView().setInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                Log.e(TAG,"Appointment Fail");
            }
        });

    }
    */

    @Override
    public void callEndCallAppointment(String appointmentNumber) {
        Call<NormalResponseObject> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).endcallAppointment(appointmentNumber);
        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "Log End call Success ");
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                Log.e(TAG, "Log End call Fail ");
            }
        });
    }

    @Override
    public void callRating(int doctorId, String appointmentNumber, float rate, String suggestion) {
        getView().showLoading();

        DoctorRatingRequest request = new DoctorRatingRequest();
        request.setAppointmentNumber(appointmentNumber);
        request.setComment(suggestion);
        request.setRate((int) rate);

        Call<NormalResponseObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postDoctorRating(doctorId,request);

        call.enqueue(new Callback<NormalResponseObject>() {
            @Override
            public void onResponse(Call<NormalResponseObject> call, Response<NormalResponseObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().onShowSuccess();
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

}
