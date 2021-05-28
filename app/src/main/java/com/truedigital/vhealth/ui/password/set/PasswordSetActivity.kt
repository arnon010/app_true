package com.truedigital.vhealth.ui.password.set

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityPasswordSetBinding
import com.truedigital.vhealth.extension.gone
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.extension.visible
import com.truedigital.vhealth.ui.base.BaseMvpBindingActivity
import com.truedigital.vhealth.ui.main.MainActivity
import com.truedigital.vhealth.utils.MyDialog

class PasswordSetActivity : BaseMvpBindingActivity<PasswordSetActivityInterface.Presenter>(),
        PasswordSetActivityInterface.View {

    override fun onBackPressed() {
        showConfirmCancelDialog()
    }

    override val binding: ActivityPasswordSetBinding by lazy {
        ActivityPasswordSetBinding.inflate(layoutInflater)
    }

    override fun createPresenter(): PasswordSetActivityInterface.Presenter {
        return PasswordSetActivityPresenter.create()
    }

    override fun initInstance() {
        presenter.data = intent.getParcelableExtra(EXTRA)
    }

    override fun setupView() {
        with(binding) {
            lytToolbar.apply {
                ivBack.visible()
                ivBack.onSingleClick { onBackPressed() }
                ivChat.gone()
            }

            edPasswordNew.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.passwordNew = s.toString()
                    validateSubmitButton()
                }
            })

            edPasswordNewConfirm.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    presenter.passwordNewConfirm = s.toString()
                    validateSubmitButton()
                }
            })

            btnSubmit.onSingleClick { presenter.executeSetPassword() }
        }
    }

    override fun showSuccess() {
        MyDialog(this).showSuccess(
                getString(R.string.set_password_success),
                object : MyDialog.OnSelectListener {
                    override fun onClickOK() {
                        MainActivity.startintent(this@PasswordSetActivity)
                    }

                    override fun onClickCancel() {}
                })
    }

    private fun validateSubmitButton() {
        binding.btnSubmit.isEnabled = presenter.isSubmitButtonEnable
    }

    private fun showConfirmCancelDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.set_password_dialog_cancel_title)
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

        private const val EXTRA = "EXTRA"

        @JvmStatic
        fun startIntern(context: Context, data: PasswordSet) {
            Intent(context, PasswordSetActivity::class.java)
                    .putExtra(EXTRA, data)
                    .run { context.startActivity(this) }
        }
    }
}
