package com.truedigital.vhealth.api;

import com.truedigital.vhealth.model.*;
import com.truedigital.vhealth.model.appointment.ApiAppointmentResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by songkrit on 12/25/2017 AD.
 */

public interface RetrofitService {
    // --------------------- User -----------------//

    @FormUrlEncoded
    @POST(Service.PATH_GENERATE_OTP)
    Call<ApiGenerateOTP> postGenerateOTP(
            @Field("TelephoneNumber") String TelephoneNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_GET_OTP)
    Call<ApiGenerateOTP> postGetOTP(
            @Field("TelephoneNumber") String TelephoneNumber
    );

    /* Deprecate */
    @FormUrlEncoded
    @POST(Service.PATH_POST_VERIFY_OTP)
    Call<NormalResponseObject> postVerifyOTP(
            @Field("ReferenceCode") String ReferenceCode,
            @Field("OTP") String Otp
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_CHECK_TELEPHONE)
    Call<ApiCheckTelephone> postCheckTelephone(
            @Field("TelephoneNumber") String TelephoneNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOGIN_AUTH)
    Call<ApiAccessToken> postLoginOtp(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("type") String type,
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("telephoneNumber") String telephoneNumber,
            @Field("otp") String otp,
            @Field("otpReference") String otpReference
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOGIN_AUTH)
    Call<ApiAccessToken> postLogin(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("authentication_method") String type_method,
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOGIN_AUTH)
    Call<ApiAccessToken> postLogin(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("authentication_method") String type_method,
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password,
            @Field("reference_code") String ReferenceCode
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOGIN_AUTH)
    Call<ApiAccessToken> postLoginFacebook(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("authentication_method") String type_method,
            @Field("grant_type") String grant_type,
            @Field("username") String username,
            @Field("password") String password,
            @Field("facebook_id") String facebook_id,
            @Field("facebook_token") String facebook_token
    );

    /*
    @FormUrlEncoded
    @POST(Service.PATH_LOGIN_AUTH)
    Call<ApiAccessToken> postLoginFacebook(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("type") String type,
            @Field("grant_type") String grant_type,
            @Field("Email") String email,
            @Field("username") String username,
            @Field("fb_id") String fbId,
            @Field("fb_token") String fb_token,
            @Field("fb_fname") String fbName,
            @Field("fb_lname") String fbLastname,
            @Field("fb_img") String fbImg
    );
    */

    @GET(Service.PATH_GET_APP_VERSION)
    Call<ApiCurrentVersion> CheckVersion(
            @Query("os") String os,
            @Query("clientId") String client_id
    );

    @GET(Service.PATH_GET_LIST_CREDIT_CARD)
    Call<ApiListCreditCard> getListCreditCard();

    @DELETE(Service.PATH_GET_LIST_CREDIT_CARD)
    Call<ApiListCreditCard> removeCreditCard(
            @Query("cardId") int cardId
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOGIN_AUTH)
    Call<ApiAccessToken> postRefreshToken(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOGIN_USER)
    Call<ApiUserObject> postLoginUser(
            @Field("Email") String email,
            @Field("Password") String password
    );

    @FormUrlEncoded
    @POST(Service.PATH_REGISTER_USER)
    Call<ApiUserObject> postRegisterUser(
            @Field("Email") String email,
            @Field("ConfirmEmail") String confirmEmail,
            @Field("Password") String password,
            @Field("ConfirmPassword") String confirmPassword,
            @Field("UserName") String username,
            @Field("Name") String name,
            @Field("Lastname") String lastname,
            @Field("BirthDay") String birthday,
            @Field("CongenitalDisease") String congenitalDisease, //โรค
            @Field("BeAllergic") String beAllergic, // แพ้ยา
            @Field("FoodAllergy") String foodAllergy
    ); // แพ้อาหาร

    @FormUrlEncoded
    @POST(Service.PATH_REGISTER_USER_WITH_EMAIL)
    Call<NormalResponseObject> postRegisterUser(
            @Field("UserName") String username,
            @Field("Email") String email,
            @Field("MobileToken") String deviceId
    );

    @FormUrlEncoded
    @POST(Service.PATH_REGISTER_USER_WITH_PASSWORD)
    Call<NormalResponseObject> postRegisterUserWithPassword(
            @Field("UserName") String username,
            @Field("Email") String email,
            @Field("Token") String deviceId,
            @Field("Password") String password,
            @Field("ConfirmedPassword") String confirmedPassword
    );

    @GET(Service.PATH_GET_CURRENT_USER)
    Call<ApiUserInfo> getCurrentUser();

    @Headers({"Accept: application/json"})
    @Multipart
    @PATCH(Service.PATH_PATCH_CURRENT_USER)
    Call<NormalResponseObject> patchUpdateUser(
            @Part("Username") RequestBody Username,
            @Part("OldPassword") RequestBody OldPassword,
            @Part("Password") RequestBody Password,
            @Part("ConfirmPassword") RequestBody ConfirmPassword,
            @Part("ReferenceCode") RequestBody ReferenceCode,
            @Part("OTP") RequestBody OTP,
            @Part MultipartBody.Part ProfileImage
    );

    @FormUrlEncoded
    @POST(Service.PATH_USER_INFO)
    Call<ApiUserObject> postUserInfo(
            @Field("UserId") int userId
    );

    @FormUrlEncoded
    @GET(Service.PATH_USER_INFO)
    Call<ApiUserObject> getUserInfo();

    @FormUrlEncoded
    @POST(Service.PATH_EDIT_PROFILE_USER)
    Call<NormalResponseObject> postEditProfileUser(
            @Field("UserId") int userId,
            @Field("UserName") String username,
            @Field("Name") String name,
            @Field("Lastname") String lastname,
            @Field("BirthDay") String birthday,
            @Field("CongenitalDisease") String congenitalDisease,
            @Field("BeAllergic") String beAllergic,
            @Field("FoodAllergy") String foodAllergy,
            @Field("JobId") int jobId,
            @Field("Email") String email,
            @Field("ContactTelephone") String ContactTelephone
    );

    @FormUrlEncoded
    @PATCH(Service.PATH_PATCH_UPDATE_PROFILE_USER)
    Call<NormalResponseObject> patchUpdateProfileUser(
            @Field("UserName") String username
//            @Field("Email") String email,
//            @Field("Telephone") String telephone
    );

    //@Headers({"Accept: application/json"})
    @GET(Service.GET_DOCTORS_SCHEDULES)
    Call<ApiDoctorSchedules> getDoctorSchedules(
            @Path("DoctorId") int doctorId,
            @Path("Year") int year,
            @Path("Month") int month
    );

