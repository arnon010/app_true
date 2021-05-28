package com.truedigital.vhealth.ui.pin.forgot

import com.truedigital.vhealth.api.RetrofitBuilder
import com.truedigital.vhealth.manager.AppManager
import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.ui.base.BaseMvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinForgotSetPinActivityPresenter : BaseMvpPresenter<PinForgotSetPinActivityInterface.View>(),
        PinForgotSetPinActivityInterface.Presenter {

    companion object {

        private const val PIN_LENGTH = 6

        @JvmStatic
        fun create(): PinForgotSetPinActivityInterface.Presenter {
            return PinForgotSetPinActivityPresenter()
        }
    }

    override var pin: String = ""
    override var pinConfirm: String = ""

    override val isSubmitButtonEnable: Boolean
        get() = pin.length == PIN_LENGTH
                && pinConfirm.length == PIN_LENGTH

    override fun executeSetPin() {
        view.showLoading()

        val accessToken = AppManager.getDataManager().access_token
        RetrofitBuilder
                .getPinService(accessToken)
                .setUpPin(
                        pin = pin,
                        pinConfirm = pinConfirm
                )
                .enqueue(object : Callback<NormalResponseObject> {
                    override fun onResponse(call: Call<NormalResponseObject>, response: Response<NormalResponseObject>) {
                        view.hideLoading()
                        if (response.isSuccessful) {
                            view.showSuccess()
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
}
