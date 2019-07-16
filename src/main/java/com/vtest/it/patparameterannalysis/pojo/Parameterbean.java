package com.vtest.it.patparameterannalysis.pojo;

import java.io.Serializable;

public class Parameterbean implements Serializable {
    private static final long serialVersionUID=1l;
    private double lowLimit;
    private double highLimit;
    private String unit;

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
}
