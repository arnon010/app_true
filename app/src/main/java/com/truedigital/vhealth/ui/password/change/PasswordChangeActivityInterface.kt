package com.truedigital.vhealth.ui.password.change

import com.truedigital.vhealth.ui.base.BaseMvpInterface

interface PasswordChangeActivityInterface {

    interface View : BaseMvpInterface.View {

        fun showSuccess()
    }

    interface Presenter : BaseMvpInterface.Presenter<View> {
        var passwordOld: String
        var passwordNew: String
        var passwordNewConfirm: String

        val isChangePasswordButtonEnable: Boolean

        fun executeChangePassword()
    }
}
