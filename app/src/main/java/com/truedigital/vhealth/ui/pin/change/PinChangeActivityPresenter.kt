package com.truedigital.vhealth.ui.pin.change

import com.truedigital.vhealth.api.RetrofitBuilder
import com.truedigital.vhealth.manager.AppManager
import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.ui.base.BaseMvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinChangeActivityPresenter : BaseMvpPresenter<PinChangeActivityInterface.View>(),
        PinChangeActivityInterface.Presenter {

    companion object {

        private const val PIN_LENGTH = 6

        @JvmStatic
        fun create(): PinChangeActivityInterface.Presenter {
            return PinChangeActivityPresenter()
        }
    }

    override var pinOld: String = ""
    override var pinNew: String = ""
    override var pinNewConfirm: String = ""

    override val isChangePinButtonEnable: Boolean
        get() = pinOld.length == PIN_LENGTH
                && pinNew.length == PIN_LENGTH
                && pinNewConfirm.length == PIN_LENGTH

    override fun executeChangePin() {
        view.showLoading()

        val accessToken = AppManager.getDataManager().access_token
        RetrofitBuilder
                .getPinService(accessToken)
                .changePin(
                        pinOld = pinOld,
                        pinNew = pinNew,
                        pinNewConfirm = pinNewConfirm
                )
                .enqueue(object : Callback<NormalResponseObject?> {
                    override fun onResponse(call: Call<NormalResponseObject?>, response: Response<NormalResponseObject?>) {
                        view.hideLoading()
                        if (response.isSuccessful) {
                            view.showSuccess()
                        } else {
                            view.showMessage(response)
                        }
                    }

                    override fun onFailure(call: Call<NormalResponseObject?>, t: Throwable) {
                        view.hideLoading()
                        view.showMessage("" + t.message)
                    }
                })
    }
}
