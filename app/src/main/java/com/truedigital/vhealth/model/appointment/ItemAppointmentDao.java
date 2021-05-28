package com.truedigital.vhealth.model.appointment;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.NormalResponseObject;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.ConvertDate;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemAppointmentDao extends NormalResponseObject {

    @SerializedName("AppointmentId") private int appointmentId;
    @SerializedName("AppointmentNumber") private String appointmentNumber;
    @SerializedName("AppointmentTime") private String appointmentTime;
    @SerializedName("PatientId") private int patientId;
    @SerializedName("PatientUsername") private String patientUsername;
    @SerializedName("DoctorId") private int doctorId;
    @SerializedName("DoctorName") private String doctorName;
    @SerializedName("ContactTypeName") private String contactTypeName;
    @SerializedName("FinalPrice") private double finalPrice;
    @SerializedName("DiscountPrice") private double discountPrice;
    @SerializedName("Status") private String status;
    @SerializedName("ContactTelephone") private String contactTelephone;
    @SerializedName("ContactEmail") private String contactEmail;
    @SerializedName("Detail") private String detail;
    @SerializedName("TypeCode") private String typeCode;

    @SerializedName("IsCanceled") private boolean canceled;
    @SerializedName("CancelBy") private String cancelBy;
    @SerializedName("CancelUserId") private int cancelUserId;

    @SerializedName("IsSold") private boolean sold;


    @SerializedName("CancelReason") private String cancelReason;
    @SerializedName("UpdatedBy") private String updatedBy;
    @SerializedName("EmailDoctor") private String emailDoctor;
    @SerializedName("PhoneDoctor") private String phoneDoctor;
    @SerializedName("EmailPatient") private String emailPatient;
    @SerializedName("PhonePatient") private String phonePatient;

    @SerializedName("IsBookingFinish") private boolean bookingFinish;
    @SerializedName("IsSentShortNote") private boolean sentShortNote;
    @SerializedName("HasDoctorNote") private boolean hasDoctorNote;
    @SerializedName("HasRecommendProduct") private boolean hasRecommendProduct;
    @SerializedName("HasRecommendMedicine") private boolean hasRecommendMedicine;

    @SerializedName("ContactMinute") private int contactMinute;
    @SerializedName("SlotMinute") private int slotMinute;
    @SerializedName("CanCancel") private boolean canCancel;

    @SerializedName("PatientProfileImage") private String patientProfileImage;
    @SerializedName("DoctorProfileImage") private String doctorProfileImage;

    @SerializedName("BadgeClass") private String badgeClass;
    @SerializedName("CouponCode") private String couponCode;
    @SerializedName("CouponPrice") private double couponPrice;

    @SerializedName("CouponIgnorePrice") private boolean couponIgnorePrice;
    @SerializedName("CouponPaidCode") private boolean couponPaidCode;
    @SerializedName("CanStart") private boolean canStart;

    @SerializedName("ContactPeriod") private String contactPeriod;
    @SerializedName("SlotPeriod") private String slotPeriod;

    @SerializedName("TotalSubTimeSlotContact") private String totalSubTimeSlotContact;
    @SerializedName("TotalSubTimeSlotContactToMinute") private int totalSubTimeSlotContactToMinute;
    @SerializedName("TotalTimeContactToMinute") private int totalTimeContactToMinute;

    @SerializedName("ClinicFullName") private String clinicFullName;
    @SerializedName("CategoryFullName") private String categoryFullName;
    @SerializedName("SubCategoryFullName") private String subCategoryFullName;
    @SerializedName("DoctorPrice") private String doctorPrice;
    @SerializedName("UpdatedDateShow") private String updatedDateShow;
    @SerializedName("DateEvent") private String dateEvent;
    @SerializedName("EventLogId") private int eventLogId;
    @SerializedName("TotalRealTalkTime") private String totalRealTalkTime;
    @SerializedName("FullTextSlotContact") private String fullTextSlotContact;
    @SerializedName("TextNumOfTimes") private String textNumOfTimes;
    @SerializedName("TextAmount") private String textAmount;
    @SerializedName("TextPay") private String textPay;
    @SerializedName("TextFree") private String textFree;
    @SerializedName("LanguageSkills") private List<String> languageSkills;

    public ItemAppointmentDao() {
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getContactTypeName() {
        if (contactTypeName == null || contactTypeName == "") {
            contactTypeName = AppConstants.CONTACT_CHAT;
        }
        return contactTypeName;
    }

    public void setContactTypeName(String contactTypeName) {
        this.contactTypeName = contactTypeName;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(String cancelBy) {
        this.cancelBy = cancelBy;
    }

    public int getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(int cancelUserId) {
        this.cancelUserId = cancelUserId;
    }


    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getEmailDoctor() {
        return emailDoctor;
    }

    public void setEmailDoctor(String emailDoctor) {
        this.emailDoctor = emailDoctor;
    }

    public String getPhoneDoctor() {
        return phoneDoctor;
    }

    public void setPhoneDoctor(String phoneDoctor) {
        this.phoneDoctor = phoneDoctor;
    }

    public String getEmailPatient() {
        return emailPatient;
    }

    public void setEmailPatient(String emailPatient) {
        this.emailPatient = emailPatient;
    }

    public String getPhonePatient() {
        return phonePatient;
    }

    public void setPhonePatient(String phonePatient) {
        this.phonePatient = phonePatient;
    }

    public boolean isBookingFinish() {
        return bookingFinish;
    }

    public void setBookingFinish(boolean bookingFinish) {
        this.bookingFinish = bookingFinish;
    }

    public boolean isSentShortNote() {
        return sentShortNote;
    }

    public void setSentShortNote(boolean sentShortNote) {
        this.sentShortNote = sentShortNote;
    }

    public boolean isHasDoctorNote() {
        return hasDoctorNote;
    }

    public void setHasDoctorNote(boolean hasDoctorNote) {
        this.hasDoctorNote = hasDoctorNote;
    }

    public boolean isHasRecommendProduct() {
        return hasRecommendProduct;
    }

    public void setHasRecommendProduct(boolean hasRecommendProduct) {
        this.hasRecommendProduct = hasRecommendProduct;
    }

    public boolean isHasRecommendMedicine() {
        return hasRecommendMedicine;
    }

    public void setHasRecommendMedicine(boolean hasRecommendMedicine) {
        this.hasRecommendMedicine = hasRecommendMedicine;
    }

    public int getContactMinute() {
        return contactMinute;
    }

    public void setContactMinute(int contactMinute) {
        this.contactMinute = contactMinute;
    }

    public int getSlotMinute() {
        return slotMinute;
    }

    public void setSlotMinute(int slotMinute) {
        this.slotMinute = slotMinute;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public String getPatientProfileImage() {
        return patientProfileImage;
    }

    public void setPatientProfileImage(String patientProfileImage) {
        this.patientProfileImage = patientProfileImage;
    }

    public String getDoctorProfileImage() {
        return doctorProfileImage;
    }

    public void setDoctorProfileImage(String doctorProfileImage) {
        this.doctorProfileImage = doctorProfileImage;
    }

    public String getBadgeClass() {
        return badgeClass;
    }

    public void setBadgeClass(String badgeClass) {
        this.badgeClass = badgeClass;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public boolean isCouponIgnorePrice() {
        return couponIgnorePrice;
    }

    public void setCouponIgnorePrice(boolean couponIgnorePrice) {
        this.couponIgnorePrice = couponIgnorePrice;
    }

    public boolean isCouponPaidCode() {
        return couponPaidCode;
    }

    public void setCouponPaidCode(boolean couponPaidCode) {
        this.couponPaidCode = couponPaidCode;
    }

    public boolean isCanStart() {
        return canStart;
    }

    public void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }

    public String getContactPeriod() {
        return contactPeriod;
    }

    public void setContactPeriod(String contactPeriod) {
        this.contactPeriod = contactPeriod;
    }

    public String getSlotPeriod() {
        return slotPeriod;
    }

    public void setSlotPeriod(String slotPeriod) {
        this.slotPeriod = slotPeriod;
    }

    public String getTotalSubTimeSlotContact() {
        return totalSubTimeSlotContact;
    }

    public void setTotalSubTimeSlotContact(String totalSubTimeSlotContact) {
        this.totalSubTimeSlotContact = totalSubTimeSlotContact;
    }

    public int getTotalSubTimeSlotContactToMinute() {
        return totalSubTimeSlotContactToMinute;
    }

    public void setTotalSubTimeSlotContactToMinute(int totalSubTimeSlotContactToMinute) {
        this.totalSubTimeSlotContactToMinute = totalSubTimeSlotContactToMinute;
    }

    public int getTotalTimeContactToMinute() {
        return totalTimeContactToMinute;
    }

    public void setTotalTimeContactToMinute(int totalTimeContactToMinute) {
        this.totalTimeContactToMinute = totalTimeContactToMinute;
    }

    public String getClinicFullName() {
        return clinicFullName;
    }

    public void setClinicFullName(String clinicFullName) {
        this.clinicFullName = clinicFullName;
    }

    public String getCategoryFullName() {
        return categoryFullName;
    }

    public void setCategoryFullName(String categoryFullName) {
        this.categoryFullName = categoryFullName;
    }

    public String getSubCategoryFullName() {
        return subCategoryFullName;
    }

    public void setSubCategoryFullName(String subCategoryFullName) {
        this.subCategoryFullName = subCategoryFullName;
    }

    public String getDoctorPrice() {
        return doctorPrice;
    }

    public void setDoctorPrice(String doctorPrice) {
        this.doctorPrice = doctorPrice;
    }

    public String getUpdatedDateShow() {
        return updatedDateShow;
    }

    public void setUpdatedDateShow(String updatedDateShow) {
        this.updatedDateShow = updatedDateShow;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public int getEventLogId() {
        return eventLogId;
    }

    public void setEventLogId(int eventLogId) {
        this.eventLogId = eventLogId;
    }

    public String getTotalRealTalkTime() {
        return totalRealTalkTime;
    }

    public void setTotalRealTalkTime(String totalRealTalkTime) {
        this.totalRealTalkTime = totalRealTalkTime;
    }

    public String getFullTextSlotContact() {
        return fullTextSlotContact;
    }

    public void setFullTextSlotContact(String fullTextSlotContact) {
        this.fullTextSlotContact = fullTextSlotContact;
    }

    public String getTextNumOfTimes() {
        return textNumOfTimes;
    }

    public void setTextNumOfTimes(String textNumOfTimes) {
        this.textNumOfTimes = textNumOfTimes;
    }

    public String getTextAmount() {
        return textAmount;
    }

    public void setTextAmount(String textAmount) {
        this.textAmount = textAmount;
    }

    public String getTextPay() {
        return textPay;
    }

    public void setTextPay(String textPay) {
        this.textPay = textPay;
    }

    public String getTextFree() {
        return textFree;
    }

    public void setTextFree(String textFree) {
        this.textFree = textFree;
    }

    public List<String> getLanguageSkills() {
        return languageSkills;
    }

    public void setLanguageSkills(List<String> languageSkills) {
        this.languageSkills = languageSkills;
    }

    public String getShowFullDate() {
        return ConvertDate.StringDateServiceFormat(getAppointmentTime());
    }

    public String getShowFullTime() {
        return ConvertDate.StringDateServiceFormatFullTime(getAppointmentTime());
    }

    public String getShowShortDate() {
        return ConvertDate.StringDateServiceFormatDate(getAppointmentTime());
    }

    public String getShowShortTime() {
        return ConvertDate.StringDateServiceFormatTime(getAppointmentTime());
    }

    public int getShowChanelIcon() {
        String typeName = getContactTypeName();
        if (typeName.equals(AppConstants.CONTACT_CHAT)) return R.drawable.ic_chat_green;
        if (typeName.equals(AppConstants.CONTACT_VOICE)) return R.drawable.ic_voice_green;
        if (typeName.equals(AppConstants.CONTACT_VIDEO)) return R.drawable.ic_video_green;
        return R.drawable.ic_chat_green;
    }



}