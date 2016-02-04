/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.util.requestholder;

import com.huawei.gsm.entity.Frequency;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class SectorKPIRequestHolder {
    private Frequency technology;
    private List<String> cells;

    public SectorKPIRequestHolder() {
    }

    public Frequency getTechnology() {
        return technology;
    }

    public void setTechnology(Frequency technology) {
        this.technology = technology;
    }

    public List<String> getCells() {
        return cells;
    }

    public void setCells(List<String> cells) {
        this.cells = cells;
    }
    
    
}
