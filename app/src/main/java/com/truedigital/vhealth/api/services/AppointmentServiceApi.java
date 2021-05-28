package com.truedigital.vhealth.api.services;

import com.truedigital.vhealth.model.ApiShipping;
import com.truedigital.vhealth.model.ApiShippingStatus;
import com.truedigital.vhealth.model.ApiTokenTokboxObject;
import com.truedigital.vhealth.model.ApiUploadSlipResponse;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.model.appointment.ApiAppointmentCancel;
import com.truedigital.vhealth.model.appointment.ApiAppointmentOPDCard;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRecommendMedicine;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRecommendMedicineRequest;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRecommendProduct;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRecommendProductRequest;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.model.appointment.ApiAppointmentResponse;
import com.truedigital.vhealth.model.appointment.ApiAppointmentShortnote;
import com.truedigital.vhealth.model.appointment.ApiAppointmentTimeLeft;
import com.truedigital.vhealth.model.appointment.ApiItemAppointmentDao;
import com.truedigital.vhealth.model.appointment.ListAppointmentDao;
import com.truedigital.vhealth.model.discount.ItemDiscount;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by songkrit on 10/10/2018 AD.
 */

public interface AppointmentServiceApi {
    //..list appointment
    @GET(AppointmentServiceUrl.GET_APPOINTMENTS)
    Call<ListAppointmentDao> getListAppointments(
            @Query("IsOnlyNotEndAppointment") boolean IsOnlyNotEndAppointment,
            @Query("IsOnlyEndAppointment") boolean IsOnlyEndAppointment,
            @Query("IsOnlyCancel") boolean IsOnlyCancel,
            @Query("SortBy") String sortBy,
            @Query("SortDirection") String SortDirection
    );

    //..appointment info
    @GET(AppointmentServiceUrl.GET_APPOINTMENT_INFO)
    Call<ApiItemAppointmentDao> getAppointmentInfo(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @POST(AppointmentServiceUrl.CREATE_APPOINTMENT)
    Call<ApiAppointmentResponse> createAppointment(
            @Body ApiAppointmentRequest body
    );

    @Headers({"Accept: application/json"})
    @POST(AppointmentServiceUrl.CREATE_APPOINTMENT_PROMPTPAY)
    Call<ApiAppointmentResponse> createAppointmentPromptpay(
            @Body ApiAppointmentRequest body
    );

    @Headers({"Accept: application/json"})
    @GET
    Call<NormalResponseObject> checkAppointmentPromptpay(
            @Url String url
    );

    @Headers({"Accept: application/json"})
    @PATCH(AppointmentServiceUrl.CANCEL_APPOINTMENT)
    Call<NormalResponseObject> cancelAppointment(
            @Path("AppointmentNumber") String appointmentNumber,
            @Body ApiAppointmentCancel body
    );

    @Headers({"Accept: application/json"})
    @POST(AppointmentServiceUrl.ENDCALL_APPOINTMENT)
    Call<NormalResponseObject> endcallAppointment(
            @Path("AppointmentNumber") String appointmentNumber
    );

    //Send Doctor Note
    @GET(AppointmentServiceUrl.PATH_GET_APPOINTMENT_SHORTNOTE)
    Call<ApiAppointmentShortnote> getAppointmentShortnote(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @PATCH(AppointmentServiceUrl.PATH_PATCH_APPOINTMENT_SHORTNOTE)
    Call<NormalResponseObject> updateAppointmentShortnote(
            @Path("AppointmentNumber") String appointmentNumber,
            @Part("ShortNote") RequestBody body
    );

    //Send Doctor OPD Card
    @GET(AppointmentServiceUrl.PATH_GET_APPOINTMENT_OPDCARD)
    Call<ApiAppointmentOPDCard> getAppointmentOPDCard(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @PATCH(AppointmentServiceUrl.PATH_PATCH_APPOINTMENT_OPDCARD)
    Call<NormalResponseObject> updateAppointmentOPDCard(
            @Path("AppointmentNumber") String appointmentNumber,
            @Part("OPDCard") RequestBody body
    );

    //Doctor Suggess Products
    @GET(AppointmentServiceUrl.PATH_GET_APPOINTMENT_RECOMMEND_PRODUCT)
    Call<ApiAppointmentRecommendProduct> getAppointmentRecommendProduct(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @PATCH(AppointmentServiceUrl.PATH_PATCH_APPOINTMENT_RECOMMEND_PRODUCT)
    Call<NormalResponseObject> updateAppointmentRecommendProduct(
            @Path("AppointmentNumber") String appointmentNumber,
            @Body ApiAppointmentRecommendProductRequest body
    );

    //Doctor Suggess Medicine
    @GET(AppointmentServiceUrl.PATH_GET_APPOINTMENT_RECOMMEND_MEDICINE)
    Call<ApiAppointmentRecommendMedicine> getAppointmentRecommendMedicine(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @PATCH(AppointmentServiceUrl.PATH_PATCH_APPOINTMENT_RECOMMEND_MEDICINE)
    Call<NormalResponseObject> updateAppointmentRecommendMedicine(
            @Path("AppointmentNumber") String appointmentNumber,
            @Body ApiAppointmentRecommendMedicineRequest body
    );

    @Headers({"Accept: application/json"})
    @POST(AppointmentServiceUrl.PATH_POST_APPOINTMENT_SENDNOTE)
    Call<NormalResponseObject> sendDoctorNote(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @FormUrlEncoded
    @POST(AppointmentServiceUrl.POST_DISCOUNT)
    Call<ItemDiscount> checkDiscountCode(
            @Path("DiscountCode") String discountCode,
            @Field("DoctorId") int doctorId,
            @Field("IsPaymentCode") boolean isPaymentCode,
            @Field("Product") String listProduct
    );

    @Headers({"Accept: application/json"})
    @POST(AppointmentServiceUrl.PATH_CREATE_TOKEN_TOKBOX)
    Call<ApiTokenTokboxObject> createTokboxToken(
            @Path("AppointmentNumber") String appointmentNumber
    );

    //..appointment info
    @GET(AppointmentServiceUrl.GET_APPOINTMENT_TIMELEFT)
    Call<ApiAppointmentTimeLeft> getAppointmentTimeLeft(
            @Path("AppointmentNumber") String appointmentNumber
    );

    //..Shipping
    @GET(AppointmentServiceUrl.PATH_GET_APPOINTMENT_SHIPPING_FEE)
    Call<ApiShipping> getShippingFee(
            @Path("AppointmentNumber") String appointmentNumber,
            @Query("lat") double lat,
            @Query("lng") double lng
    );

    @GET(AppointmentServiceUrl.PATH_GET_APPOINTMENT_SHIPPING_STATUS)
    Call<ApiShippingStatus> getShippingStatus(
            @Path("AppointmentNumber") String appointmentNumber
    );

    @Headers({"Accept: application/json"})
    @Multipart
    @POST(AppointmentServiceUrl.PATH_UPLOAD_SLIP)
    Call<ApiUploadSlipResponse> postUploadSlip(
            @Part MultipartBody.Part SlipImage
    );

    @Headers({"Accept: application/json"})
    @POST(AppointmentServiceUrl.CREATE_APPOINTMENT_WITH_SLIP)
    Call<ApiAppointmentResponse> createAppointmentWithSlip(
            @Body ApiAppointmentRequest body
    );
}
