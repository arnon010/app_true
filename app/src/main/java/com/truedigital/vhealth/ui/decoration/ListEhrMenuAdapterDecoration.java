package com.truedigital.vhealth.ui.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by warinthorn_s on 5/22/2018.
 */

public class ListEhrMenuAdapterDecoration extends RecyclerView.ItemDecoration  {

    private int spanCount;
    private int spacing;

    private Drawable mDivider;
    private int mInsets;

    public ListEhrMenuAdapterDecoration(Context context, int spanCount, int spacing, int line_divider) {
        this.spanCount = spanCount;
        this.spacing = spacing;

        mDivider = ContextCompat.getDrawable(context, line_divider);

        mInsets = 4;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        if (parent.getChildCount() == 0) return;

        int childCount = parent.getChildCount();

        if(childCount % 2 != 0)
        {
            childCount += 1;
        }

        for (int i = 0; i < childCount; i++) {
            if(i + 1 <= childCount - spanCount){
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                final int left = child.getLeft() + params.leftMargin ;
                final int right = child.getRight() - params.rightMargin ;
                final int top = child.getBottom() + params.bottomMargin + mInsets;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    /** Draw dividers to the right of each child view */
    public void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();

        if(childCount % 2 != 0)
        {
            childCount += 1;
        }

        for (int i = 0; i < childCount; i++) {
            if(i % 2 == 0)
            {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                final int left = child.getRight() + params.rightMargin + mInsets;
                final int right = left + mDivider.getIntrinsicWidth();
                final int top = child.getTop() + params.topMargin ;
                final int bottom = child.getBottom() - params.bottomMargin ;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position

        if (position >= 0) {
            int column = position % spanCount; // item column

            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            if (position < spanCount) { // top
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // bottom

        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
        }
    }
}
