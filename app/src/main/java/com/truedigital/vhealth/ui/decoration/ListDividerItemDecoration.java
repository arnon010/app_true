package com.truedigital.vhealth.ui.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.truedigital.vhealth.R;

/**
 * Created by warinthorn_s on 5/22/2018.
 */

public class ListDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private boolean drawTopBottom;

    public ListDividerItemDecoration(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        this.drawTopBottom = true;
    }

    public ListDividerItemDecoration(Context context, int layout_line, boolean drawTopBottom) {
        mDivider = ContextCompat.getDrawable(context, layout_line);
        this.drawTopBottom = drawTopBottom;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        if(drawTopBottom && childCount > 0)
        {
            int start = parent.getChildAt(0).getTop();
            int endStart = start + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, start, right, endStart);
            mDivider.draw(c);

            int startEnd = parent.getChildAt(childCount - 1).getBottom();
            int end = startEnd + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, startEnd, right, end);
            mDivider.draw(c);
        }

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
