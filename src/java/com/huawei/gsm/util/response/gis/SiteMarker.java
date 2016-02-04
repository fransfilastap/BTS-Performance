/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.response.gis;

import com.huawei.gsm.entity.Severity;
import com.huawei.gsm.entity.Site;
import java.util.List;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
public class SiteMarker extends Site{
    
    private Severity severity;
    private List<CellMarker> cellMarkers;
    private List<SectorMarker> sectors;
    
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public List<CellMarker> getCellMarkers() {
        return cellMarkers;
    }

    public void setCellMarkers(List<CellMarker> cellMarkers) {
        this.cellMarkers = cellMarkers;
    }

    public List<SectorMarker> getSectors() {
        return sectors;
    }

    public void setSectors(List<SectorMarker> sectors) {
        this.sectors = sectors;
    }
    
    
    
    
}
