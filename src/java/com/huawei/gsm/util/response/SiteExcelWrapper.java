/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.response;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class SiteExcelWrapper {
    
    private String siteId;
    private String siteName;
    private String address;
    private double latitute;
    private double longitude;
    private String siteGroup;
    private String cellIndex;
    private String cellName;
    private String frequency;

    public SiteExcelWrapper() {
    }

    public SiteExcelWrapper(String siteId, String siteName, String address, double latitute, double longitude, String siteGroup, String cellIndex, String cellName, String frequency) {
        this.siteId = siteId;
        this.siteName = siteName;
        this.address = address;
        this.latitute = latitute;
        this.longitude = longitude;
        this.siteGroup = siteGroup;
        this.cellIndex = cellIndex;
        this.cellName = cellName;
        this.frequency = frequency;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSiteGroup() {
        return siteGroup;
    }

    public void setSiteGroup(String siteGroup) {
        this.siteGroup = siteGroup;
    }

    public String getCellIndex() {
        return cellIndex;
    }

    public void setCellIndex(String cellIndex) {
        this.cellIndex = cellIndex;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    
    
}
