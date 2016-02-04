/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.requestholder;

import com.huawei.gsm.entity.KQIType;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SectorKQIChartRequestHolder {
    private KQIType type;
    private List<String> cells;

    public SectorKQIChartRequestHolder() {
    }

    
    
    public KQIType getType() {
        return type;
    }

    public void setType(KQIType type) {
        this.type = type;
    }

    public List<String> getCells() {
        return cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }
    
    
    
}
