package com.vtest.it.patparameterannalysis.pojo;

import java.io.Serializable;
import java.util.List;

public class DieInfor implements Serializable {
    private static final long serialVersionUID=1l;
    private int coordinateX;
    private int coordinateY;
    private int hardBin;
    private int softBin;
    private int site;
    private List<Double> paramList;

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getHardBin() {
        return hardBin;
    }

    public void setHardBin(int hardBin) {
        this.hardBin = hardBin;
    }

    public int getSoftBin() {
        return softBin;
    }

    public void setSoftBin(int softBin) {
        this.softBin = softBin;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public List<Double> getParamList() {
        return paramList;
    }

    public void setParamList(List<Double> paramList) {
        this.paramList = paramList;
    }
}
