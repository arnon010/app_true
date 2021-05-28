package com.truedigital.vhealth.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.truedigital.vhealth.R
import com.truedigital.vhealth.databinding.WidgetSettingCheckBinding
import com.truedigital.vhealth.extension.gone
import com.truedigital.vhealth.extension.onSingleClick
import com.truedigital.vhealth.extension.visible

class SettingCheckView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        WidgetSettingCheckBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var onItemClicked: () -> Unit = {}

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingCheckView)
        with(typedArray) {
            setName(getString(R.styleable.SettingCheckView_android_text))
            setChecked(getBoolean(R.styleable.SettingCheckView_android_checked, false))
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

    fun setChecked(checked: Boolean) = apply {
        if (checked) {
            binding.ivCheck.visible()
        } else {
            binding.ivCheck.gone()
        }
    }
}
