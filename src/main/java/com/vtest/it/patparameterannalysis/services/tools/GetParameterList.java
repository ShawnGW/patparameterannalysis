package com.vtest.it.patparameterannalysis.services.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GetParameterList {
    public static Map<String, TreeMap<Integer, File>> getList(File source) {
        Map<String, TreeMap<Integer, File>> map = new HashMap<>();
        File[] files = source.listFiles();
        for (File rawdata : files) {
            String fileName = rawdata.getName();
            String waferId = fileName.split("_")[0];
            Integer order = Integer.valueOf(fileName.split("_")[1].substring(2, 3));
            if (map.containsKey(waferId)) {
                map.get(waferId).put(order, rawdata);
            } else {
                TreeMap<Integer, File> storeMap = new TreeMap<>();
                storeMap.put(order, rawdata);
                map.put(waferId, storeMap);
            }
        }
        return map;
    }
}
