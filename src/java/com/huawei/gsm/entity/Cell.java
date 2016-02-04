/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.entity;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */

public class Cell implements Serializable {

    private Long id;
    private String cellId;
    private String cellIndex;
    private String cellName;
    private String frequency;
    private Site site;
    private int sector;

    public Cell() {
    }

    public Cell(Long id, String cellId, String cellIndex, String cellName, String frequency, Site site) {
        this.id = id;
        this.cellId = cellId;
        this.cellIndex = cellIndex;
        this.cellName = cellName;
        this.frequency = frequency;
        this.site = site;
    }

    public Cell(int id, Object object, Object object0, Object object1, Object object2, Object object3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Site getSite() {
        return site;
    }
    public void setSite(Site site) {
        this.site = site;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }
    
    
    
    
}
