package com.truedigital.vhealth.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songkrit on 1/2/2018 AD.
 */

public class ValidUtils {

    private static final int PASSWORD_LENGTH = 6;
    private static final int TELEPHONE_LENGTH = 9;

    private ValidUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isEmptyValid(String text) {
        if (text.isEmpty() || text == null) {
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isEqual(String strSource, String strCompare) {
        return strSource.equalsIgnoreCase(strCompare);
    }

    public static boolean isPasswordValid(String password) {
        if (password.isEmpty() || password.length() < PASSWORD_LENGTH) {
            return false;
        }
        return true;
    }

    public static boolean isTelephoneValid(String telephoneNumber) {
        if (telephoneNumber.isEmpty() || telephoneNumber.length() < TELEPHONE_LENGTH) {
            return false;
        }
        return true;
    }
}
