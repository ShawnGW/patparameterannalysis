package com.vtest.it.patparameterannalysis.pojo;

import java.io.Serializable;

public class ParameterAfterCalculateResultBean implements Serializable {
    private static final long serialVersionUID=1l;
    private boolean flag;
    private double lowLimit;
    private double highLimit;
    private String unit;
    private double minValue;
    private double maxValue;
    private double average;
    private double middleValue;
    private double mostValue;
    private double standardDeviation;
    private double variance;
    private double valueOfQuarter;
    private double valueOfThreeQuarter;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public double getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(double lowLimit) {
        this.lowLimit = lowLimit;
    }

    public double getHighLimit() {
        return highLimit;
    }

    public void setHighLimit(double highLimit) {
        this.highLimit = highLimit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMiddleValue() {
        return middleValue;
    }

    public void setMiddleValue(double middleValue) {
        this.middleValue = middleValue;
    }

    public double getMostValue() {
        return mostValue;
    }

    public void setMostValue(double mostValue) {
        this.mostValue = mostValue;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double getValueOfQuarter() {
        return valueOfQuarter;
    }

    public void setValueOfQuarter(double valueOfQuarter) {
        this.valueOfQuarter = valueOfQuarter;
    }

    public double getValueOfThreeQuarter() {
        return valueOfThreeQuarter;
    }

    public void setValueOfThreeQuarter(double valueOfThreeQuarter) {
        this.valueOfThreeQuarter = valueOfThreeQuarter;
    }
}
