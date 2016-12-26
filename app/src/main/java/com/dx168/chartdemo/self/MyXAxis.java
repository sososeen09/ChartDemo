package com.dx168.chartdemo.self;

import android.util.SparseArray;

import com.github.mikephil.charting.components.XAxis;

/**
 * Created by yunlong.su on 2016/12/26.
 */

public class MyXAxis extends XAxis {
    private SparseArray<String> mLabels;

    public SparseArray<String> getXLabels() {
        return mLabels;
    }


    public void setXLabels(SparseArray<String> labels) {
        this.mLabels = labels;
    }
}
