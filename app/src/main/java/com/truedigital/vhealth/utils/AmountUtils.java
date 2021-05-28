package com.truedigital.vhealth.utils;

import java.text.DecimalFormat;

public class AmountUtils {

    static public String decimalFormat(double value) {
        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        return formatter.format(value);
    }
}
