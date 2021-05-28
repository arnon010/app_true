package com.truedigital.vhealth.ui.main;

import androidx.annotation.StringRes;
import android.widget.ImageView;

import com.truedigital.vhealth.model.BloodPressureObject;
import com.truedigital.vhealth.model.BloodSugarObject;
import com.truedigital.vhealth.model.ChildGrowthCriteriaObject;
import com.truedigital.vhealth.model.CongenitalDiseaseObject;
import com.truedigital.vhealth.model.DailyBloodPressureObject;
import com.truedigital.vhealth.model.DailyBloodSugarObject;
import com.truedigital.vhealth.model.DailyHeartBeatRateObject;
import com.truedigital.vhealth.model.FoodAllergyObject;
import com.truedigital.vhealth.model.HeartBeatRateObject;
import com.truedigital.vhealth.model.ItemArticleDao;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.DoctorNoteObject;
import com.truedigital.vhealth.model.LaboratoryOtherObject;
import com.truedigital.vhealth.model.MedicationHistoryObject;
import com.truedigital.vhealth.model.MedicineAllergyObject;
import com.truedigital.vhealth.model.PregnantHistoryObject;
import com.truedigital.vhealth.model.RowHeaderVaccineObject;
import com.truedigital.vhealth.model.SystemConfigurationObject;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.model.appointment.ItemAppointmentDao;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;

/**
 * Created by songkrit on 12/20/2017 AD.
 */

public class MainActivityInterface {
    public interface View extends BaseMvpInterface.View {
        void openLogin();
        void showToolbarMain(boolean isShow);
        void updateToolbar(String title, boolean showImage, String urlImageDoctor, boolean showBtnAdd);

        void showToolbar(@StringRes int resId, boolean showHomeAsUp);
        void showToolbar(String title, boolean showHomeAsUp);
        void showToolbar(String title, boolean showHomeAsUp, String urlImage);
        void showToolbar(boolean showHomeAsUp, String image);
        void showToolbar(@StringRes int resId, boolean showHomeAsUp, boolean hideBgBottom);
        void showToolbar(String title, boolean showHomeAsUp, boolean hideBgBottom);
        void setShowBackButton(boolean isShow);
        void setSwipeRefreshLayout(boolean isShow);
        void hideToolbar();
        void showCallingBar();
        void hideCallingBar();
        void openDoctorDetail(int id);
        void openDoctorDetail(ItemDoctorDao data);

        void canStartAppointment(boolean canStart);

        //..Setting
        void openSettingApp();
        void openSettingAppLanguage();
        void onSetLanguage(String language);

        //..Products
        void openListProducts(int groupId);
        void openProductDetail(int id);
        void openProductConfirm(int id);

        //..Doctor
        void openDoctorCalendar(int doctorId);
        //..Appointment
        void gotoAppointment();
        void openAppointmentCreate(ItemDoctorDao data);
        void openAppointmentCreateDetail(ItemDoctorDao data);
        void openAppointmentConfirm(ItemDoctorDao data);
        void openAppointmentSuccess(ItemDoctorDao data, ApiAppointmentRequest appointmentData, double discountPrice);
        void openAppointmentDetail(int appointmentType, String appointmentNumber);
        void openAppointmentDetail(int position, ItemAppointmentDao data, ImageView shareView);
        void openAppointmentCancel(ItemAppointmentDao data, boolean isViewOnly);

        void openAppointmentHistory(final String appointmentNumber);
        void openAppointmentCancel(ItemAppointmentDao data);

        void openAppointmentCreateFromArticle(int doctorId);

        void openRoom(String appointmentNumber,String contactTypeCode);

        //..Articles
        void openListArticles(int doctorId, String doctorName, int articleGroupId);
        void openArticleDetail(int articleId);
        void openArticlePatientDetail(int articleId);
        void openArticleDetail(ItemArticleDao data);
        void openArticlePatientDetail(ItemArticleDao data);
        void openSimilarArticle(int articleId);

