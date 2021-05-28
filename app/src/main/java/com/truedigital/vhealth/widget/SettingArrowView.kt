package com.truedigital.vhealth.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.WidgetSettingArrowBinding
import com.truedigital.vhealth.extension.onSingleClick

class SettingArrowView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        WidgetSettingArrowBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var onItemClicked: () -> Unit = {}

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingArrowView)
        with(typedArray) {
            setName(getString(R.styleable.SettingArrowView_android_text))
            setEnable(getBoolean(R.styleable.SettingArrowView_android_enabled, true))
        }
        typedArray.recycle()

        binding.root.onSingleClick {
            onItemClicked.invoke()
        }
    }

    fun onItemClicked(block: () -> Unit) = apply {
        onItemClicked = block
    }

    fun setName(value: String?) = apply {
        binding.tvName.text = value
    }

    fun setEnable(enable: Boolean) = apply {
        binding.root.isEnabled = enable
        if (enable) {
            binding.ltyContent.setBackgroundResource(R.color.white)
        } else {
            binding.ltyContent.setBackgroundResource(R.color.color_gray_light)
        }
    }
}
