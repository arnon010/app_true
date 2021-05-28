package com.truedigital.vhealth.ui.setting.profle

import com.truedigital.vhealth.api.RetrofitBuilder
import com.truedigital.vhealth.extension.isEmailValid
import com.truedigital.vhealth.manager.AppManager
import com.truedigital.vhealth.manager.DataManager
import com.truedigital.vhealth.model.NormalResponseObject
import com.truedigital.vhealth.ui.base.BaseMvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingProfileActivityPresenter : BaseMvpPresenter<SettingProfileActivityInterface.View>(),
        SettingProfileActivityInterface.Presenter {

    companion object {

        @JvmStatic
        fun create(): SettingProfileActivityInterface.Presenter {
            return SettingProfileActivityPresenter()
        }
    }

    override var username: String = usernameStore
    override var email: String = emailStore
    override var phoneNumber: String = phoneNumberStore

    override val isUpdateProfileButtonEnable: Boolean
        get() = isDataChanged
                && isUsernameValid
                && isEmailValid
                && isPhoneNumberValid

    private val dataManager: DataManager
        get() = AppManager.getDataManager()

    override val usernameStore: String
        get() = dataManager.userName
    override val emailStore: String
        get() = dataManager.userEmail
    override val phoneNumberStore: String
        get() = dataManager.phone

    override fun executeUpdateProfile() {
        view.showLoading()

        val accessToken = AppManager.getDataManager().access_token
        val call = RetrofitBuilder
                .getRetrofitToken(accessToken)
                .patchUpdateProfileUser(
                        username.trim()
//                        email.trim(),
//                        phoneNumber.trim()
                )
        call.enqueue(object : Callback<NormalResponseObject?> {
            override fun onResponse(call: Call<NormalResponseObject?>, response: Response<NormalResponseObject?>) {
                view.hideLoading()
                if (response.isSuccessful) {
                    dataManager.userName = username
                    dataManager.userEmail = email
                    dataManager.phone = phoneNumber

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

    private val isUsernameValid: Boolean
        get() = username.isNotBlank()
    private val isEmailValid: Boolean
        get() = email.isEmailValid() || email.isEmpty()
    private val isPhoneNumberValid: Boolean
        get() = phoneNumber.isNotBlank() && phoneNumber.length >= 9

    private val isDataChanged: Boolean
        get() = username != usernameStore
                || email != emailStore
                || phoneNumber != phoneNumberStore
}
