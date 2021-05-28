package com.truedigital.vhealth.extension

private val PHONE_INPUT = "(\\d{3})(\\d{3})(\\d+)".toRegex()
private const val PHONE_OUTPUT = "$1-$2-$3"

fun String.toPhoneFormat(): String {
    return this.replace(PHONE_INPUT, PHONE_OUTPUT)
}
