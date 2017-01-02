package com.dx168.chartdemo.self;

import android.graphics.Canvas;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by yunlong.su on 2016/12/26.
 */

public class MyXAxisRenderer extends XAxisRenderer {
    private final String TAG = getClass().getSimpleName();
    private final ViewPortHandler mViewPortHandler;
    private final LineChart mLineChart;
    protected MyXAxis mXAxis;

    public MyXAxisRenderer(ViewPortHandler viewPortHandler, MyXAxis xAxis, Transformer trans, LineChart lineChart) {
        super(viewPortHandler, xAxis, trans);
        mXAxis = xAxis;
        mViewPortHandler = viewPortHandler;
        this.mLineChart = lineChart;
    }

    @Override
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {
//        super.drawLabels(c, pos, anchor);

        Log.d(TAG, "drawLabels contentWidth: " + mViewPortHandler.contentWidth());
        float[] position = new float[]{
                0f, 0f
        };
        int count = mXAxis.getXLabels().size();


        for (int i = 0; i < count; i++) {
              /*获取label对应key值，也就是x轴坐标0,60,121,182,242*/
            int ix = mXAxis.getXLabels().keyAt(i);
            position[0] = ix;
            /*在图表中的x轴转为像素，方便绘制text*/
            mTrans.pointValuesToPixel(position);
            /*x轴越界*/
            String label = mXAxis.getXLabels().valueAt(i);
                /*文本长度*/
//            int labelWidth = Utils.calcTextWidth(mAxisLabelPaint, label);
//                /*右出界*/
//            if ((labelWidth / 2 + position[0]) > mViewPortHandler.contentRight()) {
//                position[0] = mViewPortHandler.contentRight() - labelWidth / 2;
//            } else if ((position[0] - labelWidth / 2) < mViewPortHandler.contentLeft()) {//左出界
//                position[0] = mViewPortHandler.contentLeft() + labelWidth / 2;
//            }
            int labelWidth = Utils.calcTextWidth(mAxisLabelPaint, label);
            if (i == 0) {
                //第一个为了与Y对其
                float xoffset = mLineChart.getAxisLeft().getXOffset();
                float posX = mViewPortHandler.offsetLeft() + xoffset;
                position[0] = posX + labelWidth / 2;
                c.drawText(label, position[0],
                        pos + Utils.convertPixelsToDp(mViewPortHandler.offsetBottom()),
                        mAxisLabelPaint);
            } else if (i == count - 1) {
                //最后一个，要与Y对其
                float xoffset = mLineChart.getAxisRight().getXOffset();
                float posX = mViewPortHandler.contentRight() + mViewPortHandler.offsetRight() + xoffset;
//                position[0] = posX - 4 * labelWidth / 2;
//                position[0] = mViewPortHandler.contentRight() - labelWidth / 2 - mViewPortHandler.offsetRight();
                position[0] = mXAxis.mEntries[mXAxis.mEntries.length - 1];
                mTrans.pointValuesToPixel(position);
                position[0] = position[0] - labelWidth ;
                c.drawText(label, position[0],
                        pos + Utils.convertPixelsToDp(mViewPortHandler.offsetBottom()),
                        mAxisLabelPaint);
            } else if (i == count / 2) {
//                position[0] = mViewPortHandler.contentWidth() / 2 + labelWidth / 2- mViewPortHandler.offsetLeft()-mXAxis.getXOffset();
                float entry = mXAxis.mEntries[mXAxis.mEntries.length / 2];
                position[0] = entry;
                mTrans.pointValuesToPixel(position);
                c.drawText(label, position[0],
                        pos + Utils.convertPixelsToDp(mViewPortHandler.offsetBottom()),
                        mAxisLabelPaint);
            } else {
                c.drawText(label, position[0],
                        pos + Utils.convertPixelsToDp(mViewPortHandler.offsetBottom()),
                        mAxisLabelPaint);
            }


        }

    }
}
