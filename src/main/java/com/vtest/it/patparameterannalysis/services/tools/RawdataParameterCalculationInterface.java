package com.vtest.it.patparameterannalysis.services.tools;


import com.vtest.it.patparameterannalysis.pojo.ParameterAfterCalculateResultBean;

import java.util.List;

public interface RawdataParameterCalculationInterface {
    public void calculate(List<Double> list, ParameterAfterCalculateResultBean parameterAfterCalculateResultBean);
}
