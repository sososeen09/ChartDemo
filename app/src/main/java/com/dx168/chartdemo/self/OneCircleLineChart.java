package com.dx168.chartdemo.self;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

/**
 * Created by yunlong.su on 2016/12/22.
 */

public class OneCircleLineChart extends LineChart {
    public OneCircleLineChart(Context context) {
        super(context);
    }

    public OneCircleLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OneCircleLineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mXAxis = new MyXAxis();
        mXAxisRenderer = new MyXAxisRenderer(mViewPortHandler, ((MyXAxis) mXAxis), mLeftAxisTransformer,this);
        mRenderer = new OneCircleLineChartRenderer(this, mAnimator, mViewPortHandler);
    }

    @Override
    public MyXAxis getXAxis() {
        return (MyXAxis) mXAxis;
    }

    @Override
    public LineData getLineData() {
        return mData;
    }

    @Override
    protected void onDetachedFromWindow() {
        // releases the bitmap in the renderer to avoid oom error
        if (mRenderer != null && mRenderer instanceof OneCircleLineChartRenderer) {
            ((OneCircleLineChartRenderer) mRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void drawMarkers(Canvas canvas) {
        super.drawMarkers(canvas);

    }
}
