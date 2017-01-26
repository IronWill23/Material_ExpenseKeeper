package com.library.ironwill.expensekeeper.model;

/**
 * 作者：chs on 2016/9/6 15:14
 * 邮箱：657083984@qq.com
 */
public class BarModel {
    private String xLabel;
    private Float yValue;

    public BarModel(String xLabel, Float yValue) {
        this.xLabel = xLabel;
        this.yValue = yValue;
    }

    public String getxLabel() {
        return xLabel;
    }

    public void setxLabel(String xLabel) {
        this.xLabel = xLabel;
    }

    public BarModel(Float yValue) {
        this.yValue = yValue;
    }

    public Float getyValue() {
        return yValue;
    }

    public void setyValue(Float yValue) {
        this.yValue = yValue;
    }
}
