package com.truedigital.vhealth.ui.payment.promptpay

import com.truedigital.vhealth.api.RetrofitBuilder
import com.truedigital.vhealth.manager.AppManager
import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.ui.base.BaseMvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromptpayActivityPresenter : BaseMvpPresenter<PromptpayActivityInterface.View>(),
        PromptpayActivityInterface.Presenter {

    companion object {

        @JvmStatic
        fun create(): PromptpayActivityInterface.Presenter {
            return PromptpayActivityPresenter()
        }
    }

    override fun checkAppointmentPromptpay(url: String?) {
        url ?: return

        RetrofitBuilder.getAppointmentService(AppManager.getDataManager().access_token)
                .checkAppointmentPromptpay(url)
                .enqueue(object : Callback<NormalResponseObject?> {
                    override fun onResponse(call: Call<NormalResponseObject?>, response: Response<NormalResponseObject?>) {
                        view.hideLoading()
                        if (response.isSuccessful) {
                            if (response.body()?.isSuccess == true) {
                                view.onPaymentSuccess()
                            }
                        } else {
                            view.showMessage(response)
                        }
                    }

                    override fun onFailure(call: Call<NormalResponseObject?>, t: Throwable) {
                        view.hideLoading()
                        view.showMessage(t.message)
                    }
                })
    }
}
