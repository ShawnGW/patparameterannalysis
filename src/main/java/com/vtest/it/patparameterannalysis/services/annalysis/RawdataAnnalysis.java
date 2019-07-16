package com.vtest.it.patparameterannalysis.services.annalysis;


import com.vtest.it.patparameterannalysis.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public  class RawdataAnnalysis {
    public void annalysis(TreeMap<Integer, File> storeMap, RawdataAnalysisResultBean bean) throws IOException {
        boolean firstRawdataFlag=true;
        HashMap<String,String> specificDieMap=new HashMap<>();
        FileReader in = new FileReader(storeMap.get(0));
        BufferedReader bufferedReader = new BufferedReader(in);
        String context;
        boolean parameterFlag = false;
        boolean specificDie = false;
        boolean informationFlag = false;
        boolean skipFlag = true;
        HashMap<String, String> waferInfors = new HashMap<>();
        Map<String, Parameterbean> parameterInfors = new LinkedHashMap<>();
        Map<String, List<Double>> parameterCollections = new HashMap<>(256);
        Map<String, List<Double>> parameterPassBinsCollections = new HashMap<>(256);
        Map<Integer, Map<String, List<Double>>> parameterPassBinsCollectionsBySite=new HashMap<>(256);
        ArrayList<String> paramList = new ArrayList<>(256);
        Map<String, DieInfor> dieInforMap = new HashMap<>();
        Set<String> passBins = new HashSet<>();
        while ((context = bufferedReader.readLine()) != null) {
            if (context.equals("WaferInfo")) {
                informationFlag = true;
                continue;
            }
            if (context.equals("ParaList")) {
                informationFlag = false;
                parameterFlag = true;
                continue;
            }
            if (context.equals("ParaRawdata")) {
                parameterFlag = false;
                specificDie = true;
                continue;
            }
            if (informationFlag) {
                String[] tokens = context.split("\\|");
                waferInfors.put(tokens[0].trim(), tokens[1].trim());
                if (tokens[0].trim().equals("pass_bins")) {
                    String[] passBinsArray = tokens[1].split(",");
                    for (int i = 0; i < passBinsArray.length; i++) {
                        passBins.add(passBinsArray[i]);
                    }
                }
            }
            if (parameterFlag) {
                String[] tokens = context.split(":");
                String paraName = tokens[0];
                paramList.add(paraName);
                String[] limits = tokens[1].trim().split("\\|");
                Parameterbean parameterbean = new Parameterbean();
                parameterbean.setLowLimit(Double.valueOf(limits[0]));
                if (limits.length == 2 && !"".equals(limits[1])) {
                    parameterbean.setHighLimit(Double.valueOf(limits[1]));
                } else {
                    parameterbean.setHighLimit(9999999.9999999);
                }
                if (limits.length == 3) {
                    parameterbean.setUnit(limits[2]);
                } else {
                    parameterbean.setUnit("NA");
                }
                parameterInfors.put(paraName, parameterbean);
            }
            if (specificDie) {
                if (skipFlag) {
                    skipFlag = false;
                    continue;
                }
                String[] tokens = context.split("\\|");
                specificDieMap.put(tokens[0] + ":" + tokens[1],context);
            }
        }
        in.close();
        bufferedReader.close();
        if (storeMap.size()>1){
            for (Integer order : storeMap.keySet()) {
                if (firstRawdataFlag){
                    firstRawdataFlag=false;
                    continue;
                }else {
                    boolean specificDieOthers=false;
                    boolean skipFlagOthers=true;
                    File rawdata=storeMap.get(order);
                    FileReader reader=new FileReader(rawdata);
                    BufferedReader bufferedReaderOthers=new BufferedReader(reader);
                    String contextOthers;
                    while ((contextOthers=bufferedReaderOthers.readLine())!=null)
                    {
                        if (contextOthers.equals("ParaRawdata")){
                            specificDieOthers = true;
                            continue;
                        }
                        if (specificDieOthers){
                            if (skipFlagOthers){
                                skipFlagOthers=false;
                                continue;
                            }
                            String[] tokens = contextOthers.split("\\|");
                            specificDieMap.put(tokens[0] + ":" + tokens[1],contextOthers);
                        }
                    }
                    bufferedReader.close();
                    reader.close();
                }
            }
        }
        for (Map.Entry<String,String> entry:specificDieMap.entrySet()) {
            String[] tokens = entry.getValue().split("\\|");
            int coordinateX = Integer.valueOf(tokens[0]);
            int coordinateY = Integer.valueOf(tokens[1]);
            int hardBin = Integer.valueOf(tokens[2]);
            int softBin = Integer.valueOf(tokens[3]);
            int site = Integer.valueOf(tokens[4]);
            List<Double> dieParamList = new ArrayList<>(256);
            for (int i = 5; i < tokens.length; i++) {
                Double param = Double.valueOf(tokens[i]);
                dieParamList.add(param);
                String parameterName=paramList.get(i - 5);
                if (parameterCollections.containsKey(parameterName)) {
                    parameterCollections.get(parameterName).add(param);
                } else {
                    List<Double> oneTypeParameterList = new LinkedList<>();
                    oneTypeParameterList.add(param);
                    parameterCollections.put(parameterName, oneTypeParameterList);
                }
                if (passBins.contains(tokens[3])) {
                    if (parameterPassBinsCollections.containsKey(parameterName)) {
                        parameterPassBinsCollections.get(parameterName).add(param);
                    } else {
                        List<Double> oneTypeParameterList = new LinkedList<>();
                        oneTypeParameterList.add(param);
                        parameterPassBinsCollections.put(parameterName, oneTypeParameterList);
                    }
                    if (parameterPassBinsCollectionsBySite.containsKey(site)){
                        Map<String, List<Double>> siteParameterPassBinsCollections=parameterPassBinsCollectionsBySite.get(site);
                        if (siteParameterPassBinsCollections.containsKey(parameterName)) {
                            siteParameterPassBinsCollections.get(parameterName).add(param);
                        } else {
                            List<Double> oneTypeParameterList = new LinkedList<>();
                            oneTypeParameterList.add(param);
                            siteParameterPassBinsCollections.put(parameterName, oneTypeParameterList);
                        }
                    }else {
                        Map<String, List<Double>> siteParameterPassBinsCollections=new HashMap<>();
                        List<Double> oneTypeParameterList = new LinkedList<>();
                        oneTypeParameterList.add(param);
                        siteParameterPassBinsCollections.put(parameterName,oneTypeParameterList);
                        parameterPassBinsCollectionsBySite.put(site,siteParameterPassBinsCollections);
                    }
                }
            }
            DieInfor dieInfor = new DieInfor();
            dieInfor.setCoordinateX(coordinateX);
            dieInfor.setCoordinateY(coordinateY);
            dieInfor.setHardBin(hardBin);
            dieInfor.setSoftBin(softBin);
            dieInfor.setSite(site);
            dieInfor.setParamList(dieParamList);
            dieInforMap.put(tokens[0] + ":" + tokens[1], dieInfor);
        }
        bean.setWaferInfors(waferInfors);
        bean.setParameterInfors(parameterInfors);
        bean.setParameterCollections(parameterCollections);
        bean.setParameterPassBinsCollections(parameterPassBinsCollections);
        bean.setParamList(paramList);
        bean.setDieInforMap(dieInforMap);
        bean.setParameterPassBinsCollectionsBySite(parameterPassBinsCollectionsBySite);
    }
}
