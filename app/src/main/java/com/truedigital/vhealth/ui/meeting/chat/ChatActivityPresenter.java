package com.truedigital.vhealth.ui.meeting.chat;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiChatListObject;
import com.truedigital.vhealth.model.ApiChatObject;
import com.truedigital.vhealth.model.ApiTokenTokboxObject;
import com.truedigital.vhealth.model.DoctorRatingRequest;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.appointment.ApiAppointmentTimeLeft;
import com.truedigital.vhealth.model.appointment.ApiItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.truedigital.vhealth.api.RetrofitBuilder.addMultipartBody;
import static com.truedigital.vhealth.api.RetrofitBuilder.addRequestBody;
import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class ChatActivityPresenter extends BaseMvpPresenter<ChatActivityInterface.View>
        implements ChatActivityInterface.Presenter{

    private static final String TAG = "VideoCall";
    private int countCallApi;

    public static ChatActivityInterface.Presenter create() {
        return new ChatActivityPresenter();
    }

    @Override
    public void onStopCallClick() {
        getView().onStopCall();
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
            callLogConcurrent(getView().getAppointmentNumber());
        }
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
                        getView().openConnect(apiTokenTokboxObject.getSessionId(),apiTokenTokboxObject.getToken());
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
                    getView().setData(response.body().getData());
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
                    //getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<NormalResponseObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void callSendMessage(int userId, String appointmentNumber, String message) {
        getView().showLoading();
        //ApiChatObject request = new ApiChatObject();
        //request.setMessage(message);

        Call<ApiChatObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postChatMessage(appointmentNumber, message);
        call.enqueue(new Callback<ApiChatObject>() {
            @Override
            public void onResponse(Call<ApiChatObject> call, Response<ApiChatObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().onSendMessageSuccess(response.body());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiChatObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }

    @Override
    public void callSendImage(Activity activity, String appointmentNumber, Uri uriImage) {
        getView().showLoading();

        MultipartBody.Part imageBody = addMultipartBody(activity, "image", uriImage);
        RequestBody body = addRequestBody("");

        Call<ApiChatObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postChatImage(appointmentNumber,body, imageBody);
        call.enqueue(new Callback<ApiChatObject>() {
            @Override
            public void onResponse(Call<ApiChatObject> call, Response<ApiChatObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().onSendImageSuccess(response.body());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiChatObject> call, Throwable t) {
                getView().hideLoading();
            }
        });

    }

    @Override
    public void getChatMessageList(String appointmentNumber) {
        getView().showLoading();

        Call<ApiChatListObject> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getChatMessageList(appointmentNumber);
        call.enqueue(new Callback<ApiChatListObject>() {
            @Override
            public void onResponse(Call<ApiChatListObject> call, Response<ApiChatListObject> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().onGetMessageListSuccess(response.body());
                }
                else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiChatListObject> call, Throwable t) {
                getView().hideLoading();
            }
        });
    }
}
