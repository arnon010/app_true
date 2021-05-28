package com.truedigital.vhealth.utils;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.truedigital.vhealth.R;

//https://stackoverflow.com/questions/3616676/how-to-draw-a-line-in-android
public class GridMatrixItemRelativeLayout extends RelativeLayout {

    private Boolean isDrawTop = true;
    private Boolean isDrawBottom = true;
    private Boolean isDrawLeft = true;
    private Boolean isDrawRight = true;

    private Context context;
    private Paint mPaint;
    private int borderColor = R.color.color_green;

    public GridMatrixItemRelativeLayout(Context context) {
        super(context);
        this.context = context;
        this.setBorder();
    }

    public GridMatrixItemRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setBorder();
    }

    public void setBorderColor(int color)
    {
        this.borderColor = color;
    }

    private void setBorder(){
        if( getBackground() == null)
        {
            int color = ContextCompat.getColor(context, R.color.transparent);
            this.setBackgroundColor(color);
        }
        mPaint = new Paint();
        int color = ContextCompat.getColor(context,  this.borderColor);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setDraw(boolean isDrawTop, boolean isDrawBottom, boolean isDrawLeft, boolean isDrawRight){
        this.isDrawTop = isDrawTop;
        this.isDrawBottom = isDrawBottom;
        this.isDrawLeft = isDrawLeft;
        this.isDrawRight = isDrawRight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isDrawTop)
        {
            canvas.drawLine(getPaddingRight(), 0, getWidth() - getPaddingLeft(), 0, mPaint );
        }

        if(isDrawBottom)
        {
            canvas.drawLine(getPaddingRight(), getHeight(), getWidth() - getPaddingLeft(), getHeight(), mPaint );
        }

        if(isDrawLeft)
        {
            canvas.drawLine(0, getPaddingTop(), 0, getHeight() - getPaddingBottom(), mPaint );
        }

        if(isDrawRight)
        {
            canvas.drawLine(getWidth(), getPaddingTop(), getWidth(), getHeight() - getPaddingBottom(), mPaint );
        }
    }


}