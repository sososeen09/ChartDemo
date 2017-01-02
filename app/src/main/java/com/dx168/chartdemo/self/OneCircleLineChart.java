package com.dx168.chartdemo.self;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.Utils;

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
        //调整MarkView的Y距离
//        super.drawMarkers(canvas);
        // if there is no marker view or drawing marker is disabled
        if (mMarker == null || !isDrawMarkersEnabled() || !valuesToHighlight())
            return;

        for (int i = 0; i < mIndicesToHighlight.length; i++) {

            Highlight highlight = mIndicesToHighlight[i];

            IDataSet set = mData.getDataSetByIndex(highlight.getDataSetIndex());

            Entry e = mData.getEntryForHighlight(mIndicesToHighlight[i]);
            int entryIndex = set.getEntryIndex(e);

            // make sure entry not null
            if (e == null || entryIndex > set.getEntryCount() * mAnimator.getPhaseX())
                continue;

            float[] pos = getMarkerPosition(highlight);

            // check bounds
            if (!mViewPortHandler.isInBounds(pos[0], pos[1]))
                continue;

            // callbacks to update the content
            mMarker.refreshContent(e, highlight);

            // draw the marker
            mMarker.draw(canvas, pos[0], pos[1]+ Utils.convertDpToPixel(-5f));
        }
    }
}
