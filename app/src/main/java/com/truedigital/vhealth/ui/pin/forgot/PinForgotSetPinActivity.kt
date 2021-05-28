package com.truedigital.vhealth.ui.pin.forgot

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityPinForgotSetPinBinding
import com.truedigital.vhealth.extension.gone
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.extension.visible
import com.truedigital.vhealth.ui.base.BaseMvpBindingActivity
import com.truedigital.vhealth.utils.MyDialog

class PinForgotSetPinActivity : BaseMvpBindingActivity<PinForgotSetPinActivityInterface.Presenter>(),
        PinForgotSetPinActivityInterface.View {

    override val binding: ActivityPinForgotSetPinBinding by lazy {
        ActivityPinForgotSetPinBinding.inflate(layoutInflater)
    }

    override fun createPresenter(): PinForgotSetPinActivityInterface.Presenter {
        return PinForgotSetPinActivityPresenter.create()
    }

    override fun setupView() {
        with(binding) {
            lytToolbar.apply {
                ivBack.visible()
                ivBack.onSingleClick { finish() }
                ivChat.gone()
            }

            edPinNew.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.pin = s.toString()
                    validateSetUpPinButton()
                }
            })

            edPinNewConfirm.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.pinConfirm = s.toString()
                    validateSetUpPinButton()
                }
            })

            btnSubmit.onSingleClick { presenter.executeSetPin() }
        }
    }

    override fun showSuccess() {
        MyDialog(this).showSuccess(getString(R.string.pin_forgot_set_pin_success), object : MyDialog.OnSelectListener {
            override fun onClickOK() {
                finish()
            }

            override fun onClickCancel() {}
        })
    }

    private fun validateSetUpPinButton() {
        binding.btnSubmit.isEnabled = presenter.isSubmitButtonEnable
    }

    companion object {

        @JvmStatic
        fun startIntern(context: Context) {
            Intent(context, PinForgotSetPinActivity::class.java)
                    .run { context.startActivity(this) }
        }
    }
}
