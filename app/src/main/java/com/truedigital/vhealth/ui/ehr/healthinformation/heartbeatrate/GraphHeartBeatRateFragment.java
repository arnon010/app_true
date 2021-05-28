package com.truedigital.vhealth.ui.ehr.healthinformation.heartbeatrate;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.truedigital.vhealth.R;
import com.truedigital.vhealth.model.ChartDataObject;
import com.truedigital.vhealth.model.ChartObject;
import com.truedigital.vhealth.model.ChartValueObject;
import com.truedigital.vhealth.model.GraphHeartBeatRateCriteriaObject;
import com.truedigital.vhealth.ui.base.BaseMvpFragment;
import com.truedigital.vhealth.ui.main.MainActivity;
import com.truedigital.vhealth.utils.AppConstants;
import com.truedigital.vhealth.utils.CustomChartValueFormatter;
import com.truedigital.vhealth.utils.CustomChartRenderer;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GraphHeartBeatRateFragment extends BaseMvpFragment<GraphHeartBeatRateFragmentInterface.Presenter>
        implements GraphHeartBeatRateFragmentInterface.View {

    private LinearLayout imgPrevious;
    private TextView txtToDay;
    private LinearLayout imgNext;
    private LineChart lineChart;
    private float defaultXScale = 1.5f;

    private ChartObject chart;
    private GraphHeartBeatRateCriteriaObject criteria;
    private GraphHeartBeatRateFragment.OnLoadListener onLoadListener;

    public interface OnLoadListener {
        void onLoadComplete();
    }

    public void setOnLoadListener(GraphHeartBeatRateFragment.OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public GraphHeartBeatRateFragment() {
        super();
    }

    public static GraphHeartBeatRateFragment newInstance(GraphHeartBeatRateCriteriaObject criteria) {
        GraphHeartBeatRateFragment fragment = new GraphHeartBeatRateFragment();
        fragment.criteria = criteria;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public GraphHeartBeatRateFragmentInterface.Presenter createPresenter() {
        return GraphHeartBeatRateFragmentPresenter.create();
    }

    @Override
    public int getLayoutView() {
        return R.layout.fragment_graph_heart_beat_rate;
    }

    @Override
    public void bindView(View view) {
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        imgPrevious = (LinearLayout) view.findViewById(R.id.imgPrevious);
        txtToDay = (TextView) view.findViewById(R.id.txtToDay);
        imgNext = (LinearLayout) view.findViewById(R.id.imgNext);
    }

    @Override
    public void setupInstance() {

    }

    @Override
    public void setupView() {
        ((MainActivity) getActivity()).setMenuEhrSelected();

        if (criteria.getPeriod() == 0) {
            imgNext.setVisibility(View.INVISIBLE);
        }
        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int period = criteria.getPeriod() - 1;
                if (period < 0) {
                    imgNext.setVisibility(View.VISIBLE);
                }
                criteria.setPeriod(period);
                getPresenter().getGraphHeartBeatRate(criteria);
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int period = criteria.getPeriod() + 1;
                if (period <= 0) {
                    if (period == 0) {
                        imgNext.setVisibility(View.INVISIBLE);
                    }
                    criteria.setPeriod(period);
                    getPresenter().getGraphHeartBeatRate(criteria);
                }
            }
        });
    }

    @Override
    public void initialize() {
        if (criteria != null && criteria.getIsCanLoad()) {
            this.searchGraphHeartBeatRate();
        }
    }

    @Override
    public void restoreView(Bundle savedInstanceState) {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void setGraphHeartBeatRate(ChartObject chart) {
        this.chart = chart;
        this.txtToDay.setText(chart.getShowDate());
        this.setupChart();
        this.setDataChart();
        this.onLoadListener.onLoadComplete();
    }

    public void searchGraphHeartBeatRate() {
        getPresenter().getGraphHeartBeatRate(criteria);
    }

    private void setupChart() {
        lineChart.clear();
        lineChart.setNoDataText(getResources().getString(R.string.data_not_found));
        lineChart.setNoDataTextColor(getActivity().getResources().getColor(R.color.color_green));
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        if (criteria.getType() == 0) {
            this.defaultXScale = 2;
        }
        lineChart.setScaleMinima(this.defaultXScale, 1);
        lineChart.setPinchZoom(false);
        lineChart.getAxisLeft().setEnabled(false);
//        comment by warinthorn_s 2018-09-06 because requirement not use
//        CustomChartMarkerView mv = new CustomChartMarkerView(getActivity(), R.layout.chart_custom_marker_view_layout);
//        mv.setChartView(lineChart);
//        lineChart.setMarker(mv);
        lineChart.setRenderer(new CustomChartRenderer(lineChart, lineChart.getAnimator(), lineChart.getViewPortHandler()));

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(chart.getXLabel().size());
        xAxis.setLabelRotationAngle(45);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum((float) -0.5);
        xAxis.setAxisMaximum((float) ((chart.getXLabel().size() - 1) + 0.5));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value % chart.getXLabel().size();
                return chart.getXLabel().get(index);
            }
        });

        Legend l = lineChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
        l.setWordWrapEnabled(true);
    }

    private void setDataChart() {
        lineChart.clear();

        ArrayList<ILineDataSet> lineDataSetList = new ArrayList();
        ArrayList<ChartValueObject> valueY = new ArrayList<>();

        if (chart.getDataList().size() > 0) {

            int i = 0;
            for (ChartDataObject data : chart.getDataList()) {
                i++;

                ArrayList<Entry> entries = new ArrayList<Entry>();

                for (ChartValueObject value : data.getValueList()) {
                    entries.add(new Entry(data.getAxisX(), value.getChartValue(), value));
                    valueY.add(value);
                }

                LineDataSet lineSet = new LineDataSet(entries, "DataSet " + i);
                int color =  getActivity().getResources().getColor(R.color.color_green);
                if (data.getType().equals(AppConstants.HEART_RATE)) {
                    color = getActivity().getResources().getColor(R.color.color_green);
                } else if (data.getType().equals(AppConstants.RESTING_RATE)) {
                    color = getActivity().getResources().getColor(R.color.orange_light);
                }

                lineSet.setHighLightColor(color);
                lineSet.setColor(color);
                lineSet.setLineWidth(2.5f);
                lineSet.setCircleColor(color);
                lineSet.setCircleColorHole(color);
                lineSet.setCircleRadius(3f);
                lineSet.setFillColor(color);
                lineSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                lineSet.setValueTextSize(10f);
                lineSet.setValueTextColor(color);
                lineSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                lineDataSetList.add(lineSet);
            }

            ChartValueObject minY = new ChartValueObject();
            ChartValueObject maxY = new ChartValueObject();
            if (valueY.size() > 0) {
                minY = Collections.min(valueY, new Comparator<ChartValueObject>() {
                    @Override
                    public int compare(ChartValueObject first, ChartValueObject second) {
                        if (first.getChartValue() > second.getChartValue())
                            return 1;
                        else if (first.getChartValue() < second.getChartValue())
                            return -1;
                        return 0;
                    }
                });
                maxY = Collections.max(valueY, new Comparator<ChartValueObject>() {
                    @Override
                    public int compare(ChartValueObject first, ChartValueObject second) {
                        if (first.getChartValue() > second.getChartValue())
                            return 1;
                        else if (first.getChartValue() < second.getChartValue())
                            return -1;
                        return 0;
                    }
                });
            }

            LineData linedata = new LineData(lineDataSetList);
            Typeface typeface = ResourcesCompat.getFont(requireContext(), R.font.true_light);
            linedata.setValueTypeface(typeface);
            linedata.setValueFormatter(new CustomChartValueFormatter(minY.getChartValue(), maxY.getChartValue(), getResources().getString(R.string.min), getResources().getString(R.string.max)));

            lineChart.setData(linedata);
        }

        lineChart.invalidate();
    }
}
