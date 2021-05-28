package com.truedigital.vhealth.ui.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.truedigital.vhealth.R

abstract class BaseMvpBindingActivity<P : BaseMvpInterface.Presenter<*>> : BaseMvpActivity<P>() {

    abstract val binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = binding
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutView(): Int {
        return R.layout.layout_empty_view
    }

    override fun bindView() {}

    override fun initInstance() {}

    override fun initialize() {}
}