    //@Headers({"Accept: application/json"})
    @GET(Service.GET_DOCTORS_SCHEDULES_DAY)
    Call<ListItemTimeSlotDao> getDoctorSchedules(
            @Path("DoctorId") int doctorId,
            @Path("Year") int year,
            @Path("Month") int month,
            @Path("Day") int day
    );

    //..Doctor

    @GET(Service.PATH_GET_LIST_DOCTORS)
    Call<ApiListDoctor> getListDoctorsStandby(
            @Query("IsStandby") boolean isStandby,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_LIST_DOCTORS)
    Call<ApiListDoctor> getListDoctors(
            @Query("CategoryId") int CategoryId,
            @Query("ClinicId") int ClinicId,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_LIST_DOCTORS)
    Call<ApiListDoctor> getListDoctorsWithSubcategory(
            @Query("CategoryId") int categoryId,
            @Query("SubCategoryId") int subCategoryId,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_DOCTORS)
    Call<ItemDoctorDao> getDoctor(
            @Path("DoctorId") int doctorId
    );

    @GET(Service.PATH_GET_DOCTORS_AVAILABLE_TIME)
    Call<ApiListDoctorAvailableTimes> getDoctorAvailabelTimes(
            @Path("DoctorId") int doctorId,
            @Query("Limit") int limit
    );

    //..Category
    @GET(Service.PATH_GET_LIST_CATEGORIES)
    Call<ListCategoryDao> getListCategories(
            @Query("IsOnlyShow") boolean IsOnlyShow,
            @Query("IsOnlyActive") boolean IsOnlyActive
    );

    @GET(Service.PATH_GET_LIST_SUB_CATEGORIES)
    Call<ListSubCategoryDao> getListSubCategories(
            @Query("categoryId") String categoryId,
            @Query("IsOnlyShow") boolean IsOnlyShow
    );

    @Headers({"Accept: application/json"})
    @POST(Service.PATH_POST_DOCTORS_FAVORITE)
    Call<NormalResponseObject> postDoctorFavorite(
            @Path("DoctorId") int doctorId,
            @Body DoctorFavoriteRequest body
    );

    @Headers({"Accept: application/json"})
    @POST(Service.PATH_POST_DOCTORS_RATING)
    Call<NormalResponseObject> postDoctorRating(
            @Path("DoctorId") int doctorId,
            @Body DoctorRatingRequest body
    );

    //..Clinics
    @GET(Service.PATH_GET_LIST_CLINICS)
    Call<ListClinicDao> getListClinics(
            @Query("IsOnlyShow") boolean IsOnlyShow,
            @Query("IsOnlyActive") boolean IsOnlyActive
    );

    @GET(Service.PATH_GET_JOB_LIST)
    Call<ApiJobObject> getListJob();

    @GET(Service.PATH_GET_LIST_SYMPTOM)
    Call<ApiListSymptom> getListSymptomType();

    @GET(Service.PATH_GET_LIST_CLINIC)
    Call<ApiListClinic> getListClinic();

    @GET(Service.PATH_GET_LIST_DOCTOR)
    Call<ApiListDoctor> getListDoctor(
            @Query("ClinicId") int clinicId,
            @Query("SymptomTypeId") int symptomTypeId
    );

