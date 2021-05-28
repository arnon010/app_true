package com.truedigital.vhealth.ui.payment;

import com.truedigital.vhealth.model.ApiProductRequest;
import com.truedigital.vhealth.model.ItemCreditCardDao;
import com.truedigital.vhealth.model.ItemDoctorDao;
import com.truedigital.vhealth.model.ItemListCreditCardDao;
import com.truedigital.vhealth.model.ItemProductDao;
import com.truedigital.vhealth.model.PaymentDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.ui.base.BaseMvpInterface;
import com.truedigital.vhealth.ui.payment.promptpay.AppointmentUiModel;

import java.util.List;

import okhttp3.MultipartBody;

public class PaymentFragmentInterface {

    public interface View extends BaseMvpInterface.View {

        void showPriceDetail();

        void hidePriceDetail();

        void openPaymentChannel();

        void openPaymentCreditCard();

        void openPaymentBankTransfer();

        void openAppointmentSuccess(ItemDoctorDao data, ApiAppointmentRequest appointmentData, double discountPrice);

        ApiAppointmentRequest getAppointment();

        ApiProductRequest getProductsRequest();

        int getDoctorId();

        double getDoctorPrice();

        int getPaymentMode();

        double getPaymentAmount();

        String getCurrencySymbol();

        double getDiscountPrice();

        double getPriceWithFee();

        void setDiscountPrice(double discountPrice);

        void onDiscountSuccess(double discountPrice);

        void onDiscountNotSuccess();

        String getDiscountCode();

        void onErrorDiscountCode();

        boolean isValidDiscountCode();

        String getPaymentCode();

        void onErrorPaymentCode();

        boolean isValidPaymentCode();

        List<ItemProductDao> getProducts();

        String getProductsJson();

        String getOmiseToken();

        String getShippingName();

        String getShippingPhone();

        String getShippingAddress();

        PaymentDao getPaymentData();

        void loadDataAppointment();

        boolean isValidCreditCard();

        boolean isValidShipping();

        boolean isValid();

        void onErrorCardNumber();

        void onErrorCardName();

        void onErrorCardExpired();

        void onErrorCardExpiredMonth();

        void onErrorCardExpiredYear();

        void onErrorCardCVV();

        String getCard_number();

        String getCard_name();

        String getCard_expired();

        int getCard_expiredMonth();

        int getCard_expiredYear();

        String getCard_cvv();

        void onErrorShippingName();

        void onErrorShippingPhone();

        void onErrorShippingAddress();

        void showListCreditCard(List<ItemListCreditCardDao> listData);

        void callOmise(ItemCreditCardDao creditCard);

        void showAuthorizingPayment(String aurhorizeUrl);

        void showAuthorize(String aurhorizeUrl);

        void onAuthorizeSuccess();

        void onAuthorizeFail();

        void onฺBuyProductSuccess(String aurhorizeUrl);

        void onฺCreateAppointmentSuccess(String aurhorizeUrl, String appointmentNumber);

        void showBuyProductSuccess();

        void showCreateAppountmentSuccess();

        void showSuccess(String message);

        void removeCreditCardSuccess(int position);

        String getAppointmentNumber();

        void setShippingFee(double fee);

        MultipartBody.Part getImageBody();

        void uploadSlipSuccess(String slipUrl);

        void openPromptpayActivityt(AppointmentUiModel uiModel);
    }

    public interface Presenter extends BaseMvpInterface.Presenter<PaymentFragmentInterface.View> {
        void createAppointment();

        void checkDiscountCode(String discountCode, int doctorId, boolean isPaymentCode, String listProducts);

        void checkPaymentCode(String discountCode, int doctorId, boolean isPaymentCode, String listProducts);

        void buyProduct(PaymentDao paymentData);

        void getListCreditCard();

        void removeCreditCard(int cardId, int position);

        void getShippingFee(String appointmentNumber, double lat, double lng);

        void uploadSlip();

        void createAppointmentWithSlip(String slipUrl);

        void buyProductWithSlip();

        void createAppointmentPormptpay(double amount);
    }
}
