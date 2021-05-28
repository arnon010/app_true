package com.truedigital.vhealth.ui.password.set

import com.truedigital.vhealth.api.RetrofitBuilder
import com.truedigital.vhealth.manager.AppManager
import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.ui.base.BaseMvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordSetActivityPresenter : BaseMvpPresenter<PasswordSetActivityInterface.View>(),
        PasswordSetActivityInterface.Presenter {

    override var data: PasswordSet? = PasswordSet()
    override var passwordNew: String = ""
    override var passwordNewConfirm: String = ""

    override val isSubmitButtonEnable: Boolean
        get() = passwordNew.length >= PASSWORD_MIN_LANGTH
                && passwordNewConfirm.length >= PASSWORD_MIN_LANGTH

    override fun executeSetPassword() {
        view.showLoading()

        val accessToken = AppManager.getDataManager().access_token
        val call = RetrofitBuilder.getRetrofitToken(accessToken)
                .patchUpdateUser(
                        null,
                        RetrofitBuilder.addRequestBody(""),
                        RetrofitBuilder.addRequestBody(passwordNew),
                        RetrofitBuilder.addRequestBody(passwordNewConfirm),
                        RetrofitBuilder.addRequestBody(data?.refCode),
                        RetrofitBuilder.addRequestBody(data?.otp),
                        null
                )
        call.enqueue(object : Callback<NormalResponseObject?> {
            override fun onResponse(call: Call<NormalResponseObject?>, response: Response<NormalResponseObject?>) {
                view.hideLoading()
                if (response.isSuccessful) {
                    AppManager.getDataManager().setLogin()
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

    companion object {

        private const val PASSWORD_MIN_LANGTH = 6

        @JvmStatic
        fun create(): PasswordSetActivityInterface.Presenter {
            return PasswordSetActivityPresenter()
        }
    }
}
