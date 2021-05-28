package com.truedigital.vhealth.utils;

import android.content.Context;
import android.widget.TextView;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ChartValueObject;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomChartMarkerView extends MarkerView {

    private TextView tvContent;

    public CustomChartMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        ChartValueObject data = (ChartValueObject) e.getData();
        String desc = "";
        if (data.getDescription() != null && !data.getDescription().equals("")) {
            desc = data.getDescription() + " ";
        }

        tvContent.setText(desc + "" + (int) e.getY());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(0, -getHeight() / 2);
    }

}
