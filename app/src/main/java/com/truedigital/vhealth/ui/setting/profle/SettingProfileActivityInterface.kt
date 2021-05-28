package com.truedigital.vhealth.ui.setting.profle

import com.truedigital.vhealth.ui.base.BaseMvpInterface

interface SettingProfileActivityInterface {

    interface View : BaseMvpInterface.View {
        fun showSuccess()
    }

    interface Presenter : BaseMvpInterface.Presenter<View> {

        var username: String
        var email: String
        var phoneNumber: String

        val usernameStore: String
        val emailStore: String
        val phoneNumberStore: String

        val isUpdateProfileButtonEnable: Boolean

        fun executeUpdateProfile()
    }
}
