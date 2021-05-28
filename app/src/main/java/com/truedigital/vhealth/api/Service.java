package com.truedigital.vhealth.api;

/**
 * Created by songkrit on 12/25/2017 AD.
 */

public class Service {
    //USER

    public static final String PATH_LOGIN_AUTH = "Token";
    public static final String PATH_POST_GET_OTP = "api/OTP";
    public static final String PATH_POST_VERIFY_OTP = "api/OTP/Verify";


    public static final String PATH_GENERATE_OTP = "GenerateOTP";
    public static final String PATH_LOGIN_USER = "Login";
    public static final String PATH_REGISTER_USER = "UserRegister";
    public static final String PATH_REGISTER_USER_WITH_EMAIL = "UserRegisterWithEmail";
    public static final String PATH_REGISTER_USER_WITH_PASSWORD = "UserRegisterWithPassword";
    public static final String PATH_USER_INFO = "UserInfo";
    public static final String PATH_EDIT_PROFILE_USER = "EditProfileUser";
    public static final String PATH_PATCH_UPDATE_PROFILE_USER = "account/profile";
    public static final String PATH_GET_JOB_LIST = "ListJob";

    public static final String PATH_POST_CHECK_TELEPHONE = "Account/CheckAvailable";
    public static final String PATH_GET_CURRENT_USER = "Account";
    public static final String PATH_PATCH_CURRENT_USER = "Account";

    public static final String PATH_GET_APP_VERSION = "Version/CheckVersion";
    public static final String PATH_GET_LIST_CREDIT_CARD = "Omise";

    //.. Title
    public static final String PATH_GET_LIST_TITLE = "Titles";
    public static final String PATH_GET_LIST_STUDY_TITLE = "StudyTitles";
    //.. Categories
    public static final String PATH_GET_LIST_CATEGORIES = "categories";
    public static final String PATH_GET_LIST_SUB_CATEGORIES = "subcategories";

    //.. Categories
    public static final String PATH_GET_LIST_CLINICS = "clinics";
    public static final String PATH_GET_LIST_SUB_CLINICS = "subclinics";

    public static final String PATH_GET_LIST_DOCTORS = "doctors";
    public static final String PATH_GET_DOCTORS = "doctors/{DoctorId}";
    public static final String PATH_GET_DOCTORS_AVAILABLE_TIME = "doctors/{DoctorId}/availableTimes";
    public static final String PATH_POST_DOCTORS_FAVORITE = "doctors/{DoctorId}/favorite";
    public static final String PATH_POST_DOCTORS_RATING = "doctors/{DoctorId}/rate";

    public static final String PATH_CREATE_DOCTORS = "doctors";
    public static final String GET_DOCTORS_SERVICE = "doctors/preference";
    public static final String PATH_DOCTORS_SERVICE = "doctors/preference";
    public static final String GET_DOCTORS_SCHEDULES = "schedules/{DoctorId}/{Year}/{Month}";
    public static final String GET_DOCTORS_SCHEDULES_DAY = "schedules/{DoctorId}/{Year}/{Month}/{Day}";

    //..Articles
    public static final String PATH_GET_LIST_ARTICLES_CAROUSEL = "articles/carouselbanner";
    public static final String PATH_CREATE_ARTICLES = "articles";
    public static final String PATH_GET_ARTICLES = "articles";
    public static final String PATH_GET_ARTICLES_GROUP = "articlegroups";
    public static final String PATH_GET_ARTICLES_BY_ID = "articles/{ArticleId}";
    public static final String PATH_GET_ARTICLES_SIMILAR = "articles/{ArticleId}/similar";

