package com.truedigital.vhealth.utils;

import android.graphics.Paint;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by songkrit on 1/2/2018 AD.
 */

public class StringUtils {

    public static String setNewLine(String text) {
        return text.replace("\\n", System.getProperty("line.separator"));
    }

    /*
    public static String getMessageNewline(ArrayList<String> messages) {
        String result = "";

        for (int i = 0; i < messages.size(); i++) {
            if (i == messages.size() - 1) {
                result += "\u2022 " + messages.get(i);
            } else {
                result += "\u2022 " + messages.get(i) + "\n";
            }
        }

        return result;
    }
    */

    public static String getMessageNewline(ArrayList<String> messages) {
        String result = "";

        for (int i = 0; i < messages.size(); i++) {
            if (i == messages.size() - 1) {
                result += "" + messages.get(i);
            } else {
                result += "" + messages.get(i) + "\n";
            }
        }

        return result;
    }

    public static void setUnderline(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public static void setStrikeline(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
