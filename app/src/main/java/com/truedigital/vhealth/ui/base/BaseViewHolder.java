package com.truedigital.vhealth.ui.base;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by songkrit on 6/24/2018 AD.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder{

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    private int mCurrentPosition;

    protected abstract void clear();

    public void onBind(int position) {
        mCurrentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }
}
