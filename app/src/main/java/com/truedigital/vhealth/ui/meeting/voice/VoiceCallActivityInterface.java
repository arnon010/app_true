package com.truedigital.vhealth.ui.meeting.voice;

import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class VoiceCallActivityInterface {
    public interface View extends BaseMvpInterface.View {

        void openDoctorNote();
        void openRating();
        void openConnectVoiceCall(String tokbox_session, String tokbox_token);
        void setTokbox_Session(String tokbox_session);
        void setTokbox_Token(String tokbox_token);

        void onStopCall();
        void onCameraSwap();
        void onMicroPhoneSelect();
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

        void onAfterEndCall();



    }
    public interface Presenter extends BaseMvpInterface.Presenter<View> {
        void onStopCallClick();
        void onCameraSwapClick();
        void onMicroPhoneClick();

        void apiGetTokenTokbox();
        void apiPostLogConcurrent();
        void apiGetAppointmentTimeLeft();
        void apiGetAppointment();
        void apiLogStartCall();
        void apiLogEndCall();
        //void stopAppointment(String appointmentNumber);

        void callLogStart(String appointmentNumber);
        void callLogEnd(String appointmentNumber);
        void callLogConcurrent(String appointmentNumber);


        void callEndCallAppointment(String appointmentNumber);
        void callRating(int doctorId, String appointmentNumber, float rate, String suggestion);
    }
}
