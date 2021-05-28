package com.truedigital.vhealth.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.truedigital.vhealth.R;

public class SpannableText {

    public static Spannable setSpan(Context context, int text, int textIndex) {
        return setSpan(context, text, textIndex, R.color.color_green);
    }

    public static Spannable setSpan(Context context, String text, String textIndex) {
        return setSpan(context, text, textIndex, R.color.color_green);
    }

    public static Spannable setSpan(Context context, String text, String textIndex, int resColor) {

        Spannable wordtoSpan = new SpannableString(text);

        int start = text.indexOf(textIndex);
        int end = start + textIndex.length();
        // index
        if (start > 0) {
            wordtoSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(resColor))
                    , start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return wordtoSpan;
    }

    public static Spannable setSpan(Context context, int text, int textIndex, int resColor) {
        String textStr = context.getResources().getString(text);
        Spannable wordtoSpan = new SpannableString(textStr);

        if (textIndex != 0) {
            String textIndexStr = context.getResources().getString(textIndex);

            int start = textStr.indexOf(textIndexStr);
            int end = start + textIndexStr.length();
            // index
            if (start > 0) {
                wordtoSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(resColor))
                        , start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
        } else {
            wordtoSpan.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_white))
                    , 0, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return wordtoSpan;
    }

    public static Spannable setClickableSpan(final Context context, int text, int textIndex, final OnClickListener onClickListener) {
        return setClickableSpan(context, text, textIndex, R.color.color_green, onClickListener);
    }

    public static Spannable setClickableSpan(final Context context, int text, int textIndex, final int restColor, final OnClickListener onClickListener) {

        String textStr = context.getString(text);
        String textIndexStr = context.getString(textIndex);

        SpannableString wordtoSpan = new SpannableString(textStr);

        int start = textStr.indexOf(textIndexStr);
        int end = start + textIndexStr.length();
        int color = ContextCompat.getColor(context, restColor);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (onClickListener == null) {
                    return;
                }
                onClickListener.onClick(textView);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.linkColor = color;
                ds.setUnderlineText(true);
            }
        };
        // index
        if (start >= 0) {
            wordtoSpan.setSpan(clickableSpan, start, end, Spannable.SPAN_PRIORITY);
            wordtoSpan.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_PRIORITY);
        }

        return wordtoSpan;
    }

    public static Spannable setClickableSpan(
            final Context context,
            int textOriginalResId,
            int textFirstResId,
            int textSecondResId,
            final int colorResId,
            final OnClickListener onClickListenerFirst,
            final OnClickListener onClickListenerSecond
    ) {

        String textOriginal = context.getString(textOriginalResId);
        String textFirst = context.getString(textFirstResId);
        String textSecoond = context.getString(textSecondResId);

        SpannableString spannableString = new SpannableString(textOriginal);

        int firstStart = textOriginal.indexOf(textFirst);
        int firstEnd = firstStart + textFirst.length();
        int secondStart = textOriginal.indexOf(textSecoond);
        int secondEnd = secondStart + textSecoond.length();
        int color = ContextCompat.getColor(context, colorResId);

        ClickableSpan clickableSpanFirst = createClickableSpan(onClickListenerFirst, color);
        ClickableSpan clickableSpanSecond = createClickableSpan(onClickListenerSecond, color);

        if (firstStart > 0) {
            spannableString.setSpan(clickableSpanFirst, firstStart, firstEnd, Spannable.SPAN_PRIORITY);
            spannableString.setSpan(new ForegroundColorSpan(color), firstStart, firstEnd, Spannable.SPAN_PRIORITY);
        }

        if (secondStart > 0) {
            spannableString.setSpan(clickableSpanSecond, secondStart, secondEnd, Spannable.SPAN_PRIORITY);
            spannableString.setSpan(new ForegroundColorSpan(color), secondStart, secondEnd, Spannable.SPAN_PRIORITY);
        }

        return spannableString;
    }

    private static ClickableSpan createClickableSpan(final OnClickListener onClickListener, int color) {
        return new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                if (onClickListener == null) {
                    return;
                }
                onClickListener.onClick(textView);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.linkColor = color;
                ds.setUnderlineText(true);
            }
        };
    }

    public interface OnClickListener {
        void onClick(View textView);
    }
}
