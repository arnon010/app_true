package com.truedigital.vhealth.ui.password.forgot

import com.onesignal.OneSignal
import com.truedigital.vhealth.api.RetrofitBuilder
import com.truedigital.vhealth.manager.AppManager
import com.truedigital.vhealth.manager.DataManager
import com.truedigital.vhealth.model.ApiAccessToken
import com.truedigital.vhealth.model.ApiGenerateOTP
import com.truedigital.vhealth.model.ApiOneSignalRequest
import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.ui.base.BaseMvpPresenter
import com.truedigital.vhealth.ui.password.set.PasswordSet
import com.truedigital.vhealth.utils.AppConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class ForgotPasswordActivityPresenter : BaseMvpPresenter<ForgotPasswordActivityInterface.View>(),
        ForgotPasswordActivityInterface.Presenter {

    private var refCode = ""

    override var phoneNumber: String = ""
    override var otp: String = ""

    override val isSubmitButtonEnable: Boolean
        get() = otp.length == OTP_LENGTH

    private val dataManager: DataManager
        get() = AppManager.getDataManager()

    override fun executeRequestOtp() {
        view.showLoading()

        RetrofitBuilder
                .getRetrofitServiceAuthen()
                .postGetOTP(phoneNumber)
                .enqueue(object : Callback<ApiGenerateOTP> {
                    override fun onResponse(call: Call<ApiGenerateOTP>, response: Response<ApiGenerateOTP>) {
                        view.hideLoading()
                        if (response.isSuccessful) {
                            refCode = response.body()?.reference ?: ""
                            view.showRefCode(refCode)
                        } else {
                            view.showMessage(response)
                        }
                    }

                    override fun onFailure(call: Call<ApiGenerateOTP>, t: Throwable) {
                        view.hideLoading()
                        view.showMessage("" + t.message)
                    }
                })
    }

    override fun executeVerifyOtp() {
        view.showLoading()

        RetrofitBuilder
                .getRetrofitServiceAuthen()
                .postLogin(
                        AppConstants.AUTHEN_CLIENT_ID,
                        AppConstants.AUTHEN_CLIENT_SECRET,
                        AppConstants.AUTHEN_METHOD_OTP,
                        AppConstants.AUTHEN_GRANT_TYPE,
                        phoneNumber,
                        otp,
                        refCode
                )
                .enqueue(object : Callback<ApiAccessToken> {
                    override fun onResponse(call: Call<ApiAccessToken>, response: Response<ApiAccessToken>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            data?.access_token
                                    ?.let {
                                        dataManager.setAccessToken(data)
                                        addOneSignalToken(it)
                                        view.navigateToSetPassword(PasswordSet(refCode = refCode, otp = otp))
                                    }
                                    ?: view.showMessage(data?.errorMessage)
                        } else {
                            view.showErrorMessage(response.errorBody())
                        }
                        view.hideLoading()
                    }

                    override fun onFailure(call: Call<ApiAccessToken>, t: Throwable) {
                        view.hideLoading()
                        view.showMessage("" + t.message)
                    }
                })
    }

    private fun getOneSignalPlayerId(): String {
        val status = OneSignal.getPermissionSubscriptionState()
        return status.subscriptionStatus.userId
    }

    private fun addOneSignalToken(accessToken: String) {
        val udId = getOneSignalPlayerId()
        val tokenOneSignal = view.tokenIdOneSignal

        val request = ApiOneSignalRequest()
        request.udId = udId
        request.token = tokenOneSignal
        request.userId = dataManager.userId
        request.os = AppConstants.APP_MOBILE_OS

        RetrofitBuilder
                .getRetrofitToken(accessToken)
                .postAddOneSignalToken(request)
                .enqueue(object : Callback<NormalResponseObject> {
                    override fun onResponse(call: Call<NormalResponseObject>, response: Response<NormalResponseObject>) {
                        if (response.isSuccessful) {
                            Timber.d("Add token OneSignal Success")
                        } else {
                            view.showMessage(response)
                        }
                    }

                    override fun onFailure(call: Call<NormalResponseObject>, t: Throwable) {
                        view.showMessage("" + t.message)
                    }
                })
    }

    companion object {

        private const val OTP_LENGTH = 6

        @JvmStatic
        fun create(): ForgotPasswordActivityInterface.Presenter {
            return ForgotPasswordActivityPresenter()
        }
    }
}
