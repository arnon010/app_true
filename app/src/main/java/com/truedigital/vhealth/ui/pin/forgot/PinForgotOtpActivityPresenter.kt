package com.truedigital.vhealth.ui.pin.forgot

import com.truedigital.vhealth.api.RetrofitBuilder
import com.truedigital.vhealth.manager.AppManager
import com.truedigital.vhealth.manager.DataManager
import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.model.pin.ApiPinRequestOtpObject
import com.truedigital.vhealth.ui.base.BaseMvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinForgotOtpActivityPresenter : BaseMvpPresenter<PinForgotOtpActivityInterface.View>(),
        PinForgotOtpActivityInterface.Presenter {

    private var refCode = ""

    override var phoneNumber: String = dataManager.phone
    override var otp: String = ""

    override val isSubmitButtonEnable: Boolean
        get() = otp.length == OTP_LENGTH

    private val dataManager: DataManager
        get() = AppManager.getDataManager()

    override fun executeRequestOtp() {
        view.showLoading()

        val accessToken = AppManager.getDataManager().access_token
        RetrofitBuilder
                .getOtpService(accessToken)
                .requestOtp()
                .enqueue(object : Callback<ApiPinRequestOtpObject> {
                    override fun onResponse(call: Call<ApiPinRequestOtpObject>, response: Response<ApiPinRequestOtpObject>) {
                        view.hideLoading()
                        if (response.isSuccessful) {
                            refCode = response.body()?.referenceCode ?: ""
                            view.showRefCode(refCode)
                        } else {
                            view.showMessage(response)
                        }
                    }

                    override fun onFailure(call: Call<ApiPinRequestOtpObject>, t: Throwable) {
                        view.hideLoading()
                        view.showMessage("" + t.message)
                    }
                })
    }

    override fun executeVerifyOtp() {
        view.showLoading()

        val accessToken = AppManager.getDataManager().access_token
        RetrofitBuilder
                .getOtpService(accessToken)
                .verifyOtp(
                        referenceCode = refCode,
                        otp = otp
                )
                .enqueue(object : Callback<NormalResponseObject> {
                    override fun onResponse(call: Call<NormalResponseObject>, response: Response<NormalResponseObject>) {
                        view.hideLoading()
                        if (response.isSuccessful) {
                            view.navigateToSetPin()
                        } else {
                            view.showMessage(response)
                        }
                    }

                    override fun onFailure(call: Call<NormalResponseObject>, t: Throwable) {
                        view.hideLoading()
                        view.showMessage("" + t.message)
                    }
                })
    }

    companion object {

        private const val OTP_LENGTH = 6

        @JvmStatic
        fun create(): PinForgotOtpActivityInterface.Presenter {
            return PinForgotOtpActivityPresenter()
        }
    }
}
