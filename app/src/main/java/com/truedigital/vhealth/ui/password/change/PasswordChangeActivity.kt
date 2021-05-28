package com.truedigital.vhealth.ui.password.change

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityPasswordChangeBinding
import com.truedigital.vhealth.extension.gone
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.extension.visible
import com.truedigital.vhealth.ui.base.BaseMvpBindingActivity
import com.truedigital.vhealth.utils.MyDialog

class PasswordChangeActivity : BaseMvpBindingActivity<PasswordChangeActivityInterface.Presenter>(),
        PasswordChangeActivityInterface.View {

    override val binding: ActivityPasswordChangeBinding by lazy {
        ActivityPasswordChangeBinding.inflate(layoutInflater)
    }

    override fun createPresenter(): PasswordChangeActivityInterface.Presenter {
        return PasswordChangeActivityPresenter.create()
    }

    override fun setupView() {
        with(binding) {
            lytToolbar.apply {
                ivBack.visible()
                ivBack.onSingleClick { finish() }
                ivChat.gone()
            }

            edPasswordOld.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.passwordOld = s.toString()
                    validateChangePasswordButton()
                }
            })

            edPasswordNew.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.passwordNew = s.toString()
                    validateChangePasswordButton()
                }
            })

            edPasswordNewConfirm.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.passwordNewConfirm = s.toString()
                    validateChangePasswordButton()
                }
            })

            btnChangePassword.onSingleClick { presenter.executeChangePassword() }
        }
    }

    override fun showSuccess() {
        MyDialog(this).showSuccess(getString(R.string.change_password_success), object : MyDialog.OnSelectListener {
            override fun onClickOK() {
                finish()
            }

            override fun onClickCancel() {}
        })
    }

    private fun validateChangePasswordButton() {
        binding.btnChangePassword.isEnabled = presenter.isChangePasswordButtonEnable
    }

    companion object {

        @JvmStatic
        fun startIntern(context: Context) {
            Intent(context, PasswordChangeActivity::class.java)
                    .run { context.startActivity(this) }
        }
    }
}
