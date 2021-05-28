package com.truedigital.vhealth.ui.password.change

import com.truedigital.vhealth.api.RetrofitBuilder
import com.truedigital.vhealth.manager.AppManager
import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.ui.base.BaseMvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordChangeActivityPresenter : BaseMvpPresenter<PasswordChangeActivityInterface.View>(),
        PasswordChangeActivityInterface.Presenter {

    companion object {

        private const val PASSWORD_MIN_LANGTH = 6

        @JvmStatic
        fun create(): PasswordChangeActivityInterface.Presenter {
            return PasswordChangeActivityPresenter()
        }
    }

    override var passwordOld: String = ""
    override var passwordNew: String = ""
    override var passwordNewConfirm: String = ""

    override val isChangePasswordButtonEnable: Boolean
        get() = passwordOld.length >= PASSWORD_MIN_LANGTH
                && passwordNew.length >= PASSWORD_MIN_LANGTH
                && passwordNewConfirm.length >= PASSWORD_MIN_LANGTH

    override fun executeChangePassword() {
        view.showLoading()

        val accessToken = AppManager.getDataManager().access_token
        val call = RetrofitBuilder.getRetrofitToken(accessToken)
                .patchUpdateUser(
                        null,
                        RetrofitBuilder.addRequestBody(passwordOld),
                        RetrofitBuilder.addRequestBody(passwordNew),
                        RetrofitBuilder.addRequestBody(passwordNewConfirm),
                        RetrofitBuilder.addRequestBody(""),
                        RetrofitBuilder.addRequestBody(""),
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
}
