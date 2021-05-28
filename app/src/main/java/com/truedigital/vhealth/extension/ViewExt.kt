package com.truedigital.vhealth.extension

import android.os.SystemClock
import android.view.View
import com.truedigital.vhealth.extension.Time.defaultInterval
import com.truedigital.vhealth.extension.Time.lastTimeClicked

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View.onSingleClick(delay: Int = defaultInterval, block: (View) -> Unit) {
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastTimeClicked > defaultInterval) {
            lastTimeClicked = SystemClock.elapsedRealtime()
            block(it)
        }
    }
}

object Time {
    const val defaultInterval = 750
    var lastTimeClicked: Long = 0
}
