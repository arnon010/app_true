package com.truedigital.vhealth.manager.prefs;

import com.truedigital.vhealth.model.ApiAccessToken;

public interface PreferencesHelperInterface {

    boolean isOpenApp();
    void setOpenApp(boolean isOpen);

    void setUserType(String userType);
    String getUserType();
    boolean isDoctor();

    int getLocalLang();
    void setLocalLang(int lang);

    boolean isSetLocalFirst();
    void setLocalFirst(boolean isFirst);

    void setAccessToken(ApiAccessToken data);

    void setLogin();
    void Logout();
    boolean isLogin();

    void setAccess_token(String access_token);
    String getAccess_token();

    void setEhrToken(String token);
    String getEhrToken();

    void setRefresh_token(String refresh_token);
    String getRefresh_token();

    void setCanRefreshToken(boolean canRefresh);
    boolean isCanRefreshToken();

    void setAppointmentNumber(String appointmentNumber);
    String getAppointmentNumber();

    String getContactTypeCode();
    void setContactTypeCode(String contactTypeCode);

    int getContactTypeId();
    void setContactTypeId(int contactTypeId);

    int getUserId();

    void setUserId(int userId);

    String getUserName();

    void setUserName(String userName);

    boolean isTimeToCall();

    void setTimeToCall(boolean isTimeToCall);

    boolean isAcceptAgreement();

    void setAcceptAgreement(boolean isAcceptAgreement);

    boolean isShowGuide();

    void setShowGuide(boolean isShowGuide);

    String getDoctorName();
    void setDoctorName(String doctorName);

    String getDoctorImageUrl();
    void setDoctorImageUrl(String doctorImageUrl);

    int getPatientId();
    void setPatientId(int patientId);

    int getDoctorId();
    void setDoctorId(int doctorId);

    String getUserEmail();
    void setUserEmail(String email);

    String getProfileImage();
    void setProfileImage(String imageUrl);

    String getDefaultLanguageCode();
    void setDefaultLanguageCode(String lang);

    String getFirstName();
    void setFirstName(String firstName);

    String getLastName();
    void setLastName(String lastName);

    String getFullName();
    void setFullName(String fullName);

    boolean isVerify();
    void setVerify(boolean isVerify);

    String getAppointmentRequest();
    void setAppointmentRequest(String json);

    String getPhone();
    void setPhone(String phone);

    String getNotificationBody();
    void setNotificationBody(String body);

    int getLoginBy();
    void setLoginBy(int loginBy);

    int getStatus();
    void setStatus(int status);

    long getLastTimeRefreshToken();
    void setLastTimeRefreshToken();

    boolean isMustRefreshToken();
}
