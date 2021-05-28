package com.truedigital.vhealth.utils;

import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

public class CustomChartRenderer extends LineChartRenderer {

    public CustomChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawValues(Canvas c) {
        if (isDrawingValuesAllowed(mChart)) {

            List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();

            for (int i = 0; i < dataSets.size(); i++) {

                ILineDataSet dataSet = dataSets.get(i);

                if (dataSet.getEntryCount() > 0) {

                    if (!shouldDrawValues(dataSet)) {
                        continue;
                    }

                    // apply the text-styling defined by the DataSet
                    applyValueTextStyle(dataSet);

                    Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

                    // make sure the values do not interfear with the circles
                    int valOffset = (int) (dataSet.getCircleRadius() * 1.75f);

                    if (!dataSet.isDrawCirclesEnabled())
                        valOffset = valOffset / 2;

                    mXBounds.set(mChart, dataSet);

                    float[] positions = trans.generateTransformedValuesLine(dataSet, mAnimator.getPhaseX(), mAnimator
                            .getPhaseY(), mXBounds.min, mXBounds.max);

                    MPPointF iconsOffset = MPPointF.getInstance(dataSet.getIconsOffset());
                    iconsOffset.x = com.github.mikephil.charting.utils.Utils.convertDpToPixel(iconsOffset.x);
                    iconsOffset.y = com.github.mikephil.charting.utils.Utils.convertDpToPixel(iconsOffset.y);

                    for (int j = 0; j < positions.length; j += 2) {

                        float x = positions[j];
                        float y = positions[j + 1];

                        if (!mViewPortHandler.isInBoundsRight(x))
                            break;

                        if (!mViewPortHandler.isInBoundsLeft(x) || !mViewPortHandler.isInBoundsY(y))
                            continue;

                        Entry entry = dataSet.getEntryForIndex(j / 2 + mXBounds.min);

                        int valOffsetTemp = valOffset;
                        if (entry.getY() == dataSet.getYMin()) {
                            valOffsetTemp = -(valOffsetTemp + 45);
                        }

                        if (dataSet.isDrawValuesEnabled()) {
                            customDrawValue(c, dataSet.getValueFormatter(), entry.getY(), entry, i, x,
                                    y - (valOffsetTemp), dataSet.getValueTextColor(j / 2));
                        }

                        if (entry.getIcon() != null && dataSet.isDrawIconsEnabled()) {

                            Drawable icon = entry.getIcon();

                            Utils.drawImage(
                                    c,
                                    icon,
                                    (int) (x + iconsOffset.x),
                                    (int) (y + iconsOffset.y),
                                    icon.getIntrinsicWidth(),
                                    icon.getIntrinsicHeight());
                        }
                    }

                    MPPointF.recycleInstance(iconsOffset);
                }
            }
        }

    }

    private void customDrawValue(Canvas c, IValueFormatter formatter, float value, Entry entry, int dataSetIndex, float x, float y, int color) {
        mValuePaint.setColor(color);
        mValuePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int coefficient = value >= 0 ? -1 : 1;

        String formattedValue = formatter.getFormattedValue(value, entry, dataSetIndex, mViewPortHandler);
        String[] split = formattedValue.split("\n");
        for (int i = 0; i < split.length; i++) {
            c.drawText(split[i], x, y - (coefficient * (split.length - (i * Utils.convertDpToPixel(9)))), mValuePaint);
        }
    }


}
