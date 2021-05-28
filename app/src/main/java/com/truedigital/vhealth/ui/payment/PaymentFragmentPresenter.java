package com.truedigital.vhealth.ui.payment;

import com.truedigital.vhealth.manager.AppManager;
import com.truedigital.vhealth.model.ApiListCreditCard;
import com.truedigital.vhealth.model.ApiProductRequest;
import com.truedigital.vhealth.model.ApiShipping;
import com.truedigital.vhealth.model.ApiUploadSlipResponse;
import com.truedigital.vhealth.model.PaymentDao;
import com.truedigital.vhealth.model.appointment.ApiAppointmentRequest;
import com.truedigital.vhealth.model.appointment.ApiAppointmentResponse;
import com.truedigital.vhealth.model.discount.ItemDiscount;
import com.truedigital.vhealth.ui.base.BaseMvpPresenter;
import com.truedigital.vhealth.ui.payment.promptpay.AppointmentUiModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.truedigital.vhealth.api.RetrofitBuilder.getAppointmentService;
import static com.truedigital.vhealth.api.RetrofitBuilder.getRetrofitToken;

public class PaymentFragmentPresenter extends BaseMvpPresenter<PaymentFragmentInterface.View>
        implements PaymentFragmentInterface.Presenter {

    private AppointmentUiModel uiModel;
    private double totalPrice = 0;

    public static PaymentFragmentInterface.Presenter create() {
        return new PaymentFragmentPresenter();
    }

    @Override
    public void checkDiscountCode(String discountCode, int doctorId, boolean isPaymentCode, String listProducts) {
        getView().showLoading();

        discountCode = discountCode.trim();
        //..fix discount for appointment
        if (doctorId > 0) listProducts = "";

        Call<ItemDiscount> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).checkDiscountCode(discountCode, doctorId, isPaymentCode, listProducts);
        call.enqueue(new Callback<ItemDiscount>() {
            @Override
            public void onResponse(Call<ItemDiscount> call, Response<ItemDiscount> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().onDiscountSuccess(response.body().getDiscountPrice());
                } else {
                    getView().onDiscountNotSuccess();
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ItemDiscount> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void checkPaymentCode(String discountCode, int doctorId, boolean isPaymentCode, String listProducts) {
        getView().showLoading();

        Call<ItemDiscount> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).checkDiscountCode(discountCode, doctorId, isPaymentCode, listProducts);
        call.enqueue(new Callback<ItemDiscount>() {
            @Override
            public void onResponse(Call<ItemDiscount> call, Response<ItemDiscount> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().onDiscountSuccess(response.body().getDiscountPrice());
                } else {
                    getView().onDiscountNotSuccess();
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ItemDiscount> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void createAppointment() {
        getView().showLoading();
        ApiAppointmentRequest body = getView().getAppointment();
        Call<ApiAppointmentResponse> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).createAppointment(body);
        call.enqueue(new Callback<ApiAppointmentResponse>() {
            @Override
            public void onResponse(Call<ApiAppointmentResponse> call, Response<ApiAppointmentResponse> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    Timber.i("createAppointment getAppointmentNumber: %s", response.body().getAppointmentNumber());
                    Timber.i("createAppointment getAurhorizeUri: %s", response.body().getAurhorizeUri());
                    getView().onฺCreateAppointmentSuccess(response.body().getAurhorizeUri(), response.body().getAppointmentNumber());
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentResponse> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void buyProduct(PaymentDao paymentData) {
        getView().showLoading();
        ApiProductRequest body = getView().getProductsRequest();

        Call<ApiAppointmentResponse> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).postBuyProducts(body);
        call.enqueue(new Callback<ApiAppointmentResponse>() {
            @Override
            public void onResponse(Call<ApiAppointmentResponse> call, Response<ApiAppointmentResponse> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    Timber.i("buyProduct getAurhorizeUri: %s", response.body().getAurhorizeUri());
                    getView().onฺBuyProductSuccess(response.body().getAurhorizeUri());
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentResponse> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }

    @Override
    public void buyProductWithSlip() {
        getView().showLoading();
        ApiProductRequest body = getView().getProductsRequest();
        Call<ApiAppointmentResponse> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).buyProductsWithSlip(body);
        call.enqueue(new Callback<ApiAppointmentResponse>() {
            @Override
            public void onResponse(Call<ApiAppointmentResponse> call, Response<ApiAppointmentResponse> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    Timber.i("buyProductWithSlip getAurhorizeUri: %s", response.body().getAurhorizeUri());
                    getView().onฺBuyProductSuccess(response.body().getAurhorizeUri());
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentResponse> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }

    @Override
    public void getListCreditCard() {
        getView().showLoading();
        Call<ApiListCreditCard> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).getListCreditCard();
        call.enqueue(new Callback<ApiListCreditCard>() {
            @Override
            public void onResponse(Call<ApiListCreditCard> call, Response<ApiListCreditCard> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().showListCreditCard(response.body().getListData());
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiListCreditCard> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void removeCreditCard(int cardId, final int position) {
        getView().showLoading();
        Call<ApiListCreditCard> call = getRetrofitToken(AppManager.getDataManager().getAccess_token()).removeCreditCard(cardId);
        call.enqueue(new Callback<ApiListCreditCard>() {
            @Override
            public void onResponse(Call<ApiListCreditCard> call, Response<ApiListCreditCard> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    getView().removeCreditCardSuccess(position);
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiListCreditCard> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getShippingFee(String appointmentNumber, double lat, double lng) {
        getView().showLoading();
        ApiProductRequest body = getView().getProductsRequest();

        Call<ApiShipping> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).getShippingFee(appointmentNumber, lat, lng);
        call.enqueue(new Callback<ApiShipping>() {
            @Override
            public void onResponse(Call<ApiShipping> call, Response<ApiShipping> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {

                    Timber.i("getShippingFee: %s", response.body().getShippingFee());
                    getView().setShippingFee(response.body().getShippingFee());
                } else {
                    getView().showMessage(response);
                    Timber.e("getShippingFee Shipping Fee error");
                    getView().setShippingFee(0);
                }
            }

            @Override
            public void onFailure(Call<ApiShipping> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
                Timber.e("getShippingFee: %s", t.getMessage());
                getView().setShippingFee(0);
            }
        });
    }

    @Override
    public void uploadSlip() {
        getView().showLoading();

        MultipartBody.Part attachImage = getView().getImageBody();
        Call<ApiUploadSlipResponse> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).postUploadSlip(attachImage);
        call.enqueue(new Callback<ApiUploadSlipResponse>() {
            @Override
            public void onResponse(Call<ApiUploadSlipResponse> call, Response<ApiUploadSlipResponse> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    Timber.i("uploadSlip getSlipUrl: %s", response.body().getSlipUrl());
                    getView().uploadSlipSuccess(response.body().getSlipUrl());
                } else {
                    getView().showMessage("Upload Fail");
                }
            }

            @Override
            public void onFailure(Call<ApiUploadSlipResponse> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage("" + t.getMessage());
            }
        });
    }

    @Override
    public void createAppointmentWithSlip(String slipUrl) {
        getView().showLoading();
        ApiAppointmentRequest body = getView().getAppointment();
        Call<ApiAppointmentResponse> call = getAppointmentService(AppManager.getDataManager().getAccess_token()).createAppointmentWithSlip(body);
        call.enqueue(new Callback<ApiAppointmentResponse>() {
            @Override
            public void onResponse(Call<ApiAppointmentResponse> call, Response<ApiAppointmentResponse> response) {
                getView().hideLoading();
                if (response.isSuccessful()) {
                    Timber.i("createAppointmentWithSlip getAppointmentNumber: %s", response.body().getAppointmentNumber());
                    Timber.i("createAppointmentWithSlip getAurhorizeUri: %s", response.body().getAurhorizeUri());
                    getView().onฺCreateAppointmentSuccess(response.body().getAurhorizeUri(), response.body().getAppointmentNumber());
                } else {
                    getView().showMessage(response);
                }
            }

            @Override
            public void onFailure(Call<ApiAppointmentResponse> call, Throwable t) {
                getView().hideLoading();
                getView().showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void createAppointmentPormptpay(double amount) {
        // Mock for test by pass can delete it
//        AppointmentUiModel uiModel = new AppointmentUiModel(
//                "https://pay.omise.co/payments/pay2_test_5m8t9gkz76487atjp8b/authorize",
//                "https://api.omise.co/charges/chrg_test_5m8t9gkw6wsex0qmns0/documents/docu_test_5m8t9gmosv4gqfh319b/downloads/969B0D536C19AF47",
//                amount
//        );
//        getView().openPromptpayActivityt(uiModel);

        if (amount == totalPrice && uiModel != null) {
            getView().openPromptpayActivityt(uiModel);
            return;
        }
        totalPrice = amount;

        getView().showLoading();
        ApiAppointmentRequest body = getView().getAppointment();
        getAppointmentService(AppManager.getDataManager().getAccess_token())
                .createAppointmentPromptpay(body)
                .enqueue(new Callback<ApiAppointmentResponse>() {
                    @Override
                    public void onResponse(Call<ApiAppointmentResponse> call, Response<ApiAppointmentResponse> response) {
                        getView().hideLoading();
                        if (response.isSuccessful()) {
                            uiModel = new AppointmentUiModel(response.body(), amount);
                            getView().openPromptpayActivityt(uiModel);
                        } else {
                            getView().showMessage(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiAppointmentResponse> call, Throwable t) {
                        getView().hideLoading();
                        getView().showMessage(t.getMessage());
                    }
                });
    }
}
