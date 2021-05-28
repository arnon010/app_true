package com.truedigital.vhealth.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.truedigital.vhealth.R
import com.truedigital.vhealth.ui.main.MainActivity

abstract class BaseMvpBindingFragment<P : BaseMvpInterface.Presenter<*>> : BaseMvpFragment<P>() {

    abstract val binding: ViewBinding

    protected val mainActivity: MainActivity?
        get() = activity as? MainActivity

    override fun getLayoutView(): Int {
        return R.layout.layout_empty_view
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = binding
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun bindView(view: View?) {}

    override fun setupInstance() {}

    override fun initialize() {}

}
