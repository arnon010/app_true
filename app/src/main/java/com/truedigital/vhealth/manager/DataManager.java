package com.truedigital.vhealth.manager;

import com.truedigital.vhealth.BuildConfig;
import com.truedigital.vhealth.manager.prefs.PreferencesHelper;
import com.truedigital.vhealth.model.ApiAccessToken;

public class DataManager implements DataManagerInterface {

    private PreferencesHelper mPrefs;

    public DataManager() {
        mPrefs = new PreferencesHelper();
        setUserType(BuildConfig.USER_TYPE);
        setCanRefreshToken(true);
    }

    @Override
    public boolean isOpenApp() {
        return mPrefs.isOpenApp();
    }

    @Override
    public void setOpenApp(boolean isOpen) {
        mPrefs.setOpenApp(isOpen);
    }

    @Override
    public int getLocalLang() {
        return mPrefs.getLocalLang();
    }

    @Override
    public void setLocalLang(int lang) {
        mPrefs.setLocalLang(lang);
    }


    @Override
    public boolean isLogin() {
        return mPrefs.isLogin();
    }

    @Override
    public void setAccessToken(ApiAccessToken data) {
        mPrefs.setAccessToken(data);
    }

    @Override
    public void setLogin() {
        mPrefs.setLogin();
    }

    @Override
    public void Logout() {
        mPrefs.Logout();
    }

    @Override
    public void setAccess_token(String access_token) {
        mPrefs.setAccess_token(access_token);
    }

    @Override
    public String getAccess_token() {
        return mPrefs.getAccess_token();
    }

    @Override
    public void setEhrToken(String token) {
        mPrefs.setEhrToken(token);
    }

    @Override
    public String getEhrToken() {
        return mPrefs.getEhrToken();
    }

    @Override
    public void setRefresh_token(String refresh_token) {
        mPrefs.setRefresh_token(refresh_token);
    }

    @Override
    public String getRefresh_token() {
        return mPrefs.getRefresh_token();
    }

    @Override
    public void setCanRefreshToken(boolean canRefresh) {
        mPrefs.setCanRefreshToken(canRefresh);
    }

    @Override
    public boolean isCanRefreshToken() {
        return mPrefs.isCanRefreshToken();
    }

    @Override
    public void setAppointmentNumber(String appointmentNumber) {
        mPrefs.setAppointmentNumber(appointmentNumber);
    }

    @Override
    public String getAppointmentNumber() {
        return mPrefs.getAppointmentNumber();
    }

    @Override
    public int getContactTypeId() {
        return mPrefs.getContactTypeId();
    }

    @Override
    public void setContactTypeId(int contactTypeId) {
        mPrefs.setContactTypeId(contactTypeId);
    }

    @Override
    public int getUserId() {
        return mPrefs.getUserId();
    }

    @Override
    public void setUserId(int userId) {
        mPrefs.setUserId(userId);
    }

    @Override
    public String getUserName() {
        return mPrefs.getUserName();
    }

    @Override
    public void setUserName(String userName) {
        mPrefs.setUserName(userName);
    }

    @Override
    public void setTimeToCall(boolean isTimeToCall) {
        mPrefs.setTimeToCall(isTimeToCall);
    }

    @Override
    public boolean isTimeToCall() {
        return mPrefs.isTimeToCall();
    }

    @Override
    public String getContactTypeCode() {
        return mPrefs.getContactTypeCode();
    }

    @Override
    public void setContactTypeCode(String contactTypeCode) {
        mPrefs.setContactTypeCode(contactTypeCode);
    }

    @Override
    public boolean isAcceptAgreement() {
        return mPrefs.isAcceptAgreement();
    }

    @Override
    public void setAcceptAgreement(boolean isAcceptAgreement) {
        mPrefs.setAcceptAgreement(isAcceptAgreement);
    }

    @Override
    public boolean isShowGuide() {
        return mPrefs.isShowGuide();
    }

