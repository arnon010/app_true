package com.truedigital.vhealth.ui.setting.info

import android.content.Intent
import android.os.Bundle
import com.truedigital.vhealth.BuildConfig
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.FragmentSettingAppBinding
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.ui.base.BaseMvpBindingFragment
import com.truedigital.vhealth.ui.password.change.PasswordChangeActivity
import com.truedigital.vhealth.ui.pin.change.PinChangeActivity
import com.truedigital.vhealth.ui.setting.profle.SettingProfileActivity
import com.truedigital.vhealth.ui.setting.testinsurance.ui.TestInsuranceFragment
import com.truedigital.vhealth.utils.AppConstants.LOCAL_LANG_ENG
import com.truedigital.vhealth.utils.AppConstants.LOCAL_LANG_THAI
import com.truedigital.vhealth.utils.CommonUtils
import com.truedigital.vhealth.utils.MyDialog

class SettingAppFragment : BaseMvpBindingFragment<SettingAppFragmentInterface.Presenter>(),
        SettingAppFragmentInterface.View {

    override val binding: FragmentSettingAppBinding by lazy {
        FragmentSettingAppBinding.inflate(layoutInflater)
    }

    override fun createPresenter(): SettingAppFragmentInterface.Presenter {
        return SettingAppFragmentPresenter.create()
    }

    override fun setupView() {
        presenter.getPinStatus()

        mainActivity?.apply {
            hideToolbar()
            setMenuSettingSelected()
        }

        with(binding) {
            svProfile.onItemClicked {
                SettingProfileActivity.startIntern(requireContext())
            }
            svChangePassword.onItemClicked {
                PasswordChangeActivity.startIntern(requireContext())
            }
            svChangePin.onItemClicked {
                PinChangeActivity.startIntern(requireContext())
            }
            svPaymentMethod.onItemClicked {

            }
            svInsuranceSetting.onItemClicked {
                val fragment = TestInsuranceFragment()
                val bundle = Bundle()
                bundle.putBoolean("insuranceFromSetting", false)
                fragment.arguments = bundle
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.content_main, fragment)?.addToBackStack(null)
                transaction?.commit()
            }

            val lang = CommonUtils.getLocalLanguage()
            if (lang == LOCAL_LANG_THAI) {
                svLangTh.setChecked(true)
            } else {
                svLangEn.setChecked(true)
            }

            svLangTh.onItemClicked {
                svLangTh.setChecked(true)
                svLangEn.setChecked(false)
                mainActivity?.onSetLanguage(LOCAL_LANG_THAI)
            }
            svLangEn.onItemClicked {
                svLangTh.setChecked(false)
                svLangEn.setChecked(true)
                mainActivity?.onSetLanguage(LOCAL_LANG_ENG)
            }

            svPrivacyPolicy.onItemClicked {
                openPopup(R.string.setting_app_privacy, BuildConfig.SERVER_BASE_INFO + ENDPOINT_PRIVACY)
            }
            svTermsAndCondition.onItemClicked {
                openPopup(R.string.setting_app_term, BuildConfig.SERVER_BASE_INFO + ENDPOINT_TERM)
            }

            svFaq.onItemClicked {
                openPopup(R.string.setting_app_fag, BuildConfig.SERVER_BASE_INFO + ENDPOINT_FAQ)
            }
            svManuals.onItemClicked {
                openPopup(R.string.setting_app_howtouse, BuildConfig.SERVER_BASE_INFO + ENDPOINT_HOWTOUSE)
            }

            btnLogout.onSingleClick { onLogoutClick() }
        }
    }

    override fun onLogoutClick() {
        presenter.Logout()
    }

    override fun openLogin() {
        mainActivity?.openLogin()
    }

    override fun openSettingLanguage() {
        mainActivity?.openSettingAppLanguage()
    }

    override fun onPinStatus(hasPin: Boolean) {
        binding.svChangePin.setEnable(hasPin)
    }

    private fun openPopup(resIdTitle: Int, url: String) {
        val title = getString(resIdTitle)
        MyDialog(requireContext()).showWebview(title, url, object : MyDialog.OnSelectListener {
            override fun onClickOK() {}
            override fun onClickCancel() {}
        })
    }

    companion object {
        const val ENDPOINT_TERM = "TermAndCondition/Chiiwii"
        const val ENDPOINT_PRIVACY = "PrivacyAndPolicy/Index"

        private const val ENDPOINT_HOWTOUSE = "HowToUse/index"
        private const val ENDPOINT_FAQ = "faq/patient"

        @JvmStatic
        fun newInstance(): SettingAppFragment {
            return SettingAppFragment()
        }
    }
}
