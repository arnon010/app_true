package com.truedigital.vhealth.api.services;

/**
 * Created by songkrit on 12/25/2017 AD.
 */

public class AppointmentServiceUrl {

    public static final String GET_APPOINTMENTS = "appointments";
    public static final String GET_APPOINTMENT_INFO = "appointments/{AppointmentNumber}/info";

    public static final String CREATE_APPOINTMENT = "appointments";
    public static final String CREATE_APPOINTMENT_PROMPTPAY = "appointments/promptpay";
    public static final String CANCEL_APPOINTMENT = "appointments/{AppointmentNumber}/cancel";
    public static final String ENDCALL_APPOINTMENT = "appointments/{AppointmentNumber}/endcall";
    //..timeleft
    public static final String GET_APPOINTMENT_TIMELEFT = "appointments/{AppointmentNumber}/timeleft";

    //Send Doctor ShortNote
    public static final String PATH_GET_APPOINTMENT_SHORTNOTE = "appointments/{AppointmentNumber}/shortnote";
    public static final String PATH_PATCH_APPOINTMENT_SHORTNOTE = "appointments/{AppointmentNumber}/shortnote";

    //Send Doctor OPD Card
    public static final String PATH_GET_APPOINTMENT_OPDCARD = "appointments/{AppointmentNumber}/opdcard";
    public static final String PATH_PATCH_APPOINTMENT_OPDCARD = "appointments/{AppointmentNumber}/opdcard";

    //recommendproduct
    public static final String PATH_GET_APPOINTMENT_RECOMMEND_PRODUCT = "appointments/{AppointmentNumber}/recommendproduct";
    public static final String PATH_PATCH_APPOINTMENT_RECOMMEND_PRODUCT = "appointments/{AppointmentNumber}/recommendproduct";

    //recommendmedicine
    public static final String PATH_GET_APPOINTMENT_RECOMMEND_MEDICINE = "appointments/{AppointmentNumber}/recommendmedicine";
    public static final String PATH_PATCH_APPOINTMENT_RECOMMEND_MEDICINE = "appointments/{AppointmentNumber}/recommendmedicine";

    //..Doctor send note
    public static final String PATH_POST_APPOINTMENT_SENDNOTE = "appointments/{AppointmentNumber}/sendnote";


    public static final String POST_DISCOUNT = "discountcodes/{DiscountCode}/check";

    //..tokbok
    public static final String PATH_CREATE_TOKEN_TOKBOX = "tokboxtoken/{AppointmentNumber}";


    //Shipping
    public static final String PATH_GET_APPOINTMENT_SHIPPING_FEE = "appointments/{AppointmentNumber}/ShippingFee";
    public static final String PATH_GET_APPOINTMENT_SHIPPING_STATUS = "Payment/{AppointmentNumber}/ShippingStatus";

    public static final String PATH_UPLOAD_SLIP = "attachments/Slip";
    public static final String CREATE_APPOINTMENT_WITH_SLIP = "appointments/slipVersion";

}
