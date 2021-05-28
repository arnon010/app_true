package com.truedigital.vhealth.manager.prefs;

import com.truedigital.vhealth.model.ApiAccessToken;
import com.truedigital.vhealth.utils.AppConstants;
import com.orhanobut.hawk.Hawk;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static com.facebook.appevents.internal.AppEventUtility.bytesToHex;

public class PreferencesHelper implements PreferencesHelperInterface {

    private static final String PREF_KEY_LOCAL_LANG = "PREF_KEY_LOCAL_LANG";
    private static final String PREF_KEY_LOCAL_LANG_FIRST = "PREF_KEY_LOCAL_LANG_FIRST";

    private static final String PREF_KEY_USER_ID = "PREF_KEY_USER_ID";
    private static final String PREF_KEY_USER_NAME = "PREF_KEY_USER_NAME";
    private static final String PREF_KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL";
    private static final String PREF_KEY_USER_IMAGE_URL = "PREF_KEY_USER_IMAGE_URL";

    private static final String PREF_KEY_IS_LOGGED_IN = "PREF_KEY_IS_LOGGED_IN";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_EHR_TOKEN = "PREF_KEY_EHR_TOKEN";
    private static final String PREF_KEY_APPOINTMENT_NUMBER = "PREF_KEY_APPOINTMENT_NUMBER";
    private static final String PREF_KEY_TIME_TOCALL = "PREF_KEY_TIME_TOCALL";
    private static final String PREF_KEY_CONTACT_TYPECODE = "PREF_KEY_CONTACT_TYPECODE";

    private static final String PREF_KEY_APPOINTMENT_TYPE = "PREF_KEY_APPOINTMENT_TYPE";
    private static final String PREF_KEY_REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN";
    private static final String PREF_KEY_ACCEPT_AGREEMENT = "PREF_KEY_ACCEPT_AGREEMENT";
    private static final String PREF_KEY_SHOW_GUIDE = "PREF_KEY_SHOW_GUIDE";
    private static final String PREF_KEY_DOCTOR_NAME = "PREF_KEY_DOCTOR_NAME";
    private static final String PREF_KEY_DOCTOR_IMAGE_URL = "PREF_KEY_DOCTOR_IMAGE_URL";
    private static final String PREF_KEY_PATIENT_ID = "PREF_KEY_PATIENT_ID";
    private static final String PREF_KEY_DOCTOR_ID = "PREF_KEY_DOCTOR_ID";

    private static final String PREF_KEY_DEFAULT_LANG = "PREF_KEY_DEFAULT_LANG";
    private static final String PREF_KEY_USER_TYPE = "PREF_KEY_USER_TYPE";
    private static final String PREF_USER_FIRST_NAME = "PREF_USER_FIRST_NAME";
    private static final String PREF_USER_LAST_NAME = "PREF_USER_LAST_NAME";
    private static final String PREF_KEY_IS_VERIFY = "PREF_KEY_IS_VERIFY";
    private static final String PREF_KEY_PHONE = "PREF_KEY_PHONE";

    private static final String PREF_KEY_APPOINTMENT_REQUEST = "PREF_KEY_APPOINTMENT_REQUEST";
    private static final String PREF_KEY_NOTIFICATION_BODY = "PREF_KEY_NOTIFICATION_BODY";

    private static final String PREF_USER_FULL_NAME = "PREF_USER_FULL_NAME";
    private static final String PREF_KEY_OPEN_APP = "PREF_KEY_OPEN_APP";

    private static final String PREF_KEY_APP_STATUS = "PREF_KEY_APP_STATUS";
    private static final String PREF_KEY_LOGIN_BY = "PREF_KEY_LOGIN_BY";
    private static final String PREF_KEY_CAN_REFRESH_TOKEN = "PREF_KEY_CAN_REFRESH_TOKEN";

    private static final String PREF_KEY_LAST_TIME_REFRESH_TOKEN = "PREF_KEY_LAST_TIME_REFRESH_TOKEN";

    @Override
    public boolean isOpenApp() {
        return get(PREF_KEY_OPEN_APP, false);
    }

    @Override
    public void setOpenApp(boolean isOpen) {
        put(PREF_KEY_OPEN_APP, isOpen);
    }

    @Override
    public int getLocalLang() {
        return get(PREF_KEY_LOCAL_LANG, AppConstants.NULL_INDEX);
    }

    @Override
    public void setLocalLang(int lang) {
        put(PREF_KEY_LOCAL_LANG, lang);
    }

