/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.requestholder;

import com.huawei.gsm.entity.KPIType;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SectorKPIChartRequestHolder {
    private List<String> cells;
    private KPIType type;

    public SectorKPIChartRequestHolder() {
    }

    public List<String> getCells() {
        return cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }

    public KPIType getType() {
        return type;
    }

    public void setType(KPIType type) {
        this.type = type;
    }
    
    
}
