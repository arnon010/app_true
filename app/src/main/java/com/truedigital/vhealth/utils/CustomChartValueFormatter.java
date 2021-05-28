package com.truedigital.vhealth.utils;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

public class CustomChartValueFormatter implements IValueFormatter {

    private boolean isRenderMinSuccess = false;
    private boolean isRenderMaxSuccess = false;
    private float valueMin = -1;
    private float valueMax = -1;
    private String minDescription = "";
    private String maxDescription = "";
    private ArrayList<Entry> entryList = new ArrayList<>();

    public CustomChartValueFormatter() {
    }

    public CustomChartValueFormatter(float valueMin, float valueMax) {
        this.valueMin = valueMin;
        this.valueMax = valueMax;
    }

    public CustomChartValueFormatter(float valueMin, float valueMax, String minDescription, String maxDescription) {
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.minDescription = "\n" + minDescription;
        this.maxDescription = "\n" + maxDescription;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        if (this.entryList.contains(entry)) {
            this.entryList.clear();
            this.entryList.add(entry);
            this.isRenderMinSuccess = false;
            this.isRenderMaxSuccess = false;
        } else {
            this.entryList.add(entry);
        }

        String valueDisplay = "";
        if (this.valueMin != -1 && this.valueMax != -1) {
            if (value == this.valueMin && !this.isRenderMinSuccess) {
                valueDisplay = (int) value + this.minDescription;
                this.isRenderMinSuccess = true;
            } else if (value == this.valueMax && !this.isRenderMaxSuccess) {
                valueDisplay = (int) value + this.maxDescription;
                this.isRenderMaxSuccess = true;
            }
        }

        return valueDisplay;
    }

}