    //..Product
    public static final String PATH_GET_LIST_PRODUCT_CAROUSEL = "products/carouselbanner";
    public static final String PATH_GET_LIST_PRODUCT_GROUPS = "productgroups";
    public static final String PATH_GET_LIST_PRODUCTS = "products";
    public static final String PATH_GET_PRODUCT = "products/{ProductId}";
    public static final String PATH_POST_BUY_PRODUCT = "products/buy";
    public static final String PATH_POST_BUY_PRODUCT_WITH_SLIP  = "products/buywithslip";
    //public static final String PATH_LIST_SYMPTOMTYPE = "ListSymptomType";
    //public static final String PATH_LIST_DOCTOR = "DoctorList";
    //public static final String PATH_LIST_CLINIC = "ListClinicDao";

    public static final String PATH_GET_APPOINTMENTS = "appointments";
    public static final String PATH_GET_APPOINTMENT_INFO = "appointments/{AppointmentNumber}/info";


    public static final String PATH_POST_CHAT_MESSAGE = "logs/{AppointmentNumber}/chatmessages";
    public static final String PATH_GET_CHAT_MESSAGE = "logs/{AppointmentNumber}/chatmessages";
    public static final String PATH_GET_CHAT_MESSAGE_LIST = "logs/{AppointmentNumber}/chatmessages";
    public static final String PATH_GET_CHAT_LIST = "logs/{AppointmentNumber}/chatmessages";

    public static final String PATH_POST_LOG_START = "logs/{AppointmentNumber}/start";
    public static final String PATH_POST_LOG_END = "logs/{AppointmentNumber}/end";
    public static final String PATH_POST_LOG_CONCURRENT = "logs/{AppointmentNumber}/concurrent";

    public static final String PATH_CHAT_MESSAGE = "logs/{AppointmentNumber}/chatmessages";
    public static final String PATH_CHAT_MESSAGE_LIST = "logs/{AppointmentNumber}/chatmessages";

    public static final String PATH_SEARCH_DOCTOR = "FilterGetDoctorList";
    public static final String PATH_FAVORITE = "FavoriteDoctor";
    public static final String PATH_FREQUENCY_DOCTOR = "UserFrequentDoctor";
    public static final String PATH_LAST_TIME_DOCTOR = "UserLastTimeDoctor";
    public static final String PATH_ADD_FAVORITE = "AddFavoriteDoctor";
    public static final String PATH_DELETE_FAVORITE = "DeleteFavoriteDoctor";
    public static final String PATH_CHECK_FAVORITE = "CheckFavoriteDoctor";
    public static final String PATH_RATE_DOCTOR = "CalculateDoctorRate";
    public static final String PATH_USER_TIME_PLAN = "UserTimePlan"; // history
    public static final String PATH_MONTH_READY_TIME = "UserGetDocotorMonthReadyTime";
    public static final String PATH_DOCTOR_MONTH_READY_TIME = "DoctorGetDocotorMonthReadyTime";
    public static final String PATH_TIME_PLAN_BY_DATE = "DoctorTimePlanByDateTime";
    public static final String PATH_BOOKING_NO = "UserPreviewBookingReadyTime";

    //public static final String PATH_CHECK_COUPON = "CheckDiscountCode";
    public static final String PATH_CHECK_COUPON = "CheckDiscountCodeWithLog";
    public static final String PATH_EXTEND_COUPON = "ExtendCoupon";

    public static final String PATH_CANCEL_APPOINTMENT = "UserCancelAppointment";
    public static final String PATH_CHECK_IS_CANCEL_APPOINTMENT = "UserCheckCancelAppointment";
    public static final String PATH_CHECK_CURRENT_APPOINTMENT = "CheckCurrentAppointment";
    public static final String PATH_ENDCALL_ONBOOKING = "EndCallOnBooking";

    //public static final String PATH_FREE_BOOKING = "UserFreeBooking";
    public static final String PATH_FREE_BOOKING = "UserBookingWithoutPay";

    public static final String PATH_PAYMENT_HISTORY = "UserListPayment";
    public static final String PATH_CHECK_DATE_TIME_BOOKING = "CheckDateTimeBooking";

