package com.truedigital.vhealth.ui.meeting.chat;

import android.app.Activity;
import android.net.Uri;

import com.truedigital.vhealth.model.ApiChatListObject;
import com.truedigital.vhealth.model.ApiChatObject;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class ChatActivityInterface {
    public interface View extends BaseMvpInterface.View {

        void openDoctorNote();
        void openRating();
        void openConnect(String tokbox_session, String tokbox_token);
        void setTokbox_Session(String tokbox_session);
        void setTokbox_Token(String tokbox_token);
        void onSendMessageSuccess(ApiChatObject data);
        void onSendImageSuccess(ApiChatObject data);
        void onGetMessageListSuccess(ApiChatListObject listData);

        void onStopCall();
        void disconnectSession();

        void openTimeLeftStart(String strTime);

        //void setInfo(Appointment data);
        void setInfo(ItemAppointmentDao data);
        void openStopConfirm();
        void onStopCallApi();
        void onShowSuccess();
        String getAppointmentNumber();

        void setDoctorId(int doctorId);
        void setDoctorName(String doctorName);
        int getDoctorId();
        String getDoctorName();

        void setData(ItemAppointmentDao data);
        void onAfterEndCall();



    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onStopCallClick();

        void apiGetTokenTokbox();
        void apiPostLogConcurrent();
        void apiGetAppointmentTimeLeft();
        void apiGetAppointment();

        void callLogStart(String appointmentNumber);
        void callLogEnd(String appointmentNumber);
        void callLogConcurrent(String appointmentNumber);

        //void stopAppointment(String appointmentNumber);
        void callSendMessage(int userId, String appointmentNumber, String message);
        void callSendImage(Activity activity, String appointmentNumber, Uri uriImage);
        void getChatMessageList(String appointmentNumber);

        void callEndCallAppointment(String appointmentNumber);
        void callRating(int doctorId, String appointmentNumber, float rate, String suggestion);
    }
}