    @Override
    public void setShowGuide(boolean isShowGuide) {
        mPrefs.setShowGuide(isShowGuide);
    }

    @Override
    public String getDoctorName() {
        return mPrefs.getDoctorName();
    }

    @Override
    public void setDoctorName(String doctorName) {
        mPrefs.setDoctorName(doctorName);
    }

    @Override
    public String getDoctorImageUrl() {
        return mPrefs.getDoctorImageUrl();
    }

    @Override
    public void setDoctorImageUrl(String doctorImageUrl) {
        mPrefs.setDoctorImageUrl(doctorImageUrl);
    }

    @Override
    public int getPatientId() {
        return mPrefs.getPatientId();
    }

    @Override
    public void setPatientId(int patientId) {
        mPrefs.setPatientId(patientId);
    }

    @Override
    public int getDoctorId() {
        return mPrefs.getDoctorId();
    }

    @Override
    public void setDoctorId(int doctorId) {
        mPrefs.setDoctorId(doctorId);
    }

    @Override
    public String getUserEmail() {
        return mPrefs.getUserEmail();
    }

    @Override
    public void setUserEmail(String email) {
        mPrefs.setUserEmail(email);
    }

    @Override
    public String getProfileImage() {
        return mPrefs.getProfileImage();
    }

    @Override
    public void setProfileImage(String imageUrl) {
        mPrefs.setProfileImage(imageUrl);
    }

    @Override
    public String getDefaultLanguageCode() {
        return mPrefs.getDefaultLanguageCode();
    }

    @Override
    public void setDefaultLanguageCode(String lang) {
        mPrefs.setDefaultLanguageCode(lang);
    }

    @Override
    public void setUserType(String userType) {
        mPrefs.setUserType(userType);
    }

    @Override
    public String getUserType() {
        return mPrefs.getUserType();
    }

    @Override
    public boolean isDoctor() {
        return mPrefs.isDoctor();
    }

    @Override
    public String getFirstName() {
        return mPrefs.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        mPrefs.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return mPrefs.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        mPrefs.setLastName(lastName);
    }


    @Override
    public String getFullName() {
        return mPrefs.getFullName();
    }

    @Override
    public void setFullName(String fullName) {
        mPrefs.setFullName(fullName);
    }

    @Override
    public boolean isVerify() {
        return mPrefs.isVerify();
    }

    @Override
    public void setVerify(boolean isVerify) {
        mPrefs.setVerify(isVerify);
    }

    @Override
    public String getAppointmentRequest() {
        return mPrefs.getAppointmentRequest();
    }

    @Override
    public void setAppointmentRequest(String json) {
        mPrefs.setAppointmentRequest(json);
    }

    @Override
    public String getPhone() {
        return mPrefs.getPhone();
    }

    @Override
    public void setPhone(String phone) {
        mPrefs.setPhone(phone);
    }

    @Override
    public String getNotificationBody() {
        return mPrefs.getNotificationBody();
    }

    @Override
    public void setNotificationBody(String body) {
        mPrefs.setNotificationBody(body);
    }

    @Override
    public int getStatus() {
        return mPrefs.getStatus();
    }

    @Override
    public void setStatus(int status) {
        mPrefs.setStatus(status);
    }

    @Override
    public int getLoginBy() {
        return mPrefs.getLoginBy();
    }

    @Override
    public void setLoginBy(int loginBy) {
        mPrefs.setLoginBy(loginBy);
    }

    @Override
    public boolean isSetLocalFirst() {
        return mPrefs.isSetLocalFirst();
    }

    @Override
    public void setLocalFirst(boolean isFirst) {
        mPrefs.setLocalFirst(isFirst);
    }

    @Override
    public long getLastTimeRefreshToken() {
        return mPrefs.getLastTimeRefreshToken();
    }

    @Override
    public void setLastTimeRefreshToken() {
        mPrefs.setLastTimeRefreshToken();
    }

    @Override
    public boolean isMustRefreshToken() {
        return mPrefs.isMustRefreshToken();
    }
}
