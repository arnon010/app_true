package com.truedigital.vhealth.ui.password.forgot

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityForgotPasswordBinding
import com.truedigital.vhealth.extension.gone
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.extension.toPhoneFormat
import com.truedigital.vhealth.extension.visible
import com.truedigital.vhealth.ui.base.BaseMvpBindingActivity
import com.truedigital.vhealth.ui.password.set.PasswordSet
import com.truedigital.vhealth.ui.password.set.PasswordSetActivity
import com.truedigital.vhealth.utils.AppConstants
import java.text.SimpleDateFormat
import java.util.*

class ForgotPasswordActivity : BaseMvpBindingActivity<ForgotPasswordActivityInterface.Presenter>(),
        ForgotPasswordActivityInterface.View {

    private var countDownTimer: CountDownTimer? = null

    override val binding: ActivityForgotPasswordBinding by lazy {
        ActivityForgotPasswordBinding.inflate(layoutInflater)
    }

    override fun onBackPressed() {
        showConfirmCancelDialog()
    }

    override fun createPresenter(): ForgotPasswordActivityInterface.Presenter? {
        return ForgotPasswordActivityPresenter.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.executeRequestOtp()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    override fun initInstance() {
        presenter.phoneNumber = intent.getStringExtra(AppConstants.EXTRA_MOBILE_NO) ?: ""
    }

    override fun setupView() {
        with(binding) {
            lytToolbar.apply {
                ivBack.visible()
                ivBack.onSingleClick { onBackPressed() }
                ivChat.gone()
            }
            edPhone.setText(presenter.phoneNumber.toPhoneFormat())

            edOtp.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.otp = s.toString()
                    validateSubmitButton()
                }
            })

            btnResend.onSingleClick {
                presenter.executeRequestOtp()
                countDownResend()
            }
            btnSubmit.onSingleClick { presenter.executeVerifyOtp() }
        }
    }

    override fun navigateToSetPassword(data: PasswordSet) {
        PasswordSetActivity.startIntern(this, data)
        finish()
    }

    override fun showRefCode(refCode: String) {
        binding.tvRefCode.text = getString(R.string.forgot_password_ref_code, refCode)
        countDownResend()
    }

    private fun countDownResend() {
        binding.btnResend.visible()
        binding.tvSecond.visible()
        disableResendButton()

        countDownTimer = object : CountDownTimer(RESEND_COUNT_DOWN_TIME, SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                val time = SimpleDateFormat("ss", Locale.getDefault())
                        .format(millisUntilFinished)
                binding.tvSecond.text = getString(R.string.forgot_password_second, time)
            }

            override fun onFinish() {
                enableResendButton()
                binding.tvSecond.gone()
            }
        }
        countDownTimer?.start()
    }

    private fun enableResendButton() {
        binding.btnResend.isEnabled = true
    }

    private fun disableResendButton() {
        binding.btnResend.isEnabled = false
    }

    private fun validateSubmitButton() {
        binding.btnSubmit.isEnabled = presenter.isSubmitButtonEnable
    }

    private fun showConfirmCancelDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.forgot_password_dialog_cancel_title)
        alertDialogBuilder.setPositiveButton(R.string.button_yes) { _, _ ->
            presenter.onLogout()
            finish()
        }
        alertDialogBuilder.setNegativeButton(R.string.button_no) { _, _ ->
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    companion object {

        private const val SECOND: Long = 1_000
        private const val RESEND_COUNT_DOWN_TIME: Long = 60 * SECOND
    }
}
