package com.vtest.it.patparameterannalysis.services.deal;

import com.vtest.it.patparameterannalysis.pojo.ParameterAfterCalculateResultBean;
import com.vtest.it.patparameterannalysis.pojo.ParameterJudgementStandardBean;
import com.vtest.it.patparameterannalysis.pojo.RawdataAnalysisResultBean;
import com.vtest.it.patparameterannalysis.pojo.waferInformation;
import com.vtest.it.patparameterannalysis.services.annalysis.RawdataAnnalysis;
import com.vtest.it.patparameterannalysis.services.tools.GetParameterList;
import com.vtest.it.patparameterannalysis.services.tools.RawdataParameterCalculation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Service
public class StartDeal {
    @Autowired
    private RawdataAnnalysis rawdataAnnalysis;
    @Autowired
    private RawdataParameterCalculation rawdataParameterCalculation;

    @Scheduled(fixedDelay = 1000 * 60)
    public void deal() {
        System.out.println("start dealing...");
        try {
            emptyDirectoryDelete(new File("/source"));
            File[] files = new File("/source").listFiles();
            if (files.length == 0) {
                return;
            }
            Map<File, waferInformation> WaferInformationMap = new HashMap<>();
            for (int i = 0; i < files.length; i++) {
                String customer = files[i].getName();
                if (files[i].isDirectory() && files[i].listFiles().length > 0) {
                    File[] devices = files[i].listFiles();
                    for (int j = 0; j < devices.length; j++) {
                        String device = devices[j].getName();
                        if (devices[j].isDirectory() && devices[j].listFiles().length > 0) {
                            File[] lots = devices[j].listFiles();
                            for (int k = 0; k < lots.length; k++) {
                                String lot = lots[k].getName();
                                if (lots[k].isDirectory() && lots[k].listFiles().length > 0) {
                                    File[] cpProcess = lots[k].listFiles();
                                    for (int l = 0; l < cpProcess.length; l++) {
                                        String cpStep = cpProcess[l].getName();
                                        if (cpProcess[l].isDirectory() && cpProcess[l].listFiles().length > 0) {
                                            waferInformation waferInformation = new waferInformation();
                                            waferInformation.setCustomer(customer);
                                            waferInformation.setDevice(device);
                                            waferInformation.setLot(lot);
                                            waferInformation.setCpStep(cpStep);
                                            waferInformation.setWaferId("NA");
                                            WaferInformationMap.put(cpProcess[l], waferInformation);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (Map.Entry<File, waferInformation> entryWafer : WaferInformationMap.entrySet()) {
                if (!fileGenerateTimeCheck(entryWafer.getKey().listFiles())) {
                    continue;
                }
                try {
                    Map<String, TreeMap<Integer, File>> map = GetParameterList.getList(entryWafer.getKey());
                    waferInformation waferInformation = entryWafer.getValue();
                    for (Map.Entry<String, TreeMap<Integer, File>> entry : map.entrySet()) {
                        try {
                            RawdataAnalysisResultBean bean = new RawdataAnalysisResultBean();
                            String waferId = entry.getKey();
                            PrintWriter printWriter = new PrintWriter(new File("/target/" + waferInformation.getCustomer() + "_" + waferInformation.getDevice() + "_" + waferInformation.getLot() + "_" + waferId + "_CP1_V186.csv"));
                            printWriter.print("X,Y\r\n");
                            TreeMap<Integer, File> storeMap = entry.getValue();
                            rawdataAnnalysis.annalysis(storeMap, bean);
//                            Map<String, ParameterJudgementStandardBean> parameterJudgmentStandard = new HashMap<>();
                            Map<Integer, Map<String, ParameterJudgementStandardBean>> parameterJudgmentStandardSites = new HashMap<>();
                            /* by all compute end*/
//                            for (String parameter : bean.getParameterPassBinsCollections().keySet()) {
//                                ParameterAfterCalculateResultBean parameterAfterCalculateResultBean = new ParameterAfterCalculateResultBean();
//                                rawdataParameterCalculation.calculate(bean.getParameterPassBinsCollections().get(parameter), parameterAfterCalculateResultBean);
//                                parameterAfterCalculateResultBean.setLowLimit(bean.getParameterInfors().get(parameter).getLowLimit());
//                                parameterAfterCalculateResultBean.setHighLimit(bean.getParameterInfors().get(parameter).getHighLimit());
//                                parameterAfterCalculateResultBean.setUnit(bean.getParameterInfors().get(parameter).getUnit());
//                                parameterAfterCalculateResultBean.setFlag(false);
//                                ParameterJudgementStandardBean parameterJudgementStandardBean = new ParameterJudgementStandardBean();
//                                double valueOfThreeQuarter = parameterAfterCalculateResultBean.getValueOfThreeQuarter();
//                                double valueOfQuarter = parameterAfterCalculateResultBean.getValueOfQuarter();
//                                parameterJudgementStandardBean.setLowLimit(parameterAfterCalculateResultBean.getMiddleValue() - 6 * ((valueOfThreeQuarter - valueOfQuarter) / 1.35));
//                                parameterJudgementStandardBean.setHighLimit(parameterAfterCalculateResultBean.getAverage() + 6 * ((valueOfThreeQuarter - valueOfQuarter) / 1.35));
//                                parameterJudgmentStandard.put(parameter, parameterJudgementStandardBean);
//                            }
                            /* by all compute end*/


                            /* by site start*/
                            for (Integer site : bean.getParameterPassBinsCollectionsBySite().keySet()) {
                                for (String parameter : bean.getParameterPassBinsCollectionsBySite().get(site).keySet()) {
                                    ParameterAfterCalculateResultBean parameterAfterCalculateResultBean = new ParameterAfterCalculateResultBean();
                                    rawdataParameterCalculation.calculate(bean.getParameterPassBinsCollectionsBySite().get(site).get(parameter), parameterAfterCalculateResultBean);
                                    parameterAfterCalculateResultBean.setLowLimit(bean.getParameterInfors().get(parameter).getLowLimit());
                                    parameterAfterCalculateResultBean.setHighLimit(bean.getParameterInfors().get(parameter).getHighLimit());
                                    parameterAfterCalculateResultBean.setUnit(bean.getParameterInfors().get(parameter).getUnit());
                                    parameterAfterCalculateResultBean.setFlag(false);
                                    ParameterJudgementStandardBean parameterJudgementStandardBean = new ParameterJudgementStandardBean();
                                    double valueOfThreeQuarter = parameterAfterCalculateResultBean.getValueOfThreeQuarter();
                                    double valueOfQuarter = parameterAfterCalculateResultBean.getValueOfQuarter();
                                    parameterJudgementStandardBean.setLowLimit(parameterAfterCalculateResultBean.getMiddleValue() - 6 * ((valueOfThreeQuarter - valueOfQuarter) / 1.35));
                                    parameterJudgementStandardBean.setHighLimit(parameterAfterCalculateResultBean.getMiddleValue() + 6 * ((valueOfThreeQuarter - valueOfQuarter) / 1.35));
                                    if (parameterJudgmentStandardSites.containsKey(site)) {
                                        parameterJudgmentStandardSites.get(site).put(parameter, parameterJudgementStandardBean);
                                    } else {
                                        Map<String, ParameterJudgementStandardBean> parameterJudgmentStandardOneParameter = new HashMap<>();
                                        parameterJudgmentStandardOneParameter.put(parameter, parameterJudgementStandardBean);
                                        parameterJudgmentStandardSites.put(site, parameterJudgmentStandardOneParameter);
                                    }
                                }
                            }
                            /* by site start*/

                            ArrayList<String> paramList = bean.getParamList();
                            /* by all start*/
//                            for (String dieCoordinate : bean.getDieInforMap().keySet()) {
//                                boolean flag = false;
//                                if (bean.getDieInforMap().get(dieCoordinate).getSoftBin() != 1) {
//                                    continue;
//                                }
//                                List<Double> paramValueList = bean.getDieInforMap().get(dieCoordinate).getParamList();
//                                for (int i = 0; i < paramValueList.size(); i++) {
//                                    if (paramValueList.get(i) < parameterJudgmentStandard.get(paramList.get(i)).getLowLimit() || paramValueList.get(i) > parameterJudgmentStandard.get(paramList.get(i)).getHighLimit()) {
//                                        flag = true;
//                                        break;
//                                    }
//                                }
//                                if (flag) {
//                                    printWriter.print(dieCoordinate.split(":")[0] + "," + dieCoordinate.split(":")[1] + ",10\r\n");
//                                }
//                            }
                            /* by all end*/

                            /* by site start*/
                            for (String dieCoordinate : bean.getDieInforMap().keySet()) {
                                boolean flag = false;
                                if (!bean.getPassBins().contains(bean.getDieInforMap().get(dieCoordinate).getSoftBin() + "")) {
                                    continue;
                                }
                                List<Double> paramValueList = bean.getDieInforMap().get(dieCoordinate).getParamList();
                                Integer site = bean.getDieInforMap().get(dieCoordinate).getSite();
                                for (int i = 0; i < paramValueList.size(); i++) {
                                    if (paramValueList.get(i) < parameterJudgmentStandardSites.get(site).get(paramList.get(i)).getLowLimit() || paramValueList.get(i) > parameterJudgmentStandardSites.get(site).get(paramList.get(i)).getHighLimit()) {
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag) {
                                    printWriter.print(dieCoordinate.split(":")[0] + "," + dieCoordinate.split(":")[1] + ",10\r\n");
                                }
                            }
                            /* by site end*/
                            printWriter.flush();
                            printWriter.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    File backupDirectory = new File("/backup/" + waferInformation.getCustomer() + "/" + waferInformation.getDevice() + "/" + waferInformation.getLot() + "/" + waferInformation.getCpStep());
                    if (!backupDirectory.exists()) {
                        backupDirectory.mkdirs();
                    }
                    for (File waferRaw : entryWafer.getKey().listFiles()) {
                        FileUtils.copyFile(waferRaw, new File(backupDirectory.getPath() + "/" + waferRaw.getName()));
                        FileUtils.forceDelete(waferRaw);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void emptyDirectoryDelete(File file) {
        if (file.isDirectory()) {
            if (file.listFiles().length == 0) {
                if (file.getName().equals("source")) {
                    return;
                }
                try {
                    FileUtils.forceDelete(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    emptyDirectoryDelete(files[i]);
                }
            }
        }
    }

    public boolean fileGenerateTimeCheck(File[] files) {
        long now = System.currentTimeMillis();
        for (int i = 0; i < files.length; i++) {
            if (!((now - files[i].lastModified()) / 1000 > 60 * 2)) {
                return false;
            }
        }
        return true;
    }
}
