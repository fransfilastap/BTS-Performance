/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.response.gis;

import com.huawei.gsm.entity.Alarm;
import com.huawei.gsm.entity.Cell;
import com.huawei.gsm.entity.Frequency;
import com.huawei.gsm.entity.Severity;
import com.huawei.gsm.entity.Site;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SectorMarker {
    
    private Site site;
    private Frequency type;
    private int sectorNumber;
    private double azimut;
    private List<Cell> cells;
    private List<Cell> cellWithAlarms;
    private Severity severity;
    private List<Alarm> alarms;

    /*public SectorMarker(Site site, Frequency type, int sectorNumber, double azimut, List<Cell> cells, List<Cell> cellWithAlarms, Severity severity, List<Alarm> alarms) {
        this.site = site;
        this.type = type;
        this.sectorNumber = sectorNumber;
        this.azimut = azimut;
        this.cells = cells;
        this.cellWithAlarms = cellWithAlarms;
        this.severity = severity;
        this.alarms = alarms;
    }*/
    
    public SectorMarker() {
    }
    
    

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Frequency getType() {
        return type;
    }

    public void setType(Frequency type) {
        this.type = type;
    }

    public int getSectorNumber() {
        return sectorNumber;
    }

    public void setSectorNumber(int sectorNumber) {
        this.sectorNumber = sectorNumber;
    }

    public double getAzimut() {
        return azimut;
    }

    public void setAzimut(double azimut) {
        this.azimut = azimut;
    }

    public List<Cell> getCellWithAlarms() {
        return cellWithAlarms;
    }

    public void setCellWithAlarms(List<Cell> cellWithAlarms) {
        this.cellWithAlarms = cellWithAlarms;
    }


    public List<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
    
    
    
    
}
