package com.dx168.chartdemo;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.dx168.chartdemo.api.ConstantTest;
import com.dx168.chartdemo.bean.DataParse;
import com.dx168.chartdemo.bean.MinutesBean;
import com.dx168.chartdemo.self.MyXAxis;
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
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Demo2Activity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private LineChart mChart;
    private DataParse mData;
    private MyXAxis xAxisLine;
    private YAxis axisLeftLine;
    private YAxis axisRightLine;
    private LineDataSet d1;
    private SparseArray<String> stringSparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        mChart = (LineChart) findViewById(R.id.chart2);

        initChart();
        stringSparseArray = setXLabels();
        setShowLabels(stringSparseArray);
        getOffLineData();
    }

    private void setShowLabels(SparseArray<String> stringSparseArray) {
        xAxisLine.setXLabels(stringSparseArray);
    }


    private void initChart() {
        mChart.setBackgroundColor(Color.parseColor("#666666"));

        mChart.setScaleEnabled(false);
        mChart.setDrawBorders(false);
        mChart.setBorderWidth(1);
        mChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        mChart.setDescription(null);
        Legend lineChartLegend = mChart.getLegend();
        //不显示图标下面的数据标识
        lineChartLegend.setEnabled(false);


        //x轴
        xAxisLine = (MyXAxis) mChart.getXAxis();
        xAxisLine.setDrawLabels(true);
        xAxisLine.setDrawGridLines(false);
        xAxisLine.setDrawAxisLine(true);
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxisLine.setLabelsToSkip(59);


        //左边y
        axisLeftLine = mChart.getAxisLeft();
        /*折线图y轴左没有basevalue，调用系统的*/
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawGridLines(false);
        /*轴不显示 避免和border冲突*/
        axisLeftLine.setDrawAxisLine(true);
        axisLeftLine.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        axisLeftLine.setCenterAxisLabels(true);
        axisLeftLine.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat mFormat = new DecimalFormat("#0.000");
                return mFormat.format(value);
            }
        });

        //右边y
        axisRightLine = mChart.getAxisRight();
        axisRightLine.setLabelCount(2, true);
        axisRightLine.setDrawLabels(true);
        axisRightLine.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00%");
                return mFormat.format(value);
            }
        });

        axisRightLine.setStartAtZero(false);
        axisRightLine.setDrawGridLines(false);
        axisRightLine.setDrawAxisLine(false);
        //背景线
        xAxisLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisRightLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        axisRightLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));


        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mv.setShowOriginalValue(true);
        mChart.setMarker(mv); // Set the marker to the chart

    }

    private void getOffLineData() {
      /*方便测试，加入假数据*/
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.MINUTESURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseMinutes(object);
        setData();


    }

    private SparseArray<String> setXLabels() {
        SparseArray<String> xLabels = new SparseArray<>();
        xLabels.put(0, "09:30");
        xLabels.put(60, "10:30");
        xLabels.put(121, "11:30/13:00");
        xLabels.put(182, "14:00");
        xLabels.put(241, "15:00");
        return xLabels;
    }

    private void setData() {
        //设置y左右两轴最大最小值
        axisLeftLine.setAxisMaximum(mData.getMax());
        axisLeftLine.setAxisMinimum(mData.getMin());
        axisRightLine.setAxisMaximum(mData.getPercentMax());
        axisRightLine.setAxisMinimum(mData.getPercentMin());

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++) {
           /* //避免数据重复，skip也能正常显示
            if (mData.getDatas().get(i).time.equals("13:30")) {
                continue;
            }*/
            MinutesBean t = mData.getDatas().get(j);
            values.add(new Entry(i, t.cjprice));
        }

        d1 = new LineDataSet(values, "成交价");
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);

        d1.enableDashedLine(10f, 5f, 0f);
        d1.enableDashedHighlightLine(10f, 5f, 0f);
        d1.setColor(Color.YELLOW);
        d1.setCircleColor(Color.BLACK);
        d1.setLineWidth(1f);
        d1.setCircleRadius(3f);
        //取消折线
        d1.disableDashedLine();
        d1.setCircleHoleRadius(1.5f);
        //圆圈是空的
        d1.setDrawCircleHole(true);
        d1.setHighlightEnabled(true);
        d1.setDrawCircles(true);
        d1.setValueTextSize(9f);
        d1.setDrawFilled(false);
        d1.setDrawValues(false);
        d1.setFormLineWidth(1f);
        d1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        d1.setFormSize(15.f);
        d1.setHighlightEnabled(true);
        d1.setDrawHighlightIndicators(true);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            d1.setFillDrawable(drawable);
        } else {
            d1.setFillColor(Color.BLACK);
        }
        d1.setDrawCircles(true);

        d1.setDrawCircleHole(true);
        d1.setHighlightEnabled(true);
        d1.setValueTextSize(9f);
        d1.setDrawFilled(false);
        d1.setDrawValues(false);
        d1.setFormLineWidth(1f);


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(d1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        //一开始就绘制圆形

        // set data
        mChart.setData(data);

        mChart.invalidate();

        mChart.animateX(2500);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

    }


}
