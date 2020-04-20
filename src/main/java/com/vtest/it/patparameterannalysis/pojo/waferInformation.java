package com.vtest.it.patparameterannalysis.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class waferInformation implements Serializable {
    private static final long serialVersionUID=1L;
    private String customer;
    private String device;
    private String lot;
    private String cpStep;
    private String waferId;

}
