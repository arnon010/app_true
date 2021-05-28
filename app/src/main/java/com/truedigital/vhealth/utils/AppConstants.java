package com.truedigital.vhealth.utils;

/**
 * Created by songkrit on 1/16/2018 AD.
 */

public final class AppConstants {
    //..Authorize
    public static final String AUTHEN_CLIENT_ID = "dd88c63b-df64-48b9-8d2a-9faac1a6c43e";
    public static final String AUTHEN_CLIENT_SECRET = "2a9933eb-c7f5-43bb-b4d9-1e7b8014338f";
    public static final String AUTHEN_TYPE = "1";
    public static final String AUTHEN_GRANT_TYPE = "password";
    public static final String AUTHEN_GRANT_TYPE_REFRESH = "refresh_token";
    public static final String AUTHEN_METHOD_PASSWORD = "password";
    public static final String AUTHEN_METHOD_OTP = "OTP";
    public static final String AUTHEN_METHOD_FACEBOOK = "facebook";

    public static final int AUTHEN_UNAUTHEN = 400;
    public static final int AUTHEN_UNAUTHORIZED = 401;
    public static final int AUTHEN_EHR_UNAUTHORIZED = 412;

    //..
    public static final String APP_MOBILE_OS = "Android";

    //..Appbar
    public static final int APPBAR_OFFSET_SIZE = 90;

    //..Validation
    public static final int VALID_MOBILE_LENGTH = 9;
    public static final int VALID_OTP_LENGTH = 6;

    public static final String LOCAL_LANG_THAI = "th";
    public static final String LOCAL_LANG_ENG = "en";

    //..Extra arguments
    public static final String EXTRA_MOBILE_PREFIX = "MOBILE_PREFIX";
    public static final String EXTRA_MOBILE_NO = "MOBILE_NO";
    public static final String EXTRA_REFERENCE_OTP = "REFERENCE_OTP";
    public static final String EXTRA_USERNAME = "USERNAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_TOKEN = "TOKEN";
    public static final String EXTRA_CATEGORY = "CATEGORY";
    public static final String EXTRA_APPOINTMENT_NUMBER = "EXTRA_APPOINTMENT_NUMBER";
    public static final String EXTRA_APPOINTMENT_TYPE = "EXTRA_APPOINTMENT_TYPE";
    public static final String EXTRA_APPOINTMENT_NO = "EXTRA_APPOINTMENT_NO";
    public static final String EXTRA_CONTACT_TYPE = "EXTRA_CONTACT_TYPE";
    public static final String EXTRA_APPOINTMENT_STATUS = "EXTRA_APPOINTMENT_STATUS";

    public static final String APPOINTMENT_STATUS_STOPCALL = "STOPCALL";
    public static final String APPOINTMENT_STATUS_CREATE = "APPOINTMENT_STATUS_CREATE";

    public static final String CONTACT_VIDEO = "Video";
    public static final String CONTACT_VOICE = "Voice";
    public static final String CONTACT_CHAT = "Chat";

    public static final String EXTRA_SIGNUP_MODE = "EXTRA_SIGNUP_MODE";
    public static final String EXTRA_SIGNUP_REFERENC_CODE = "EXTRA_SIGNUP_REFERENC_CODE";
    public static final String EXTRA_SIGNUP_OTP = "EXTRA_SIGNUP_OTP";
    public static final String EXTRA_FORGOT_PASSWORD_MODE = "EXTRA_FORGOT_PASSWORD_MODE";

    public static final int REQUEST_TIMESLOT = 500;
    public static final String EXTRA_TIMESLOT_DATE = "EXTRA_TIMESLOT_DATE";
    public static final String EXTRA_TIMESLOT_TIME = "EXTRA_TIMESLOT_TIME";
    //..Message
    public static final String MSG_CONFIRM_EMAIL = "confirmEmail";

    //..Doctor
    public static final String EXTRA_DOCTOR_ID = "DOCTOR_ID";
    public static final String EXTRA_DOCTOR_NAME = "DOCTOR_NAME";

    //..Article
    public static final String EXTRA_ARTICLE_ID = "EXTRA_ARTICLE_ID";
    public static final String EXTRA_ARTICLE_GROUP_ID = "EXTRA_ARTICLE_GROUP_ID";
    //..index
    public static final int NULL_INDEX = -1;
    // App Status
    public static final int APP_STATUS_NORMAL = 0;
    public static final int APP_STATUS_SIGNUP = 1;
    // Login Status
    public static final int APP_LOGIN_PHONE = 0;
    public static final int APP_LOGIN_FACEBOOK = 1;
    //Request Code
    public static final int REQUEST_CODE_CATEGORY = 101;
    public static final int REQUEST_CODE_ROOM = 201;
    public static final int REQUEST_CODE_EHR_PIN = 1;
    public static final int REQUEST_CODE_TIME_SLOT = 301;
    public static final int REQUEST_CODE_AUTHORIZE_PAYMENT = 209;

    //Request Code Media
    public static final int REQUEST_PICK_IMAGE_IMAGE_WRITE_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_PICK_IMAGE_IMAGE_READ_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_PICK_IMAGE = 3;
    public static final int REQUEST_CAMERA_READ_WRITE_EXTERNAL_STORAGE = 4;
    public static final int REQUEST_CAMERA_READ_READ_EXTERNAL_STORAGE = 5;
    public static final int REQUEST_CAMERA = 6;

    public enum GridMatrixType {
        Text,
        Image,
        TextAndImage
    }

    public static final int TIMEOUT_SECONDS = 60;

    //e-HR Menu
    public static final String EHR_MENU_EMPTY = "EHR000";
    public static final String EHR_MENU_INFORMATION = "EHR001";
    public static final String EHR_MENU_DOCTOR_NOTE = "EHR002";
    public static final String EHR_MENU_MEDICINE_ALLERGY = "EHR003";
    public static final String EHR_MENU_FOOD_ALLERGY = "EHR004";
    public static final String EHR_MENU_PREGNANT_HISTORY = "EHR005";
    public static final String EHR_MENU_MEDICATION_HISTORY = "EHR006";
    public static final String EHR_MENU_VACINATION_HISTORY = "EHR007";
    public static final String EHR_MENU_LABORATORY = "EHR008";
    public static final String EHR_MENU_FAMILY = "EHR009";
    public static final String EHR_MENU_CHILD_GROWTH_HISTORY = "EHR010";
    public static final String EHR_MENU_HEART_BEAT_RATE = "EHR011";
    public static final String EHR_MENU_BLOOD_PRESSURE = "EHR012";
    public static final String EHR_MENU_BLOOD_SUGAR = "EHR013";
    public static final String EHR_MENU_MENSTRUAL_PERIOD = "EHR014";
    public static final String EHR_MENU_MEDICAL_HISTORY = "EHR015";

    public static final int PAGE_SIZE = 10;
    public static final int PAGE_SIZE_NORMAL = 20;
    public static final int START_PAGE_INDEX = 1;

    // EHR Patient Heart Rate type
    public static final String HEART_RATE = "H";
    public static final String RESTING_RATE = "R";

    // EHR Blood Pressure Type
    public static final String SYS = "SYS";
    public static final String DIA = "DIA";
}
