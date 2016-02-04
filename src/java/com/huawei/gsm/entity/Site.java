/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author fransfilastap
 */

public class Site implements Serializable {
    

    private int id;
    private String siteName;
    private String siteId;
    private String address;
    private double latitude;
    private double longitude;
    private SiteGroup group; 
    private List<Cell> cells;

    
    public Site( String siteId, String siteName ){
        this.siteId = siteId;
        this.siteName = siteName;
    }

    public Site(int id, String siteName, String siteId,  String address, double latitude, double longitude,SiteGroup group) {
        this.id = id;
        this.siteName = siteName;
        this.siteId = siteId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.group = group;
    }
    
    public Site(){}

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
    
    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public SiteGroup getGroup() {
        return group;
    }

    public void setGroup(SiteGroup group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }


    @Override
    public String toString() {
        String name = id+"Site ID = "+getSiteId()+"; Site Mame = "+getSiteName();
        return name; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean equals(Object obj) {
        Site s = (Site) obj;
        return ( s.getSiteId().equalsIgnoreCase(getSiteId()) );
    }


    
    
    
    
    
    
}