        void openDoctorNoteConfirm(ItemAppointmentDao data, boolean isViewOnly);
        void openDoctorNoteConfirm(ItemAppointmentDao data, boolean isViewOnly, boolean isShowPayment);

        void openRelationshipMenu(int patientId);
        void openHealthInformationMenu(int patientId, boolean isChild, String titleToolbar);
        void openDoctorNote(int patientId, int patientMenuId);
        void openDoctorNoteDetail(DoctorNoteObject data);
        void openPregnantHistory(int patientId, int patientMenuId);
        void openNewFormPregnantHistory(PregnantHistoryObject data, boolean isNewFromMenu);
        void openUpdateFormPregnantHistory(PregnantHistoryObject data);
        void openMedicationHistory(int patientId, int patientMenuId);
        void openNewFormMedicationHistory(MedicationHistoryObject data, Boolean isNewFromMenu);
        void openUpdateFormMedicationHistory(MedicationHistoryObject data);
        void openFormMedicationHistoryChiiwiiLive(MedicationHistoryObject data);
        void openVaccinationHistory(int patientId, int patientMenuId, String menuCode);
        void openVaccineDetail(RowHeaderVaccineObject data);
        void openLaboratory(int patientId, int patientMenuId, int defaultTap);
        void openNewFormLaboratoryOther(LaboratoryOtherObject data, boolean isNewFromMenu);
        void openUpdateFormLaboratoryOther(LaboratoryOtherObject data);
        void openHeartBeatRate(int patientId, int patientMenuId, String menuCode);
        void openViewAllHeartBeatRate(int patientId, int patientMenuId);
        void openTimeHeartBeatRate(DailyHeartBeatRateObject data);
        void openNewFormHeartBeatRate(HeartBeatRateObject data);
        void openUpdateFormHeartBeatRate(HeartBeatRateObject data);
        void openBloodPressure(int patientId, int patientMenuId, String menuCode);
        void openViewAllBloodPressure(int patientId, int patientMenuId);
        void openTimeBloodPressure(DailyBloodPressureObject data);
        void openNewFormBloodPressure(BloodPressureObject data);
        void openUpdateFormBloodPressure(BloodPressureObject data);
        void openBloodSugar(int patientId, int patientMenuId, String menuCode);
        void openViewAllBloodSugar(int patientId, int patientMenuId);
        void openTimeBloodSugar(DailyBloodSugarObject data);
        void openNewFormBloodSugar(BloodSugarObject data);
        void openUpdateFormBloodSugar(BloodSugarObject data);
        void openMenstrualPeriod(int patientId, int patientMenuId, String menuCode);
        void openChildGrowth(ChildGrowthCriteriaObject criteria);
        void openMedicineAllergy(int patientId, int patientMenuId);
        void openNewFormMedicineAllergy(MedicineAllergyObject data, Boolean isNewFromMenu);
        void openUpdateFormMedicineAllergy(MedicineAllergyObject data);
        void openFoodAllergy(int patientId, int patientMenuId);
        void openNewFormFoodAllergy(FoodAllergyObject data, Boolean isNewFromMenu);
        void openUpdateFormFoodAllergy(FoodAllergyObject data);
        void openCongenitalDisease(int patientId, int patientMenuId);
        void openNewFormCongenitalDisease(CongenitalDiseaseObject data, Boolean isNewFromMenu);
        void openUpdateFormCongenitalDisease(CongenitalDiseaseObject data);

        void openVideoCall();

        void setSystemConfigurationApi(SystemConfigurationObject data);
        void onNavigateEHR(boolean hasPIN);

        SystemConfigurationObject getSystemConfiguration();

        void foundNewVersion();

        void openAddress();
        void openShippingStatus(String appointmentNumber, String invoiceNo, String status);

        void setMenuHomeSelected();
        void setMenuEhrSelected();
        void setMenuAppointmentSelected();
        void setMenuSettingSelected();
    }

    public interface Presenter extends BaseMvpInterface.Presenter<MainActivityInterface.View> {
        void getSystemConfigurationApi();
        void getPINStatus();
        void callServiceGetAppointment(final String appointmentNumber);
        void getApplicationVersion();
    }

}
