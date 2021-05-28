package com.truedigital.vhealth.ui.payment.promptpay

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.CountDownTimer
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.StreamEncoder
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.caverock.androidsvg.SVG
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.ActivityPromptpayBinding
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.glide.SvgDecoder
import com.truedigital.vhealth.glide.SvgDrawableTranscoder
import com.truedigital.vhealth.glide.SvgSoftwareLayerSetter
import com.truedigital.vhealth.ui.base.BaseMvpBindingActivity
import com.truedigital.vhealth.ui.main.MainActivity
import com.truedigital.vhealth.utils.AmountUtils
import com.truedigital.vhealth.utils.AppConstants
import com.truedigital.vhealth.utils.FileSaveUtils
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class PromptpayActivity : BaseMvpBindingActivity<PromptpayActivityInterface.Presenter>(),
        PromptpayActivityInterface.View {

    private var countDownTimerExpire: CountDownTimer? = null
    private var countDownTimerCheckStatus: CountDownTimer? = null
    private var uiModel: AppointmentUiModel? = null

    override val binding: ActivityPromptpayBinding by lazy {
        ActivityPromptpayBinding.inflate(layoutInflater)
    }

    override fun onBackPressed() {
        showConfirmCancelDialog()
    }

    override fun onResume() {
        super.onResume()
        countDownTimerCheckStatus?.start()
    }

    override fun onPause() {
        super.onPause()
        countDownTimerCheckStatus?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimerExpire?.cancel()
        countDownTimerCheckStatus?.cancel()
    }

    override fun createPresenter(): PromptpayActivityInterface.Presenter {
        return PromptpayActivityPresenter.create()
    }

    override fun initInstance() {
        uiModel = intent.getParcelableExtra(EXTRA_APPOINTMENT)
    }

    @SuppressLint("SetTextI18n")
    override fun setupView() {
        uiModel?.let { model ->
            showPromptPayInfo(model.qrUri)
            checkPaymentStatusWithCountDown(model.aurhorizeUri)

            val amountFormat = AmountUtils.decimalFormat(model.amount ?: 0.0)
            binding.tvPromptpayAmount.text = "$amountFormat ${getString(R.string.money_baht)}"
            binding.btnPayment.onSingleClick {
                presenter.checkAppointmentPromptpay(model.aurhorizeUri)
            }
        }

        binding.ibBack.onSingleClick {
            onBackPressed()
        }
    }

    override fun onPaymentSuccess() {
        setResult(RESULT_OK)
        finish()
    }

    private fun requestCaptureQrcode() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        captureQrCode()
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    }

                    override fun onPermissionRationaleShouldBeShown(request: PermissionRequest?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                })
                .check()
    }

    private fun captureQrCode() {
        try {
            FileSaveUtils(this).storeToGallery(binding.lytQrcode)
            Toast.makeText(this, R.string.promptpay_save_image_success, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, R.string.promptpay_save_image_failed, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPaymentStatusWithCountDown(aurhorizeUri: String?) {
        countDownTimerCheckStatus = object : CountDownTimer(CHECK_PAYMENT_STATUS_TIME, CHECK_PAYMENT_EVERY) {
            override fun onTick(millisUntilFinished: Long) {
                presenter.checkAppointmentPromptpay(aurhorizeUri)
            }

            override fun onFinish() {
            }
        }
        countDownTimerCheckStatus?.start()
    }

    private fun showPromptPayInfo(qrUri: String?) {
        val uri = Uri.parse(qrUri)
        loadQrSvg(uri)

        countDownTimerExpire = object : CountDownTimer(QR_CODE_EXPITE_TIME, SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                val time = SimpleDateFormat("mm:ss", Locale.getDefault())
                        .format(millisUntilFinished)
                binding.tvExpireTime.text = getString(R.string.promptpay_timer, time)
            }

            override fun onFinish() {
                showQrExpireDialog()
            }
        }
        countDownTimerExpire?.start()
    }

    private fun loadQrSvg(uri: Uri?) {
        Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri::class.java, this), InputStream::class.java)
                .from(Uri::class.java)
                .`as`(SVG::class.java)
                .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
                .sourceEncoder(StreamEncoder())
                .cacheDecoder(FileToStreamDecoder(SvgDecoder()))
                .decoder(SvgDecoder())
                .animate(android.R.anim.fade_in)
                .listener(SvgSoftwareLayerSetter())
                .listener(object : RequestListener<Uri, PictureDrawable> {
                    override fun onException(e: java.lang.Exception?, model: Uri?, target: Target<PictureDrawable>?, isFirstResource: Boolean): Boolean {
                        loadQrPng(qrUri = uri)
                        return false
                    }

                    override fun onResourceReady(resource: PictureDrawable?, model: Uri?, target: Target<PictureDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        Handler().postDelayed({
                            requestCaptureQrcode()
                        }, 1000)
                        return false
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(uri)
                .into(binding.ivQrcode)
    }

    private fun loadQrPng(qrUri: Uri?) {
        Glide.with(this)
                .load(qrUri)
                .listener(object : RequestListener<Uri, GlideDrawable> {
                    override fun onException(e: Exception?, model: Uri?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable?, model: Uri?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        Handler().postDelayed({
                            requestCaptureQrcode()
                        }, 1000)
                        return false
                    }
                })
                .into(binding.ivQrcode)
    }

    private fun showConfirmCancelDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.promptpay_title_confirm_cancel_payment)
        alertDialogBuilder.setMessage(R.string.promptpay_desc_confirm_cancel_payment)
        alertDialogBuilder.setPositiveButton(R.string.button_yes) { _, _ ->
            MainActivity.startintent(this)
            finishAffinity()
        }
        alertDialogBuilder.setNegativeButton(R.string.button_no) { _, _ ->
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    private fun showQrExpireDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(getString(R.string.promptpay_qrcode_expired))
        alertDialogBuilder.setPositiveButton(
                R.string.error_message_button_ok
        ) { _, _ ->
            MainActivity.startintent(this)
            finish()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    companion object {

        private const val EXTRA_APPOINTMENT = "EXTRA_APPOINTMENT"

        private const val SECOND: Long = 1_000
        private const val QR_CODE_EXPITE_TIME: Long = 15 * 60 * SECOND
        private const val CHECK_PAYMENT_STATUS_TIME: Long = 15 * 60 * SECOND
        private const val CHECK_PAYMENT_EVERY: Long = 20 * SECOND

        @JvmStatic
        fun startIntentForResult(context: FragmentActivity, uiModel: AppointmentUiModel) {
            Intent(context, PromptpayActivity::class.java)
                    .putExtra(EXTRA_APPOINTMENT, uiModel)
                    .run { context.startActivityForResult(this, AppConstants.REQUEST_CODE_AUTHORIZE_PAYMENT) }
        }
    }
}