    //DOCTOR
    public static final String PATH_LOGIN_DOCTOR = "loginDoctor";
    public static final String PATH_REGISTER_DOCTOR = "UserDoctorRegisterAndroid";
    public static final String PATH_LIST_SUB_SPECIALTY = "ListSubSpecialty";
    public static final String PATH_NEWS_LISTS = "getActivity";
    public static final String PATH_POST_NEWS_DETAIL = "ActivityDetail";
    public static final String PATH_DOCTOR_INFO = "DoctorInfo";
    public static final String PATH_EDIT_PROFILE_DOCTOR = "EditProfileDoctor";
    public static final String PATH_DOCTOR_TIME_PLAN = "DoctorTimePlan"; // history and schedule appointment
    public static final String PATH_MASTER_TIME_PLAN = "DoctorMasterTimePlanByDoctorId";
    public static final String PATH_MASTER_TIME_PLAN_BY_DATE = "DoctorMasterTimePlanByDate";
    public static final String PATH_ADD_TIME_PLAN = "DoctorAddMasterTimePlan";
    public static final String PATH_EDIT_TIME_PLAN = "DoctorEditMasterTimePlan";
    public static final String PATH_DELETE_TIME_PLAN_BYDATE = "DoctorDeleteSlotMasterTimePlan";
    public static final String PATH_SET_CONTACT_PRICE = "DoctorSetContactPrice";
    public static final String PATH_GET_CONTACT_PRICE = "DoctorGetContactPrice";
    public static final String PATH_SET_NOTIFICATION_TIME = "DoctorSetNotiAppointmentTime";
    public static final String PATH_GET_NOTIFICATION_TIME = "DoctorGetNotiAppointmentTime";
    public static final String PATH_SETTING_LANGUAGE = "DoctorSetLanguage";
    public static final String PATH_GET_LANGUAGE = "DoctorGetLanguage";
    public static final String PATH_UPLOAD_IMAGE = "DoctorUploadImage";

    //ALL 
    public static final String PATH_FORGOT_PASSWORD = "ForgetPassword";
    public static final String PATH_CHANGE_PASSWORD = "ChangePassword";
    public static final String PATH_CHANGE_PASSWORD_FROM_FORGET = "ChangePasswordFromForget";
    public static final String PATH_UPDATE_IMAGE_PROFILE = "UpdateUserImage";

    public static final String PATH_GET_LIST_SPECIALTY = "ListSpecialty";
    public static final String PATH_GET_LIST_ALL_SPECIALTY = "ListAllSpecialty";
    public static final String PATH_GET_LIST_SECTION = "ListSection";
    public static final String PATH_GET_LIST_CLINIC = "ListClinicDao";
    public static final String PATH_GET_LIST_SYMPTOM = "ListSymptomType";
    public static final String PATH_GET_LIST_DOCTOR = "DoctorList";


    //Favorite
    public static final String PATH_ADD_FAVORITE_CLINIC = "AddFavoriteClinic";
    public static final String PATH_ADD_FAVORITE_SYMPTOM = "AddFavoriteSymptomType";
    public static final String PATH_DEL_FAVORITE_CLINIC = "DeleteFavoriteClinic";
    public static final String PATH_DEL_FAVORITE_SYMPTOM = "DeleteFavoriteSymptomType";
    public static final String PATH_ADD_FAVORITE_DOCTOR = "AddFavoriteDoctor";
    public static final String PATH_DEL_FAVORITE_DOCTOR = "DeleteFavoriteDoctor";

    public static final String PATH_ADD_ONESIGNAL_TOKEN = "onesignaltokens";
    public static final String PATH_ADD_TOKEN = "Addtoken";
    public static final String PATH_SEARCH_BOOKING = "UserSearchBookingReadyTime";
    public static final String PATH_SEND_NOTIFICATION = "notifications";
    public static final String PATH_GET_TIME_SERVER = "GetTimeServer";
    public static final String PATH_CREATE_TOKEN_TOKBOX = "OpenTokCreateToken";
    public static final String PATH_LOG_OUT = "Logout";
    public static final String PATH_LOG_START_CALL = "LogStartCall";
    public static final String PATH_LOG_END_CALL = "LogEndCall";
    public static final String PATH_LOG_CONCURRENT_CONNECTIONS = "LogConcurrentConnection";
    public static final String PATH_LOG_APPOINTMENT_STATUS = "CheckAppointmentStatus";
    public static final String PATH_LOG_SIGNAL_CALL = "LogSignal";
    public static final String PATH_LOG_CHECK_START_CALL = "CheckStartCall";
    public static final String PATH_LIST_NOTIFICATION_MESSAGE = "UserListNoti";

