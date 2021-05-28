package com.truedigital.vhealth.utils;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    private boolean enabledSwipe = true;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabledSwipe) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabledSwipe) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public void setSwipeEnabled(boolean enabled) {
        this.enabledSwipe = enabled;
    }
}