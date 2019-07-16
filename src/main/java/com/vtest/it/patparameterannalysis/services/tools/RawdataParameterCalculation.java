package com.vtest.it.patparameterannalysis.services.tools;

import com.vtest.it.patparameterannalysis.pojo.ParameterAfterCalculateResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawdataParameterCalculation implements RawdataParameterCalculationInterface {
    @Autowired
    private QuickSort quickSort;
    @Override
    public void calculate(List<Double> list, ParameterAfterCalculateResultBean parameterAfterCalculateResultBean) {
        double minValue = list.get(0);
        double maxValue = list.get(0);
        double average;
        double middleValue;
        double mostValue;
        double standardDeviation;
        double variance = 0;
        double valueOfQuarter;
        double valueOfThreeQuarter;
        Double sum = 0d;
        int count = 0;
        Double temp = null;
        boolean flag = false;
        for (Double value : list) {
            maxValue = maxValue < value ? value : maxValue;
            minValue = minValue > value ? value : minValue;
            sum += value;
            if (count == 0) {
                temp = value;
                count++;
            } else {
                if (temp == value) {
                    count++;
                } else {
                    count--;
                }
            }
        }
        average = sum / list.size();
        mostValue = temp;
        for (Double value : list) {
            variance += Math.pow((value - average), 2);
        }
        variance /= list.size();
        standardDeviation = Math.sqrt(variance);
        Double[] values = list.toArray(new Double[list.size()]);
        quickSort.sort(values, 0, values.length - 1);
        middleValue = values.length % 2 != 0 ? values[values.length / 2] : (double) (values[values.length / 2 - 1] + values[values.length / 2]) / 2;
        valueOfQuarter = (values.length % 2 == 0) ? ((values.length % 4 == 0) ? ((double) (values[(values.length / 2) / 2 - 1] + values[(values.length / 2) / 2]) / 2) : ((values[values.length / 4]))) : (((values.length / 2) % 2 == 0) ? (valueOfQuarter = values[values.length / 4]) : ((double) (values[values.length / 4] + values[values.length / 4 + 1]) / 2));
        valueOfThreeQuarter = (values.length % 2 == 0) ? ((values.length % 4 == 0) ? ((double) (values[(values.length / 2) / 2 - 1 + (values.length / 2)] + values[(values.length / 2) / 2 + (values.length / 2)]) / 2) : (values[3 * (values.length / 4) + 1])) : (((values.length / 2) % 2 == 0) ? (values[3 * (values.length / 4)]) : ((double) (values[3 * (values.length / 4) + 1] + values[3 * (values.length / 4) + 2]) / 2));
        parameterAfterCalculateResultBean.setAverage(average);
        parameterAfterCalculateResultBean.setMaxValue(maxValue);
        parameterAfterCalculateResultBean.setMiddleValue(middleValue);
        parameterAfterCalculateResultBean.setMinValue(minValue);
        parameterAfterCalculateResultBean.setStandardDeviation(standardDeviation);
        parameterAfterCalculateResultBean.setVariance(variance);
        parameterAfterCalculateResultBean.setMostValue(mostValue);
        parameterAfterCalculateResultBean.setValueOfQuarter(valueOfQuarter);
        parameterAfterCalculateResultBean.setValueOfThreeQuarter(valueOfThreeQuarter);
    }

}