    public static final String PATH_TIME_LEFT = "AppointmentTimeLeft";
    public static final String PATH_CHECK_TOKEN = "CheckUserToken";

    public static final String PATH_CHECK_APPLICATION_VERSION = "CheckApplicationVersion";
    public static final String PATH_SEND_SHORTNOTE = "SendShortNote";

    public static final String PATH_VALIDATE_BOOKING = "ValidateBooking";



    //--SystemConfiguration
    public static final String PATH_GET_SYSTEM_CONFIGURATION_API = "Parameters";

    //EHR
    //--login EHR
    public static final String PATH_POST_LOGIN_EHR = "Account/LoginEHR";
    public static final String PATH_GET_PIN_STATUS = "Account/HasPIN";
    public static final String PATH_POST_CREATE_PIN_EHR = "Account/SetPIN";
    //--EHRMenus
    public static final String PATH_GET_PATIENT_BY_ID = "Patients/{Id}";

    //--EHRMenus
    public static final String PATH_GET_MENU_LIST = "EHRMenus/{PatientId}/MenuList";
    public static final String PATH_GET_CURRENT_MENU = "EHRMenus/{PatientId}/CurrentMenu";
    public static final String PATH_GET_CURRENT_SUB_MENU = "EHRMenus/{PatientId}/CurrentSubMenu";
    public static final String PATH_GET_PATIENT_MENU = "EHRMenus/{PatientMenuId}/PatientMenu";
    public static final String PATH_POST_CHECK_PATIENT_EHR_MENU = "EHRMenus/{PatientId}/CheckPatientEHRMenu";

    //--Family
    public static final String PATH_POST_CREATE_PATIENT_RELATIONSHIP = "Family/{PatientId}";
    public static final String PATH_GET_PATIENT_RELATIONSHIP = "Family/{PatientId}";

    //--DoctorNote
    public static final String PATH_GET_DOCTOR_NOTE_LIST = "DoctorNote/{PatientId}";
    public static final String PATH_GET_DOCTOR_NOTE_DETAIL = "DoctorNote/{PatientId}/Detail";
    public static final String PATH_POST_CHECK_RECOMMEND_PRODUCT_COUPONS = "DiscountCodes/{DiscountCode}/CheckAppointmentRecommendProduct";
    public static final String PATH_GET_DEFAULT_DATE_DOCTOR_NOTE = "DoctorNote/{PatientId}/DefaultDate";
    public static final String PATH_GET_DOCTOR_LIST_DOCTOR_NOTE = "DoctorNote/{PatientId}/Doctor";

    //--BankAccount
    public static final String PATH_GET_BANK_ACCOUNT_LIST = "BankAccount";

    //--Pregnant
    public static final String PATH_GET_PREGNANT_LIST = "Pregnant/{PatientId}";
    public static final String PATH_GET_PREGNANT_DETAIL = "Pregnant/{PatientId}/Detail";
    public static final String PATH_GET_DEFAULT_DATE_PREGNANT = "Pregnant/{PatientId}/DefaultDate";
    public static final String PATH_GET_PLACE_LIST_PREGNANT = "Pregnant/{PatientId}/Place";
    public static final String PATH_POST_PREGNANT_HISTORY = "Pregnant/{PatientId}";
    public static final String PATH_PUT_PREGNANT_HISTORY = "Pregnant/{PatientId}";
    public static final String PATH_DELETE_PREGNANT_HISTORY = "Pregnant/{PregnancyId}";