    @Override
    public void setAccessToken(ApiAccessToken data) {
        setAccess_token(data.getAccess_token());
        setRefresh_token(data.getRefresh_token());
        setVerify(data.isVerify());

        setUserId(data.getUserId());
        setUserName(data.getUserName());
        setUserEmail(data.getEmail());
        setProfileImage(data.getProfileImage());
        setFirstName(data.getFirstname());
        setLastName(data.getLastname());
        setFullName(data.getFullname());
        setPhone(data.getTelephone());
        setPatientId(data.getPatientId());
    }

    public void setLogin() {
        put(PREF_KEY_IS_LOGGED_IN, true);
    }

    @Override
    public void Logout() {
        setUserId(AppConstants.NULL_INDEX);
        put(PREF_KEY_IS_LOGGED_IN, false);
    }

    @Override
    public boolean isLogin() {
        return getUserId() != AppConstants.NULL_INDEX && get(PREF_KEY_IS_LOGGED_IN, false);
    }

    @Override
    public void setAccess_token(String access_token) {
        put(PREF_KEY_ACCESS_TOKEN, access_token);
    }

    @Override
    public String getAccess_token() {
        return get(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setEhrToken(String token) {
        put(PREF_KEY_EHR_TOKEN, token);
    }

    @Override
    public String getEhrToken() {
        return get(PREF_KEY_EHR_TOKEN, "");
    }

    @Override
    public void setRefresh_token(String refresh_token) {
        put(PREF_KEY_REFRESH_TOKEN, refresh_token);
    }

    @Override
    public String getRefresh_token() {
        return get(PREF_KEY_REFRESH_TOKEN, null);
    }

    @Override
    public void setCanRefreshToken(boolean canRefresh) {
        put(PREF_KEY_CAN_REFRESH_TOKEN, canRefresh);
    }

    @Override
    public boolean isCanRefreshToken() {
        return get(PREF_KEY_CAN_REFRESH_TOKEN, false);
    }

    @Override
    public void setAppointmentNumber(String appointmentNumber) {
        put(PREF_KEY_APPOINTMENT_NUMBER, appointmentNumber);
    }

    @Override
    public String getAppointmentNumber() {
        return get(PREF_KEY_APPOINTMENT_NUMBER, null);
    }

    @Override
    public String getContactTypeCode() {
        return get(PREF_KEY_CONTACT_TYPECODE, "");
    }

    @Override
    public void setContactTypeCode(String contactTypeCode) {
        put(PREF_KEY_CONTACT_TYPECODE, contactTypeCode);
    }

    @Override
    public int getContactTypeId() {
        return get(PREF_KEY_APPOINTMENT_TYPE, AppConstants.NULL_INDEX);
    }

    @Override
    public void setContactTypeId(int contactTypeId) {
        put(PREF_KEY_APPOINTMENT_TYPE, contactTypeId);
    }

    @Override
    public int getUserId() {
        return get(PREF_KEY_USER_ID, AppConstants.NULL_INDEX);
    }

    @Override
    public void setUserId(int userId) {
        put(PREF_KEY_USER_ID, userId);
    }

    @Override
    public String getUserName() {
        return get(PREF_KEY_USER_NAME, "");
    }

    @Override
    public void setUserName(String userName) {
        put(PREF_KEY_USER_NAME, userName);
    }

    @Override
    public boolean isTimeToCall() {
        return get(PREF_KEY_TIME_TOCALL, false);
    }

    @Override
    public void setTimeToCall(boolean isTimeToCall) {
        put(PREF_KEY_TIME_TOCALL, isTimeToCall);
    }

    @Override
    public boolean isAcceptAgreement() {
        return get(PREF_KEY_ACCEPT_AGREEMENT, false);
    }

    @Override
    public void setAcceptAgreement(boolean isAcceptAgreement) {
        put(PREF_KEY_ACCEPT_AGREEMENT, isAcceptAgreement);
    }

    @Override
    public boolean isShowGuide() {
        return get(PREF_KEY_SHOW_GUIDE, false);
    }

    @Override
    public void setShowGuide(boolean isShowGuide) {
        put(PREF_KEY_SHOW_GUIDE, isShowGuide);
    }

    public String getDoctorName() {
        return get(PREF_KEY_DOCTOR_NAME, "");
    }

    public void setDoctorName(String doctorName) {
        put(PREF_KEY_DOCTOR_NAME, doctorName);
    }

    public String getDoctorImageUrl() {
        return get(PREF_KEY_DOCTOR_IMAGE_URL, "");
    }

    public void setDoctorImageUrl(String doctorImageUrl) {
        put(PREF_KEY_DOCTOR_IMAGE_URL, doctorImageUrl);
    }

    @Override
    public int getPatientId() {
        return get(PREF_KEY_PATIENT_ID, AppConstants.NULL_INDEX);
    }

    @Override
    public void setPatientId(int patientId) {
        put(PREF_KEY_PATIENT_ID, patientId);
    }

    @Override
    public int getDoctorId() {
        return get(PREF_KEY_DOCTOR_ID, AppConstants.NULL_INDEX);
    }

    @Override
    public void setDoctorId(int doctorId) {
        put(PREF_KEY_DOCTOR_ID, doctorId);
    }

    @Override
    public String getUserEmail() {
        return get(PREF_KEY_USER_EMAIL, "");
    }

    @Override
    public void setUserEmail(String email) {
        put(PREF_KEY_USER_EMAIL, email);
    }

    @Override
    public String getProfileImage() {
        return get(PREF_KEY_USER_IMAGE_URL, "");
    }

    @Override
    public void setProfileImage(String imageUrl) {
        put(PREF_KEY_USER_IMAGE_URL, imageUrl);
    }

    @Override
    public String getDefaultLanguageCode() {
        return get(PREF_KEY_DEFAULT_LANG, "");
    }

    @Override
    public void setDefaultLanguageCode(String lang) {
        put(PREF_KEY_DEFAULT_LANG, lang);
    }

    @Override
    public void setUserType(String userType) {
        put(PREF_KEY_USER_TYPE, userType);
    }

    @Override
    public String getUserType() {
        return get(PREF_KEY_USER_TYPE, "");
    }

    @Override
    public boolean isDoctor() {
        return getUserType().equalsIgnoreCase("DOCTOR");
    }


    @Override
    public String getFirstName() {
        return get(PREF_USER_FIRST_NAME, "");
    }

    @Override
    public void setFirstName(String firstName) {
        put(PREF_USER_FIRST_NAME, firstName);
    }

    @Override
    public String getLastName() {
        return get(PREF_USER_LAST_NAME, "");
    }

    @Override
    public void setLastName(String lastName) {
        put(PREF_USER_LAST_NAME, lastName);
    }

    @Override
    public String getFullName() {
        return get(PREF_USER_FULL_NAME, "");
    }

    @Override
    public void setFullName(String fullName) {
        put(PREF_USER_FULL_NAME, fullName);
    }

    @Override
    public boolean isVerify() {
        return get(PREF_KEY_IS_VERIFY, false);
    }

    @Override
    public void setVerify(boolean isVerify) {
        put(PREF_KEY_IS_VERIFY, isVerify);
    }


    @Override
    public String getAppointmentRequest() {
        return get(PREF_KEY_APPOINTMENT_REQUEST, "");
    }

    @Override
    public void setAppointmentRequest(String json) {
        put(PREF_KEY_APPOINTMENT_REQUEST, json);
    }

    @Override
    public String getPhone() {
        return get(PREF_KEY_PHONE, "");
    }

    @Override
    public void setPhone(String phone) {
        put(PREF_KEY_PHONE, phone);
    }

    @Override
    public String getNotificationBody() {
        return get(PREF_KEY_NOTIFICATION_BODY, "");
    }

    @Override
    public void setNotificationBody(String body) {
        put(PREF_KEY_NOTIFICATION_BODY, body);
    }

    @Override
    public int getLoginBy() {
        return get(PREF_KEY_LOGIN_BY, AppConstants.APP_LOGIN_PHONE);
    }

    @Override
    public void setLoginBy(int loginBy) {
        put(PREF_KEY_LOGIN_BY, loginBy);
    }

    @Override
    public int getStatus() {
        return get(PREF_KEY_APP_STATUS, AppConstants.APP_STATUS_NORMAL);
    }

    @Override
    public void setStatus(int status) {
        put(PREF_KEY_APP_STATUS, status);
    }

    @Override
    public void setLocalFirst(boolean isFirst) {
        put(PREF_KEY_LOCAL_LANG_FIRST, isFirst);
    }

    @Override
    public boolean isSetLocalFirst() {
        return get(PREF_KEY_LOCAL_LANG_FIRST, false);
    }

    @Override
    public long getLastTimeRefreshToken() {
        return get(PREF_KEY_LAST_TIME_REFRESH_TOKEN, 0L);
    }

    @Override
    public void setLastTimeRefreshToken() {
        put(PREF_KEY_LAST_TIME_REFRESH_TOKEN, new Date().getTime());
    }

    @Override
    public boolean isMustRefreshToken() {
        long time = new Date().getTime();
        long lastTime = getLastTimeRefreshToken();
        if (time > lastTime + 30 * 60 * 1000) {
            setLastTimeRefreshToken();
            return true;
        } else {
            return false;
        }
    }

    private <T> void put(String key, T value) {
        Hawk.put(hash256(key), value);
    }

    private <T> T get(String key, T defaultValue) {
        return Hawk.get(hash256(key), defaultValue);
    }

    private String hash256(String value) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hashbytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashbytes);
        } catch (NoSuchAlgorithmException e) {
            return value;
        }
    }
}
