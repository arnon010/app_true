package com.truedigital.vhealth.ui.setting.profle

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivitySettingProfileBinding
import com.truedigital.vhealth.extension.gone
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.extension.visible
import com.truedigital.vhealth.ui.base.BaseMvpBindingActivity
import com.truedigital.vhealth.utils.MyDialog

class SettingProfileActivity : BaseMvpBindingActivity<SettingProfileActivityInterface.Presenter>(),
        SettingProfileActivityInterface.View {

    override val binding: ActivitySettingProfileBinding by lazy {
        ActivitySettingProfileBinding.inflate(layoutInflater)
    }

    override fun createPresenter(): SettingProfileActivityInterface.Presenter {
        return SettingProfileActivityPresenter.create()
    }

    override fun setupView() {
        with(binding) {
            lytToolbar.apply {
                ivBack.visible()
                ivBack.onSingleClick { finish() }
                ivChat.gone()
            }

            edUserName.setText(presenter.usernameStore)
            edEmail.setText(presenter.emailStore)
            edPhone.setText(presenter.phoneNumberStore)
            btnUpdateProfile.onSingleClick { presenter.executeUpdateProfile() }
            validateUpdateProfileButton()

            edUserName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.username = s.toString()
                    validateUpdateProfileButton()
                }
            })

            edEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.email = s.toString()
                    validateUpdateProfileButton()
                }
            })

            edPhone.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.phoneNumber = s.toString()
                    validateUpdateProfileButton()
                }
            })
        }
    }

    override fun showSuccess() {
        MyDialog(this).showSuccess(getString(R.string.update_profile_success), object : MyDialog.OnSelectListener {
            override fun onClickOK() {
                finish()
            }

            override fun onClickCancel() {}
        })
    }

    private fun validateUpdateProfileButton() {
        binding.btnUpdateProfile.isEnabled = presenter.isUpdateProfileButtonEnable
    }

    companion object {

        @JvmStatic
        fun startIntern(context: Context) {
            Intent(context, SettingProfileActivity::class.java)
                    .run { context.startActivity(this) }
        }
    }
}
