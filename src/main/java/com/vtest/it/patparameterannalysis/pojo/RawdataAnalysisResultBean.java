package com.vtest.it.patparameterannalysis.pojo;

import java.io.Serializable;
import java.util.*;

public class RawdataAnalysisResultBean implements Serializable {
    private static final long serialVersionUID=1l;
    private HashMap<String, String> waferInfors;
    private Map<String, Parameterbean> parameterInfors;
    private Map<String, List<Double>> parameterCollections;
    private Map<String, List<Double>> parameterPassBinsCollections;
    private Map<Integer,Map<String, List<Double>>> parameterPassBinsCollectionsBySite;
    private ArrayList<String> paramList;
    private Map<String, DieInfor> dieInforMap;
    private Set<String> passBins;


    public HashMap<String, String> getWaferInfors() {
        return waferInfors;
    }

    public void setWaferInfors(HashMap<String, String> waferInfors) {
        this.waferInfors = waferInfors;
    }

    public Map<String, Parameterbean> getParameterInfors() {
        return parameterInfors;
    }

    public void setParameterInfors(Map<String, Parameterbean> parameterInfors) {
        this.parameterInfors = parameterInfors;
    }

    public Map<String, List<Double>> getParameterCollections() {
        return parameterCollections;
    }

    public void setParameterCollections(Map<String, List<Double>> parameterCollections) {
        this.parameterCollections = parameterCollections;
    }

    public Map<String, List<Double>> getParameterPassBinsCollections() {
        return parameterPassBinsCollections;
    }

    public void setParameterPassBinsCollections(Map<String, List<Double>> parameterPassBinsCollections) {
        this.parameterPassBinsCollections = parameterPassBinsCollections;
    }

    public ArrayList<String> getParamList() {
        return paramList;
    }

    public void setParamList(ArrayList<String> paramList) {
        this.paramList = paramList;
    }

    public Map<String, DieInfor> getDieInforMap() {
        return dieInforMap;
    }

    public void setDieInforMap(Map<String, DieInfor> dieInforMap) {
        this.dieInforMap = dieInforMap;
    }

    public Map<Integer, Map<String, List<Double>>> getParameterPassBinsCollectionsBySite() {
        return parameterPassBinsCollectionsBySite;
    }

    public void setParameterPassBinsCollectionsBySite(Map<Integer, Map<String, List<Double>>> parameterPassBinsCollectionsBySite) {
        this.parameterPassBinsCollectionsBySite = parameterPassBinsCollectionsBySite;
    }

    public Set<String> getPassBins() {
        return passBins;
    }

    public void setPassBins(Set<String> passBins) {
        this.passBins = passBins;
    }
}
