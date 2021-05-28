package com.truedigital.vhealth.ui.password.set

import com.truedigital.vhealth.ui.base.BaseMvpInterface

interface PasswordSetActivityInterface {

    interface View : BaseMvpInterface.View {

        fun showSuccess()

    }

    interface Presenter : BaseMvpInterface.Presenter<View> {
        var data: PasswordSet?
        var passwordNew: String
        var passwordNewConfirm: String

        val isSubmitButtonEnable: Boolean

        fun executeSetPassword()
    }
}
