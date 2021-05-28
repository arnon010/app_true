package com.truedigital.vhealth.ui.pin.change

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityPinChangeBinding
import com.truedigital.vhealth.extension.gone
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.extension.visible
import com.truedigital.vhealth.ui.base.BaseMvpBindingActivity
import com.truedigital.vhealth.ui.pin.forgot.PinForgotOtpActivity
import com.truedigital.vhealth.utils.MyDialog

class PinChangeActivity : BaseMvpBindingActivity<PinChangeActivityInterface.Presenter>(),
        PinChangeActivityInterface.View {

    override val binding: ActivityPinChangeBinding by lazy {
        ActivityPinChangeBinding.inflate(layoutInflater)
    }

    override fun createPresenter(): PinChangeActivityInterface.Presenter {
        return PinChangeActivityPresenter.create()
    }

    override fun setupView() {
        with(binding) {
            lytToolbar.apply {
                ivBack.visible()
                ivBack.onSingleClick { finish() }
                ivChat.gone()
            }

            edPinOld.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.pinOld = s.toString()
                    validateChangePinButton()
                }
            })

            edPinNew.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.pinNew = s.toString()
                    validateChangePinButton()
                }
            })

            edPinNewConfirm.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.pinNewConfirm = s.toString()
                    validateChangePinButton()
                }
            })

            tvPinForgot.onSingleClick {
                PinForgotOtpActivity.startIntent(this@PinChangeActivity)
            }
            btnChangePin.onSingleClick { presenter.executeChangePin() }
        }
    }

    override fun showSuccess() {
        MyDialog(this).showSuccess(getString(R.string.change_pin_success), object : MyDialog.OnSelectListener {
            override fun onClickOK() {
                finish()
            }

            override fun onClickCancel() {}
        })
    }

    private fun validateChangePinButton() {
        binding.btnChangePin.isEnabled = presenter.isChangePinButtonEnable
    }

    companion object {

        @JvmStatic
        fun startIntern(context: Context) {
            Intent(context, PinChangeActivity::class.java)
                    .run { context.startActivity(this) }
        }
    }
}
