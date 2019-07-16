package com.vtest.it.patparameterannalysis.pojo;

import java.io.Serializable;

public class ParameterJudgementStandardBean implements Serializable {
    private static final long serialVersionUID=1l;
    private double lowLimit;
    private double highLimit;

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
}