    //.. Articles
    @GET(Service.PATH_GET_LIST_ARTICLES_CAROUSEL)
    Call<ListArticlesBannerDao> getListArticlesBanner();

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Service.PATH_CREATE_ARTICLES)
    Call<NormalResponseObject> postCreateArticles(
            @Part("Article") RequestBody body
    );//, @Part MultipartBody.Part image

    @GET(Service.PATH_GET_ARTICLES)
    Call<ApiListArticles> getArticles(
            @Query("Doctorid") int Doctorid,
            @Query("ArticleGroupId") int ArticleGroupId
    );

    @GET(Service.PATH_GET_ARTICLES_GROUP)
    Call<ApiDoctorArticleGroup> getArticlesGroups();

    @GET(Service.PATH_GET_ARTICLES_BY_ID)
    Call<ItemArticleDao> getArticlesById(
            @Path("ArticleId") int ArticleId

    );

    @GET(Service.PATH_GET_ARTICLES_SIMILAR)
    Call<ApiListArticles> getArticlesSimilar(
            @Path("ArticleId") int ArticleId
    );

    //.. Products
    @GET(Service.PATH_GET_LIST_PRODUCT_CAROUSEL)
    Call<ListProductBannerDao> getListProductBanner();

    @GET(Service.PATH_GET_LIST_PRODUCT_GROUPS)
    Call<ListProductGroupDao> getListProductGroups(
            @Query("IsOnlyActive") boolean isOnlyActive,
            @Query("IsRecommendProduct") boolean isRecommendProduct
    ); // IsRecommendProduct =true

    @GET(Service.PATH_GET_LIST_PRODUCT_GROUPS)
    Call<ListProductGroupDao> getListProductGroups(
            @Query("Keyword") String Keyword,
            @Query("IsOnlyActive") boolean isOnlyActive,
            @Query("PageIndex") int PageIndex,
            @Query("PageSize") int PageSize,
            @Query("SortBy") String SortBy,
            @Query("SortDirection") String SortDirection
    );

    @GET(Service.PATH_GET_LIST_PRODUCTS)
    Call<ListProductDao> getListProducts(
            @Query("ProductGroupId") int ProductGroupId,
            @Query("IsOnlyActive") boolean IsOnlyActive
    );

    @GET(Service.PATH_GET_PRODUCT)
    Call<ItemProductDao> getProduct(
            @Path("ProductId"
            ) int productId);

    @Headers({"Accept: application/json"})
    @POST(Service.PATH_POST_BUY_PRODUCT)
    Call<ApiAppointmentResponse> postBuyProducts(
            @Body ApiProductRequest body
    );

    @Headers({"Accept: application/json"})
    @POST(Service.PATH_POST_BUY_PRODUCT_WITH_SLIP)
    Call<ApiAppointmentResponse> buyProductsWithSlip(
            @Body ApiProductRequest body
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_BUY_PRODUCT)
    Call<ApiAppointmentResponse> postBuyProducts(
            @Field("Product") String listProduct,
            @Field("OmiseToken") String OmiseToken,
            @Field("DiscountCode") String DiscountCode,
            @Field("PaymentCode") String PaymentCode,
            @Field("ShippingLocations") String ShippingLocations,
            @Field("Name") String Name,
            @Field("IsPaymentCode") boolean IsPaymentCode,
            @Field("AppointmentNumber") String AppointmentNumber
    );

    //Favorite
    @FormUrlEncoded
    @POST(Service.PATH_ADD_FAVORITE_CLINIC)
    Call<ApiFavoriteObject> postAddFavoriteClinic(
            @Field("ClinicId") int clinicId
    );

    @FormUrlEncoded
    @POST(Service.PATH_DEL_FAVORITE_CLINIC)
    Call<ApiFavoriteObject> postDelFavoriteClinic(
            @Field("ClinicId") int clinicId
    );

    @FormUrlEncoded
    @POST(Service.PATH_ADD_FAVORITE_SYMPTOM)
    Call<ApiFavoriteObject> postAddFavoriteSymptom(
            @Field("SymptomTypeId") int symptomTypeId
    );

    @FormUrlEncoded
    @POST(Service.PATH_DEL_FAVORITE_SYMPTOM)
    Call<ApiFavoriteObject> postDelFavoriteSymptom(
            @Field("SymptomTypeId") int symptomTypeId
    );

    @FormUrlEncoded
    @POST(Service.PATH_ADD_FAVORITE_DOCTOR)
    Call<ApiFavoriteObject> postAddFavoriteDoctor(
            @Field("DoctorUserId") int doctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_DEL_FAVORITE_DOCTOR)
    Call<ApiFavoriteObject> postDelFavoriteDoctor(
            @Field("DoctorUserId") int doctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_SEARCH_DOCTOR)
    Call<ApiSearchDoctorObject> postSearchDoctor(
            @Field("ClinicId") int clinicId,
            @Field("SpecialtyTitle") String specialtyTitle,
            @Field("SectionId") int sectionId,
            @Field("SymptomId") int symptomId,
            @Field("TextSearch") String keyWord,
            @Field("Page") int pageIndex
    );

    @FormUrlEncoded
    @POST(Service.PATH_SEARCH_DOCTOR)
    Call<ApiSearchDoctorObject> postSearchDoctor(
            @Field("ClinicId") int clinicId,
            @Field("SpecialtyTitle") String specialtyTitle,
            @Field("SectionId") int sectionId,
            @Field("SymptomId") int symptomId,
            @Field("TextSearch") String keyWord,
            @Field("Page") int pageIndex,
            @Field("AvailableDoctors") String lastSearchDoctor
    );

    @FormUrlEncoded
    @POST(Service.PATH_FAVORITE)
    Call<ApiFavoriteObject> postFavorite(
            @Field("UserId") int userId,
            @Field("Page") int pageIndex
    );

    @FormUrlEncoded
    @POST(Service.PATH_FREQUENCY_DOCTOR)
    Call<ApiFavoriteObject> postFrequencyDoctor(
            @Field("UserId") int userId,
            @Field("Page") int pageIndex
    );

    @FormUrlEncoded
    @POST(Service.PATH_LAST_TIME_DOCTOR)
    Call<ApiFavoriteObject> postLastTimeDoctor(
            @Field("UserId") int userId,
            @Field("Page") int pageIndex
    );

    @FormUrlEncoded
    @POST(Service.PATH_ADD_FAVORITE)
    Call<ApiFavoriteObject> postAddFavorite(
            @Field("UserId") int userId,
            @Field("DoctorUserId") int doctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_DELETE_FAVORITE)
    Call<ApiFavoriteObject> postDeleteFavorite(
            @Field("UserId") int userId,
            @Field("DoctorUserId") int doctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_CHECK_FAVORITE)
    Call<ApiFavoriteObject> postCheckFavorite(
            @Field("UserId") int userId,
            @Field("DoctorUserId") int doctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_RATE_DOCTOR)
    Call<NormalResponseObject> postRateDoctor(
            @Field("UserId") int userId,
            @Field("DoctorUserId") int doctorId,
            @Field("Rate") int rateValue,
            @Field("AppointmentNumber") String appointmentNumber,
            @Field("UserComment") String userComment
    );

    @GET(Service.PATH_USER_TIME_PLAN)
    Call<ApiAppointmentListObject> getUserAppointmentList
            (@Query("UserId") int userId,
             @Query("LangId") int languageId,
             @Query("TypeAppointment") int typeAppointment,
             @Query("Page") int pageIndex
            );

    @FormUrlEncoded
    @POST(Service.PATH_MONTH_READY_TIME)
    Call<ApiMonthReadyTimeObject> postMonthReadyTime(
            @Field("DoctorUserId") int doctorId,
            @Field("MonthYear") String date
    );// format MM-dd-yyyy use dayOfMonth 1

    @FormUrlEncoded
    @POST(Service.PATH_DOCTOR_MONTH_READY_TIME)
    Call<ApiMonthReadyTimeObject> postDoctorMonthReadyTime(
            @Field("DoctorUserId") int doctorId,
            @Field("MonthYear") String date
    );// format MM-dd-yyyy use dayOfMonth 1

    @FormUrlEncoded
    @POST(Service.PATH_TIME_PLAN_BY_DATE)
    Call<ApiTimePlanByDateObject> postTimePlanByDate(
            @Field("UserId") int doctorId,
            @Field("DateTimeSelect") String date
    );

    @FormUrlEncoded
    @POST(Service.PATH_BOOKING_NO)
    Call<ApiBookingNoObject> postBookingNo(
            @Field("UserId") int userId,
            @Field("DoctorUserId") int doctorId,
            @Field("DateTimeBooking") String datetimeBooking,
            @Field("ContactType") int contactType,
            @Field("DetailBooking") String retailBooking,
            @Field("AllergyHistory") String allergyHistory
    );

    @FormUrlEncoded
    @POST(Service.PATH_CHECK_COUPON)
    Call<ApiCheckCouponObject> postCheckCouponWithLog(
            @Field("Code") String code,
            @Field("UserId") int userId,
            @Field("AccDoctorId") int accDoctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_EXTEND_COUPON)
    Call<ApiCheckCouponObject> postExtendCoupon(
            @Field("Code") String code,
            @Field("UserId") int userId,
            @Field("AccDoctorId") int accDoctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_CANCEL_APPOINTMENT)
    Call<NormalResponseObject> postCancelAppointment(
            @Field("UserId") int userId,
            @Field("AppointmentNumber") String appointmentNumber,
            @Field("Reason") String reason,
            @Field("Description") String description,
            @Field("UserType") int userType
    );

    @FormUrlEncoded
    @POST(Service.PATH_FREE_BOOKING)
    Call<NormalResponseObject> postFreeBooking(
            @Field("AppointmentNumber") String appointmentNumber,
            @Field("UserId") int userId,
            @Field("DoctorUserId") int doctorId,
            @Field("DateTimeBooking") String dateTimeBooking, //format MM-dd-yyyy HH:mm
            @Field("ContactType") int contactType,
            @Field("DetailBooking") String detailBooking,
            @Field("AllergyHistory") String allergyHistory,
            @Field("BeforeTime") int beforeTime,
            @Field("DiscountCode") String discountCode,
            @Field("Email") String email,
            @Field("ContactTelephone") String contactTelephone
    );

    @FormUrlEncoded
    @POST(Service.PATH_VALIDATE_BOOKING)
    Call<NormalResponseObject> postValidateBooking(
            @Field("AppointmentNumber") String appointmentNumber,
            @Field("UserId") int userId,
            @Field("DoctorUserId") int doctorId,
            @Field("DateTimeBooking") String dateTimeBooking, //format MM-dd-yyyy HH:mm
            @Field("DiscountCode") String discountCode,
            @Field("Email") String email,
            @Field("ContactTelephone") String contactTelephone
    );

    @FormUrlEncoded
    @POST(Service.PATH_CHECK_IS_CANCEL_APPOINTMENT)
    Call<NormalResponseObject> postCheckIsCancelAppointment(
            @Field("UserId") int userId,
            @Field("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_ENDCALL_ONBOOKING)
    Call<ApiCurrentAppointment> postEndCallOnBooking(
            @Field("AppointmentNumber") String appointmentNumber
    );

    @GET(Service.PATH_CHECK_CURRENT_APPOINTMENT)
    Call<ApiCurrentAppointment> postCheckCurrentAppointment(
            @Query("UserId") int userId
    );

    @FormUrlEncoded
    @POST(Service.PATH_PAYMENT_HISTORY)
    Call<ApiPaymentHistoryObject> postPaymentHistory(
            @Field("UserId") int userId,
            @Field("langId") int languageId, // 1 == Thai, 2 == Eng
            @Field("Page") int pageIndex
    );

    @FormUrlEncoded
    @POST(Service.PATH_CHECK_DATE_TIME_BOOKING)
        //format MM-dd-yyyy HH:mm
    Call<NormalResponseObject> postCheckDateTimeBooking(
            @Field("BookingDateTime") String dateTime,
            @Field("UserId") int userId
    );

    // ------------------ Doctor --------------- //

    @FormUrlEncoded
    @POST(Service.PATH_LOGIN_DOCTOR)
    Call<ApiDoctorObject> postLoginDoctor(
            @Field("Email") String email,
            @Field("Password") String password
    );

    @FormUrlEncoded
    @POST(Service.PATH_REGISTER_DOCTOR)
    Call<ApiDoctorObject> postRegisterDoctor(
            @Field("Email") String email,
            @Field("ConfirmEmail") String confirmEmail,
            @Field("Password") String password,
            @Field("ConfirmPassword") String confirmPassword,
            @Field("Name") String name,
            @Field("Lastname") String lastname,
            @Field("TitleName") String titleName,
            @Field("TitleStudy") String titleStudy,
            @Field("Tel") String tel,
            @Field("Address") String address,
            @Field("LanguageSkill") String languageSkill,
            @Field("CareerHistory") String careerHistory,
            @Field("Diploma") String diploma,
            @Field("NoProfessional") String NoProfessional,
            @Field("SpecialtyId") String specialtyId,
            @Field("SubSpecialtyId") String subSpecialtyId
    );

    @GET(Service.PATH_LIST_SUB_SPECIALTY)
    Call<ApiFilterObject> getSubSpecialty();

    @GET(Service.PATH_NEWS_LISTS)
    Call<ApiNewsObject> getNewsList();

    @FormUrlEncoded
    @POST(Service.PATH_POST_NEWS_DETAIL)
    Call<ApiNewsObject> postNewsDetail(
            @Field("ActivityId") int newsId
    );

    @FormUrlEncoded
    @POST(Service.PATH_DOCTOR_INFO)
    Call<ApiDoctorObject> postDoctorInfo(
            @Field("UserId") int userId
    );

    @FormUrlEncoded
    @POST(Service.PATH_EDIT_PROFILE_DOCTOR)
    Call<NormalResponseObject> postEditProfileDoctor(
            @Field("UserId") int userId,
            @Field("Address") String address,
            @Field("Tel") String telephoneNumber,
            @Field("TitleStudy") String studyTitle,
            @Field("Specialty") String specialtyList,
            @Field("SubSpecialty") String subSpecialtyIdList,
            @Field("CareerHistory") String careerHistory,
            @Field("Diploma") String diploma
    );

    @GET(Service.PATH_DOCTOR_TIME_PLAN)
    Call<ApiAppointmentListObject> getAppointmentList(
            @Query("DocId") int doctorId,
            @Query("LangId") int languageId,
            @Query("TypeAppointment") int typeAppointment,
            @Query("Page") int pageIndex
    );

    @GET(Service.PATH_DOCTOR_TIME_PLAN)
    Call<ApiScheduleAppointmentObject> getScheduleAppointmentList(
            @Query("DocId") int doctorId,
            @Query("LangId") int languageId,
            @Query("DateTimePlan") String dateTimePlan
    ); //example 2017/02/20

    @FormUrlEncoded
    @POST(Service.PATH_MASTER_TIME_PLAN)
    Call<ApiTimePlanObject> postMasterTimePlan(
            @Field("UserId") int userId,
            @Field("Days") int days
    ); //monday = 1, tuesday = 2, wednesday = 3 ...

    @FormUrlEncoded
    @POST(Service.PATH_MASTER_TIME_PLAN_BY_DATE)
    Call<ApiTimePlanObject> postMasterTimePlanByDate(
            @Field("UserId") int userId,
            @Field("DateTimeSelect") String Date
    ); //MM-dd-yyyy

    @FormUrlEncoded
    @POST(Service.PATH_ADD_TIME_PLAN)
    Call<NormalResponseObject> postAddMasterTimePlan(
            @Field("UserId") int userId,
            @Field("Days") int days, //monday = 1, tuesday = 2, wednesday = 3 ...
            @Field("strTime") String strTime,
            @Field("isSingleDay") boolean isSingleDay,
            @Field("Scheduleddate") String date
    );

    @FormUrlEncoded
    @POST(Service.PATH_EDIT_TIME_PLAN)
    Call<NormalResponseObject> postEditMasterTimePlan(
            @Field("UserId") int userId,
            @Field("ItemId") int itemId
    );

    @FormUrlEncoded
    @POST(Service.PATH_DELETE_TIME_PLAN_BYDATE)
    Call<NormalResponseObject> postDeleteMasterTimePlan(
            @Field("UserId") int userId,
            @Field("Days") int dayOfWeek,
            @Field("strTime") String timeList,
            @Field("isSingleDay") boolean isSingleDay,
            @Field("Scheduleddate") String date
    );

    @FormUrlEncoded
    @POST(Service.PATH_SET_CONTACT_PRICE)
    Call<NormalResponseObject> postSetContactPrice(
            @Field("UserId") int doctorId,
            @Field("Price") int price,
            @Field("IsChat") boolean isChat,
            @Field("IsVoice") boolean isVoice,
            @Field("IsVideo") boolean isVideo
    );

    @FormUrlEncoded
    @POST(Service.PATH_GET_CONTACT_PRICE)
    Call<ApiContactObject> getContactPrice(
            @Field("UserId") int doctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_SET_NOTIFICATION_TIME)
    Call<NormalResponseObject> postSetNotificationTime(
            @Field("DoctorId") int doctorId,
            @Field("NotiAppointmentTime") int time
    );

    @FormUrlEncoded
    @POST(Service.PATH_GET_NOTIFICATION_TIME)
    Call<ApiNotificationTimeObject> postGetNotificationTime(
            @Field("DoctorId") int doctorId
    );

    @FormUrlEncoded
    @POST(Service.PATH_SETTING_LANGUAGE)
    Call<NormalResponseObject> postSetLanguage(
            @Field("DoctorId") int doctorId,
            @Field("Language") int language
    );

    @FormUrlEncoded
    @POST(Service.PATH_GET_LANGUAGE)
    Call<ApiLanguageObject> postGetLanguage(
            @Field("DoctorId") int doctorId
    );

    @Multipart
    @POST(Service.PATH_UPLOAD_IMAGE)
    Call<NormalResponseObject> postUpdateImage(
            @Part("DoctorId") RequestBody doctorId,
            @Part("ImageType") RequestBody imageType,
            @Part MultipartBody.Part image
    );

    // ------------------------------ ALL ----------------------------------//  

    @FormUrlEncoded
    @POST(Service.PATH_FORGOT_PASSWORD)
    Call<NormalResponseObject> postForgotPassword(
            @Field("Email") String email,
            @Field("MobileToken") String deviceId
    );

    @FormUrlEncoded
    @POST(Service.PATH_CHANGE_PASSWORD)
    Call<NormalResponseObject> postChangePassword(
            @Field("UserId") int userId,
            @Field("Password") String oldPassword,
            @Field("NewPassword") String newPassword,
            @Field("ConfirmPassword") String confirmPassword
    );

    @FormUrlEncoded
    @POST(Service.PATH_CHANGE_PASSWORD_FROM_FORGET)
    Call<NormalResponseObject> postChangePasswordForget(
            @Field("UserId") int userId,
            @Field("Token") String devideId,
            @Field("NewPassword") String newPassword,
            @Field("ConfirmPassword") String confirmPassword
    );


    @Multipart
    @POST(Service.PATH_UPDATE_IMAGE_PROFILE)
    Call<NormalResponseObject> postUpdateImageProfile(
            @Part("UserId") RequestBody userId,
            @Part MultipartBody.Part imgProfile
    );

    @GET(Service.PATH_GET_LIST_SPECIALTY)
    Call<ApiFilterObject> getSpecialty();

    @GET(Service.PATH_GET_LIST_ALL_SPECIALTY)
    Call<ApiFilterObject> getAllSpecialty(); // specialty and sub specialty

    @GET(Service.PATH_GET_LIST_SECTION)
    Call<ApiFilterObject> getSection();

    @GET(Service.PATH_GET_LIST_CLINIC)
    Call<ApiFilterObject> getClinic();

    @GET(Service.PATH_GET_LIST_SYMPTOM)
    Call<ApiFilterObject> getSymptom();

    @FormUrlEncoded
    @POST(Service.PATH_ADD_TOKEN)
    Call<NormalResponseObject> postAddToken(
            @Field("udid") String udid,
            @Field("userid") int userId,
            @Field("tokenid") String tokenId,
            @Field("ostype") int osType, // 0 = android, 1 = ios
            @Field("isenable") int isEnable
    ); // 0 = close, 1 = open

    @Headers({"Accept: application/json"})
    @POST(Service.PATH_ADD_ONESIGNAL_TOKEN)
    Call<NormalResponseObject> postAddOneSignalToken(
            @Body ApiOneSignalRequest params
    );

    @FormUrlEncoded
    @POST(Service.PATH_SEARCH_BOOKING)
    Call<ApiSearchBookingObject> postSearchBooking(
            @Field("AppointmentNumber") String appointmentNumber
    );

    @POST(Service.PATH_SEND_NOTIFICATION)
    @Headers("content-type: application/json")
    Call<OneSignalResponse> postSendNotification(
            @Body RequestBody params
    );

    @GET(Service.PATH_GET_TIME_SERVER)
    Call<ApiTimeServerObject> getTimeServer();

    @FormUrlEncoded
    @POST(Service.PATH_CREATE_TOKEN_TOKBOX)
    Call<ApiTokenTokboxObject> postCreateTokenTokbox(
            @Field("AppointmentNumber") String appointmentNumber,
            @Field("UserId") int userId
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOG_OUT)
    Call<NormalResponseObject> postLogout(
            @Field("UserId") int userId
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOG_START_CALL)
    Call<NormalResponseObject> postStartCall(
            @Field("UserId") int userId,
            @Field("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOG_END_CALL)
    Call<NormalResponseObject> postEndCall(
            @Field("UserId") int userId,
            @Field("AppointmentNumber") String appointmentNumber,
            @Field("DisconnectCode") String disconnectCode
    ); // 01 = Disconnect, 02 = ConnectionLost

    @FormUrlEncoded
    @POST(Service.PATH_LOG_CONCURRENT_CONNECTIONS)
    Call<NormalResponseObject> postConcurrentConnections(
            @Field("UserId") int userId,
            @Field("DoctorId") int doctorId,
            @Field("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOG_APPOINTMENT_STATUS)
    Call<ApiAppointmentStatus> postAppointmentStatus(
            @Field("UserId") int userId,
            @Field("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_LOG_CHECK_START_CALL)
    Call<NormalResponseObject> postCheckStartCall(
            @Field("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_SEND_SHORTNOTE)
    Call<NormalResponseObject> postSendShortNote(
            @Field("AppointmentNumber") String appointmentNumber,
            @Field("Note") String Note
    );

    @FormUrlEncoded
    @POST(Service.PATH_LIST_NOTIFICATION_MESSAGE)
    Call<ApiNotificationMessageObject> postNotificationMessage(
            @Field("UserId") int userId,
            @Field("langId") int languageId,
            @Field("Page") int pageIndex
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Service.PATH_POST_CHAT_MESSAGE)
    Call<ApiChatObject> postChatMessage(
            @Path("AppointmentNumber") String appointmentNumber,
            @Field("Message") String message
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Service.PATH_POST_CHAT_MESSAGE)
    Call<ApiChatObject> postChatImage(
            @Path("AppointmentNumber") String appointmentNumber,
            @Part("Message") RequestBody message,
            @Part MultipartBody.Part image
    );

    @GET(Service.PATH_GET_CHAT_MESSAGE_LIST)
    Call<ApiChatListObject> getChatMessageList(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @GET(Service.PATH_GET_CHAT_MESSAGE)
    Call<ApiChatObject> getChatMessage(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @POST(Service.PATH_POST_LOG_START)
    Call<NormalResponseObject> postLogStart(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @POST(Service.PATH_POST_LOG_END)
    Call<NormalResponseObject> postLogEnd(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @POST(Service.PATH_POST_LOG_CONCURRENT)
    Call<NormalResponseObject> postLogConcurrent(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Multipart
    @POST(Service.PATH_CHAT_MESSAGE)
    Call<ApiChatObject> postChatAttachFile
            (@Part("UserId") RequestBody userId,
             @Part("AppointmentNumber") RequestBody appointmentNumber,
             @Part MultipartBody.Part image
            );

    @FormUrlEncoded
    @POST(Service.PATH_GET_CHAT_LIST)
    Call<ApiChatListObject> postGetChatList(
            @Field("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_TIME_LEFT)
    Call<ApiTimeLeftObject> postGetTimeLeft(
            @Field("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_CHECK_TOKEN)
    Call<NormalResponseObject> postCheckToken(
            @Field("UserId") int userId,
            @Field("UdId") String udid,
            @Field("TokenId") String tokenId
    );

    @GET(Service.PATH_GET_SYSTEM_CONFIGURATION_API)
    Call<SystemConfigurationObject> getSystemConfigurationApi(
            @Query("os") String os
    );

    // ------------------------------ EHR ----------------------------------//  
    //--login EHR
    @FormUrlEncoded
    @POST(Service.PATH_POST_LOGIN_EHR)
    Call<ApiPatientObject> postLoginEHR(
            @Field("PIN") String pin
    );

    //--create PIN for EHR
    @FormUrlEncoded
    @PATCH(Service.PATH_POST_CREATE_PIN_EHR)
    Call<NormalResponseObject> postCreatePin(
            @Field("PIN") String pin, @Field("ConfirmPIN") String confirmPin
    );

    //--Get pin status
    @GET(Service.PATH_GET_PIN_STATUS)
    Call<ApiCheckPinObject> getPINStatus();

    //--Patients
    @GET(Service.PATH_GET_PATIENT_BY_ID)
    Call<ApiPatientObject> getPatient(
            @Path("Id") int patientId
    );

    //--EHRMenus
    @GET(Service.PATH_GET_MENU_LIST)
    Call<ApiListEhrMenuObject> getMenuList(
            @Path("PatientId") int patientId,
            @Query("IsChild") boolean isChild,
            @Query("ParentMenuCode") String parentMenuCode
    );

    @GET(Service.PATH_GET_CURRENT_MENU)
    Call<ApiCurrentEhrMenuObject> getCurrentMenu(
            @Path("PatientId") int patientId,
            @Query("IsChild") boolean isChild
    );

    @GET(Service.PATH_GET_CURRENT_SUB_MENU)
    Call<ApiCurrentEhrMenuObject> getCurrentSubMenu(
            @Path("PatientId") int patientId,
            @Query("IsChild") boolean isChild,
            @Query("ParentMenuCode") String parentMenuCode
    );

    @GET(Service.PATH_GET_PATIENT_MENU)
    Call<ApiEhrMenuObject> getPatientMenu(
            @Path("PatientMenuId") int patientMenuId
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_CHECK_PATIENT_EHR_MENU)
    Call<NormalResponseObject> postCheckPatientEHRMenu(
            @Path("PatientId") int patientId,
            @Field("MenuCode") String menuCode,
            @Field("MenuName") String menuName
    );

    //--Family
    @FormUrlEncoded
    @POST(Service.PATH_POST_CREATE_PATIENT_RELATIONSHIP)
    Call<ApiPatientRelationshipObject> postPatientRelationship(
            @Path("PatientId") int patientId,
            @Field("MenuCode") String menuCode,
            @Field("RelationshipName") String relationshipName
    );

    @GET(Service.PATH_GET_PATIENT_RELATIONSHIP)
    Call<ApiListPatientRelationshipObject> getPatientRelationshipList(
            @Path("PatientId") int patientId
    );


    //--DoctorNote
    @GET(Service.PATH_GET_DEFAULT_DATE_DOCTOR_NOTE)
    Call<ApiDoctorNoteCriteriaObject> getDoctorNoteDefaultDateCriteria(
            @Path("PatientId") int patientId
    );

    @GET(Service.PATH_GET_DOCTOR_LIST_DOCTOR_NOTE)
    Call<ApiListDoctorNoteCriteriaObject> getDoctorNoteDoctorListCriteria(
            @Path("PatientId") int patientId
    );

    @GET(Service.PATH_GET_DOCTOR_NOTE_LIST)
    Call<ApiListDoctorNoteObject> getDoctorNoteList(
            @Path("PatientId") int patientId,
            @Query("DoctorId") int doctorId,
            @Query("StartDate") String startDate,
            @Query("EndDate") String endDate,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_DOCTOR_NOTE_DETAIL)
    Call<ApiDoctorNoteObject> getDoctorNoteDetail(
            @Path("PatientId") int patientId,
            @Query("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_CHECK_RECOMMEND_PRODUCT_COUPONS)
    Call<ApiRecommendProductCouponsObject> postCheckRecommendProductCoupons(
            @Path("DiscountCode") String discountCode,
            @Field("AppointmentId") int appointmentId,
            @Field("ProductIdList") ArrayList<Integer> productIdList,
            @Field("TypeList") ArrayList<Integer> typeList
    );

    //--BankAccount
    @GET(Service.PATH_GET_BANK_ACCOUNT_LIST)
    Call<ApiListBankAccountObject> getBankAccountList();

    //--Pregnant
    @GET(Service.PATH_GET_DEFAULT_DATE_PREGNANT)
    Call<ApiPregnantHistoryCriteriaObject> getPregnantDefaultDateCriteria(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_PLACE_LIST_PREGNANT)
    Call<ApiListPlacePregnantHistoryCriteriaObject> getPregnantPlaceListCriteria(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_PREGNANT_LIST)
    Call<ApiListPregnantHistoryObject> getPregnantList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("Place") String place,
            @Query("StartDate") String startDate,
            @Query("EndDate") String endDate,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_PREGNANT_DETAIL)
    Call<ApiPregnantHistoryObject> getPregnantHistoryDetail(
            @Path("PatientId") int patientId,
            @Query("PregnancyId") int pregnancyId
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Service.PATH_POST_PREGNANT_HISTORY)
    Call<ApiPregnantHistoryObject> postPregnantHistory(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @PUT(Service.PATH_PUT_PREGNANT_HISTORY)
    Call<NormalResponseObject> putPregnantHistory(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @DELETE(Service.PATH_DELETE_PREGNANT_HISTORY)
    Call<NormalResponseObject> deletePregnantHistory(
            @Path("PregnancyId") int pregnancyId
    );


    //--Medicination
    @GET(Service.PATH_GET_DEFAULT_DATE_MEDICINATION)
    Call<ApiMedicationHistoryCriteriaObject> getMedicinationDefaultDateCriteria(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_PLACE_LIST_MEDICINATION)
    Call<ApiListFromMedicationHistoryCriteriaObject> getMedicinationPlaceListCriteria(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_MEDICINATION_LIST)
    Call<ApiListMedicationHistoryObject> getMedicinationList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("Place") String place,
            @Query("StartDate") String startDate,
            @Query("EndDate") String endDate,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_MEDICINATION_DETAIL)
    Call<ApiMedicationHistoryObject> getMedicinationHistoryDetail(
            @Path("PatientId") int patientId,
            @Query("MedicinationId") int medicinationId
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Service.PATH_POST_MEDICINATION_HISTORY)
    Call<ApiMedicationHistoryObject> postMedicinationHistory(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @PUT(Service.PATH_PUT_MEDICINATION_HISTORY)
    Call<NormalResponseObject> putMedicinationHistory(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @DELETE(Service.PATH_DELETE_MEDICINATION_HISTORY)
    Call<NormalResponseObject> deleteMedicinationHistory(
            @Path("MedicinationId") int medicinationId
    );


    //--Vaccine
    @GET(Service.PATH_GET_GRID_VACCINATION_HISTORY)
    Call<ApiGridVaccinationHistoryObject> getGridVaccinationHistory(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("IsNecessary") boolean isNecessary
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_NEW_VACCINATION_HISTORY)
    Call<ApiVaccinationHistoryObject> postNewVaccinationHistory(
            @Path("PatientId") int patientId,
            @Field("PatientMenuId") int patientMenuId,
            @Field("MenuCode") String menuCode,
            @Field("VaccineId") int vaccineId,
            @Field("Month") int month
    );

    @DELETE(Service.PATH_DELETE_VACCINATION_HISTORY)
    Call<NormalResponseObject> deleteVaccinationHistory(
            @Path("PatientVaccinationId") int patientVaccinationId
    );

    //--Laboratory
    @GET(Service.PATH_GET_GRID_CHIIWII_LAB)
    Call<ApiGridLaboratoryChiiwiiObject> getGridChiiwiiLab(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_MANUAL_LAB_LIST)
    Call<ApiListLaboratoryOtherObject> getPatientManualLabList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_MANUAL_LAB_DETAIL)
    Call<ApiLaboratoryOtherObject> getPatientManualLabDetail(
            @Path("PatientId") int patientId,
            @Query("LabId") int labId
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Service.PATH_POST_MANUAL_LAB)
    Call<ApiLaboratoryOtherObject> postManualLab(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @PUT(Service.PATH_PUT_MANUAL_LAB)
    Call<NormalResponseObject> putManualLab(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @DELETE(Service.PATH_DELETE_MANUAL_LAB)
    Call<NormalResponseObject> deletePatientManualLab(
            @Path("LabId") int labId
    );

    //--HeartRate
    @GET(Service.PATH_GET_CHART_DATA_HEART_RATE)
    Call<ApiChartObject> getChartDataHeartRate(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("PeriodType") int periodType,
            @Query("Period") int period
    );

    @GET(Service.PATH_GET_TODAY_HEART_RESTING_RATE)
    Call<ApiTodayHeartRestingRateObject> getTodayHeartRestingRate(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_DAILY_HEART_BEAT_RATE)
    Call<ApiListDailyHeartBeatRateObject> getDailyHeartBeatRate(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_HEART_BEAT_RATE_LIST)
    Call<ApiListHeartBeatRateObject> getHeartBeatRateList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("DailyDate") String dailyDate
    );

    @GET(Service.PATH_GET_HEART_BEAT_RATE)
    Call<ApiHeartBeatRateObject> getHeartBeatRate(
            @Path("PatientId") int patientId,
            @Query("HeartRateId") int heartRateId
    );

    @GET(Service.PATH_GET_HEART_BEAT_RATE_TYPE)
    Call<ApiListHeartBeatRateTypeObject> getHeartBeatRateType();

    @FormUrlEncoded
    @POST(Service.PATH_POST_HEART_BEAT_RATE)
    Call<ApiHeartBeatRateObject> postHeartBeatRate(
            @Path("PatientId") int patientId,
            @Field("PatientMenuId") int patientMenuId,
            @Field("MenuCode") String menuCode,
            @Field("Type") String type,
            @Field("RecordDate") String recordDate,
            @Field("BPM") int bpm
    );

    @FormUrlEncoded
    @PUT(Service.PATH_PUT_HEART_BEAT_RATE)
    Call<NormalResponseObject> putHeartBeatRate(
            @Path("PatientId") int patientId,
            @Field("HeartRateId") int heartRateId,
            @Field("Type") String type,
            @Field("RecordDate") String recordDate,
            @Field("BPM") int bpm
    );

    @DELETE(Service.PATH_DELETE_HEART_BEAT_RATE)
    Call<NormalResponseObject> deleteHeartBeatRate(
            @Path("HeartRateId") int heartRateId
    );

    //--BloodPressure
    @GET(Service.PATH_GET_CHART_DATA_BLOOD_PRESSURE)
    Call<ApiChartObject> getChartDataBloodPressure(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("PeriodType") int periodType,
            @Query("Period") int period
    );

    @GET(Service.PATH_GET_TODAY_BLOOD_PRESSURE)
    Call<ApiTodayBloodPressureObject> getTodayBloodPressure(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_DAILY_BLOOD_PRESSURE)
    Call<ApiListDailyBloodPressureObject> getDailyBloodPressure(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_BLOOD_PRESSURE_LIST)
    Call<ApiListBloodPressureObject> getBloodPressureList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("DailyDate") String dailyDate
    );

    @GET(Service.PATH_GET_BLOOD_PRESSURE)
    Call<ApiBloodPressureObject> getBloodPressure(
            @Path("PatientId") int patientId,
            @Query("BloodPressureId") int bloodPressureId
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_BLOOD_PRESSURE)
    Call<ApiBloodPressureObject> postBloodPressure(
            @Path("PatientId") int patientId,
            @Field("PatientMenuId") int patientMenuId,
            @Field("MenuCode") String menuCode,
            @Field("RecordDate") String recordDate,
            @Field("SYSValue") int sysValue,
            @Field("DIAValue") int diaValue
    );

    @FormUrlEncoded
    @PUT(Service.PATH_PUT_BLOOD_PRESSURE)
    Call<NormalResponseObject> putBloodPressure(
            @Path("PatientId") int patientId,
            @Field("BloodPressureId") int bloodPressureId,
            @Field("RecordDate") String recordDate,
            @Field("SYSValue") int sysValue,
            @Field("DIAValue") int diaValue
    );

    @DELETE(Service.PATH_DELETE_BLOOD_PRESSURE)
    Call<NormalResponseObject> deleteBloodPressure(
            @Path("BloodPressureId") int bloodPressureId
    );

    //--BloodSugar
    @GET(Service.PATH_GET_CHART_DATA_BLOOD_SUGAR)
    Call<ApiChartObject> getChartDataBloodSugar(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("PeriodType") int periodType,
            @Query("Period") int period
    );

    @GET(Service.PATH_GET_TODAY_BLOOD_GLUCOSE)
    Call<ApiTodayBloodGlucoseObject> getTodayBloodGlucose(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_DAILY_BLOOD_SUGAR)
    Call<ApiListDailyBloodSugarObject> getDailyBloodSugar(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @GET(Service.PATH_GET_BLOOD_SUGAR_LIST)
    Call<ApiListBloodSugarObject> getBloodSugarList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("DailyDate") String dailyDate
    );

    @GET(Service.PATH_GET_BLOOD_SUGAR)
    Call<ApiBloodSugarObject> getBloodSugar(
            @Path("PatientId") int patientId,
            @Query("BloodSugarId") int bloodSugarId
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_BLOOD_SUGAR)
    Call<ApiBloodSugarObject> postBloodSugar(
            @Path("PatientId") int patientId,
            @Field("PatientMenuId") int patientMenuId,
            @Field("MenuCode") String menuCode,
            @Field("RecordDate") String recordDate,
            @Field("SugarValue") int sugarValue
    );

    @FormUrlEncoded
    @PUT(Service.PATH_PUT_BLOOD_SUGAR)
    Call<NormalResponseObject> putBloodSugar(
            @Path("PatientId") int patientId,
            @Field("BloodSugarId") int bloodSugarId,
            @Field("RecordDate") String recordDate,
            @Field("SugarValue") int sugarValue
    );

    @DELETE(Service.PATH_DELETE_BLOOD_SUGAR)
    Call<NormalResponseObject> deleteBloodSugar(
            @Path("BloodSugarId") int bloodSugarId
    );

    //--Menstruation
    @GET(Service.PATH_GET_MENSTRUAL_PERIOD_LIST)
    Call<ApiListMenstruationObject> getMenstrualPeriodList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_MENSTRUAL_PERIOD)
    Call<ApiMenstruationObject> postMenstrualPeriod(
            @Path("PatientId") int patientId,
            @Field("PatientMenuId") int patientMenuId,
            @Field("MenuCode") String menuCode,
            @Field("MenstruationDate") String menstruationDate
    );

    @DELETE(Service.PATH_DELETE_MENSTRUAL_PERIOD)
    Call<NormalResponseObject> deleteMenstrualPeriod(
            @Path("MenstruationId") int menstruationId
    );

    //--ChildGrowth
    @GET(Service.PATH_GET_GRID_CHILD_GROWTH_HISTORY)
    Call<ApiGridChildGrowthHistoryObject> getGridChildGrowthHistory(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId
    );

    @FormUrlEncoded
    @POST(Service.PATH_POST_NEW_VCHILD_GROWTH_HISTORY)
    Call<ApiChildGrowthHistoryObject> postNewChildGrowthHistory(
            @Path("PatientId") int patientId,
            @Field("PatientMenuId") int patientMenuId,
            @Field("MenuCode") String menuCode,
            @Field("ChildGrowthId") int childGrowthId
    );

    @DELETE(Service.PATH_DELETE_CHILD_GROWTH_HISTORY)
    Call<NormalResponseObject> deleteChildGrowthHistory(
            @Path("PatientChildGrowthId") int patientChildGrowthId
    );

    //--MedicineAllergy
    @GET(Service.PATH_GET_MEDICINE_ALLERGY_LIST)
    Call<ApiListMedicineAllergyObject> getMedicineAllergyList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_MEDICINE_ALLERGY_DETAIL)
    Call<ApiMedicineAllergyObject> getMedicineAllergyDetail(
            @Path("PatientId") int patientId,
            @Query("MedicineAllergyId") int medicineAllergyId
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Service.PATH_POST_MEDICINE_ALLERGY)
    Call<ApiMedicineAllergyObject> postMedicineAllergy(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @PUT(Service.PATH_PUT_MEDICINE_ALLERGY)
    Call<NormalResponseObject> putMedicineAllergy(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @DELETE(Service.PATH_DELETE_MEDICINE_ALLERGY)
    Call<NormalResponseObject> deleteMedicineAllergy(
            @Path("MedicineAllergyId") int medicineAllergyId
    );

    //--FoodAllergy
    @GET(Service.PATH_GET_FOOD_ALLERGY_LIST)
    Call<ApiListFoodAllergyObject> getFoodAllergyList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_FOOD_ALLERGY_DETAIL)
    Call<ApiFoodAllergyObject> getFoodAllergyDetail(
            @Path("PatientId") int patientId,
            @Query("FoodAllergyId") int foodAllergyId
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Service.PATH_POST_FOOD_ALLERGY)
    Call<ApiFoodAllergyObject> postFoodAllergy(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @PUT(Service.PATH_PUT_FOOD_ALLERGY)
    Call<NormalResponseObject> putFoodAllergy(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @DELETE(Service.PATH_DELETE_FOOD_ALLERGY)
    Call<NormalResponseObject> deleteFoodAllergy(
            @Path("FoodAllergyId") int foodAllergyId
    );

    //--CongenitalDisease
    @GET(Service.PATH_GET_CONGENITAL_DISEASE_LIST)
    Call<ApiListCongenitalDiseaseObject> getCongenitalDiseaseList(
            @Path("PatientId") int patientId,
            @Query("PatientMenuId") int patientMenuId,
            @Query("PageIndex") int pageIndex,
            @Query("PageSize") int pageSize
    );

    @GET(Service.PATH_GET_CONGENITAL_DISEASE_DETAIL)
    Call<ApiCongenitalDiseaseObject> getCongenitalDiseaseDetail(
            @Path("PatientId") int patientId,
            @Query("CongenitalDiseaseId") int congenitalDiseaseId
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Service.PATH_POST_CONGENITAL_DISEASE)
    Call<ApiCongenitalDiseaseObject> postCongenitalDisease(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @PUT(Service.PATH_PUT_CONGENITAL_DISEASE)
    Call<NormalResponseObject> putCongenitalDisease(
            @Path("PatientId") int patientId,
            @Part("Data") RequestBody data,
            @Part ArrayList<MultipartBody.Part> fileList
    );

    @DELETE(Service.PATH_DELETE_CONGENITAL_DISEASE)
    Call<NormalResponseObject> deleteCongenitalDisease(
            @Path("CongenitalDiseaseId") int congenitalDiseaseId
    );
}