    //--Medicination
    public static final String PATH_GET_MEDICINATION_LIST = "Medicination/{PatientId}";
    public static final String PATH_GET_MEDICINATION_DETAIL = "Medicination/{PatientId}/Detail";
    public static final String PATH_GET_DEFAULT_DATE_MEDICINATION = "Medicination/{PatientId}/DefaultDate";
    public static final String PATH_GET_PLACE_LIST_MEDICINATION = "Medicination/{PatientId}/Place";
    public static final String PATH_POST_MEDICINATION_HISTORY = "Medicination/{PatientId}";
    public static final String PATH_PUT_MEDICINATION_HISTORY = "Medicination/{PatientId}";
    public static final String PATH_DELETE_MEDICINATION_HISTORY = "Medicination/{MedicinationId}";

    //--Vaccine
    public static final String PATH_GET_GRID_VACCINATION_HISTORY = "Vaccine/{PatientId}/VaccinationHistory";
    public static final String PATH_POST_NEW_VACCINATION_HISTORY = "Vaccine/{PatientId}/VaccinationHistory";
    public static final String PATH_DELETE_VACCINATION_HISTORY= "Vaccine/{PatientVaccinationId}/VaccinationHistory";

    //--Laboratory
    public static final String PATH_GET_GRID_CHIIWII_LAB = "Laboratory/{PatientId}/PatientChiiwiiLab";
    public static final String PATH_GET_MANUAL_LAB_LIST = "Laboratory/{PatientId}/PatientManualLab";
    public static final String PATH_GET_MANUAL_LAB_DETAIL = "Laboratory/{PatientId}/PatientManualLabDetail";
    public static final String PATH_POST_MANUAL_LAB = "Laboratory/{PatientId}/PatientManualLab";
    public static final String PATH_PUT_MANUAL_LAB = "Laboratory/{PatientId}/PatientManualLab";
    public static final String PATH_DELETE_MANUAL_LAB = "Laboratory/{LabId}";

    //--HeartRate
    public static final String PATH_GET_CHART_DATA_HEART_RATE = "HeartRate/{PatientId}/ChartDataHeartRate";
    public static final String PATH_GET_TODAY_HEART_RESTING_RATE = "HeartRate/{PatientId}/TodayHeartRestingRate";
    public static final String PATH_GET_DAILY_HEART_BEAT_RATE = "HeartRate/{PatientId}/DailyHeartBeatRate";
    public static final String PATH_GET_HEART_BEAT_RATE_LIST = "HeartRate/{PatientId}/HeartBeatRateList";
    public static final String PATH_GET_HEART_BEAT_RATE = "HeartRate/{PatientId}";
    public static final String PATH_GET_HEART_BEAT_RATE_TYPE = "HeartRate/Type";
    public static final String PATH_POST_HEART_BEAT_RATE = "HeartRate/{PatientId}";
    public static final String PATH_PUT_HEART_BEAT_RATE = "HeartRate/{PatientId}";
    public static final String PATH_DELETE_HEART_BEAT_RATE = "HeartRate/{HeartRateId}";

    //--BloodPressure
    public static final String PATH_GET_CHART_DATA_BLOOD_PRESSURE = "BloodPressure/{PatientId}/ChartDataBloodPressure";
    public static final String PATH_GET_TODAY_BLOOD_PRESSURE = "BloodPressure/{PatientId}/TodayBloodPressure";
    public static final String PATH_GET_DAILY_BLOOD_PRESSURE = "BloodPressure/{PatientId}/DailyBloodPressure";
    public static final String PATH_GET_BLOOD_PRESSURE_LIST = "BloodPressure/{PatientId}/BloodPressureList";
    public static final String PATH_GET_BLOOD_PRESSURE = "BloodPressure/{PatientId}";
    public static final String PATH_POST_BLOOD_PRESSURE = "BloodPressure/{PatientId}";
    public static final String PATH_PUT_BLOOD_PRESSURE = "BloodPressure/{PatientId}";
    public static final String PATH_DELETE_BLOOD_PRESSURE = "BloodPressure/{BloodPressureId}";

