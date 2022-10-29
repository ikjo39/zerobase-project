package com.example.zerobaseproject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WifiInfo {
    private double distance;
    private String manageNum;
    private String region;
    private String wifiName;
    private String roadAddr;
    private String detailedAddr;
    private String insPlace;
    private String insType;
    private String insDepartment;
    private String serviceClass;
    private String netType;
    private String insYear;
    private String inOutClass;
    private String accessEnv;
    private double xCor;
    private double yCor;
    private String operateDate;

}