    //--BloodSugar
    public static final String PATH_GET_CHART_DATA_BLOOD_SUGAR = "BloodSugar/{PatientId}/ChartDataBloodSugar";
    public static final String PATH_GET_TODAY_BLOOD_GLUCOSE = "BloodSugar/{PatientId}/TodayBloodGlucose";
    public static final String PATH_GET_DAILY_BLOOD_SUGAR = "BloodSugar/{PatientId}/DailyBloodSugar";
    public static final String PATH_GET_BLOOD_SUGAR_LIST = "BloodSugar/{PatientId}/BloodSugarList";
    public static final String PATH_GET_BLOOD_SUGAR = "BloodSugar/{PatientId}";
    public static final String PATH_POST_BLOOD_SUGAR = "BloodSugar/{PatientId}";
    public static final String PATH_PUT_BLOOD_SUGAR = "BloodSugar/{PatientId}";
    public static final String PATH_DELETE_BLOOD_SUGAR = "BloodSugar/{BloodSugarId}";

    //--Menstruation
    public static final String PATH_GET_MENSTRUAL_PERIOD_LIST = "Menstruation/{PatientId}/MenstrualPeriodList";
    public static final String PATH_POST_MENSTRUAL_PERIOD = "Menstruation/{PatientId}/MenstrualPeriod";
    public static final String PATH_DELETE_MENSTRUAL_PERIOD = "Menstruation/{MenstruationId}";

    //--ChildGrowth
    public static final String PATH_GET_GRID_CHILD_GROWTH_HISTORY = "ChildGrowth/{PatientId}/PatientChildGrowth";
    public static final String PATH_POST_NEW_VCHILD_GROWTH_HISTORY = "ChildGrowth/{PatientId}/PatientChildGrowth";
    public static final String PATH_DELETE_CHILD_GROWTH_HISTORY= "ChildGrowth/{PatientChildGrowthId}";

    //--MedicineAllergy
    public static final String PATH_GET_MEDICINE_ALLERGY_LIST = "MedicineAllergy/{PatientId}";
    public static final String PATH_GET_MEDICINE_ALLERGY_DETAIL = "MedicineAllergy/{PatientId}/Detail";
    public static final String PATH_POST_MEDICINE_ALLERGY = "MedicineAllergy/{PatientId}";
    public static final String PATH_PUT_MEDICINE_ALLERGY = "MedicineAllergy/{PatientId}";
    public static final String PATH_DELETE_MEDICINE_ALLERGY = "MedicineAllergy/{MedicineAllergyId}";

    //--FoodAllergy
    public static final String PATH_GET_FOOD_ALLERGY_LIST = "FoodAllergy/{PatientId}";
    public static final String PATH_GET_FOOD_ALLERGY_DETAIL = "FoodAllergy/{PatientId}/Detail";
    public static final String PATH_POST_FOOD_ALLERGY = "FoodAllergy/{PatientId}";
    public static final String PATH_PUT_FOOD_ALLERGY = "FoodAllergy/{PatientId}";
    public static final String PATH_DELETE_FOOD_ALLERGY = "FoodAllergy/{FoodAllergyId}";

    //--CongenitalDisease
    public static final String PATH_GET_CONGENITAL_DISEASE_LIST = "CongenitalDisease/{PatientId}";
    public static final String PATH_GET_CONGENITAL_DISEASE_DETAIL = "CongenitalDisease/{PatientId}/Detail";
    public static final String PATH_POST_CONGENITAL_DISEASE = "CongenitalDisease/{PatientId}";
    public static final String PATH_PUT_CONGENITAL_DISEASE = "CongenitalDisease/{PatientId}";
    public static final String PATH_DELETE_CONGENITAL_DISEASE = "CongenitalDisease/{CongenitalDiseaseId}";

}
